/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baquiax.ventasapi.objetos;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author luis
 */
public class LectorJson {

    public LectorJson() {
    }

    public String read(BufferedReader bufferedReader) throws IOException {
        String json = "";
        while (bufferedReader.ready()) {
            json += bufferedReader.readLine();
        }
        return json;
    }
}
