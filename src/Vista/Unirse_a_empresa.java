package Vista;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.net.URL;
import javax.swing.text.JTextComponent;

//Gabriel Ricardo Rodriguez de León
public class Unirse_a_empresa extends JFrame implements ActionListener {

    JPanel menuItemsPanel;
    JButton btnUnirseEmpresa;
    private boolean menuExpandido = false;
    private final int ANCHO_MINIMO_MENU = 70;
    private final int ANCHO_MAXIMO_MENU = 250;
    private Timer animacionTimer;
    private final int VELOCIDAD_ANIMACION = 5;
    private final int PASO_ANIMACION = 15;
    private ImageIcon originalLogoIcon;
    private final int LOGO_CONTRAIDO_ALTO = 60;
    private final int LOGO_EXPANDIDO_ALTO = 100;
    JLabel pR2;
    JPanel lateral;
    Font bt = new Font("Segoe UI", Font.BOLD, 14);
    JSeparator separatorMenu;
    JLabel Titulo;
    JSeparator linea;
    Font b = new Font("Segoe UI", Font.BOLD, 50);
    //de aqui en adelante se trabaja lo de unirse a una empresa
    JPanel unirsepnl;
    JTextField cod;
    JButton btnunirse;
    JButton cancelar;
    JSeparator lineaaboton;
    JSeparator lineaunirse;
    JButton X;
    Font sb = new Font("Segoe UI", Font.BOLD, 30);
    JLabel subunir;
    private Connection conn;
    private int idUsuario;
    //para la barrita de arriba
    JButton x;
    JButton m;

    public Unirse_a_empresa(int idUsuario, Connection conn) {
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
        this.idUsuario = idUsuario;
        this.conn = conn; // La conexión se pasa y se guarda
        this.setResizable(false);
        this.getContentPane().setBackground(Color.decode("#F7F8FC"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1366, 768);
        obj();
        agr();
        posicionar();
        configurarMenuHamburguesa();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    public void obj() {
        lbl();
        pnl();
        Btn();
        img();
        Linea();
        separador();
        txt();
        barritaarriba();
    }

    public void barritaarriba() {
        x = new JButton("x");
        m = new JButton("-");
        this.add(m);
        this.add(x);
        x.setBackground(Color.decode("#F7F8FC"));
        m.setBackground(Color.decode("#F7F8FC"));
        x.setForeground(Color.decode("#6A7790"));
        m.setForeground(Color.decode("#6A7790"));
        x.setBounds(1315, 0, 50, 25);
        m.setBounds(1265, 0, 50, 25);
        x.setBorder(null);
        m.setBorder(null);
        m.addActionListener(this);
        x.addActionListener(this);
    }

    public void lbl() {
        pR2 = new JLabel();
        pR2.setHorizontalAlignment(SwingConstants.CENTER);
        pR2.setVerticalAlignment(SwingConstants.CENTER);
        Titulo = new JLabel("Únete a una empresa");
        Titulo.setFont(b);
        Titulo.setForeground(Color.decode("#6A7793"));
        subunir = new JLabel("Ingresa codigo de la empresa");
        subunir.setForeground(Color.decode("#6A7793"));
        subunir.setFont(sb);

    }

    public void Btn() {
        btnUnirseEmpresa = createMenuItem("Unirse a empresa", "/Imagenes/capa-mas.png");
        btnunirse = new JButton("Ingresar codigo");
        btnunirse.setBackground(Color.decode("#6A7793"));
        btnunirse.setFont(bt);
        btnunirse.setForeground(Color.decode("#FFFFFF"));
        X = new JButton("x");
        X.setBackground(Color.decode("#FFFFFF"));
        X.setForeground(Color.gray);
        X.setBorder(null);
        X.addActionListener(this);
        cancelar = new JButton("Cancelar");
        cancelar.setBackground(Color.decode("#6A7793"));
        cancelar.setFont(bt);
        cancelar.setForeground(Color.decode("#FFFFFF"));
        forma(btnunirse);
        forma(cancelar);

    }

    public void img() {
        try {
            URL imageUrl = getClass().getResource("/Imagenes/logo.png");
            if (imageUrl != null) {
                originalLogoIcon = new ImageIcon(imageUrl);
            } else {
                System.err.println("No se pudo encontrar 'logo.png' en los recursos de /Imagenes.");
                originalLogoIcon = new ImageIcon();
            }
        } catch (Exception e) {
            System.err.println("Error al cargar 'logo.png': " + e.getMessage());
            originalLogoIcon = new ImageIcon();
        }
    }

    public void Linea() {
        separatorMenu = new JSeparator(JSeparator.HORIZONTAL);
        separatorMenu.setForeground(Color.decode("#B4A8AA"));
        separatorMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    }

    public void separador() {
        linea = new JSeparator();
        linea.setBackground(Color.decode("#B4A8AA"));
        lineaaboton = new JSeparator();
        lineaunirse = new JSeparator();
        lineaaboton.setBackground(Color.decode("#586875"));
        lineaunirse.setBackground(Color.decode("#586875"));
    }

    public void posicionar() {
        lateral.setBounds(0, 0, ANCHO_MINIMO_MENU, this.getHeight());
        pR2.setBounds(10, 20, ANCHO_MINIMO_MENU - 20, LOGO_CONTRAIDO_ALTO);
        Actualizartamañoicono();
        menuItemsPanel.setBounds(0, pR2.getY() + pR2.getHeight() + 10,
                ANCHO_MAXIMO_MENU,
                this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));
        Titulo.setBounds(120, 25, 600, 70);
        linea.setBounds(120, 95, 1190, 25);
        unirsepnl.setBounds(400, 250, 540, 270);
        lineaaboton.setBounds(50, 200, 440, 30);
        lineaunirse.setBounds(50, 75, 440, 30);
        btnunirse.setBounds(100, 220, 170, 35);
        cancelar.setBounds(290, 220, 170, 35);
        X.setBounds(490, 0, 50, 30);
        subunir.setBounds(60, 20, 440, 50);
        cod.setBounds(50, 125, 440, 30);
    }

    private void Actualizartamañoicono() {
        if (originalLogoIcon != null && originalLogoIcon.getImage() != null) {
            int labelWidth = pR2.getWidth();
            int labelHeight = pR2.getHeight();

            if (labelWidth > 0 && labelHeight > 0) {
                Image originalImage = originalLogoIcon.getImage();
                int originalWidth = originalImage.getWidth(null);
                int originalHeight = originalImage.getHeight(null);

                double scaleX = (double) labelWidth / originalWidth;
                double scaleY = (double) labelHeight / originalHeight;
                double scale = Math.min(scaleX, scaleY);

                int scaledWidth = (int) (originalWidth * scale);
                int scaledHeight = (int) (originalHeight * scale);

                Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                pR2.setIcon(new ImageIcon(scaledImage));
                pR2.setText("");
            }
        }
    }

    public void forma(JButton btnunirse) {
        btnunirse.setFocusPainted(false);
        btnunirse.setBorderPainted(false);
        btnunirse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnunirse.setOpaque(false);
        btnunirse.setContentAreaFilled(false);
        btnunirse.addActionListener(this);
        btnunirse.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btnunirse.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 40, 40);
                super.paint(g, c);
                g2.dispose();
            }
        });

    }

    public void pnl() {
        lateral = new JPanel();
        lateral.setBackground(Color.decode("#CFD1E0"));
        lateral.setLayout(null);
        menuItemsPanel = new JPanel();
        menuItemsPanel.setBackground(Color.decode("#CFD1E0"));
        menuItemsPanel.setLayout(new BoxLayout(menuItemsPanel, BoxLayout.Y_AXIS));
        menuItemsPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        unirsepnl = new JPanel();
        unirsepnl.setBackground(Color.decode("#FFFFFF"));
        unirsepnl.setVisible(false);
        unirsepnl.setLayout(null);
    }

    public void agr() {
        this.add(lateral);
        this.add(Titulo);
        this.add(linea);
        lateral.add(menuItemsPanel);
        menuItemsPanel.add(Box.createVerticalStrut(20));
        menuItemsPanel.add(btnUnirseEmpresa);
        menuItemsPanel.add(Box.createVerticalGlue());
        setMenuItemsVisibility(false);
        this.add(unirsepnl);
        lateral.add(pR2);
        unirsepnl.add(lineaunirse);
        unirsepnl.add(lineaaboton);
        unirsepnl.add(X);
        unirsepnl.add(subunir);
        unirsepnl.add(cod);
        unirsepnl.add(btnunirse);
        unirsepnl.add(cancelar);

    }

    public void txt() {
        cod = new JTextField();
        permitirletras(cod);
        deshabilitarCopiarPegar(cod);
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
                if (!letra.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ 0-9.-]")) {
                    e.consume();
                }
                if (campo.getText().length() > 10) {
                    e.consume();
                }
            }
        });
    }

    private void configurarMenuHamburguesa() {
        pR2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleMenu();
            }
        });
        animacionTimer = new Timer(VELOCIDAD_ANIMACION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentWidth = lateral.getWidth();
                int targetWidth = menuExpandido ? ANCHO_MAXIMO_MENU : ANCHO_MINIMO_MENU;

                if (currentWidth != targetWidth) {
                    int step = (targetWidth > currentWidth) ? PASO_ANIMACION : -PASO_ANIMACION;
                    int newWidth = currentWidth + step;

                    if ((step > 0 && newWidth > targetWidth) || (step < 0 && newWidth < targetWidth)) {
                        newWidth = targetWidth;
                    }

                    lateral.setSize(newWidth, lateral.getHeight());

                    int targetPR2Height = menuExpandido ? LOGO_EXPANDIDO_ALTO : LOGO_CONTRAIDO_ALTO;
                    pR2.setBounds(10, 20, newWidth - 20, targetPR2Height);

                    menuItemsPanel.setBounds(0, pR2.getY() + pR2.getHeight() + 10,
                            newWidth,
                            Unirse_a_empresa.this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));

                    Actualizartamañoicono();

                    lateral.revalidate();
                    lateral.repaint();

                } else {
                    animacionTimer.stop();
                    setMenuItemsVisibility(menuExpandido);
                }
            }
        });
    }

    private JButton createMenuItem(String text, String iconPath) {
        JButton menuItem = new JButton("   " + text);
        menuItem.setFont(bt);
        menuItem.setForeground(Color.decode("#3C4043"));
        menuItem.setBackground(Color.decode("#CFD1E0"));
        menuItem.setOpaque(true);
        menuItem.setBorderPainted(false);
        menuItem.setFocusPainted(false);
        menuItem.setHorizontalAlignment(SwingConstants.LEFT);
        menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuItem.setMaximumSize(new Dimension(ANCHO_MAXIMO_MENU, 40));
        menuItem.setAlignmentX(Component.LEFT_ALIGNMENT);

        try {
            URL iconURL = getClass().getResource(iconPath);
            if (iconURL != null) {
                ImageIcon originalIcon = new ImageIcon(iconURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                menuItem.setIcon(new ImageIcon(scaledImage));
            } else {
                System.err.println("No se pudo encontrar el icono: " + iconPath + ". Usando icono de respaldo si está disponible.");
                if (originalLogoIcon != null && originalLogoIcon.getImage() != null) {
                    menuItem.setIcon(new ImageIcon(originalLogoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el icono para " + text + ": " + iconPath + " - " + e.getMessage());
            if (originalLogoIcon != null && originalLogoIcon.getImage() != null) {
                menuItem.setIcon(new ImageIcon(originalLogoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            }
        }

        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setBackground(Color.decode("#E0E2E7"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(Color.decode("#CFD1E0"));
            }
        });

        menuItem.addActionListener(this);
        return menuItem;
    }

    private void setMenuItemsVisibility(boolean visible) {
        if (visible) {
            pR2.setBounds(10, 20, ANCHO_MAXIMO_MENU - 20, LOGO_EXPANDIDO_ALTO);
            Actualizartamañoicono();

            menuItemsPanel.setBounds(0, pR2.getY() + pR2.getHeight() + 10,
                    ANCHO_MAXIMO_MENU,
                    this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));

            for (Component comp : menuItemsPanel.getComponents()) {
                if (comp instanceof JButton) {
                    JButton button = (JButton) comp;
                    button.setText("   " + getOriginalButtonText(button));
                }
            }
            separatorMenu.setVisible(false);
        } else {
            pR2.setBounds(10, 20, ANCHO_MINIMO_MENU - 20, LOGO_CONTRAIDO_ALTO);
            Actualizartamañoicono();

            menuItemsPanel.setBounds(0, pR2.getY() + pR2.getHeight() + 10,
                    ANCHO_MINIMO_MENU,
                    this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));

            for (Component comp : menuItemsPanel.getComponents()) {
                if (comp instanceof JButton) {
                    ((JButton) comp).setText(" ");
                }
            }
            separatorMenu.setVisible(false);
        }
        lateral.revalidate();
        lateral.repaint();
    }

    private String getOriginalButtonText(JButton button) {
        if (button == btnUnirseEmpresa) {
            return "Unirse a empresa";
        }
        return "";
    }

    private void toggleMenu() {
        menuExpandido = !menuExpandido;
        if (!menuExpandido) {
            setMenuItemsVisibility(false);
        }
        animacionTimer.start();
    }

    public static void main(String[] args) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == x) {
            System.exit(0);
        }
        if (e.getSource() == m) {
            this.setState(JFrame.ICONIFIED);
        }

        if (e.getSource() == btnUnirseEmpresa) {
            unirsepnl.setVisible(true);
            if (menuExpandido) {
                toggleMenu();
            }
        }
        if (e.getSource() == X) {
            unirsepnl.setVisible(false);
        }
        if (e.getSource() == btnunirse) {
            finalizarRegistro();
        }
        if (e.getSource() == cancelar) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que quieres cancelar? No se guardarán los datos.", "Confirmar Cancelar",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new Inicio_de_sesion();
                this.dispose();

            }
        }

    }

    private void finalizarRegistro() {
        String codigoEmpresa = cod.getText().trim();
        boolean originalAutoCommit = true;

        if (codigoEmpresa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el Código de empresa.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        PreparedStatement stmtU = null;
        int idEmpresaEncontrado = -1;

        try {

            originalAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            // 1. Buscar la empresa
            String sql = "SELECT idEmpresa FROM Empresa WHERE Codigo_empresa = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigoEmpresa);
            rs = stmt.executeQuery();

            if (rs.next()) {
                idEmpresaEncontrado = rs.getInt("idEmpresa");
            } else {
                JOptionPane.showMessageDialog(this, "Código de empresa no válido. Ingrese un código registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                cod.setText("");
                return;
            }

            String sqlUp = "UPDATE Usuarios SET idEmpresa = ?, Estado_Union_Empresa = 'Activo' WHERE idUsuarios = ?";
            stmtU = conn.prepareStatement(sqlUp);
            stmtU.setInt(1, idEmpresaEncontrado);
            stmtU.setInt(2, idUsuario);

            int rowsUp = stmtU.executeUpdate();

            if (rowsUp > 0) {
                conn.commit();
                JOptionPane.showMessageDialog(this, "¡Registro exitoso!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
                new Inicio_de_sesion();
                this.dispose();
            } else {
                throw new SQLException("Error al actualizar el usuario. No se modificaron filas.");
            }

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    // Si hubo un error, se hace rollback
                    System.err.println("Error SQL, iniciando rollback...");
                    conn.rollback();
                    System.err.println("Rollback completado.");
                }
            } catch (SQLException ex) {
                System.err.println("Error durante el rollback: " + ex.getMessage());
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Error al finalizar registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                // Cerrar recursos en el bloque finally
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (stmtU != null) {
                    stmtU.close();
                }

                if (conn != null && conn.getAutoCommit() != originalAutoCommit) {
                    conn.setAutoCommit(originalAutoCommit);
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar recursos o restaurar autocommit: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
