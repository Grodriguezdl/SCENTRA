package ManejoBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActualizarEmpleados {
//Melvin Rodriguez

    public boolean actualizarDatosUsuario(
            //atributos
            int idUsuario,
            String nuevoRol,
            String nuevoEstadoUnionEmpresa
    ) {
        //creación de conexión
        Conexion conexion = new Conexion();
        Connection con = null;
        //para ejecutar la consulta de sql
        PreparedStatement ps = null;
        // verificación si la consulta fue ejecutada correctamente
        boolean exito = false;
//try-catch
        try {
            con = conexion.getConnection();
//consulta de sql(update)
            if (con != null) {
                String sql = "UPDATE Usuarios SET "
                        + "Rol = ?, "
                        + "Estado_Union_Empresa = ? "
                        + "WHERE idUsuarios = ?";

                ps = con.prepareStatement(sql);
                ps.setString(1, nuevoRol);
                ps.setString(2, nuevoEstadoUnionEmpresa);
                ps.setInt(3, idUsuario);

                int filas = ps.executeUpdate();
                //verificar los cambios
                if (filas > 0) {
                    exito = true;
                    System.out.println("✔ Usuario (Empleado) actualizado correctamente.");
                } else {
                    System.out.println("✘ No se encontró un usuario (empleado) con ese ID.");
                }
            } else {
                System.err.println("✘ Conexión no disponible.");
            }

        } catch (SQLException e) {
            System.err.println("✘ Error SQL al actualizar usuario (empleado): " + e.getMessage());
            e.printStackTrace();
            //lo siguiente es para el cierre de todo y asi evitar errores
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("✘ Error al cerrar conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return exito;
    }
}
