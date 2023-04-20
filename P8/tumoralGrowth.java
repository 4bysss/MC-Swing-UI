
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;

import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
/**
 * lifeSimGUI
 */
public class tumoralGrowth {
    private int ancho,alto,anchoCel,altoCel,nGen,p,q,NH;
    private float[] probabilidades;
    private int old,nuevo;
    private int[][] PH;
    private boolean[][][] estadoTumor;
    private JFrame frame;
    private boolean flag;
    private SpringLayout layout;
    private JTextField TFGeneraciones,Ps_TF,Pp_TF,Np_TF,Pm_TF;
    private JComboBox<String>CBModoInicial;
    private JButton Generar,Reset,GenerarMas,GenerarIlimitado,Detener,ConfigInicial;
    private JLabel contenedorVida, contenedorGrafica;
    private BufferedImage imagenVida,imagenGrafica;
    private Graphics gVida,gGrafica;
    
    tumoralGrowth(){

        //Inicializamos todo los elementos
        p = 0; q = 1;
        ancho = alto = 600;
        anchoCel = altoCel = 6;
        nGen = 0;
        estadoTumor = new boolean[100][100][2];
        String [] modos = {"Default","Personalizado"};
        frame = new JFrame("Crecimiento Tumoral");
        layout = new SpringLayout();
        TFGeneraciones = new JTextField("Generaciones");
        Pp_TF = new JTextField("PP");
        Ps_TF = new JTextField("PS");
        Pm_TF = new JTextField("PM");
        Np_TF = new JTextField("NP");
    
        TFGeneraciones.setPreferredSize(new Dimension(100,30));
        Pp_TF.setPreferredSize(new Dimension(30,30));
        Ps_TF.setPreferredSize(new Dimension(30,30));
        Pm_TF.setPreferredSize(new Dimension(30,30));
        Np_TF.setPreferredSize(new Dimension(30,30));
        CBModoInicial = new JComboBox<>(modos);
        Generar = new JButton("Generar");//TODO Añador action listener
        Reset = new JButton("Reset");
        GenerarMas = new JButton("Generar más");
        GenerarIlimitado = new JButton("Generar Ilimitado");
        Detener = new JButton("Detener");
        ConfigInicial = new JButton("Personalizar");
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
        //TODO Añadir funciones 
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
                flag = true;
                nGen = Integer.parseInt(TFGeneraciones.getText());
                GeneraAleatorio(estadoTumor);
                SwingWorker w = new SwingWorker<>() {
                    @Override

                    protected Object doInBackground() throws Exception {
                        
                        old = nuevo = cuentaVivos(estadoTumor);
                        normaliza(old,45);


                        for (int i = 0; i < nGen&&flag==true; i++) {

                            pinta(estadoTumor , nuevo, old, i, gVida, gGrafica, imagenGrafica, contenedorVida, contenedorGrafica);
                            nucleoCompu(estadoTumor);
                            old = nuevo;
                            nuevo = cuentaVivos(estadoTumor);
                            normaliza(nuevo,45);
                            if(p == 0){ p = 1; q = 0;}
                            else{       p = 0; q = 1;}
                            TimeUnit.MILLISECONDS.sleep(25);
                        }
                        return null;
                    }
                };
                w.execute();


            }
        });/*
        //TODO Adaptar todos los botones.
        GenerarMas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingWorker w = new SwingWorker<>() {
                    protected Object doInBackground() throws Exception {
                        int add = Integer.parseInt(TFGeneraciones.getText());
                        cuentaVivos(a,b,c,old);
                        normaliza(old,45);

                        nuevo = old.clone();
                        for (int i = nGen; i < nGen+add&&flag==true; i++) {

                            pinta(a, b ,c , nuevo, old, i, gVida, gGrafica, imagenGrafica, contenedorVida, contenedorGrafica);
                            nucleoCompu(a,b,c);
                            old = nuevo.clone();
                            cuentaVivos(a,b,c,nuevo);
                            normaliza(nuevo,45);
                            if(p == 0){ p = 1; q = 0;}
                            else{       p = 0; q = 1;}
                            TimeUnit.MILLISECONDS.sleep(25);
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
                flag = true;
                nGen = Integer.parseInt(TFGeneraciones.getText());
                String SelectedMode = (String)CBModoInicial.getSelectedItem();
                GeneraAleatorio(a,b,c);

                SwingWorker w = new SwingWorker<>() {
                    protected Object doInBackground() throws Exception {
                        int add = Integer.parseInt(TFGeneraciones.getText());
                        cuentaVivos(a,b,c,old);
                        normaliza(old,45);

                        nuevo = old.clone();
                        for (int i = nGen;flag==true; i++) {


                            pinta(a, b ,c , nuevo, old, i, gVida, gGrafica, imagenGrafica, contenedorVida, contenedorGrafica);
                            nucleoCompu(a,b,c);
                            old = nuevo.clone();
                            cuentaVivos(a,b,c,nuevo);
                            normaliza(nuevo,45);
                            if(p == 0){ p = 1; q = 0;}
                            else{       p = 0; q = 1;}
                            TimeUnit.MILLISECONDS.sleep(25);

                        }
                        return null;
                    }
                };
                w.execute();
            }
        });*/

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
        //TODO Implementar funcion de reset
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

        ConfigInicial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ventanaConfig();
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
        frame.add(ConfigInicial);
        frame.add(Pp_TF);
        frame.add(Ps_TF);
        frame.add(Pm_TF);
        frame.add(Np_TF);

        //Colocamos los elementos en el frame
        layout.putConstraint(SpringLayout.WEST, contenedorVida, 400, SpringLayout.EAST, frame);
        layout.putConstraint(SpringLayout.NORTH, contenedorVida, 40, SpringLayout.NORTH, frame);

        layout.putConstraint(SpringLayout.WEST, contenedorGrafica, 0, SpringLayout.WEST, contenedorVida);
        layout.putConstraint(SpringLayout.NORTH, contenedorGrafica, 40, SpringLayout.SOUTH, contenedorVida);

        layout.putConstraint(SpringLayout.WEST, TFGeneraciones, 40, SpringLayout.EAST, contenedorVida);
        layout.putConstraint(SpringLayout.NORTH, TFGeneraciones, 150, SpringLayout.NORTH, contenedorVida);

        layout.putConstraint(SpringLayout.WEST, Pm_TF, 20, SpringLayout.EAST, TFGeneraciones);
        layout.putConstraint(SpringLayout.NORTH, Pm_TF, 0, SpringLayout.NORTH, TFGeneraciones);

        layout.putConstraint(SpringLayout.WEST, Pp_TF, 60, SpringLayout.EAST, TFGeneraciones);
        layout.putConstraint(SpringLayout.NORTH, Pp_TF, 0, SpringLayout.NORTH, TFGeneraciones);

        layout.putConstraint(SpringLayout.WEST, Np_TF, 100, SpringLayout.EAST, TFGeneraciones);
        layout.putConstraint(SpringLayout.NORTH, Np_TF, 0, SpringLayout.NORTH, TFGeneraciones);

        layout.putConstraint(SpringLayout.WEST, Ps_TF, 140, SpringLayout.EAST, TFGeneraciones);
        layout.putConstraint(SpringLayout.NORTH, Ps_TF, 0, SpringLayout.NORTH, TFGeneraciones);

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
        
        layout.putConstraint(SpringLayout.WEST, ConfigInicial, 20, SpringLayout.EAST, CBModoInicial);
        layout.putConstraint(SpringLayout.NORTH, ConfigInicial, 0, SpringLayout.NORTH, CBModoInicial);

        

        //Llenamos los elemntos graficos con negro


        

        




    }
    //F:wq
    //TODO Modelas para configuracion inicial del tumor
    public void GeneraAleatorio(boolean[][][] estadoTumor){
        for (int i = 0; i < estadoTumor.length; i++) {
            for (int j = 0; j < estadoTumor.length; j++) {

                

            }
        }
    }
    //TODO Nucleo cumputacional tumores
    public void nucleoCompu(boolean [][][] estadoTumor)throws Exception{

    }

    //TODO Pintar basando en 0 o 1
    private void pinta(boolean[][][]estadoTumor,int nuevo, int old, int generacionActual, Graphics gVida, Graphics Ggrafica, BufferedImage imagenGrafica, JLabel contenedorVida, JLabel contenedorGrafica){
        /*BufferedImage aux = new BufferedImage(ancho, alto/3, BufferedImage.TYPE_INT_BGR);
        Ggrafica = aux.getGraphics();
        Ggrafica.drawImage(imagenGrafica, -1, 0, null);
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                    
                gVida.setColor(new Color(a[i][j][q],(float)0.086,(float)0.706));
                gVida.fillRect(i*anchoCel, j*altoCel, i*(anchoCel+1), j*(altoCel+1));
            }
        }
        Ggrafica.setColor(Color.RED);
        Ggrafica.drawLine(ancho-1,200 - (int)concentracionActual[0], ancho-1, 200 - (int)concentracionAntes[0]);//Concentracion de A 
        Ggrafica.setColor(Color.BLUE);
        Ggrafica.drawLine(ancho-1,200 - (int)concentracionActual[1], ancho-1, 200 - (int)concentracionAntes[1]);//Concentracion de B 
        Ggrafica.setColor(Color.GREEN);
        Ggrafica.drawLine(ancho-1,200 - (int)concentracionActual[2], ancho-1, 200 - (int)concentracionAntes[2]);//Concentracion de C 
        gGrafica = imagenGrafica.getGraphics();
        gGrafica.drawImage(aux, 0, 0, null);
        contenedorVida.repaint();
        contenedorGrafica.repaint();*/
    };


    private int cuentaVivos(boolean [][][] estadoTumor){
        int vivos = 0;
        for(int i = 0; i < estadoTumor.length; i++) {
            for (int j = 0; j < estadoTumor.length; j++) {
                vivos += estadoTumor[i][j][q] ? 1 : 0;
            }
        }
        return vivos;
    }

    private int normaliza(int vivos,int norm){
        return vivos/norm;
    }
    //TODO Calculardora de probabilidades
    private void calculaProbabilidad(float[]probabilidades){}


    private void ventanaConfig(){
        JFrame frameaux = new JFrame("Ventana generica");
        JPanel panel = new JPanel(new GridLayout(100,100));
        panel.setPreferredSize(new Dimension(3000,3000));
        frameaux.setResizable(true);
        JToggleButton[][] botones = new JToggleButton[100][100];
        for (int i = 0; i < 100; i++) {
           for (int j = 0; j < 100; j++) {
               botones[i][j] = new JToggleButton();
               botones[i][j].setPreferredSize(new Dimension(30,30)); 
               panel.add(botones[i][j]);
           } 
        }
        frameaux.getContentPane().add(panel);
        frameaux.pack();
        frameaux.setLocationRelativeTo(null);
        frameaux.setVisible(true);
        frame.addWindowListener(new WindowAdapter(){
             @Override
             public void windowClosing(WindowEvent arg0) {

                     
             }
        });

    }
}
