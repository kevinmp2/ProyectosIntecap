/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.*;

/**
 *
 * @author kewin
 */
public class UsuarioModelDAO {
    
    Conexion conexion;

    public UsuarioModelDAO() {
        this.conexion = new Conexion();
    }
    
    
    public String insertarUsuario(String nombre, int identificacion, int telefono, String direccion){
        
        String respuestaRegisto = null;
        
        try {
            Connection accesoDB = conexion.conectarDB();
            CallableStatement cs = accesoDB.prepareCall("{call insertarUsuario(?,?,?,?)}");
            cs.setString(1, nombre);
            cs.setInt(2, identificacion);
            cs.setInt(3, telefono);
            cs.setString(4, direccion);
            
            
            int filasAfectadas = cs.executeUpdate();
            
            if(filasAfectadas > 0){
                respuestaRegisto = "Registro de usuario exitoso!";
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario" + e);
        }
        
        return respuestaRegisto;
    }
   
}
