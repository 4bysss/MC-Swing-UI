import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.stream.Collectors;
public class urmInterpreter {
    private JFrame frame;
    private SpringLayout layout;
    private JTextArea code, debug, output;
    private JButton ejecutar,limpiar;
    private JMenuBar barra;
    private JMenu Archivo;
    private JMenuItem Cargar,Guardar;

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


       ejecutar = new JButton("Ejecutar");


       code.setPreferredSize(new Dimension(720,720));
       debug.setPreferredSize(new Dimension(360,720));
       output.setPreferredSize(new Dimension(50,25));


       code.setLineWrap(true);
       debug.setLineWrap(true);
       output.setLineWrap(true);
        

       layout.putConstraint(SpringLayout.NORTH, code, 150, SpringLayout.NORTH, frame);
       layout.putConstraint(SpringLayout.WEST, code, 300, SpringLayout.WEST, frame);


       layout.putConstraint(SpringLayout.NORTH, debug, 0, SpringLayout.NORTH, code);
       layout.putConstraint(SpringLayout.WEST, debug, 15, SpringLayout.EAST, code);


       layout.putConstraint(SpringLayout.NORTH, ejecutar, 15, SpringLayout.SOUTH, code);
       layout.putConstraint(SpringLayout.WEST, ejecutar, 0, SpringLayout.WEST, code);


       layout.putConstraint(SpringLayout.NORTH, output, 0, SpringLayout.NORTH, ejecutar);
       layout.putConstraint(SpringLayout.WEST, output, 15, SpringLayout.EAST, ejecutar);
       
       
       frame.add(code);
       frame.add(debug);
       frame.add(output);
       frame.add(ejecutar);


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


       




   } 
}
