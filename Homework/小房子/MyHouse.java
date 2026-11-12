import javax.swing.*;
import java.awt.*;

public class MyHouse extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. 天空與草地：畫布為 500 x 400，各占一半
        g.setColor(new Color(135, 206, 235));
        g.fillRect(0, 0, 500, 200);
        g.setColor(new Color(100, 180, 80));
        g.fillRect(0, 200, 500, 200);

        // 2. 太陽：黃色正圓，圓心 (410, 75)，半徑 30
        g.setColor(Color.YELLOW);
        g.fillOval(380, 45, 60, 60);
        // 每隔 45 度畫一條光芒，共 8 條
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45);
            int x1 = 410 + (int) Math.round(38 * Math.cos(angle));
            int y1 = 75 + (int) Math.round(38 * Math.sin(angle));
            int x2 = 410 + (int) Math.round(52 * Math.cos(angle));
            int y2 = 75 + (int) Math.round(52 * Math.sin(angle));
            g.drawLine(x1, y1, x2, y2);
        }

        // 3. 雲朵：3 個互相重疊的白色橢圓
        g.setColor(Color.WHITE);
        g.fillOval(55, 65, 70, 40);
        g.fillOval(90, 45, 80, 60);
        g.fillOval(135, 65, 70, 40);

        // 4. 房屋主體：先填色，再畫外框
        g.setColor(new Color(255, 220, 165));
        g.fillRect(140, 200, 220, 140);
        g.setColor(Color.BLACK);
        g.drawRect(140, 200, 220, 140);

        // 5. 屋頂：只用 3 條 drawLine 畫出三角形
        g.setColor(new Color(150, 60, 40));
        g.drawLine(120, 200, 250, 120);
        g.drawLine(250, 120, 380, 200);
        g.drawLine(120, 200, 380, 200);

        // 6. 圓角門與圓形門把
        g.setColor(new Color(140, 85, 45));
        g.fillRoundRect(225, 260, 50, 80, 15, 15);
        g.setColor(Color.YELLOW);
        g.fillOval(262, 300, 7, 7);

        // 7. 左右各一扇窗，包含外框與十字窗框
        for (int x = 160; x <= 290; x += 130) {
            g.setColor(new Color(190, 235, 255));
            g.fillRect(x, 225, 50, 50);
            g.setColor(Color.BLACK);
            g.drawRect(x, 225, 50, 50);
            g.drawLine(x + 25, 225, x + 25, 275);
            g.drawLine(x, 250, x + 50, 250);
        }

        // 8. 請把字串改成自己的學號與姓名
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.PLAIN, 16));
        g.drawString("學號 姓名", 15, 380);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("我的小房子");
            MyHouse panel = new MyHouse();
            panel.setPreferredSize(new Dimension(500, 400));
            frame.add(panel);
            frame.pack();
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
