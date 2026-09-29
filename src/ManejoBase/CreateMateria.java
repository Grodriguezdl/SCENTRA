package ManejoBase;


import java.sql.*;
import javax.swing.JOptionPane;

public class CreateMateria {

    private int getMaxIdMateriaPrima() {
        Conexion conexion = new Conexion();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int maxId = 0;
        try {
            con = conexion.getConnection();
            String sql = "SELECT MAX(idMateria_Prima) FROM Materia_Prima";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el ID máximo de Materia Prima: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return maxId;
    }

    public boolean crearMateriaPrima(
            int idEmpresa, String nombre, String unidad_de_medida,
            int cantidad_disponible, int cantidad_minima, double precio_unitario,
            Timestamp ultima_actualizacion_inv
    ) {
        Conexion conexion = new Conexion();
        Connection con = null;
        PreparedStatement ps = null;
        boolean creado = false;
        int newId = 0; // Variable para almacenar el ID generado

        try {
            con = conexion.getConnection();
            if (con != null) {
                newId = getMaxIdMateriaPrima() + 1;

                String sql = "INSERT INTO Materia_Prima ("
                        + "idMateria_Prima, idEmpresa, Nombre, Unidad_de_medida, Cantidad_disponible, "
                        + "Cantidad_minima, Precio_unitario, Ultima_actualización_inv"
                        + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                ps = con.prepareStatement(sql);

                ps.setInt(1, newId);
                ps.setInt(2, idEmpresa);
                ps.setString(3, nombre);
                ps.setString(4, unidad_de_medida);
                ps.setInt(5, cantidad_disponible);
                ps.setInt(6, cantidad_minima);
                ps.setDouble(7, precio_unitario);
                ps.setTimestamp(8, ultima_actualizacion_inv);

                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    creado = true;
                    JOptionPane.showMessageDialog(null, "Materia Prima creada exitosamente con ID: " + newId, "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Registrar movimiento de inventario
                    Nuevomovimientoagr movimiento = new Nuevomovimientoagr();
                    movimiento.crearMovimiento(
                            "Materia_Prima",
                            newId,
                            cantidad_disponible,
                            precio_unitario,
                            cantidad_disponible * precio_unitario,
                            "Creación de nueva Materia Prima: " + nombre
                    );

                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo crear la Materia Prima.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Conexión a la base de datos no disponible.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al crear Materia Prima: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return creado;
    }
}
