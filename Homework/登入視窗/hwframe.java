import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class hwframe extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JCheckBox showPasswordCheckBox;
    private final char defaultEchoChar;

    public hwframe() {
        setTitle("會員登入");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 300);
        setResizable(false);

        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout(10, 20));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );
        setContentPane(mainPanel);

        // 標題
        JLabel titleLabel = new JLabel("會員登入", SwingConstants.CENTER);
        titleLabel.setFont(
                new Font(Font.SANS_SERIF, Font.BOLD, 24)
        );
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 輸入區
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 帳號標籤
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("帳號："), gbc);

        // 帳號輸入框
        usernameField = new JTextField(18);
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(usernameField, gbc);

        // 密碼標籤
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("密碼："), gbc);

        // 密碼輸入框
        passwordField = new JPasswordField(18);
        defaultEchoChar = passwordField.getEchoChar();
        gbc.gridx = 1;
        gbc.weightx = 1;
        inputPanel.add(passwordField, gbc);

        // 顯示密碼選項
        showPasswordCheckBox = new JCheckBox("顯示密碼");
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(showPasswordCheckBox, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // 按鈕區
        JPanel buttonPanel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 15, 0
        ));

        JButton loginButton = new JButton("登入");
        JButton clearButton = new JButton("清除");

        buttonPanel.add(loginButton);
        buttonPanel.add(clearButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 點擊登入
        loginButton.addActionListener(e -> login());

        // 點擊清除
        clearButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            showPasswordCheckBox.setSelected(false);
            passwordField.setEchoChar(defaultEchoChar);
            usernameField.requestFocusInWindow();
        });

        // 切換密碼顯示
        showPasswordCheckBox.addActionListener(e -> {
            passwordField.setEchoChar(
                    showPasswordCheckBox.isSelected()
                            ? (char) 0
                            : defaultEchoChar
            );
        });

        // 按 Enter 即可登入
        getRootPane().setDefaultButton(loginButton);

        // 視窗顯示在螢幕中央
        setLocationRelativeTo(null);
    }

    private void login() {
        String username = usernameField.getText().trim();
        char[] password = passwordField.getPassword();

        try {
            // 檢查是否輸入帳號與密碼
            if (username.isEmpty() || password.length == 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "請輸入帳號與密碼！",
                        "提醒",
                        JOptionPane.WARNING_MESSAGE
                );

                if (username.isEmpty()) {
                    usernameField.requestFocusInWindow();
                } else {
                    passwordField.requestFocusInWindow();
                }
                return;
            }

            // 練習用的固定帳號與密碼
            boolean validUsername = username.equals("admin");
            boolean validPassword = Arrays.equals(
                    password,
                    new char[]{'1', '2', '3', '4', '5', '6'}
            );

            if (validUsername && validPassword) {
                JOptionPane.showMessageDialog(
                        this,
                        "登入成功！歡迎 " + username + "！",
                        "登入成功",
                        JOptionPane.INFORMATION_MESSAGE
                );

                openHomeWindow(username);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "帳號或密碼錯誤，請重新輸入。",
                        "登入失敗",
                        JOptionPane.ERROR_MESSAGE
                );

                passwordField.setText("");
                passwordField.requestFocusInWindow();
            }
        } finally {
            // 清除暫存的密碼陣列
            Arrays.fill(password, '\0');
        }
    }

    private void openHomeWindow(String username) {
        JFrame homeFrame = new JFrame("會員首頁");
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setSize(420, 250);

        JPanel homePanel = new JPanel(new BorderLayout(10, 20));
        homePanel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        JLabel welcomeLabel = new JLabel(
                "歡迎登入，" + username + "！",
                SwingConstants.CENTER
        );
        welcomeLabel.setFont(
                new Font(Font.SANS_SERIF, Font.BOLD, 22)
        );
        homePanel.add(welcomeLabel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("登出");
        logoutButton.addActionListener(e -> {
            homeFrame.dispose();
            new hwframe().setVisible(true);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(logoutButton);
        homePanel.add(buttonPanel, BorderLayout.SOUTH);

        homeFrame.setContentPane(homePanel);
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            hwframe frame = new hwframe();
            frame.setVisible(true);
        });
    }
}
