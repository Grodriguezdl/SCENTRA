package Vista;


import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
//Gabriel Ricardo Rodriguez de León
public class Progreso extends JFrame implements ActionListener {

    JProgressBar Barra;
    JLabel Porcentaje;
    JLabel pR;

    public Progreso() {
       try {
            URL iconURL = getClass().getResource("/Imagenes/logomini.png");

            if (iconURL != null) {
                Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
                setIconImage(icon);
            } else {
                System.err.println("No se encontró el recurso del icono para la barra de tareas: /Imagenes/logomini.png");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen del icono para la barra de tareas: " + e.getMessage());
            e.printStackTrace();
        }
        this.setUndecorated(true);
        this.getContentPane().setBackground(Color.decode("#F7F8FC"));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        obj();
        agr();
        posicionar();
        //Tamaño maximo-inicio
        this.setSize(750, 500);
        //Final- copia pega en mis otras ventanas
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(100);
                Barra.setValue(i);
                Porcentaje.setText(i + "%");
                if (Barra.getValue() == 100) {
                    new Inicio_de_sesion();
                    dispose();
                }
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        new Progreso();
    }

    public void agr() {
        this.add(Barra);
        this.add(Porcentaje);
        this.add(pR);
    }

    public void posicionar() {
        Barra.setBounds(100, 250, 530, 25);
        Porcentaje.setBounds(210, 300, 300, 25);
        Porcentaje.setHorizontalAlignment(JLabel.CENTER);
        
        try {
            // Obtener la URL del recurso desde el classpath
            URL imageUrl = getClass().getResource("/Imagenes/logo.png");

            if (imageUrl != null) {
                ImageIcon usu = new ImageIcon(imageUrl);
               
                pR.setBounds(270, 45, 200, 210); 
                
                // Escalar la imagen al tamaño del JLabel
                Image scaledImage = usu.getImage().getScaledInstance(
                    pR.getWidth(),
                    pR.getHeight(),
                    Image.SCALE_SMOOTH
                );
                pR.setIcon(new ImageIcon(scaledImage));
            } else {
                System.err.println("Error: La imagen 'logo.png' no fue encontrada en el paquete /Imagenes.");
                pR.setText("Imagen no encontrada"); // Texto de fallback
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza completa para depuración
        }
        
    }

    public void obj() {
        Barra = new JProgressBar();
        Barra.setValue(0);
        Porcentaje = new JLabel("--%");
        Porcentaje.setForeground(Color.decode("#586875"));
        pR = new JLabel();
    }

   
    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
