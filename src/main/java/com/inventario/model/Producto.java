/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import nl.michelbijnen.jsonapi.annotation.JsonApiObject;
import nl.michelbijnen.jsonapi.annotation.JsonApiProperty;
import nl.michelbijnen.jsonapi.generator.JsonApiDtoExtendable;

/**
 *
 * @author USUARIO
 */

@JsonApiObject("Producto")
@Entity
@Table(name = "productos")
public class Producto extends JsonApiDtoExtendable {
    
    @Id
    
    @JsonApiProperty
    private Integer id_producto;
    @JsonApiProperty
    private String nombre;
    @JsonApiProperty
    private Long precio;
    @JsonApiProperty
    private String descripcion;

    public Producto() {
    }
    
    public Producto(Integer productId) {
        this.id_producto = productId;
    }

    public Producto(String id, Integer id_producto, String nombre, Long precio, String descripcion) {
        this.setId(id);
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.generate("/producto", "/newProducts");
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
