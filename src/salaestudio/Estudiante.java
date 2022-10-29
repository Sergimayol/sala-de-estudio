package salaestudio;

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
        System.out.println("Hola, soy " + this.id);
    }

}
