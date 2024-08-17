/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author kewin
 */
public class Conexion {
    
    String dataBase = "Biblioteca";
    String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String password = "maslovy";
    String driver = "com.mysql.cj.jdbc.Driver";
    
    Connection conection;
    
    
    public Conexion(){
        
    }
   
    public Connection conectarDB(){
        try {
            Class.forName(driver);
            conection = DriverManager.getConnection(url + dataBase, user, password);
            System.out.println("Conexion a la base de datos " + dataBase + " exitosa!" );
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar a la base de datos " + dataBase);
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return conection;
    }
    
    
    public void desconectarDB() {
        try {
            conection.close();
        } catch (SQLException e) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, e);
        }

    }
    
}
