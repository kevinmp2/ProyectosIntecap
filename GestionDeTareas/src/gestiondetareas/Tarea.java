/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestiondetareas;

/**
 *
 * @author kev
 */
public class Tarea {
    
    private String descripcion;
    private boolean completada;
    private int prioridad;

    public Tarea(String descripcion, int prioridad) {
        this.descripcion = descripcion;
        this.completada = false;
        this.prioridad = prioridad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void completar() {
        this.completada = true;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Tarea:" +
                "  descripcion= " + descripcion  +
                ", completada=" + completada +
                ", prioridad=" + prioridad +
                '.';
    }
    
}
