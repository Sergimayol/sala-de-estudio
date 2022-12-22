public class Director implements Runnable {

    @Override
    public void run() {
        int ronda = 1;
        int NUM_RONDAS = 3;
        while (ronda <= NUM_RONDAS) {
            try {
                Thread.sleep((long) (Math.random() * 1000));

                System.out.println("\tEl sr. Director comença la ronda " + ronda);
                Main.estadoDirector = Main.EstadoDirector.FUERA;
                System.out.println("\tEl director entra en la sala");
                Main.estadoDirector = Main.EstadoDirector.DENTRO;
                Main.entradaSalaEstudio.acquire(Main.numEstudiantes);

                Thread.sleep((long) (Math.random() * 5000));

                System.out.println("\tEl director sale de la sala");
                Main.estadoDirector = Main.EstadoDirector.FUERA;
                Main.entradaSalaEstudio.release(Main.numEstudiantes);


                /*Main.mutexContador.acquire();
                if (Main.contadorEstudiantes == 0) {
                    System.out.println("El Director veu que no hi ha ningú a la sala d'estudis");
                    Main.mutexContador.release();
                } else {
                    if (Main.contadorEstudiantes < Main.numMaxEstudiantesEnSalaEstudio) {
                        System.out.println("\tEl Director està esperant per entrar. No molesta als que estudien");
                        Main.mutexContador.release();
                    } else {
                        Main.estadoDirector = Main.EstadoDirector.DENTRO;
                        System.out.println("\tEl Director està dins la sala d'estudi: S'HA ACABAT LA FESTA!");
                        Main.mutexContador.release();
                    }
                    Main.rondaDirector.acquire(); // Se bloquea
                }*/

                System.out.println("\tEl Director acaba la ronda " + ronda + " de " + NUM_RONDAS);
                Main.estadoDirector = Main.EstadoDirector.FUERA;
                ronda++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
