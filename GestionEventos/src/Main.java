import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JTextField eventoInput;
    private JTextField fechaInput;
    private JTextField clienteInput;
    private ReservaController controller;
    private JTextArea reservasArea;

    public Main() {
        controller = new ReservaController();

        // Configurar la ventana principal
        setTitle("Gestion De Reservas");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Campos de entrada
        eventoInput = new JTextField(20);
        eventoInput.setToolTipText("Nombre del Evento");

        fechaInput = new JTextField(20);
        fechaInput.setToolTipText("Fecha del Evento");

        clienteInput = new JTextField(20);
        clienteInput.setToolTipText("Nombre del Cliente");

        // Botón para agregar reserva
        JButton agregarButton = new JButton("Agregar Reserva");
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String evento = eventoInput.getText();
                String fecha = fechaInput.getText();
                String cliente = clienteInput.getText();
                Reserva reserva = new Reserva(evento, fecha, cliente);
                controller.agregarReserva(reserva);
                eventoInput.setText("");
                fechaInput.setText("");
                clienteInput.setText("");
                mostrarReservas(); // Refrescar la lista de reservas
            }
        });

// Botón para eliminar reservas
        JButton eliminarReservaButton = new JButton("Eliminar Reserva");
        eliminarReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarReserva(); // Llamar al método para eliminar la reserva
                mostrarReservas(); // Refrescar la lista de reservas después de la eliminación
            }
        });


        // Botón para cambiar el estado de la reserva seleccionada
        JButton cambiarEstadoButton = new JButton("Cambiar Estado a 'Realizado'");
        cambiarEstadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarEstadoReserva(); // Cambiar el estado de la reserva seleccionada
                mostrarReservas(); // Refrescar la lista de reservas
            }
        });

        // Área de texto para mostrar las reservas
        reservasArea = new JTextArea(10, 50);
        reservasArea.setEditable(false);

        // Añadir componentes a la ventana
        add(new JLabel("Evento:"));
        add(eventoInput);
        add(new JLabel("Fecha:"));
        add(fechaInput);
        add(new JLabel("Cliente:"));
        add(clienteInput);
        add(agregarButton);
        add(eliminarReservaButton);
        add(cambiarEstadoButton);
        add(new JScrollPane(reservasArea)); // Para que el área de texto sea desplazable

    }

    // Método para mostrar las reservas en el área de texto
    private void mostrarReservas() {
        reservasArea.setText(""); // Limpiar el área de texto
        for (Reserva reserva : controller.getReservas()) {
            reservasArea.append(reserva.toString() + "\n");
        }
    }

    // Método para eliminar una reserva
    private void eliminarReserva() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el nombre del evento que desea eliminar:");
        boolean found = false;

        // Usamos un ciclo para buscar y eliminar la reserva correspondiente
        for (Reserva reserva : controller.getReservas()) {
            if (reserva.getEvento().equalsIgnoreCase(input)) {
                controller.eliminarReserva(reserva); // Llamar al método eliminar en el controlador
                found = true;
                JOptionPane.showMessageDialog(this, "Reserva eliminada con éxito");
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Reserva no encontrada");
        }
    }


    // Método para cambiar el estado de la reserva seleccionada
    private void cambiarEstadoReserva() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el nombre del evento para cambiar su estado a 'Realizado':");
        for (Reserva reserva : controller.getReservas()) {
            if (reserva.getEvento().equalsIgnoreCase(input)) {
                reserva.setEstado("Realizado");
                JOptionPane.showMessageDialog(this, "Estado de la reserva cambiado a 'Realizado'");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Reserva no encontrada");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
