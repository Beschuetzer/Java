package major.adam;

import static major.adam.ThreadColor.ANSI_CYAN;
import static major.adam.ThreadColor.ANSI_RED;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(ThreadColor.ANSI_PURPLE + "Hello from main thread");

        //Three ways to create a thread
        //1). To extend the thread class and override .run()
        Thread anotherThread = new AnotherThread();

        //can only start instance of a thread once and only once (can not start a thread instance that has already run)
        anotherThread.setName("== Another Thread ==");
        anotherThread.start();
//        anotherThread.start();

        //2). Use anonymous class (if only running code once)
        //have to start thread immediately

        //this is how to create an anonymous class
        new Thread() {
            public void run() {
                System.out.println(ANSI_RED + "Hello from anonymous thread");
            }
        }.start();

        System.out.println(ThreadColor.ANSI_PURPLE + "Hello again from main");

        //using Runnable interface (only have to implement one method)
        Thread myThread = new Thread(new MyRunnable());
        myThread.start();

        //anonymous class
        new Thread(new MyRunnable() {
            @Override
            public void run() {
                super.run();
                System.out.println("Anonymous myRunnable Thread started and waiting to join...");

                //joining a thread to another thread
                //useful when making api calls on one thread and then executing another thread
                //after that api call has finished
                //first thread waits for second thread to finish
                try {
                    //.join takes a timeout duration, after which it just resumes execution
                    anotherThread.join(1000);
                    System.out.println(ANSI_CYAN + ".join() finishing from anonymous class or timed out");
                } catch (InterruptedException exception) {
                    System.out.println(ANSI_CYAN + "I couldn't wait after all, I was interrupted.");
                    return;
                }

            }
        }).start();

        //.interrupt() is used to trigger the InterruptException and the interrupt() to be called
//        anotherThread.interrupt();


//        someThread.setPriority() = a recommendation to JVM as to thread execution order (ultimately decided by OS)
    }
}
