/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baquiax.ventasapi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Luis
 */
public class Coneccion {

    private String url = "jdbc:sqlserver://interviews.database.windows.net:1433;databaseName=interview-luisbasilio";
    private String user = "luisbasilio";
    private String password = "c,aUASND62734easdf";

    private static Coneccion conexionSingleton = null;

    private static Connection CONECCION = null;

    public Coneccion() throws SQLException {
       try {
            CONECCION = DriverManager.getConnection(url, user, password);
            if (CONECCION != null) {
                System.out.println("Conexión exitosa");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (CONECCION != null) {
                try {
                    CONECCION.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        if (CONECCION == null) {
            conexionSingleton = new Coneccion();
        }
        return CONECCION;
    }

}
