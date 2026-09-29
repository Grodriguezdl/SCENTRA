package ManejoBase;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
//Alison Del Rosario Vicente Coroy
public class MostrarEmpleados {
    
    private final Conexion conexionService;

    public MostrarEmpleados(Conexion conexionService) {
        this.conexionService = conexionService;
    }

    public void mostrarEmpleados(int idEmpresa, DefaultTableModel modelo) {

        String sql = "SELECT idUsuarios,Nombre,Rol,Contrasena, "
                + " Estado_Union_Empresa FROM Usuarios WHERE idEmpresa = ?";

        try (java.sql.Connection conn = conexionService.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEmpresa);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    modelo.addRow(new Object[]{
                        rs.getInt("idUsuarios"),
                        rs.getString("Nombre"),
                        rs.getString("Rol"),
                        rs.getString("Contrasena"),
                        rs.getString("Estado_Union_Empresa")
                    });
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener ítems de empresa " + idEmpresa, e);
        }

    }
}
