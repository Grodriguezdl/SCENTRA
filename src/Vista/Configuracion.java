package Vista;

//importaciones
import ManejoBase.ActualizarLogo;
import ManejoBase.Actualizarinfoempr;
import ManejoBase.Actualizarinfopersonal;
import ManejoBase.Manejoinfo;
import ManejoBase.SesionUsuario;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.util.List;
import java.io.File;
import java.net.URL;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

public class Configuracion extends JFrame implements ActionListener {

    //Gabriel Ricardo Rodriguez de León
    //Crea los componentes del menú
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
    //final
    //componentes de la clase
    JTextField Iniciales;//empresa
    JTextField Nombreempresa;//empresa
    JTextField Nombreusuario;//usuario
    JTextField Contraseña;
    JLabel pR;
    JPanel infemp;
    JPanel infpe;
    JPanel Logoa;
    JButton actLog;
    JButton Actempr;
    JButton Actper;
    JComboBox CmbRol;//usuario, no es modificable
    JComboBox CmbColorIndustria;//empresa
    JComboBox CmbTipoIndustria;//empresa
    JSeparator linea;
    JLabel Titulo;
    JLabel infempr;
    JLabel infper;
    JLabel actlogo;
    JLabel Nombreemp;
    JLabel Inicem;
    JLabel Coloremp;
    JLabel Tipoempr;
    JLabel Nombreusu;
    JLabel Contrasenaus;
    JLabel Rolus;
    //Fuentes de texto
    Font b = new Font("Segoe UI", Font.BOLD, 50);
    JPanel lateral;//Panel
    Font bt = new Font("Segoe UI", Font.BOLD, 14);
    //subtitulos
    Font s = new Font("Segoe UI", Font.BOLD, 24);
    Font mini = new Font("Segoe UI", Font.BOLD, 12);
    String rolUsuario;
    int idEmpresaUsuario;
    JLabel codempr;
    Actualizarinfoempr actualizarEmpresa;
    Actualizarinfopersonal actualizarPersonal;
    ActualizarLogo logoUpd;
    //para la barrita de arriba
    JButton x;
    JButton m;
//Constructor principal de la clase que crea el Frame

    public Configuracion() {
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
        //metodos para agregar,posicionar,llenar, etc.
        obj();
        agr();
        posicionar();
        configurarMenuHamburguesa();
        llenarcmb();
        cargarinfo();
        accesorol();
        //detectar si es o no administrador
        if (!"Administrador".equalsIgnoreCase(rolUsuario)) {//equalsIgnoreCase para flexibilidad
            btnEmpleados.setVisible(false);
        }
        //visibilidad
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        actualizarEmpresa = new Actualizarinfoempr();
        actualizarPersonal = new Actualizarinfopersonal();
        logoUpd = new ActualizarLogo();
    }

    //Agrega componentes
    public void agr() {
        this.add(lateral);
        this.add(Titulo);
        this.add(linea);
        this.add(infemp);
        this.add(infpe);
        this.add(Logoa);
        //inicio  //menu hamburguesa
        lateral.add(menuItemsPanel);
        menuItemsPanel.add(Box.createVerticalStrut(20));
        menuItemsPanel.add(btnDashboard);
        menuItemsPanel.add(Box.createVerticalStrut(5));
        menuItemsPanel.add(btnInventario);
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
        //final
        infpe.add(Actper);
        infpe.add(infper);
        infemp.add(Actempr);
        infemp.add(infempr);
        Logoa.add(actlogo);
        Logoa.add(actLog);
        Logoa.add(pR);
        infemp.add(Nombreempresa);
        infemp.add(Iniciales);
        infemp.add(CmbColorIndustria);
        infemp.add(CmbTipoIndustria);
        infpe.add(CmbRol);
        infpe.add(Nombreusuario);
        infpe.add(Contraseña);
        infemp.add(Nombreemp);
        infemp.add(Inicem);
        infemp.add(Coloremp);
        infemp.add(Tipoempr);
        infpe.add(Nombreusu);
        infpe.add(Contrasenaus);
        infpe.add(Rolus);
        this.add(codempr);
    }

    //posiciona componentes
    public void posicionar() {
        lateral.setBounds(0, 0, ANCHO_MINIMO_MENU, this.getHeight());
        Titulo.setBounds(120, 25, 600, 70);
        linea.setBounds(120, 95, 1190, 25);
        Logoa.setBounds(930, 110, 400, 400);
        infemp.setBounds(170, 110, 750, 340);
        infpe.setBounds(170, 460, 750, 250);
        Actempr.setBounds(260, 290, 200, 35);
        Actper.setBounds(260, 200, 200, 35);
        infempr.setBounds(230, 10, 330, 30);
        infper.setBounds(240, 10, 330, 30);
        actlogo.setBounds(130, 10, 200, 30);
        actLog.setBounds(110, 350, 200, 35);
        Nombreempresa.setBounds(70, 75, 240, 25);
        CmbTipoIndustria.setBounds(400, 75, 240, 25);
        CmbColorIndustria.setBounds(70, 200, 240, 25);
        Iniciales.setBounds(400, 200, 240, 25);
        Nombreemp.setBounds(70, 50, 240, 25);
        Tipoempr.setBounds(400, 50, 240, 25);
        Coloremp.setBounds(70, 180, 240, 25);
        Inicem.setBounds(400, 180, 240, 25);
        Nombreusuario.setBounds(70, 75, 240, 25);
        CmbRol.setBounds(400, 75, 240, 25);
        Contraseña.setBounds(70, 150, 240, 25);
        // labels
        Nombreusu.setBounds(70, 50, 240, 25);
        Contrasenaus.setBounds(70, 125, 240, 25);
        Rolus.setBounds(400, 50, 240, 25);
        pR2.setBounds(10, 20, ANCHO_MINIMO_MENU - 20, LOGO_CONTRAIDO_ALTO);
        Actualizartamañoicono();
        menuItemsPanel.setBounds(0, pR2.getY() + pR2.getHeight() + 10,
                ANCHO_MAXIMO_MENU,
                this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));
        codempr.setBounds(1117, 510, 100, 25);
    }

//llama a los metodos que crean componentes
    public void obj() {
        lbl();
        pnl();
        Btn();
        separador();
        cmb();
        img();
        txt();
        Linea();
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

//crea separadores de texto
    public void separador() {
        linea = new JSeparator();
        linea.setBackground(Color.decode("#B4A8AA"));
    }
//crea TextField

    public void txt() {
        Iniciales = new JTextField();
        Nombreempresa = new JTextField();
        Nombreusuario = new JTextField();
        Contraseña = new JTextField();
        permitirletras(Iniciales);
        permitirletras(Nombreempresa);
        permitirletras(Nombreusuario);
        deshabilitarCopiarPegar(Iniciales);
        deshabilitarCopiarPegar(Nombreempresa);
        deshabilitarCopiarPegar(Nombreusuario);
        deshabilitarCopiarPegar(Contraseña);
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

//Separador de texto
    public void Linea() {
        separatorMenu = new JSeparator(JSeparator.HORIZONTAL);
        separatorMenu.setForeground(Color.decode("#B4A8AA"));
        separatorMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    }
//Crea comboBox

    public void cmb() {
        CmbColorIndustria = new JComboBox();
        CmbColorIndustria.setBackground(Color.decode("#FFFFFF"));
        CmbColorIndustria.setForeground(Color.BLACK);
        CmbColorIndustria.setFont(bt);
        CmbColorIndustria.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CmbColorIndustria.setOpaque(false);
        CmbTipoIndustria = new JComboBox();
        CmbTipoIndustria.setBackground(Color.decode("#FFFFFF"));
        CmbTipoIndustria.setForeground(Color.BLACK);
        CmbTipoIndustria.setFont(bt);
        CmbTipoIndustria.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CmbTipoIndustria.setOpaque(false);
        CmbRol = new JComboBox();
        CmbRol.setBackground(Color.decode("#FFFFFF"));
        CmbRol.setForeground(Color.BLACK);
        CmbRol.setFont(bt);
        CmbRol.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CmbRol.setOpaque(false);
        CmbRol.setEnabled(false);

    }
//Crea labels

    public void lbl() {
        pR2 = new JLabel();
        pR2.setHorizontalAlignment(SwingConstants.CENTER);
        pR2.setVerticalAlignment(SwingConstants.CENTER);
        // Carga el logo por defecto desde los recursos
        try {
            originalLogoIcon = new ImageIcon(getClass().getResource("/Imagenes/logo.png"));
            if (originalLogoIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Error al cargar 'Imagenes/logo.png': Imagen incompleta.");
                originalLogoIcon = new ImageIcon(); // Icono vacío si no carga bien
            }
        } catch (Exception e) {
            System.err.println("Error al cargar 'Imagenes/logo.png': " + e.getMessage());
            originalLogoIcon = new ImageIcon(); // Icono vacío en caso de error
        }

        Titulo = new JLabel("Configuración");
        Titulo.setFont(b);
        Titulo.setForeground(Color.decode("#6A7793"));
        infempr = new JLabel("Información de la empresa");
        infempr.setFont(s);
        infempr.setForeground(Color.decode("#6A7793"));
        infper = new JLabel("Información personal");
        infper.setFont(s);
        infper.setForeground(Color.decode("#6A7793"));
        actlogo = new JLabel("Actualizar logo");
        actlogo.setFont(s);
        actlogo.setForeground(Color.decode("#6A7793"));
        Nombreemp = new JLabel("Nombre de la empresa");
        Nombreemp.setFont(mini);
        Nombreemp.setForeground(Color.decode("#6A7793"));
        Inicem = new JLabel("Iniciales de la empresa");
        Inicem.setFont(mini);
        Inicem.setForeground(Color.decode("#6A7793"));
        Coloremp = new JLabel("Color de la empresa");
        Coloremp.setFont(mini);
        Coloremp.setForeground(Color.decode("#6A7793"));
        Tipoempr = new JLabel("Tipo de empresa");
        Tipoempr.setFont(mini);
        Tipoempr.setForeground(Color.decode("#6A7793"));
        Nombreusu = new JLabel("Usuario");
        Nombreusu.setFont(mini);
        Nombreusu.setForeground(Color.decode("#6A7793"));
        Contrasenaus = new JLabel("Contraseña");
        Rolus = new JLabel("Rol del usuario");
        codempr = new JLabel("-Codigo-");
        codempr.setFont(mini);
        codempr.setForeground(Color.decode("#6A7793"));
    }
//Crea botones

    public void Btn() {
        Actper = new JButton("Guardar cambios");
        Actper.setBackground(Color.decode("#6A7793"));
        Actper.setForeground(Color.white);
        Actper.setFont(bt);
        Actper.addActionListener(this);
        Actempr = new JButton("Guardar cambios");
        Actempr.setBackground(Color.decode("#6A7793"));
        Actempr.setForeground(Color.white);
        Actempr.setFont(bt);
        Actempr.addActionListener(this);
        actLog = new JButton("Actualizar");
        actLog.setBackground(Color.decode("#6A7793"));
        actLog.setForeground(Color.white);
        actLog.setFont(bt);
        actLog.addActionListener(this);
        forma();
        //inicio de asignacion de botones de menú
        btnDashboard = createMenuItem("Dashboard", "/Imagenes/panel-de-control.png");
        btnInventario = createMenuItem("Inventario", "/Imagenes/alt-de-inventario.png");
        btnHistorial = createMenuItem("Historial", "/Imagenes/calendario-reloj.png");
        btnConfiguracion = createMenuItem("Configuración", "/Imagenes/alt-administrador.png");
        btnEmpleados = createMenuItem("Empleados", "/Imagenes/empleados.png");
        btnCerrarSesion = createMenuItem("Cerrar Sesión", "/Imagenes/cierre-de-sesion-de-usuario.png");
        //final
    }

    //creacion de panel lateral o menu y otros
    public void pnl() {
        lateral = new JPanel();
        lateral.setBackground(Color.decode("#CFD1E0"));
        lateral.setLayout(null);
        infemp = new JPanel();
        infpe = new JPanel();
        Logoa = new JPanel();
        menuItemsPanel = new JPanel();
        infpe.setBackground(Color.white);
        Logoa.setBackground(Color.white);
        infemp.setBackground(Color.white);
        menuItemsPanel.setBackground(Color.decode("#CFD1E0"));
        infpe.setLayout(null);
        infemp.setLayout(null);
        Logoa.setLayout(null);
        menuItemsPanel.setLayout(new BoxLayout(menuItemsPanel, BoxLayout.Y_AXIS));
        menuItemsPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    //Maneja la forma redondeada de los componentes
    public void forma() {
        Actempr.setFocusPainted(false);
        Actempr.setBorderPainted(false);
        Actempr.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Actempr.setOpaque(false);
        Actempr.setContentAreaFilled(false);
        Actempr.addActionListener(this);
        Actempr.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Actempr.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 40, 40);
                super.paint(g, c);
                g2.dispose();
            }
        });
        Actper.setFocusPainted(false);
        Actper.setBorderPainted(false);
        Actper.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Actper.setOpaque(false);
        Actper.setContentAreaFilled(false);
        Actper.addActionListener(this);
        Actper.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Actper.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 40, 40);
                super.paint(g, c);
                g2.dispose();
            }
        });
        actLog.setFocusPainted(false);
        actLog.setBorderPainted(false);
        actLog.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actLog.setOpaque(false);
        actLog.setContentAreaFilled(false);
        actLog.addActionListener(this);
        actLog.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(actLog.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 40, 40);
                super.paint(g, c);
                g2.dispose();
            }
        });
    }
//maneja agregar logo a label y creacion del mismo

    public void img() {
        pR = new JLabel();
        pR.setBounds(75, 30, 270, 300);

    }
//cargar la información en los txt,cmb correspondientes

    private void cargarinfo() {
        Manejoinfo.Infoempresa companyInfo = Manejoinfo.getInfoempr(idEmpresaUsuario);
        if (companyInfo != null) {
            Nombreempresa.setText(companyInfo.nombreEmpresa);
            Iniciales.setText(companyInfo.inicialesEmpresa);
            CmbColorIndustria.setSelectedItem(companyInfo.colorIndustria);
            CmbTipoIndustria.setSelectedItem(companyInfo.tipoIndustria);
            codempr.setText(companyInfo.codigoEmpresa);

            String logoPathFromDB = companyInfo.logoPath;

            actualizarLogoDisplay(logoPathFromDB);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró información de la empresa.");
            Nombreempresa.setText("");
            Iniciales.setText("");
            CmbColorIndustria.setSelectedIndex(-1);
            CmbTipoIndustria.setSelectedIndex(-1);
            codempr.setText("-Código-");
            actualizarLogoDisplay(null);
        }
        // Obtener información del perfil del usuario actual
        Manejoinfo.perfilus userProfile = Manejoinfo.obtenerperfil(SesionUsuario.getInstance().getNombreusuario());
        if (userProfile != null) {
            // Rellenar campos del perfil de usuario
            Nombreusuario.setText(userProfile.nombreUsuario);
            Contraseña.setText(userProfile.contrasena);
            CmbRol.setSelectedItem(userProfile.rol);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontro información del usuario");
            // Limpiar campos si no se encuentra información
            Nombreusuario.setText("");
            Contraseña.setText("");
            CmbRol.setSelectedIndex(-1);
        }
    }

    private void llenarcmb() {
        // Llenar CmbTipoIndustria
        List<String> tipos = Manejoinfo.obttipos();
        CmbTipoIndustria.removeAllItems(); // Limpiar ítems existentes (si los hubiera)
        for (String tipo : tipos) {
            CmbTipoIndustria.addItem(tipo);
        }

        // Llenar CmbColorIndustria
        List<String> colores = Manejoinfo.obtcolores();
        CmbColorIndustria.removeAllItems();
        for (String color : colores) {
            CmbColorIndustria.addItem(color);
        }

        // Llenar CmbRol
        List<String> roles = Manejoinfo.obtroles();
        CmbRol.removeAllItems();
        for (String rol : roles) {
            CmbRol.addItem(rol);
        }
    }
//determina el que puede o no cambiar el usuario según su rol(con el que se inicia sesión)

    private void accesorol() {
        if ("Administrador".equalsIgnoreCase(rolUsuario)) {
            Nombreempresa.setEditable(true);
            Iniciales.setEditable(true);
            CmbColorIndustria.setEnabled(true);
            CmbTipoIndustria.setEnabled(true);
            Actempr.setEnabled(true);
            Nombreusuario.setEditable(true);
            Contraseña.setEditable(true);
            CmbRol.setEnabled(false);
            Actper.setEnabled(true);
            actLog.setEnabled(true);
            btnEmpleados.setVisible(true);

        } else if ("Empleado".equalsIgnoreCase(rolUsuario)) {
            Nombreempresa.setEditable(false);
            Iniciales.setEditable(false);
            CmbColorIndustria.setEnabled(false);
            CmbTipoIndustria.setEnabled(false);
            Actempr.setEnabled(false);
            Nombreusuario.setEditable(false);
            Contraseña.setEditable(true);
            CmbRol.setEnabled(false);
            Actper.setEnabled(true);
            actLog.setEnabled(false);
            btnEmpleados.setVisible(false);
            setMenuItemsVisibility(menuExpandido);
        } else {
            Nombreempresa.setEditable(false);
            Iniciales.setEditable(false);
            CmbColorIndustria.setEnabled(false);
            CmbTipoIndustria.setEnabled(false);
            Actempr.setEnabled(false);

            Nombreusuario.setEditable(false);
            Contraseña.setEditable(false);
            CmbRol.setEnabled(false);
            Actper.setEnabled(false);
            actLog.setEnabled(false);
            btnEmpleados.setVisible(false);
            setMenuItemsVisibility(menuExpandido);
        }
    }
//cambia el tamaño del logo al expandir y contraer

    private void Actualizartamañoicono() {
        if (originalLogoIcon != null) {
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
//animación menu

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
                            Configuracion.this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));

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
//nueva función

    private JButton createMenuItem(String text, String iconPath) {
        //para mostrar el nombre y icono
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
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));
            if (originalIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                throw new Exception("Icono no cargado completamente: " + iconPath);
            }
            Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            menuItem.setIcon(new ImageIcon(scaledImage));
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
//metodo,copia y pega

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

    //funcion, copia y pega
    private String getOriginalButtonText(JButton button) {
        if (button == btnDashboard) {
            return "Dashboard";
        }
        if (button == btnInventario) {
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

    //nuevo metodo
    private void toggleMenu() {
        menuExpandido = !menuExpandido;
        if (!menuExpandido) {
            setMenuItemsVisibility(false);
        }
        animacionTimer.start();
    }

    private void actualizarLogoDisplay(String logoFileName) {
        ImageIcon newLogoIcon = null;
        if (logoFileName != null && !logoFileName.isEmpty()) {
            try {
                String currentDirectory = System.getProperty("user.dir");
                String imageFolderPath = currentDirectory + File.separator + "Imagenes";
                File logoFile = new File(imageFolderPath + File.separator + logoFileName);

                if (logoFile.exists()) {
                    newLogoIcon = new ImageIcon(logoFile.getAbsolutePath());
                    if (newLogoIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                        System.err.println("Error al cargar la imagen del logo desde el sistema de archivos: Imagen incompleta.");
                        newLogoIcon = null;
                    }
                } else {
                    System.err.println("Advertencia: No se encontró la imagen del logo en el sistema de archivos: " + logoFile.getAbsolutePath());
                    newLogoIcon = null;
                }
            } catch (Exception e) {
                System.err.println("Error al cargar el logo específico " + logoFileName + ": " + e.getMessage());
                newLogoIcon = null;
            }
        }

        if (newLogoIcon == null || newLogoIcon.getImage() == null) {
            try {
                newLogoIcon = new ImageIcon(getClass().getResource("/Imagenes/logo.png"));
                if (newLogoIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                    System.err.println("Error al cargar el logo por defecto: Imagen incompleta.");
                    newLogoIcon = new ImageIcon();
                }
            } catch (Exception e) {
                System.err.println("Error al cargar el logo por defecto: " + e.getMessage());
                newLogoIcon = new ImageIcon();
            }
        }

        if (pR != null) {
            if (newLogoIcon != null && newLogoIcon.getImage() != null) {
                int targetWidth = pR.getWidth() > 0 ? pR.getWidth() : 270;
                int targetHeight = pR.getHeight() > 0 ? pR.getHeight() : 300;
                Image scaledImagePR = newLogoIcon.getImage().getScaledInstance(
                        targetWidth, targetHeight, Image.SCALE_SMOOTH);
                pR.setIcon(new ImageIcon(scaledImagePR));
            } else {
                pR.setIcon(null);
            }
            pR.revalidate();
            pR.repaint();
        }
    }

    public static void main(String[] args) {
        new Configuracion();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == x) {
            System.exit(0);
        }
        if (e.getSource() == m) {
            this.setState(JFrame.ICONIFIED);
        }
        if (e.getSource() == btnDashboard) {
            new Dashboard();
            this.dispose();
            if (menuExpandido) {
                toggleMenu();
            }
        } else if (e.getSource() == btnInventario) {
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
        } else if (e.getSource() == Actempr) {
            String nombre = Nombreempresa.getText();
            String tipo = (String) CmbTipoIndustria.getSelectedItem();
            String color = (String) CmbColorIndustria.getSelectedItem();
            String iniciales = Iniciales.getText();

            if (nombre.isEmpty() || tipo == null || color == null || iniciales.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos de información de la empresa.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean exito = actualizarEmpresa.actualizarDatosUsuario(nombre, tipo, color, iniciales);

            if (exito) {
                JOptionPane.showMessageDialog(this, "La información de la empresa se actualizó correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarinfo();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la información de la empresa. Consulta los logs para más detalles.", "Error de Actualización", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == Actper) {
            int idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();
            String nuevoNombreUsuario = Nombreusuario.getText();
            String nuevaContrasena = Contraseña.getText();

            if (nuevoNombreUsuario.isEmpty() || nuevaContrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete el nombre de usuario y la contraseña.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (nuevaContrasena.length() < 6) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres.", "Contraseña Inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean exito = actualizarPersonal.actualizarDatosUsuario(
                    idUsuarioActual,
                    nuevoNombreUsuario,
                    nuevaContrasena
            );

            if (exito) {
                JOptionPane.showMessageDialog(this, "Su información personal se actualizó correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarinfo();
                SesionUsuario.getInstance().setNombreusuario(nuevoNombreUsuario);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar su información personal. Consulta los logs para más detalles.", "Error de Actualización", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == actLog) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Archivos de Imagen (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                if (selectedFile.length() > 5 * 1024 * 1024) {
                    JOptionPane.showMessageDialog(this, "El archivo de logo es demasiado grande (máximo 5MB).", "Archivo Grande", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String newLogoFileName = logoUpd.procesarNuevoLogo(selectedFile, idEmpresaUsuario);

                if (newLogoFileName != null) {
                    JOptionPane.showMessageDialog(this, "Logo actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarLogoDisplay(newLogoFileName);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el logo. Asegúrese de que el archivo es una imagen válida y que tiene permisos de escritura.", "Error de Actualización", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }
}
