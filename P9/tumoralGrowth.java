
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
    private int ancho,alto,anchoCel,altoCel,nGen,generacio,p,q,NP;
    private double Ps,Pp,Pm;
    private int old,nuevo;
    private int[][][] PH;
    private boolean[][][] estadoTumor;
    private JFrame frame;
    private boolean flag;
    private SpringLayout layout;
    private JTextField TFGeneraciones,Ps_TF,Pp_TF,Np_TF,Pm_TF;
    private JComboBox<String>CBModoInicial;
    private JButton Generar,Reset,GenerarMas,GenerarIlimitado,Detener,ConfigInicial,Default1,Default2,Default3,Default4;
    private JLabel contenedorVida, contenedorGrafica;
    private BufferedImage imagenVida,imagenGrafica;
    private Graphics gVida,gGrafica;
    
    tumoralGrowth(){
        generacio = 0;
        p = 0; q = 0;
        //Inicializamos todo los elementos
        ancho = alto = 600;
        anchoCel = altoCel = 6;
        nGen = 0;
        estadoTumor = new boolean[100][100][2];
        PH = new int[100][100][2];
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

        Default1 = new JButton("Default 1");
        Default2 = new JButton("Default 2");
        Default3 = new JButton("Default 3");
        Default4 = new JButton("Default 4");


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

        Ps_TF.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent arg0) {
                if(Ps_TF.getText().equals("")){
                    Ps_TF.setText("PS");
                }
            }
            @Override
            public void focusGained(FocusEvent arg0) {
                if(Ps_TF.getText().equals("PS")){
                    Ps_TF.setText("");
                }
            }
        });

        Pp_TF.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent arg0) {
                if(Pp_TF.getText().equals("")){
                    Pp_TF.setText("PP");
                }
            }
            @Override
            public void focusGained(FocusEvent arg0) {
                if(Pp_TF.getText().equals("PP")){
                    Pp_TF.setText("");
                }
            }
        });

        Np_TF.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent arg0) {
                if(Np_TF.getText().equals("")){
                    Np_TF.setText("NP");
                }
            }
            @Override
            public void focusGained(FocusEvent arg0) {
                if(Np_TF.getText().equals("NP")){
                    Np_TF.setText("");
                }
            }
        });

        Pm_TF.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent arg0) {
                if(Pm_TF.getText().equals("")){
                    Pm_TF.setText("PM");
                }
            }
            @Override
            public void focusGained(FocusEvent arg0) {
                if(Pm_TF.getText().equals("PM")){
                    Pm_TF.setText("");
                }
            }
        });

        Generar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                flag = true;
                nGen = Integer.parseInt(TFGeneraciones.getText());
                Ps = Double.parseDouble(Ps_TF.getText());
                Pp = Double.parseDouble(Pp_TF.getText());
                NP = Integer.parseInt(Np_TF.getText());
                Pm = Double.parseDouble(Pm_TF.getText());
                if(CBModoInicial.getSelectedItem()=="Personalizado"){

                }
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
                            //if(p == 0){ p = 1; q = 0;}
                            //else{       p = 0; q = 1;}
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
            public void actionPerformed(ActionEvent arg0) {
                SwingWorker w = new SwingWorker<>() {
                    protected Object doInBackground() throws Exception {
                        nGen = Integer.parseInt(TFGeneraciones.getText());
                        Ps = Double.parseDouble(Ps_TF.getText());
                        Pp = Double.parseDouble(Pp_TF.getText());
                        NP = Integer.parseInt(Np_TF.getText());
                        Pm = Double.parseDouble(Pm_TF.getText());
                        int add = Integer.parseInt(TFGeneraciones.getText());
                        cuentaVivos(estadoTumor);

                        nuevo = old;
                        normaliza(old,45);
                        for (int i = nGen; i < nGen+add&&flag==true; i++) {

                            pinta(estadoTumor , nuevo, old, i, gVida, gGrafica, imagenGrafica, contenedorVida, contenedorGrafica);
                            nucleoCompu(estadoTumor);
                            old = nuevo;
                            nuevo = cuentaVivos(estadoTumor);
                            normaliza(nuevo,45);
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

                SwingWorker w = new SwingWorker<>() {
                    protected Object doInBackground() throws Exception {
                        nGen = Integer.parseInt(TFGeneraciones.getText());
                        Ps = Double.parseDouble(Ps_TF.getText());
                        Pp = Double.parseDouble(Pp_TF.getText());
                        NP = Integer.parseInt(Np_TF.getText());
                        Pm = Double.parseDouble(Pm_TF.getText());
                        int add = Integer.parseInt(TFGeneraciones.getText());
                        old = cuentaVivos(estadoTumor);
                        normaliza(old,45);

                        nuevo = old;
                        for (int i = nGen;flag==true; i++) {


                            pinta(estadoTumor , nuevo, old, i, gVida, gGrafica, imagenGrafica, contenedorVida, contenedorGrafica);
                            nucleoCompu(estadoTumor);
                            normaliza(nuevo, 45);
                            old = nuevo;
                            nuevo = cuentaVivos(estadoTumor);
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
                        for (int i = 0; i < 100; i++) {
                            for (int j = 0; j < 100; j++) {
                                estadoTumor[i][j][p] = false;
                            }
                        }
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

        Default1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                GeneraInicial(estadoTumor, 1);
               Ps_TF.setText("1"); 
               Pp_TF.setText("0.25"); 
               Np_TF.setText("1"); 
               Pm_TF.setText("0.2"); 
            }
        });

        Default2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                GeneraInicial(estadoTumor, 2);
               Ps_TF.setText("1"); 
               Pp_TF.setText("0.25"); 
               Np_TF.setText("1"); 
               Pm_TF.setText("0.8"); 
            }
        });

        Default3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                GeneraInicial(estadoTumor, 2);
               Ps_TF.setText("1"); 
               Pp_TF.setText("0.25"); 
               Np_TF.setText("2"); 
               Pm_TF.setText("0.2"); 
            }
        });

        Default4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                GeneraInicial(estadoTumor, 2);
               Ps_TF.setText("1"); 
               Pp_TF.setText("0.25"); 
               Np_TF.setText("2"); 
               Pm_TF.setText("0.8"); 
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
        frame.add(Default1);
        frame.add(Default2);
        frame.add(Default3);
        frame.add(Default4);
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
        
        layout.putConstraint(SpringLayout.WEST, Default4, 15, SpringLayout.EAST, Default3);
        layout.putConstraint(SpringLayout.NORTH, Default4, 0, SpringLayout.NORTH, Default3);

        layout.putConstraint(SpringLayout.WEST, Default2, 15, SpringLayout.EAST, Default1);
        layout.putConstraint(SpringLayout.NORTH, Default2, 0, SpringLayout.NORTH, Default1);

        layout.putConstraint(SpringLayout.WEST, Default3, 15, SpringLayout.EAST, Default2);
        layout.putConstraint(SpringLayout.NORTH, Default3, 0, SpringLayout.NORTH, Default2);

        layout.putConstraint(SpringLayout.WEST, Default1, 15, SpringLayout.EAST, Generar);
        layout.putConstraint(SpringLayout.NORTH, Default1, 0, SpringLayout.NORTH, Generar);

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

        

        




    }
    //TODO Modelas para configuracion inicial del tumor
    public void GeneraInicial(boolean[][][] estadoTumor,int D){
        switch(D){
            case 1:
                for (int i = 0; i < 3; i++) {
                    estadoTumor[i+50][50][p] = true;   
                }
                break;
            case 2:
                estadoTumor[50][50][p] = true;
                break;
        }
    }
    //TODO Nucleo cumputacional tumores
    public void nucleoCompu(boolean [][][] estadoTumor)throws Exception{
        for (int i = 0; i < estadoTumor.length; i++) {
           for (int j = 0; j < estadoTumor.length; j++) {
                if(estadoTumor[i][j][p]){

                    if(sobrevive()){
                        if(prolifera(i, j)){
                            double rrp = Math.random();
                            int posicion = calculaPosicion(rrp, i, j, estadoTumor);
                            proliferaAct(i, j, posicion);

                            
                        }else 
                        if(migra()){
                            double rrm = Math.random();
                            int posicion = calculaPosicion(rrm, i, j, estadoTumor);
                            migraAct(i, j, posicion);

                        }
                    }else{
                        estadoTumor[i][j][q] = false;
                        PH[i][j][q] = 0;
                    }
                }
           } 
        }

    }

    //TODO Pintar basando en 0 o 1
    private void pinta(boolean[][][]estadoTumor,int nuevo, int old, int generacionActual, Graphics gVida, Graphics Ggrafica, BufferedImage imagenGrafica, JLabel contenedorVida, JLabel contenedorGrafica){
        BufferedImage aux = new BufferedImage(ancho, alto/3, BufferedImage.TYPE_INT_BGR);
        Ggrafica = aux.getGraphics();
        if(generacio>300){
        Ggrafica.drawImage(imagenGrafica, -1, 0, null);
        generacio--;}
        else{

            Ggrafica.drawImage(imagenGrafica, 0, 0, null);
        }
        anchoCel = altoCel = 6;
        for (int i = 0; i < estadoTumor.length; i++) {
            for (int j = 0; j < estadoTumor.length; j++) {
                if(estadoTumor[i][j][p]){
                    gVida.setColor(Color.PINK);
                    gVida.fillRect(i*anchoCel, j*altoCel, (i+1)*anchoCel, (j+1)*altoCel);
                }
                else{
                    gVida.setColor(Color.BLACK);
                    gVida.fillRect(i*anchoCel, j*altoCel, (i+1)*anchoCel, (j+1)*altoCel);

                }
            }
        }
        Ggrafica.setColor(Color.GREEN);
        Ggrafica.drawLine(generacio,200 - old/60, generacio+1, 200 - nuevo/60);//Concentracion de C 
        gGrafica = imagenGrafica.getGraphics();
        gGrafica.drawImage(aux, 0, 0, null);
        contenedorVida.repaint();
        contenedorGrafica.repaint();
        generacio++;
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

    private double calculaProbabilidad(int i, int j, int Ni, int Nj, boolean[][][] estadoTumor){
            return (1-boolToint(estadoTumor[Math.abs((i+Ni)%100)][Math.abs((j+Nj)%100)][p]))/
                (double)(4-
                 (boolToint(estadoTumor[Math.abs((i+1)%100)][j][p])+
                  boolToint(estadoTumor[Math.abs((i-1)%100)][j][p])+
                  boolToint(estadoTumor[i][Math.abs((j+1)%100)][p])+
                  boolToint(estadoTumor[i][Math.abs((j-1)%100)][p])));

    }



    private double P1(int i,int j,boolean[][][]estadoTumor){
        return calculaProbabilidad(i, j, -1, 0, estadoTumor);
    }
    private double P2(int i,int j,boolean[][][]estadoTumor){
        return calculaProbabilidad(i, j, +1, 0, estadoTumor);
    }
    private double P3(int i,int j,boolean[][][]estadoTumor){
        return calculaProbabilidad(i, j, 0, -1, estadoTumor);
    }
    private double P4(int i,int j,boolean[][][]estadoTumor){
        return calculaProbabilidad(i, j, 0, 1, estadoTumor);
    }

    private int boolToint(boolean B){
        return B ? 1 : 0;
    }

    private int calculaPosicion(double Aleatorio,int i,int j,boolean[][][]estadoTumor){
        if(0<=Aleatorio && Aleatorio<=P1(i,j,estadoTumor)){
            return 1;
        }
        else if(Aleatorio<=P1(i,j,estadoTumor)+P2(i, j, estadoTumor)){
            return 2;
        }
        else if(Aleatorio<=P1(i, j, estadoTumor)+P2(i, j, estadoTumor)+P3(i, j, estadoTumor)){
            return 3;
        }
        else if(Aleatorio<=1){
            return 4;
        }
        return 0;

    }

    private void actualizaGen(int i, int j, int posicion, boolean[][][] estadoTumor,boolean migra){
        int Ni = 0, Nj = 0;
        switch(posicion){
            case 1:
                Ni = -1; Nj = 0;break;
            case 2:
                Ni = 1; Nj = 0;break;
            case 3:
                Ni = 0; Nj = -1;break;
            case 4:
                Ni = 0; Nj = 1;break;
        }
        if(!estadoTumor[Math.abs((i+Ni)%100)][Math.abs((j+Nj)%100)][p]){
            if(migra){
                estadoTumor[i][j][q] = false;
                PH[Math.abs((i+Ni)%100)][Math.abs((j+Nj)%100)][q]=PH[i][j][p];
            }
            else{
                estadoTumor[i][j][q] = true;
                PH[Math.abs((i+Ni)%100)][Math.abs((j+Nj)%100)][q]=0;

            }
            estadoTumor[Math.abs((i+Ni)%100)][Math.abs((j+Nj)%100)][q] = true;
            PH[i][j][q]=0;
        }
    }

    private void proliferaAct(int i, int j, int posicion){
        actualizaGen(i, j, posicion, estadoTumor, false);
    }
    private void migraAct(int i, int j, int posicion){
        actualizaGen(i, j, posicion, estadoTumor, true);
    }
    private boolean prolifera(int i, int j){
        double rrp = Math.random();
        if(rrp<Pp){
            PH[i][j][q]++;
        }
        return PH[i][j][q] >= NP;
    }
    private boolean sobrevive(){
        double rs = Math.random();
        return rs<Ps;
    }

    private boolean migra(){
        double rrm = Math.random();
        return rrm<Pm;
    }


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
        frameaux.addWindowListener(new WindowAdapter(){
             @Override
             public void windowClosing(WindowEvent arg0) {
                for (int i = 0; i < 100; i++) {
                    for (int j = 0; j < 100; j++) {
                        estadoTumor[i][j][p] = botones[j][i].isSelected();
                    }
                }
                
             }
        });

    }
}
