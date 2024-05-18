/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author kewin
 */
public class Conexion {
    
    
    public static Connection conectarDB(){
        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_bancario", "root","maslovy");
            System.out.println("Conexcion a la base de datos correctamente!");
            return cn;
        } catch (SQLException e) {
            System.out.println("Error al conectar la base de datos! " + e);
        }
        
        return null;
    }
        
    /*String dataBase = "Sistema_bancario";
    String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String password = "maslovy";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection conec;

    public Conexion(){
    }
    
    public Connection conectarDB(){
        try {
            Class.forName(driver);
            conec = DriverManager.getConnection(url + dataBase, user, password);
            System.out.println("Conexion a la base de datos " + dataBase + " exitosa!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar la base de datos: " + dataBase);
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, e);
            
        }
        
        return conec;
    }
    
    public void desconectarDB() {
        try {
            conec.close();
        } catch (SQLException e) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, e);
        }

    }
    
    public static void main(String[] args){
        Conexion conexion = new Conexion();
        conexion.conectarDB();
        
    }*/
}
