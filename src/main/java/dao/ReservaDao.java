/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Cliente;
import Modelo.Reserva;
import Modelo.Mesa;
import Modelo.Pedido;
import conf.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase DAO para manejar las operaciones de la entidad Reserva.
 * Permite recuperar reservas con estado específico de la base de datos.
 * 
 * @author EMMANUEL
 */
public class ReservaDao {
    private Connection conexion;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public ReservaDao() {
        this.conexion = DataSource.obtenerConexion();
    }

    /**
     * Método para obtener todas las reservas con estado "Reservado".
     * 
     * @return Una lista de objetos Reserva con el estado "Reservado".
     */
    public ArrayList<Reserva> obtenerReservas() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        String sql = """
            SELECT r.idReserva, r.fechaReserva, r.horaReserva, r.duracionReserva, r.estadoReserva, 
                   m.idMesa, m.numeroMesa, m.estadoMesa, 
                   c.idCliente, c.nombre, c.apellido, c.telefono,
                   p.idPedido, p.estadoPedido, p.totalPedido
            FROM reserva r
            JOIN mesa m ON r.idMesa = m.idMesa
            JOIN cliente c ON r.idCliente = c.idCliente
            LEFT JOIN pedido p ON r.idPedido = p.idPedido
            WHERE r.estadoReserva IN ('Reservada', 'En Progreso')
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setIdReserva(rs.getInt("idReserva"));
                reserva.setFechaReserva(rs.getDate("fechaReserva"));
                reserva.setHoraReserva(rs.getTime("horaReserva"));
                reserva.setDuracionReserva(rs.getInt("duracionReserva"));
                reserva.setEstadoReserva(rs.getString("estadoReserva"));

                // Crear y configurar el objeto Mesa
                Mesa mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("idMesa"));
                mesa.setNumeroMesa(rs.getInt("numeroMesa"));
                mesa.setEstadoMesa(rs.getString("estadoMesa"));
                reserva.setMesa(mesa);

                // Crear y configurar el objeto Cliente
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setTelefono(rs.getString("telefono"));
                reserva.setCliente(cliente);

                // Crear y configurar el objeto Pedido (si existe)
                if (rs.getInt("idPedido") != 0) { // Verificar si hay pedido asociado
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(rs.getInt("idPedido"));
                    pedido.setEstado(rs.getString("estadoPedido"));
                    pedido.setTotal(rs.getDouble("totalPedido"));
                    reserva.setPedido(pedido);
                }

                reservas.add(reserva);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reservas: " + e.getMessage());
        }
        return reservas;
    }


}

