import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Graphics;
/**
 * randomGenerator
 */
public class randomGenerator extends SwingWorker {
    private JPanel panel;
    //Debemos limitar el texto a 9 caracteres
    protected Object doInBackground() throws Exception {
        int ancho = 600;
        int alto = 600;

        JButton Limpiar = new JButton("Limpiar");
        JButton Generador = new JButton("Generar más");
        SpringLayout lo = new SpringLayout();
        JFrame frameAux = new JFrame("Generador de puntos aleatorios");
        frameAux.setLayout(lo);
        frameAux.setResizable(true);

        //Creamos el combo Box que contendra los distintos items(Modos de generacion de numero PseudoAleatorios)
        String[] modos = {"26.1a","26.1b","26.2","26.3","Combinatorio","Fish-Moore","Randu"};
        JComboBox<String> modosRN = new JComboBox<>(modos);


        //Creamos el cuaddro de texto que admitira el numero de puntos a introducir
        JTextField cuadroText = new JTextField(9);
        
        //Creamos la imagen que vamos a utilizar.
        BufferedImage bufferedImage = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_BGR);
        Graphics g = bufferedImage.getGraphics();
        g.setColor(Color.MAGENTA);
        
        //Creamos la caja que contendra los numeros (normalizados) que ha generado  
        JTextArea cajaNum = new JTextArea();
        cajaNum.setAutoscrolls(true);
        cajaNum.setBorder(new LineBorder(Color.black));
        cajaNum.setLineWrap(true);
        cajaNum.setWrapStyleWord(true);
        //cajaNum.setVerticalAlignment(JLabel.TOP);
        JScrollPane ScrolCaja = new JScrollPane(cajaNum);
        ScrolCaja.setPreferredSize(new Dimension(ancho,alto/4));
        ScrolCaja.setViewportView(cajaNum);
        ScrolCaja.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel panel = new JLabel(new ImageIcon(bufferedImage));
        frameAux.setSize(800,800);
        

        //Posicion de la imagen con respecto al marco 
        lo.putConstraint(SpringLayout.WEST, panel, 400, SpringLayout.EAST, frameAux);
        lo.putConstraint(SpringLayout.NORTH, panel, 70, SpringLayout.VERTICAL_CENTER, frameAux);
            
        //Posicion de la caja con respecto al marco
        lo.putConstraint(SpringLayout.WEST, ScrolCaja, 400, SpringLayout.EAST, frameAux);
        lo.putConstraint(SpringLayout.NORTH, ScrolCaja, 700, SpringLayout.NORTH, frameAux);
        


        //Posicion del boton generador con respecto a la imagen        
        lo.putConstraint(SpringLayout.WEST, Generador, 150, SpringLayout.EAST, panel);
        lo.putConstraint(SpringLayout.NORTH, Generador, 150, SpringLayout.NORTH, panel);
        

        //Posicion de el cuadro de texto con respecto al boton generaddor
        lo.putConstraint(SpringLayout.WEST, cuadroText, 0, SpringLayout.WEST, Generador);
        lo.putConstraint(SpringLayout.NORTH, cuadroText, 30, SpringLayout.NORTH, Generador);


        //Posicion de el cuadro selector con respecto al cuadro de texto
        lo.putConstraint(SpringLayout.WEST, modosRN, 0, SpringLayout.WEST, cuadroText);
        lo.putConstraint(SpringLayout.NORTH, modosRN, 25, SpringLayout.NORTH, cuadroText);

        //Posicion del boton limpiador con respecto del cuadro de texto
        lo.putConstraint(SpringLayout.WEST, Limpiar, 0, SpringLayout.WEST, cuadroText);
        lo.putConstraint(SpringLayout.NORTH, Limpiar, 55, SpringLayout.NORTH, cuadroText);



        //Añadimos los componentes al frame
        frameAux.add(Limpiar);
        frameAux.add(modosRN);
        frameAux.add(cuadroText);
        frameAux.add(panel);
        frameAux.add(Generador);
        frameAux.add(ScrolCaja);
        frameAux.validate();
        //Acciones del boton limpiador
        Limpiar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                g.clearRect(0, 0,panel.getWidth(), panel.getHeight());
                panel.repaint();
                cajaNum.setText("");
            }
        });

        //Acciones del boton generador
        Generador.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
            SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
            long unnormalizedX = 0;
            long unnormalizedY = 0;
            float normalizedX = 0;
            float normalizedY = 0;
            String TNumero = cuadroText.getText();
            String Metodo = (String) modosRN.getSelectedItem(); 
            TNumero.replaceAll("\\D", "");
            Integer numPuntos = Integer.parseInt(TNumero);
            switch (Metodo) {
                case "26.1a":
                    unnormalizedX = RN261A((long)(numPuntos%31));
                    unnormalizedY = RN261A(unnormalizedX);
                    normalizedX = (float)unnormalizedX/31.0f;
                    normalizedY = unnormalizedY/31.0f;
                    cajaNum.append(" "+normalizedX+" "+normalizedY);
                    for (int i = 0; i < numPuntos; i++) {

                        unnormalizedX = RN261A(unnormalizedY);
                        unnormalizedY = RN261A(unnormalizedX);
                        normalizedX = (float)unnormalizedX/31.0f;
                        normalizedY = (float)unnormalizedY/31.0f;
                        cajaNum.append(" "+normalizedX+" "+normalizedY);

                        //ScrolCaja.getHorizontalScrollBar().setValue(maxHor);

                        g.drawLine((int)(normalizedX*ancho), (int)(normalizedY*alto), (int)(normalizedX*ancho), (int)(normalizedY*alto));
                    }
                    panel.repaint();
                    break;

                case "26.1b":
                    unnormalizedX = RN261B((long)(numPuntos%31));
                    unnormalizedY = RN261B(unnormalizedX);
                    normalizedX = (float)unnormalizedX/31.0f;
                    normalizedY = unnormalizedY/31.0f;
                    cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                    for (int i = 0; i < numPuntos; i++) {

                        unnormalizedX = RN261B(unnormalizedY);
                        unnormalizedY = RN261B(unnormalizedX);
                        normalizedX = (float)unnormalizedX/31.0f;
                        normalizedY = (float)unnormalizedY/31.0f;

                        cajaNum.append(" "+normalizedX+" "+normalizedY);
                        //cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);

                        int maxHor = ScrolCaja.getHorizontalScrollBar().getMaximum();
                        int maxVer = ScrolCaja.getVerticalScrollBar().getMaximum();
                        ScrolCaja.getHorizontalScrollBar().setValue(maxHor);
                        ScrolCaja.getVerticalScrollBar().setValue(maxVer);

                        //cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                        g.drawLine((int)(normalizedX*ancho), (int)(normalizedY*alto), (int)(normalizedX*ancho), (int)(normalizedY*alto));
                    }
                    panel.repaint();
                    break;

                case "26.2":
                    unnormalizedX = RN262((long)(numPuntos%31));
                    unnormalizedY = RN262(unnormalizedX);
                    normalizedX = (float)unnormalizedX/30.0f;
                    normalizedY = unnormalizedY/30.0f;
                    cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                    for (int i = 0; i < numPuntos; i++) {

                        unnormalizedX = RN262(unnormalizedY);
                        unnormalizedY = RN262(unnormalizedX);
                        normalizedX = (float)unnormalizedX/30.0f;
                        normalizedY = (float)unnormalizedY/30.0f;

                        cajaNum.append(" "+normalizedX+" "+normalizedY);
                        //cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                        int maxHor = ScrolCaja.getHorizontalScrollBar().getMaximum();
                        int maxVer = ScrolCaja.getVerticalScrollBar().getMaximum();
                        ScrolCaja.getHorizontalScrollBar().setValue(maxHor);
                        ScrolCaja.getVerticalScrollBar().setValue(maxVer);

                        //cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                        //g.drawLine((int)(normalizedX*ancho), (int)(normalizedY*alto), (int)(normalizedX*ancho), (int)(normalizedY*alto));
                    }
                    panel.repaint();
                    break;

                case "26.3":
                    unnormalizedX = RN263((long)(numPuntos%31));
                    unnormalizedY = RN263(unnormalizedX);
                    normalizedX = (float)unnormalizedX/(long)(Math.pow(2, 31)-2);
                    normalizedY = (float)unnormalizedY/(long)(Math.pow(2, 31)-2);
                    cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                    for (int i = 0; i < numPuntos; i++) {

                        unnormalizedX = RN263(unnormalizedY);
                        unnormalizedY = RN263(unnormalizedX);
                        normalizedX = (float)unnormalizedX/(long)(Math.pow(2, 31)-2);
                        normalizedY = (float)unnormalizedY/(long)(Math.pow(2, 31)-2);

                        cajaNum.append(" "+normalizedX+" "+normalizedY);
                        //cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                        int maxHor = ScrolCaja.getHorizontalScrollBar().getMaximum();
                        int maxVer = ScrolCaja.getVerticalScrollBar().getMaximum();
                        ScrolCaja.getHorizontalScrollBar().setValue(maxHor);
                        ScrolCaja.getVerticalScrollBar().setValue(maxVer);

                        //cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                        g.drawLine((int)(normalizedX*ancho), (int)(normalizedY*alto), (int)(normalizedX*ancho), (int)(normalizedY*alto));
                    }
                    panel.repaint();
                    break;

                case "Combinatorio":
                    unnormalizedX = Combi2642((long)(numPuntos%31));
                    unnormalizedY = Combi2642(unnormalizedX);
                    normalizedX = (float)unnormalizedX/32362;
                    normalizedY = (float)unnormalizedY/32362;
                    cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                    for (int i = 0; i < numPuntos; i++) {

                        unnormalizedX = Combi2642(unnormalizedY);
                        unnormalizedY = Combi2642(unnormalizedX);
                        normalizedX = (float)unnormalizedX/32362;
                        normalizedY = (float)unnormalizedY/32362;

                        cajaNum.append(" "+normalizedX+" "+normalizedY);
                        //cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                        int maxHor = ScrolCaja.getHorizontalScrollBar().getMaximum();
                        int maxVer = ScrolCaja.getVerticalScrollBar().getMaximum();
                        ScrolCaja.getHorizontalScrollBar().setValue(maxHor);
                        ScrolCaja.getVerticalScrollBar().setValue(maxVer);

                        //cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                        g.drawLine((int)(normalizedX*ancho), (int)(normalizedY*alto), (int)(normalizedX*ancho), (int)(normalizedY*alto));
                    }
                    panel.repaint();
                    break;

                case "Fish-Moore":
                    unnormalizedX = FishmanMoore((long)(numPuntos%31));
                    unnormalizedY = FishmanMoore(unnormalizedX);
                    normalizedX = (float)unnormalizedX/(long)(Math.pow(2, 31)-2);
                    normalizedY = unnormalizedY/(long)(Math.pow(2, 31)-2);
                    cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                    for (int i = 0; i < numPuntos; i++) {

                        unnormalizedX = FishmanMoore(unnormalizedY);
                        unnormalizedY = FishmanMoore(unnormalizedX);
                        normalizedX = (float)unnormalizedX/(long)(Math.pow(2, 31)-2);
                        normalizedY = (float)unnormalizedY/(long)(Math.pow(2, 31)-2);

                        cajaNum.append(" "+normalizedX+" "+normalizedY);
                        //cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                        int maxHor = ScrolCaja.getHorizontalScrollBar().getMaximum();
                        int maxVer = ScrolCaja.getVerticalScrollBar().getMaximum();
                        ScrolCaja.getHorizontalScrollBar().setValue(maxHor);
                        ScrolCaja.getVerticalScrollBar().setValue(maxVer);

                        //cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                        g.drawLine((int)(normalizedX*ancho), (int)(normalizedY*alto), (int)(normalizedX*ancho), (int)(normalizedY*alto));
                    }
                    panel.repaint();
                    break;

                case "Randu":
                    unnormalizedX = Randu((long)(numPuntos%31));
                    unnormalizedY = Randu(unnormalizedX);
                    normalizedX = (float)unnormalizedX/(long)(Math.pow(2, 31)-2);
                    normalizedY = unnormalizedY/(long)(Math.pow(2, 31)-2);
                    cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                    for (int i = 0; i < numPuntos; i++) {

                        unnormalizedX = Randu(unnormalizedY);
                        unnormalizedY = Randu(unnormalizedX);
                        normalizedX = (float)unnormalizedX/(long)(Math.pow(2, 31)-2);
                        normalizedY = (float)unnormalizedY/(long)(Math.pow(2, 31)-2);

                        cajaNum.append(" "+normalizedX+" "+normalizedY);
                        //cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                        int maxHor = ScrolCaja.getHorizontalScrollBar().getMaximum();
                        int maxVer = ScrolCaja.getVerticalScrollBar().getMaximum();
                        ScrolCaja.getHorizontalScrollBar().setValue(maxHor);
                        ScrolCaja.getVerticalScrollBar().setValue(maxVer);

                        //cajaNum.setText(cajaNum.getText()+" "+normalizedX+" "+normalizedY);
                        g.drawLine((int)(normalizedX*ancho), (int)(normalizedY*alto), (int)(normalizedX*ancho), (int)(normalizedY*alto));
                    }
                    panel.repaint();
                    break;

                default:
                    break;
                    }
            return null;
            };
            };
                worker.execute();
            }
        });
        frameAux.setVisible(true);
        
        return null;
    }
    private long RN261A(long n){
        n = 5*n % 32;
        return n;
    }


    private long RN261B(long n){
        n = 7*n % 32;
        return n;
    }


    private long RN262(long n){

        n = 3*n % 31;
        return n;
    }


    private long RN263(long n){
        n = 7*n % (long)(Math.pow(2, 31)-1);
        return n;
    }

    private long Combi2642(long n){
        long w = 157*n % 32363;
        long x = 146*n % 31727;
        long y = 142*n % 31657;
        return (w-x+y)%32362;
    }


    private long FishmanMoore(long n){
        n = 48271*n % (long)(Math.pow(2, 31)-1);
        return n;
    }


    static public long Randu(long n){
        n = (long)(Math.pow(2, 16)+3)*n % (long)(Math.pow(2,31)-1);
        return n;
    }
    randomGenerator(){

    }
}
