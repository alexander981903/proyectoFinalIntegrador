/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Cliente;
import Modelo.Mesa;
import Modelo.Pedido;
import Modelo.Producto;
import Modelo.Reserva;
import conf.DataSource;
import java.awt.Desktop;
import java.io.File;
import java.sql.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.*;



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
        PreparedStatement stmtReserva = null;
        PreparedStatement stmtUpdateMesa = null; 
        ResultSet rs = null;
        ResultSet rsStock = null;

        try {
            // Desactivar auto-commit para manejar transacciones
            conexion.setAutoCommit(false);

            // Paso 1: Insertar la reserva en la tabla "reserva" sin idPedido, solo idMesa y cliente
            String sqlReserva = "INSERT INTO reserva (fechaReserva, horaReserva, duracionReserva, estadoReserva, idCliente, idMesa) VALUES (?, ?, ?, 'Reservada', ?, ?)";
            stmtReserva = conexion.prepareStatement(sqlReserva, Statement.RETURN_GENERATED_KEYS);
            stmtReserva.setDate(1, fechaReserva);
            stmtReserva.setTime(2, horaReserva);
            stmtReserva.setInt(3, duracionReserva);
            stmtReserva.setInt(4, idCliente);
            stmtReserva.setInt(5, idMesa);
            stmtReserva.executeUpdate();

            // Obtener el ID de la reserva recién creada
            rs = stmtReserva.getGeneratedKeys();
            int idReserva = 0;
            if (rs.next()) {
                idReserva = rs.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID de la reserva.");
            }

            // Paso 2: Insertar el pedido en la tabla "pedido", ahora sin necesidad de idReserva
            String sqlPedido = "INSERT INTO pedido (fechaPedido, estadoPedido, totalPedido, idCliente, idMesa) VALUES (?, 'Pendiente', ?, ?, ?)";
            stmtPedido = conexion.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            stmtPedido.setDate(1, fechaPedido);
            
            double totalPedido = 0;
            for (Producto producto : productos) {
                double precio = producto.getPrecio();
                totalPedido += precio * producto.getCantidad();                
            }
            
            if (totalPedido == 0) {
                System.err.println("El total del pedido es 0. No se puede realizar la reserva: "+ totalPedido);
                return false;
            }
            
            stmtPedido.setDouble(2, totalPedido);
            stmtPedido.setInt(3, idCliente);
            stmtPedido.setInt(4, idMesa);
            stmtPedido.executeUpdate();

            // Obtener el ID del pedido recién creado
            rs = stmtPedido.getGeneratedKeys();
            int idPedido = 0;
            if (rs.next()) {
                idPedido = rs.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID del pedido.");
            }

            // Paso 3: Actualizar la reserva con el idPedido
            String sqlUpdateReserva = "UPDATE reserva SET idPedido = ? WHERE idReserva = ?";
            PreparedStatement stmtUpdateReserva = conexion.prepareStatement(sqlUpdateReserva);
            stmtUpdateReserva.setInt(1, idPedido);
            stmtUpdateReserva.setInt(2, idReserva);
            stmtUpdateReserva.executeUpdate();

            // Paso 4: Insertar productos en la tabla "pedido_producto"
            String sqlPedidoProducto = "INSERT INTO pedido_producto (idPedido, idProducto, cantidad, precio, tamaño) VALUES (?, ?, ?, ?,?)";
            stmtPedidoProducto = conexion.prepareStatement(sqlPedidoProducto);

            for (Producto producto : productos) {
                // Recuperar el stock actual del producto desde la base de datos
                String sqlStock = "SELECT stock FROM producto WHERE idProducto = ?";
                PreparedStatement stmtStock = conexion.prepareStatement(sqlStock);
                stmtStock.setInt(1, producto.getIdProducto());
                rsStock = stmtStock.executeQuery();

                if (rsStock.next()) {
                    int stockActual = rsStock.getInt("stock");

                    // Verificar si hay suficiente stock
                    if (stockActual < producto.getCantidad()) {
                        throw new SQLException("No hay suficiente stock para el producto: " + producto.getNombreProducto() + ". Stock disponible: " + stockActual);
                    }

                    // Insertar en la tabla pedido_producto
                    stmtPedidoProducto.setInt(1, idPedido);
                    stmtPedidoProducto.setInt(2, producto.getIdProducto());
                    stmtPedidoProducto.setInt(3, producto.getCantidad());{
                    stmtPedidoProducto.setDouble(4, producto.getPrecio());
                    stmtPedidoProducto.setString(5,producto.getTamaño());

                }
                    stmtPedidoProducto.executeUpdate();

                    // Actualizar el stock de los productos
                    String sqlUpdateProducto = "UPDATE producto SET stock = stock - ? WHERE idProducto = ?";
                    stmtUpdateProducto = conexion.prepareStatement(sqlUpdateProducto);
                    stmtUpdateProducto.setInt(1, producto.getCantidad());
                    stmtUpdateProducto.setInt(2, producto.getIdProducto());
                    stmtUpdateProducto.executeUpdate();
                } else {
                    throw new SQLException("No se encontró el producto con ID: " + producto.getIdProducto());
                }
            }

            // Paso 5: Actualizar el estado de la mesa a "Reservada"
            String sqlUpdateMesa = "UPDATE mesa SET estadoMesa = 'Reservada' WHERE idMesa = ?";
            stmtUpdateMesa = conexion.prepareStatement(sqlUpdateMesa);
            stmtUpdateMesa.setInt(1, idMesa);
            stmtUpdateMesa.executeUpdate();

            // Confirmar la transacción
            conexion.commit();
            return true;

        } catch (SQLException e) {
            // Realizar rollback en caso de error
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
            // Cerrar recursos y reactivar auto-commit
            try {
                if (rs != null) rs.close();
                if (rsStock != null) rsStock.close(); // Cerrar el ResultSet del stock
                if (stmtPedido != null) stmtPedido.close();
                if (stmtPedidoProducto != null) stmtPedidoProducto.close();
                if (stmtUpdateProducto != null) stmtUpdateProducto.close();
                if (stmtReserva != null) stmtReserva.close();
                if (stmtUpdateMesa != null) stmtUpdateMesa.close(); // Cerrar el Statement de actualización de la mesa
                conexion.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public boolean actualizarEstadoAtencion(int idMesa, int idReserva, int idPedido) {
        PreparedStatement stmtSelectMesa = null;
        PreparedStatement stmtSelectReserva = null;
        PreparedStatement stmtSelectPedido = null;
        PreparedStatement stmtUpdateMesa = null;
        PreparedStatement stmtUpdateReserva = null;
        PreparedStatement stmtUpdatePedido = null;

        ResultSet rsMesa = null;
        ResultSet rsReserva = null;
        ResultSet rsPedido = null;

        try {
            conexion.setAutoCommit(false);

            // Verificar y actualizar el estado actual de la mesa
            String sqlSelectMesa = "SELECT estadoMesa FROM mesa WHERE idMesa = ?";
            stmtSelectMesa = conexion.prepareStatement(sqlSelectMesa);
            stmtSelectMesa.setInt(1, idMesa);
            rsMesa = stmtSelectMesa.executeQuery();

            if (rsMesa.next()) {
                String estadoMesa = rsMesa.getString("estadoMesa");
                String nuevoEstadoMesa = switch (estadoMesa) {
                    case "Reservada" -> "Ocupada";
                    case "Ocupada" -> "Disponible";
                    default -> estadoMesa;
                };

                if (!estadoMesa.equals(nuevoEstadoMesa)) {
                    String sqlUpdateMesa = "UPDATE mesa SET estadoMesa = ? WHERE idMesa = ?";
                    stmtUpdateMesa = conexion.prepareStatement(sqlUpdateMesa);
                    stmtUpdateMesa.setString(1, nuevoEstadoMesa);
                    stmtUpdateMesa.setInt(2, idMesa);
                    stmtUpdateMesa.executeUpdate();
                }
            }

            // Verificar y actualizar el estado actual de la reserva
            String sqlSelectReserva = "SELECT estadoReserva FROM reserva WHERE idReserva = ?";
            stmtSelectReserva = conexion.prepareStatement(sqlSelectReserva);
            stmtSelectReserva.setInt(1, idReserva);
            rsReserva = stmtSelectReserva.executeQuery();

            if (rsReserva.next()) {
                String estadoReserva = rsReserva.getString("estadoReserva");
                String nuevoEstadoReserva = switch (estadoReserva) {
                    case "Reservada" -> "En Progreso";
                    case "En Progreso" -> "Terminado";
                    default -> estadoReserva;
                };

                if (!estadoReserva.equals(nuevoEstadoReserva)) {
                    String sqlUpdateReserva = "UPDATE reserva SET estadoReserva = ? WHERE idReserva = ?";
                    stmtUpdateReserva = conexion.prepareStatement(sqlUpdateReserva);
                    stmtUpdateReserva.setString(1, nuevoEstadoReserva);
                    stmtUpdateReserva.setInt(2, idReserva);
                    stmtUpdateReserva.executeUpdate();
                }
            }

            // Verificar y actualizar el estado actual del pedido
            String sqlSelectPedido = "SELECT estadoPedido FROM pedido WHERE idPedido = ?";
            stmtSelectPedido = conexion.prepareStatement(sqlSelectPedido);
            stmtSelectPedido.setInt(1, idPedido);
            rsPedido = stmtSelectPedido.executeQuery();

            if (rsPedido.next()) {
                String estadoPedido = rsPedido.getString("estadoPedido");
                String nuevoEstadoPedido = switch (estadoPedido) {
                    case "Pendiente" -> "En Progreso";
                    case "En Progreso" -> "Atendido";
                    default -> estadoPedido;
                };

                if (!estadoPedido.equals(nuevoEstadoPedido)) {
                    String sqlUpdatePedido = "UPDATE pedido SET estadoPedido = ? WHERE idPedido = ?";
                    stmtUpdatePedido = conexion.prepareStatement(sqlUpdatePedido);
                    stmtUpdatePedido.setString(1, nuevoEstadoPedido);
                    stmtUpdatePedido.setInt(2, idPedido);
                    stmtUpdatePedido.executeUpdate();
                }
            }

            conexion.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            System.err.println("Error al actualizar los estados: " + e.getMessage());
            return false;

        } finally {
            try {
                if (rsMesa != null) rsMesa.close();
                if (rsReserva != null) rsReserva.close();
                if (rsPedido != null) rsPedido.close();
                if (stmtSelectMesa != null) stmtSelectMesa.close();
                if (stmtSelectReserva != null) stmtSelectReserva.close();
                if (stmtSelectPedido != null) stmtSelectPedido.close();
                if (stmtUpdateMesa != null) stmtUpdateMesa.close();
                if (stmtUpdateReserva != null) stmtUpdateReserva.close();
                if (stmtUpdatePedido != null) stmtUpdatePedido.close();
                conexion.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public boolean generarComprobanteWord(int idReserva) {
        PreparedStatement stmtReserva = null;
        ResultSet rsReserva = null;

        try {
            String sql = """
                SELECT r.idReserva, r.fechaReserva, r.horaReserva, r.duracionReserva, r.estadoReserva,
                       p.idPedido, p.totalPedido, p.subtotalPedido, p.igvPedido,
                       c.nombre AS cliente_nombre, c.apellido AS cliente_apellido, c.dni AS cliente_dni, c.telefono AS cliente_telefono,
                       pp.idProducto, pr.nombrePlato AS producto_nombre, pp.cantidad AS producto_cantidad, pp.precio AS producto_precio, pp.total AS producto_total
                FROM reserva r
                JOIN cliente c ON r.idCliente = c.idCliente
                JOIN pedido p ON r.idPedido = p.idPedido
                JOIN pedido_producto pp ON p.idPedido = pp.idPedido
                JOIN producto pr ON pp.idProducto = pr.idProducto
                WHERE r.idReserva = ?
            """;

            stmtReserva = conexion.prepareStatement(sql);
            stmtReserva.setInt(1, idReserva);
            rsReserva = stmtReserva.executeQuery();

            if (rsReserva.next()) {
                // Crear el documento Word
                XWPFDocument document = new XWPFDocument();

                // Agregar título
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setText("Comprobante de Reserva");
                titleRun.setBold(true);
                titleRun.setFontSize(16);

                // Agregar detalles de la reserva
                XWPFParagraph reservaDetails = document.createParagraph();
                reservaDetails.setAlignment(ParagraphAlignment.LEFT);
                reservaDetails.createRun().setText("ID Reserva: " + rsReserva.getInt("idReserva"));
                reservaDetails.createRun().setText("\nFecha Reserva: " + rsReserva.getDate("fechaReserva"));
                reservaDetails.createRun().setText("\nHora Reserva: " + rsReserva.getTime("horaReserva"));
                reservaDetails.createRun().setText("\nDuración Reserva: " + rsReserva.getInt("duracionReserva") + " minutos");
                reservaDetails.createRun().setText("\nEstado Reserva: " + rsReserva.getString("estadoReserva"));

                // Agregar detalles del cliente
                XWPFParagraph clienteDetails = document.createParagraph();
                clienteDetails.setAlignment(ParagraphAlignment.LEFT);
                clienteDetails.createRun().setText("Cliente: " + rsReserva.getString("cliente_nombre") + " " + rsReserva.getString("cliente_apellido"));
                clienteDetails.createRun().setText("\nDNI: " + rsReserva.getString("cliente_dni"));
                clienteDetails.createRun().setText("\nTeléfono: " + rsReserva.getString("cliente_telefono"));

                // Crear una lista para los productos
                XWPFParagraph productosDetails = document.createParagraph();
                productosDetails.setAlignment(ParagraphAlignment.LEFT);
                productosDetails.createRun().setText("\nProductos:");

                // Crear una tabla para los productos
                XWPFTable table = document.createTable();
                XWPFTableRow headerRow = table.getRow(0);
                headerRow.getCell(0).setText("Producto");
                headerRow.addNewTableCell().setText("Cantidad");
                headerRow.addNewTableCell().setText("Precio");
                headerRow.addNewTableCell().setText("Total");
                
                XWPFParagraph pedidoDetails = document.createParagraph();
                pedidoDetails.setAlignment(ParagraphAlignment.LEFT);
                pedidoDetails.createRun().setText("\nSubtotal: S/ " + rsReserva.getDouble("subtotalPedido"));
                pedidoDetails.createRun().setText("\nIGV: S/ " + rsReserva.getDouble("igvPedido"));
                pedidoDetails.createRun().setText("\nTotal Pedido: S/ " + rsReserva.getDouble("totalPedido"));


                // Crear una lista de productos antes de leer los detalles de cada uno
                ArrayList<Map<String, Object>> productos = new ArrayList<>();

                // Leer todos los productos asociados con la reserva
                do {
                    Map<String, Object> producto = new HashMap<>();
                    producto.put("nombre", rsReserva.getString("producto_nombre"));
                    producto.put("cantidad", rsReserva.getInt("producto_cantidad"));
                    producto.put("precio", rsReserva.getDouble("producto_precio"));
                    producto.put("total", rsReserva.getDouble("producto_total"));
                    productos.add(producto);
                } while (rsReserva.next());

                // Ahora recorrer la lista de productos y agregar filas a la tabla
                for (Map<String, Object> producto : productos) {
                    XWPFTableRow productRow = table.createRow();
                    productRow.getCell(0).setText((String) producto.get("nombre"));
                    productRow.getCell(1).setText(String.valueOf(producto.get("cantidad")));
                    productRow.getCell(2).setText(String.valueOf(producto.get("precio")));
                    productRow.getCell(3).setText(String.valueOf(producto.get("total")));
                }
                
                
                // Guardar el documento
                String rutaCarpeta = "C:/Comprobantes/";
                File carpeta = new File(rutaCarpeta);

                if (!carpeta.exists()) {
                    if (carpeta.mkdirs()) {
                        System.out.println("Carpeta creada en: " + rutaCarpeta);
                    } else {
                        System.err.println("No se pudo crear la carpeta.");
                        return false;
                    }
                }

                String rutaPersonalizada = rutaCarpeta + "Comprobante_Reserva_" + idReserva + ".docx";

                try (FileOutputStream fileOut = new FileOutputStream(rutaPersonalizada)) {
                    document.write(fileOut);
                }

                document.close();

                System.out.println("Comprobante generado correctamente en Word.");
                try {
                    File archivo = new File(rutaPersonalizada);
                    if (archivo.exists()) {
                        Desktop.getDesktop().open(archivo);
                        System.out.println("El archivo se ha abierto correctamente.");
                    }
                } catch (IOException e) {
                    System.err.println("Error al intentar abrir el archivo: " + e.getMessage());
                }
                return true;
            } else {
                System.err.println("No se encontró la reserva.");
                return false;
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error al generar el comprobante: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rsReserva != null) rsReserva.close();
                if (stmtReserva != null) stmtReserva.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }



    
    /**
    * Obtiene una reserva por su ID.
    * 
    * @param idReserva ID de la reserva a buscar.
    * @return un objeto Reserva si se encuentra, o null si no se encuentra.
    */
    public Reserva getReservaPorId(int idReserva) {
        Reserva reserva = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT r.idReserva, r.fechaReserva, r.horaReserva, r.duracionReserva, r.estadoReserva, " +
                         "r.idCliente, r.idMesa, r.idPedido, c.nombre AS nombreCliente, m.numeroMesa, m.estadoMesa, p.fechaPedido, p.totalPedido " +
                         "FROM reserva r " +
                         "JOIN cliente c ON r.idCliente = c.idCliente " +
                         "JOIN mesa m ON r.idMesa = m.idMesa " +
                         "JOIN pedido p ON r.idPedido = p.idPedido " +
                         "WHERE r.idReserva = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idReserva);
            rs = stmt.executeQuery();

            if (rs.next()) {
                reserva = new Reserva();
                reserva.setIdReserva(rs.getInt("idReserva"));
                reserva.setFechaReserva(rs.getDate("fechaReserva"));
                reserva.setHoraReserva(rs.getTime("horaReserva"));
                reserva.setDuracionReserva(rs.getInt("duracionReserva"));
                reserva.setEstadoReserva(rs.getString("estadoReserva"));

                // Crear el objeto Cliente
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombreCliente"));
                reserva.setCliente(cliente);

                // Crear el objeto Mesa
                Mesa mesa = new Mesa();
                mesa.setIdMesa(rs.getInt("idMesa"));
                mesa.setNumeroMesa(rs.getInt("numeroMesa"));
                mesa.setEstadoMesa(rs.getString("estadoMesa"));
                reserva.setMesa(mesa);

                // Crear el objeto Pedido
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("idPedido"));
                pedido.setFechaPedido(rs.getDate("fechaPedido"));
                pedido.setTotal(rs.getDouble("totalPedido"));
                reserva.setPedido(pedido);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la reserva por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reserva;
    }




}

