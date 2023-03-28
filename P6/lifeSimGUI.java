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
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
/**
 * lifeSimGUI
 */
public class lifeSimGUI {
    private int ancho,alto,anchoCel,altoCel,nGen;
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
        nGen = 0;
        M1 = new int[100][100];
        M2 = new int[100][100];
        String [] modos = {"Aleatorio", "Islas","Dispara!!!"};
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
                flag = true;
                nGen = Integer.parseInt(TFGeneraciones.getText());
                String SelectedMode = (String)CBModoInicial.getSelectedItem();
                switch (SelectedMode) {
                    case "Aleatorio":
                        GeneraAleatorio(M1,nGen);
                        break;
                    case "Islas":
                        break;

                    case"Dispara!!!":
                        generaGlider(M1);
                        break;

                }
                SwingWorker w = new SwingWorker<>() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        int old = cuentaVivos(M1);
                        int nuevo = old;
                        for (int i = 0; i < nGen&&flag==true; i++) {

                            pinta(M1, nuevo/20, old/20, i, gVida, gGrafica, imagenGrafica, contenedorVida, contenedorGrafica);
                            M1 = nucleoCompu(M1, M2);
                            old = nuevo;
                            nuevo = cuentaVivos(M1);
                            TimeUnit.MILLISECONDS.sleep(10);
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
                        int add = Integer.parseInt(TFGeneraciones.getText());
                        int old = cuentaVivos(M1);
                        int nuevo = old;
                        for (int i = nGen; i < nGen+add&&flag==true; i++) {

                            pinta(M1, nuevo/20, old/20, i, gVida, gGrafica, imagenGrafica, contenedorVida, contenedorGrafica);
                            M1 = nucleoCompu(M1, M2);
                            old = nuevo;
                            nuevo = cuentaVivos(M1);
                            TimeUnit.MILLISECONDS.sleep(10);
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
                switch (SelectedMode) {
                    case "Aleatorio":
                        GeneraAleatorio(M1,nGen);
                        break;
                    case "Islas":
                        break;

                    case"Dispara!!!":
                        generaGlider(M1);
                        break;

                }
                SwingWorker w = new SwingWorker<>() {
                    protected Object doInBackground() throws Exception {
                        int add = Integer.parseInt(TFGeneraciones.getText());
                        int old = cuentaVivos(M1);
                        int nuevo = old;
                        for (int i = nGen;flag==true; i++) {

                            pinta(M1, nuevo/20, old/20, i, gVida, gGrafica, imagenGrafica, contenedorVida, contenedorGrafica);
                            M1 = nucleoCompu(M1, M2);
                            old = nuevo;
                            nuevo = cuentaVivos(M1);
                            TimeUnit.MILLISECONDS.sleep(10);
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
                        for (int i = 0; i < M1.length; i++) {
                            for (int j = 0; j < M1.length; j++) {
                                M1[i][j] = 0;
                            }
                        }
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

    public int[][] GeneraAleatorio(int[][]M1,int seed){
        long aux = seed;
        for (int i = 0; i < M1.length; i++) {
            for (int j = 0; j < M1.length; j++) {
                aux = randomGenerator.Randu(aux);
                M1[i][j] = (int)aux%2;

            }
        }
        return M1;
    }

    public int[][] nucleoCompu(int[][] M1, int[][] M2)throws Exception{
        int suma = 0;
        int estadoCel = 0;
        for (int i = 0; i < M1.length; i++) {
            for (int j = 0; j < M1.length; j++) {
                if(i==0){
                    if(j==0){suma += M1[i+1][j] + M1[i][j+1] + M1[i+1][j+1];}
                    else if(j==M1.length-1){suma += M1[i+1][j] + M1[i][j-1] + M1[i+1][j-1];}
                    else{suma += M1[i+1][j] + M1[i][j+1] + M1[i+1][j+1] + M1[i+1][j-1] + M1[i][j-1];}
                }
                else if(i==M1.length-1){
                    if(j==0){suma += M1[i-1][j] + M1[i][j+1] + M1[i-1][j+1];}
                    else if(j==M1.length-1){suma += M1[i-1][j] + M1[i][j-1] + M1[i-1][j-1];}
                    else{suma += M1[i-1][j] + M1[i][j+1] + M1[i-1][j+1] + M1[i-1][j-1] + M1[i][j-1];}
                    
                }
                else if(i!=0 && i!=M1.length-1){
                    if(j==0){suma += M1[i-1][j] + M1[i+1][j] + M1[i][j+1] + M1[i-1][j+1] + M1[i+1][j+1];}
                    else if(j==M1.length-1){suma += M1[i-1][j] + M1[i+1][j] + M1[i][j-1] + M1[i-1][j-1] + M1[i+1][j-1];}
                    else{suma += M1[i-1][j] + M1[i+1][j] + M1[i][j+1] + M1[i][j-1] + M1[i-1][j+1] + M1[i+1][j+1] + M1[i-1][j-1] + M1[i+1][j-1];}

                }
                estadoCel = M1[i][j];
                if(estadoCel==1 && suma>=2 && suma<=3){M2[i][j]=1;}
                else if(estadoCel==0 && suma==3){M2[i][j]=1;}
                else{M2[i][j]=0;}
                //estadoCel = estadoCel * ((suma==2 || suma==3) ? 1:0);
                suma = 0;

            }
        }
        for (int i = 0; i < M1.length; i++) {
            for (int j = 0; j < M1.length; j++) {
                M1[i][j] = M2[i][j];
            }
        }
        return M1;
    }
    private void pinta(int[][]M1,int numVivos,int numVivosAntes,int generacionActual,Graphics gVida,Graphics Ggrafica,BufferedImage imagenGrafica, JLabel contenedorVida, JLabel contenedorGrafica){
        BufferedImage aux = new BufferedImage(ancho, alto/3, BufferedImage.TYPE_INT_BGR);
        Ggrafica = aux.getGraphics();
        Ggrafica.setColor(Color.RED);
        Ggrafica.drawImage(imagenGrafica, -1, 0, null);
        for (int i = 0; i < M1.length; i++) {
            for (int j = 0; j < M1.length; j++) {
                if(M1[i][j]!=0){
                    gVida.setColor(Color.BLUE);
                    gVida.fillRect(i*anchoCel, j*altoCel, i*(anchoCel+1), j*(altoCel+1));
                }
                else{

                    gVida.setColor(Color.BLACK);
                    gVida.fillRect(i*anchoCel, j*altoCel, i*(anchoCel+1), j*(altoCel+1));
                }
            }
        }
        
        Ggrafica.drawLine(ancho-1,200 - numVivosAntes, ancho-1, 200 - numVivos); 
        gGrafica = imagenGrafica.getGraphics();
        gGrafica.drawImage(aux, 0, 0, null);
        contenedorVida.repaint();
        contenedorGrafica.repaint();
    };


    private int cuentaVivos(int[][]M1){
        int vivos = 0;
        for (int i = 0; i < M1.length; i++) {
            for (int j = 0; j < M1.length; j++) {
                if(M1[i][j] != 0){
                    vivos++;
                }
            }
        }
        return vivos;
    }
    //TODO Generador de islas
    //TODO Generador de cosas que disparan
    private int[][] generaGlider(int[][] M1){
        int[][]gliderTemplate = {{0,1,0},{0,0,1},{1,1,1}};
        for (int i = 0; i < M1.length; i+=5) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0 ; k < 3; k++) {
                   M1[i+k][j] =gliderTemplate[j][k]; 
                }
            }
        }
        return M1;
    } 
    //TODO Crear nuclero cumputacional del juego de la vida
}
