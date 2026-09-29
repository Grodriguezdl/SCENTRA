package ManejoBase;

import java.sql.*;
import javax.swing.JOptionPane;

public class Nuevomovimientoagr {

    // Método para obtener el ID máximo actual de Movimientos_Inventario
    private int getMaxIdMovimiento() {
        Conexion conexion = new Conexion();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int maxId = 0;
        try {
            con = conexion.getConnection();
            String sql = "SELECT MAX(idMovimiento) FROM Movimientos_Inventario";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Mostrar JOptionPane en lugar de System.err.println
            JOptionPane.showMessageDialog(null, "Error al obtener el ID máximo de Movimientos_Inventario: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
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

    public boolean crearMovimiento(
            String tipoItem,
            int idItem, // ID del producto/materia/material recién creado
            int cantidad,
            double costoUnitario,
            double costoTotal,
            String comentariosDetalles
    ) {
        Conexion conexion = new Conexion();
        Connection con = null;
        PreparedStatement ps = null;
        boolean creado = false;

        try {
            con = conexion.getConnection();
            if (con != null) {
                int newIdMovimiento = getMaxIdMovimiento() + 1;
                Timestamp fechaMovimiento = new Timestamp(System.currentTimeMillis());
                int idUsuario = SesionUsuario.getInstance().getIdUsuario(); // Obtener idUsuario de la sesión

                String sql = "INSERT INTO Movimientos_Inventario ("
                        + "idMovimiento, Tipo_movimiento, Tipo_Item, idMateriaPrima_FK, "
                        + "idProductoTerminado_FK, idMaterialEmpaque_FK, Cantidad, FechaMovimiento, "
                        + "idUsuario, Costo_Unitario, Costo_Total, Comentarios_detalles"
                        + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                ps = con.prepareStatement(sql);

                ps.setInt(1, newIdMovimiento);
                ps.setString(2, "Entrada"); // Siempre "Entrada" para la creación
                ps.setString(3, tipoItem);

                // Asignar el ID del ítem al FK correcto y los otros a NULL
                if (tipoItem.equals("Materia_Prima")) {
                    ps.setInt(4, idItem);
                    ps.setNull(5, Types.INTEGER);
                    ps.setNull(6, Types.INTEGER);
                } else if (tipoItem.equals("Productos_Terminados")) {
                    ps.setNull(4, Types.INTEGER);
                    ps.setInt(5, idItem);
                    ps.setNull(6, Types.INTEGER);
                } else if (tipoItem.equals("Material_empaque")) {
                    ps.setNull(4, Types.INTEGER);
                    ps.setNull(5, Types.INTEGER);
                    ps.setInt(6, idItem);
                } else {

                    JOptionPane.showMessageDialog(null, "Tipo de ítem desconocido para el movimiento de inventario: " + tipoItem, "Error de Movimiento", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                ps.setInt(7, cantidad);
                ps.setTimestamp(8, fechaMovimiento);
                ps.setInt(9, idUsuario);       
                ps.setDouble(10, costoUnitario);
                ps.setDouble(11, costoTotal);
                ps.setString(12, comentariosDetalles);
                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    creado = true;                
                    JOptionPane.showMessageDialog(null, "Registro de movimiento de inventario creado exitosamente con ID: " + newIdMovimiento, "Movimiento Creado", JOptionPane.INFORMATION_MESSAGE);
                } else {                  
                    JOptionPane.showMessageDialog(null, "No se pudo crear el registro de movimiento de inventario.", "Error de Movimiento", JOptionPane.ERROR_MESSAGE);
                }
            } else {              
                JOptionPane.showMessageDialog(null, "Conexión a la base de datos no disponible para crear movimiento.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {           
            JOptionPane.showMessageDialog(null, "Error SQL al crear Movimiento de Inventario: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
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