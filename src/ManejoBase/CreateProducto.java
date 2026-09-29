package ManejoBase;

import java.sql.*;
import javax.swing.JOptionPane;

public class CreateProducto {

    private int getMaxIdProductos() {
        Conexion conexion = new Conexion();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int maxId = 0;
        try {
            con = conexion.getConnection();
            String sql = "SELECT MAX(idProductos) FROM Productos_Terminados";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el ID máximo de Productos: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
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

    public boolean crearProducto(
            int idEmpresa, String nombre, String descripcion,
            int cantidadDisponible, double costoProduccion, double precioVenta,
            Timestamp fechaProduccion, Timestamp ultimaActualizacionProd
    ) {
        Conexion conexion = new Conexion();
        Connection con = null;
        PreparedStatement ps = null;
        boolean creado = false;
        int newId = 0; // Variable para almacenar el ID generado

        try {
            con = conexion.getConnection();
            if (con != null) {
                newId = getMaxIdProductos() + 1;

                String sql = "INSERT INTO Productos_Terminados ("
                        + "idProductos, idEmpresa, Nombre, Descripción, Cantidad_disponible, "
                        + "Costo_produccion, Precio_venta, Fecha_produccion, Ultima_actualizacion_prod"
                        + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                ps = con.prepareStatement(sql);

                ps.setInt(1, newId);
                ps.setInt(2, idEmpresa);
                ps.setString(3, nombre);
                ps.setString(4, descripcion);
                ps.setInt(5, cantidadDisponible);
                ps.setDouble(6, costoProduccion);
                ps.setDouble(7, precioVenta);
                ps.setTimestamp(8, fechaProduccion);
                ps.setTimestamp(9, ultimaActualizacionProd);

                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    creado = true;
                    JOptionPane.showMessageDialog(null, "Producto Terminado creado exitosamente con ID: " + newId, "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Registrar movimiento de inventario
                    Nuevomovimientoagr movimiento = new Nuevomovimientoagr();
                    movimiento.crearMovimiento(
                            "Productos_Terminados",
                            newId,
                            cantidadDisponible,
                            costoProduccion, // Para productos, el costo unitario es el costo de producción
                            cantidadDisponible * costoProduccion,
                            "Creación de nuevo Producto Terminado: " + nombre
                    );

                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo crear el Producto Terminado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Conexión a la base de datos no disponible.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al crear Producto Terminado: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
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