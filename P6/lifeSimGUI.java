import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.event.FocusListener;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;

import java.awt.Graphics;
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
        CBModoInicial = new JComboBox<>(modos);
        Generar = new JButton("Generar");//TODO Añador action listener
        Reset = new JButton("Reset");//TODO Añadir action listener
        GenerarMas = new JButton("Generar más");//TODO Dar action listener
        GenerarIlimitado = new JButton("Generar Ilimitado");//TODO Dar action listener
        contenedorVida = new JLabel();
        contenedorGrafica = new JLabel();
        imagenVida = new BufferedImage(ancho, alto,BufferedImage.TYPE_INT_BGR);
        imagenGrafica = new BufferedImage(ancho, alto,BufferedImage.TYPE_INT_BGR);
        gVida = imagenVida.getGraphics();
        gGrafica = imagenGrafica.getGraphics();

        
        
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


            }
        });





        

        




    }
    //TODO Generador aleatorio
    //TODO Generador de islas
    //TODO Generador de cosas que disparan
    //TODO Crear nuclero cumputacional del juego de la vida
}
