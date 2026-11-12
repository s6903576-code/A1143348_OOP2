import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class exercise2 extends JFrame {
    private final JComboBox<String> typeBox =
            new JComboBox<>(new String[]{"長度", "重量", "溫度"});
    private final JTextField inputField = new JTextField();
    private final JTextField resultField = new JTextField();
    private final JComboBox<String> fromBox = new JComboBox<>();
    private final JComboBox<String> toBox = new JComboBox<>();

    public exercise2() {
        super("單位換算器");
        setSize(480, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // NORTH：換算類型
        add(typeBox, BorderLayout.NORTH);

        // CENTER：兩列，每列一個文字欄位與單位選單
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 15));
        JPanel sourceRow = new JPanel(new BorderLayout(10, 0));
        sourceRow.add(inputField, BorderLayout.CENTER);
        sourceRow.add(fromBox, BorderLayout.EAST);
        JPanel targetRow = new JPanel(new BorderLayout(10, 0));
        targetRow.add(resultField, BorderLayout.CENTER);
        targetRow.add(toBox, BorderLayout.EAST);
        resultField.setEditable(false);
        centerPanel.add(sourceRow);
        centerPanel.add(targetRow);
        add(centerPanel, BorderLayout.CENTER);

        // SOUTH：換算按鈕
        JButton convertButton = new JButton("換算");
        add(convertButton, BorderLayout.SOUTH);
        typeBox.addActionListener(e -> updateUnits());
        convertButton.addActionListener(e -> convert());
        inputField.addActionListener(e -> convert());
        updateUnits();
        setLocationRelativeTo(null);
    }

    private void updateUnits() {
        String[] units;
        switch (typeBox.getSelectedIndex()) {
            case 0:
                units = new String[]{"公尺", "公分", "英吋", "英尺"};
                break;
            case 1:
                units = new String[]{"公斤", "公克", "磅", "盎司"};
                break;
            default:
                units = new String[]{"攝氏", "華氏", "克氏"};
                break;
        }
        fromBox.setModel(new DefaultComboBoxModel<>(units));
        toBox.setModel(new DefaultComboBoxModel<>(units));
        toBox.setSelectedIndex(1);
        resultField.setText("");
    }

    private void convert() {
        try {
            double value = Double.parseDouble(inputField.getText().trim());
            if (!Double.isFinite(value)) {
                throw new NumberFormatException();
            }
            int from = fromBox.getSelectedIndex();
            int to = toBox.getSelectedIndex();
            double result;

            if (typeBox.getSelectedIndex() == 0) {
                // 各單位相當於多少公尺
                double[] factors = {1.0, 0.01, 0.0254, 0.3048};
                result = value * factors[from] / factors[to];
            } else if (typeBox.getSelectedIndex() == 1) {
                // 各單位相當於多少公斤
                double[] factors = {1.0, 0.001, 0.45359237, 0.028349523125};
                result = value * factors[from] / factors[to];
            } else {
                // 先將來源溫度換成攝氏
                double celsius;
                if (from == 0) {
                    celsius = value;
                } else if (from == 1) {
                    celsius = (value - 32.0) * 5.0 / 9.0;
                } else {
                    celsius = value - 273.15;
                }
                // 再由攝氏換成目標溫度
                if (to == 0) {
                    result = celsius;
                } else if (to == 1) {
                    result = celsius * 9.0 / 5.0 + 32.0;
                } else {
                    result = celsius + 273.15;
                }
            }
            if (!Double.isFinite(result)) {
                throw new NumberFormatException();
            }
            DecimalFormat format = new DecimalFormat("0.##########");
            resultField.setText(format.format(result));
        } catch (NumberFormatException ex) {
            resultField.setText("");
            JOptionPane.showMessageDialog(this,
                    "請輸入有效數字，且數值不要過大！", "輸入錯誤",
                    JOptionPane.ERROR_MESSAGE);
            inputField.requestFocusInWindow();
            inputField.selectAll();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new exercise2().setVisible(true));
    }
}
