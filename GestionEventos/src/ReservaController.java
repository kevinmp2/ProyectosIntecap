import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReservaController {
    private Stack<ReservaModel> reservasRecientes;

    public ReservaController() {
        reservasRecientes = new Stack<>();
    }

    public void agregarReserva(ReservaModel reservaModel) {
        reservasRecientes.push(reservaModel);
    }

    public List<ReservaModel> getReservas() {
        return new ArrayList<>(reservasRecientes);
    }

    public void eliminarReserva(ReservaModel reservaModel) {
        reservasRecientes.remove(reservaModel);
    }
}
