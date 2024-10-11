/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestiondetareas;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author kev
 */
public class GestorTareas {
    
    private ArrayList<Tarea> tareas;

    public GestorTareas() {
        tareas = new ArrayList<>();
    }

    public void agregarTarea(String descripcion, int prioridad) {
        Tarea nuevaTarea = new Tarea(descripcion, prioridad);
        tareas.add(nuevaTarea);
    }

    public void eliminarTarea(int indice) {
        if (indice >= 0 && indice < tareas.size()) {
            tareas.remove(indice);
            System.out.println("Tarea eliminada con exito.");
        } else {
            System.out.println("Índice de tarea invalido.");
        }
    }

    public void mostrarTareas() {
        System.out.println("Tareas pendientes:");
        for (int i = 0; i < tareas.size(); i++) {
            if (!tareas.get(i).isCompletada()) {
                System.out.println(i + ": " + tareas.get(i));
            }
        }
    }

    public void completarTarea(int indice) {
        if (indice >= 0 && indice < tareas.size()) {
            tareas.get(indice).completar();
            System.out.println("Tarea completada.");
        } else {
            System.out.println("Indice de tarea invalido.");
        }
    }

    public void modificarTarea(int indice, String nuevaDescripcion, int nuevaPrioridad) {
        if (indice >= 0 && indice < tareas.size()) {
            Tarea tarea = tareas.get(indice);
            tarea.setDescripcion(nuevaDescripcion);
            tarea.setPrioridad(nuevaPrioridad);
            System.out.println("Tarea modificada con exito.");
        } else {
            System.out.println("Indice de tarea invalido.");
        }
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Menu Gestion De Tareas:");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Eliminar tarea completada");
            System.out.println("3. Mostrar tareas pendientes");
            System.out.println("4. Completar tarea");
            System.out.println("5. Modificar tarea");
            System.out.println("6. Salir");
            System.out.print("Selecciona una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingresa la tarea: ");
                    scanner.nextLine(); 
                    String descripcion = scanner.nextLine();
                    System.out.print("Ingresa la prioridad de la tarea (1-5): ");
                    int prioridad = scanner.nextInt();
                    if (prioridad > 5 ) {
                        System.out.println("El maximo de prioridad es 5");
                    } else {
                        agregarTarea(descripcion, prioridad);
                    }

                    break;
                case 2:
                    System.out.print("Ingresa el indice de la tarea a eliminar: ");
                    int indiceEliminar = scanner.nextInt();
                    eliminarTarea(indiceEliminar);
                    break;
                case 3:
                    mostrarTareas();
                    break;
                case 4:
                    System.out.print("Ingresa el indice de la tarea a completar: ");
                    int indiceCompletar = scanner.nextInt();
                    completarTarea(indiceCompletar);
                    break;
                case 5:
                    System.out.print("Ingresa el índice de la tarea a modificar: ");
                    int indiceModificar = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Ingresa la nueva descripcion: ");
                    String nuevaDescripcion = scanner.nextLine();
                    System.out.print("Ingresa la nueva prioridad (1-5): ");
                    int nuevaPrioridad = scanner.nextInt();
                    modificarTarea(indiceModificar, nuevaDescripcion, nuevaPrioridad);
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción invalida.");
            }
        } while (opcion != 6);

        scanner.close();
    }
    
}
