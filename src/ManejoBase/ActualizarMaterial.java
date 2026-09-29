package ManejoBase;


import java.sql.*;
import javax.swing.JOptionPane;// Importa la clase JOptionPane

public class ActualizarMaterial {

    public boolean actualizarCantidadMaterialEmpaque(
            //atributos
            int idEmpaque,
            int cantidad
    ) {
        //creación/llamada a conexion
        Conexion conexion = new Conexion();
        Connection con = null;
        //para ejecutar la consulta de sql
        PreparedStatement ps = null;
        // verificación si la consulta fue ejecutada correctamente
        boolean exitoActualizacion = false;
//try-catch
        try {
            con = conexion.getConnection();
//consulta de sql(update)
            if (con != null) {
                String sql = "UPDATE Material_empaque SET "
                        + "Cantidad = ?, "
                        + "Ultima_actualizacion_empq = ? "
                        + "WHERE idEmpaque = ?";

                ps = con.prepareStatement(sql);

                ps.setInt(1, cantidad);
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                ps.setInt(3, idEmpaque);

                int filasAfectadas = ps.executeUpdate();
 //verificar los cambios
                if (filasAfectadas > 0) {
                    exitoActualizacion = true;
                    JOptionPane.showMessageDialog(null, "Cantidad actualizada para idEmpaque " + idEmpaque, "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró Material de Empaque con ID: " + idEmpaque, "Error de Actualización", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Conexión a la base de datos no disponible.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al actualizar cantidad de Material de Empaque: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
         //lo siguiente es para el cierre de todo y asi evitar errores
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar recursos: " + e.getMessage(), "Error de Recursos", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        return exitoActualizacion;
    }
}
