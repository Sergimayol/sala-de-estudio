package salaestudio;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

import utils.NombresEstudiantes;

/**
 *
 * @author Sergi
 */
public class Main {

    static int numEstudiantes = 0;
    static int maxEstudiantes = 0;
    static int numDirectores = 1;
    static int contNumEstudiantes = 0;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore semEstudiantes;
    static Semaphore semDirectores;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Mensaje inicial de la simulación
        mensaje_inicio();
        // Asignación de los semáforos
        semEstudiantes = new Semaphore(maxEstudiantes);
        semDirectores = new Semaphore(numDirectores);
        // Inicio de la simulación
        inicio_simulacion();
    }

    /**
     * Inicio de la simulación.
     */
    private static void inicio_simulacion() {
        Thread[] hilos = new Thread[numEstudiantes + 1];

        int i = 0;
        hilos[i] = new Thread(new Director());
        hilos[i].start();
        for (i = 1; i < numEstudiantes + 1; i++) {
            String nombre = NombresEstudiantes.getNombre(i - 1);
            hilos[i] = new Thread(new Estudiante(nombre));
            hilos[i].start();
        }

        for (i = 0; i < numEstudiantes + 1; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException ex) {
                System.out.println("Error al esperar a los hilos");
            }
        }

        System.out.println("SIMULACIÓ ACABADA");
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

    public static int getcontNumEstudiantes() throws InterruptedException {
        mutex.acquire();
        int cont = contNumEstudiantes;
        mutex.release();
        return cont;
    }

}
