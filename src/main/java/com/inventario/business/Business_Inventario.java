/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.business;

import com.inventario.model.Data;
import com.inventario.model.DataException;
import com.inventario.model.Inventario;
import com.inventario.model.Producto;
import com.inventario.repository.IRepository_Inventario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author USUARIO
 */
public class Business_Inventario implements IBusiness_Inventario {

    private static final Logger logger = LoggerFactory.getLogger(Business_Inventario.class);
    private final RestTemplate restTemplate;
    
    @Autowired
    IRepository_Inventario rep_inventario;

    /**
     * 
     * @param restTemplate 
     */
    @Autowired
    public Business_Inventario(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    /**
     * 
     * @param producto
     * @return
     * @throws DataException 
     */
    @Override
    public Inventario crearInventario(Inventario producto) throws DataException {
        logger.info("Añadiendo inventario.");
        Inventario rInventario = null;
        
        try {
            
            ResponseEntity<Data> dataResponse = restTemplate.getForEntity("http://localhost:8080/producto/" + producto.getId_producto(), Data.class);
            
            if (dataResponse.getStatusCode().is2xxSuccessful()) {
                logger.info("Producto " + producto.getId_producto() + " no existe.");
                throw new DataException(404, "Producto " + producto.getId_producto() + " no existe.");
            }
            
            rInventario = rep_inventario.save(producto);
            
        } catch (DataException e) {
            logger.error("Error creando inventario para el producto." + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error creando inventario." + e.getMessage());
//            throw e;
            throw new DataException(500, "Error guardando el producto." + e.getMessage());
        }
        
        return rInventario;
    }

    /**
     * 
     * @param producto
     * @throws DataException 
     */
    @Override
    public void actualizarInventario(Inventario producto) throws DataException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
