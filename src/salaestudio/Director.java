package salaestudio;

public class Director implements Runnable {

    private SalaEstudio sala;
    private int ronda = 1;
    private Estado estadoDirector;
    private final int NUM_RONDAS = 3;

    private enum Estado {
        FUERA,
        ESPERANDO,
        DENTRO
    };

    public Director(SalaEstudio sala) {
        this.sala = sala;
        this.estadoDirector = Estado.FUERA;
    }

    @Override
    public void run() {
        // El director realiza 3 rondas
        while (ronda <= NUM_RONDAS) {
            sala.setMensaje("\tEl Sr. Director comença la ronda");
            // Si no hay nadie en la sala, la ronda se acaba
            if (sala.getNumEstudiantes() == 0) {
                sala.setMensaje("\tEl Director veu que no hi ha ningú a la sala d'estudis");
            } else {
                // Si el número de estudiantes en la sala es menor que el máximo
                // el director se queda esperando
                if (sala.getNumEstudiantes() < sala.getMAX_ESTUDIANTES()) {
                    sala.setMensaje("\tEl Director està esperant per entrar. No molesta als que estudien");
                    estadoDirector = Estado.ESPERANDO;
                    // block director thread with a semaphore
                    sala.entrarDirector();
                } else {
                    // Si el número de estudiantes en la sala es igual al máximo
                    // el director entra en la sala
                    sala.setMensaje("\tEl Director està dins la sala d'estudi: S'HA ACABAT LA FESTA!");
                    estadoDirector = Estado.DENTRO;
                    // El director espera hasta que todos los estudiantes salgan de la sala
                    sala.entrarDirector();
                }
            }
            sala.setMensaje("\tEl Director acaba la ronda " + ronda + " de " + NUM_RONDAS);
            ronda++;
        }
    }

    public Estado getEstadoDirector() {
        return estadoDirector;
    }

    public void setEstadoDirector(Estado estadoDirector) {
        this.estadoDirector = estadoDirector;
    }

}
