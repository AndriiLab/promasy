package com.github.andriilab.promasy.data.commands;

import com.github.andriilab.promasy.data.repositories.IRepository;
import com.github.andriilab.promasy.data.storage.Storage;
import com.github.andriilab.promasy.domain.bid.entities.AmountUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class
CommandsHandlerTest {

    @Mock
    private Storage storage;

    @Mock
    private IRepository<AmountUnit> repository;

    private CommandsHandler handler;
    private AmountUnit amountUnit;

    @BeforeEach
    void setUp() {
        handler = new CommandsHandler(storage);
        amountUnit = new AmountUnit();
    }

    static Stream<Arguments> commands() {
        return Stream.of(
                arguments(named("create", (Function<AmountUnit, ICommand<AmountUnit>>) CreateCommand::new),
                        (BiConsumer<IRepository<AmountUnit>, AmountUnit>) IRepository::create),
                arguments(named("update", (Function<AmountUnit, ICommand<AmountUnit>>) UpdateCommand::new),
                        (BiConsumer<IRepository<AmountUnit>, AmountUnit>) IRepository::update),
                arguments(named("delete", (Function<AmountUnit, ICommand<AmountUnit>>) DeleteCommand::new),
                        (BiConsumer<IRepository<AmountUnit>, AmountUnit>) IRepository::delete),
                arguments(named("refresh", (Function<AmountUnit, ICommand<AmountUnit>>) RefreshCommand::new),
                        (BiConsumer<IRepository<AmountUnit>, AmountUnit>) IRepository::refresh)
        );
    }

    @ParameterizedTest(name = "{0} command")
    @MethodSource("commands")
    void handle_knownCommand_dispatchesToMatchingRepositoryMethod(
            Function<AmountUnit, ICommand<AmountUnit>> commandFactory,
            BiConsumer<IRepository<AmountUnit>, AmountUnit> expectedCall) {
        when(storage.getByClass(AmountUnit.class)).thenReturn(repository);

        handler.handle(commandFactory.apply(amountUnit));

        expectedCall.accept(verify(repository), amountUnit);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void handle_unknownCommand_throwsUnsupportedOperationException() {
        ICommand<AmountUnit> unknownCommand = () -> amountUnit;

        assertThatThrownBy(() -> handler.handle(unknownCommand))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage(unknownCommand.getClass().getName());
    }
}
