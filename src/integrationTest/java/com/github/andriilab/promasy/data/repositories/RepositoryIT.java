package com.github.andriilab.promasy.data.repositories;

import com.github.andriilab.promasy.data.commands.CommandsHandler;
import com.github.andriilab.promasy.data.commands.CreateCommand;
import com.github.andriilab.promasy.data.commands.DeleteCommand;
import com.github.andriilab.promasy.data.commands.RefreshCommand;
import com.github.andriilab.promasy.data.commands.UpdateCommand;
import com.github.andriilab.promasy.domain.IEntity;
import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import com.github.andriilab.promasy.domain.bid.entities.ReasonForSupplierChoice;
import com.github.andriilab.promasy.domain.item.entities.Producer;
import com.github.andriilab.promasy.domain.item.entities.Supplier;
import com.github.andriilab.promasy.it.AbstractIntegrationTest;
import com.github.andriilab.promasy.it.Sql;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.PersistenceException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The generic {@link Repository} behaviour (create, update, soft delete, refresh, locking, listing) against real SQL,
 * run for every simple reference-data repository and driven through {@link CommandsHandler} like the application does.
 */
class RepositoryIT extends AbstractIntegrationTest {

    private final CommandsHandler commands = new CommandsHandler(storage);

    /** One simple repository under test: how to build its entity and where the description lives in the table. */
    static final class Subject<T extends IEntity> {
        final String table;
        final String descriptionColumn;
        final Class<T> entityClass;
        final Function<String, T> factory;

        Subject(String table, String descriptionColumn, Class<T> entityClass, Function<String, T> factory) {
            this.table = "promasy." + table;
            this.descriptionColumn = descriptionColumn;
            this.entityClass = entityClass;
            this.factory = factory;
        }

        @Override
        public String toString() {
            return entityClass.getSimpleName();
        }
    }

    static Stream<Arguments> subjects() {
        return Stream.of(
                new Subject<>("amount_units", "amount_unit_desc", AmountUnit.class, AmountUnit::new),
                new Subject<>("producers", "brand_name", Producer.class, Producer::new),
                new Subject<>("suppliers", "supplier_name", Supplier.class, name -> new Supplier(name, "", "")),
                new Subject<>("reasons_for_suppl", "reason_name", ReasonForSupplierChoice.class,
                        ReasonForSupplierChoice::new)
        ).map(Arguments::of);
    }

    private <T extends IEntity> T created(Subject<T> subject, String description) {
        T entity = subject.factory.apply(description);
        commands.handle(new CreateCommand<>(entity));
        return entity;
    }

    private <T extends IEntity> List<String> listedDescriptions(Subject<T> subject) {
        return storage.getByClass(subject.entityClass).getResults().stream()
                .map(IEntity::getDescription).collect(Collectors.toList());
    }

    private static Object column(Subject<?> subject, String column, long id) {
        return Sql.scalar("SELECT " + column + " FROM " + subject.table + " WHERE id = ?", id);
    }

    @ParameterizedTest
    @MethodSource("subjects")
    <T extends IEntity> void create_newEntity_assignsHiloIdAndCreationAudit(Subject<T> subject) {
        T first = created(subject, "first");
        T second = created(subject, "second");

        assertThat(first.getModelId()).isPositive();
        assertThat(second.getModelId()).isPositive().isNotEqualTo(first.getModelId());
        assertThat(column(subject, "created_by", first.getModelId())).isEqualTo(admin.getModelId());
        Timestamp createdDate = (Timestamp) column(subject, "created_date", first.getModelId());
        assertThat(Math.abs(createdDate.getTime() - System.currentTimeMillis()))
                .isLessThan(TimeUnit.MINUTES.toMillis(5));
        assertThat(column(subject, "modified_by", first.getModelId())).isNull();
        assertThat(column(subject, "active", first.getModelId())).isEqualTo(true);
        assertThat(column(subject, "version", first.getModelId())).isEqualTo(0L);
    }

    @ParameterizedTest
    @MethodSource("subjects")
    <T extends IEntity> void update_changedEntity_bumpsVersionAndSetsModificationAudit(Subject<T> subject) {
        T entity = created(subject, "before");

        entity.setDescription("after");
        commands.handle(new UpdateCommand<>(entity));

        long id = entity.getModelId();
        assertThat(column(subject, subject.descriptionColumn, id)).isEqualTo("after");
        // one UPDATE: getServerTimestamp() reads without a transaction, so it no longer flushes the edit first
        assertThat(column(subject, "version", id)).isEqualTo(1L);
        assertThat(column(subject, "modified_by", id)).isEqualTo(admin.getModelId());
        assertThat(column(subject, "modified_date", id)).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("subjects")
    <T extends IEntity> void delete_existingEntity_keepsRowInactiveAndHidesItFromResults(Subject<T> subject) {
        T kept = created(subject, "kept");
        T removed = created(subject, "removed");

        commands.handle(new DeleteCommand<>(removed));

        long id = removed.getModelId();
        assertThat(Sql.count("SELECT count(*) FROM " + subject.table + " WHERE id = ?", id)).isEqualTo(1);
        assertThat(column(subject, "active", id)).isEqualTo(false);
        assertThat(column(subject, "modified_by", id)).isEqualTo(admin.getModelId());
        assertThat(listedDescriptions(subject)).containsExactly(kept.getDescription());
    }

    @ParameterizedTest
    @MethodSource("subjects")
    <T extends IEntity> void refresh_rowChangedOutsideHibernate_reloadsEntity(Subject<T> subject) {
        T entity = created(subject, "original");

        Sql.execute("UPDATE " + subject.table + " SET " + subject.descriptionColumn + " = 'changed' WHERE id = ?",
                entity.getModelId());
        commands.handle(new RefreshCommand<>(entity));

        assertThat(entity.getDescription()).isEqualTo("changed");
    }

    @ParameterizedTest
    @MethodSource("subjects")
    <T extends IEntity> void update_staleVersion_failsAndLeavesEntityManagerUsable(Subject<T> subject) {
        T entity = created(subject, "original");
        Sql.execute("UPDATE " + subject.table + " SET version = version + 1 WHERE id = ?", entity.getModelId());

        entity.setDescription("stale write");
        assertThatThrownBy(() -> commands.handle(new UpdateCommand<>(entity)))
                .isInstanceOf(PersistenceException.class);

        assertThat(em.getTransaction().isActive()).isFalse();
        assertThat(column(subject, subject.descriptionColumn, entity.getModelId())).isEqualTo("original");
        T another = created(subject, "another");
        assertThat(listedDescriptions(subject)).contains(another.getDescription());
    }

    /** A failed write rolls back and clears the shared EM, so every entity the UI still holds is detached. */
    @ParameterizedTest
    @MethodSource("subjects")
    <T extends IEntity> void update_entityDetachedByEarlierFailedWrite_savesChangesAndKeepsVersionInSync(Subject<T> subject) {
        T held = created(subject, "held");
        T broken = created(subject, "broken");
        Sql.execute("UPDATE " + subject.table + " SET version = version + 1 WHERE id = ?", broken.getModelId());
        broken.setDescription("stale write");
        assertThatThrownBy(() -> commands.handle(new UpdateCommand<>(broken))).isInstanceOf(PersistenceException.class);
        assertThat(em.contains(held)).isFalse();

        held.setDescription("first");
        commands.handle(new UpdateCommand<>(held));
        // the caller keeps its own instance, so a second write must not look stale
        held.setDescription("second");
        commands.handle(new UpdateCommand<>(held));

        long id = held.getModelId();
        assertThat(column(subject, subject.descriptionColumn, id)).isEqualTo("second");
        assertThat(held.getVersion()).isEqualTo((Long) column(subject, "version", id));
        assertThat(column(subject, "modified_by", id)).isEqualTo(admin.getModelId());
    }

    @ParameterizedTest
    @MethodSource("subjects")
    <T extends IEntity> void delete_entityDetachedByEarlierFailedWrite_deactivatesRow(Subject<T> subject) {
        T held = created(subject, "held");
        T broken = created(subject, "broken");
        Sql.execute("UPDATE " + subject.table + " SET version = version + 1 WHERE id = ?", broken.getModelId());
        assertThatThrownBy(() -> commands.handle(new UpdateCommand<>(broken))).isInstanceOf(PersistenceException.class);

        commands.handle(new DeleteCommand<>(held));

        assertThat(column(subject, "active", held.getModelId())).isEqualTo(false);
        assertThat(listedDescriptions(subject)).doesNotContain("held");
    }

    @ParameterizedTest
    @MethodSource("subjects")
    <T extends IEntity> void getResults_calledTwice_returnsIndependentListsOrderedByDescription(Subject<T> subject) {
        created(subject, "b");
        created(subject, "a");

        List<T> first = storage.getByClass(subject.entityClass).getResults();
        List<T> second = storage.getByClass(subject.entityClass).getResults();

        assertThat(first).isNotSameAs(second).containsExactlyElementsOf(second);
        assertThat(first.stream().map(IEntity::getDescription).toArray()).isEqualTo(new Object[]{"a", "b"});
        assertThatThrownBy(() -> first.add(null)).isInstanceOf(UnsupportedOperationException.class);
        assertThat(second).hasSize(2);
    }
}
