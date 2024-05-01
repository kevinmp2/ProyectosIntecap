/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

/**
 *
 * @author kewin
 */
public class ProductoModel {
    
    int idProducto;
    String nombre;
    String noSerie;
    int existencias;
    
    private Conexion conexion;

    public ProductoModel() {
        conexion = new Conexion();
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNoSerie() {
        return noSerie;
    }

    public void setNoSerie(String noSerie) {
        this.noSerie = noSerie;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }
    
    
    public String insertarProducto(ProductoModel producto){
        Connection conec = conexion.conectarDB();
        
        if(conec != null) {
            try {
                String insertarProductos = "{call insertarProducto(?, ?, ?)}";
                CallableStatement statement = conec.prepareCall(insertarProductos);
                statement.setString(1, producto.getNombre());
                statement.setString(2, producto.getNoSerie());
                statement.setInt(3, producto.getExistencias());
                statement.execute();
                
                return "Registro insertado exitosamente";
            } catch (SQLException e) {
                return e.getMessage();
            } finally {
                conexion.desconectarDB();
            }
        }else {
            System.out.println("No se logro conectar a la base de datos");
        }
        
        return null;
    }
    
    
}
