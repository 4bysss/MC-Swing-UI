import javax.swing.FocusManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.Arrays;

public class cifraVernam {
    private JFrame ventana;
    private SpringLayout layout;
    private JComboBox<String> claves;
    private JButton encriptar, limpiar;
    private JTextField Tplano, Tecncrip,pssw;
    cifraVernam(){
        int ancho = 600;
        int alto = 600;
        
        ventana = new JFrame("Cifrado");
        
        layout = new SpringLayout();

        ventana.setLayout(layout);
        ventana.setSize(ancho, alto);
        ventana.setResizable(true);
        ventana.setVisible(true);


        //Inicializamos todos los elementos

        String[] keys = {"6","9","57","339","747","997"};
        claves = new JComboBox<>(keys);

        encriptar = new JButton("Encriptar");
        limpiar = new JButton("Limpiar");

        Tplano = new JTextField("Texto plano");
        Tecncrip = new JTextField("Texto encriptado");
        pssw = new JTextField("Password");
            
        Tplano.setPreferredSize(new Dimension(200, 30));
        Tecncrip.setPreferredSize(new Dimension(200, 30));
        pssw.setPreferredSize(new Dimension(200, 30));
        
        Tplano.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent arg0) {
                if(Tplano.getText().equals("Texto plano")){
                    Tplano.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent arg0) {
                if(Tplano.getText().equals("")){
                    Tplano.setText("Texto plano");
                }
            }
        });

        Tecncrip.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent arg0) {
                if(Tecncrip.getText().equals("Texto encriptado")){
                    Tecncrip.setText("");
                }
            }
            public void focusLost(FocusEvent arg0) {
                if(Tecncrip.getText().equals("")){
                    Tecncrip.setText("Texto encriptado");
                }
            };
        });

        pssw.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent arg0) {
                if(pssw.getText().equals("Password")){
                    pssw.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent arg0) {
                if(pssw.getText().equals("")){
                    pssw.setText("Password");
                }
            }
        });

        ventana.add(claves);
        ventana.add(encriptar);
        ventana.add(limpiar);
        ventana.add(pssw);
        ventana.add(Tplano);
        ventana.add(Tecncrip);


        layout.putConstraint(SpringLayout.WEST, Tplano, 400, SpringLayout.EAST, ventana);
        layout.putConstraint(SpringLayout.NORTH, Tplano, 400, SpringLayout.NORTH, ventana);
        
        
        layout.putConstraint(SpringLayout.EAST, Tecncrip,400, SpringLayout.WEST, Tplano);
        layout.putConstraint(SpringLayout.NORTH, Tecncrip, 0, SpringLayout.NORTH, Tplano);


        layout.putConstraint(SpringLayout.WEST, pssw,0, SpringLayout.WEST, Tplano);
        layout.putConstraint(SpringLayout.NORTH, pssw, 15, SpringLayout.SOUTH, Tplano);


        layout.putConstraint(SpringLayout.WEST, encriptar,0, SpringLayout.WEST, pssw);
        layout.putConstraint(SpringLayout.NORTH, encriptar, 15, SpringLayout.SOUTH, pssw);


        layout.putConstraint(SpringLayout.WEST, limpiar,0, SpringLayout.WEST, encriptar);
        layout.putConstraint(SpringLayout.NORTH, limpiar, 15, SpringLayout.SOUTH, encriptar);


        layout.putConstraint(SpringLayout.WEST, claves,0, SpringLayout.WEST, limpiar);
        layout.putConstraint(SpringLayout.NORTH, claves, 15, SpringLayout.SOUTH, limpiar);

        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
               pssw.setText("Password");
               Tplano.setText("Texto plano");
               Tecncrip.setText("Texto encriptado");
            }
        });
        encriptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SwingWorker w = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        long[] clavebin = new long[30];
                        conversorKesimo(Integer.parseInt((String)claves.getSelectedItem()), "2", clavebin);
                        String passw = pssw.getText();
                        String TplanoS = Tplano.getText();
                        String TecncripS = new String();
                        TecncripS = encripta(TplanoS, passw, TecncripS, clavebin);
                        Tecncrip.setText(TecncripS);
                        return null;
                    }

                };
                w.execute();
            }
        });




        
        






   } 
   public String encripta(String Tplano, String pssw, String Tecncrip,long[]clavebin){
        String binarioT = new String(); 
        String binarioP = new String(); 
        String binarioE = new String(); 
        System.out.println("Entra");
        binarioT  = stringAbinario(Tplano, binarioT);
        binarioP  = stringAbinario(pssw, binarioP);
        int[] binarioTint = new int[binarioT.length()];
        int[] clave = new int[512];
        int[] clave2 = new int[512];
        int[] codificador = new int[512];
        System.out.println("Tenemmos"+binarioP+"Y tambien " + binarioT);
        for (int i = 0; i < 512; i++) {
            if(i<binarioP.length()){
                clave[i] = Character.getNumericValue(binarioP.charAt(i));
            }
            if(i<binarioT.length()){
                binarioTint[i] = Character.getNumericValue(binarioT.charAt(i));
            }
        }
        System.out.println("pitocorto");
        codificador = kColores(clave, clave2, clavebin,binarioT.length(), 2, true, 1);
        System.out.println("El codificador es: "+Arrays.toString(codificador));
        System.out.println("El codbon es: "+Arrays.toString(clavebin));
        for (int i = 0; i < binarioT.length(); i++) {
            System.out.println("inbucle");
            binarioE += Integer.toString(miXOR(binarioTint[i],codificador[i]));
        }
        System.out.println("postbucle");
        Tecncrip = binarioAstring(Tecncrip, binarioE);
        System.out.println(binarioE);
        return Tecncrip;
        


   };



   public int miXOR(int a, int b){
       if(a==b){return 0;}
       else{return 1;}
   };


   public String stringAbinario(String Tplano,String binario){

        String aux = new String(); 
        int[] ASCIIindex = new int[Tplano.length()];

        //Almacenamos los indices de cada uno de los caracteres del textoo plano
        for (int i = 0; i < Tplano.length(); i++) {
            ASCIIindex[i] = Tplano.charAt(i);
        }
        System.out.println(Arrays.toString(ASCIIindex));

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < Tplano.length(); i++) {
            aux = Integer.toBinaryString(ASCIIindex[i]); 
            if(aux.length()<8){
                for (int j = aux.length(); j< 8; j++) {
                    aux = "0" + aux;                   
                }
            }


            buffer.append(aux);
        }

        binario = buffer.toString().replace(" ", "");
        return binario;

   };

   public String binarioAstring(String Tplano,String binario){
        
        int codigoChar;
        for (int i = 0; i < binario.length(); i+=8) {
            System.out.println("hola");
            codigoChar = Integer.parseInt(binario.substring(i, i+8),2);
            System.out.println(codigoChar);
            System.out.println("adio");
            Tplano += Character.toString(codigoChar);
        }
        return Tplano;
   };
    private static int[] kColores(int[]arr1,int[]arr2,long[]claveBin,int generaciones,int nCol,boolean cFront,int Rvecinos){
        int suma = 0;
        int[] codificador = new int[generaciones];
        for (int i = 0; i < generaciones; i++) {
            
            for (int j = 0; j < arr1.length; j++) {
                if(cFront){
                    for (int k = 0; k <= Rvecinos; k++) {
                        suma += arr1[(k+j)%claveBin.length];
                    }
                    for(int k=1,aux = 0;k <= Rvecinos && aux < Rvecinos ;k++,aux++){
                        if(j-k<0){
                            k = -(arr1.length - j - 1);
                        }
                        suma += arr1[j-k]%claveBin.length;
                    }
                }
                else{
                    for (int k = 0; k <= Rvecinos && k+j < arr1.length; k++) {
                        suma += arr1[k+j]%arr1.length;
                    }
                    for(int k=1;k <= Rvecinos && j-k>=0;k++){
                        suma += arr1[k+j]%arr1.length;
                    }
                }
                arr2[j] = (int)claveBin[suma];
                suma = 0;
            }
            codificador[i] = arr1[256];

            /*for (int z = 0; z < arr1.length; z++) {
               arr1[z] = arr2[z]; 
            }*/
            arr1 = Arrays.copyOf(arr2, arr2.length);
        }
            return codificador;

    }



    private static void conversorKesimo(long clave, String nCol,long[] inputOutput){
        long[] Arraux = new long[inputOutput.length];
        long aux;
        int i;
        switch (nCol) {
            case "2":
                aux = clave;
                i = 0;
                while((aux)>0){
                    Arraux[i]=aux%2;
                    i++;
                    aux = aux/2;

                }
                for (int j = 0; j < Arraux.length; j++) {
                    inputOutput[j]=Arraux[j];
                }
                break;

        }
    } 


}
