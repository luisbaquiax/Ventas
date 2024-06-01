/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baquiax.ventasapi.database;

import com.baquiax.ventasapi.model.Producto;
import com.baquiax.ventasapi.model.Venta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis
 */
public class ProductoDb {

    private final String SELECT = "SELECT * FROM [test].[Productos]";
    private final String INSERT = "INSERT INTO [test].[Productos] (codigo_barras,nombre_producto,descripcion,categoria,precio) VALUES(?,?,?,?,?)";
    public static final String ULTIMO
            = "SELECT TOP 1 [Productos].id_producto AS ultimo \n"
            + "FROM [test].[Productos] \n"
            + "ORDER BY [Productos].id_producto DESC;";

    /**
     * Insert a new Producto into table Productos
     *
     * @param producto
     * @return
     */
    public boolean insert(Producto producto) {
        try {
            PreparedStatement statement = Coneccion.getConnection().prepareStatement(INSERT);
            statement.setString(1, producto.getCodigoBarra());
            statement.setString(2, producto.getNombre());
            statement.setString(3, producto.getDescripcion());
            statement.setString(4, producto.getCategoria());
            statement.setDouble(5, producto.getPrecio());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(VentaDb.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    /**
     * Return a list of Products
     *
     * @return
     */
    public List<Producto> getProductos() {
        List<Producto> list = new ArrayList<>();
        try {
            PreparedStatement statement = Coneccion.getConnection().prepareStatement(SELECT);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Producto(resultSet.getInt("id_producto"), resultSet.getString("codigo_barras"), resultSet.getString("nombre_producto"), resultSet.getString("descripcion"), resultSet.getString("categoria"), resultSet.getDouble("precio")));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

}
