package calculadora;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;


public class Calculadora {

    public static void main(String[] args) {

        JFrame janela = new JFrame();

        janela.setTitle("Calculadora");
        janela.setSize(400, 600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(4, 4));

        String[] txt = {
            "7", "8", "9", "÷",
            "4", "5", "6", "×",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        JButton[] btn = new JButton[16];

        for (int i = 0; i < 16; i++) {
            btn[i] = new JButton(txt[i]);
            painel.add(btn[i]);
        }

        janela.add(painel);

        janela.setVisible(true);
    }
}