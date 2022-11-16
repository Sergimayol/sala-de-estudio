package salaestudio;

import java.util.concurrent.Semaphore;

public class SalaEstudio {

    private Semaphore semaforo;
    private int numDirector;
    private int numEstudiantes;
    private String mensaje = "";

    public SalaEstudio(int numDirector, int numEstudiantes) {
        this.numDirector = numDirector;
        this.numEstudiantes = numEstudiantes;
        this.semaforo = new Semaphore(numDirector + numEstudiantes);
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

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

}
