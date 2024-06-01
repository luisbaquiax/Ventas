/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.baquiax.ventasapi.controller;

import com.baquiax.ventasapi.database.ClienteDb;
import com.baquiax.ventasapi.database.VentaDb;
import com.baquiax.ventasapi.model.Datos;
import com.baquiax.ventasapi.model.Venta;
import com.baquiax.ventasapi.objetos.JsonConverter;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.annotation.MultipartConfig;

/**
 *
 * @author Luis
 */
@WebServlet(name = "ControllerData", urlPatterns = {"/ControllerData"})
@MultipartConfig
public class ControllerData extends HttpServlet {

    JsonConverter converter;
    VentaDb ventasDb;
    ClienteDb clienteDb;

    public ControllerData() {
        this.converter = new JsonConverter();
        this.ventasDb = new VentaDb();
        this.clienteDb = new ClienteDb();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("hola get");
        if (request.getParameter("tarea").equals("ventas")) {
            //response.getWriter().write(converter.toJson(clienteDb.getClientes()));
            response.getWriter().write(converter.toJson(ventasDb.getVentas()));
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("hola post");

        Part partes = request.getPart("archivo");
        InputStream inputStream = partes.getInputStream();
        //procesar datos
        Datos datos = new Datos();
        datos.manejoDatos(inputStream);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");

        //mensaje
        response.getWriter().print("{\"message\": \"Se ha leido el contendio del archivo y subido el contenido del archivo\"}");

    }

}
