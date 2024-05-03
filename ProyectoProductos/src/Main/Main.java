/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Model.Conexion;
import View.ProductoView;

/**
 *
 * @author kewin
 */
public class Main {

    public static void main(String[] args) {
        
        ProductoView producto = new ProductoView();
        
        Conexion conexion = new Conexion();
        conexion.conectarDB();
        
        
        producto.setVisible(true);
        

        
        
        
        
        
    }
    
}
