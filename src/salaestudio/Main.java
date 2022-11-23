package salaestudio;

import java.util.Scanner;

import utils.NombresEstudiantes;

/**
 *
 * @author Sergi
 */
public class Main {

    private static int numEstudiantes = 0;
    private static int maxEstudiantes = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Mensaje inicial de la simulación
        mensaje_inicio();
        // Inicio de la simulación
        inicio_simulacion();
    }

    /**
     * Inicio de la simulación.
     */
    private static void inicio_simulacion() {
        SalaEstudio sala = new SalaEstudio(1, maxEstudiantes);

        sala.setMensaje("Sala de estudio abierta");

        Thread[] hilos = new Thread[numEstudiantes + 1];
        int i = 0;
        hilos[i] = new Thread(new Director(sala));
        hilos[i].start();
        for (i = 1; i < numEstudiantes + 1; i++) {
            String nombre = NombresEstudiantes.getNombre(i - 1);
            hilos[i] = new Thread(new Estudiante(nombre, sala));
            hilos[i].start();
        }

        for (i = 0; i < numEstudiantes + 1; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException ex) {
                System.out.println("Error al esperar a los hilos");
            }
        }

        System.out.println("Fin de la simulación");
    }

    /**
     * Método que muestra el mensaje de inicio de la simulación y pide el número
     * total de estudiantes y el máximo de estudiantes que pueden estar en la sala a
     * la vez.
     * 
     * @return void
     */
    private static void mensaje_inicio() {
        Scanner sc = new Scanner(System.in);

        System.out.println("SIMULACIÓN DE SALA DE ESTUDIO");
        System.out.print("Número de total estudiantes: ");
        numEstudiantes = sc.nextInt();
        System.out.print("\nNúmero máximo de estudiantes: ");
        maxEstudiantes = sc.nextInt();
        System.out.print("\n");

        sc.close();
    }
}
