/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.LibroModelDAO;
import View.LibrosView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author kewin
 */
public class LibroController implements ActionListener {
    
    
    LibrosView libro = new LibrosView();
    LibroModelDAO libroModel = new LibroModelDAO();
    
    
    public LibroController(LibrosView libroView, LibroModelDAO libroModD){
        this.libro = libroView;
        this.libroModel = libroModD;
        this.libro.BTGuardarLibro.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == libro.BTGuardarLibro){
            String nombre = libro.TXTNombreL.getText();
            String autor = libro.TXTAutorL.getText();
            String fecha = libro.TXTFechaL.getText();
            String editorial = libro.TXTEditorialL.getText();
            int copias = Integer.parseInt(libro.TXTCopias.getText());
            
            
            String respuestaResgistro = libroModel.insertarLibro(nombre, autor, fecha, editorial, copias);
            
            if(respuestaResgistro != null){
                JOptionPane.showMessageDialog(null, respuestaResgistro);
            }else{
                 JOptionPane.showMessageDialog(null, "Error al registrar!");
            }
        }
       
    }
    
}
