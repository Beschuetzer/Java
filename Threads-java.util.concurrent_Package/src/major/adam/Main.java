package major.adam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        ReentrantLock bufferLock = new ReentrantLock();
        MyProducer myProducer = new MyProducer(buffer, ThreadColor.ANSI_BLUE, bufferLock);
        MyConsumer myConsumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE, bufferLock);
        MyConsumer myConsumer2 = new MyConsumer(buffer, ThreadColor.ANSI_GREEN, bufferLock);

        new Thread(myProducer).start();
        new Thread(myConsumer1).start();
        new Thread(myConsumer2).start();
    }
}

class MyProducer implements Runnable {
    private final List<String> buffer;
    private final String color;
    private final ReentrantLock bufferLock;


    public MyProducer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5",};

        for (String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                bufferLock.lock();
                try {
                    buffer.add(num);
                } finally {
                    bufferLock.unlock();
                }
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException exception) {
                System.out.println("Producer was interrupted");
            }
        }

        System.out.println(color + "Adding " + Main.EOF + " and exiting...");

        bufferLock.lock();
        try {
            buffer.add(Main.EOF);
        } finally {
            bufferLock.unlock();
        }
    }
}

class MyConsumer implements Runnable {
    private final List<String> buffer;
    private final String color;
    private final ReentrantLock bufferLock;

    public MyConsumer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        int counter = 0;
        while (true) {
                if (bufferLock.tryLock()) {
                    try {
                        if (buffer.isEmpty()) continue;
                        if (buffer.get(0).equals(Main.EOF)) {
                            System.out.println(color + "Exiting.......");
                            break;
                        }
                        System.out.println(color + "Removed " + buffer.remove(0));
                        System.out.println("counter = " + counter);
                        counter = 0;
                    } finally {
                        bufferLock.unlock();
                    }
                }
            counter++;

//            bufferLock.lock();
//            try {
//                if (buffer.isEmpty()) continue;
//                if (buffer.get(0).equals(Main.EOF)) {
//                    System.out.println(color + "Exiting.......");
//                    break;
//                }
//                System.out.println(color + "Removed " + buffer.remove(0));
//            } finally {
//                bufferLock.unlock();
//            }
        }
    }
}
