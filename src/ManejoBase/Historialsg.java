package ManejoBase;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.math.*;

public class Historialsg {

    Conexion conn;
    private int idEmpresaUsuario;

    public Historialsg() {
        conn = new Conexion();
        idEmpresaUsuario = SesionUsuario.getInstance().getIdEmpresa();
    }

    public DefaultTableModel obtenerlosmovimientos() {
        DefaultTableModel mdl = new DefaultTableModel();
        mdl.addColumn("IDMovimiento");
        mdl.addColumn("Tipo Movimiento");
        mdl.addColumn("Tipo Item");
        mdl.addColumn("Nombre Item");
        mdl.addColumn("Cantidad");
        mdl.addColumn("Fecha Movimiento");
        mdl.addColumn("Usuario");
        mdl.addColumn("Costo Unitario");
        mdl.addColumn("Costo Total");
        mdl.addColumn("Comentarios");

        String muchotexto = "SELECT mi.idMovimiento, mi.Tipo_movimiento, mi.Tipo_Item, " +
                            "CASE " +
                            "    WHEN mi.Tipo_Item = 'Materia_Prima' THEN mp.Nombre " +
                            "    WHEN mi.Tipo_Item = 'Productos_Terminados' THEN pt.Nombre " +
                            "    WHEN mi.Tipo_Item = 'Material_Empaque' THEN me.Nombre " +
                            "END as Nombre_Item, " +
                            "mi.Cantidad, mi.FechaMovimiento, u.Nombre as Nombre_Usuario, mi.Costo_Unitario, " +
                            "mi.Costo_Total, mi.Comentarios_detalles " +
                            "FROM Movimientos_Inventario mi " +
                            "LEFT JOIN Materia_Prima mp ON mi.idMateriaPrima_FK = mp.idMateria_Prima " +
                            "LEFT JOIN Productos_Terminados pt ON mi.idProductoTerminado_FK = pt.idProductos " +
                            "LEFT JOIN Material_empaque me ON mi.idMaterialEmpaque_FK = me.idEmpaque " +
                            "JOIN Usuarios u ON mi.idUsuario = u.idUsuarios ";
        
        if (idEmpresaUsuario != -1) {
            muchotexto += "WHERE (mp.idEmpresa = ? OR pt.idEmpresa = ? OR me.idEmpresa = ?) " +
                          "OR (mi.idMateriaPrima_FK IS NULL AND mi.idProductoTerminado_FK IS NULL AND mi.idMaterialEmpaque_FK IS NULL AND u.idEmpresa = ?) ";
        }
        muchotexto += "ORDER BY mi.FechaMovimiento DESC";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = conn.getConnection();

            if (connection == null || connection.isClosed()) {
                JOptionPane.showMessageDialog(null, "Error: No se pudo establecer conexión con la base de datos. Verifique la configuración de conexión.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                return mdl;
            }

            pstmt = connection.prepareStatement(muchotexto);

            if (idEmpresaUsuario != -1) {
                pstmt.setInt(1, idEmpresaUsuario);
                pstmt.setInt(2, idEmpresaUsuario);
                pstmt.setInt(3, idEmpresaUsuario);
                pstmt.setInt(4, idEmpresaUsuario);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] fc = new Object[10];
                fc[0] = rs.getInt("idMovimiento");
                fc[1] = rs.getString("Tipo_movimiento");
                fc[2] = rs.getString("Tipo_Item");
                fc[3] = rs.getString("Nombre_Item");
                fc[4] = rs.getInt("Cantidad");
                fc[5] = rs.getTimestamp("FechaMovimiento");
                fc[6] = rs.getString("Nombre_Usuario");
                fc[7] = rs.getBigDecimal("Costo_Unitario");
                fc[8] = rs.getBigDecimal("Costo_Total");
                fc[9] = rs.getString("Comentarios_detalles");
                mdl.addRow(fc);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos generales: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar ResultSet en obtenerlosmovimientos: " + e.getMessage());
                e.printStackTrace();
            }
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement en obtenerlosmovimientos: " + e.getMessage());
                e.printStackTrace();
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar Connection en obtenerlosmovimientos: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return mdl;
    }

    public DefaultTableModel obtenerlosmovimientosPorRango(Timestamp fechaInicio, Timestamp fechaFin) {
        DefaultTableModel mdl = new DefaultTableModel();
        mdl.addColumn("IDMovimiento");
        mdl.addColumn("Tipo Movimiento");
        mdl.addColumn("Tipo Item");
        mdl.addColumn("Nombre Item");
        mdl.addColumn("Cantidad");
        mdl.addColumn("Fecha Movimiento");
        mdl.addColumn("Usuario");
        mdl.addColumn("Costo Unitario");
        mdl.addColumn("Costo Total");
        mdl.addColumn("Comentarios");

        String query = "SELECT mi.idMovimiento, mi.Tipo_movimiento, mi.Tipo_Item, " +
                       "CASE " +
                       "    WHEN mi.Tipo_Item = 'Materia_Prima' THEN mp.Nombre " +
                       "    WHEN mi.Tipo_Item = 'Productos_Terminados' THEN pt.Nombre " +
                       "    WHEN mi.Tipo_Item = 'Material_Empaque' THEN me.Nombre " +
                       "END as Nombre_Item, " +
                       "mi.Cantidad, mi.FechaMovimiento, u.Nombre as Nombre_Usuario, mi.Costo_Unitario, " +
                       "mi.Costo_Total, mi.Comentarios_detalles " +
                       "FROM Movimientos_Inventario mi " +
                       "LEFT JOIN Materia_Prima mp ON mi.idMateriaPrima_FK = mp.idMateria_Prima " +
                       "LEFT JOIN Productos_Terminados pt ON mi.idProductoTerminado_FK = pt.idProductos " +
                       "LEFT JOIN Material_empaque me ON mi.idMaterialEmpaque_FK = me.idEmpaque " +
                       "JOIN Usuarios u ON mi.idUsuario = u.idUsuarios ";
        
        query += "WHERE mi.FechaMovimiento BETWEEN ? AND ? ";
        if (idEmpresaUsuario != -1) {
            query += "AND (mp.idEmpresa = ? OR pt.idEmpresa = ? OR me.idEmpresa = ?) " +
                     "OR (mi.FechaMovimiento BETWEEN ? AND ? AND mi.idMateriaPrima_FK IS NULL AND mi.idProductoTerminado_FK IS NULL AND mi.idMaterialEmpaque_FK IS NULL AND u.idEmpresa = ?) ";
        }
        query += "ORDER BY mi.FechaMovimiento DESC";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = conn.getConnection();

            if (connection == null || connection.isClosed()) {
                JOptionPane.showMessageDialog(null, "Error: No se pudo establecer conexión con la base de datos para el rango de fechas. Verifique la configuración de conexión.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                return mdl;
            }

            pstmt = connection.prepareStatement(query);

            pstmt.setTimestamp(1, fechaInicio);
            pstmt.setTimestamp(2, fechaFin);

            if (idEmpresaUsuario != -1) {
                pstmt.setInt(3, idEmpresaUsuario);
                pstmt.setInt(4, idEmpresaUsuario);
                pstmt.setInt(5, idEmpresaUsuario);
                pstmt.setTimestamp(6, fechaInicio);
                pstmt.setTimestamp(7, fechaFin);
                pstmt.setInt(8, idEmpresaUsuario);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] fc = new Object[10];
                fc[0] = rs.getInt("idMovimiento");
                fc[1] = rs.getString("Tipo_movimiento");
                fc[2] = rs.getString("Tipo_Item");
                fc[3] = rs.getString("Nombre_Item");
                fc[4] = rs.getInt("Cantidad");
                fc[5] = rs.getTimestamp("FechaMovimiento");
                fc[6] = rs.getString("Nombre_Usuario");
                fc[7] = rs.getBigDecimal("Costo_Unitario");
                fc[8] = rs.getBigDecimal("Costo_Total");
                fc[9] = rs.getString("Comentarios_detalles");
                mdl.addRow(fc);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos por rango de fechas: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar ResultSet en obtenerlosmovimientosPorRango: " + e.getMessage());
                e.printStackTrace();
            }
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement en obtenerlosmovimientosPorRango: " + e.getMessage());
                e.printStackTrace();
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar Connection en obtenerlosmovimientosPorRango: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return mdl;
    }
}