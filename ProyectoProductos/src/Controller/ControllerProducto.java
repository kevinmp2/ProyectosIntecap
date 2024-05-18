/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.ProductoModel;
import View.ProductoView;

/**
 *
 * @author kewin
 */
public class ControllerProducto {
    private ProductoModel productoModel;
    private ProductoView productoView;
    
    public ControllerProducto() {
        productoModel = new ProductoModel();
    }
    
    public String insertProducto(String nombreProducto, String NoSerie, int existencia){
        productoModel.setNombre(nombreProducto);
        productoModel.setNoSerie(NoSerie);
        productoModel.setExistencias(existencia);
        
        String mensaje = productoModel.insertarProducto(productoModel);
        
        return mensaje;
        
    }
    
    
}
