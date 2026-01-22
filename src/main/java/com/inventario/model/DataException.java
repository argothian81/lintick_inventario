/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.model;

/**
 *
 * @author USUARIO
 */
public class DataException extends Exception {
    
    private int rc;

    public DataException(int rc, String message) {
        super(message);
        this.rc = rc;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }
    
}
