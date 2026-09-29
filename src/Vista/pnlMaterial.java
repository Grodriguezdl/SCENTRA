package Vista;

import ManejoBase.Conexion;
import ManejoBase.SesionUsuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import ManejoBase.MostrarMaterial;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.text.JTextComponent;

public class pnlMaterial extends JPanel implements ActionListener {

    JSeparator linea;
    private JTextField txtNombre;
    private JTextField txtIdEmpaque;
    private JTextField txtIdEmpresa; // Este campo se puede ocultar o eliminar si no se usa para mostrar
    private JTextField txtUnidadDeMedida;
    private JTextField txtCantidadActual;
    private JTextField txtPrecioUnitario;
    private JTextField txtUltimaActualizacionEmpq;
    private JTable tablaMaterial;
    private DefaultTableModel mdTabla;
    private JScrollPane scTabla;
    private JTableHeader titulos;
    private JPanel pnlTextos;
    MostrarMaterial MostrarMaterials;

    Font bt = new Font("Segoe IU", Font.PLAIN, 14);
    Font bt2 = new Font("Segoe IU", Font.BOLD, 15);
    private String rolUsuario;
    private int idEmpresaUsuario;

    public pnlMaterial() {
        setLayout(null);
        setBackground(new Color(208, 201, 208));
        SesionUsuario sesion = SesionUsuario.getInstance();
        this.rolUsuario = sesion.getRol();
        this.idEmpresaUsuario = sesion.getIdEmpresa();
        Conexion conn = new Conexion();
        MostrarMaterials = new MostrarMaterial(conn);

        obj();
        agr();
        posicionar();
        MostrarMaterial();

        tablaMaterial.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaMaterial.getSelectedRow() != -1) {
                int selectedRow = tablaMaterial.getSelectedRow();
                txtIdEmpaque.setText(mdTabla.getValueAt(selectedRow, 0).toString());
                txtIdEmpresa.setText(mdTabla.getValueAt(selectedRow, 1).toString());
                txtNombre.setText(mdTabla.getValueAt(selectedRow, 2).toString());
                txtUnidadDeMedida.setText(mdTabla.getValueAt(selectedRow, 3).toString());
                txtCantidadActual.setText(mdTabla.getValueAt(selectedRow, 4).toString());
                txtPrecioUnitario.setText(mdTabla.getValueAt(selectedRow, 5).toString());
                txtUltimaActualizacionEmpq.setText(mdTabla.getValueAt(selectedRow, 6) != null ? mdTabla.getValueAt(selectedRow, 6).toString() : "");

                resetPlaceholder(txtIdEmpaque, "ID Empaque");
                resetPlaceholder(txtIdEmpresa, "ID Empresa");
                resetPlaceholder(txtNombre, "Nombre");
                resetPlaceholder(txtUnidadDeMedida, "Unidad de Medida");
                resetPlaceholder(txtCantidadActual, "Cantidad Actual");
                resetPlaceholder(txtPrecioUnitario, "Precio Unitario");
                resetPlaceholder(txtUltimaActualizacionEmpq, "Última Actualización");
            }
        });
    }

    public void posicionar() {
        pnlTextos.setBounds(300, 40, 350, 300);

        txtIdEmpaque.setBounds(20, 20, 145, 30);
        txtIdEmpaque.setEditable(false);
        txtIdEmpresa.setBounds(185, 20, 145, 30);
        txtIdEmpresa.setEditable(false); // ID Empresa también es solo de lectura
        txtNombre.setBounds(20, 70, 310, 30);
        txtUnidadDeMedida.setBounds(20, 120, 145, 30);
        txtCantidadActual.setBounds(185, 120, 145, 30);
        txtPrecioUnitario.setBounds(20, 170, 145, 30);
        txtUltimaActualizacionEmpq.setBounds(185, 170, 145, 30);
        txtUltimaActualizacionEmpq.setEditable(false);

        linea.setBounds(20, 230, 310, 6);

        scTabla.setBounds(35, 360, 880, 170);
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
        pnlTextos.add(txtIdEmpaque);
        pnlTextos.add(txtIdEmpresa);
        pnlTextos.add(txtNombre);
        pnlTextos.add(txtUnidadDeMedida);
        pnlTextos.add(txtCantidadActual);
        pnlTextos.add(txtPrecioUnitario);
        pnlTextos.add(txtUltimaActualizacionEmpq);
        pnlTextos.add(linea);
        add(scTabla);
    }

    public void Tb() {
        String[] columnas = {"ID Empaque", "ID Empresa", "Nombre", "Unidad de Medida", "Cantidad", "Precio Unitario", "Última Actualización"};
        mdTabla = new DefaultTableModel(null, columnas);
        tablaMaterial = new JTable(mdTabla);
        tablaMaterial.setFillsViewportHeight(true);
        scTabla = new JScrollPane(tablaMaterial);

        tablaMaterial.setFont(bt);
        tablaMaterial.setBackground(new Color(249, 247, 250));
        tablaMaterial.setForeground(Color.BLACK);
        tablaMaterial.setRowHeight(28);
        tablaMaterial.setShowGrid(true);
        tablaMaterial.setGridColor(Color.BLACK);
        titulos = tablaMaterial.getTableHeader();
        titulos.setFont(bt2);
        titulos.setBackground(new Color(116, 119, 147));
        titulos.setForeground(Color.WHITE);
        titulos.setReorderingAllowed(false);
        tablaMaterial.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablaMaterial.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaMaterial.getColumnModel().getColumn(2).setPreferredWidth(180);
        tablaMaterial.getColumnModel().getColumn(3).setPreferredWidth(150);
        tablaMaterial.getColumnModel().getColumn(4).setPreferredWidth(100);
        tablaMaterial.getColumnModel().getColumn(5).setPreferredWidth(100);
        tablaMaterial.getColumnModel().getColumn(6).setPreferredWidth(150);
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

    public void txf() {
        txtIdEmpaque = createStyledTextField("ID Empaque");
        txtIdEmpresa = createStyledTextField("ID Empresa");
        txtNombre = createStyledTextField("Nombre");
        permitirletras(txtNombre);
        txtUnidadDeMedida = createStyledTextField("Unidad de Medida");
        txtCantidadActual = createStyledTextField("Cantidad Actual");
        permitirnumeros(txtCantidadActual);
        txtPrecioUnitario = createStyledTextField("Precio Unitario");
        permitirnumeros(txtPrecioUnitario);
        txtUltimaActualizacionEmpq = createStyledTextField("Última Actualización");
        txtUltimaActualizacionEmpq.setEditable(false);
        deshabilitarCopiarPegar(txtNombre);
        deshabilitarCopiarPegar(txtUnidadDeMedida);
        deshabilitarCopiarPegar(txtCantidadActual);
        deshabilitarCopiarPegar(txtPrecioUnitario);
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
                if (campo.getText().length() > 4) {
                    e.consume();
                }
            }
        });
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

    private void resetPlaceholder(JTextField field, String placeholder) {
        if (field.getText().equals(placeholder)) {
            field.setForeground(Color.GRAY);
        } else if (!field.getText().isEmpty()) {
            field.setForeground(Color.BLACK);
        }
    }

    public void diseñobtn(JButton btn) {
        btn.setBackground(new Color(106, 119, 147));
        btn.setForeground(Color.WHITE);
        forma(btn);
    }

    public void btn() {
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

    public void MostrarMaterial() {
        mdTabla.setRowCount(0);
        try {
            MostrarMaterials.mostrarMaterial(this.idEmpresaUsuario, mdTabla);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el material de empaque: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public Object[] getDatosMaterialEmpaque() {
        try {
            // idEmpresa ya no se obtiene del campo, se pasará desde Inventarios
            // int idEmpresa = txtIdEmpresa.getText().trim().equals("ID Empresa") || txtIdEmpresa.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtIdEmpresa.getText().trim());
            String nombre = txtNombre.getText().trim().equals("Nombre") || txtNombre.getText().trim().isEmpty() ? null : txtNombre.getText().trim();
            String unidadMedida = txtUnidadDeMedida.getText().trim().equals("Unidad de Medida") || txtUnidadDeMedida.getText().trim().isEmpty() ? null : txtUnidadDeMedida.getText().trim();
            int cantidad = txtCantidadActual.getText().trim().equals("Cantidad Actual") || txtCantidadActual.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtCantidadActual.getText().trim());
            double precioUnitario = txtPrecioUnitario.getText().trim().equals("Precio Unitario") || txtPrecioUnitario.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtPrecioUnitario.getText().trim());

            if (nombre == null || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del Material de Empaque no puede estar vacío.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            // Se devuelve el resto de los datos, idEmpresa se maneja en Inventarios
            return new Object[]{nombre, unidadMedida, cantidad, precioUnitario};
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa valores numéricos válidos para Cantidad y Precio.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void clearFields() {
        txtIdEmpaque.setText("ID Empaque");
        txtIdEmpaque.setForeground(Color.GRAY);
        txtIdEmpresa.setText("ID Empresa");
        txtIdEmpresa.setForeground(Color.GRAY);
        txtNombre.setText("Nombre");
        txtNombre.setForeground(Color.GRAY);
        txtUnidadDeMedida.setText("Unidad de Medida");
        txtUnidadDeMedida.setForeground(Color.GRAY);
        txtCantidadActual.setText("Cantidad Actual");
        txtCantidadActual.setForeground(Color.GRAY);
        txtPrecioUnitario.setText("Precio Unitario");
        txtPrecioUnitario.setForeground(Color.GRAY);
        txtUltimaActualizacionEmpq.setText("Última Actualización");
        txtUltimaActualizacionEmpq.setForeground(Color.GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
