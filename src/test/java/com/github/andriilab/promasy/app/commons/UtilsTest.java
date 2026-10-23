package com.github.andriilab.promasy.app.commons;

import org.junit.jupiter.api.Test;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {

    private static JTable tableOf(Object[][] rows) {
        return new JTable(new DefaultTableModel(rows, new Object[]{"object", "name"}));
    }

    @Test
    void getRowWithObject_objectInColumn_returnsItsRowIndex() {
        Object target = new Object();
        JTable table = tableOf(new Object[][]{{new Object(), "a"}, {target, "b"}, {new Object(), "c"}});

        assertThat(Utils.getRowWithObject(table, 0, target)).isEqualTo(1);
    }

    @Test
    void getRowWithObject_objectNotInTable_returnsMinusOne() {
        JTable table = tableOf(new Object[][]{{new Object(), "a"}});

        assertThat(Utils.getRowWithObject(table, 0, new Object())).isEqualTo(-1);
    }

    @Test
    void getRowWithObject_emptyTable_returnsMinusOne() {
        assertThat(Utils.getRowWithObject(tableOf(new Object[0][]), 0, new Object())).isEqualTo(-1);
    }

    @Test
    void getRowWithObject_equalButNotIdenticalObject_isNotFound() {
        String stored = new String("same");
        JTable table = tableOf(new Object[][]{{stored, "a"}});

        assertThat(Utils.getRowWithObject(table, 0, new String("same"))).isEqualTo(-1);
    }

    @Test
    void getRowWithObject_searchesOnlyTheGivenColumn() {
        Object target = new Object();
        JTable table = tableOf(new Object[][]{{new Object(), target}});

        assertThat(Utils.getRowWithObject(table, 0, target)).isEqualTo(-1);
        assertThat(Utils.getRowWithObject(table, 1, target)).isEqualTo(0);
    }
}
