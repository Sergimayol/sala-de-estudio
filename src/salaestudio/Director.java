package salaestudio;

public class Director implements Runnable {

    private int ronda = 1;
    private Estado estadoDirector;
    private final int NUM_RONDAS = 3;

    public enum Estado {
        FUERA,
        ESPERANDO,
        DENTRO
    };

    public Director() {
        this.estadoDirector = Estado.FUERA;
    }

    @Override
    public void run() {
        // El director realiza 3 rondas
        while (ronda <= NUM_RONDAS) {
            try {
                this.esperarTiempoAleatorio();
                System.out.println("\tEl Sr. Director comença la ronda");
                // Si no hay nadie en la sala, la ronda se acaba
                if (Main.getcontNumEstudiantes() == 0) {
                    System.out.println("\tEl Director veu que no hi ha ningú a la sala d'estudis");
                } else {
                    // Si el número de estudiantes en la sala es menor que el máximo
                    // el director se queda esperando
                    if (Main.getcontNumEstudiantes() < Main.maxEstudiantes) {
                        System.out.println("\tEl Director està esperant per entrar. No molesta als que estudien");
                        estadoDirector = Estado.ESPERANDO;
                        // block director thread with a semaphore
                        try {
                            Main.semDirectores.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Bloquear la sala de estudio
                        // ?? Como bloquear para que no entre otro estudiante

                        // Si el número de estudiantes en la sala es igual al máximo
                        // el director entra en la sala
                        System.out.println("\tEl Director està dins la sala d'estudi: S'HA ACABAT LA FESTA!");
                        estadoDirector = Estado.DENTRO;
                        // El director espera hasta que todos los estudiantes salgan de la sala
                        try {
                            Main.semDirectores.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Desbloquear la sala de estudio cuando todos los estudiantes hayan salido
                        // ?? Como desbloquear para que entre otro estudiante
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\tEl Director acaba la ronda " + ronda + " de " + NUM_RONDAS);
            ronda++;
        }

    }

    private void esperarTiempoAleatorio() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Estado getEstadoDirector() {
        return estadoDirector;
    }

    public void setEstadoDirector(Estado estadoDirector) {
        this.estadoDirector = estadoDirector;
    }

}
