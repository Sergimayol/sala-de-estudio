public class Estudiante implements Runnable {
    private final String id;

    public Estudiante(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
            Main.entradaSalaAbierta.acquire();
            Main.mutexContador.acquire();
            Main.contadorEstudiantes++;
            System.out.printf("%s entra a la sala d'estudi, nombre estudiants: %d\n", this.id, Main.contadorEstudiantes);
            Main.entradaSalaAbierta.release();
            if (Main.contadorEstudiantes >= Main.numMaxEstudiantesEnSalaEstudio) {
                System.out.printf("%s: FESTA!!!!!\n", this.id);
                if (Main.estadoDirector == Main.EstadoDirector.ESPERANDO) {
                    Main.directorEsperando.release();
                }
            } else {
                System.out.printf("%s estudia\n", this.id);
            }
            Main.mutexContador.release();
            Thread.sleep((long) (Math.random() * 2000));
            Main.mutexContador.acquire();
            Main.contadorEstudiantes--;
            System.out.printf("%s surt de la sala d'estudi, nombre estudiants: %d\n", this.id, Main.contadorEstudiantes);
            if (Main.contadorEstudiantes == 0 && Main.estadoDirector != Main.EstadoDirector.FUERA) {
                if (Main.estadoDirector == Main.EstadoDirector.ESPERANDO) {
                    System.out.printf("%s: ADEU Senyor Director, pot entrar si vol, no hi ha ningú\n", this.id);
                }
                Main.directorEsperando.release();
            }
            Main.mutexContador.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}