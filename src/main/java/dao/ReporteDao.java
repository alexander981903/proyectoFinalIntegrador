/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Modelo.Pedido;
import conf.DataSource;
import Modelo.Cliente;
import Modelo.Mesa;
import Modelo.Reserva;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Clase para manejar consultas relacionadas con reportes en la base de datos.
 * Proporciona métodos para obtener información sobre pedidos e ingresos.
 * 
 * @author EMMANUEL
 */
public class ReporteDao {
    
    private Connection conexion;
    private int cantidadReservas;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     */
    public ReporteDao() {
        this.conexion = DataSource.obtenerConexion();
    }

    /**
     * Obtiene una lista de pedidos asociados a un cliente usando su DNI.
     * 
     * @return Lista de reservas en determinado rango de fechas.
     */
    public ArrayList<Reserva> obtenerReservasPorRangoDeFechas(Date fechaInicio, Date fechaFin) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT r.idReserva, r.fechaReserva, r.horaReserva, r.duracionReserva, r.estadoReserva, " +
                     "c.idCliente, c.nombre, c.apellido, c.dni, m.idMesa, m.numeroMesa, " +
                     "p.idPedido, p.fechaPedido, p.estadoPedido, p.totalPedido " +
                     "FROM reserva r " +
                     "INNER JOIN cliente c ON r.idCliente = c.idCliente " +
                     "INNER JOIN mesa m ON r.idMesa = m.idMesa " +
                     "INNER JOIN pedido p ON r.idPedido = p.idPedido " +
                     "WHERE r.fechaReserva BETWEEN ? AND ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setDate(1, fechaInicio);
            ps.setDate(2, fechaFin);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reserva reserva = new Reserva();
                    reserva.setIdReserva(rs.getInt("idReserva"));
                    reserva.setFechaReserva(rs.getDate("fechaReserva"));
                    reserva.setHoraReserva(rs.getTime("horaReserva"));
                    reserva.setDuracionReserva(rs.getInt("duracionReserva"));
                    reserva.setEstadoReserva(rs.getString("estadoReserva"));

                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("idCliente"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setDni(rs.getString("dni"));
                    reserva.setCliente(cliente);

                    Mesa mesa = new Mesa();
                    mesa.setIdMesa(rs.getInt("idMesa"));
                    mesa.setNumeroMesa(rs.getInt("numeroMesa"));
                    reserva.setMesa(mesa);

                    // Crear el objeto pedido y asignar los datos correspondientes
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(rs.getInt("idPedido"));
                    pedido.setFechaPedido(rs.getDate("fechaPedido"));
                    pedido.setEstado(rs.getString("estadoPedido"));
                    pedido.setTotal(rs.getDouble("totalPedido"));
                    reserva.setPedido(pedido);

                    reservas.add(reserva);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reservas por rango de fechas: " + e.getMessage());
        }
        return reservas;
    }



    /**
     * Calcula los ingresos totales generados en el sistema.
     * 
     * @return Total de ingresos en forma de número decimal.
     */
    public double calcularIngresosTotales() {
        double totalIngresos = 0.0;
        String sql = "SELECT SUM(total) AS totalIngresos FROM pedido WHERE estado = 'COMPLETADO'";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalIngresos = rs.getDouble("totalIngresos");
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular ingresos totales: " + e.getMessage());
        }

        return totalIngresos;
    }

    /**
    * Obtiene los clientes que han realizado más reservas, filtrado por nombre o DNI, y un rango de fechas específico.
    * 
    * @param filtro Nombre o DNI del cliente por el que se desea buscar.
    * @param fechaInicio Fecha de inicio del rango de fechas (formato 'YYYY-MM-DD').
    * @param fechaFin Fecha de fin del rango de fechas (formato 'YYYY-MM-DD').
    * @return Lista de clientes que han realizado más reservas dentro del rango de fechas.
    */
   public ArrayList<Cliente> obtenerReservasCliente(String filtro, Date fechaInicio, Date fechaFin) {
       ArrayList<Cliente> clientes = new ArrayList<>();
       String sql = "SELECT c.idCliente, c.nombre, c.apellido, c.dni, COUNT(r.idReserva) AS cantidadReservas " +
                    "FROM cliente c " +
                    "INNER JOIN reserva r ON c.idCliente = r.idCliente " +
                    "WHERE (c.nombre LIKE ? OR c.dni LIKE ?) " +
                    "AND r.fechaReserva BETWEEN ? AND ? " +
                    "GROUP BY c.idCliente " +
                    "ORDER BY cantidadReservas DESC";

       try (PreparedStatement ps = conexion.prepareStatement(sql)) {
           ps.setString(1, "%" + filtro + "%");
           ps.setString(2, "%" + filtro + "%");
           ps.setDate(3, fechaInicio);  // Fecha de inicio
           ps.setDate(4, fechaFin);     // Fecha de fin

           try (ResultSet rs = ps.executeQuery()) {
               while (rs.next()) {
                   Cliente cliente = new Cliente();
                   cliente.setIdCliente(rs.getInt("idCliente"));
                   cliente.setNombre(rs.getString("nombre"));
                   cliente.setApellido(rs.getString("apellido"));
                   cliente.setDni(rs.getString("dni"));
                   cantidadReservas = rs.getInt("cantidadReservas");
                   System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido() + " con " + cantidadReservas + " reservas.");
                   clientes.add(cliente);
               }
           }
       } catch (SQLException e) {
           System.err.println("Error al obtener clientes con más reservas: " + e.getMessage());
       }

       return clientes;
   }
   
    public void exportarReporteAExcel(JTable table) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Reporte");

        // Crear el encabezado de la tabla
        XSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < table.getColumnCount(); i++) {
            XSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(table.getColumnName(i));
        }

        // Rellenar las filas con los datos de la tabla
        for (int i = 0; i < table.getRowCount(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            for (int j = 0; j < table.getColumnCount(); j++) {
                XSSFCell cell = row.createCell(j);
                Object value = table.getValueAt(i, j);
                if (value != null) {
                    cell.setCellValue(value.toString());
                }
            }
        }

        // Crear la carpeta si no existe
        String rutaCarpeta = "C:/Reportes/";
        File carpeta = new File(rutaCarpeta);
        if (!carpeta.exists()) {
            if (carpeta.mkdirs()) {
                System.out.println("Carpeta creada en: " + rutaCarpeta);
            } else {
                System.err.println("No se pudo crear la carpeta.");
                return;
            }
        }

        // Ruta completa del archivo de Excel
        String rutaExcel = rutaCarpeta + "Reporte.xlsx";

        // Guardar el archivo Excel
        try (FileOutputStream fileOut = new FileOutputStream(rutaExcel)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Archivo Excel generado correctamente en: " + rutaExcel);

            // Abrir el archivo Excel
            try {
                File excelFile = new File(rutaExcel);
                if (excelFile.exists()) {
                    Desktop.getDesktop().open(excelFile);
                    System.out.println("El archivo Excel se ha abierto correctamente.");
                }
            } catch (IOException e) {
                System.err.println("Error al intentar abrir el archivo Excel: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error al generar el archivo Excel: " + e.getMessage());
        }
    }



    public int getCantidadReservas() {
        return cantidadReservas;
    }

   

}

