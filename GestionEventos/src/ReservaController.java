import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReservaController {
    private Stack<Reserva> reservasRecientes;

    public ReservaController() {
        reservasRecientes = new Stack<>();
    }

    public void agregarReserva(Reserva reserva) {
        reservasRecientes.push(reserva);
    }

    public List<Reserva> getReservas() {
        return new ArrayList<>(reservasRecientes);
    }

    // Método para eliminar una reserva
    public void eliminarReserva(Reserva reserva) {
        reservasRecientes.remove(reserva);
    }
}
