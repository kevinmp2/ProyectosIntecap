/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.sql.Connection;
import Model.UsuarioModel;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import Model.Conexion;

/**
 *
 * @author kewin
 */
public class UsuarioController {
    
    
    // Metodo para iniciar sesion
    public boolean loginUser(UsuarioModel usuario) {
        boolean inicio = false;

        Connection conec = Conexion.conectarDB();
        String sql = "SELECT USUARIO, PASSWORD FROM tbUsuario WHERE USUARIO = ? AND PASSWORD = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conec.prepareStatement(sql);
            pstmt.setString(1, usuario.getUsuario());
            pstmt.setString(2, usuario.getPassword());
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                inicio = true;
            } else {
                System.out.println("Credenciales incorrectas");
                //JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conec != null) conec.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return inicio;
    }
}
