import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class exercise1{

    // 累計擲骰子的次數與總和
    private int count = 0;
    private int total = 0;

    private final Random random = new Random();

    // 建立視窗與元件
    private final JFrame frame = new JFrame("骰子模擬器");

    private final JLabel infoLabel =
            new JLabel("已擲 0 次，總和 0，平均 0.00",
                       SwingConstants.CENTER);

    private final JLabel diceLabel =
            new JLabel("-", SwingConstants.CENTER);

    private final JButton rollButton = new JButton("擲骰子");

    public exercise1() {

        // 設定視窗
        frame.setSize(400, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 中央點數的字體：60 pt
        diceLabel.setFont(new Font("SansSerif", Font.PLAIN, 60));
        diceLabel.setForeground(Color.BLACK);

        // 下方按鈕放在面板內
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(rollButton);

        // 將元件加入視窗
        frame.add(infoLabel, BorderLayout.NORTH);
        frame.add(diceLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // 按下按鈕時，執行擲骰子
        rollButton.addActionListener(e -> rollDice());

        // 開啟時置中
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void rollDice() {

        // nextInt(6) 產生 0～5，加 1 後變成 1～6
        int point = random.nextInt(6) + 1;

        // 更新次數與總和
        count++;
        total += point;

        // 轉成 double，避免整數除法
        double average = (double) total / count;

        // 更新中央點數
        diceLabel.setText(String.valueOf(point));

        // 根據點數設定文字顏色
        if (point == 6) {
            diceLabel.setForeground(Color.GREEN);
        } else if (point == 1) {
            diceLabel.setForeground(Color.RED);
        } else {
            diceLabel.setForeground(Color.BLACK);
        }

        // 更新上方統計，平均顯示小數點後兩位
        infoLabel.setText(
            String.format(
                java.util.Locale.US,
                "已擲 %d 次，總和 %d，平均 %.2f",
                count, total, average
            )
        );
    }

    public static void main(String[] args) {

        // 在 Swing 的事件處理執行緒中建立視窗
        SwingUtilities.invokeLater(() -> new exercise1());
    }
}
