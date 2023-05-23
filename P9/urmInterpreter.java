import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
public class urmInterpreter {
    private JFrame frame;
    private SpringLayout layout;
    private JTextArea code, debug, output,iniciales;
    private JButton ejecutar,limpia,iniciaStep,step;
    private JMenuBar barra;
    private JMenu Archivo;
    private JMenuItem Cargar,Guardar;

    private int[] registros,argM;
    private int contadorDePrograma,argI,opCode=-1;
    private String[] lineasaux;
    private ArrayList<String> lineas;
    urmInterpreter(){
       int ancho,alto;
       ancho = alto = 1024;


       frame = new JFrame("Urm Interpreter");


       layout = new SpringLayout();


       frame.setLayout(layout);
       frame.setSize(ancho,alto);
       frame.setResizable(true);
       frame.setVisible(true);


       barra = new JMenuBar();
       Archivo = new JMenu("Archivo");
       Cargar = new JMenuItem("Cargar");
       Guardar = new JMenuItem("Guardar");
       Archivo.add(Cargar);
       Archivo.add(Guardar);
       barra.add(Archivo);
       frame.setJMenuBar(barra);



       code = new JTextArea("Codigo fuente");
       debug = new JTextArea("Debug");
       output = new JTextArea("Output");
       iniciales = new JTextArea("Input");


       ejecutar = new JButton("Ejecutar");
       iniciaStep = new JButton("Iniciar Step");
       step = new JButton("Step");
       limpia = new JButton("Limpiar");


       code.setPreferredSize(new Dimension(720,720));
       debug.setPreferredSize(new Dimension(360,720));
       output.setPreferredSize(new Dimension(150,25));
       iniciales.setPreferredSize(new Dimension(150,25));

       registros = new int[15];
       argM = new int[4];
       contadorDePrograma = 0;

       


       code.setLineWrap(true);
       debug.setLineWrap(true);
       output.setLineWrap(true);
       iniciales.setLineWrap(true);
        

       layout.putConstraint(SpringLayout.NORTH, code, 150, SpringLayout.NORTH, frame);
       layout.putConstraint(SpringLayout.WEST, code, 300, SpringLayout.WEST, frame);


       layout.putConstraint(SpringLayout.NORTH, debug, 0, SpringLayout.NORTH, code);
       layout.putConstraint(SpringLayout.WEST, debug, 15, SpringLayout.EAST, code);


       layout.putConstraint(SpringLayout.NORTH, ejecutar, 15, SpringLayout.SOUTH, code);
       layout.putConstraint(SpringLayout.WEST, ejecutar, 0, SpringLayout.WEST, code);


       layout.putConstraint(SpringLayout.NORTH, output, 0, SpringLayout.NORTH, ejecutar);
       layout.putConstraint(SpringLayout.WEST, output, 15, SpringLayout.EAST, ejecutar);
       

       layout.putConstraint(SpringLayout.NORTH, iniciales, 0, SpringLayout.NORTH, output);
       layout.putConstraint(SpringLayout.WEST, iniciales, 15, SpringLayout.EAST, output);


       layout.putConstraint(SpringLayout.NORTH, iniciaStep, 15, SpringLayout.SOUTH, ejecutar);
       layout.putConstraint(SpringLayout.WEST, iniciaStep, 0, SpringLayout.WEST, ejecutar);


       layout.putConstraint(SpringLayout.NORTH, step, 15, SpringLayout.SOUTH, iniciaStep);
       layout.putConstraint(SpringLayout.WEST, step, 0, SpringLayout.WEST, iniciaStep);
       
       layout.putConstraint(SpringLayout.NORTH, limpia, 0, SpringLayout.NORTH, step);
       layout.putConstraint(SpringLayout.WEST, limpia, 15, SpringLayout.EAST, step);

       frame.add(code);
       frame.add(debug);
       frame.add(output);
       frame.add(ejecutar);
       frame.add(iniciales);
       frame.add(iniciaStep);
       frame.add(step);
       frame.add(limpia);


    Cargar.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent arg0) {
               JFileChooser FC = new JFileChooser();
               FC.setFileFilter(new FileNameExtensionFilter("Archivos URM", "urm"));
               int opcion = FC.showOpenDialog(null);
               code.setText("");
               if(opcion == JFileChooser.APPROVE_OPTION){
                   try {
                    
                       File f = FC.getSelectedFile();
                       FileReader Fr = new FileReader(f);
                       BufferedReader Br = new BufferedReader(Fr);
                       String linea;
                       while((linea=Br.readLine())!=null){
                           code.setText(code.getText()+linea+"\n");

                       }
                       Br.close();

                   } catch (Exception e) {}

               }
           }

    });
    iniciaStep.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
            opCode = -1;
            debug.setText("");
            contadorDePrograma = 1;
            iniIniciales();
           lineasaux = code.getText().split("\n");
           lineas = new ArrayList<String>();
            for (int i = 0; i < lineasaux.length; i++) {
                lineas.add(lineasaux[i]);
            }
        }
    });
    step.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if(contadorDePrograma!=0){
                opCode = interpretaInstruccion(lineas.get(contadorDePrograma-1));
                System.out.println(opCode);
                switch (opCode) {
                    case 1:
                        argI = extraeIarg(lineas.get(contadorDePrograma-1));
                        break;
                    case 2:
                        argI = extraeIarg(lineas.get(contadorDePrograma-1));
                        break;
                    case 3: 
                        extraeDarg(lineas.get(contadorDePrograma-1), argM);
                        break;
                    case 4:
                        extraeTarg(lineas.get(contadorDePrograma-1), argM);
                        break;

                    default:
                        break;
                }
                ejecutaIns(opCode, argI, argM);
                contadorDePrograma++;

                output.setText(Arrays.toString(registros));
                if(contadorDePrograma==0){
                    debug.append("Fin del programa!");

                }
            }
        }
    });
    ejecutar.addActionListener(new ActionListener() {
        int argI;
        int opCode = -1;
        int[] argM = new int[4];
        public void actionPerformed(ActionEvent arg0) {
            debug.setText("");
            contadorDePrograma = 1;
            iniIniciales();
            lineasaux = code.getText().split("\n");
            lineas = new ArrayList<String>();
            for (int i = 0; i < lineasaux.length; i++) {
                lineas.add(lineasaux[i]);
            }
            while (contadorDePrograma-1<lineasaux.length && opCode != 0 && contadorDePrograma!=0) {
                opCode = interpretaInstruccion(lineas.get(contadorDePrograma-1));
                System.out.println(opCode);
                switch (opCode) {
                    case 1:
                        argI = extraeIarg(lineas.get(contadorDePrograma-1));
                        break;
                    case 2:
                        argI = extraeIarg(lineas.get(contadorDePrograma-1));
                        break;
                    case 3: 
                        extraeDarg(lineas.get(contadorDePrograma-1), argM);
                        break;
                    case 4:
                        extraeTarg(lineas.get(contadorDePrograma-1), argM);
                        break;

                    default:
                        break;
                }
                ejecutaIns(opCode, argI, argM);
                contadorDePrograma++;

            }
            debug.append("Fin del programa!");
            output.setText(Arrays.toString(registros));


        }
    });
    Guardar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            JFileChooser FC = new JFileChooser();
            FC.setFileFilter(new FileNameExtensionFilter("Archivos URM", "urm"));
            int opcion = FC.showSaveDialog(null);
            if(opcion == FC.APPROVE_OPTION){
                File f = FC.getSelectedFile();
                Object error = "Fichero ya existe, ¿Quieres reemplazarlo?";
                if(f.exists()){
                    ImageIcon icon = new ImageIcon("./pngegg (1).png");
                    int Confirmacion = JOptionPane.showConfirmDialog(null,
                        error,
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        icon);
                    if(Confirmacion==JOptionPane.YES_OPTION){
                        try {
                            FileWriter FW = new FileWriter(f.getName(),false);
                            if(code.getText().charAt(code.getText().length()-1)=='\n'){
                                FW.write(code.getText().substring(0,code.getText().length()-1));
                            }
                            else{
                                FW.write(code.getText().substring(0,code.getText().length()));
                            }
                            FW.close();
                        } catch (Exception e) {}
                    }
                    else{return;}
                }
                else{
                        try {
                            FileWriter FW = new FileWriter(f.getName()+".urm",true);
                            if(code.getText().charAt(code.getText().length()-1)=='\n'){
                                FW.write(code.getText().substring(0,code.getText().length()-1));
                            }
                            else{
                                FW.write(code.getText().substring(0,code.getText().length()));
                            }
                            FW.close();
                        } catch (Exception e) {}

                }
            }

        }
    });
    limpia.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            anulArgs(argM);
            debug.setText("");
            code.setText("");
            output.setText("");
            iniciales.setText("");
            for (int i = 0; i < registros.length; i++) {
                registros[i] = 0;
            }

        }
    });
   }
 
   int extraeIarg(String I){
       String arg = I.substring(I.indexOf("(")+1,I.indexOf(")")).replaceAll("\\s+", "");
       int numero = -1;
       try {
            numero = Integer.parseInt(arg);
       } catch (NumberFormatException e) {
            System.out.println("Parametro no es un digito, por favor introduza parametro valido.");
        return -1;
       }
       return numero;
   }
   void extraeDarg(String I,int[]arg){
       int inicio = I.indexOf("(")+1;
       int fin = I.indexOf(",");
       String argu = I.substring(inicio,fin).replaceAll("\\s+", "");
       anulArgs(arg);
       try {
            arg[0] = Integer.parseInt(argu);
       } catch (NumberFormatException e) {
            arg[3] = -1;
            System.out.println("Primer parametro no valido, por favor introduza uno valido.");
            return;

       }
       inicio = fin+1;
       fin = I.indexOf(")");
       argu = I.substring(inicio,fin);
       try {
            arg[1] = Integer.parseInt(argu);
        
       } catch (NumberFormatException e) {
            arg[3] = -1;
            System.out.println("Segundo parametro no valido, por favor introduza uno valido.");

       }

   }
   void extraeTarg(String I, int[]arg){
       int inicio = I.indexOf("(")+1;
       int fin = I.indexOf(",");
       String argu = I.substring(inicio,fin).replaceAll("\\s+", "");
       anulArgs(arg);
       try {
            arg[0] = Integer.parseInt(argu);
       } catch (NumberFormatException e) {
            arg[3] = -1;
            System.out.println("Primer parametro no valido, por favor introduza uno valido.");
            return;

       }
       inicio = fin+1;
       fin = I.indexOf(",",inicio);
       argu = I.substring(inicio,fin);
       try {
            arg[1] = Integer.parseInt(argu);
        
       } catch (NumberFormatException e) {
            arg[3] = -1;
            System.out.println("Segundo parametro no valido, por favor introduza uno valido.");
            return;

       }
       inicio = fin+1;
       fin = I.indexOf(")");
       argu = I.substring(inicio,fin);
       try {
            arg[2] = Integer.parseInt(argu);
        
       } catch (NumberFormatException e) {
            arg[3] = -1;
            System.out.println("Tercer parametro no valido, por favor introduza uno valido.");
            return;

       }

   }
   void ejecutaIns(int opCode,int argI,int[]multArg){
       switch (opCode) {
        case 1:
            System.out.println("hace un S");
            registros[argI-1]++;
            debug.append(contadorDePrograma+":R"+argI+" = "+registros[argI-1]+"\n");
            break;
        case 2:
            System.out.println("hace un Z");
            registros[argI-1] = 0;
            debug.append(contadorDePrograma+":R"+argI+" = "+registros[argI-1]+"\n");
            break;
        case 3:
            System.out.println("hace un T");
            registros[multArg[1]-1] = registros[multArg[0]-1];
            debug.append(contadorDePrograma+":R"+multArg[1]+" = "+registros[multArg[0]-1]+"\n");
            break;
        case 4:
            System.out.println("hace un J");
            if(registros[multArg[0]-1]==registros[multArg[1]-1]){
                debug.append(contadorDePrograma+":Jump to "+multArg[2]+"\n");
                contadorDePrograma = multArg[2]-1;
            }
            else{
                debug.append(contadorDePrograma+":No jump \n");
            }

            break;


        default:
            break;
       }
   }

   int interpretaInstruccion(String I){
        String type = I.substring(0,2);
        switch(type){
            case("S("):
                return 1;
            case("Z("):
                return 2;
            case("T("):
                return 3;
            case("J("):
                return 4;
            default:
                return 0;
        }
   }
   void iniIniciales(){
       String[] valores = iniciales.getText().replaceAll("\\s+", "").split(",");
       for (int i = 0; i < valores.length; i++) {
           registros[i] = Integer.parseInt(valores[i]);
        
       }
   }
   void anulArgs(int[]arg){
       for (int i = 0; i < arg.length; i++) {
           arg[i] = 0;
        
       }
   }

}
