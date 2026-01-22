/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.inventario.business;

import com.inventario.model.DataException;
import com.inventario.model.Inventario;

/**
 *
 * @author USUARIO
 */
public interface IBusiness_Inventario {
    
    /**
     * 
     * @param producto
     * @return
     * @throws DataException 
     */
    public Inventario crearInventario(Inventario producto) throws DataException;
    
    /**
     * 
     * @param producto
     * @throws DataException 
     */
    public void actualizarInventario(Inventario producto) throws DataException;
    
}
