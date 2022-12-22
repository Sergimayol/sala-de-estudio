public class Estudiante implements Runnable {
    private final String id;

    public Estudiante(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {

            Thread.sleep((long) (Math.random() * 2000));
            Main.entradaSalaEstudio.acquire(1);
            Main.mutexContador.acquire();
            Main.contadorEstudiantes++;
            System.out.printf("%s entra hay %d\n", this.id, Main.contadorEstudiantes);
            Main.mutexContador.release();

            Thread.sleep((long) (Math.random() * 1000));

            Main.mutexContador.acquire();
            Main.contadorEstudiantes--;
            System.out.printf("%s sale %d\n", this.id, Main.contadorEstudiantes);
            if (Main.contadorEstudiantes == 0) {
                System.out.printf("%s soy el úlitmo\n", this.id);
            }
            Main.mutexContador.release();
            Main.entradaSalaEstudio.release();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}