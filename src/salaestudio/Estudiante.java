package salaestudio;

import salaestudio.Director.Estado;

public class Estudiante implements Runnable {

    private final String id;

    public Estudiante(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public void run() {
        // Si la sala esta bloqueada, el estudiante espera
        // ?? Como mirar si esta el aula bloqueeda para que no entre otro estudiante

        // Sino entra en la sala
        this.entarAulaEstudio();

        try {
            Main.mutex.acquire();
            // Si el número de estudiantes en la sala es mayor que el máximo el estudiante
            // fiesta
            if (Main.contNumEstudiantes > Main.maxEstudiantes) {
                System.out.println(id + ": FESTA!!!!!");
                Main.semDirectores.release();
            } else {
                // Sino el estudiante estudia
                System.out.println(id + " estudia");
            }
            Main.mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Sale de la sala
        this.salirAulaEstudio();

        try {
            if (Main.getcontNumEstudiantes() == 0) {
                System.out.println(this.id + ": ADEU Senyor Director es queda sol");
                // Desbloquear al director
                Main.semDirectores.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void esperarTiempoAleatorio() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void entarAulaEstudio() {
        try {
            this.esperarTiempoAleatorio();
            this.incrementarNumEstudiantes();
            Main.semEstudiantes.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void salirAulaEstudio() {
        this.esperarTiempoAleatorio();
        this.decrementarNumEstudiantes();
        Main.semEstudiantes.release();
    }

    private void incrementarNumEstudiantes() {
        try {
            Main.mutex.acquire();
            Main.contNumEstudiantes++;
            Main.mutex.release();
            System.out.println(
                    id + " entra a la sala d'estudi, nombre estudiants: " + Main.getcontNumEstudiantes());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void decrementarNumEstudiantes() {
        try {
            Main.mutex.acquire();
            Main.contNumEstudiantes--;
            System.out
                    .println(this.id + " surt de la sala d'estudi, nombre estudiants: " + Main.getcontNumEstudiantes());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Main.mutex.release();
        }
    }

}
