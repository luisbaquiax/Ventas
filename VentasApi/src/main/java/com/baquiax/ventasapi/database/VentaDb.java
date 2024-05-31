/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baquiax.ventasapi.database;

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
public class VentaDb {

    private final String SELECT = "SELECT * FROM [test].[Ventas]";
    private final String INSERT = "INSERT INTO [test].[Ventas] (fecha_venta,id_cliente,id_producto,cantidad,total_venta) VALUES(?,?,?,?,?)";
    
    public boolean insert(Venta venta) {
        try {
            PreparedStatement statement = Coneccion.getConnection().prepareStatement(INSERT);
            statement.setString(1, venta.getFecha());
            statement.setInt(2, venta.getIdCliente());
            statement.setInt(3, venta.getIdProducto());
            statement.setInt(4, venta.getCantidad());
            statement.setDouble(5, venta.getTotal());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(VentaDb.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public List<Venta> getVentas() {
        List<Venta> list = new ArrayList<>();
        try {
            PreparedStatement statement = Coneccion.getConnection().prepareStatement(SELECT);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                list.add(new Venta(resultSet.getInt("id_venta"), resultSet.getString("fecha_venta"), resultSet.getInt("id_cliente"), resultSet.getInt("id_producto"), resultSet.getInt("cantidad"), resultSet.getDouble("total_venta")));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentaDb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }
}
