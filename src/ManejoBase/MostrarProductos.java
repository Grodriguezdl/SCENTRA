package ManejoBase;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
//Alison Del Rosario Vicente Coroy
public class MostrarProductos {
     private final Conexion conexionService;

    public MostrarProductos(Conexion conexionService) {
        this.conexionService = conexionService;
    }

    public void mostrarProductos(int idEmpresa, DefaultTableModel modelo) {

       String sql = "SELECT idProductos,idEmpresa,Nombre,Descripción,Cantidad_disponible, "
                 + " Costo_produccion, Precio_venta,Fecha_produccion, Ultima_actualizacion_prod FROM Productos_Terminados WHERE idEmpresa = ?";

        try (java.sql.Connection conn = conexionService.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEmpresa);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    modelo.addRow(new Object[]{
                        rs.getInt("idProductos"),
                        rs.getInt("idEmpresa"),
                        rs.getString("Nombre"),
                        rs.getString("Descripción"),
                        rs.getInt("Cantidad_disponible"),
                        rs.getDouble("Costo_produccion"),
                        rs.getDouble("Precio_venta"),
                       rs.getDate("Fecha_produccion"), 
                         rs.getTimestamp("Ultima_actualizacion_prod") 
                    });
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener ítems de empresa " + idEmpresa, e);
        }

    }
}
