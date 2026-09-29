package Vista;

import ManejoBase.Conexion;
import ManejoBase.Historialsg;
import ManejoBase.JoinInventario;
import ManejoBase.SesionUsuario;
import java.sql.*;
import javax.swing.SwingWorker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
//Alison vicente
//Melvin Rodriguez

public class Dashboard extends JFrame implements ActionListener {

    JoinInventario joinInventarioLocal;
    //inicio elementos de menu hamburguesa
    JTable mostraract;
    JScrollPane scrollPaneActividad;
    JPanel menuItemsPanel;
    JButton btnDashboard;
    JButton btnInventario;
    JButton btnHistorial;
    JButton btnConfiguracion;
    JButton btnEmpleados;
    JButton btnCerrarSesion;
    JButton btnInventarios;
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
    //final
    JButton btnPregunta;
    JLabel lbltitulo;
    JLabel lblInventario;
    JLabel lblActividad;
    JLabel lblTodo;
    JLabel rayas;
    JLabel x;
    JPanel pnlFolder;
    JPanel pnlTotal;
    JPanel pnlValor;
    JPanel pnlItems;
    JSeparator lineaTitulo;
    private ImageIcon iconFolder;
    private ImageIcon iconValor;
    private ImageIcon iconItem;
    private ImageIcon iconTotal;
    private ImageIcon iconRayas;
    private ImageIcon iconX;
    private String rolUsuario;
    private int idEmpresaUsuario;
    private JPanel pnlPregunta;

    // Dimensiones de la tabla y el panel de preguntas
    private final int TABLA_X = 120;
    private final int TABLA_Y = 480;
    private final int TABLA_ALTURA = 200;
    private final int TABLA_ANCHO_COMPLETO = 1190; // Ancho original de la tabla
    private final int TABLA_ANCHO_REDUCIDO = 700; // Ancho de la tabla cuando pnlPregunta está visible

    private final int PNL_PREGUNTA_ANCHO = 450;
    private final int PNL_PREGUNTA_ALTO = 200;
    private final int PNL_PREGUNTA_X_OCULTO = 1366; // Fuera de la vista a la derecha
    private final int PNL_PREGUNTA_X_VISIBLE = TABLA_X + TABLA_ANCHO_REDUCIDO + 30; // Posición al lado de la tabla reducida

    //fuente
    Font bt = new Font("Segoe UI", Font.BOLD, 14);
    Historialsg historialsg;
    Conexion conexion;
//para la barrita de arriba
    JButton xs;
    JButton m;

    //contructor principal de la clase
    public Dashboard() {
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
        idEmpresaUsuario = sesion.getIdEmpresa();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1366, 768);
        try {
            conexion = new Conexion();

            historialsg = new Historialsg();
            obj(); // Inicializa componentes Swing
            agr(); // Agrega componentes a la ventana
            posicionar(); // Posiciona los componentes

            configurarMenuHamburguesa();
            if (!"Administrador".equalsIgnoreCase(rolUsuario)) {
                btnEmpleados.setVisible(false);
            }
            this.setVisible(true);
            this.setLocationRelativeTo(null);

        } catch (RuntimeException e) {
            e.printStackTrace(); // Imprime la traza completa de la pila
            JOptionPane.showMessageDialog(null,
                    "No se pudo iniciar el Dashboard.\n"
                    + "Por favor, verifique la conexión a la base de datos y consulte la consola para más detalles.\n"
                    + "Error: " + e.getMessage(),
                    "Error de Inicialización Crítica",
                    JOptionPane.ERROR_MESSAGE);
            // Salir de la aplicación si hay un error crítico al inicio
            System.exit(1);
        } catch (Exception e) {
            // Capturar otras excepciones inesperadas
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Ha ocurrido un error inesperado al iniciar el Dashboard.\n"
                    + "Por favor, consulte la consola para más detalles.\n"
                    + "Error: " + e.getMessage(),
                    "Error Inesperado",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        cargarActividadReciente();
    }

    public static void main(String[] args) {
        new Dashboard();
    }

    public void posicionar() {
        // La tabla inicia con el ancho completo
        scrollPaneActividad.setBounds(TABLA_X, TABLA_Y, TABLA_ANCHO_COMPLETO, TABLA_ALTURA);

        // El panel de preguntas inicia fuera de la vista
        pnlPregunta.setBounds(PNL_PREGUNTA_X_OCULTO, TABLA_Y, PNL_PREGUNTA_ANCHO, PNL_PREGUNTA_ALTO);

        lateral.setBounds(0, 0, ANCHO_MINIMO_MENU, this.getHeight());
        lbltitulo.setBounds(120, 25, 600, 70);
        lineaTitulo.setBounds(120, 95, 1190, 25);

        pnlFolder.setBounds(327, 200, 190, 175);
        pnlItems.setBounds(537, 200, 190, 175);
        pnlTotal.setBounds(747, 200, 190, 175);
        pnlValor.setBounds(957, 200, 190, 175);
        lblInventario.setBounds(120, 120, 200, 20);
        lblActividad.setBounds(120, 430, 200, 20);
        lblTodo.setBounds(948, 430, 200, 20);
        rayas.setBounds(988, 390, 200, 100);
        x.setBounds(1045, 390, 200, 100);
        btnPregunta.setBounds(1180, 420, 130, 40);
        pR2.setBounds(10, 20, ANCHO_MINIMO_MENU - 20, LOGO_CONTRAIDO_ALTO);
        Actualizartamañoicono();
        menuItemsPanel.setBounds(0, pR2.getY() + pR2.getHeight() + 10,
                ANCHO_MAXIMO_MENU,
                this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));
    }
    // inicializar los componetes 

    public void obj() {
        pnl();
        lbl();
        btn();
        icon();
        linea();
        pnl2();
        img();
        tab();
        crearPanelPregunta();
        barritaarriba();
    }

    public void barritaarriba() {
        xs = new JButton("x");
        m = new JButton("-");
        this.add(m);
        this.add(xs);
        xs.setBackground(Color.decode("#F7F8FC"));
        m.setBackground(Color.decode("#F7F8FC"));
        xs.setForeground(Color.decode("#6A7790"));
        m.setForeground(Color.decode("#6A7790"));
        xs.setBounds(1315, 0, 50, 25);
        m.setBounds(1265, 0, 50, 25);
        m.addActionListener(this);
        xs.addActionListener(this);
        xs.setBorder(null);
        m.setBorder(null);
    }

    public void agr() {
        add(lbltitulo);
        add(lineaTitulo);
        add(pnlFolder);
        add(pnlTotal);
        add(pnlValor);
        add(pnlItems);
        add(lblInventario);
        add(lblActividad);
        add(lblTodo);
        add(rayas);
        add(x);
        add(btnPregunta);
        add(lateral);
        //inicio
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
        //final
        add(scrollPaneActividad);
        add(pnlPregunta);
    }
// inicializar los LABEL  

    public void lbl() {
        pR2 = new JLabel();
        pR2.setHorizontalAlignment(SwingConstants.CENTER);
        pR2.setVerticalAlignment(SwingConstants.CENTER);
        lbltitulo = new JLabel("Dashboard");
        lbltitulo.setFont(new Font("Segoe UI", Font.BOLD, 44));
        lbltitulo.setForeground(new Color(106, 119, 147));
        lblInventario = new JLabel("Conteo de inventario");
        lblInventario.setFont(bt);
        lblInventario.setForeground(new Color(106, 119, 147));
        lblActividad = new JLabel("Actividad Reciente:");
        lblActividad.setFont(bt);
        lblActividad.setForeground(new Color(106, 119, 147));
        lblTodo = new JLabel("Toda Actividad");
        lblTodo.setFont(bt);
        lblTodo.setForeground(new Color(106, 119, 147));
        lblTodo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    new Historial();
                    dispose();
                    if (menuExpandido) {
                        toggleMenu();
                    }
                }
            }
        });
    }

    // El separador 
    public void linea() {
        lineaTitulo = new JSeparator();
        lineaTitulo.setBackground(Color.decode("#B4A8AA"));
        separatorMenu = new JSeparator(JSeparator.HORIZONTAL);
        separatorMenu.setForeground(Color.decode("#B4A8AA"));
        separatorMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    }

    // para inicializar las imaenes y el contero dentro del panel 
    public void pnl2() {
        // Carga los iconos desde los recursos del classpath
        try {
            iconFolder = new ImageIcon(getClass().getResource("/Imagenes/folder.png"));
            iconValor = new ImageIcon(getClass().getResource("/Imagenes/valor.png"));
            iconItem = new ImageIcon(getClass().getResource("/Imagenes/items.png"));
            iconTotal = new ImageIcon(getClass().getResource("/Imagenes/total.png"));

            // Verificar si las imágenes se cargaron correctamente
            if (iconFolder.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Error al cargar 'Imagenes/folder.png': Imagen incompleta.");
                iconFolder = new ImageIcon(); // Asigna un icono vacío o de error
            }
            if (iconValor.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Error al cargar 'Imagenes/valor.png': Imagen incompleta.");
                iconValor = new ImageIcon();
            }
            if (iconItem.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Error al cargar 'Imagenes/items.png': Imagen incompleta.");
                iconItem = new ImageIcon();
            }
            if (iconTotal.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Error al cargar 'Imagenes/total.png': Imagen incompleta.");
                iconTotal = new ImageIcon();
            }

        } catch (Exception e) {
            System.err.println("Error al cargar iconos: " + e.getMessage());
            // En caso de error, inicializa los iconos a nulo o a un icono de error genérico
            iconFolder = new ImageIcon();
            iconValor = new ImageIcon();
            iconItem = new ImageIcon();
            iconTotal = new ImageIcon();
        }

        // los nombres que llearan dentro del PANEL
        pnlFolder = CrearPaneles("Grupos", "3", iconFolder);
        pnlItems = CrearPaneles("Items", "-- Cargando --", iconItem);
        pnlTotal = CrearPaneles("Cantidad total", "-- Cargando --", iconTotal);
        pnlValor = CrearPaneles("Total Valor", "GTQ -- Cargando --", iconValor);

        // Para le verificacion de la conexion si esta conectada correcta mente 
        new SwingWorker<String[], Void>() {
            @Override
            protected String[] doInBackground() throws Exception {
                joinInventarioLocal = new JoinInventario(conexion);
                Connection conn = null;
                try {
                    conn = conexion.getConnection(); // Utiliza TU instancia de Conexion
                    if (conn == null) {
                        throw new SQLException("No se pudo establecer la conexión a la base de datos.");
                    }
                    String itemsCount = String.valueOf(joinInventarioLocal.getTotalItemsCount(idEmpresaUsuario));
                    String totalQuantity = String.valueOf(joinInventarioLocal.getTotalQuantity(idEmpresaUsuario));
                    double valueSum = joinInventarioLocal.getTotalValue(idEmpresaUsuario);
                    String totalValue = String.format("GTQ %.2f", valueSum);
                    return new String[]{itemsCount, totalQuantity, totalValue};
                } finally {
                    if (conn != null) {
                        try {
                            conn.close(); // Cierra la conexión cuando termines
                        } catch (SQLException ex) {
                            System.err.println("Error al cerrar la conexión: " + ex.getMessage());
                        }
                    }
                }
            }

            // para cargar la informacion de los conteos
            @Override
            protected void done() {
                try {
                    String[] results = get(); // Obtiene los resultados del doInBackground
                    String itemsCount = results[0];
                    String totalQuantity = results[1];
                    String totalValue = results[2];

                    updatePanelLabels(pnlItems, itemsCount, iconItem);
                    updatePanelLabels(pnlTotal, totalQuantity, iconTotal);
                    updatePanelLabels(pnlValor, totalValue, iconValor);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Dashboard.this,
                            "Error al cargar los datos del inventario: " + ex.getMessage(),
                            "Error de Carga",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    updatePanelLabels(pnlItems, "Error", iconItem);
                    updatePanelLabels(pnlTotal, "Error", iconTotal);
                    updatePanelLabels(pnlValor, "Error", iconValor);
                } finally {
                    Dashboard.this.revalidate(); // Asegura que los cambios se reflejen
                    Dashboard.this.repaint();
                }
            }
        }.execute();
    }

    // Nuevo método auxiliar para actualizar los JLabels dentro de un JPanel existente
    private void updatePanelLabels(JPanel panel, String newQuantity, Icon newIcon) {

        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;

                if (label.getFont().equals(new Font("Segoe UI", Font.BOLD, 24))) {
                    label.setText(newQuantity);
                } else if (label.getIcon() != null && newIcon != null && label.getIcon() instanceof ImageIcon && newIcon instanceof ImageIcon) {
                  
                }
            }
        }
    }

    // para poner las imagenes en lso LABEL 
    public void img() {
        try {
            originalLogoIcon = new ImageIcon(getClass().getResource("/Imagenes/logo.png"));
            if (originalLogoIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Error al cargar 'Imagenes/logo.png': Imagen incompleta.");
                originalLogoIcon = new ImageIcon();
            }
        } catch (Exception e) {
            System.err.println("Error al cargar 'Imagenes/logo.png': " + e.getMessage());
            originalLogoIcon = new ImageIcon();
        }
    }

    // inicializar el apartado de preguntas 
    public void btn() {
        btnPregunta = new JButton("Pregunta?");
        btnPregunta.setBackground(new Color(106, 119, 147));
        btnPregunta.setForeground(Color.WHITE);
        forma(btnPregunta);

        btnPregunta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al hacer clic, alternar visibilidad y ajustar la UI
                if (pnlPregunta.getX() == PNL_PREGUNTA_X_OCULTO) { // Si está oculto
                    animarPanel(pnlPregunta, PNL_PREGUNTA_X_OCULTO, PNL_PREGUNTA_X_VISIBLE, true);
                    animarTabla(scrollPaneActividad, TABLA_ANCHO_COMPLETO, TABLA_ANCHO_REDUCIDO);
                } else { // Si está visible
                    animarPanel(pnlPregunta, PNL_PREGUNTA_X_VISIBLE, PNL_PREGUNTA_X_OCULTO, false);
                    animarTabla(scrollPaneActividad, TABLA_ANCHO_REDUCIDO, TABLA_ANCHO_COMPLETO);
                }
            }
        });

        // cargar el texto y los botones 
        btnDashboard = createMenuItem("Dashboard", "/Imagenes/panel-de-control.png");
        btnInventarios = createMenuItem("Inventario", "/Imagenes/alt-de-inventario.png");
        btnHistorial = createMenuItem("Historial", "/Imagenes/calendario-reloj.png");
        btnConfiguracion = createMenuItem("Configuración", "/Imagenes/alt-administrador.png");
        btnEmpleados = createMenuItem("Empleados", "/Imagenes/empleados.png");
        btnCerrarSesion = createMenuItem("Cerrar Sesión", "/Imagenes/cierre-de-sesion-de-usuario.png");
    }

    // para cargar las iamgenes 
    public void icon() {
        try {
            iconRayas = new ImageIcon(getClass().getResource("/Imagenes/rayas.png"));
            rayas = new JLabel(iconRayas);

            iconX = new ImageIcon(getClass().getResource("/Imagenes/equis.png"));
            x = new JLabel(iconX);
            
            // Verificación adicional de carga
            if (iconRayas.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Error al cargar 'Imagenes/rayas.png': Imagen incompleta.");
                iconRayas = new ImageIcon();
            }
            if (iconX.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Error al cargar 'Imagenes/equis.png': Imagen incompleta.");
                iconX = new ImageIcon();
            }

        } catch (Exception e) {
            System.err.println("Error al cargar iconos auxiliares: " + e.getMessage());
            iconRayas = new ImageIcon();
            iconX = new ImageIcon();
        }
    }
    // cargar los datos en la tabla , se obtiene en el historial 

    public void tab() {
        mostraract = new JTable();
        mostraract.setFillsViewportHeight(true);
        mostraract.setRowHeight(25);
        mostraract.getTableHeader().setReorderingAllowed(false);
        mostraract.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        mostraract.getTableHeader().setBackground(new Color(230, 230, 230));
        mostraract.getTableHeader().setForeground(new Color(106, 119, 147));

        scrollPaneActividad = new JScrollPane(mostraract);
        scrollPaneActividad.setBorder(BorderFactory.createEmptyBorder());
    }

    // cargar la informacion 
    private void cargarActividadReciente() {
        DefaultTableModel tempModel = new DefaultTableModel(new Object[]{"Fecha", "Acción", "Detalle"}, 0);
        tempModel.addRow(new Object[]{"Cargando datos, por favor espere...", "", ""});
        mostraract.setModel(tempModel);

        new SwingWorker<DefaultTableModel, Void>() {
            @Override
            protected DefaultTableModel doInBackground() throws Exception {
                return historialsg.obtenerlosmovimientos();
            }

            @Override
            protected void done() {
                try {
                    DefaultTableModel modeloHistorial = get();
                    mostraract.setModel(modeloHistorial);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Dashboard.this,
                            "Error al cargar la actividad reciente: " + ex.getMessage(),
                            "Error de Carga",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    DefaultTableModel errorModel = new DefaultTableModel(new Object[]{"Fecha", "Acción", "Detalle"}, 0);
                    errorModel.addRow(new Object[]{"No se pudieron cargar los datos.", "", ""});
                    mostraract.setModel(errorModel);
                }
            }
        }.execute();
    }

    // inicializar el panel 
    public void pnl() {
        lateral = new JPanel();
        lateral.setBackground(Color.decode("#CFD1E0"));
        lateral.setLayout(null);
        menuItemsPanel = new JPanel();
        menuItemsPanel.setBackground(Color.decode("#CFD1E0"));
        menuItemsPanel.setLayout(new BoxLayout(menuItemsPanel, BoxLayout.Y_AXIS));
        menuItemsPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
    }
    // creacion de la ventana emergente 

    private void crearPanelPregunta() {
        pnlPregunta = new JPanel();
        pnlPregunta.setLayout(new BorderLayout(10, 10));
        pnlPregunta.setBackground(Color.WHITE);
        pnlPregunta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(106, 119, 147), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        // Inicialmente no es visible
        // pnlPregunta.setVisible(false); // Ya se oculta moviéndolo fuera de la pantalla

        JLabel lblTituloPregunta = new JLabel("Centro de Ayuda / Preguntas Frecuentes");
        lblTituloPregunta.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTituloPregunta.setForeground(new Color(50, 50, 50));
        lblTituloPregunta.setHorizontalAlignment(SwingConstants.CENTER);
        pnlPregunta.add(lblTituloPregunta, BorderLayout.NORTH);

        // apartado que se mostrara en la ventana emergente 
        JTextArea txtContenido = new JTextArea(
                "¡Bienvenido al Centro de Ayuda!\n\n"
                + "Aquí encontrarás respuestas a las preguntas más comunes sobre el uso de la aplicación.\n\n"
                + "1. ¿Cómo agrego un nuevo producto?\n"
                + "   Ve al apartado 'Inventario' y selecciona una de las siguientes categorías para asignar productos:\n"
                + "   - Materia Prima\n"
                + "   - Productos Terminados\n"
                + "   - Material de Empaque\n\n"
                + "2. ¿Dónde veo el historial de movimientos?\n"
                + "   En la sección 'Historial' podrás consultar todas las actividades\n"
                + "   y exportarlas a un archivo de Excel.\n\n"
                + "3. ¿Para qué sirve el campo ID?\n"
                + "   El campo 'ID', ubicado abajo de inventario 'Eliminar o actualizar', se utiliza para escribir\n"
                + "   el identificador del producto que deseas modificar o eliminar.\n\n"
                + "4. ¿Qué se puede hacer en la sección de Configuración?\n"
                + "   Puedes cambiar el logo de la empresa , ver la informacion de la empresa y personal  .\n\n"
                + "Para asistencia adicional, por favor contacta al soporte técnico:\n"
                + "  ️ soportetecnico@scentra.com\n"
                + "   +502 5789 4063"
        );

        txtContenido.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtContenido.setForeground(new Color(80, 80, 80));
        txtContenido.setLineWrap(true);
        txtContenido.setWrapStyleWord(true);
        txtContenido.setEditable(false);
        txtContenido.setBackground(Color.WHITE);
        JScrollPane scrollTxt = new JScrollPane(txtContenido);
        scrollTxt.setBorder(BorderFactory.createEmptyBorder());

        pnlPregunta.add(scrollTxt, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(106, 119, 147));
        btnCerrar.setForeground(Color.WHITE);
        forma(btnCerrar);
        btnCerrar.addActionListener(e -> {
            animarPanel(pnlPregunta, PNL_PREGUNTA_X_VISIBLE, PNL_PREGUNTA_X_OCULTO, false);
            animarTabla(scrollPaneActividad, TABLA_ANCHO_REDUCIDO, TABLA_ANCHO_COMPLETO);
        });

        JPanel pnlBotonCerrar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBotonCerrar.setBackground(Color.WHITE);
        pnlBotonCerrar.add(btnCerrar);
        pnlPregunta.add(pnlBotonCerrar, BorderLayout.SOUTH);
    }
    
    // Método para la forma de los botones
    public void forma(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(button.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 40, 40);
                super.paint(g, c);
                g2.dispose();
            }
        });
    }

    // Método para animar el movimiento del panel
    private void animarPanel(JComponent component, int startX, int endX, boolean showAfterAnimation) {
        Timer timer = new Timer(5, new ActionListener() {
            int currentX = startX;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (startX < endX) { // Animando hacia la derecha (mostrar)
                    currentX += 15; // Velocidad de la animación
                    if (currentX >= endX) {
                        currentX = endX;
                        ((Timer) e.getSource()).stop();
                    }
                } else { // Animando hacia la izquierda (ocultar)
                    currentX -= 15; // Velocidad de la animación
                    if (currentX <= endX) {
                        currentX = endX;
                        ((Timer) e.getSource()).stop();
                    }
                }
                component.setLocation(currentX, component.getY());
                revalidate();
                repaint();
            }
        });
        timer.start();
    }

    // Método para animar el cambio de ancho de la tabla
    private void animarTabla(JComponent component, int startWidth, int endWidth) {
        Timer timer = new Timer(5, new ActionListener() {
            int currentWidth = startWidth;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (startWidth < endWidth) { // Aumentando ancho
                    currentWidth += 15;
                    if (currentWidth >= endWidth) {
                        currentWidth = endWidth;
                        ((Timer) e.getSource()).stop();
                    }
                } else { // Disminuyendo ancho
                    currentWidth -= 15;
                    if (currentWidth <= endWidth) {
                        currentWidth = endWidth;
                        ((Timer) e.getSource()).stop();
                    }
                }
                component.setSize(currentWidth, component.getHeight());
                revalidate();
                repaint();
            }
        });
        timer.start();
    }

    public JPanel CrearPaneles(String titulo, String cantidad, Icon icon) {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblCantidad = new JLabel(cantidad);
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblCantidad.setForeground(Color.BLACK);
        lblCantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnl.add(Box.createVerticalStrut(20));
        pnl.add(lblIcon);
        pnl.add(Box.createVerticalStrut(8));
        pnl.add(lblCantidad);
        pnl.add(Box.createVerticalStrut(5));
        pnl.add(lblTitulo);
        pnl.add(Box.createVerticalGlue());
        return pnl;
    }

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
                            Dashboard.this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));
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
            // Cargar la imagen desde los recursos del classpath
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));
            if (originalIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                throw new Exception("Icono no cargado completamente: " + iconPath);
            }
            Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            menuItem.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("Error al cargar el icono para " + text + ": " + iconPath + " - " + e.getMessage());
            // Si el ícono específico falla, intentar con el ícono de logo por defecto
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
        if (e.getSource() == xs) {
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
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que quieres cerrar la sesión?", "Confirmar Cierre de Sesión",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new Inicio_de_sesion();
                SesionUsuario.getInstance().cerrarsesion();
                this.dispose();

            }
        }
    }
}
