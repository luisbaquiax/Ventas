/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baquiax.ventasapi.model;

import com.baquiax.ventasapi.database.ClienteDb;
import com.baquiax.ventasapi.database.Coneccion;
import com.baquiax.ventasapi.database.ProductoDb;
import com.baquiax.ventasapi.database.VentaDb;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Luis
 */
public class Datos {

    private ClienteDb clienteDb;
    private ProductoDb productoDb;
    private VentaDb ventaDb;

    public Datos() {
        this.clienteDb = new ClienteDb();
        this.productoDb = new ProductoDb();
        this.ventaDb = new VentaDb();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {

        Coneccion.getConnection();
        ClienteDb clienteDb = new ClienteDb();
        //clienteDb.insert(new Cliente("Luis", "baquiax", "arroga.com"));
        System.out.println(Arrays.toString(clienteDb.getClientes().toArray()));
        Datos datos = new Datos();
        
        //datos.manejoDatos(null);
       
    }

    public void manejoDatos(InputStream inputStream) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            FileInputStream file = new FileInputStream(new File("C:/Users/Usuario/Desktop/Prueba Técnica Luis/Ventas.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            int index = 0;
            for (Row row : sheet) {
                if (index > 0) {
                    if (row.getCell(0) != null) {
                        String nombre = getCellValueAsString(row.getCell(1), dateFormat);
                        String apellido = getCellValueAsString(row.getCell(2), dateFormat);
                        String correo = getCellValueAsString(row.getCell(3), dateFormat);

                        Cliente cliente = new Cliente(nombre, apellido, correo);
                        clienteDb.insert(cliente);

                        String producto = getCellValueAsString(row.getCell(5), dateFormat);
                        String codigoBarras = getCellValueAsString(row.getCell(4), dateFormat);
                        String descripcion = getCellValueAsString(row.getCell(6), dateFormat);
                        String categoria = getCellValueAsString(row.getCell(7), dateFormat);
                        String precio = getCellValueAsString(row.getCell(9), dateFormat).strip();

                        Producto pro = new Producto(codigoBarras, producto, descripcion, categoria, Double.parseDouble(precio));
                        productoDb.insert(pro);

                        String fechaVenta = getCellValueAsString(row.getCell(0), dateFormat);
                        String cantidad = getCellValueAsString(row.getCell(8), dateFormat);
                        String total = getCellValueAsString(row.getCell(10), dateFormat);

                        List<Cliente> clientes = clienteDb.getClientes();
                        List<Producto> productos = productoDb.getProductos();
                        int ultimoCliente = 0;
                        int ultimoProducto = 0;
                        if (!clientes.isEmpty() && !productos.isEmpty()) {
                            ultimoCliente = clientes.get(clientes.size() - 1).getId();
                            ultimoProducto = productos.get(productos.size() - 1).getId();
                            Venta venta = new Venta(fechaVenta, ultimoCliente, ultimoProducto, Integer.parseInt(cantidad), Double.parseDouble(total));
                            System.out.println(venta.toString());
                            ventaDb.insert(venta);
                        }

                    }
                }
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCellValueAsString(Cell cell, SimpleDateFormat dateFormat) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    return dateFormat.format(date);
                } else {
                    String number = String.valueOf(cell.getNumericCellValue());
                    return number.substring(0, number.length() - 2);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
