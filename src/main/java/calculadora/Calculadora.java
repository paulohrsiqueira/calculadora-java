package calculadora;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.BorderLayout;

public class Calculadora {

    public static void main(String[] args) {

        JFrame janela = new JFrame();

        janela.setTitle("Calculadora");
        janela.setSize(400, 600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        JTextField visor = new JTextField();

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

            btn[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e ){
                    JButton btnClicado = (JButton) e.getSource();
                    
                    String txtAtual = visor.getText();
                    String txtProx = btnClicado.getText();

                    visor.setText(txtAtual + txtProx);
                }
            });


            painel.add(btn[i]);
        }

   
        janela.add(visor, BorderLayout.NORTH);
        janela.add(painel, BorderLayout.CENTER);

        janela.setVisible(true);
    }
}