package ManejoBase;

import java.sql.*;
import javax.swing.JOptionPane;

public class Create_Material {

    private int getMaxIdEmpaque() {
        Conexion conexion = new Conexion();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int maxId = 0;
        try {
            con = conexion.getConnection();
            String sql = "SELECT MAX(idEmpaque) FROM Material_empaque";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el ID máximo de Material de Empaque: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
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

    public boolean crearMaterialEmpaque(
            int idEmpresa, String nombre, String unidad_de_medida,
            int cantidad, double precio_unitario, Timestamp ultima_actualizacion_empq
    ) {
        Conexion conexion = new Conexion();
        Connection con = null;
        PreparedStatement ps = null;
        boolean creado = false;
        int newId = 0; // Variable para almacenar el ID generado

        try {
            con = conexion.getConnection();
            if (con != null) {
                newId = getMaxIdEmpaque() + 1;

                String sql = "INSERT INTO Material_empaque ("
                        + "idEmpaque, idEmpresa, Nombre, Unidad_de_medida, Cantidad, Precio_unitario, Ultima_actualizacion_empq"
                        + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

                ps = con.prepareStatement(sql);

                ps.setInt(1, newId);
                ps.setInt(2, idEmpresa);
                ps.setString(3, nombre);
                ps.setString(4, unidad_de_medida);
                ps.setInt(5, cantidad);
                ps.setDouble(6, precio_unitario);
                ps.setTimestamp(7, ultima_actualizacion_empq);

                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    creado = true;
                    JOptionPane.showMessageDialog(null, "Material de Empaque creado exitosamente con ID: " + newId, "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Registrar movimiento de inventario
                    Nuevomovimientoagr movimiento = new Nuevomovimientoagr();
                    movimiento.crearMovimiento(
                            "Material_empaque",
                            newId,
                            cantidad,
                            precio_unitario,
                            cantidad * precio_unitario,
                            "Creación de nuevo Material de Empaque: " + nombre
                    );

                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo crear el Material de Empaque.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Conexión a la base de datos no disponible.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al crear Material de Empaque: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
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
