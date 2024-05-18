/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Conexion;
import Model.NuevoClienteModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author kewin
 */
public class NuevoClienteController {
    
    
    /*Metodo para registrar cliente*/
    public boolean nuevoCliente(NuevoClienteModel nuevoCl){
        
        boolean respuesta = false;
        
        Connection conec = Conexion.conectarDB();
        try {
            PreparedStatement consult = conec.prepareStatement("{call nuevoCliente(?, ?, ?, ?, ?)}");
            consult.setInt(1, nuevoCl.getCui());
            consult.setString(2, nuevoCl.getNombre());
            consult.setString(3, nuevoCl.getApellido());
            consult.setInt(4, nuevoCl.getTelefono());
            consult.setString(5, nuevoCl.getDireccion());
            
            if(consult.executeUpdate() > 0) {
                respuesta = true;
            }
            conec.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar el cliente: " + e);
        }
        
        return respuesta;
    }
     
    // Metodo para verificar si ya existe el cliente 
    public boolean existeCliente(int clienteExiste){
        
        boolean respuesta = false;
        String sql = "SELECT CUI FROM Cliente WHERE CUI = '"+ clienteExiste + "';";
        Statement st; 
        
        try {
            Connection conec = Conexion.conectarDB();
            st = conec.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                respuesta = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el cliente: " + e);
        }
        
        return respuesta;
    }
    
    
    public boolean actualizarCliente(NuevoClienteModel cliente, int idCliente) {
        boolean respuesta = false;
        Connection conec = Conexion.conectarDB();

        if (conec != null) {
            try {
                PreparedStatement consulta = conec.prepareStatement("{call ActualizarCliente(?, ?, ?, ?, ?, ?)}");
                consulta.setInt(1, idCliente);
                consulta.setInt(2, cliente.getCui());
                consulta.setString(3, cliente.getNombre());
                consulta.setString(4, cliente.getApellido());
                consulta.setInt(5, cliente.getTelefono());
                consulta.setString(6, cliente.getDireccion());

                int filasAfectadas = consulta.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta = true;
                }
            } catch (SQLException e) {
                System.out.println("Error al actualizar el cliente: " + e);
            } finally {
                try {
                    conec.close();
                } catch (SQLException e) {
                    System.out.println("Error: " + e);
                }
            }
        }

        return respuesta;
    }
    
    public boolean eliminar(int idCliente) {
        boolean respuesta = false;
        Connection conec = Conexion.conectarDB();

        try {
            PreparedStatement consulta = conec.prepareStatement("{call EliminarCliente(?)}");
            consulta.setInt(1, idCliente); 

            int filasAfectadas = consulta.executeUpdate();
            if (filasAfectadas > 0) {
                respuesta = true; 
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e);
        } finally {
            try {
                if (conec != null && !conec.isClosed()) {
                    conec.close();
                }
            } catch (SQLException e) {
                System.out.println("Error" + e); 
            }
        }

        return respuesta;
    }
    
}
