import java.lang.reflect.Constructor;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * MiInterfazDeUsuario
 */
public class MiInterfazDeUsuario {
    private JFrame ventana;
    private JButton boton1,boton2,boton3;
    private JMenuBar barraMenu;
    private JMenu menu1,menu2,menu3;
    private JMenuItem[] itemsmenu1;
    private SpringLayout lo;
    private JPanel PanelIzq, PanelDer;
    MiInterfazDeUsuario(){
        //Imagen del mono
        ImageIcon ImagenMono = new ImageIcon(new ImageIcon("../P1/cover4.jpg").getImage().getScaledInstance(720, 720, Image.SCALE_SMOOTH));
        JLabel EtiquetaMono = new JLabel(ImagenMono);

        //Creacion de la ventana
        ventana = new JFrame("Mi ventana molona");
        ventana.setSize(1024, 1024);
        lo = new SpringLayout();
        ventana.setLayout(lo);
        ventana.setResizable(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creacion de paneles
        JPanel ContDer = new JPanel();
        ContDer.setLayout(lo);


        //Creacion de los botones
        boton1 = new JButton("No hago nada");
        boton2 = new JButton("Yo tampoco");
        boton3 = new JButton("Soy Absolutanmente inutil");
        

        //Colocamos los botones
        lo.putConstraint(SpringLayout.EAST, boton1, -150, SpringLayout.EAST, ContDer);
        lo.putConstraint(SpringLayout.NORTH, boton1, -150, SpringLayout.VERTICAL_CENTER, ContDer);


        lo.putConstraint(SpringLayout.EAST, boton2, 0, SpringLayout.EAST, boton1);
        lo.putConstraint(SpringLayout.SOUTH, boton2, 50, SpringLayout.SOUTH, boton1);



        lo.putConstraint(SpringLayout.EAST, boton3, 0, SpringLayout.EAST, boton2);
        lo.putConstraint(SpringLayout.SOUTH, boton3, 50, SpringLayout.SOUTH, boton2);


        lo.putConstraint(SpringLayout.EAST, EtiquetaMono, -800, SpringLayout.EAST,ContDer );
        lo.putConstraint(SpringLayout.NORTH, EtiquetaMono, 0, SpringLayout.SOUTH,ventana );
        //lo.putConstraint(SpringLayout.SOUTH, EtiquetaMono, 0, SpringLayout.SOUTH, ventana);
       

        ContDer.add(boton1);
        ContDer.add(boton2);
        ContDer.add(boton3);
        ContDer.add(EtiquetaMono);
        ventana.setContentPane(ContDer);


        //Creacion barra con los menus
        barraMenu = new JMenuBar();
        ventana.setJMenuBar(barraMenu);


        //Creacion de los menus de la barra
        menu1 = new JMenu("Dolor");
        menu2 = new JMenu("Archivo");
        menu3 = new JMenu("Ayuda");
        barraMenu.add(menu1);
        barraMenu.add(menu2);
        barraMenu.add(menu3);

        //Items Menu 1 
        itemsmenu1 = new JMenuItem[4];
        itemsmenu1[0] = new JMenuItem("Ojala");
        itemsmenu1[1] = new JMenuItem("haber");
        itemsmenu1[2] = new JMenuItem("aprobado");
        itemsmenu1[3] = new JMenuItem("PCTR");
        for (int i = 0; i < itemsmenu1.length; i++) {
            menu1.add(itemsmenu1[i]);
        }
        //Items Menu 2
        JMenuItem ICargar = new JMenuItem("Cargar");
        JMenuItem IGuardar = new JMenuItem("Guardar");
        JMenuItem IGuardarComo = new JMenuItem("Guardar como");
        menu2.add(ICargar);
        menu2.add(IGuardar);
        menu2.add(IGuardarComo);
        //Items Menu 3
        JMenuItem IDocumentacion = new JMenuItem("Documentacion");
        JMenuItem ISocorro = new JMenuItem("Generador de puntos aleatorios");
        JMenuItem IAutomataCelular = new JMenuItem("Automata celular");
        //Submenu al menu 3
        JMenu IAcercaDe = new JMenu("Acerca de");
        JMenuItem INosotros = new JMenuItem("Nosotros");
        JMenuItem IVosotros = new JMenuItem("Vosotros");
        JMenuItem INadie = new JMenuItem("Nadie");
        IAcercaDe.add(INosotros);
        IAcercaDe.add(IVosotros);
        IAcercaDe.add(INadie);

        menu3.add(IDocumentacion);
        menu3.add(ISocorro);
        menu3.add(IAcercaDe);
        menu3.add(IAutomataCelular);

        //Eventos del sistema
        boton1.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Boton1");
        }});


        boton2.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Boton2");
        }});


        boton3.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Boton3");
        }});


        itemsmenu1[0].addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Boton1Menu1");
        }});


        itemsmenu1[1].addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Boton2Menu1");
        }});


        itemsmenu1[2].addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Boton3Menu1");
        }});


        itemsmenu1[3].addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Boton4Menu1");
        }});


        INadie.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Nadie");
        }});


        INosotros.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Nosotros");
        }});


        IVosotros.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Vosotros");
        }});


        ICargar.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Cargar");
        }});


        IGuardar.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Guardar");
        }});


        IGuardarComo.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Guardar como");
        }});


        IDocumentacion.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            mostrarVentana("Documentacion");
        }});


        ISocorro.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent arg0) {
            randomGenerator rG = new randomGenerator();
            rG.execute();
        }});

        IAutomataCelular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ca1DSim AutomataCelularGui = new ca1DSim();
                
            }
        });


        
    }


    private void mostrarVentana(String title){

        ventana = new JFrame(title);
        ventana.setSize(800, 800);
        lo = new SpringLayout();
        ventana.setLayout(lo);
        ventana.setResizable(true);
        ventana.setVisible(true);

    }



    public static void main(String[] args) {

        MiInterfazDeUsuario v = new MiInterfazDeUsuario();
        
        v.ventana.setVisible(true); 
    }
}
