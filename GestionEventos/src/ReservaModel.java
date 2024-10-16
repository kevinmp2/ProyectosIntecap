public class ReservaModel {
    private String evento;
    private String fecha;
    private String cliente;
    private String estado;

    public ReservaModel(String evento, String fecha, String cliente) {
        this.evento = evento;
        this.fecha = fecha;
        this.cliente = cliente;
        this.estado = "Pendiente";
    }

    // Getters y setters
    public String getEvento() { return evento; }
    public String getFecha() { return fecha; }
    public String getCliente() { return cliente; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Evento: " + evento + ", Fecha: " + fecha + ", Cliente: " + cliente + ", Estado: " + estado;
    }
}

