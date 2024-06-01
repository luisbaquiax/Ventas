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
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Luis
 */
public class Datos {

    private ClienteDb clienteDb;
    private ProductoDb productoDb;
    private VentaDb ventaDb;
    private List<Producto> productos;
    private List<Cliente> clientes;
    private List<Venta> ventas;

    public Datos() {
        this.clienteDb = new ClienteDb();
        this.productoDb = new ProductoDb();
        this.ventaDb = new VentaDb();
        this.productos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {

        Coneccion.getConnection();
        ClienteDb clienteDb = new ClienteDb();
        VentaDb ventaDb = new VentaDb();
        //clienteDb.insert(new Cliente("Luis", "baquiax", "arroga.com"));
        System.out.println(Arrays.toString(clienteDb.getClientes().toArray()));
        System.out.println(Arrays.toString(ventaDb.getVentas().toArray()));
        Datos datos = new Datos();

        datos.manejoDatos(null);

    }

    /**
     * Crea un Worbook usando el inputStream, despues se recorre cada fila sin
     * tomar en cuenta la primera fila que corresponde al encabezado
     *
     * @param inputStream
     */
    public void manejoDatos(InputStream inputStream) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
//            FileInputStream file = new FileInputStream(new File("C:/Users/Usuario/Desktop/Prueba Técnica Luis/Ventas.xlsx"));
            Workbook workbook = new XSSFWorkbook(inputStream);
            //Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int index = 0;
            for (Row row : sheet) {
                if (index > 0) {
                    if (row.getCell(0) != null) {
                        String nombre = getCellValueAsString(row.getCell(1), dateFormat);
                        String apellido = getCellValueAsString(row.getCell(2), dateFormat);
                        String correo = getCellValueAsString(row.getCell(3), dateFormat);

                        Cliente cliente = new Cliente(nombre, apellido, correo);
                        clientes.add(cliente);

                        String producto = getCellValueAsString(row.getCell(5), dateFormat);
                        String codigoBarras = getCellValueAsString(row.getCell(4), dateFormat);
                        String descripcion = getCellValueAsString(row.getCell(6), dateFormat);
                        String categoria = getCellValueAsString(row.getCell(7), dateFormat);
                        String precio = getCellValueAsString(row.getCell(9), dateFormat).strip();

                        Producto pro = new Producto(codigoBarras, producto, descripcion, categoria, Double.parseDouble(precio));
                        productos.add(pro);

                        String fechaVenta = getCellValueAsString(row.getCell(0), dateFormat);
                        String cantidad = getCellValueAsString(row.getCell(8), dateFormat);
                        String total = getCellValueAsString(row.getCell(10), dateFormat);

                        Venta venta = new Venta(fechaVenta, 0, 0, Integer.parseInt(cantidad), Double.parseDouble(total));
                        ventas.add(venta);
                    }
                }
                index++;
            }
            subirDatos();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void subirDatos() {
        for (Producto p : productos) {
            productoDb.insert(p);
        }
        for (Cliente cliente : clientes) {
            clienteDb.insert(cliente);
        }
        List<Cliente> clientes = clienteDb.getClientes();
        List<Producto> productos = productoDb.getProductos();
        for (int i = 0; i < ventas.size(); i++) {
            ventas.get(i).setIdCliente(clientes.get(i).getId());
            ventas.get(i).setIdProducto(productos.get(i).getId());
        }
        for (int i = 0; i < ventas.size(); i++) {
            ventaDb.insert(ventas.get(i));
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
