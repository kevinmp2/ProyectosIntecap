/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author kewin
 */
public class UsuarioModel {
    
    private String Nombre;
    private int Identificacion;
    private int telefono;
    private String direccion;

    public UsuarioModel() {
    }
    
    public UsuarioModel(String Nombre, int Identificacion, int telefono, String direccion) {
        this.Nombre = Nombre;
        this.Identificacion = Identificacion;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getIdentificacion() {
        return Identificacion;
    }

    public void setIdentificacion(int Identificacion) {
        this.Identificacion = Identificacion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
}
