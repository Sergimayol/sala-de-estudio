package salaestudio;

public class Director implements Runnable {

    private SalaEstudio sala;

    public Director(SalaEstudio sala) {
        this.sala = sala;
    }

    @Override
    public void run() {
        System.out.println("Hola, soy el director");
        System.out.println(sala.getMensaje());
    }
    
}
