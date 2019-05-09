package com.github.andriilab.promasy.gui.components.toolbars;

import com.github.andriilab.promasy.gui.commons.Labels;

import javax.swing.*;
import java.awt.*;
import java.time.Year;
import java.util.stream.IntStream;

public class ControlsToolbar extends JToolBar {

    private ControlsToolbarListener listener;
    private JComboBox<Integer> yearPicker;
    private int selectedYear;

    public ControlsToolbar() {
        setFloatable(false);
        selectedYear = Year.now().getValue();
        listener = new EmptyControlsToolbarListener();
        yearPicker = new JComboBox<>(generateYearSpan(selectedYear));
        yearPicker.setSelectedIndex(10);
        yearPicker.setEditable(true);
        yearPicker.setPreferredSize(new Dimension(52, 22));

        setLayout(new FlowLayout(FlowLayout.TRAILING));

        add(new JLabel(Labels.withColon("reportYear")));
        add(yearPicker);

        yearPicker.addActionListener(e -> {
            if (parseSelectedYear()) {
                listener.reportYearChanged();
            }
        });
    }

    private static Integer[] generateYearSpan(int currentYear) {
        return IntStream.rangeClosed(currentYear - 10, currentYear + 5).boxed().toArray(Integer[]::new);
    }

    private boolean parseSelectedYear() {
        if (yearPicker.getSelectedItem() != null && yearPicker.getSelectedItem() instanceof Integer) {
            int input = Math.abs((int) yearPicker.getSelectedItem());
            if ((int) (Math.log10(input) + 1) == 4)
                return setSelectedYear(input);
        }

        yearPicker.setSelectedIndex(10);
        return setSelectedYear(Year.now().getValue());
    }

    private boolean setSelectedYear(int year) {
        if (year == selectedYear)
            return false;
        selectedYear = year;
        return true;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public void setListener(ControlsToolbarListener listener) {
        this.listener = listener;
    }
}
