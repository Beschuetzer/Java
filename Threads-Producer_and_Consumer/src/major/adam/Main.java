package major.adam;

import java.util.Collections;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Message message = new Message();
        (new Thread(new Writer(message))).start();
        new Thread(new Reader(message)).start();

    }
}

class Message {
    private String message;
    private boolean empty = true;

    public synchronized String read() {
        while (empty) {
            System.out.println("reading...");
            try {
                wait();
            } catch (InterruptedException exception) {

            }
        }

        empty = true;
        notifyAll();
        return message;
    }

    public synchronized void write(String message) {
        while (!empty) {
            System.out.println("writing...");
            try {
                wait();
            } catch (InterruptedException exception) {

            }
        }

        empty = false;
        notifyAll();
        this.message = message;

        Collections.synchronizedList()
    }
}

class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        String[] messages = {
                "Humpty Dumpty sat on a wall",
                "Humpty Dumpty had a great fall",
                "All the king's horses and all the king's men",
                "Couldn't put humpty together again"
        };

        Random random = new Random();

        for (int i = 0; i < messages.length; i++) {
            message.write(messages[i]);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException exception) {

            }
        }

        message.write("finished");
    }
}

class Reader implements Runnable {
    private Message message;

    public Reader(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (String lastestMsg = message.read(); !lastestMsg.equals("finished");) {
            lastestMsg = message.read();

            System.out.println(lastestMsg);

            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException exception) {

            }
        }
    }
}