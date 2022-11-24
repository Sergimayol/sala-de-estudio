package salaestudio;

import java.util.concurrent.Semaphore;

public class SalaEstudio {

    private Semaphore semaforoSalaEstudio;
    private Semaphore semaforoDirector;
    private int numDirector;
    private int numEstudiantes;
    private final int MAX_ESTUDIANTES;
    private String mensaje = "";

    public SalaEstudio(int numDirector, int numEstudiantes) {
        this.numDirector = numDirector;
        this.numEstudiantes = 0;
        this.semaforoSalaEstudio = new Semaphore(1);
        this.semaforoDirector = new Semaphore(0);
        this.MAX_ESTUDIANTES = numEstudiantes;
    }

    public void entrarDirector() {
        try {
            this.semaforoDirector.acquire();
        } catch (InterruptedException ex) {
            System.out.println("Error al entrar el director");
        }
    }

    public void salirDirector() {
        this.semaforoDirector.release();
    }

    public void entrarEstudiante(String id) {
        this.numEstudiantes++;
        System.out.println(
                id + " entra a la sala d'estudi, nombre estudiants: " + numEstudiantes);
    }

    public void salirEstudiante(String id) {
        this.numEstudiantes--;
        System.out.println(id + " surt de la sala d'estudi, nombre estudiants: " + numEstudiantes);
    }

    public void blockSalaEstudio() {
        try {
            this.semaforoSalaEstudio.acquire();
        } catch (InterruptedException ex) {
            System.out.println("Error al bloquear la sala");
        }
    }

    public void unblockSalaEstudio() {
        this.semaforoSalaEstudio.release();
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

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

}
