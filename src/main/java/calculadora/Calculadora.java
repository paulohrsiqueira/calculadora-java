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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.util.Map;
import java.util.HashMap;




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
        visor.setEditable(false);
        visor.setFocusable(false);

        JPanel painel = new JPanel();
        
        painel.setBackground(Color.BLACK);
        painel.setLayout(new GridLayout(5, 4,8,8));
        painel.setBorder(new EmptyBorder(8, 8, 8, 8));

        String[] txtBotoes = {
            "⌫","AC", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-","0", ".","="
        };

        Map<String, BotaoCalculadora> btnMap = new HashMap<>();

        Color cinza = Color.decode("#505050");
        Color laranja = Color.decode("#FF9500");
        Color fundo = Color.decode("#1C1C1C");

        for (int i = 0; i < txtBotoes.length; i++) {

        BotaoCalculadora btn = new BotaoCalculadora(txtBotoes[i]);

        btnMap.put(txtBotoes[i], btn);

        btn.setFont(new Font("Arial", Font.PLAIN, 25));
        btn.setCorOriginal(fundo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
            

            if (txtBotoes[i].equals("⌫")) {
                btn.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 25));
                btn.setCorOriginal(cinza);
            }
            else if (txtBotoes[i].equals("AC")||txtBotoes[i].equals("%")){
                btn.setCorOriginal(cinza);
            }
            else if (txtBotoes[i].equals("+") 
                || txtBotoes[i].equals("-") 
                || txtBotoes[i].equals("×") 
                || txtBotoes[i].equals("÷") 
                || txtBotoes[i].equals("=")) {
                btn.setCorOriginal(laranja);
            }
            
            btn.addActionListener(new ActionListener() {
            
                @Override
                public void actionPerformed(ActionEvent e ){
                    JButton btnClicado = (JButton) e.getSource();
                    
                    String txtAtual = visor.getText();
                    String txtProx = btnClicado.getText();


                //Backspace
                switch (txtProx){

                case "⌫": 
                        if (txtAtual.equals("ERROR")) {
                            visor.setText("");
                        } 
                        else if (txtAtual.length() > 0) {
                            visor.setText(txtAtual.substring(0, txtAtual.length() - 1));
                        }
                    
                break;

                //Limpa
                case "AC":
                    
                        visor.setText("");
                        numero1 = 0;
                        numero2 = 0;
                        operacao = null;
                    
                    break;


                //Verifica o operador
                case "+":
                case "-":
                case "×":
                case "÷":
                    if (txtAtual.equals("ERROR")) {
                        visor.setText("");
                        break;
                    }   
                    if (txtAtual.isEmpty()) {
                        if(txtProx.equals("-")){
                            visor.setText("-");}
                    } else if(txtAtual.equals("-")) {
                        break; 
                    } else { 

                        if(txtAtual.equals(".")){
                        txtAtual = "0.";}
                        
                        numero1 = Double.parseDouble(txtAtual);
                        operacao = txtProx;
                        visor.setText("");
                    }
                break;

                case "%":

                    if (txtAtual.isEmpty() || txtAtual.equals("ERROR")) {
                        visor.setText("");
                        break;
                    }

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

                    txtAtual = visor.getText();

                    if(txtAtual.equals("ERROR")){
                        break;
                    } else if (txtAtual.isEmpty()) {
                        visor.setText("-");
                    } else if (txtAtual.equals("-")) {
                        visor.setText("");
                    } else if (txtAtual.equals(".")) {
                        visor.setText("-.");
                    } else if (txtAtual.equals("-.")) {
                        visor.setText(".");
                    } else {
                        double numero = Double.parseDouble(txtAtual);
                        numero = numero * -1;
                        formatarResultado(visor, numero);
                    }

                
                break;

                case "=": 
                        if(txtAtual.equals("ERROR")){
                        break;
                        }
                        if (txtAtual.isEmpty() || operacao == null || txtAtual.equals("-") || txtAtual.equals(".") || txtAtual.equals("-.")) {
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

                //Verifica o ponto 
                case ".":
                if (txtAtual.equals("ERROR")) {
                    visor.setText(txtProx);
                    break;
                } else if (!txtAtual.contains(".")) {
                    visor.setText(txtAtual + txtProx);
                } 
                break;

                default:
                    if (txtAtual.equals("ERROR")) {
                        visor.setText(txtProx);
                    } else {
                        visor.setText(txtAtual + txtProx);
                    }
                    break;
                } } });
                        
painel.add(btn);
        }

   
        janela.add(visor, BorderLayout.NORTH);
        janela.add(painel, BorderLayout.CENTER);


    //Numeros teclado e NumPad
            for (int numero = 0; numero <= 9; numero++) {

            final int numeroAtual = numero;

            adicionarAtalho(
                janela,
                btnMap,
                KeyStroke.getKeyStroke(String.valueOf(numero)),
                KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0 + numero, 0),
                "pressionarNumero" + numero,
                String.valueOf(numeroAtual)
            );
        }
    

    //Enter teclado e Numpad 
            adicionarAtalho(janela, btnMap,
                 KeyStroke.getKeyStroke("ENTER"),
                 null, 
                 "pressionarEnter", 
                 "=");
    
    //Backspace teclado
            adicionarAtalho(janela, btnMap,
                 KeyStroke.getKeyStroke("BACK_SPACE"),
                 null, 
                 "pressionarBackspace", 
                 "⌫");
    
    //Operadores teclado e Numpad
            adicionarAtalho(janela, btnMap,
                    KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, KeyEvent.SHIFT_DOWN_MASK),
                    KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0),
                    "pressionarMais",
                    "+"
                );


                adicionarAtalho(janela, btnMap,
                    KeyStroke.getKeyStroke(KeyEvent.VK_MINUS,0),
                    KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0),
                    "pressionarMenos",
                    "-"
                );


                adicionarAtalho(janela, btnMap,
                    KeyStroke.getKeyStroke(KeyEvent.VK_8, KeyEvent.SHIFT_DOWN_MASK),
                    KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, 0),
                    "pressionarMultiplicacao",
                    "×"
                );

                adicionarAtalho(janela, btnMap,
                    KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0),
                    KeyStroke.getKeyStroke(KeyEvent.VK_DIVIDE, 0),
                    "pressionarDivisao",
                    "÷"
                );

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

    static void adicionarAtalho(
        JFrame janela,
        Map<String, BotaoCalculadora> btnMap,
        KeyStroke tecla1,
        KeyStroke tecla2,
        String nomeAcao,
        String botao) {

    janela.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        .put(tecla1, nomeAcao);

    if (tecla2 != null) {
        janela.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(tecla2, nomeAcao);
    }


    janela.getRootPane().getActionMap()
        .put(nomeAcao, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnMap.get(botao).doClick();
            }
        });
    }
    
}