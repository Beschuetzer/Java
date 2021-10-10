package major.adam;


//the deadlock occurs because Thread1 and Thread2 try to obtain the locks in a different order
public class Main {

    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();

    }
}

class Thread1 extends Thread {
    public void run() {
        synchronized (Main.lock1) {
            System.out.println("Thread1: Has lock1");
            try {
                Thread.sleep(100);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

            System.out.println("Thread1: Waiting for lock2");
            synchronized (Main.lock2) {
                System.out.println("Thread 1: Has lock1 and lock2");
            }
            System.out.println("Thread 1: Released lock2");
        }
        System.out.println("Thread 1: Released lock1.  Exiting...");
    }
}

class Thread2 extends Thread {
    @Override
    public void run() {
        synchronized (Main.lock2) {
            System.out.println("Thread2: Has lock2");
            try {
                Thread.sleep(100);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

            System.out.println("Thread2: Waiting for lock1");
            synchronized (Main.lock1) {
                System.out.println("Thread 2: Has lock1 and lock2");
            }
            System.out.println("Thread 2: Released lock1");
        }
        System.out.println("Thread 2: Released lock2.  Exiting...");
    }
}