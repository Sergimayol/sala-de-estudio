public class Director implements Runnable {

    @Override
    public void run() {
        int ronda = 1;
        int NUM_RONDAS = 3;
        while (ronda <= NUM_RONDAS) {
            try {
                Thread.sleep((long) (Math.random() * 1000));
                Main.mutexContador.acquire();
                Main.estadoDirector = Main.EstadoDirector.FUERA;
                System.out.println("\tEl Sr. Director comença la ronda");
                if (Main.contadorEstudiantes == 0) {
                    System.out.println("El Director veu que no hi ha ningú a la sala d'estudis");
                    Main.mutexContador.release();
                } else {
                    if (Main.contadorEstudiantes < Main.numMaxEstudiantesEnSalaEstudio) {
                        System.out.println("\tEl Director està esperant per entrar. No molesta als que estudien");
                        Main.estadoDirector = Main.EstadoDirector.ESPERANDO;
                        Main.mutexContador.release();
                        Main.directorEsperando.acquire();
                    } else {
                        Main.estadoDirector = Main.EstadoDirector.DENTRO;
                        System.out.println("\tEl Director està dins la sala d'estudi: S'HA ACABAT LA FESTA!");
                        Main.mutexContador.release();
                        Main.entradaSalaAbierta.acquire();
                        Main.directorEsperando.acquire();
                    }
                }
                Main.mutexContador.acquire();
                Main.estadoDirector = Main.EstadoDirector.FUERA;
                System.out.println("\tEl Director acaba la ronda " + ronda + " de " + NUM_RONDAS);
                Main.entradaSalaAbierta.release();
                Main.mutexContador.release();
                ronda++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
