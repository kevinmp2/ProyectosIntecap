/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Controller.LibroController;
import Controller.UsuarioController;
import Model.LibroModelDAO;
import Model.UsuarioModelDAO;
import View.LibrosView;
import View.MenuPrincipal;
import View.UsuarioView;

/**
 *
 * @author kewin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.setVisible(true);
        menuPrincipal.setLocationRelativeTo(null);
        
    }
    
}
