package com.proyectofinal.appchat.Modelos;

public class Chats {

    String imagen;
    String nombre;
    String keyChat;
    String uidRecibidos;
    String idMensaje;
    String tipoMensaje;
    String mensaje;
    String emisorUid;
    String receptorUid;
    long tiempo;

    public Chats() {
    }

    public Chats(String imagen, String nombre, String keyChat, String uidRecibidos, String idMensaje, String tipoMensaje, String mensaje, String emisorUid, String receptorUid, long tiempo) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.keyChat = keyChat;
        this.uidRecibidos = uidRecibidos;
        this.idMensaje = idMensaje;
        this.tipoMensaje = tipoMensaje;
        this.mensaje = mensaje;
        this.emisorUid = emisorUid;
        this.receptorUid = receptorUid;
        this.tiempo = tiempo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getKeyChat() {
        return keyChat;
    }

    public void setKeyChat(String keyChat) {
        this.keyChat = keyChat;
    }

    public String getUidRecibidos() {
        return uidRecibidos;
    }

    public void setUidRecibidos(String uidRecibidos) {
        this.uidRecibidos = uidRecibidos;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEmisorUid() {
        return emisorUid;
    }

    public void setEmisorUid(String emisorUid) {
        this.emisorUid = emisorUid;
    }

    public String getReceptorUid() {
        return receptorUid;
    }

    public void setReceptorUid(String receptorUid) {
        this.receptorUid = receptorUid;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }
}
