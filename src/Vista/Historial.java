package Vista;

import ManejoBase.Historialsg;
import ManejoBase.SesionUsuario;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.*;
import java.time.format.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import java.sql.Timestamp;
import java.net.URL;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Historial extends JFrame implements ActionListener, ItemListener {

    JScrollPane scpnl;
    JSeparator separatorMenu;
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
    JLabel imgexportar;
    JLabel pR2;
    JTable tabla;
    JButton Exportar;
    JComboBox<String> CmbRangof;
    JSeparator linea;
    JLabel Titulo;
    Font b = new Font("Segoe UI", Font.BOLD, 50);
    JPanel lateral;
    Font bt = new Font("Segoe UI", Font.BOLD, 14);
    Font bt2 = new Font("Segoe IU", Font.PLAIN, 14);
    Font bt3 = new Font("Segoe IU", Font.PLAIN, 15);
    private String rolUsuario;
    private int idEmpresaUsuario;
    Historialsg historialvis;
    JButton x;
    JButton m;

    public Historial() {
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
        historialvis = new Historialsg();
        obj();
        agr();
        posicionar();
        configurarMenuHamburguesa();
        cargardatostabla(0);
        if (!"Administrador".equalsIgnoreCase(rolUsuario)) {
            btnEmpleados.setVisible(false);
        }
        this.setVisible(true);
        this.setLocationRelativeTo(null);
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

        CmbRangof.setBounds(970, 110, 330, 25);
        Exportar.setBounds(170, 110, 130, 35);

        scpnl.setBounds(120, 160, 1180, 550);

    }

    public void agr() {
        this.add(lateral);
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
        this.add(Titulo);
        this.add(linea);
        this.add(CmbRangof);
        this.add(Exportar);
        this.add(scpnl);
        this.add(imgexportar);
    }

    public void obj() {
        lbl();
        pnl();
        Btn();
        separador();
        cmb();
        img();
        Tabla();
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

    public void lbl() {
        pR2 = new JLabel();
        pR2.setHorizontalAlignment(SwingConstants.CENTER);
        pR2.setVerticalAlignment(SwingConstants.CENTER);
        Titulo = new JLabel("Historial");
        Titulo.setFont(b);
        Titulo.setForeground(Color.decode("#6A7793"));

    }

    public void Linea() {
        separatorMenu = new JSeparator(JSeparator.HORIZONTAL);
        separatorMenu.setForeground(Color.decode("#B4A8AA"));
        separatorMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    }

    public void Tabla() {
        tabla = new JTable();
        tabla.setFont(bt2);
        tabla.setBackground(Color.decode("#FFFFFF"));
        tabla.setForeground(Color.BLACK);
        tabla.setRowHeight(28);
        tabla.setShowGrid(true);
        tabla.setGridColor(Color.BLACK);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JTableHeader titulo = tabla.getTableHeader();
        titulo.setFont(bt3);
        titulo.setBackground(new Color(116, 119, 147));
        titulo.setForeground(Color.white);
        titulo.setReorderingAllowed(false);
        titulo.setResizingAllowed(true);
        titulo.setPreferredSize(new Dimension(titulo.getWidth(), 30));
        DefaultTableCellRenderer hr = (DefaultTableCellRenderer) titulo.getDefaultRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        tabla.setDefaultRenderer(Object.class, cr);
        scpnl = new JScrollPane(tabla);
        scpnl.getViewport().setBackground(Color.decode("#FFFFFF"));
    }

    public void cargardatostabla(int rangeOption) {
        DefaultTableModel mdl;
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = null;

        switch (rangeOption) {
            case 0:
                startDate = endDate.minusWeeks(1);
                break;
            case 1:
                startDate = endDate.minusMonths(1);
                break;
            case 2:
                startDate = endDate.minusYears(1);
                break;
            case 3:
                startDate = null;
                endDate = null;
                break;
            default:
                startDate = null;
                endDate = null;
                break;
        }

        if (startDate != null && endDate != null) {
            mdl = historialvis.obtenerlosmovimientosPorRango(
                    Timestamp.valueOf(startDate.atStartOfDay()),
                    Timestamp.valueOf(endDate.atTime(LocalTime.MAX)));
        } else {
            mdl = historialvis.obtenerlosmovimientos();
        }
        tabla.setModel(mdl);

        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(120);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(180);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(8).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(9).setPreferredWidth(200);

        }
    }

    public void Btn() {
        btnDashboard = createMenuItem("Dashboard", "/Imagenes/panel-de-control.png");
        btnInventario = createMenuItem("Inventario", "/Imagenes/alt-de-inventario.png");
        btnHistorial = createMenuItem("Historial", "/Imagenes/calendario-reloj.png");
        btnConfiguracion = createMenuItem("Configuración", "/Imagenes/alt-administrador.png");
        btnEmpleados = createMenuItem("Empleados", "/Imagenes/empleados.png");
        btnCerrarSesion = createMenuItem("Cerrar Sesión", "/Imagenes/cierre-de-sesion-de-usuario.png");
        Exportar = new JButton("Exportar");
        Exportar.setBackground(Color.decode("#6A7793"));
        Exportar.setForeground(Color.white);
        Exportar.setFont(bt);
        Exportar.setFocusPainted(false);
        Exportar.setBorderPainted(false);
        Exportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Exportar.setOpaque(false);
        Exportar.setContentAreaFilled(false);
        Exportar.addActionListener(this);
        Exportar.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Exportar.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 40, 40);
                super.paint(g, c);
                g2.dispose();
            }
        });
    }

    public void separador() {
        linea = new JSeparator();
        linea.setBackground(Color.decode("#B4A8AA"));
    }

    public void pnl() {
        lateral = new JPanel();
        lateral.setBackground(Color.decode("#CFD1E0"));
        lateral.setLayout(null);
        menuItemsPanel = new JPanel();
        menuItemsPanel.setBackground(Color.decode("#CFD1E0"));
        menuItemsPanel.setLayout(new BoxLayout(menuItemsPanel, BoxLayout.Y_AXIS));
        menuItemsPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    public void fecha() {
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String semana = hoy.minusWeeks(1).format(formato) + " - " + hoy.format(formato);
        String mes = hoy.minusMonths(1).format(formato) + " - " + hoy.format(formato);
        String ano = hoy.minusYears(1).format(formato) + " - " + hoy.format(formato);
        CmbRangof.addItem("Última semana (" + semana + ")");
        CmbRangof.addItem("Último mes (" + mes + ")");
        CmbRangof.addItem("Último año(" + ano + ")");
        CmbRangof.addItem("Todos los registros");
    }

    public void cmb() {
        CmbRangof = new JComboBox<>();
        fecha();
        CmbRangof.setBackground(Color.decode("#FFFFFF"));
        CmbRangof.setForeground(Color.BLACK);
        CmbRangof.setFont(bt);
        CmbRangof.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CmbRangof.setOpaque(false);
        CmbRangof.addItemListener(this);
    }

    public void exportarTablaAExcel(JTable table) {
        JFileChooser sd = new JFileChooser();
        sd.setDialogTitle("Guardar Historial como Excel");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx");
        sd.setFileFilter(filtro);

        int userSelection = sd.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = sd.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }
            try (Workbook hoja = new XSSFWorkbook();
                 FileOutputStream out = new FileOutputStream(filePath)) {

                Sheet sheet = hoja.createSheet("Historial de Movimientos");
                TableModel model = table.getModel();

                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < model.getColumnCount(); i++) {
                    Cell headerCell = headerRow.createCell(i);
                    headerCell.setCellValue(model.getColumnName(i));
                }

                for (int r = 0; r < model.getRowCount(); r++) {
                    Row row = sheet.createRow(r + 1);
                    for (int c = 0; c < model.getColumnCount(); c++) {
                        Cell cell = row.createCell(c);
                        Object value = model.getValueAt(r, c);
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }

                hoja.write(out);

                JOptionPane.showMessageDialog(this,
                        "La tabla se ha exportado a Excel de manera correcta en:\n" + filePath,
                        "Exportación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al exportar la tabla a Excel: " + ex.getMessage(),
                        "Error de Exportación",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
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

        imgexportar = new JLabel();
        imgexportar.setBounds(130, 115, 25, 25);
        try {
            URL exportIconURL = getClass().getResource("/Imagenes/exportacion-de-archivos.png");
            if (exportIconURL != null) {
                ImageIcon img = new ImageIcon(exportIconURL);
                imgexportar.setIcon(new ImageIcon(img.getImage().getScaledInstance(imgexportar.getWidth(), imgexportar.getHeight(), Image.SCALE_SMOOTH)));
            } else {
                System.err.println("No se pudo encontrar 'exportacion-de-archivos.png' en los recursos de /Imagenes.");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar 'exportacion-de-archivos.png': " + e.getMessage());
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
                            Historial.this.getHeight() - (pR2.getY() + pR2.getHeight() + 10));

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
                System.err.println("No se pudo encontrar el icono para " + text + ": " + iconPath + ". Usando icono de respaldo si está disponible.");
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

    private void toggleMenu() {
        menuExpandido = !menuExpandido;
        if (!menuExpandido) {
            setMenuItemsVisibility(false);
        }
        animacionTimer.start();
    }

    public static void main(String[] args) {
        new Historial();
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
            cargardatostabla(CmbRangof.getSelectedIndex());
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
               SesionUsuario.getInstance().cerrarsesion();
                new Inicio_de_sesion();
                this.dispose();
            }
        } else if (e.getSource() == Exportar) {
            exportarTablaAExcel(tabla);
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == CmbRangof) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int selectedIndex = CmbRangof.getSelectedIndex();
                cargardatostabla(selectedIndex);
            }
        }
    }
}