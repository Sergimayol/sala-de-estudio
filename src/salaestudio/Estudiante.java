package salaestudio;

import salaestudio.Director.Estado;

public class Estudiante implements Runnable {

    private final String id;
    private SalaEstudio sala;

    public Estudiante(String id, SalaEstudio sala) {
        this.id = id;
        this.sala = sala;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public void run() {
        // Si la sala esta bloqueada, el estudiante espera
        if (this.sala.getEstadoDirector() == Estado.DENTRO) {
            this.sala.blockSalaEstudio();
        }

        // Espera un tiempo aleatorio
        this.esperarTiempoAleatorio();

        // Sino entra en la sala
        sala.entrarEstudiante(this.id);

        // Si el número de estudiantes en la sala es menor que el máximo el estudiante
        // estudia
        if (sala.getNumEstudiantes() > sala.getMAX_ESTUDIANTES()) {
            System.out.println(id + ": FESTA!!!!!");
            if (this.sala.getEstadoDirector() != Estado.DENTRO) {
                // Desbloquear al director
                sala.salirDirector();
            }
        } else {
            // Sino el estudiante hace fiesta
            System.out.println(id + " estudia");
        }

        // Espera un tiempo aleatorio
        this.esperarTiempoAleatorio();

        // Sale de la sala
        sala.salirEstudiante(this.id);

        if (sala.getNumEstudiantes() == 0) {
            System.out.println(this.id + ": ADEU Senyor Director es queda sol");
            sala.salirDirector();
        }
    }

    private void esperarTiempoAleatorio() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
