/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baquiax.ventasapi.database;

import com.baquiax.ventasapi.model.Cliente;
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
public class ClienteDb {

    private final String INSERT = "INSERT INTO [test].[Clientes] (nombre,apellido,correo_electronico) VALUES(?,?,?);";
    private final String SELECT = "SELECT * FROM [test].[Clientes];";
    public static final String ULTIMO = "SELECT TOP 1 [Clientes].id_cliente AS ultimo \n"
            + "FROM [test].[Clientes] \n"
            + "ORDER BY [Clientes].id_cliente DESC;";

    /**
     * Insert a new Customer into table Clientes
     *
     * @param cliente
     * @return
     */
    public boolean insert(Cliente cliente) {
        try {
            PreparedStatement statement = Coneccion.getConnection().prepareStatement(INSERT);
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellido());
            statement.setString(3, cliente.getEmail());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ClienteDb.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    /**
     * Return a list of Customers
     *
     * @return
     */
    public List<Cliente> getClientes() {
        List<Cliente> list = new ArrayList<>();
        try {
            PreparedStatement statement = Coneccion.getConnection().prepareStatement(SELECT);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new Cliente(resultSet.getInt("id_cliente"), resultSet.getString("nombre"), resultSet.getString("apellido"), resultSet.getString("correo_electronico")));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public int getUltimoId(String sql) {
        int ultimo = 0;
        try (PreparedStatement statemente = Coneccion.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statemente.executeQuery();
            while (resultSet.next()) {
                ultimo = resultSet.getInt("ultimo");
            }
            resultSet.close();
            statemente.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ultimo;
    }
}
