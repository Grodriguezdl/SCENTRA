package Vista;

import ManejoBase.Conexion;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.sql.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.JTextComponent;

public class Registro extends JFrame implements ActionListener {
//Gabriel Ricardo Rodriguez de León

    boolean a = true;
    char i = '*';
    JRadioButton radio1, radio2, radio3;
    JLabel labelimgact; //cual label se ve
    JLabel siglabel;    // el label q se desvanece
    Timer tiempodes;
    int vis = 0;
    private static final int vel = 140;
    private static final int entretiempo = 30;
    JLayeredPane imalp;
    ButtonGroup btg;
    JLabel rolim;
    JLabel imgusuario;
    JLabel imgcontra;
    JLabel Bienvenido1;//Bienvenidos...
    JLabel Bienvenido2;
    JPanel lateral;//Panel
    JLabel pR;//Logo de scentra
    JTextField Usuario;//Aun no lo uso
    JPasswordField Contrasena;//Aun no lo uso
    JLabel sub;
    JLabel usuario;
    JLabel contraseña;
    JLabel registro;
    JLabel Rols;
    JComboBox Rol;
    Font b = new Font("Segoe UI", Font.BOLD, 50);//Fuente para el Bienvenidos
    Font s = new Font("Segoe UI", Font.BOLD, 24);
    Font bt = new Font("Segoe UI", Font.BOLD, 14);
    Font nm = new Font("Segoe UI", Font.PLAIN, 14);

    JButton iniciar;
    //para la barrita de arriba
    JButton x;
    JButton m;
    private String[] rutasImagenesFade = {"r1.png", "r2.png", "r3.png"};
    private String imagenActualFadePath = "";

    public Registro() {
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
        this.setSize(1366, 768);

        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    public void obj() {
        lbl();
        pnl();
        Txt();
        Btn();
        imagenes();
        barritaarriba();
    }

    public void barritaarriba() {
        x = new JButton("x");
        m = new JButton("-");
        lateral.add(m);
        lateral.add(x);
        x.setBackground(Color.decode("#6A7790"));
        m.setBackground(Color.decode("#6A7790"));
        x.setForeground(Color.WHITE);
        m.setForeground(Color.WHITE);
        x.setBounds(515, 0, 50, 25);
        m.setBounds(465, 0, 50, 25);
        x.setBorder(null);
        m.setBorder(null);
        m.addActionListener(this);
        x.addActionListener(this);
    }

    public void pnl() {
        lateral = new JPanel();
        lateral.setBackground(Color.decode("#6A7790"));
        lateral.setLayout(null);
    }

    public static void main(String[] args) {
        new Registro();
    }

    public void imagenes() {
        imalp = new JLayeredPane();
        imalp.setBounds(100, 150, 400, 400);
        lateral.add(imalp);
        labelimgact = new JLabel();
        siglabel = new JLabel();
        labelimgact.setBounds(0, 0, 400, 400);
        siglabel.setBounds(0, 0, 400, 400);
        imalp.add(labelimgact, JLayeredPane.DEFAULT_LAYER);
        imalp.add(siglabel, JLayeredPane.PALETTE_LAYER);
        labelimgact.setVisible(false);
        siglabel.setVisible(false);
        //radiobuttons
        radio1 = new JRadioButton();
        radio2 = new JRadioButton();
        radio3 = new JRadioButton();
        radio1.setOpaque(false);
        radio2.setOpaque(false);
        radio3.setOpaque(false);
        btg = new ButtonGroup();
        btg.add(radio1);
        btg.add(radio2);
        btg.add(radio3);
        radio1.setBounds(240, 640, 50, 50);
        radio2.setBounds(290, 640, 50, 50);
        radio3.setBounds(340, 640, 50, 50);
        lateral.add(radio1);
        lateral.add(radio2);
        lateral.add(radio3);
        radio1.addActionListener(e -> mostrarlaimagen(0));
        radio2.addActionListener(e -> mostrarlaimagen(1));
        radio3.addActionListener(e -> mostrarlaimagen(2));
        radio1.setSelected(true);
        mostrarlaimagen(0);

        rolim = new JLabel();
        imgusuario = new JLabel();
        imgcontra = new JLabel();
        add(imgusuario);
        add(imgcontra);
        add(rolim);
        imgusuario.setBounds(700, 295, 20, 20);
        imgcontra.setBounds(700, 375, 20, 20);
        rolim.setBounds(700, 455, 20, 20);

        try {
            URL rolIconUrl = getClass().getResource("/Imagenes/rol.png");
            if (rolIconUrl != null) {
                ImageIcon irol = new ImageIcon(rolIconUrl);
                rolim.setIcon(new ImageIcon(irol.getImage().getScaledInstance(rolim.getWidth(), rolim.getHeight(), Image.SCALE_SMOOTH)));
            } else {
                System.err.println("Error: Imagen '/Imagenes/rol.png' no encontrada.");
            }

            URL userIconUrl = getClass().getResource("/Imagenes/usuario.png");
            if (userIconUrl != null) {
                ImageIcon ius = new ImageIcon(userIconUrl);
                imgusuario.setIcon(new ImageIcon(ius.getImage().getScaledInstance(imgusuario.getWidth(), imgusuario.getHeight(), Image.SCALE_SMOOTH)));
            } else {
                System.err.println("Error: Imagen '/Imagenes/usuario.png' no encontrada.");
            }

            URL passIconUrl = getClass().getResource("/Imagenes/contra.png");
            if (passIconUrl != null) {
                ImageIcon ics = new ImageIcon(passIconUrl);
                imgcontra.setIcon(new ImageIcon(ics.getImage().getScaledInstance(imgcontra.getWidth(), imgcontra.getHeight(), Image.SCALE_SMOOTH)));
            } else {
                System.err.println("Error: Imagen '/Imagenes/contra.png' no encontrada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error general al cargar iconos en 'imagenes()': " + e.getMessage());
        }

        imgcontra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {

                    if (a) {
                        Contrasena.setEchoChar((char) 0);
                        a = false;
                    } else {
                        Contrasena.setEchoChar(i);
                        a = true;
                    }
                }
            }
        });
    }

    public void mostrarlaimagen(int numi) {
        final String nombreImagen;
        if (numi >= 0 && numi < rutasImagenesFade.length) {
            nombreImagen = rutasImagenesFade[numi];
        } else {
            return;
        }

        URL imgUrl = getClass().getResource("/Imagenes/" + nombreImagen);
        if (imgUrl == null) {
            System.err.println("Error: La imagen no se encontró en la ruta de recursos: /Imagenes/" + nombreImagen);
            return;
        }

        if (nombreImagen.equals(imagenActualFadePath) && labelimgact.isVisible()) {
            if (tiempodes != null && tiempodes.isRunning()) {
                tiempodes.stop();
            }
            return;
        }

        final ImageIcon nuevaIcono = new ImageIcon(imgUrl);
        final Image imagenEscalada = nuevaIcono.getImage().getScaledInstance(imalp.getWidth(), imalp.getHeight(), Image.SCALE_SMOOTH);

        if (!labelimgact.isVisible() || labelimgact.getIcon() == null || imagenActualFadePath.isEmpty()) {
            labelimgact.setIcon(new ImageIcon(imagenEscalada));
            labelimgact.setVisible(true);
            siglabel.setVisible(false);
            imagenActualFadePath = nombreImagen;
            if (tiempodes != null && tiempodes.isRunning()) {
                tiempodes.stop();
            }
            return;
        }

        siglabel.setIcon(new ImageIcon(imagenEscalada));
        siglabel.setVisible(true);
        imalp.setLayer(siglabel, JLayeredPane.PALETTE_LAYER);

        if (tiempodes != null && tiempodes.isRunning()) {
            tiempodes.stop();
        }

        vis = 0;
        tiempodes = new Timer(entretiempo, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vis += vel;
                if (vis >= 255) {
                    vis = 255;
                    tiempodes.stop();
                    labelimgact.setIcon(new ImageIcon(imagenEscalada));
                    labelimgact.setVisible(true);
                    siglabel.setVisible(false);
                    siglabel.setIcon(null);
                    imagenActualFadePath = nombreImagen;
                    return;
                }

                if (imagenEscalada != null) {
                    BufferedImage transparentImage = new BufferedImage(
                            siglabel.getWidth(), siglabel.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = transparentImage.createGraphics();
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) vis / 255f));

                    g2d.drawImage(imagenEscalada, 0, 0, siglabel.getWidth(), siglabel.getHeight(), null);
                    g2d.dispose();
                    siglabel.setIcon(new ImageIcon(transparentImage));
                }
            }
        });
        tiempodes.start();
    }

    public void agr() {
        this.add(lateral);
        this.add(sub);
        this.add(Bienvenido1);
        this.add(Bienvenido2);
        this.add(pR);
        this.add(usuario);
        this.add(contraseña);
        this.add(Usuario);
        this.add(Contrasena);
        this.add(iniciar);
        this.add(registro);
        this.add(Rols);
        this.add(Rol);
    }

    public void Txt() {
        Usuario = new JTextField(10);
     
        Contrasena = new JPasswordField(8);
        Contrasena.setToolTipText("Nota: La contraseña debe tener más "
                + "de 6 caracteres, mayúsculas y "
                + " números.");
        UIManager.put("ToolTip.font", (nm));
        
        Usuario.setHorizontalAlignment(SwingConstants.CENTER);
        Contrasena.setHorizontalAlignment(SwingConstants.CENTER);
        Rol = new JComboBox();
        Rol.addItem("Administrador");
        Rol.addItem("Empleado");
        Rol.setBackground(Color.WHITE);
        Contrasena.setEchoChar(i);  
        permitirletras(Usuario);
        validarcontra(Contrasena);
        deshabilitarCopiarPegar(Usuario);
        deshabilitarCopiarPegar(Contrasena);
    }

    private void deshabilitarCopiarPegar(JTextComponent textComponent) {
        // Deshabilitar atajos de teclado (Ctrl+C, Ctrl+V, Ctrl+X)
        InputMap inputMap = textComponent.getInputMap();
        ActionMap actionMap = textComponent.getActionMap();

        String[] disabledActions = {"copy", "cut", "paste"};
        for (String action : disabledActions) {
            inputMap.put(KeyStroke.getKeyStroke(action.toUpperCase()), "none");
            actionMap.put("none", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // No hacer nada
                }
            });
        }
        textComponent.setComponentPopupMenu(null);
        textComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    e.consume();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    e.consume();
                }
            }
        });

        textComponent.setTransferHandler(null);
    }
    
    public void permitirletras(JTextField campo) {

        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String letra = String.valueOf(c);
                if (!letra.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ .-]")) {
                    e.consume();
                }
                 if (campo.getText().length() > 20) {
                    e.consume();
                }
            }
        });
    }

        public void validarcontra(JTextField campo) {

        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String letra = String.valueOf(c);
                if (!letra.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9.-]")) {
                    e.consume();
                }
                if (campo.getText().length() > 20) {
                    e.consume();
                }
            }
        });
    }
    public void Btn() {
        iniciar = new JButton("Continuar");
        iniciar.setBackground(Color.decode("#6A7793"));
        iniciar.setForeground(Color.white);
        iniciar.setFont(bt);
        iniciar.setFocusPainted(false);
        iniciar.setBorderPainted(false);
        iniciar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iniciar.setOpaque(false);
        iniciar.setContentAreaFilled(false);
        iniciar.addActionListener(this);
        iniciar.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(iniciar.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 40, 40);
                super.paint(g, c);
                g2.dispose();
            }
        });
    }

    public void posicionar() {
        lateral.setBounds(800, 0, 566, 768);
        Bienvenido1.setBounds(50, 110, 600, 50);
        Bienvenido1.setHorizontalAlignment(JLabel.LEFT);
        Bienvenido2.setBounds(50, 160, 600, 50);
        Bienvenido2.setHorizontalAlignment(JLabel.LEFT);
        sub.setBounds(50, 210, 600, 50);
        usuario.setBounds(50, 250, 600, 50);
        Usuario.setBounds(50, 290, 640, 30);
        contraseña.setBounds(50, 330, 600, 50);
        Contrasena.setBounds(50, 370, 640, 30);
        registro.setBounds(50, 480, 640, 30);
        iniciar.setBounds(280, 520, 200, 40);
        pR.setBounds(30, 10, 110, 110);

        try {
            URL logoUrl = getClass().getResource("/Imagenes/logo.png");
            if (logoUrl != null) {
                ImageIcon usu = new ImageIcon(logoUrl);
                pR.setIcon(new ImageIcon(usu.getImage().getScaledInstance(pR.getWidth(), pR.getHeight(), Image.SCALE_SMOOTH)));
            } else {
                System.err.println("Error: Imagen '/Imagenes/logo.png' no encontrada en posicionar().");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error general al cargar el logo en 'posicionar()': " + e.getMessage());
        }

        Rols.setBounds(50, 410, 600, 50);
        Rol.setBounds(50, 450, 640, 30);
    }

    public void lbl() {
        this.setResizable(false);
        Bienvenido1 = new JLabel("Todo a la mano, ");
        Bienvenido2 = new JLabel("todo a tu modo");
        sub = new JLabel("Registrate");
        Bienvenido1.setFont(b);
        Bienvenido2.setFont(b);
        sub.setFont(s);
        Bienvenido1.setForeground(Color.decode("#586875"));
        Bienvenido2.setForeground(Color.decode("#586875"));
        sub.setForeground(Color.decode("#586875"));
        registro = new JLabel("¿Ya tienes cuenta? Inicia sesión");
        registro.setForeground(Color.decode("#586875"));
        registro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Inicio_de_sesion r = new Inicio_de_sesion();
                dispose();
            }
        });
        pR = new JLabel();
        usuario = new JLabel("Usuario");
        contraseña = new JLabel("Contraseña");
        usuario.setForeground(Color.decode("#B4A8AA"));
        contraseña.setForeground(Color.decode("#B4A8AA"));
        Rols = new JLabel("Rol");
        Rols.setForeground(Color.decode("#B4A8AA"));

    }

    //Alison del Rosario Vicente Coroy
    public boolean validarcampos(JTextField nombre, JPasswordField contraseña) {
        String usuario = nombre.getText();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            Conexion con = new Conexion();
            conn = con.getConnection();

            String sql = "Select Nombre From Usuarios Where Nombre = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Nombre de usuario ya existente, ingrese nuevamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                nombre.setText(" ");
                contraseña.setText(" ");
                return true;
            } 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en SQL");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == x) {
            System.exit(0);
        }
        if (e.getSource() == m) {
            this.setState(JFrame.ICONIFIED);
        }
        // Si se presiona el botón iniciar
        if (e.getSource() == iniciar) {
            // Obtiene los textos ingresados por el usuario
            String nombreUsuario = Usuario.getText().trim();
            String contrasena = new String(Contrasena.getPassword());
            String rol = (String) Rol.getSelectedItem();

            // Verifica que los campos no estén en blanco antes de ingresar
            if (nombreUsuario.isEmpty() || contrasena.isEmpty() || rol == null || rol.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los datos para continuar el registro.");
                return;
            }
          if(validarcampos(Usuario,Contrasena)){
              return;
          }
            if (contrasena.length() > 6) {
                boolean May = false;
                boolean Num = false;
                char l;
                for (int j = 0; j < contrasena.length(); j++) {
                    l = contrasena.charAt(j);
                    if (Character.isDigit(l)) {
                        Num = true;
                    }
                    if (Character.isUpperCase(l)) {
                        May = true;
                    }
                }
                if (Num == true && May == true) {
                    Connection conn = null;
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    try {
                        Conexion con = new Conexion();
                        conn = con.getConnection();
                        conn.setAutoCommit(false);

                        int crearId = 1;
                        Statement st = null;
                        try {
                            st = conn.createStatement();
                            rs = st.executeQuery("SELECT MAX(idUsuarios) FROM Usuarios");

                            if (rs.next()) {
                                int ultimoId = rs.getInt(1);
                                if (ultimoId > 0) {
                                    crearId = ultimoId + 1;
                                }
                            }
                        } finally {
                            if (rs != null) {
                                try {
                                    rs.close();
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            if (st != null) {
                                try {
                                    st.close();
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }

                        String sql = "INSERT INTO Usuarios (idUsuarios, nombre, rol, contrasena, idEmpresa, Estado_Union_Empresa) VALUES (?, ?, ?, ?, 1, 'Pendiente')";
                        stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, crearId);
                        stmt.setString(2, nombreUsuario);
                        stmt.setString(3, rol);
                        stmt.setString(4, contrasena);

                        int rowsAffected = stmt.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "Primer paso: Completado.", "Completo", JOptionPane.INFORMATION_MESSAGE);

                            if ("Empleado".equals(rol)) {
                                new Unirse_a_empresa(crearId, conn).setVisible(true);
                                this.dispose();
                            } else if ("Administrador".equals(rol)) {
                                new Unirse_admin(crearId, conn).setVisible(true);
                                this.dispose();
                            }
                        } else {
                            throw new SQLException("No se pudo insertar el usuario en la base de datos.");
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + ex.getMessage(), "Error de Registro", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();

                        if (conn != null) {
                            try {
                                conn.rollback();
                                System.err.println("Transacción revertida debido a un error.");
                            } catch (SQLException rollbackEx) {
                                System.err.println("Error al intentar revertir la transacción: " + rollbackEx.getMessage());
                                rollbackEx.printStackTrace();
                            }
                        }
                    } finally {
                        try {
                            if (stmt != null) {
                                stmt.close();
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "El usuario o contraseña no cumple los requerimientos.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "La contraseña debe de tener al menos 6 caracteres, con números y mayúsculas incluidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
