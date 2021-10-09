package major.adam;

import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<String>(6);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        MyProducer myProducer = new MyProducer(buffer, ThreadColor.ANSI_BLUE);
        MyConsumer myConsumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE);
        MyConsumer myConsumer2 = new MyConsumer(buffer, ThreadColor.ANSI_GREEN);

        executorService.execute(myProducer);
        executorService.execute(myConsumer1);
        executorService.execute(myConsumer2);


        //region using the more basic Future<T> interface
        Future<String> future = executorService.submit(() -> {
            System.out.println(ThreadColor.ANSI_GREEN + "I'm being printed from the callable class");
            Thread.sleep(1000);
            return "This is the result";
        });

        try {
            System.out.println(future.get());
        } catch (ExecutionException e) {
            System.out.println("Something went wrong");
        } catch (InterruptedException exception) {
            System.out.println("Thread running task was interrupted");
        }
        //endregion

        //region using the extended FutureTask<T> interface
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(ThreadColor.ANSI_GREEN + "I'm being printed from the FutureTask section...");
            Thread.sleep(1000);
            return "This is the result of FutureTask";
        });
        executorService.submit(futureTask);
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //endregion

        executorService.shutdown();
    }
}

class MyProducer implements Runnable {
    private final ArrayBlockingQueue<String> buffer;
    private final String color;


    public MyProducer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1", "2", "3", "4", "5",};

        for (String num : nums) {
            try {
                System.out.println(color + "Adding..." + num);
                buffer.put(num);
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException exception) {
                System.out.println("Producer was interrupted");
            }
        }

        System.out.println(color + "Adding " + Main.EOF + " and exiting...");

        try {
            buffer.put(Main.EOF);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyConsumer implements Runnable {
    private final ArrayBlockingQueue<String> buffer;
    private final String color;

    public MyConsumer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        int counter = 0;
        while (true) {
            synchronized (buffer) {
                try {
                    String nextElementInBuffer = buffer.peek();
                    if (buffer.isEmpty()) continue;
                    if (nextElementInBuffer != null && nextElementInBuffer.equals(Main.EOF)) {
                        System.out.println(color + "Exiting.......");
                        break;
                    }
                    System.out.println(color + "Removed " + buffer.take());
                    System.out.println("counter = " + counter);
                    counter = 0;
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
            counter++;
        }
    }
}
