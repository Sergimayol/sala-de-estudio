import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    private static final int TIEMPO_DE_ESPERA = 1000;
    private static final int numDirectores = 1;
    private static final Semaphore semaforoDirector = new Semaphore(numDirectores);
    private static final Semaphore mutexNumEstudiantesEsCero = new Semaphore(1);
    private static final Semaphore mutexNumEstudiantesEsMayorAMax = new Semaphore(0);
    private static final Semaphore mutexContador = new Semaphore(1);
    private static int contadorEstudiantes = 0;
    private static int numEstudiantes = 0;
    private static int numMaxEstudiantesEnSalaEstudio;
    private static int numThreads;

    public static void main(String[] args) {
        // Mensaje inicial de la simulación
        mensaje_inicio();
        // Inicio simulación
        try {
            inicio_simulacion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void inicio_simulacion() throws InterruptedException {
        Thread[] threads = new Thread[numThreads];
        int i = 0;

        threads[i] = new Thread(new Director());
        for (i = 1; i < numEstudiantes + 1; i++) {
            String nombre = NombresEstudiantes.getNombre(i - 1);
            threads[i] = new Thread(new Estudiante(nombre));
        }
        for (i = 0; i < numThreads; i++) {
            threads[i].start();
        }

        for (i = 0; i < numThreads; i++) {
            threads[i].join();
        }

        System.out.println("SIMULACIÓ ACABADA");
    }

    private static void esperarTiempoAleatorio() {
        try {
            Thread.sleep((long) (Math.random() * TIEMPO_DE_ESPERA));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        numMaxEstudiantesEnSalaEstudio = sc.nextInt();
        System.out.print("\n");
        numThreads = numDirectores + numEstudiantes;

        sc.close();
    }

    private static void bloquearContadorEstudiantes() {
        try {
            mutexContador.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void desbloquearContadorEstudiantes() {
        mutexContador.release();
    }


    private static void contadorEstudiantesEsCero() {
        try {
            desbloquearDirector(0);
            mutexNumEstudiantesEsCero.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void contadorEstudiantesNoEsCero() {
        if (Director.estadoDirector != EstadoDirector.ESPERANDO) {
            bloquearDirector();
        }
        mutexNumEstudiantesEsCero.release();
    }


    private static void contadorEstudiantesEsMayorAlMaximo() {
        try {
            desbloquearDirector(1);
            mutexNumEstudiantesEsMayorAMax.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void contadorEstudiantesEsMenorAlMaximo() {
        mutexNumEstudiantesEsMayorAMax.release();
        if (Director.estadoDirector != EstadoDirector.ESPERANDO) {
            bloquearDirector();
        }
    }

    private static void bloquearDirector() {
        try {
            if (Director.estadoDirector != EstadoDirector.FUERA) {
                System.out.println("El Director està esperant per entrar. No molesta als que estudien");
            }
            Director.estadoDirector = EstadoDirector.ESPERANDO;
            semaforoDirector.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void desbloquearDirector(int estado) {
        switch (estado) {
            case 0:
                System.out.println("\tEl Director veu que no hi ha ningú a la sala d'estudis");
                Director.estadoDirector = EstadoDirector.FUERA;
                break;
            case 1:
                System.out.println("\tEl Director està dins la sala d'estudi: S'HA ACABAT LA FESTA!");
                Director.estadoDirector = EstadoDirector.DENTRO;
                break;
        }
        semaforoDirector.release();
    }

    private enum EstadoDirector {
        DENTRO,
        FUERA,
        ESPERANDO
    }

    private static class Estudiante implements Runnable {
        String id;

        public Estudiante(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            esperarTiempoAleatorio();
            while (Director.estadoDirector == EstadoDirector.DENTRO) {
                // Esperar
            }
            bloquearContadorEstudiantes();
            contadorEstudiantes++;
            System.out.println(this.id + " entra a la sala d'estudi, nombre estudiants: " + contadorEstudiantes);
            if (contadorEstudiantes >= numMaxEstudiantesEnSalaEstudio) {
                System.out.println(id + ": FESTA!!!!!");
                contadorEstudiantesNoEsCero();
                desbloquearContadorEstudiantes();
                contadorEstudiantesEsMayorAlMaximo();
            } else {
                System.out.println(this.id + " estudia");
                contadorEstudiantesNoEsCero();
                desbloquearContadorEstudiantes();
                contadorEstudiantesEsMenorAlMaximo();
            }
            esperarTiempoAleatorio();
            bloquearContadorEstudiantes();
            contadorEstudiantes--;
            System.out.println(this.id + " surt de la sala d'estudi, nombre estudiants: " + contadorEstudiantes);
            if (contadorEstudiantes == 0) {
                contadorEstudiantesEsCero();
                System.out.println(this.id + ": ADEU Senyor Director es queda sol");
            }
            desbloquearContadorEstudiantes();
        }

    }

    private static class Director implements Runnable {
        static EstadoDirector estadoDirector;
        private final int MAX_RONDAS = 3;

        @Override
        public void run() {
            for (int ronda = 1; ronda <= MAX_RONDAS; ronda++) {
                // Se espera un tiempo aleatorio para empezar una ronda
                esperarTiempoAleatorio();
                System.out.println("El Sr. Director comença la ronda");
                estadoDirector = EstadoDirector.FUERA;
                contadorEstudiantesNoEsCero();
                bloquearDirector();
                System.out.println("\tEl Director acaba la ronda " + ronda + " de " + MAX_RONDAS);
            }
        }

    }
}