package Vista;

//importar bibliotecas
import ManejoBase.ActualizarEmpleados;
import ManejoBase.Conexion;
import ManejoBase.MostrarEmpleados;
import ManejoBase.SesionUsuario;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL; // Importación añadida
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.JTextComponent;
public class Empleados extends JFrame implements ActionListener {
//crear los componentes
    JPanel menuItemsPanel;
    JButton btnDashboard;
    JButton btnInventarios;
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
    JSeparator linea;
    JButton btnDatos;
    JLabel lbltitulo;
    JSeparator lineaTitulo;
    private JTextField txtNombreCompleto;
    private JTextField txtID;
    private JTextField txtEmpresa;
    private JTextField txtCargo;
    private JTextField txtSalario;
    private JTable tablaEmpleados;
    private DefaultTableModel mdTabla;
    private JScrollPane scTabla;
    private JTableHeader titulos;
    private JPanel pnlTextos;
    Font bt = new Font("Segoe UI", Font.BOLD, 14);
    private String rolUsuario;
    private int idEmpresaUsuario;
    MostrarEmpleados MostrarEmpleados;
    ActualizarEmpleados actualizarEmpleados;
    private final String PLACEHOLDER_NOMBRE = " Nombre completo";
    private final String PLACEHOLDER_ID = " ID Usuario";
    private final String PLACEHOLDER_ESTADO_UNION_EMPRESA = " Estado Unión Empresa";
    private final String PLACEHOLDER_CARGO = " Rol";
    private final String PLACEHOLDER_CONTRASENA = " Contrasena";
    //para la barrita de arriba
    JButton x;
    JButton m;
    //Constructor
    public Empleados() {
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
        Conexion conn = new Conexion();
        MostrarEmpleados = new MostrarEmpleados(conn);
        actualizarEmpleados = new ActualizarEmpleados();
        //lamados a los metodos con los componentes
        obj();
        agr();
        posicionar();
        configurarMenuHamburguesa();
        //si no es administrador
        if (!"Administrador".equalsIgnoreCase(rolUsuario)) {
            btnEmpleados.setVisible(false);
        }
        cargarEmpleadosEnTabla();
        
        tablaEmpleados.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //cargar los datos del registro seleccionado a los textfields
                int selectedRow = tablaEmpleados.getSelectedRow();
                if (selectedRow != -1) {
                    txtID.setText(mdTabla.getValueAt(selectedRow, 0).toString());
                    txtNombreCompleto.setText(mdTabla.getValueAt(selectedRow, 1).toString());
                    txtCargo.setText(mdTabla.getValueAt(selectedRow, 2).toString());
                    txtSalario.setText(mdTabla.getValueAt(selectedRow, 3).toString());
                    txtEmpresa.setText(mdTabla.getValueAt(selectedRow, 4).toString());
                    setActualTextColor(txtID, PLACEHOLDER_ID);
                    setActualTextColor(txtNombreCompleto, PLACEHOLDER_NOMBRE);
                    setActualTextColor(txtCargo, PLACEHOLDER_CARGO);
                    setActualTextColor(txtSalario, PLACEHOLDER_CONTRASENA);
                    setActualTextColor(txtEmpresa, PLACEHOLDER_ESTADO_UNION_EMPRESA);
                }
            }
        });
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        //crear objeto
        new Empleados();
    }

    //posicionar, tal y como su nombre indica, los componentes
    public void posicionar() {
        lateral.setBounds(0, 0, ANCHO_MINIMO_MENU, this.getHeight());
        lbltitulo.setBounds(120, 25, 600, 70);
        lineaTitulo.setBounds(120, 95, 1190, 25);
        pnlTextos.setBounds(520, 150, 350, 245);
        txtNombreCompleto.setBounds(20, 20, 310, 30);
        txtID.setBounds(20, 60, 145, 30);
        txtEmpresa.setBounds(185, 60, 145, 30);
        txtCargo.setBounds(20, 100, 145, 30);
        txtSalario.setBounds(185, 100, 145, 30);
        linea.setBounds(20, 180, 310, 6);
        btnDatos.setBounds(110, 200, 120, 30);
        scTabla.setBounds(270, 450, 880, 200);
        pR2.setBounds(10, 20, ANCHO_MINIMO_MENU - 20, LOGO_CONTRAIDO_ALTO);
        Actualizartamañoicono();
        menuItemsPanel.setBounds(0, pR2.getY() + pR2.getHeight() + 10,
                ANCHO_MAXIMO_MENU,
                this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));
    }
//llamar a los metodos que contienen la creación de los componentes
    public void obj() {
        pnl();
        btn();
        txf();
        linea();
        Tb();
        lbl();
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
        m.addActionListener(this);
        x.addActionListener(this);
        x.setBorder(null);
        m.setBorder(null);
    }
    
    //Agregar los componentes al frame o panel, según corresponda
    public void agr() {
        add(pnlTextos);
        pnlTextos.add(txtNombreCompleto);
        pnlTextos.add(txtID);
        pnlTextos.add(txtEmpresa);
        pnlTextos.add(txtCargo);
        pnlTextos.add(txtSalario);
        pnlTextos.add(btnDatos);
        pnlTextos.add(linea);
        add(lineaTitulo);
        add(lbltitulo);
        add(scTabla);
        btnDatos.addActionListener(this);
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
    
    //
    public void Tb() {
        String[] columnas = {"ID", "Nombre", "Rol", "Contrasena", "Estado Unión Empresa"};
        mdTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEmpleados = new JTable(mdTabla);
        tablaEmpleados.setFillsViewportHeight(true);
        scTabla = new JScrollPane(tablaEmpleados);

        tablaEmpleados.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaEmpleados.setBackground(new Color(249, 247, 250));
        tablaEmpleados.setForeground(Color.BLACK);
        tablaEmpleados.setRowHeight(28);
        tablaEmpleados.setShowGrid(true);
        tablaEmpleados.setGridColor(Color.BLACK);

        titulos = tablaEmpleados.getTableHeader();
        titulos.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titulos.setBackground(new Color(116, 119, 147));
        titulos.setForeground(Color.WHITE);
        titulos.setReorderingAllowed(false);

        tablaEmpleados.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablaEmpleados.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaEmpleados.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaEmpleados.getColumnModel().getColumn(3).setPreferredWidth(120);
        tablaEmpleados.getColumnModel().getColumn(4).setPreferredWidth(180);
    }

    public void pnl() {
        pnlTextos = new JPanel();
        pnlTextos.setLayout(null);
        pnlTextos.setBackground(new Color(180, 168, 170));
        lateral = new JPanel();
        lateral.setBackground(Color.decode("#CFD1E0"));
        lateral.setLayout(null);
        menuItemsPanel = new JPanel();
        menuItemsPanel.setBackground(Color.decode("#CFD1E0"));

        menuItemsPanel.setLayout(new BoxLayout(menuItemsPanel, BoxLayout.Y_AXIS));
        menuItemsPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    private void cargarEmpleadosEnTabla() {
        mdTabla.setRowCount(0);
        try {
            MostrarEmpleados.mostrarEmpleados(idEmpresaUsuario, mdTabla);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los empleados: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void txf() {
        txtNombreCompleto = new JTextField(PLACEHOLDER_NOMBRE);
        txtID = new JTextField(PLACEHOLDER_ID);
        txtEmpresa = new JTextField(PLACEHOLDER_ESTADO_UNION_EMPRESA);
        txtCargo = new JTextField(PLACEHOLDER_CARGO);
        txtSalario = new JTextField(PLACEHOLDER_CONTRASENA);

        diseñotxf(txtNombreCompleto, PLACEHOLDER_NOMBRE);
        diseñotxf(txtID, PLACEHOLDER_ID);
        diseñotxf(txtEmpresa, PLACEHOLDER_ESTADO_UNION_EMPRESA);
        diseñotxf(txtCargo, PLACEHOLDER_CARGO);
        diseñotxf(txtSalario, PLACEHOLDER_CONTRASENA);
        txtNombreCompleto.setEnabled(false);
        txtID.setEnabled(false);
        txtEmpresa.setEnabled(true);
        txtCargo.setEnabled(false);
        txtSalario.setEnabled(false);
        deshabilitarCopiarPegar(txtEmpresa);
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
    public void lbl() {
        lbltitulo = new JLabel("Empleados");
        lbltitulo.setFont(new Font("Segoe UI", Font.BOLD, 44));
        lbltitulo.setForeground(new Color(106, 119, 147));

        pR2 = new JLabel();
        pR2.setHorizontalAlignment(SwingConstants.CENTER);
        pR2.setVerticalAlignment(SwingConstants.CENTER);
    }

    public void linea() {
        lineaTitulo = new JSeparator();
        lineaTitulo.setBackground(Color.decode("#B4A8AA"));

        linea = new JSeparator(JSeparator.HORIZONTAL);
        linea.setForeground(Color.decode("#6A7793"));
        linea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        separatorMenu = new JSeparator(JSeparator.HORIZONTAL);
        separatorMenu.setForeground(Color.decode("#B4A8AA"));
        separatorMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

    }

    public void btn() {
        btnDatos = new JButton("Actualizar");
        diseñobtn(btnDatos);
        btnDashboard = createMenuItem("Dashboard", "/Imagenes/panel-de-control.png");
        btnInventarios = createMenuItem("Inventario", "/Imagenes/alt-de-inventario.png");
        btnHistorial = createMenuItem("Historial", "/Imagenes/calendario-reloj.png");
        btnConfiguracion = createMenuItem("Configuración", "/Imagenes/alt-administrador.png");
        btnEmpleados = createMenuItem("Empleados", "/Imagenes/empleados.png");
        btnCerrarSesion = createMenuItem("Cerrar Sesión", "/Imagenes/cierre-de-sesion-de-usuario.png");
    }

    public void diseñotxf(JTextField txf, String placeholder) {
        txf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txf.setBackground(new Color(249, 247, 250));
        txf.setForeground(Color.GRAY);
        txf.setBorder(null);
        txf.setCaretColor(Color.BLACK);

        txf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txf.getText().equals(placeholder)) {
                    txf.setText("");
                    txf.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txf.getText().isEmpty()) {
                    txf.setText(placeholder);
                    txf.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void setActualTextColor(JTextField txf, String placeholder) {
        if (!txf.getText().isEmpty() && !txf.getText().equals(placeholder)) {
            txf.setForeground(Color.BLACK);
        } else {
            txf.setForeground(Color.GRAY);
        }
    }

    public void diseñobtn(JButton btn) {
        btn.setBackground(new Color(106, 119, 147));
        btn.setForeground(Color.WHITE);
        forma(btn);
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

    public void img() {
        try {
            // Cargar la imagen como un recurso desde el classpath
            URL imageUrl = getClass().getResource("/Imagenes/logo.png");
            if (imageUrl != null) {
                originalLogoIcon = new ImageIcon(imageUrl);
            } else {
                System.err.println("Recurso 'logo.png' no encontrado. Asegúrate de que está en el classpath en la carpeta /Imagenes/.");
                originalLogoIcon = new ImageIcon(); // ImageIcon vacío si no se encuentra
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
        } else {
            pR2.setIcon(null); // Limpiar el icono si no hay imagen válida
            pR2.setText("Logo"); // Opcional: mostrar texto si la imagen no carga
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
                            Empleados.this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));

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
        JButton menuItem = new JButton(" " + text);
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
            // Cargar la imagen como un recurso desde el classpath
            URL iconUrl = getClass().getResource(iconPath);
            if (iconUrl != null) {
                ImageIcon originalIcon = new ImageIcon(iconUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                menuItem.setIcon(new ImageIcon(scaledImage));
            } else {
                System.err.println("Recurso '" + iconPath + "' no encontrado. Asegúrate de que está en el classpath.");
                // Opcional: usar un icono predeterminado o el logo si el específico no se encuentra
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
                    button.setText(" " + getOriginalButtonText(button));
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
        if(e.getSource() == x){
        System.exit(0);
        }
        if(e.getSource() == m){
        this.setState(JFrame.ICONIFIED);
        }
        if (e.getSource() == btnDashboard) {
            new Dashboard();
            this.dispose();
            if (menuExpandido) {
                toggleMenu();
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
        } else if (e.getSource() == btnDatos) {
            try {
                String idText = txtID.getText().trim();
                String nuevoRol = txtCargo.getText().trim();
                String nuevoEstadoUnionEmpresa = txtEmpresa.getText().trim();

                if (idText.isEmpty() || idText.equals(PLACEHOLDER_ID)
                        || nuevoRol.isEmpty() || nuevoRol.equals(PLACEHOLDER_CARGO)
                        || nuevoEstadoUnionEmpresa.isEmpty() || nuevoEstadoUnionEmpresa.equals(PLACEHOLDER_ESTADO_UNION_EMPRESA)) {
                    JOptionPane.showMessageDialog(this, "Por favor, completa los campos: ID Usuario, Rol y Estado Unión Empresa para actualizar.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idUsuario = Integer.parseInt(idText);

                boolean exito = actualizarEmpleados.actualizarDatosUsuario(
                        idUsuario,
                        nuevoRol,
                        nuevoEstadoUnionEmpresa
                );

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Usuario (Empleado) actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarEmpleadosEnTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar el usuario (empleado). Verifica el ID o los datos.", "Error de Actualización", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un valor numérico válido para ID de Usuario.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al actualizar el usuario (empleado): " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void limpiarCampos() {
        txtID.setText(PLACEHOLDER_ID);
        txtID.setForeground(Color.GRAY);
        txtNombreCompleto.setText(PLACEHOLDER_NOMBRE);
        txtNombreCompleto.setForeground(Color.GRAY);
        txtEmpresa.setText(PLACEHOLDER_ESTADO_UNION_EMPRESA);
        txtEmpresa.setForeground(Color.GRAY);
        txtCargo.setText(PLACEHOLDER_CARGO);
        txtCargo.setForeground(Color.GRAY);
        txtSalario.setText(PLACEHOLDER_CONTRASENA);
        txtSalario.setForeground(Color.GRAY);

    }
}