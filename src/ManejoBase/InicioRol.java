package ManejoBase;


import java.sql.*;
import javax.swing.JOptionPane;

public class InicioRol {

    public String[] validar(String nombreUs, String contra) {
        String[] datosusuario = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            Conexion con = new Conexion();
            conn = con.getConnection();
            if (conn != null) {
                String sql = "Select Rol, idEmpresa From Usuarios Where Nombre = ? and Contrasena = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, nombreUs);
                stmt.setString(2, contra);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    String rol = rs.getString("Rol");
                    String idEmpresa = String.valueOf(rs.getString("idEmpresa"));
                    datosusuario = new String[]{rol, idEmpresa};
                } else {
                    JOptionPane.showMessageDialog(null, "Error de conexión");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en SQL");
            e.printStackTrace();
        }
        return datosusuario;
    }
}
