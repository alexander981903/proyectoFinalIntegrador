/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Producto;
import conf.DataSource;
import java.sql.Time;
import java.sql.*;
import java.util.ArrayList;



public class PedidoDao {
    private Connection conexion;

    public PedidoDao() {
        this.conexion = DataSource.obtenerConexion(); // Suponiendo que DataSource ya está configurado
    }

    /**
     * Realiza un nuevo pedido y luego una reserva de mesa.
     * 
     * @param idCliente ID del cliente que realiza el pedido.
     * @param fechaPedido Fecha en que se realiza el pedido.
     * @param idMesa ID de la mesa donde se realiza el pedido.
     * @param productos Lista de productos que se incluyen en el pedido.
     * @param fechaReserva Fecha de la reserva.
     * @param horaReserva Hora de la reserva.
     * @param duracionReserva Duración de la reserva (en minutos).
     * @return true si el pedido y la reserva fueron realizados exitosamente, false si hubo algún error.
     */
    public boolean realizarPedidoYReserva(int idCliente, Date fechaPedido, int idMesa, ArrayList<Producto> productos, 
            Date fechaReserva, Time horaReserva, int duracionReserva) {
        PreparedStatement stmtPedido = null;
        PreparedStatement stmtPedidoProducto = null;
        PreparedStatement stmtUpdateProducto = null;
        PreparedStatement stmtReserva = null; // Para la inserción en la tabla reserva
        ResultSet rs = null;

        try {
            // Desactivar auto-commit para iniciar la transacción
            conexion.setAutoCommit(false);

            // Paso 1: Insertar el pedido en la tabla "pedido"
            String sqlPedido = "INSERT INTO pedido (fechaPedido, estadoPedido, totalPedido, idCliente, idMesa) VALUES (?, 'Pendiente', ?, ?, ?)";
            stmtPedido = conexion.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            stmtPedido.setDate(1, fechaPedido);
            stmtPedido.setInt(2, idCliente);  // Cliente que realiza el pedido
            stmtPedido.setInt(3, idMesa);     // Mesa donde se realiza el pedido

            // Calcular el total del pedido según el tamaño seleccionado
            double totalPedido = 0;
            for (Producto producto : productos) {
                // Verifica el precio según el tamaño seleccionado
                double precio = "Familiar".equals(producto.getTamaño()) ? producto.getPrecioFamiliar() : producto.getPrecioPersonal();
                totalPedido += precio * producto.getCantidad(); // Multiplica el precio por la cantidad
            }

            stmtPedido.setDouble(4, totalPedido); // Establecer el total del pedido
            stmtPedido.executeUpdate();

            // Obtener el ID del pedido recién insertado
            rs = stmtPedido.getGeneratedKeys();
            int idPedido = 0;
            if (rs.next()) {
                idPedido = rs.getInt(1);  // Recuperar el ID generado del pedido
            } else {
                throw new SQLException("No se pudo obtener el ID del pedido.");
            }

            // Paso 2: Insertar los productos en la tabla "pedido_producto"
            String sqlPedidoProducto = "INSERT INTO pedido_producto (idPedido, idProducto, cantidad) VALUES (?, ?, ?)";
            stmtPedidoProducto = conexion.prepareStatement(sqlPedidoProducto);
            for (Producto producto : productos) {
                // Asegúrate de que haya stock suficiente antes de insertar el producto en el pedido
                if (producto.getStock() < producto.getCantidad()) {
                    throw new SQLException("No hay suficiente stock para el producto: " + producto.getNombreProducto());
                }

                // Insertar cada producto en el pedido
                stmtPedidoProducto.setInt(1, idPedido);           // ID del pedido
                stmtPedidoProducto.setInt(2, producto.getIdProducto());  // ID del producto
                stmtPedidoProducto.setInt(3, producto.getCantidad());    // Cantidad solicitada
                stmtPedidoProducto.executeUpdate();

                // Paso 3: Actualizar el stock de los productos (restar la cantidad solicitada)
                String sqlUpdateProducto = "UPDATE producto SET stock = stock - ? WHERE idProducto = ?";
                stmtUpdateProducto = conexion.prepareStatement(sqlUpdateProducto);
                stmtUpdateProducto.setInt(1, producto.getCantidad());  // Restar la cantidad solicitada
                stmtUpdateProducto.setInt(2, producto.getIdProducto()); // ID del producto
                stmtUpdateProducto.executeUpdate();
            }

            // Paso 4: Insertar la reserva en la tabla "reserva"
            String sqlReserva = "INSERT INTO reserva (fechaReserva, horaReserva, duracionReserva, estadoReserva, idCliente, idMesa) VALUES (?, ?, ?, 'Reservada', ?, ?)";
            stmtReserva = conexion.prepareStatement(sqlReserva);
            stmtReserva.setDate(1, fechaReserva);
            stmtReserva.setTime(2, horaReserva);
            stmtReserva.setInt(3, duracionReserva);  // Duración de la reserva
            stmtReserva.setInt(4, idCliente);  // ID del cliente
            stmtReserva.setInt(5, idMesa);     // ID de la mesa
            stmtReserva.executeUpdate();

            // Confirmar la transacción
            conexion.commit();
            return true;

        } catch (SQLException e) {
            // En caso de error, revertir la transacción
            try {
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            System.err.println("Error al realizar el pedido y la reserva: " + e.getMessage());
            return false;

        } finally {
            // Cerrar recursos y volver a activar auto-commit
            try {
                if (rs != null) rs.close();
                if (stmtPedido != null) stmtPedido.close();
                if (stmtPedidoProducto != null) stmtPedidoProducto.close();
                if (stmtUpdateProducto != null) stmtUpdateProducto.close();
                if (stmtReserva != null) stmtReserva.close();
                conexion.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

