package ManejoBase;

import java.sql.*;
import javax.swing.JOptionPane;
public class DeleteProducto {
    //el parametro de "idProducto" es lo unico que se necesita para eliminar
    public boolean eliminarProducto(int idProducto) {
         //creación de conexión
        Conexion conexion = new Conexion();
        Connection con = conexion.getConnection();
          //para ejecutar la consulta de sql
        PreparedStatement ps = null;
         // verificación si la consulta fue ejecutada correctamente
        boolean eliminado = false;
//        si hay alguna conexion
        if (con != null) {
            String sql = "DELETE FROM Productos_Terminados WHERE idProductos = ?";
           //try-catch
            try {
                ps = con.prepareStatement(sql);
                ps.setInt(1, idProducto);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    eliminado = true;
                    JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró Producto con el ID: " + idProducto, "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar Producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
              //cierre de conexion para evitar errores
            finally {
                try {
                    if (ps != null) ps.close();
                    if (con != null) con.close(); // Cerrar la conexión aquí
                } catch (SQLException e) {
                    System.err.println("Error al cerrar recursos: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo establecer conexión con la base de datos.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
        return eliminado;
    }
}

