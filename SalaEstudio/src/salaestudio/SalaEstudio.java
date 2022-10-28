/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package salaestudio;

/**
 *
 * @author Alex
 */
import java.util.concurrent.Semaphore;

public class SalaEstudio implements Runnable {

    private static final int MAX = 15;
    private static final int director = 1;
    private static final int estudiantes = 5;
    private static final int MAX_COUNT = 1000;
    int id;
    static volatile int counter;
    static Semaphore mutex = new Semaphore(1);
    int ronda = 1;

    public SalaEstudio(int id) {
        this.id = id;
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("SIMULACIÓN DE LA SALA DE ESTUDIO");
        System.out.println("Número total de estudiantes: " + estudiantes);
        System.out.println("Número máximo de estudiantes: " + MAX);
        //Inicializamos los Threads
        Thread[] threads = new Thread[estudiantes];
        int i;
        for (i = 0; i < estudiantes; i++) {
            threads[i] = new Thread(new SalaEstudio(i));
            threads[i].start();
        }
        //Los estudiantes entran a la sala
        for (i = 0; i < estudiantes; i++) {
            threads[i].join();
        }
    }

    @Override
    public void run() {
        int max = MAX_COUNT / estudiantes;
        System.out.printf("Estudiante %d\n", id);
        for (int i = 0; i < max; i++) {
            try {
                mutex.acquire();
            } catch (InterruptedException e) {
            }
            counter += 1;
            mutex.release();
            
        }

    }

    //Método director, probablemente haya que hacer una clase 
    public void director() {
        System.out.println("El Sr. Director empieza la ronda");
        System.out.println("El director acaba la ronda " + ronda + " de 3");
        ronda++;
        if (ronda == 3) {
            
        }
    }
}
