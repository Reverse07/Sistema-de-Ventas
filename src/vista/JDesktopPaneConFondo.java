package vista;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

public class JDesktopPaneConFondo extends JDesktopPane {

    private Image imagen;

    public JDesktopPaneConFondo() {
        imagen = new ImageIcon(getClass().getResource("/img/fondo3.jpg")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
