
package vista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GradientButton extends JButton {

    private Color colorStart = new Color(25, 118, 210);
    private Color colorEnd = new Color(21, 101, 192);
    private Color hoverStart = new Color(33, 150, 243);
    private Color hoverEnd = new Color(30, 136, 229);
    private boolean hover = false;

    public GradientButton(String text, Icon icon) {
        super(text, icon);
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
                // Opcional: efecto presionado (bajar 1 píxel)
                setLocation(getX(), getY() + 1);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Regresar a posición original
                setLocation(getX(), getY() - 1);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        // Suavizar bordes y degradado
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Crear degradado según hover o no
        GradientPaint paint;
        if (hover) {
            paint = new GradientPaint(0, 0, hoverStart, 0, height, hoverEnd);
        } else {
            paint = new GradientPaint(0, 0, colorStart, 0, height, colorEnd);
        }
        g2.setPaint(paint);

        // Dibujar fondo redondeado
        g2.fillRoundRect(0, 0, width, height, 30, 30);

        // Dibujar sombra interior sutil (opcional)
        g2.setColor(new Color(255, 255, 255, 50));
        g2.drawRoundRect(1, 1, width - 3, height - 3, 30, 30);

        g2.dispose();

        super.paintComponent(g);
    }
}
