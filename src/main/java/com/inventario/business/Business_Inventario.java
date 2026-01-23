/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.business;

import com.inventario.correo.Correo;
import com.inventario.model.Data;
import com.inventario.model.DataException;
import com.inventario.model.Inventario;
import com.inventario.model.Producto;
import com.inventario.repository.IRepository_Inventario;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 *
 * @author USUARIO
 */
@Service
public class Business_Inventario implements IBusiness_Inventario {

    private static final Logger logger = LoggerFactory.getLogger(Business_Inventario.class);
    private RestClient restClient;
    
    @Autowired
    IRepository_Inventario rep_inventario;
    
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
            this.restClient = RestClient.builder().baseUrl("http://localhost:8080").build();
            
            Data dataResponse = this.restClient.get().uri("/producto/{id}", producto.getId_producto()).retrieve().body(Data.class);
            
            if (dataResponse.getRc() != 200) {
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
        Integer nueva = 0;
        StringBuilder mensaje;
        
        try {
            
            this.restClient = RestClient.builder().baseUrl("http://localhost:8080").build();
            
            Data dataResponse = this.restClient.get().uri("/producto/{id}", producto.getId_producto()).retrieve().body(Data.class);
            
            if (dataResponse.getRc() != 200) {
                logger.info("Producto " + producto.getId_producto() + " no existe.");
                throw new DataException(404, "Producto " + producto.getId_producto() + " no existe.");
            }
            
            if (rep_inventario.existsById(Long.valueOf(producto.getId_producto().toString()))) {
                logger.info("Producto " + producto.getId_producto() + " sin inventario. Favor añadir productos");
                throw new DataException(404, "Producto " + producto.getId_producto() + " sin inventario. Favor añadir productos");
            }
            
            rProducto = obtenerCantidaById(producto.getId_producto());
            nueva = rProducto.getCantidad() + producto.getCantidad();
            
            producto.setCantidad(nueva);
            
            rInventario = crearInventario(producto);
            rProducto.setCantidad(rInventario.getCantidad());
            
            mensaje = new StringBuilder();
            mensaje.append("Buenos días/tardes");
            mensaje.append("\n");
            mensaje.append("Se ha reagistrado una nueva actualización de inventario sobre el producto ");
            mensaje.append(rProducto.getNombre());
            mensaje.append("(");
            mensaje.append(producto.getId_producto());
            mensaje.append(").");
            mensaje.append("\n");
            mensaje.append("-------------------------------------------------------------------------------");
            mensaje.append("\n");
            mensaje.append("Producto: ");
            mensaje.append(rProducto.getNombre());
            mensaje.append("(");
            mensaje.append(producto.getId_producto());
            mensaje.append(")");
            mensaje.append("\n");
            mensaje.append("Cantidad anterior: ");
            mensaje.append(rProducto.getCantidad());
            mensaje.append("\n");
            mensaje.append("Cantidad nueva: ");
            mensaje.append(nueva);
            mensaje.append("\n");
            
            try {
                Correo correo = new Correo();
                correo.enviarCorreo(mensaje.toString());
            } catch (DataException e) {
                logger.error("Error enviando correo. " + e.getMessage());
            }
            
        } catch (DataException e) {
            logger.error("Error actualizando inventario para el producto. " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error actualizando inventario. " + e.getMessage());
//            throw e;
            throw new DataException(500, "Erroractualizando inventario.");
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
            
            this.restClient = RestClient.builder().baseUrl("http://localhost:8080").build();
            
            Data dataResponse = this.restClient.get().uri("/producto/{id}", id).retrieve().body(Data.class);
            
            if (dataResponse.getRc() != 200) {
                logger.info("Producto " + id + " no existe.");
                throw new DataException(404, "Producto " + id + " no existe.");
            }
            
            if (rProducto == null) {
                logger.error("Producto " + id + " no existe.");
                throw new EntityNotFoundException("Producto " + id + " no existe.");
            } else {
                logger.info("nombre " + rProducto.getNombre());
            }
            
            rProducto = (Producto) dataResponse.getRespuesta();
            
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
            logger.error("Error obteniendo producto. " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error obteniendo producto. " + e.getMessage());
//            throw e;
            throw new DataException(500, "Error obteniendo producto. ");
        }
        
        return rProducto;
    
    }

    /**
     * Business para realizar compra
     * 
     * @param compra
     * @return
     * @throws DataException 
     */
    @Override
    public Producto comprarProducto(Inventario compra) throws DataException {
        logger.info("Realizando compra.");
        Inventario rInventario = null;
        Producto rProducto = null;
        Integer nueva = 0;
        StringBuilder mensaje;
        
        try {
            
        //Conectando a servicio de productos
            this.restClient = RestClient.builder().baseUrl("http://localhost:8080").build();
            
            Data dataResponse = this.restClient.get().uri("/producto/{id}", compra.getId_producto()).retrieve().body(Data.class);
        
        //Validando que el producto exista
            if (dataResponse.getRc() != 200) {
                logger.info("Producto " + compra.getId_producto() + " no existe.");
                throw new DataException(404, "Producto " + compra.getId_producto() + " no existe.");
            }
            
            if (rep_inventario.existsById(Long.valueOf(compra.getId_producto().toString()))) {
                logger.info("Producto " + compra.getId_producto() + " sin inventario. Favor añadir productos");
                throw new DataException(404, "Producto " + compra.getId_producto() + " sin inventario. Favor añadir productos");
            }
            
            rProducto = obtenerCantidaById(compra.getId_producto());
            
            mensaje = new StringBuilder();
        
        //Validando existencia de producto
            if (rProducto.getCantidad() < compra.getCantidad()) {
                logger.info("No hay suficientes " + rProducto.getNombre() + "(" + compra.getId_producto() + ") en inventario. Favor escoja menos cantidad o solicite el aumento de inventario");
                
                try {
                    mensaje.append("Buenos días/tardes");
                    mensaje.append("\n");
                    mensaje.append("Se intentó realizar una compra,  pero el producto no presenta inventario suficiente ");
                    mensaje.append(rProducto.getNombre());
                    mensaje.append("(");
                    mensaje.append(compra.getId_producto());
                    mensaje.append("), pero no hay inventario suficiente.");
                    mensaje.append("\n");
                    mensaje.append("-------------------------------------------------------------------------------");
                    mensaje.append("\n");
                    mensaje.append("Producto: ");
                    mensaje.append(rProducto.getNombre());
                    mensaje.append("(");
                    mensaje.append(compra.getId_producto());
                    mensaje.append(")");
                    mensaje.append("\n");
                    mensaje.append("Cantidad actual: ");
                    mensaje.append(rProducto.getCantidad());
                    mensaje.append("\n");
                    mensaje.append("Cantidad solicitada: ");
                    mensaje.append(compra.getCantidad());
                    mensaje.append("\n");
                    
                //Correo de alerta
                    Correo correo = new Correo();
                    correo.enviarCorreo(mensaje.toString());
                } catch (DataException e) {
                    logger.error("Error enviando correo. " + e.getMessage());
                }
                
                throw new DataException(400, "No hay suficientes " + rProducto.getNombre() + "(" + compra.getId_producto() + ") en inventario. Favor escoja menos cantidad o solicite el aumento de inventario");
            }
            
        //Realizando actualización de inventario
            nueva = rProducto.getCantidad() - compra.getCantidad();
            
            compra.setCantidad(nueva);
            
            rInventario = crearInventario(compra);
            rProducto.setCantidad(rInventario.getCantidad());
            
            try {
                mensaje.append("Buenos días/tardes");
                mensaje.append("\n");
                mensaje.append("Se realizó una compra por el producto ");
                
                mensaje.append(rProducto.getNombre());
                mensaje.append("(");
                mensaje.append(compra.getId_producto());
                mensaje.append(").");
                mensaje.append("\n");
                mensaje.append("-------------------------------------------------------------------------------");
                mensaje.append("\n");
                mensaje.append("Producto: ");
                mensaje.append(rProducto.getNombre());
                mensaje.append("(");
                mensaje.append(compra.getId_producto());
                mensaje.append(")");
                mensaje.append("\n");
                mensaje.append("Cantidad solicitada: ");
                mensaje.append(compra.getCantidad());
                mensaje.append("\n");
                mensaje.append("Cantidad actual (luego de la compra): ");
                mensaje.append(nueva);
                mensaje.append("\n");
                
            //Mensaje de compra exitosa
                Correo correo = new Correo();
                correo.enviarCorreo(mensaje.toString());
            } catch (DataException e) {
                logger.error("Error enviando correo. " + e.getMessage());
            }
            
        } catch (DataException e) {
            logger.error("Error realizando compra para el producto. " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error realizando compra para el producto. " + e.getMessage());
//            throw e;
            throw new DataException(500, "Error realizando compra para el producto. ");
        }
        
        return rProducto;
    }
    
}
