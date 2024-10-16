import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservaView extends JFrame {
    private JTextField eventoInput;
    private JTextField fechaInput;
    private JTextField clienteInput;
    private ReservaController controller;
    private JTextArea reservasArea;

    public ReservaView() {
        controller = new ReservaController();

        setTitle("Gestion De Reservas");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        eventoInput = new JTextField(20);
        eventoInput.setToolTipText("Nombre del Evento");

        fechaInput = new JTextField(20);
        fechaInput.setToolTipText("Fecha del Evento");

        clienteInput = new JTextField(20);
        clienteInput.setToolTipText("Nombre del Cliente");


        JButton agregarButton = new JButton("Agregar Reserva");
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String evento = eventoInput.getText();
                String fecha = fechaInput.getText();
                String cliente = clienteInput.getText();
                ReservaModel reservaModel = new ReservaModel(evento, fecha, cliente);
                controller.agregarReserva(reservaModel);
                eventoInput.setText("");
                fechaInput.setText("");
                clienteInput.setText("");
                mostrarReservas();
            }
        });

        JButton eliminarReservaButton = new JButton("Eliminar Reserva");
        eliminarReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarReserva();
                mostrarReservas();
            }
        });


        JButton cambiarEstadoButton = new JButton("Cambiar Estado a 'Realizado'");
        cambiarEstadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarEstadoReserva();
                mostrarReservas();
            }
        });

        reservasArea = new JTextArea(10, 50);
        reservasArea.setEditable(false);
        add(new JLabel("Evento:"));
        add(eventoInput);
        add(new JLabel("Fecha:"));
        add(fechaInput);
        add(new JLabel("Cliente:"));
        add(clienteInput);
        add(agregarButton);
        add(eliminarReservaButton);
        add(cambiarEstadoButton);
        add(new JScrollPane(reservasArea));

    }

    private void mostrarReservas() {
        reservasArea.setText("");
        for (ReservaModel reservaModel : controller.getReservas()) {
            reservasArea.append(reservaModel.toString() + "\n");
        }
    }

    private void eliminarReserva() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el nombre del evento que desea eliminar:");
        boolean found = false;

        for (ReservaModel reservaModel : controller.getReservas()) {
            if (reservaModel.getEvento().equalsIgnoreCase(input)) {
                controller.eliminarReserva(reservaModel);
                found = true;
                JOptionPane.showMessageDialog(this, "Reserva eliminada con exito");
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Reserva no encontrada");
        }
    }


    private void cambiarEstadoReserva() {
        String input = JOptionPane.showInputDialog(this, "Ingrese el nombre del evento para cambiar su estado a 'Realizado':");
        for (ReservaModel reservaModel : controller.getReservas()) {
            if (reservaModel.getEvento().equalsIgnoreCase(input)) {
                reservaModel.setEstado("Realizado");
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
                new ReservaView().setVisible(true);
            }
        });
    }
}
