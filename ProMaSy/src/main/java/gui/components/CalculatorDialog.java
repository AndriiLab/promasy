package gui.components;

import controller.Logger;
import gui.commons.Colors;
import gui.commons.Icons;
import gui.commons.Labels;
import model.models.EmptyModel;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple graphical calculator dialog
 */
public class CalculatorDialog extends JDialog implements ActionListener {

    private JButton[] numberBtns;
    private Map<String, JButton> operatorBtns;
    private JTextPane logPane;
    private JPanel buttonPanel;
    private StringBuilder sb;
    private BigDecimal number1;
    private BigDecimal number2;
    private BigDecimal result = BigDecimal.ZERO;
    private String operator;

    public CalculatorDialog(JFrame parent) {
        super(parent, Labels.getProperty("calculator"), false);
        setSize(240, 347);
        setLocationRelativeTo(parent);
        setResizable(false);
        Dimension buttonDim = new Dimension(45, 45);

        Font buttonFont = new Font(UIManager.getDefaults().getFont("Button.font").getName(), Font.BOLD, 15);
        logPane = new JTextPane();
        logPane.setEditable(false);
        logPane.setPreferredSize(new Dimension(40, 135));
        logPane.setText(EmptyModel.STRING);

        sb = new StringBuilder();

        String[] operators = {".", "+", "-", "*", "/", "=", "C", "<", "clp"};
        operatorBtns = new HashMap<>();
        for (String operator : operators) {
            JButton button = new JButton(operator);
            button.setFont(buttonFont);
            button.setPreferredSize(buttonDim);
            if (operator.equals("clp")) {
                button.setText(EmptyModel.STRING);
                button.setIcon(Icons.CLIPBOARD);
                button.setToolTipText(Labels.getProperty("copyToClipboard"));
            }
            button.addActionListener(this);
            operatorBtns.put(operator, button);
        }

        numberBtns = new JButton[10];
        for (int i = 0; i < 10; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setFont(buttonFont);
            button.setPreferredSize(buttonDim);
            button.addActionListener(this);
            numberBtns[i] = button;
        }

        createLayout();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                buttonPanel.requestFocusInWindow();
            }
        });

        buttonPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if ((key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) || (key >= KeyEvent.VK_NUMPAD0 && key <= KeyEvent.VK_NUMPAD9)) {
                    printNumber(String.valueOf(e.getKeyChar()));
                } else if (key == KeyEvent.VK_ENTER) {
                    printResult();
                } else if (key == KeyEvent.VK_BACK_SPACE) {
                    removeNum();
                } else if (key == KeyEvent.VK_COMMA || key == KeyEvent.VK_PERIOD || key == KeyEvent.VK_DECIMAL) {
                    printPeriod();
                } else if (key == KeyEvent.VK_PLUS ||
                        key == KeyEvent.VK_ADD ||
                        key == KeyEvent.VK_MULTIPLY ||
                        key == KeyEvent.VK_MINUS ||
                        key == KeyEvent.VK_SUBTRACT ||
                        key == KeyEvent.VK_DIVIDE ||
                        key == KeyEvent.VK_SLASH ||
                        (key == KeyEvent.VK_EQUALS) && ((e.getModifiers() & KeyEvent.SHIFT_MASK) != 0)) {
                    printOperator(String.valueOf(e.getKeyChar()));
                } else if ((key == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                    copyToClipboard();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        StyledDocument doc = logPane.getStyledDocument();
        if (ev.getSource() instanceof JButton) {
            JButton pressedButton = (JButton) ev.getSource();
            String btnText = pressedButton.getText();
            if (!btnText.isEmpty() && !Character.isDigit(btnText.toCharArray()[0])) {
                switch (btnText) {
                    case "C":
                        panelRemove(0, doc.getLength());
                        clear();
                        break;
                    case "<":
                        removeNum();
                        break;
                    case ".":
                        printPeriod();
                        break;
                    case "=":
                        printResult();
                        break;
                    default:
                        printOperator(btnText);
                        break;
                }
            } else if (btnText.isEmpty()) {
                copyToClipboard();
            } else {
                printNumber(btnText);
            }
        }
    }

    private void copyToClipboard() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(result.toPlainString()), null);
    }

    private void printOperator(String btnText) {
        if (operator != null) {
            printResult();
        }
        if (sb.length() == 0) {
            panelAdd(String.valueOf(result), Colors.BLUE);
            number1 = result;

        } else {
            number1 = new BigDecimal(sb.toString());
        }
        sb.setLength(0);
        operator = btnText;
        panelAdd("\n" + btnText + "\n", Colors.RED_LIGHT_SELECTED);
    }

    private void printPeriod() {
        if (sb.length() == 0) {
            printNumber("0");
        }
        printNumber(".");
    }

    private void removeNum() {
        if (logPane.getStyledDocument().getLength() > 0 && sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            panelRemove(logPane.getStyledDocument().getLength() - 1, 1);
        }
    }

    private void printResult() {
        if (sb.length() > 0) {
            number2 = new BigDecimal(sb.toString());
            calculateResult();
            panelAdd("\n=\n" + result + "\n", Colors.RED_LIGHT_SELECTED);
            clear();
        }
    }

    private void printNumber(String number) {
        sb.append(number);
        panelAdd(number, Colors.BLUE);
    }

    private void panelRemove(int offset, int charsRemove) {
        StyledDocument doc = logPane.getStyledDocument();
        try {
            doc.remove(offset, charsRemove);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    private void panelAdd(String text, Color color) {
        StyledDocument doc = logPane.getStyledDocument();
        Style style = logPane.addStyle("CurrentStyle", null);
        StyleConstants.setForeground(style, color);
        StyleConstants.setFontSize(style, 20);
        StyleConstants.setBold(style, true);
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException e) {
            Logger.errorEvent(null, e);
        }

    }

    private void clear() {
        number1 = null;
        number2 = null;
        operator = null;
        sb.setLength(0);
    }

    private void calculateResult() {
        switch (operator) {
            case "+":
                result = number1.add(number2);
                break;
            case "-":
                result = number1.subtract(number2);
                break;
            case "*":
                result = number1.multiply(number2);
                break;
            case "/":
                if (!number2.equals(BigDecimal.ZERO)) {
                    result = number1.divide(number2);
                    break;
                }
            default:
                result = BigDecimal.ZERO;
        }
    }

    private void createLayout() {
        buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 3;
        gc.gridx = 1;

        for (int i = 0; i < numberBtns.length; i++) {
            buttonPanel.add(numberBtns[i], gc);
            gc.gridx++;
            if (i % 3 == 0) {
                gc.gridy--;
                gc.gridx = 0;
            }
        }

        gc.gridy = 3;
        gc.gridx = 0;
        buttonPanel.add(operatorBtns.get("clp"), gc);

        gc.gridx = 2;
        buttonPanel.add(operatorBtns.get("."), gc);

        gc.gridy = 0;
        gc.gridx = 4;
        buttonPanel.add(operatorBtns.get("<"), gc);

        gc.gridy++;
        buttonPanel.add(operatorBtns.get("C"), gc);

        gc.gridy = 0;
        gc.gridx = 3;
        buttonPanel.add(operatorBtns.get("+"), gc);

        gc.gridy++;
        buttonPanel.add(operatorBtns.get("-"), gc);

        gc.gridy++;
        buttonPanel.add(operatorBtns.get("*"), gc);

        gc.gridy++;
        buttonPanel.add(operatorBtns.get("/"), gc);

        gc.gridx++;
        buttonPanel.add(operatorBtns.get("="), gc);

        setLayout(new BorderLayout());
        add(new JScrollPane(logPane), BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
