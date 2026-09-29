package Vista;

import ManejoBase.ActualizarMateria;
import ManejoBase.ActualizarMaterial;
import ManejoBase.ActualizarProductos;
import ManejoBase.CreateMateria;
import ManejoBase.CreateProducto;
import ManejoBase.Create_Material;
import ManejoBase.DeleteMateria;
import ManejoBase.DeleteMaterial;
import ManejoBase.DeleteProducto;
import ManejoBase.SesionUsuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import javax.swing.border.EmptyBorder;
import java.net.URL;
import javax.swing.text.JTextComponent;

public class Inventarios extends JFrame implements ActionListener {

    JPanel menuItemsPanel;
    JButton btnDashboard;
    JButton btnInventario;
    JButton btnHistorial;
    JButton btnConfiguracion;
    JButton btnEmpleados;
    JButton btnCerrarSesion;
    private boolean menuExpandido = false;
    private final int ANCHO_MINIMO_MENU = 70;
    private final int ANCHO_MAXIMO_MENU = 250;
    private Timer animacionTimer;
    private final int VELOCIDAD_ANIMACION = 5;
    private final int PASO_ANIMACION = 15;
    private ImageIcon originalLogoIcon;
    private final int LOGO_CONTRAIDO_ALTO = 60;
    private final int LOGO_EXPANDIDO_ALTO = 100;
    JSeparator separatorMenu;
    JLabel pR2;
    JPanel lateral;
    JLabel lbltitulo;
    JSeparator lineaTitulo;
    JButton btnMaterial;
    JButton btnInventarios;
    JButton btnProductos;
    JButton btnMateria;
    JButton btnAñadir;
    JButton btnEliminar;
    JButton btnActualizar;
    pnlInventario msinv;
    private JTextField txtID;
    private CardLayout cardLayout;
    private JPanel panelContenedor;
    private JPanel panelTipos;
    private String rolUsuario;
    private int idEmpresaUsuario;
    JLabel EliminarAct;
    pnlMaterial pnlMaterialIns;
    pnlMateria pnlMateriaIns;
    pnlProductos pnlProductosIns;
    Font bt = new Font("Segoe UI", Font.BOLD, 14);
    JButton x;
    JButton m;

    public Inventarios() {
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
        this.setResizable(false);
        this.getContentPane().setBackground(Color.decode("#F7F8FC"));
        SesionUsuario sesion = SesionUsuario.getInstance();
        this.rolUsuario = sesion.getRol();
        this.idEmpresaUsuario = sesion.getIdEmpresa();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1366, 768);
        pnlMaterialIns = new pnlMaterial();
        pnlMateriaIns = new pnlMateria();
        pnlProductosIns = new pnlProductos();
        obj();
        agr();
        posicionar();
        configurarMenuHamburguesa();
        if (!"Administrador".equalsIgnoreCase(rolUsuario)) {
            btnEmpleados.setVisible(false);
        }
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new Inventarios();
    }

    public void posicionar() {
        lateral.setBounds(0, 0, ANCHO_MINIMO_MENU, this.getHeight());
        lbltitulo.setBounds(120, 25, 600, 70);
        lineaTitulo.setBounds(120, 95, 1190, 25);
        panelTipos.setBounds(100, 120, 250, 550);
        panelContenedor.setBounds(370, 120, 950, 550);
        btnProductos.setBounds(35, 20, 180, 40);
        btnMateria.setBounds(35, 70, 180, 40);
        btnMaterial.setBounds(35, 120, 180, 40);
        btnInventario.setBounds(35, 170, 180, 40);
        txtID.setBounds(50, 270, 150, 30);
        btnAñadir.setBounds(52, 370, 150, 40);
        btnEliminar.setBounds(45, 420, 160, 40);
        btnActualizar.setBounds(40, 470, 170, 40);
        pR2.setBounds(10, 20, ANCHO_MINIMO_MENU - 20, LOGO_CONTRAIDO_ALTO);
        Actualizartamañoicono();
        menuItemsPanel.setBounds(0, pR2.getY() + pR2.getHeight() + 10,
                ANCHO_MAXIMO_MENU,
                this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));
        EliminarAct.setBounds(70, 240, 150, 30);
    }

    public void agr() {
        msinv = new pnlInventario();

        add(lbltitulo);
        add(lineaTitulo);
        add(panelContenedor);
        panelContenedor.add(pnlProductosIns, "Productos");
        panelContenedor.add(pnlMateriaIns, "Materia");
        panelContenedor.add(pnlMaterialIns, "Material");
        panelContenedor.add(msinv, "Inventarios");
        add(panelTipos);
        panelTipos.add(btnProductos);
        panelTipos.add(btnMateria);
        panelTipos.add(btnMaterial);
        panelTipos.add(btnInventario);
        panelTipos.add(txtID);
        panelTipos.add(EliminarAct);
        panelTipos.add(btnAñadir);
        panelTipos.add(btnEliminar);
        panelTipos.add(btnActualizar);
        btnProductos.addActionListener(this);
        btnMateria.addActionListener(this);
        btnMaterial.addActionListener(this);
        btnInventario.addActionListener(this);
        btnEliminar.addActionListener(this);
        btnAñadir.addActionListener(this);
        btnActualizar.addActionListener(this);
        add(lateral);
        lateral.add(menuItemsPanel);
        menuItemsPanel.add(Box.createVerticalStrut(20));
        menuItemsPanel.add(btnDashboard);
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(btnInventarios);
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(btnHistorial);
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(btnConfiguracion);
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(btnEmpleados);
        menuItemsPanel.add(Box.createVerticalStrut(20));
        menuItemsPanel.add(separatorMenu);
        menuItemsPanel.add(Box.createVerticalStrut(10));
        menuItemsPanel.add(btnCerrarSesion);
        menuItemsPanel.add(Box.createVerticalGlue());
        setMenuItemsVisibility(false);
        lateral.add(pR2);
    }

    public void obj() {
        pnl();
        lbl();
        btn();
        txf();
        linea();
        cardLayout();
        img();
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

    public void btn() {

        btnProductos = new JButton("Productos Terminados");
        diseñobtn(btnProductos);
        btnMateria = new JButton("Materia Prima");
        diseñobtn(btnMateria);
        btnMaterial = new JButton("Material de Empaque");
        diseñobtn(btnMaterial);
        btnInventario = new JButton("Inventario");
        diseñobtn(btnInventario);
        btnAñadir = new JButton("Añadir objeto");
        diseñobtn(btnAñadir);
        btnEliminar = new JButton("Eliminar objeto");
        diseñobtn(btnEliminar);
        btnActualizar = new JButton("Actualizar objeto");
        diseñobtn(btnActualizar);

        btnDashboard = createMenuItem("Dashboard", "/Imagenes/panel-de-control.png");
        btnInventarios = createMenuItem("Inventario", "/Imagenes/alt-de-inventario.png");
        btnHistorial = createMenuItem("Historial", "/Imagenes/calendario-reloj.png");
        btnConfiguracion = createMenuItem("Configuración", "/Imagenes/alt-administrador.png");
        btnEmpleados = createMenuItem("Empleados", "/Imagenes/empleados.png");
        btnCerrarSesion = createMenuItem("Cerrar Sesión", "/Imagenes/cierre-de-sesion-de-usuario.png");
    }

    public void lbl() {
        lbltitulo = new JLabel("Productos Terminados");
        lbltitulo.setFont(new Font("Segoe UI", Font.BOLD, 44));
        lbltitulo.setForeground(new Color(106, 119, 147));
        pR2 = new JLabel();
        pR2.setHorizontalAlignment(SwingConstants.CENTER);
        pR2.setVerticalAlignment(SwingConstants.CENTER);
        EliminarAct = new JLabel("Eliminar o actualizar");
        EliminarAct.setForeground(new Color(106, 119, 147));
    }

    public void linea() {
        lineaTitulo = new JSeparator();
        lineaTitulo.setBackground(Color.decode("#B4A8AA"));

        separatorMenu = new JSeparator(JSeparator.HORIZONTAL);
        separatorMenu.setForeground(Color.decode("#6A7793"));
        separatorMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    }

    public void pnl() {
        panelTipos = new JPanel();
        panelTipos.setLayout(null);
        panelTipos.setBackground(new Color(208, 201, 208));

        lateral = new JPanel();
        lateral.setBackground(Color.decode("#CFD1E0"));
        lateral.setLayout(null);
        menuItemsPanel = new JPanel();
        menuItemsPanel.setBackground(Color.decode("#CFD1E0"));

        menuItemsPanel.setLayout(new BoxLayout(menuItemsPanel, BoxLayout.Y_AXIS));
        menuItemsPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    public void cardLayout() {
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
    }

    public void txf() {
        txtID = new JTextField(" ID");
        txtID.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtID.setBackground(new Color(249, 247, 250));
        txtID.setForeground(Color.GRAY);
        txtID.setBorder(null);
        txtID.setCaretColor(Color.BLACK);
        permitirnumeros(txtID);
        deshabilitarCopiarPegar(txtID);
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
    public void permitirnumeros(JTextField campo) {

        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String letra = String.valueOf(c);
                if (!letra.matches("[0-9]")) {
                    e.consume();
                }
                if (campo.getText().length() > 7) {
                    e.consume();
                }
            }
        });
    }

    public void forma(JButton boton) {
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);

        boton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(boton.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 40, 40);
                super.paint(g, c);
                g2.dispose();
            }
        });
    }

    public void diseñobtn(JButton btn) {
        btn.setBackground(new Color(106, 119, 147));
        btn.setForeground(Color.WHITE);
        forma(btn);
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
                            Inventarios.this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));

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
        JButton menuItem = new JButton("  " + text);
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
                    button.setText("  " + getOriginalButtonText(button));
                }
            }
            separatorMenu.setVisible(true);
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
        if (button == btnDashboard) {
            return "Dashboard";
        }
        if (button == btnInventarios) {
            return "Inventario";
        }
        if (button == btnHistorial) {
            return "Historial";
        }
        if (button == btnConfiguracion) {
            return "Configuración";
        }
        if (button == btnEmpleados) {
            return "Empleados";
        }
        if (button == btnCerrarSesion) {
            return "Cerrar Sesión";
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == x) {
            System.exit(0);
        }
        if (e.getSource() == m) {
            this.setState(JFrame.ICONIFIED);
        }
        if (e.getSource() == btnProductos) {
            cardLayout.show(panelContenedor, "Productos");//Manda a llamar dentro del PanelContenedor al panel asignado como "Productos"
            lbltitulo.setText("Productos Terminados");
            txtID.setVisible(true);
            txtID.setText(" ID");
            btnAñadir.setVisible(true);
            btnEliminar.setVisible(true);
            btnActualizar.setVisible(true);
            EliminarAct.setVisible(true);
            if (pnlProductosIns != null) {
                pnlProductosIns.MostrarProductos();
            }
        } else if (e.getSource() == btnMateria) {
            cardLayout.show(panelContenedor, "Materia");
            lbltitulo.setText("Materia Prima");
            txtID.setVisible(true);
            txtID.setText(" ID");
            btnAñadir.setVisible(true);
            btnEliminar.setVisible(true);
            btnActualizar.setVisible(true);
            EliminarAct.setVisible(true);
            if (pnlMateriaIns != null) {
                pnlMateriaIns.MostrarMateria();
            }
        } else if (e.getSource() == btnMaterial) {
            cardLayout.show(panelContenedor, "Material");
            lbltitulo.setText("Material de Empaque");
            txtID.setVisible(true);
            txtID.setText(" ID");
            btnAñadir.setVisible(true);
            btnEliminar.setVisible(true);
            btnActualizar.setVisible(true);
            EliminarAct.setVisible(true);
            if (pnlMaterialIns != null) {
                pnlMaterialIns.MostrarMaterial();
            }
        } else if (e.getSource() == btnInventario) {
            cardLayout.show(panelContenedor, "Inventarios");
            lbltitulo.setText("Inventario");
            txtID.setVisible(false);
            txtID.setText("");
            btnAñadir.setVisible(false);
            btnEliminar.setVisible(false);
            btnActualizar.setVisible(false);
            EliminarAct.setVisible(false);
            if (msinv != null) {
                msinv.paraeljoin();
            }
        } else if (e.getSource() == btnEliminar) {
            String idText = txtID.getText().trim();

            if (idText.isEmpty() || idText.equals("ID")) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un ID para eliminar.", "Error de Eliminación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idToDelete;
            try {
                idToDelete = Integer.parseInt(idText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String currentPanelName = "";
            for (Component comp : panelContenedor.getComponents()) {
                if (comp.isVisible()) {
                    if (comp.getClass().getSimpleName().equals("pnlProductos")) {
                        currentPanelName = "Productos Terminados";
                    } else if (comp.getClass().getSimpleName().equals("pnlMateria")) {
                        currentPanelName = "Materia Prima";
                    } else if (comp.getClass().getSimpleName().equals("pnlMaterial")) {
                        currentPanelName = "Material de Empaque";
                    }
                    // No hay "Inventario General" porque en ese caso txtID está oculto.
                    break;
                }
            }

            boolean eliminado = false;
            if (currentPanelName.equals("Productos Terminados")) {
                DeleteProducto deleteProducto = new DeleteProducto();
                eliminado = deleteProducto.eliminarProducto(idToDelete);
                if (eliminado) {
                    Component[] components = panelContenedor.getComponents();
                    for (Component comp : components) {
                        if (comp instanceof pnlProductos) {
                            ((pnlProductos) comp).MostrarProductos();
                            break;
                        }
                    }
                }
                JOptionPane.showMessageDialog(this, "La eliminación de Productos Terminados aún no está implementada.", "Información", JOptionPane.INFORMATION_MESSAGE);

            } else if (currentPanelName.equals("Materia Prima")) {
                DeleteMateria deleteMateria = new DeleteMateria();
                eliminado = deleteMateria.eliminarMateriaPrima(idToDelete);
                if (eliminado) {
                    if (pnlMateriaIns != null) {
                        pnlMateriaIns.MostrarMateria();
                    }
                }

            } else if (currentPanelName.equals("Material de Empaque")) {

                DeleteMaterial deleteMaterial = new DeleteMaterial();
                eliminado = deleteMaterial.eliminarMaterialEmpaque(idToDelete);
                if (eliminado) {

                    if (pnlMaterialIns != null) {
                        pnlMaterialIns.MostrarMaterial();
                    }
                }

            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un tipo de inventario (Productos, Materia Prima, Material de Empaque) para eliminar un elemento.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
            txtID.setText("ID");
        } else if (e.getSource() == btnAñadir) {
            String currentPanelName = "";
            for (Component comp : panelContenedor.getComponents()) {
                if (comp.isVisible()) {
                    if (comp.equals(pnlProductosIns)) {
                        currentPanelName = "Productos Terminados";
                    } else if (comp.equals(pnlMateriaIns)) {
                        currentPanelName = "Materia Prima";
                    } else if (comp.equals(pnlMaterialIns)) {
                        currentPanelName = "Material de Empaque";
                    }
                    break;
                }
            }

            if (currentPanelName.equals("Materia Prima")) {

                if (pnlMateriaIns != null) {

                    Object[] materiaData = pnlMateriaIns.getDatosMateriaPrima();

                    if (materiaData != null) {
                        try {
                            // idEmpresa se toma de la sesión del usuario
                            int idEmpresa = this.idEmpresaUsuario;
                            // Los índices de materiaData se ajustan porque idEmpresa ya no viene del panel
                            String nombre = (String) materiaData[0]; // Ahora materiaData[0] es el nombre
                            String unidad_de_medida = (String) materiaData[1];
                            int cantidad_disponible = (int) materiaData[2];
                            int cantidad_minima = (int) materiaData[3];
                            double precio_unitario = (double) materiaData[4];
                            Timestamp ultima_actualizacion_inv = new Timestamp(System.currentTimeMillis());

                            CreateMateria createMateria = new CreateMateria();
                            boolean creado = createMateria.crearMateriaPrima(
                                    idEmpresa, nombre, unidad_de_medida,
                                    cantidad_disponible, cantidad_minima, precio_unitario,
                                    ultima_actualizacion_inv
                            );

                            if (creado) {
                                pnlMateriaIns.MostrarMateria();
                                pnlMateriaIns.clearFields();
                            }
                        } catch (ClassCastException | NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Error de tipo de dato o formato al procesar la materia prima. Verifique la entrada.", "Error", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error: La instancia del panel de Materia Prima no está disponible.", "Error Interno", JOptionPane.ERROR_MESSAGE);
                }

            } else if (currentPanelName.equals("Material de Empaque")) {

                if (pnlMaterialIns != null) {
                    Object[] materialData = pnlMaterialIns.getDatosMaterialEmpaque();

                    if (materialData != null) {
                        try {
                            // idEmpresa se toma de la sesión del usuario
                            int idEmpresa = this.idEmpresaUsuario;
                            // Los índices de materialData se ajustan
                            String nombre = (String) materialData[0]; // Ahora materialData[0] es el nombre
                            String unidad_de_medida = (String) materialData[1];
                            int cantidad = (int) materialData[2];
                            double precio_unitario = (double) materialData[3];
                            Timestamp ultima_actualizacion_empq = new Timestamp(System.currentTimeMillis());

                            Create_Material createMaterial = new Create_Material();
                            boolean creado = createMaterial.crearMaterialEmpaque(
                                    idEmpresa, nombre, unidad_de_medida,
                                    cantidad, precio_unitario, ultima_actualizacion_empq
                            );

                            if (creado) {
                                pnlMaterialIns.MostrarMaterial();
                                pnlMaterialIns.clearFields();
                            }

                        } catch (ClassCastException | NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Error de tipo de dato o formato al procesar el material de empaque. Verifique la entrada.", "Error", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error: La instancia del panel de Material de Empaque no está disponible.", "Error Interno", JOptionPane.ERROR_MESSAGE);
                }

            } else if (currentPanelName.equals("Productos Terminados")) {
                if (pnlProductosIns != null) {
                    Object[] productoData = pnlProductosIns.getDatosProducto();

                    if (productoData != null) {
                        try {
                            // idEmpresa se toma de la sesión del usuario
                            int idEmpresa = this.idEmpresaUsuario;
                            // Los índices de productoData se ajustan
                            String nombre = (String) productoData[0]; // Ahora productoData[0] es el nombre
                            String descripcion = (String) productoData[1];
                            int cantidadDisponible = (int) productoData[2];
                            double costoProduccion = (double) productoData[3];
                            double precioVenta = (double) productoData[4];

                            Timestamp fechaProduccion = new Timestamp(System.currentTimeMillis());
                            Timestamp ultimaActualizacionProd = new Timestamp(System.currentTimeMillis());

                            CreateProducto createProducto = new CreateProducto();
                            boolean creado = createProducto.crearProducto(
                                    idEmpresa, nombre, descripcion,
                                    cantidadDisponible, costoProduccion, precioVenta,
                                    fechaProduccion, ultimaActualizacionProd
                            );

                            if (creado) {
                                pnlProductosIns.MostrarProductos();
                                pnlProductosIns.clearFields();
                            }
                        } catch (ClassCastException | NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Error de tipo de dato o formato al procesar el producto. Verifique la entrada.", "Error", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error: La instancia del panel de Productos Terminados no está disponible.", "Error Interno", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un tipo de inventario (Productos, Materia Prima, Material de Empaque) para añadir un elemento.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == btnActualizar) {
            String idText = txtID.getText().trim();

            if (idText.isEmpty() || idText.equals("ID")) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce el ID del elemento a actualizar.", "Error de Actualización", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idToUpdate;
            try {
                idToUpdate = Integer.parseInt(idText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String cantidadText = JOptionPane.showInputDialog(this, "Introduce la nueva cantidad disponible:", "Actualizar Cantidad", JOptionPane.QUESTION_MESSAGE);
            if (cantidadText == null || cantidadText.trim().isEmpty()) {
                return;
            }

            int newQuantity;
            try {
                newQuantity = Integer.parseInt(cantidadText.trim());
                if (newQuantity < 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad no puede ser negativa.", "Error de Cantidad", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String currentPanelName = "";
            for (Component comp : panelContenedor.getComponents()) {
                if (comp.isVisible()) {
                    if (comp.getClass().getSimpleName().equals("pnlProductos")) {
                        currentPanelName = "Productos Terminados";
                    } else if (comp.getClass().getSimpleName().equals("pnlMateria")) {
                        currentPanelName = "Materia Prima";
                    } else if (comp.getClass().getSimpleName().equals("pnlMaterial")) {
                        currentPanelName = "Material de Empaque";
                    }
                    break;
                }
            }

            boolean actualizado = false;
            if (currentPanelName.equals("Productos Terminados")) {
                ActualizarProductos actualizarProducto = new ActualizarProductos();
                actualizado = actualizarProducto.actualizarCantidadProducto(idToUpdate, newQuantity);
                if (actualizado) {
                    if (pnlProductosIns != null) {
                        pnlProductosIns.MostrarProductos();
                        pnlProductosIns.clearFields();
                    }
                }
            } else if (currentPanelName.equals("Materia Prima")) {
                ActualizarMateria actualizarMateria = new ActualizarMateria();
                actualizado = actualizarMateria.actualizarCantidadMateriaPrima(idToUpdate, newQuantity);
                if (actualizado) {
                    if (pnlMateriaIns != null) {
                        pnlMateriaIns.MostrarMateria();
                        pnlMateriaIns.clearFields();
                    }
                }
            } else if (currentPanelName.equals("Material de Empaque")) {
                ActualizarMaterial actualizarMaterial = new ActualizarMaterial();
                actualizado = actualizarMaterial.actualizarCantidadMaterialEmpaque(idToUpdate, newQuantity);
                if (actualizado) {
                    if (pnlMaterialIns != null) {
                        pnlMaterialIns.MostrarMaterial();
                        pnlMaterialIns.clearFields();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un tipo de inventario (Productos, Materia Prima, Material de Empaque) para actualizar un elemento.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
            txtID.setText("ID");

        } else if (e.getSource() == btnDashboard) {
            new Dashboard();//Crea un objeto del tipo
            this.dispose();
            if (menuExpandido) {
                toggleMenu(); // Contraer al hacer clic
            }
        } else if (e.getSource() == btnInventarios) {
            new Inventarios();
            this.dispose();
            if (menuExpandido) {
                toggleMenu();
            }
        } else if (e.getSource() == btnHistorial) {

            new Historial();
            this.dispose();
            if (menuExpandido) {
                toggleMenu();
            }
        } else if (e.getSource() == btnConfiguracion) {

            new Configuracion();
            this.dispose();
            if (menuExpandido) {
                toggleMenu();
            }
        } else if (e.getSource() == btnEmpleados) {

            new Empleados();
            this.dispose();
            if (menuExpandido) {
                toggleMenu();
            }
        } else if (e.getSource() == btnCerrarSesion) {
            System.out.println("Cerrar Sesión");
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que quieres cerrar la sesión?", "Confirmar Cierre de Sesión",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                SesionUsuario.getInstance().cerrarsesion();
                new Inicio_de_sesion();
                this.dispose();
            }
        }
    }
}
