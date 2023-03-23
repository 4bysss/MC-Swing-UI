import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ca1DSim {
    private JFrame frame;
    private SpringLayout layout;
    private JComboBox<String> colores;
    private JComboBox<String> modoInicial;
    private JTextField generaciones, clave;
    private JButton generar,reset;
    private BufferedImage output;
    private Graphics g;
    private JLabel contenedor;
    ca1DSim(){
        //Dimensiones de la ventana
        int ancho = 600;
        int alto = 600;
        

        //Modos que el ususario podra seleccionar
        String[] modos = {"Central", "Aleatoria"};
        String[] tCol = {"2","3","4","5"};

        //Inicializamos todos los elementos de la ventana
        layout = new SpringLayout();


        frame = new JFrame("Automata Celular");
        frame.setLayout(layout);
        frame.setSize(ancho, alto);
        frame.setResizable(true);
        frame.setVisible(true);


        colores = new JComboBox<>(tCol);
        modoInicial = new JComboBox<>(modos);

    
        generaciones = new JTextField(9);
        clave = new JTextField(9);
        generar = new JButton("Generar");
        reset = new JButton("Reset");


        output = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_BGR);
        g = output.getGraphics();
        g.setColor(new Color(21,21,21));
        g.fillRect(0, 0, ancho, alto);
        
        contenedor = new JLabel(new ImageIcon(output));

        //Añadimos los elementos a el frame
        frame.add(colores);
        frame.add(modoInicial);
        frame.add(generaciones);
        frame.add(clave);
        frame.add(generar);
        frame.add(reset);
        frame.add(contenedor);

        //Colocamos dentro del frame todos y cada uno de los elementos
        layout.putConstraint(SpringLayout.WEST, contenedor, 400, SpringLayout.EAST, frame);
        layout.putConstraint(SpringLayout.NORTH, contenedor, 70, SpringLayout.NORTH, frame);
        
        
        layout.putConstraint(SpringLayout.WEST, colores, 150, SpringLayout.EAST, contenedor);
        layout.putConstraint(SpringLayout.NORTH, colores, 350, SpringLayout.NORTH, contenedor);


        layout.putConstraint(SpringLayout.WEST, modoInicial, 20, SpringLayout.EAST, colores);
        layout.putConstraint(SpringLayout.NORTH, modoInicial, 0, SpringLayout.NORTH, colores);


        layout.putConstraint(SpringLayout.WEST, generaciones, 0, SpringLayout.WEST, colores);
        layout.putConstraint(SpringLayout.NORTH, generaciones, -40, SpringLayout.NORTH, colores);


        layout.putConstraint(SpringLayout.WEST, clave, 100, SpringLayout.WEST, generaciones);
        layout.putConstraint(SpringLayout.NORTH, clave, 0, SpringLayout.NORTH, generaciones);


        layout.putConstraint(SpringLayout.WEST, generar, 0, SpringLayout.WEST, generaciones);
        layout.putConstraint(SpringLayout.NORTH, generar, -40, SpringLayout.NORTH, generaciones);


        layout.putConstraint(SpringLayout.WEST, reset, 0, SpringLayout.WEST, clave);
        layout.putConstraint(SpringLayout.NORTH, reset, -40, SpringLayout.NORTH, clave);


        //Añadimos las funcionalidades a los botones
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                g.clearRect(0, 0, contenedor.getWidth(), contenedor.getHeight());
                contenedor.repaint();
            }
        });
        generar.addActionListener(new ActionListener(){


            public void actionPerformed(ActionEvent arg0) {
                int[]arr1 = new int[ancho];
                int[]arr2 = new int[ancho];
                String mCol = (String)colores.getSelectedItem();
                String mIni = (String)modoInicial.getSelectedItem();
                int generations = Integer.parseInt(generaciones.getText());
                int key = Integer.parseInt(clave.getText());
                switch (mIni) {
                    case "Central":
                        generaCentral(arr1,Integer.parseInt(mCol)-1);
                        break;
                    case "Aleatoria":
                        generaAleatorio(arr1,Integer.parseInt(mCol));
                        break;

                    default:
                        break;
                }
                    SwingWorker worker;
                    int[] claveBin;

                    claveBin = new int[100];
                    conversorKesimo(key, mCol,claveBin);
                    worker = new SwingWorker(){
                        protected Object doInBackground() throws Exception {
                            kColores(arr1, arr2, generations, g,contenedor,claveBin,Integer.parseInt(mCol));
                            return null;
                        };
                    };
                    worker.execute();

            }


        });
        
    }
    
    private void conversorKesimo(int clave, String nCol,int[] inputOutput){
        int[] Arraux = new int[inputOutput.length];
        int aux;
        int i;
        switch (nCol) {
            case "2":
                aux = clave;
                i = 0;
                while((aux/2)!=0){
                    if(aux/2==1){
                        Arraux[i]=1;
                        aux = aux/2;
                        i++;
                    }
                    Arraux[i]=aux%2;
                    i++;
                    aux = aux/2;

                }
                for (int j = 0; j < Arraux.length; j++) {
                    inputOutput[j]=Arraux[j];
                }
                break;

            case "3":
                aux = clave;
                i = 0;
                while((aux/3)!=0){
                    if((aux/3)<3){
                        Arraux[i]= aux%3;
                        aux = aux/3;
                        i++;
                    }
                    Arraux[i]=aux%3;
                    i++;
                    aux = aux/3;

                }
                for (int j = 0; j < Arraux.length; j++) {
                    inputOutput[j]=Arraux[j];
                }
                break;
            case "4":
                aux = clave;
                i = 0;
                while((aux/4)!=0){
                    if((aux/4)<4){
                        Arraux[i]=aux%4;
                        aux = aux/4;
                        i++;
                    }
                    Arraux[i]=aux%4;
                    i++;
                    aux = aux/4;

                }
                for (int j = 0; j < Arraux.length; j++) {
                    inputOutput[j]=Arraux[Arraux.length - j - 1];
                }
                break;
            case "5":
                aux = clave;
                i = 0;
                while((aux/5)!=0){
                    if((aux/5)<5){
                        Arraux[i]=aux%5;
                        aux = aux/5;
                        i++;
                    }
                    Arraux[i]=aux%5;
                    i++;
                    aux = aux/5;

                }
                for (int j = 0; j < Arraux.length; j++) {
                    inputOutput[j]=Arraux[j];
                }
                break;
        }
    }
 


    private void generaAleatorio(int[]arr1,int nCol){
        int rand = 7;
        for (int i = 0; i < arr1.length; i++) {
            rand = (int)randomGenerator.Randu((long)rand);
            arr1[i] = rand%nCol;
        }

    }
    private void generaCentral(int[] arr1,int colores){
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = 0;
        }
        arr1[arr1.length/2 - 1 ] = colores;

    }


    private void kColores(int[]arr1,int[]arr2,int generaciones,Graphics g,JLabel contenedor,int[]claveBin,int nCol){
        int[] temp = new int[arr1.length];
        int suma;
        System.out.println(Arrays.toString(arr1));
        for (int i = 0; i < generaciones; i++) {
            int cosa = 0;
            for (int j = 0; j < arr1.length; j++) {

                if(j == 0){ suma = (arr1[j]+arr1[j+1]+arr1[arr1.length-1]);}

                else if(j == arr1.length-1){ suma = (arr1[j]+arr1[0]+arr1[j-1]);}

                else{ suma = arr1[j] + arr1[j+1] + arr1[j-1];}
                arr2[j] = claveBin[suma];
                suma =0 ;
            }
            for (int k = 0; k < arr2.length; k++) {
                switch (arr2[k]) {
                    case 0:
                       g.setColor(Color.GREEN);  g.drawOval(k, i, 1, 1); 
                        break;

                    case 1:
                       g.setColor(Color.MAGENTA);  g.drawOval(k, i, 1, 1); 
                        
                        break;

                    case 2:
                       g.setColor(Color.BLUE);  g.drawOval(k, i, 1, 1); 
                        
                        break;

                    case 3:
                       g.setColor(Color.RED);  g.drawOval(k, i, 1, 1); 
                        
                        break;

                    case 4:
                       g.setColor(Color.YELLOW);  g.drawOval(k, i, 1, 1); 
                        
                        break;

                    default:
                        break;
                }
            }
            arr1 = Arrays.copyOf(arr2, arr2.length);
            /*for (int z = 0; z < arr1.length; z++) {
               arr1[z] = arr2[z]; 
            }*/
            contenedor.repaint();
        }
    }


}
