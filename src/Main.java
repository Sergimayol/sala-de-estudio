import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    private static final int TIEMPO_DE_ESPERA = 1000;
    private static final int numDirectores = 1;
    private static final Semaphore mutex = new Semaphore(1);
    private static int contadorEstudiantes = 0;
    private static int numEstudiantes = 0;
    private static int numMaxEstudiantesEnSalaEstudio;
    private static int numThreads;
    private static Semaphore semaforoDirector;
    private static Semaphore semaforoEstudiantes;

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
        semaforoEstudiantes = new Semaphore(numMaxEstudiantesEnSalaEstudio);
        semaforoDirector = new Semaphore(numDirectores);

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

    private static void bloquearMutex() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private static void desbloquearMutex() {
        mutex.release();
    }

    private static void bloquearDirector() {
        try {
            semaforoDirector.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private static void desbloquearDirector() {
        semaforoDirector.release();
    }

    private static void bloquearSalaDeEstudio() {
        try {
            semaforoEstudiantes.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private static void desbloquearSalaDeEstudio() {
        semaforoEstudiantes.release();
    }

    private enum EstadoDirector {
        FUERA,
        ESPERANDO,
        DENTRO
    }

    private static class Estudiante implements Runnable {
        String id;

        public Estudiante(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            esperarTiempoAleatorio();
            entrarSalaDeEstudio();
            if (contadorEstudiantes >= numMaxEstudiantesEnSalaEstudio) {
                System.out.println(id + ": FESTA!!!!!");
                desbloquearDirector();
            } else {
                System.out.println(id + " estudia");
            }
            desbloquearMutex();

            esperarTiempoAleatorio();
            salirSalaDeEstudio();
        }

        private void salirSalaDeEstudio() {
            bloquearMutex();
            contadorEstudiantes--;
            System.out.println(this.id + " surt de la sala d'estudi, nombre estudiants: " + contadorEstudiantes);
            if (contadorEstudiantes == 0) {
                System.out.println(this.id + ": ADEU Senyor Director es queda sol");
                desbloquearDirector();
            }
            desbloquearMutex();
        }

        private void entrarSalaDeEstudio() {
            while (Director.estadoDirector == EstadoDirector.DENTRO) {
                // Esperar
            }
            bloquearMutex();
            contadorEstudiantes++;
            System.out.println(this.id + " entra a la sala d'estudi, nombre estudiants: " + contadorEstudiantes);
        }
    }

    private static class Director implements Runnable {

        public static EstadoDirector estadoDirector;
        private final int MAX_RONDAS = 3;
        private boolean esMutexDesbloqueado = false;

        @Override
        public void run() {
            for (int ronda = 1; ronda <= MAX_RONDAS; ronda++) {
                this.esMutexDesbloqueado = false;
                estadoDirector = EstadoDirector.FUERA;
                // Se espera un tiempo aleatorio para empezar una ronda
                esperarTiempoAleatorio();
                System.out.println("El Sr. Director comença la ronda");
                // Se bloquea el contador de numero de estudiantes
                bloquearMutex();
                if (contadorEstudiantes == 0) {
                    System.out.println("\tEl Director veu que no hi ha ningú a la sala d'estudis");
                    esperarTiempoAleatorio();
                    System.out.println("\tEl Director acaba la ronda " + ronda + " de " + MAX_RONDAS);
                    desbloquearMutex();
                    continue;
                }

                if (contadorEstudiantes < numMaxEstudiantesEnSalaEstudio) {
                    System.out.println("El Director està esperant per entrar. No molesta als que estudien");
                    estadoDirector = EstadoDirector.ESPERANDO;
                    desbloquearMutex();
                    this.esMutexDesbloqueado = true;
                    bloquearDirector();
                }

                if (this.esMutexDesbloqueado) {
                    bloquearMutex();
                }

                if (contadorEstudiantes >= numMaxEstudiantesEnSalaEstudio) {
                    System.out.println("\tEl Director està dins la sala d'estudi: S'HA ACABAT LA FESTA!");
                    estadoDirector = EstadoDirector.DENTRO;
                    bloquearDirector();
                    desbloquearMutex();
                }

                System.out.println("\tEl Director acaba la ronda " + ronda + " de " + MAX_RONDAS);
            }
        }

    }
}