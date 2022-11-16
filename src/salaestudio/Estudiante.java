package salaestudio;

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
        System.out.println("Hola, soy " + this.id);
        System.out.println(sala.getMensaje());
    }

}
