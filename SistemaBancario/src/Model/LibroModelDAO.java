/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author kewin
 */
public class LibroModelDAO {
    
    Conexion conexion;

    public LibroModelDAO() {
        this.conexion = new Conexion();
    }
    
    
    public String insertarLibro(String nombre, String autor, String fechaLanzamiento, String editorial, int copias) {

        String respuestaRegistro = null;

        try {
            // Obtener la conexión a la base de datos
            Connection accesoDB = conexion.conectarDB();
            
           

            // Llamar al procedimiento almacenado de insertar libro
            CallableStatement cs = accesoDB.prepareCall("{call InsertarLibro(?,?,?,?,?)}");

            // Configurar los parámetros del procedimiento almacenado
            cs.setString(1, nombre);
            cs.setString(2, autor);
            cs.setString(3, fechaLanzamiento);
            cs.setString(4, editorial);
            cs.setInt(5, copias);

            // Ejecutar la consulta
            int filasAfectadas = cs.executeUpdate();

            // Verificar si el registro fue exitoso
            if (filasAfectadas > 0) {
                respuestaRegistro = "Registro de libro exitoso!";
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar libro: " + e);
        }

        return respuestaRegistro;
    }
    
}
