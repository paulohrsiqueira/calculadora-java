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

    static double numero1;
    static double numero2;
    static String operacao;

    public static void main(String[] args) {

        JFrame janela = new JFrame();

        janela.setTitle("Calculadora");
        janela.setSize(400, 600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        JTextField visor = new JTextField();

        JPanel painel = new JPanel();
        
        
        painel.setLayout(new GridLayout(5, 4));

        String[] txt = {
            "⌫","AC", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-","0", ".","="
        };

        JButton[] btn = new JButton[20];

        for (int i = 0; i < 20; i++) {
            btn[i] = new JButton(txt[i]);

            btn[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e ){
                    JButton btnClicado = (JButton) e.getSource();
                    
                    String txtAtual = visor.getText();
                    String txtProx = btnClicado.getText();


                //Backspace
                if (txtProx.equals("⌫")) {
                        if (txtAtual.length() > 0) {
                            visor.setText(txtAtual.substring(0, txtAtual.length() - 1));
                        }
                    }


                //Limpa
                else if (txtProx.equals("AC")) {

                    visor.setText("");
                    numero1 = 0;
                    numero2 = 0;
                    operacao = null;
                }

                else if (txtAtual.isEmpty() && (txtProx.equals("-"))){
                    visor.setText("-");
                }

                    
                //Verifica o operador
                else if(txtProx.equals("+") 
                        || txtProx.equals("-") 
                        || txtProx.equals("×") 
                        || txtProx.equals("÷")){
                        
                        numero1 = Double.parseDouble(txtAtual);
                        operacao = txtProx;
                        visor.setText("");
                        }
                
                else if(txtProx.equals("%")){
                   
                   numero2 = Double.parseDouble(txtAtual);
                   
                    
                    if (operacao == null) {
                        double resultado = numero2 / 100;
                        formatarResultado(visor, resultado);
                    } else {
                       double porcentagem = numero1 * numero2 / 100;
                    
                      if(operacao.equals("+")){
                      double resultado = numero1 + porcentagem;
                      formatarResultado(visor, resultado);
                    } else if (operacao.equals("-")) {
                        double resultado = numero1 - porcentagem;
                        formatarResultado(visor, resultado);
                    } else if (operacao.equals("×")) {
                        double resultado = numero1 * (numero2 / 100);
                        formatarResultado(visor, resultado);
                    } else if (operacao.equals("÷")){
                        if (numero2 == 0) {
                                visor.setText("ERROR");
                            } else {
                                double resultado = numero1 / (numero2 / 100);
                                formatarResultado(visor, resultado);
                            }
                        }
                    
                }
                }
                else if(txtProx.equals("+/-")){

                    txtAtual = visor.getText();

                    if (!txtAtual.isEmpty()) {
                        double numero = Double.parseDouble(txtAtual);
                        numero = numero * -1;
                        formatarResultado(visor, numero);
                    }

                }
                else if (txtProx.equals("=")) {

                        numero2 = Double.parseDouble(txtAtual);
                        double resultado = 0;
                        boolean erro = false;


                    //Calcula o resultado
                if (operacao.equals("+")) {
                            resultado = numero1 + numero2;
                        } else if (operacao.equals("-")) {
                            resultado = numero1 - numero2;
                        } else if (operacao.equals("×")) {
                            resultado = numero1 * numero2;
                        } else if (operacao.equals("÷")) {
                            if(numero2 == 0){
                                erro = true;
                            }else{
                            resultado = numero1 / numero2;}
                        }

                    //Formata o resultado
                    if (erro) {
                        visor.setText("ERROR");
                        } else {
                            formatarResultado(visor, resultado);
                        }

                    } 
                    //Verifica se o ponto já foi adicionado
                else if(txtProx.equals(".")){

                        if(!txtAtual.contains(".")){
                            visor.setText(txtAtual+txtProx);
                        }
                    }

                else{

                    visor.setText(txtAtual + txtProx);
                }}
            });


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
}