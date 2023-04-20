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
public class belZab {
    private int ancho,alto,anchoCel,altoCel,nGen,p,q;
    private float[] old,nuevo;
    private float[][][] a,b,c;
    private JFrame frame;
    private boolean flag;
    private SpringLayout layout;
    private JTextField TFGeneraciones;
    private JComboBox<String>CBModoInicial;
    private JButton Generar,Reset,GenerarMas,GenerarIlimitado,Detener;
    private JLabel contenedorVida, contenedorGrafica;
    private BufferedImage imagenVida,imagenGrafica;
    private Graphics gVida,gGrafica;
    
    belZab(){

        //Inicializamos todo los elementos
        p = 0; q = 1;
        ancho = alto = 600;
        anchoCel = altoCel = 6;
        nGen = 0;
        a = new float[100][100][2];
        b = new float[100][100][2];
        c = new float[100][100][2];
        String [] modos = {"Aleatorio"};
        frame = new JFrame("belZub");
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
        old = new float[3];
        nuevo = new float[3];
        
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
                GeneraAleatorio(a,b,c);
                SwingWorker w = new SwingWorker<>() {
                    @Override

                    protected Object doInBackground() throws Exception {
                        cuentaVivos(a,b,c,old);
                        normaliza(old,45);

                        nuevo = old.clone();

                        for (int i = 0; i < nGen&&flag==true; i++) {

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
        //TODO Adaptar todos los botones.
        GenerarMas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SwingWorker w = new SwingWorker<>() {
                    @Override
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
                        for (int i = 0; i < a.length; i++) {
                            for (int j = 0; j < a.length; j++) {
                                a[i][j][p] = 0;
                                a[i][j][q] = 0;

                                b[i][j][p] = 0;
                                b[i][j][q] = 0;

                                c[i][j][p] = 0;
                                c[i][j][q] = 0;
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

    public void GeneraAleatorio(float[][][]a,float b[][][],float c[][][]){
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {

                a[i][j][p] = (float)Math.random();
                b[i][j][p] = (float)Math.random();
                c[i][j][p] = (float)Math.random();
                

            }
        }
    }

    public void nucleoCompu(float a[][][],float b[][][],float c[][][])throws Exception{
        int height;
        int width;
        height = width = a.length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float c_a = 0;
                float c_b = 0;
                float c_c = 0;
                for (int i = x-1; i <= x+1; i++) {
                    for (int j = y-1; j <= y+1; j++) {
                        c_a += a[(i+width)%width][(j+height)%height][p];
                        c_b += b[(i+width)%width][(j+height)%height][p];
                        c_c += c[(i+width)%width][(j+height)%height][p];
                    }                    
                }
                c_a /= 9.0;
                c_b /= 9.0;
                c_c /= 9.0;
                a[x][y][q] = Math.min(1,Math.max(0, c_a + c_a * (c_b - c_c)));
                b[x][y][q] = Math.min(1,Math.max(0, c_b + c_b * (c_b - c_a)));
                c[x][y][q] = Math.min(1,Math.max(0, c_c + c_c * (c_a - c_b)));
            }
        }

    }
    private void pinta(float[][][] a, float[][][]b,float[][][]c,float[]concentracionActual,float[] concentracionAntes,int generacionActual,Graphics gVida,Graphics Ggrafica,BufferedImage imagenGrafica, JLabel contenedorVida, JLabel contenedorGrafica){
        BufferedImage aux = new BufferedImage(ancho, alto/3, BufferedImage.TYPE_INT_BGR);
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
        contenedorGrafica.repaint();
    };


    private void cuentaVivos(float[][][]a, float b[][][], float c[][][],float[]conc){
        float[] concentracion = new float[3];
        for (int i = 0; i < conc.length; i++) {
            conc[i] = 0;
        }
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                conc[0] += a[i][j][q];
                conc[1] += b[i][j][q];
                conc[2] += c[i][j][q];
            }
        }
    }

    private void normaliza(float[]conc,int norm){
        for (int i = 0; i < conc.length; i++) {
            conc[i]/=norm;
        }
    }
}
