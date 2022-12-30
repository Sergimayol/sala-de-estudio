import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    static final int numDirectores = 1;
    static volatile int contadorEstudiantes = 0;
    static int numEstudiantes = 0;
    static int numMaxEstudiantesEnSalaEstudio = 0;
    static Semaphore mutexContador = new Semaphore(1);
    //static Semaphore directorDentro = new Semaphore(0);
    //static Semaphore numEstudiantesEsMayorAMax = new Semaphore(0);
    static Semaphore directorEsperando = new Semaphore(0);
    static Semaphore entradaSalaAbierta = new Semaphore(1);

    static EstadoDirector estadoDirector = EstadoDirector.FUERA;

    public enum EstadoDirector {
        DENTRO,
        FUERA,
        ESPERANDO
    }

    public static void main(String[] args) throws InterruptedException {

        // Mensaje inicial de la simulación
        mensajeInicio();

        // Inicio simulación
        Thread dir = new Thread(new Director());
        dir.start();

        Thread[] threads = new Thread[numEstudiantes];
        int i;
        for (i = 0; i < numEstudiantes; i++) {
            threads[i] = new Thread(new Estudiante(NombresEstudiantes.nombres[i]));
            threads[i].start();
        }

        dir.join();
        for (i = 0; i < numEstudiantes; i++) {
            threads[i].join();
        }

        System.out.println("SIMULACIÓ ACABADA");
    }

    /**
     * Método que muestra el mensaje de inicio de la simulación y pide el número
     * total de estudiantes y el máximo de estudiantes que pueden estar en la sala a
     * la vez.
     */
    private static void mensajeInicio() {
        Scanner sc = new Scanner(System.in);

        System.out.println("SIMULACIÓN DE SALA DE ESTUDIO");
        System.out.print("Número de total estudiantes: ");
        numEstudiantes = sc.nextInt();
        System.out.print("\nNúmero máximo de estudiantes: ");
        numMaxEstudiantesEnSalaEstudio = sc.nextInt();
        System.out.print("\n");

        sc.close();
    }

}