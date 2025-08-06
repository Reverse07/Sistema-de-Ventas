
package vista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GradientButton extends JButton {

    private Color colorStart;
    private Color colorEnd;
    private Color hoverStart;
    private Color hoverEnd;
    private boolean hover = false;

    // === Constructor con colores por defecto (Azules) ===
    public GradientButton(String text, Icon icon) {
        this(text, icon,
                new Color(25, 118, 210), new Color(21, 101, 192)); // Azul base
    }

    // === Constructor con colores personalizados ===
    public GradientButton(String text, Icon icon, Color startColor, Color endColor) {
        super(text, icon);

        this.colorStart = startColor;
        this.colorEnd = endColor;

        // Hover más claros
        this.hoverStart = startColor.brighter();
        this.hoverEnd = endColor.brighter();

        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setIconTextGap(10);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setLocation(getX(), getY() + 1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setLocation(getX(), getY() - 1);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint paint;
        if (hover) {
            paint = new GradientPaint(0, 0, hoverStart, 0, height, hoverEnd);
        } else {
            paint = new GradientPaint(0, 0, colorStart, 0, height, colorEnd);
        }
        g2.setPaint(paint);

        g2.fillRoundRect(0, 0, width, height, 30, 30);

        g2.setColor(new Color(255, 255, 255, 50));
        g2.drawRoundRect(1, 1, width - 3, height - 3, 30, 30);

        g2.dispose();

        super.paintComponent(g);
    }
}
