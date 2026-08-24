package calculadora;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class BotaoCalculadora extends JButton {

    private Color corOriginal;

    public BotaoCalculadora() {
        
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(corOriginal.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(corOriginal);
            }
            });
    }
    
    public void setCorOriginal(Color cor) {
        corOriginal = cor;
        setBackground(cor);
    }



   @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(getBackground());

        g2.fillOval(0, 0, getWidth(), getHeight());

        String texto = getText();

        if (texto != null && !texto.isEmpty()) {
            g2.setColor(getForeground());
            g2.setFont(getFont());

            java.awt.FontMetrics fm = g2.getFontMetrics();

            int x = (getWidth() - fm.stringWidth(texto)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

            g2.drawString(texto, x, y);
        }
}
}