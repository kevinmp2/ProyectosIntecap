/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.UsuarioModelDAO;
import View.UsuarioView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author kewin
 */
public class UsuarioController implements ActionListener{
    
    UsuarioView usuario = new UsuarioView();
    UsuarioModelDAO usuarioModel = new UsuarioModelDAO();
    
    
    public UsuarioController(UsuarioView usuar, UsuarioModelDAO usuaModD){
        
        this.usuario = usuar;
        this.usuarioModel = usuaModD;
        this.usuario.BTGuardarUsuario.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == usuario.BTGuardarUsuario){
          String nombre = usuario.TXTNombre.getText();
          int identificacion = Integer.valueOf(usuario.TXTIdentificacion.getText());
          int telefono = Integer.valueOf(usuario.TXTTelefono.getText());
          String direccion = usuario.TXTDireccion.getText();
          
          
          String respuestaRegistro = usuarioModel.insertarUsuario(nombre, identificacion, telefono, direccion);
           
          if(respuestaRegistro != null) {
              JOptionPane.showMessageDialog(null, respuestaRegistro);
          }else{
               JOptionPane.showMessageDialog(null, "Error al registrar!");
          }
      }
    }
       
}
