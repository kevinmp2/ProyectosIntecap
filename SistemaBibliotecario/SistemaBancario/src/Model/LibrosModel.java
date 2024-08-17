/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author kewin
 */
public class LibrosModel {
    
    private String nombre;
    private String autor;
    private String fechaLanzamiento;
    private String editorial;
    private int copias;

    public LibrosModel() {
    }

    public LibrosModel(String nombre, String autor, String fechaLanzamiento, String editorial, int copias) {
        this.nombre = nombre;
        this.autor = autor;
        this.fechaLanzamiento = fechaLanzamiento;
        this.editorial = editorial;
        this.copias = copias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getCopias() {
        return copias;
    }

    public void setCopias(int copias) {
        this.copias = copias;
    }
    
}
