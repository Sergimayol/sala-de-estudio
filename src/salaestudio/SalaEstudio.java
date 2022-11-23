package salaestudio;

import java.util.concurrent.Semaphore;

public class SalaEstudio {

    private Semaphore semaforo;
    private int numDirector;
    private int numEstudiantes;
    private final int MAX_ESTUDIANTES;
    private String mensaje = "";

    public SalaEstudio(int numDirector, int numEstudiantes) {
        this.numDirector = numDirector;
        this.numEstudiantes = 0;
        this.semaforo = new Semaphore(numDirector + numEstudiantes);
        this.MAX_ESTUDIANTES = numEstudiantes;
    }

    public void entrarDirector() {
        try {
            semaforo.acquire(numDirector);
        } catch (InterruptedException ex) {
            System.out.println("Error al entrar el director");
        }
    }

    public void salirDirector() {
        semaforo.release(numDirector);
    }

    public void entrarEstudiante(String id) {
        try {
            this.numEstudiantes++;
            System.out.println(
                    id + "entra a la sala d'estudi, nombre estudiants: " + numEstudiantes + "\n" + id + " estudia");
            semaforo.acquire();
        } catch (InterruptedException ex) {
            System.out.println("Error al entrar el estudiante");
        }
    }

    public void salirEstudiante(String id) {
        this.numEstudiantes--;
        System.out.println(id + " surt de la sala d'estudi, nombre estudiants: " + numEstudiantes);
        semaforo.release();
    }

    public int getMAX_ESTUDIANTES() {
        return MAX_ESTUDIANTES;
    }

    public int getNumDirector() {
        return numDirector;
    }

    public void setNumDirector(int numDirector) {
        this.numDirector = numDirector;
    }

    public int getNumEstudiantes() {
        return numEstudiantes;
    }

    public void setNumEstudiantes(int numEstudiantes) {
        this.numEstudiantes = numEstudiantes;
    }

    public Semaphore getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    @Override
    public String toString() {
        return "SalaEstudio{" + "semaforo=" + semaforo + ", numDirector=" + numDirector + ", numEstudiantes="
                + numEstudiantes + '}';
    }

    public void entrarSala(String id) {
        try {
            semaforo.acquire();
            mensaje = id + " entra en la sala";
            System.out.println(mensaje);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void salirSala(String id) {
        semaforo.release();
        mensaje = id + " sale de la sala";
        System.out.println(mensaje);
    }

    public void block() {
        try {
            this.semaforo.acquire();
        } catch (InterruptedException ex) {
            System.out.println("Error al bloquear el semáforo");
        }
    }

    public void unblock() {
        this.semaforo.release();
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

}
