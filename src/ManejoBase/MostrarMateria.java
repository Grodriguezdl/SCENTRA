package ManejoBase;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
//Alison Del Rosario Vicente Coroy
public class MostrarMateria {
     private final Conexion conexionService;

    public MostrarMateria(Conexion conexionService) {
        this.conexionService = conexionService;
    }

    public void mostrarMateria(int idEmpresa, DefaultTableModel modelo) {

        String sql = "SELECT idMateria_Prima, idEmpresa, Nombre, Unidad_de_medida, "
                   + "Cantidad_disponible, Cantidad_minima, Precio_unitario, Ultima_actualización_inv " 
                   + "FROM Materia_Prima WHERE idEmpresa = ?";
        
          try (java.sql.Connection conn = conexionService.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEmpresa);
         
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    modelo.addRow(new Object[]{
                        rs.getInt("idMateria_Prima"),
                        rs.getInt("idEmpresa"),
                        rs.getString("Nombre"),
                        rs.getString("Unidad_de_medida"),
                        rs.getInt("Cantidad_disponible"),
                        rs.getInt("Cantidad_minima"),
                        rs.getDouble("Precio_unitario"),
                        rs.getTimestamp("Ultima_actualización_inv") 
                    });
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener ítems de empresa " + idEmpresa, e);
        }

    }
}