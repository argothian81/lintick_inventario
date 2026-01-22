/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.model;

import nl.michelbijnen.jsonapi.annotation.JsonApiObject;
import nl.michelbijnen.jsonapi.annotation.JsonApiProperty;
import nl.michelbijnen.jsonapi.generator.JsonApiDtoExtendable;

/**
 *
 * @author USUARIO
 */
@JsonApiObject("Data")
public class Data extends JsonApiDtoExtendable {
    
    @JsonApiProperty
    private int rc;
    @JsonApiProperty
    private String mensaje;
    @JsonApiProperty
    private Object respuesta;

    public Data(int rc, String mensaje) {
        this.rc = rc;
        this.mensaje = mensaje;
    }

    public Data(int rc, String mensaje, Object respuesta) {
        this.rc = rc;
        this.mensaje = mensaje;
        this.respuesta = respuesta;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Object getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Object respuesta) {
        this.respuesta = respuesta;
    }
    
    
    
}
