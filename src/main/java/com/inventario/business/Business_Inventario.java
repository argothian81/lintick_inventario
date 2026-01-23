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
import jakarta.persistence.EntityNotFoundException;
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
            
            if (!dataResponse.getStatusCode().is2xxSuccessful()) {
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
     * @return 
     * @throws DataException 
     */
    @Override
    public Producto actualizarInventario(Inventario producto) throws DataException {
        logger.info("Actualizando inventario.");
        Inventario rInventario = null;
        Producto rProducto = null;
        
        try {
            
            ResponseEntity<Data> dataResponse = restTemplate.getForEntity("http://localhost:8080/producto/" + producto.getId_producto(), Data.class);
            
            if (!dataResponse.getStatusCode().is2xxSuccessful()) {
                logger.info("Producto " + producto.getId_producto() + " no existe.");
                throw new DataException(404, "Producto " + producto.getId_producto() + " no existe.");
            }
            
            if (rep_inventario.existsById(Long.valueOf(producto.getId_producto().toString()))) {
                logger.info("Producto " + producto.getId_producto() + " sin inventario. Favor añadir productos");
                throw new DataException(404, "Producto " + producto.getId_producto() + " sin inventario. Favor añadir productos");
            }
            
            rProducto = obtenerCantidaById(producto.getId_producto());
            
            rInventario = crearInventario(producto);
            rProducto.setCantidad(rInventario.getCantidad());
            
        } catch (DataException e) {
            logger.error("Error actualizando inventario para el producto." + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error actualizando inventario." + e.getMessage());
//            throw e;
            throw new DataException(500, "Error guardando el producto." + e.getMessage());
        }
        
        return rProducto;
    }

    /**
     * 
     * @param id
     * @return
     * @throws DataException 
     */
    @Override
    public Producto obtenerCantidaById(Integer id) throws DataException {
        logger.info("Actualizando inventario.");
        Inventario rInventario = null;
        Producto rProducto = null;
        
        try {
            
            ResponseEntity<Data> dataResponse = restTemplate.getForEntity("http://localhost:8080/producto/" + id, Data.class);
            
            if (!dataResponse.getStatusCode().is2xxSuccessful()) {
                logger.info("Producto " + id + " no existe.");
                throw new DataException(404, "Producto " + id + " no existe.");
            }
            
            if (rProducto == null) {
                logger.error("Producto " + id + " no existe.");
                throw new EntityNotFoundException("Producto " + id + " no existe.");
            } else {
                logger.info("nombre " + rProducto.getNombre());
            }
            
            rProducto = (Producto) dataResponse.getBody().getRespuesta();
            
            if (rep_inventario.existsById(Long.valueOf(id.toString()))) {
                logger.info("Producto " + id + " sin inventario. Favor añadir productos");
                throw new DataException(404, "Producto " + id + " sin inventario. Favor añadir productos");
            }
            
            rInventario = rep_inventario.getReferenceById(Long.valueOf(id.toString()));
            
            if (rInventario == null) {
                logger.error("Producto " + id + " no existe.");
                throw new EntityNotFoundException("Producto " + id + " no existe.");
            } else {
                logger.info("id " + rInventario.getId_producto() + " y cantidad " + rInventario.getCantidad());
            }
            
            rProducto.setCantidad(rInventario.getCantidad());
            
        } catch (EntityNotFoundException e) {
            logger.error("Producto " + id + " no existe." + e.getMessage());
//            throw e;
            throw new DataException(404, "Producto " + id + " no existe.");
        } catch (DataException e) {
            logger.error("Error actualizando inventario para el producto." + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error actualizando inventario." + e.getMessage());
//            throw e;
            throw new DataException(500, "Error guardando el producto." + e.getMessage());
        }
        
        return rProducto;
    
    }
    
}
