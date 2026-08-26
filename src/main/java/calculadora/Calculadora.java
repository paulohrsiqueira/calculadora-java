package calculadora;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;

public class Calculadora {

    static double numero1;
    static double numero2;
    static String operacao;

    public static void main(String[] args) {

        JFrame janela = new JFrame();

        janela.setTitle("Calculadora");
        janela.setSize(400, 600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(new BorderLayout());

        JTextField visor = new JTextField();

        visor.setFont(new Font("Arial", Font.PLAIN, 40));
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setBackground(Color.BLACK);
        visor.setForeground(Color.WHITE);
        visor.setBorder(null);
        visor.setPreferredSize(new Dimension(0, 100));

        JPanel painel = new JPanel();
        
        painel.setBackground(Color.BLACK);
        painel.setLayout(new GridLayout(5, 4,8,8));
        painel.setBorder(new EmptyBorder(8, 8, 8, 8));

        String[] txt = {
            "⌫","AC", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-","0", ".","="
        };

        BotaoCalculadora[] btn = new BotaoCalculadora[20];

        Color cinza = Color.decode("#505050");
        Color laranja = Color.decode("#FF9500");
        Color fundo = Color.decode("#1C1C1C");

        for (int i = 0; i < 20; i++) {
            
            btn[i] = new BotaoCalculadora();
            btn[i].setText(txt[i]);

            btn[i].setFont(new Font("Arial", Font.PLAIN, 25));
            btn[i].setCorOriginal(fundo);
            btn[i].setForeground(Color.WHITE);
            btn[i].setFocusPainted(false);
            btn[i].setBorderPainted(false);
            

            if (txt[i].equals("⌫")) {
                btn[i].setFont(new Font("Segoe UI Symbol", Font.PLAIN, 25));
                btn[i].setCorOriginal(cinza);
            }
            else if (txt[i].equals("AC")||txt[i].equals("%")){
                btn[i].setCorOriginal(cinza);
            }
            else if (txt[i].equals("+") || txt[i].equals("-") || txt[i].equals("×") || txt[i].equals("÷") || txt[i].equals("=")) {
                btn[i].setCorOriginal(laranja);
            }

            btn[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e ){
                    JButton btnClicado = (JButton) e.getSource();
                    
                    String txtAtual = visor.getText();
                    String txtProx = btnClicado.getText();


                //Backspace
                switch (txtProx){
                case "⌫": 
                    {
                        if (txtAtual.length() > 0) {
                            visor.setText(txtAtual.substring(0, txtAtual.length() - 1));
                        }
                    }
                    break;

                //Limpa
                case "AC":
                    {
                        visor.setText("");
                        numero1 = 0;
                        numero2 = 0;
                        operacao = null;
                    }
                    break;


                //Verifica o operador
                case "+":
                case "-":
                case "×":
                case "÷":

                    if (txtAtual.isEmpty()) {
                        if(txtProx.equals("-")){
                            visor.setText("-");}
                    }else if(txtAtual.equals("-")) {
                        break;
                    }else {
                        numero1 = Double.parseDouble(txtAtual);
                        operacao = txtProx;
                        visor.setText("");
                    }
                break;

                case "%":
                        numero2 = Double.parseDouble(txtAtual);

                    if (operacao == null) {
                        double resultado = numero2 / 100;
                        formatarResultado(visor, resultado);
                        }
                    else { 
                        double resultado = calcPorcentagem(numero1, numero2, operacao);

                        if (Double.isNaN(resultado)) {
                            visor.setText("ERROR");
                        } else {
                            formatarResultado(visor, resultado);
                        }}
                break;

                case "+/-":
                {

                    txtAtual = visor.getText();

                    if (!txtAtual.isEmpty()) {
                        double numero = Double.parseDouble(txtAtual);
                        numero = numero * -1;
                        formatarResultado(visor, numero);
                    }

                }
                break;

                case "=": 

                        if (txtAtual.isEmpty() || operacao == null) {
                        break;
                        }

                        numero2 = Double.parseDouble(txtAtual);
                        double resultado = 0;
                        boolean erro = false;

                        // Calcula o resultado
                        switch (operacao) {
                            case "+":
                                resultado = numero1 + numero2;
                                break;

                            case "-":
                                resultado = numero1 - numero2;
                                break;

                            case "×":
                                resultado = numero1 * numero2;
                                break;

                            case "÷":
                                if (numero2 == 0) {
                                    erro = true;
                                } else {
                                    resultado = numero1 / numero2;
                                }
                                break;
                        }

                    //Formata o resultado 
                    if (erro) {
                        visor.setText("ERROR");
                        } else {
                            formatarResultado(visor, resultado);
                        }

                 
                break;

                //Verifica se o ponto já foi adicionado
                case ".":
                    if (!txtAtual.contains(".")) {
                        visor.setText(txtAtual + txtProx);
                    }
                break;

                default:
                    visor.setText(txtAtual + txtProx);
                break;
                } } }); 

painel.add(btn[i]);
        }

   
        janela.add(visor, BorderLayout.NORTH);
        janela.add(painel, BorderLayout.CENTER);

        janela.setVisible(true);
    }
    static void formatarResultado(JTextField visor, double resultado) {
        if (resultado == (int) resultado) {
            visor.setText(String.valueOf((int) resultado));
        } else {
            visor.setText(String.valueOf(resultado));
        }
    }

    static double calcPorcentagem(double numero1, double numero2, String operacao){
            
        if(operacao.equals("+")){

            double porcentagem = numero1 * numero2 / 100;
            double resultado = numero1 + porcentagem;
            return resultado;
                      
        } else if (operacao.equals("-")) {
            double porcentagem = numero1 * numero2 / 100;
            double resultado = numero1 - porcentagem;
            return resultado;

        } else if (operacao.equals("×")) {
            double resultado = numero1 * (numero2 / 100);
            return resultado; 

        } else if (operacao.equals("÷")){
            if (numero2 == 0) {
                return Double.NaN;
            } else {
                double resultado = numero1 / (numero2 / 100);
                return resultado;
                }
            
        } else {
         return Double.NaN;
        }
    }
    
}