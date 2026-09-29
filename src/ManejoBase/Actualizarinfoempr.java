package ManejoBase;

import java.sql.*;

public class Actualizarinfoempr {
    SesionUsuario sesion = SesionUsuario.getInstance();

    public boolean actualizarDatosUsuario(
            String Nombre,
            String Tipo,
            String Color,
            String Iniciales
    ) {
        Conexion conexion = new Conexion();
        Connection con = null;
        PreparedStatement ps = null;
        boolean exito = false;

        int idEmpresaActual = sesion.getIdEmpresa();

        try {
            con = conexion.getConnection();
            if (con != null) {
                String sql = "UPDATE Empresa SET "
                        + "Nombre = ?, "
                        + "Tipo = ?, "
                        + "Color = ?, "
                        + "Iniciales = ? "
                        + "WHERE idEmpresa = ?";

                ps = con.prepareStatement(sql);
                ps.setString(1, Nombre);
                ps.setString(2, Tipo);
                ps.setString(3, Color);
                ps.setString(4, Iniciales);
                ps.setInt(5, idEmpresaActual);

                int filas = ps.executeUpdate();
                if (filas > 0) {
                    exito = true;

                } else {
                    System.out.println("✘ No se actualizó la información de la empresa. Puede que el ID de empresa no exista o no haya cambios.");
                }
            } else {
                System.err.println("✘ Esta Conexión no está disponible.");
            }

        } catch (SQLException e) {
            System.err.println("✘ Error al actualizar la información de la empresa " + e.getMessage());
            e.printStackTrace();
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

    public boolean actualizarLogoEmpresa(int idEmpresa, String nuevoLogoPath) {
        Conexion conexion = new Conexion();
        Connection con = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            con = conexion.getConnection();
            if (con != null) {
                String sql = "UPDATE Empresa SET Logo = ? WHERE idEmpresa = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, nuevoLogoPath);
                ps.setInt(2, idEmpresa);

                int filas = ps.executeUpdate();
                if (filas > 0) {
                    exito = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exito;
    }
}