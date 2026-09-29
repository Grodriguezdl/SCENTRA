package Vista;

import ManejoBase.Conexion;
import ManejoBase.JoinInventario;
import ManejoBase.SesionUsuario;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
//Alison Del Rosario Vicente Coroy

public class pnlInventario extends JPanel implements ActionListener {

    JSeparator linea;

    Conexion con;
    JoinInventario ji;
    private JTable tablaInventario;
    private DefaultTableModel mdTabla;
    private JScrollPane scTabla;
    private JTableHeader titulos;
//Sub
    Font bt = new Font("Segoe IU", Font.PLAIN, 14);
//titulos
    Font bt2 = new Font("Segoe IU", Font.BOLD, 15);
    private String rolUsuario;
    private int idEmpresaUsuario;

    public pnlInventario() {
        setLayout(null);
        setBackground(new Color(208, 201, 208)); // Color de fondo
        SesionUsuario sesion = SesionUsuario.getInstance();
        this.rolUsuario = sesion.getRol();
        this.idEmpresaUsuario = sesion.getIdEmpresa();
        obj();
        agr();
        posicionar();
    }

    public void posicionar() {
//        pnlTextos.setBounds(40, 150, 350, 225);

        linea.setBounds(20, 170, 310, 6);

//        scTabla.setBounds(410, 250, 600, 200);
        scTabla.setBounds(35, 32, 880, 480);

    }

    public void obj() {
        con = new Conexion();
        ji = new JoinInventario(con);
        pnl();
        btn();
        txf();
        linea();
        Tb();
    }

    public void paraeljoin() {
        mdTabla.setRowCount(0);
        int idEmpresaacargar = this.idEmpresaUsuario;
        try {
            ji.llenarporempresa(idEmpresaacargar, mdTabla);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos");
            ex.printStackTrace();
        }
    }

    public void agr() {
        add(scTabla);
    }

    public void Tb() {
        String[] columnas = {"Categoria", "ID", "Nombre Objeto", "Cantidad", "Costo/Precio"};
        mdTabla = new DefaultTableModel(null, columnas);
        tablaInventario = new JTable(mdTabla);
        tablaInventario.setFillsViewportHeight(true);
        scTabla = new JScrollPane(tablaInventario);

        tablaInventario.setFont(bt);
        tablaInventario.setBackground(new Color(249, 247, 250));
        tablaInventario.setForeground(Color.BLACK); // Texto negro
        tablaInventario.setRowHeight(28);
        tablaInventario.setShowGrid(true);
        tablaInventario.setGridColor(Color.BLACK);

        titulos = tablaInventario.getTableHeader();
        titulos.setFont(bt2);
        titulos.setBackground(new Color(116, 119, 147));
        titulos.setForeground(Color.WHITE);
        titulos.setReorderingAllowed(false);

        //Ancho de columnas
        tablaInventario.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablaInventario.getColumnModel().getColumn(1).setPreferredWidth(80);
        tablaInventario.getColumnModel().getColumn(2).setPreferredWidth(300);
        tablaInventario.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaInventario.getColumnModel().getColumn(4).setPreferredWidth(120);
    }

    //pnl.setBounds(0, 0, 180, 100);
    public void linea() {
        linea = new JSeparator(JSeparator.HORIZONTAL);
        linea.setForeground(Color.decode("#6A7793"));
        linea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    }

    public void pnl() {
    }

    public void txf() {

    }

    public void diseñotxf(JTextField txf) {
        txf.setFont(bt);
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

    //metodo que maneja la forma redondeada de los botones
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
