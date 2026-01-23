/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.rest;

import com.inventario.business.IBusiness_Inventario;
import com.inventario.model.Data;
import com.inventario.model.DataException;
import com.inventario.model.Inventario;
import com.inventario.model.Producto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/inventario")
public class InventarioController {
    
    private static final Logger logger = LoggerFactory.getLogger(InventarioController.class);
    Data respuesta;
    
    @Autowired
    private IBusiness_Inventario inventarioBusiness;
    
    /**
     * 
     * @param prod
     * @return 
     */
    @ResponseStatus(HttpStatus.CREATED) 
    @RequestMapping(
            value = "/crear", 
            produces = "application/vnd.api+json", 
            method=RequestMethod.POST)
    @Operation(summary = "Crear un nuevo inventario")
    public ResponseEntity<Data> crearInventarioById(@Parameter(description = "ID Producto, en objeto Inventario", required = true) @RequestBody Inventario prod) {
        logger.info("Iniciando el método insertar Producto.");
        
        try {
            Inventario producto = inventarioBusiness.crearInventario(prod);
            logger.info("Proces OK.");
            
            respuesta = new Data(200,"Proceso OK", producto);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        
        } catch (DataException e) {
            logger.error(e.getMessage());
            respuesta = new Data(e.getRc(), e.getMessage(), null);
            return new ResponseEntity<>(respuesta, e.getRc() == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            respuesta = new Data(500, "Error en el proceso, " + e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_GATEWAY);
        }
    }
    
    /**
     * 
     * @param prod
     * @return 
     */
    @RequestMapping(
            value = "/actualizar", 
            produces = "application/vnd.api+json", 
            method=RequestMethod.POST)
    @Operation(summary = "Actualiza las cantidades dentro del inventario")
    public ResponseEntity<Data> actualizarInventarioById(@Parameter(description = "Objeto Inventario", required = true) @RequestBody Inventario prod) {
        logger.info("Iniciando el método insertar Producto.");
        
        try {
            Inventario producto = inventarioBusiness.actualizarInventario(prod);
            logger.info("Proces OK.");
            
            respuesta = new Data(200,"Proceso OK", producto);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        
        } catch (DataException e) {
            logger.error(e.getMessage());
            respuesta = new Data(e.getRc(), e.getMessage(), null);
            return new ResponseEntity<>(respuesta, e.getRc() == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            respuesta = new Data(500, "Error en el proceso, " + e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_GATEWAY);
        }
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    @GetMapping("/cantidad/{id}")
    @Operation(summary = "Consulta las cantidades de cada producto")
    public ResponseEntity<Data> consultarPorductos(@Parameter(description = "ID Producto", required = true) @PathVariable Integer id) {
        logger.info("Entrando a consultar todo.");
        
        try {
            Producto productoFull = inventarioBusiness.obtenerCantidaById(id);
            logger.info("Proces OK.");
            
            respuesta = new Data(200,"Proceso OK", productoFull);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        
        } catch (DataException e) {
            logger.error(e.getMessage());
            respuesta = new Data(e.getRc(), e.getMessage(), null);
            return new ResponseEntity<>(respuesta, e.getRc() == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            respuesta = new Data(500, "Error en el proceso, " + e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_GATEWAY);
        }
        
    }
    
    /**
     * 
     * @param prod
     * @return 
     */
    @RequestMapping(
            value = "/comprar", 
            produces = "application/vnd.api+json", 
            method=RequestMethod.POST)
    @Operation(summary = "Realiza la compra de un producto especifico")
    public ResponseEntity<Data> comprarProducto(@Parameter(description = "Objeto Inventario", required = true) @RequestBody Inventario prod) {
        logger.info("Iniciando el método insertar Producto.");
        
        try {
            Inventario producto = inventarioBusiness.comprarProducto(prod);
            logger.info("Proces OK.");
            
            respuesta = new Data(200,"Proceso OK", producto);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        
        } catch (DataException e) {
            logger.error(e.getMessage());
            respuesta = new Data(e.getRc(), e.getMessage(), null);
            return new ResponseEntity<>(respuesta, e.getRc() == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            respuesta = new Data(500, "Error en el proceso, " + e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_GATEWAY);
        }
    }
}
