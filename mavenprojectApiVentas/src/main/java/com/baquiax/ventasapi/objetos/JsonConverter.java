/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baquiax.ventasapi.objetos;

import com.google.gson.Gson;

/**
 *
 * @author luis
 * @param <T>
 */
public class JsonConverter<T> {

    private Gson gson;

    public JsonConverter() {
        this.gson = new Gson();
    }

    public T fromJson(String json, Class<T> classT) {
        return gson.fromJson(json, classT);
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

}
