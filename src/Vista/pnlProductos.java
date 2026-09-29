package Vista;

import ManejoBase.Conexion;
import ManejoBase.SesionUsuario;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import ManejoBase.MostrarProductos;
import javax.swing.text.JTextComponent;

public class pnlProductos extends JPanel implements ActionListener {

    JSeparator linea;

    private JTextField txtIdProducto;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtCantidadDisponible;
    private JTextField txtCostoProduccion;
    private JTextField txtPrecioVenta;
    private JTextField txtFechaProduccion;
    private JTextField txtUltimaActualizacionProd;
    private JTextField txtIdEmpresa; // Este campo se puede ocultar o eliminar si no se usa para mostrar

    private JTable tablaProductos;
    private DefaultTableModel mdTabla;
    private JScrollPane scTabla;
    private JTableHeader titulos;
    private JPanel pnlTextos;
    private String rolUsuario;
    private int idEmpresaUsuario;
    MostrarProductos MostrarProductos;

    Font bt = new Font("Segoe IU", Font.PLAIN, 14);
    Font bt2 = new Font("Segoe IU", Font.BOLD, 15);

    public pnlProductos() {
        setLayout(null);
        setBackground(new Color(208, 201, 208));
        SesionUsuario sesion = SesionUsuario.getInstance();
        this.rolUsuario = sesion.getRol();
        this.idEmpresaUsuario = sesion.getIdEmpresa();
        Conexion conn = new Conexion();
        MostrarProductos = new MostrarProductos(conn);

        obj();
        agr();
        posicionar();
        MostrarProductos();
        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaProductos.getSelectedRow() != -1) {
                int selectedRow = tablaProductos.getSelectedRow();
                txtIdProducto.setText(mdTabla.getValueAt(selectedRow, 0).toString());
                txtIdEmpresa.setText(mdTabla.getValueAt(selectedRow, 1).toString());
                txtNombre.setText(mdTabla.getValueAt(selectedRow, 2).toString());
                txtDescripcion.setText(mdTabla.getValueAt(selectedRow, 3).toString());
                txtCantidadDisponible.setText(mdTabla.getValueAt(selectedRow, 4).toString());
                txtCostoProduccion.setText(mdTabla.getValueAt(selectedRow, 5).toString());
                txtPrecioVenta.setText(mdTabla.getValueAt(selectedRow, 6).toString());
                txtFechaProduccion.setText(mdTabla.getValueAt(selectedRow, 7) != null ? mdTabla.getValueAt(selectedRow, 7).toString() : "");
                txtUltimaActualizacionProd.setText(mdTabla.getValueAt(selectedRow, 8) != null ? mdTabla.getValueAt(selectedRow, 8).toString() : "");
                resetPlaceholder(txtIdProducto, "ID Producto");
                resetPlaceholder(txtIdEmpresa, "ID Empresa");
                resetPlaceholder(txtNombre, "Nombre");
                resetPlaceholder(txtDescripcion, "Descripción");
                resetPlaceholder(txtCantidadDisponible, "Cantidad Disponible");
                resetPlaceholder(txtCostoProduccion, "Costo Producción");
                resetPlaceholder(txtPrecioVenta, "Precio Venta");
                resetPlaceholder(txtFechaProduccion, "Fecha Producción");
                resetPlaceholder(txtUltimaActualizacionProd, "Ultima Actualización");
            }
        });
    }

    public void posicionar() {
        pnlTextos.setBounds(300, 30, 350, 320);

        txtIdProducto.setBounds(20, 20, 145, 30);
        txtIdProducto.setEditable(false);
        txtIdEmpresa.setBounds(185, 20, 145, 30);
        txtIdEmpresa.setEditable(false); // ID Empresa también es solo de lectura
        txtNombre.setBounds(20, 70, 310, 30);
        txtDescripcion.setBounds(20, 120, 310, 30);
        txtCantidadDisponible.setBounds(20, 170, 145, 30);
        txtCostoProduccion.setBounds(185, 170, 145, 30);
        txtPrecioVenta.setBounds(20, 220, 145, 30);
        txtFechaProduccion.setBounds(185, 220, 145, 30);
        txtUltimaActualizacionProd.setBounds(20, 270, 310, 30);
        txtFechaProduccion.setEditable(false);
        txtUltimaActualizacionProd.setEditable(false);

        linea.setBounds(20, 310, 310, 6);

        scTabla.setBounds(35, 370, 880, 170);
    }

    public void obj() {
        pnl();
        btn();
        txf();
        linea();
        Tb();
    }

    public void agr() {
        add(pnlTextos);
        pnlTextos.add(txtIdProducto);
        pnlTextos.add(txtIdEmpresa);
        pnlTextos.add(txtNombre);
        pnlTextos.add(txtDescripcion);
        pnlTextos.add(txtCantidadDisponible);
        pnlTextos.add(txtCostoProduccion);
        pnlTextos.add(txtPrecioVenta);
        pnlTextos.add(txtFechaProduccion);
        pnlTextos.add(txtUltimaActualizacionProd);
        pnlTextos.add(linea);
        add(scTabla);
    }

    public void Tb() {
        String[] columnas = {"idProductos", "idEmpresa", "Nombre", "Descripción", "Cantidad Disponible", "Costo de producción.", "Precio venta", "Fecha_Produccion", "Ultima_actualizacion_prod"};
        mdTabla = new DefaultTableModel(null, columnas);
        tablaProductos = new JTable(mdTabla);
        tablaProductos.setFillsViewportHeight(true);
        scTabla = new JScrollPane(tablaProductos);

        tablaProductos.setFont(bt);
        tablaProductos.setBackground(new Color(249, 247, 250));
        tablaProductos.setForeground(Color.BLACK);
        tablaProductos.setRowHeight(28);
        tablaProductos.setShowGrid(true);
        tablaProductos.setGridColor(Color.BLACK);

        titulos = tablaProductos.getTableHeader();
        titulos.setFont(bt2);
        titulos.setBackground(new Color(116, 119, 147));
        titulos.setForeground(Color.WHITE);
        titulos.setReorderingAllowed(false);

        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(400);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(400);
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(250);
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(275);
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(285);
        tablaProductos.getColumnModel().getColumn(6).setPreferredWidth(250);
        tablaProductos.getColumnModel().getColumn(7).setPreferredWidth(275);
        tablaProductos.getColumnModel().getColumn(8).setPreferredWidth(285);
    }

    public void linea() {
        linea = new JSeparator(JSeparator.HORIZONTAL);
        linea.setForeground(Color.decode("#6A7793"));
        linea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    }

    public void pnl() {
        pnlTextos = new JPanel();
        pnlTextos.setLayout(null);
        pnlTextos.setBackground(new Color(180, 168, 170));
    }

    private void resetPlaceholder(JTextField field, String placeholder) {
        if (field.getText().equals(placeholder) || field.getText().isEmpty()) {
            field.setForeground(Color.GRAY);
        } else {
            field.setForeground(Color.BLACK);
        }
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        textField.setFont(bt);
        textField.setBackground(new Color(249, 247, 250));
        textField.setForeground(Color.GRAY);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        textField.setCaretColor(Color.BLACK);
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        return textField;
    }

    public void txf() {
        txtIdProducto = createStyledTextField("ID Producto");
        txtNombre = createStyledTextField("Nombre");
        permitirletras(txtNombre);
        txtDescripcion = createStyledTextField("Descripción");
        permitirletras(txtDescripcion);
        txtCantidadDisponible = createStyledTextField("Cantidad Disponible");
        permitirnumeros(txtCantidadDisponible);
        txtCostoProduccion = createStyledTextField("Costo Producción");
        permitirnumeros(txtCostoProduccion);
        txtPrecioVenta = createStyledTextField("Precio Venta");
        permitirnumeros(txtPrecioVenta);
        txtFechaProduccion = createStyledTextField("Fecha Producción");
        txtUltimaActualizacionProd = createStyledTextField("Última Actualización");
        txtIdEmpresa = createStyledTextField("ID Empresa");
        deshabilitarCopiarPegar(txtNombre);
        deshabilitarCopiarPegar(txtDescripcion);
        deshabilitarCopiarPegar(txtCantidadDisponible);
        deshabilitarCopiarPegar(txtCostoProduccion);
        deshabilitarCopiarPegar(txtPrecioVenta);
        deshabilitarCopiarPegar(txtIdEmpresa);
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
                if (!letra.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ  ]")) {
                    e.consume();
                }
                if (campo.getText().length() > 15) {
                    e.consume();
                }

            }
        });
    }

    public void permitirnumeros(JTextField campo) {

        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String letra = String.valueOf(c);
                if (!letra.matches("[0-9. ]")) {
                    e.consume();
                }
                if (campo.getText().length() > 3) {
                    e.consume();
                }
            }
        });
    }

    public void diseñotxf(JTextField txf) {
        txf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txf.setBackground(new Color(249, 247, 250));
        txf.setForeground(Color.GRAY);
        txf.setBorder(null);
        txf.setCaretColor(Color.BLACK);
    }

    public void diseñobtn(JButton btn) {
        btn.setBackground(new Color(106, 119, 147));
        btn.setForeground(Color.WHITE);
        forma(btn);
    }

    public void btn() {
    }

    public void MostrarProductos() {
        mdTabla.setRowCount(0);
        try {
            MostrarProductos.mostrarProductos(this.idEmpresaUsuario, mdTabla);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public Object[] getDatosProducto() {
        try {
            // idEmpresa ya no se obtiene del campo, se pasará desde Inventarios
            // int idEmpresa = txtIdEmpresa.getText().trim().equals("ID Empresa") || txtIdEmpresa.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtIdEmpresa.getText().trim());
            String nombre = txtNombre.getText().trim().equals("Nombre") || txtNombre.getText().trim().isEmpty() ? null : txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim().equals("Descripción") || txtDescripcion.getText().trim().isEmpty() ? null : txtDescripcion.getText().trim();
            int cantidadDisponible = txtCantidadDisponible.getText().trim().equals("Cantidad Disponible") || txtCantidadDisponible.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtCantidadDisponible.getText().trim());
            double costoProduccion = txtCostoProduccion.getText().trim().equals("Costo Producción") || txtCostoProduccion.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtCostoProduccion.getText().trim());
            double precioVenta = txtPrecioVenta.getText().trim().equals("Precio Venta") || txtPrecioVenta.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtPrecioVenta.getText().trim());

            if (nombre == null || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del Producto no puede estar vacío.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            // Se devuelve el resto de los datos, idEmpresa se maneja en Inventarios
            return new Object[]{nombre, descripcion, cantidadDisponible, costoProduccion, precioVenta};
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa valores numéricos válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void clearFields() {
        txtIdProducto.setText("ID Producto");
        txtIdProducto.setForeground(Color.GRAY);
        txtNombre.setText("Nombre");
        txtNombre.setForeground(Color.GRAY);
        txtDescripcion.setText("Descripción");
        txtDescripcion.setForeground(Color.GRAY);
        txtCantidadDisponible.setText("Cantidad Disponible");
        txtCantidadDisponible.setForeground(Color.GRAY);
        txtCostoProduccion.setText("Costo Producción");
        txtCostoProduccion.setForeground(Color.GRAY);
        txtPrecioVenta.setText("Precio Venta");
        txtPrecioVenta.setForeground(Color.GRAY);
        txtFechaProduccion.setText("Fecha Producción");
        txtFechaProduccion.setForeground(Color.GRAY);
        txtUltimaActualizacionProd.setText("Última Actualización");
        txtUltimaActualizacionProd.setForeground(Color.GRAY);
        txtIdEmpresa.setText("ID Empresa");
        txtIdEmpresa.setForeground(Color.GRAY);
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

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
