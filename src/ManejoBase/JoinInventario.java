package ManejoBase;


import java.sql.*;
import javax.swing.table.*;

public class JoinInventario {

    private static final String SQL_ITEMS_POR_EMPRESA
            = "SELECT 'Materia Prima'          AS Categoria, mp.idMateria_Prima AS id, mp.Nombre, mp.Cantidad_disponible AS cantidad, mp.Precio_unitario AS precio "
            + "  FROM Materia_Prima mp WHERE mp.idEmpresa = ? "
            + "UNION ALL "
            + "SELECT 'Producto Terminado'       AS Categoria, pt.idProductos         AS id, pt.Nombre, pt.Cantidad_disponible AS cantidad, pt.Costo_produccion AS precio "
            + "  FROM Productos_Terminados pt WHERE pt.idEmpresa = ? "
            + "UNION ALL "
            + "SELECT 'Material Empaque'         AS Categoria, me.idEmpaque           AS id, me.Nombre, me.Cantidad           AS cantidad, me.Precio_unitario AS precio "
            + "  FROM Material_empaque me WHERE me.idEmpresa = ?";

    private static final String SQL_TOTAL_ITEMS_COUNT
            = "SELECT COUNT(*) FROM (" + SQL_ITEMS_POR_EMPRESA + ") AS subquery;";

    private static final String SQL_TOTAL_QUANTITY
            = "SELECT SUM(cantidad_total) FROM ("
            + "SELECT mp.Cantidad_disponible AS cantidad_total FROM Materia_Prima mp WHERE mp.idEmpresa = ? "
            + "UNION ALL "
            + "SELECT pt.Cantidad_disponible AS cantidad_total FROM Productos_Terminados pt WHERE pt.idEmpresa = ? "
            + "UNION ALL "
            + "SELECT me.Cantidad AS cantidad_total FROM Material_empaque me WHERE me.idEmpresa = ?"
            + ") AS subquery_cantidad;";

    private static final String SQL_TOTAL_VALUE
            = "SELECT SUM(valor_total) FROM ("
            + "SELECT mp.Cantidad_disponible * mp.Precio_unitario AS valor_total FROM Materia_Prima mp WHERE mp.idEmpresa = ? "
            + "UNION ALL "
            + "SELECT pt.Cantidad_disponible * pt.Costo_produccion AS valor_total FROM Productos_Terminados pt WHERE pt.idEmpresa = ? "
            + "UNION ALL "
            + "SELECT me.Cantidad * me.Precio_unitario AS valor_total FROM Material_empaque me WHERE me.idEmpresa = ?"
            + ") AS subquery_valor;";

    Conexion cons;

    public JoinInventario(Conexion cons) {
        this.cons = cons;
    }

    public void llenarporempresa(int idEmpresa, DefaultTableModel modelo) {
        modelo.setRowCount(0);

        try (Connection conn = cons.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_ITEMS_POR_EMPRESA)) {

            ps.setInt(1, idEmpresa);
            ps.setInt(2, idEmpresa);
            ps.setInt(3, idEmpresa);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    modelo.addRow(new Object[]{
                        rs.getString("Categoria"),
                        rs.getInt("id"),
                        rs.getString("Nombre"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio")
                    });
                }
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al cargar datos de inventario para empresa " + idEmpresa + ": " + e.getMessage());
            throw new RuntimeException("Error al cargar datos de inventario para empresa " + idEmpresa, e);
        } catch (Exception e) {
            System.err.println("Error inesperado al cargar datos de inventario para empresa " + idEmpresa + ": " + e.getMessage());
            throw new RuntimeException("Error inesperado al cargar datos de inventario para empresa " + idEmpresa, e);
        }
    }

    public int getTotalItemsCount(int idEmpresa) throws SQLException {
        try (Connection conn = cons.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_TOTAL_ITEMS_COUNT)) {

            ps.setInt(1, idEmpresa);
            ps.setInt(2, idEmpresa);
            ps.setInt(3, idEmpresa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public int getTotalQuantity(int idEmpresa) throws SQLException {
        try (Connection conn = cons.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_TOTAL_QUANTITY)) {

            ps.setInt(1, idEmpresa);
            ps.setInt(2, idEmpresa);
            ps.setInt(3, idEmpresa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public double getTotalValue(int idEmpresa) throws SQLException {
        try (Connection conn = cons.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_TOTAL_VALUE)) {

            ps.setInt(1, idEmpresa);
            ps.setInt(2, idEmpresa);
            ps.setInt(3, idEmpresa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        return 0.0;
    }
}
