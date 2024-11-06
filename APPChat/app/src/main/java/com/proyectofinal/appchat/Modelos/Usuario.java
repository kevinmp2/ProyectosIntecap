package com.proyectofinal.appchat.Modelos;

public class Usuario {

    private String UID, nombre, email, imagen;

    public Usuario(){

    }

    public Usuario(String UID, String nombre, String email, String imagen) {
        this.UID = UID;
        this.nombre = nombre;
        this.email = email;
        this.imagen = imagen;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
