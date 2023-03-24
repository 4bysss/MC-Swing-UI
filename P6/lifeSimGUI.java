import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;

import java.awt.event.FocusListener;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
/**
 * lifeSimGUI
 */
public class lifeSimGUI {
    private int ancho,alto,anchoCel,altoCel;
    private int[][] M1,M2;
    private JFrame frame;
    private boolean flag;
    private SpringLayout layout;
    private JTextField TFGeneraciones;
    private JComboBox<String>CBModoInicial;
    private JButton Generar,Reset,GenerarMas,GenerarIlimitado,Detener;
    private JLabel contenedorVida, contenedorGrafica;
    private BufferedImage imagenVida,imagenGrafica;
    private Graphics gVida,gGrafica;
    
    lifeSimGUI(){

        //Inicializamos todo los elementos
        ancho = alto = 600;
        anchoCel = altoCel = 6;
        M1 = new int[100][100];
        M2 = new int[100][100];
        String [] modos = {"Aleatorio", "Islas","Dispara!!!!"};
        frame = new JFrame("Game of Life");
        layout = new SpringLayout();
        TFGeneraciones = new JTextField("Generaciones");
        TFGeneraciones.setPreferredSize(new Dimension(100,30));
        CBModoInicial = new JComboBox<>(modos);
        Generar = new JButton("Generar");//TODO Añador action listener
        Reset = new JButton("Reset");
        GenerarMas = new JButton("Generar más");
        GenerarIlimitado = new JButton("Generar Ilimitado");
        Detener = new JButton("Detener");
        imagenVida = new BufferedImage(ancho, alto,BufferedImage.TYPE_INT_BGR);
        imagenGrafica = new BufferedImage(ancho, alto/3,BufferedImage.TYPE_INT_BGR);
        gVida = imagenVida.getGraphics();
        gGrafica = imagenGrafica.getGraphics();
        contenedorVida = new JLabel(new ImageIcon(imagenVida));
        contenedorGrafica = new JLabel(new ImageIcon(imagenGrafica));
        gVida.setColor(new Color(0,0,0));
        gVida.fillRect(0, 0, ancho, alto);
        
        gGrafica.setColor(new Color(0,0,0));
        
        gGrafica.fillRect(0, 0, ancho, alto/3);
        

        
        
        //Configuramos frame
        frame.setLayout(layout);
        frame.setSize(1024, 1024);
        frame.setResizable(true);
        frame.setVisible(true);

        //Añadimos los action listener
        TFGeneraciones.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent arg0) {
                if(TFGeneraciones.getText().equals("")){
                    TFGeneraciones.setText("Generaciones");
                }
            }
            @Override
            public void focusGained(FocusEvent arg0) {
                if(TFGeneraciones.getText().equals("Generaciones")){
                    TFGeneraciones.setText("");
                }
            }
        });

        Generar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int nGen = Integer.parseInt(TFGeneraciones.getText());
                String SelectedMode = (String)CBModoInicial.getSelectedItem();
                switch (SelectedMode) {
                    case "Aleatorio":
                        
                        break;
                    case "Islas":
                        break;

                    case"Dispara!!!":
                        break;

                }
                SwingWorker w = new SwingWorker<>() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        for (int i = 0; i < nGen; i++) {
                            //TODO llamar a generar
                        }
                        return null;
                    }
                };
                w.execute();


            }
        });

        GenerarMas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SwingWorker w = new SwingWorker<>() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        int nGen = Integer.parseInt(TFGeneraciones.getText());
                        for (int i = 0; i < nGen; i++) {
                            //TODO llamar a generar
                        }
                        return null;
                    }
                };
                w.execute();
            }
        });

        GenerarIlimitado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SwingWorker w = new SwingWorker<>() {
                    protected Object doInBackground() throws Exception {
                        while(flag){
                            //TODO llamar a generar
                        }
                        return null;
                    }
                };
                w.execute();
            }
        });

        Detener.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingWorker w = new SwingWorker<>() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        flag=false;
                        return null;
                    }        
                };
                w.execute();
            }
        });

        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SwingWorker w = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        flag = false;
                        gVida.clearRect(0, 0, ancho, alto);
                        gGrafica.clearRect(0, 0, ancho, alto/3);
                        contenedorVida.repaint();
                        contenedorGrafica.repaint();
                        return null;
                    }
                };
                w.execute();
            }
        });

        //Incluimos los elementos al frame;
        frame.add(TFGeneraciones);
        frame.add(CBModoInicial);
        frame.add(Generar);
        frame.add(GenerarMas);
        frame.add(GenerarIlimitado);
        frame.add(Reset);
        frame.add(Detener);
        frame.add(contenedorVida);
        frame.add(contenedorGrafica);

        //Colocamos los elementos en el frame
        layout.putConstraint(SpringLayout.WEST, contenedorVida, 400, SpringLayout.EAST, frame);
        layout.putConstraint(SpringLayout.NORTH, contenedorVida, 40, SpringLayout.NORTH, frame);

        layout.putConstraint(SpringLayout.WEST, contenedorGrafica, 0, SpringLayout.WEST, contenedorVida);
        layout.putConstraint(SpringLayout.NORTH, contenedorGrafica, 40, SpringLayout.SOUTH, contenedorVida);

        layout.putConstraint(SpringLayout.WEST, TFGeneraciones, 40, SpringLayout.EAST, contenedorVida);
        layout.putConstraint(SpringLayout.NORTH, TFGeneraciones, 150, SpringLayout.NORTH, contenedorVida);

        layout.putConstraint(SpringLayout.WEST, Generar, 0, SpringLayout.WEST, TFGeneraciones);
        layout.putConstraint(SpringLayout.NORTH, Generar, 30, SpringLayout.SOUTH, TFGeneraciones);
        
        layout.putConstraint(SpringLayout.WEST, GenerarMas, 0, SpringLayout.WEST, Generar);
        layout.putConstraint(SpringLayout.NORTH, GenerarMas, 30, SpringLayout.SOUTH, Generar);

        layout.putConstraint(SpringLayout.WEST, GenerarIlimitado, 0, SpringLayout.WEST, GenerarMas);
        layout.putConstraint(SpringLayout.NORTH, GenerarIlimitado, 30, SpringLayout.SOUTH, GenerarMas);

        layout.putConstraint(SpringLayout.WEST, Reset, 0, SpringLayout.WEST, GenerarIlimitado);
        layout.putConstraint(SpringLayout.NORTH, Reset, 30, SpringLayout.SOUTH, GenerarIlimitado);
        
        layout.putConstraint(SpringLayout.WEST, Detener, 0, SpringLayout.WEST, Reset);
        layout.putConstraint(SpringLayout.NORTH, Detener, 30, SpringLayout.SOUTH, Reset);

        layout.putConstraint(SpringLayout.WEST, CBModoInicial, 0, SpringLayout.WEST, Detener);
        layout.putConstraint(SpringLayout.NORTH, CBModoInicial, 30, SpringLayout.SOUTH, Detener);
        

        //Llenamos los elemntos graficos con negro


        

        




    }

    public int[][] GeneraAleatorio(int[][]M1){
        long aux = 7;
        for (int i = 0; i < M1.length; i++) {
            for (int j = 0; j < M1.length; j++) {
                aux = randomGenerator.Randu(aux);
                M1[i][j] = (int)aux%2;

            }
        }
        return M1;
    }

    public int[][] nucleoCompu(int[][] M1, int[][] M2){
        int suma = 0;
        for (int i = 0; i < M1.length; i++) {
            for (int j = 0; j < M1.length; j++) {
                if(i == 0){
                    suma += M1[i][j] + M1[i+1][j];
                }
                if(i == M1.length-1){
                    suma += M1[i][j] + M1[i-1][j];
                }
                if(j == 0){
                    suma += M1[i][j] + M1[i][j+1];
                }
                if(j == M1.length-1){
                    suma += M1[i][j] + M1[i][j-1];
                }
                if(i != 0 && i != M1.length-1 && j != 0 && j != M1.length-1){
                    suma += M1[i][j] + M1[i-1][j] + M1[i+1][j] + M1[i][j-1] + M1[i][j+1];
                }
                int estadoCel = M1[i][j];
                estadoCel = estadoCel * (suma>=2 ? 1:0);
                //estadoCel = estadoCel * ((suma==2 || suma==3) ? 1:0);
                estadoCel = estadoCel * (suma<=3 ? 1:0);
                estadoCel = estadoCel + (estadoCel==0 ? 1:0) * (suma==3 ? 1:0);

            }
        }
        return M1;
    }
    //TODO Crear funcion Pinta
    //TODO Generador de islas
    //TODO Generador de cosas que disparan
    //TODO Crear nuclero cumputacional del juego de la vida
}
