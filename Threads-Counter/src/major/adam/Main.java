package major.adam;

public class Main {

    public static void main(String[] args) {
        CountDown countDown = new CountDown();
        CountDown countDown2 = new CountDown();
        CountDown countDown3 = new CountDown();


        //race-condition is created here because countDown is passed to both threads
        //to prevent, create two instance and pass one to each thread (but not usually possible in real world)
        CountDownThread countDownThread1 = new CountDownThread(countDown);
        countDownThread1.setName("Thread 1");
        CountDownThread countDownThread2 = new CountDownThread(countDown);
        countDownThread2.setName("Thread 2");
        countDownThread1.start();
        countDownThread2.start();

        //every java object has an "intrinsic lock / monitor (primitive types don't have this lock)"
        //can synchronize a block of statements that work with an object by forcing the
        //block to acquire the object's lock before executing the statement block
        //have to synchronize on an object that the threads share, so they are competing for the same lock (i.e. not local variables)
        //can synchronize static objects and static methods (lock is owned by class of the instance)
        //re-entrant = thread can acquire a lock it already owns (when a synchronized method calls another synchronized method)
        //critical section = code that references a shared resource (only one thread at a time should be able to execute a critical section)
        //thread-safe = all of the critical sections of the code have been synchronized (basically have to synchronize methods that are not thread-safe our self)
        //only synchronize the code that MUST-BE synchronized (to prevent unnecessary thread blocking)


    }
}

class CountDown {
    private int i = 0;

    //synchronized
    public void doCountDown() {
        String color;
        switch (Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
                break;
        }

        synchronized (this) {
            for (i = 10; i > 0; i--) {
                System.out.println(color + Thread.currentThread().getName() + ": i = " + i);
            }
        }
    }
}

class CountDownThread extends Thread {
    private CountDown countDown;

    public CountDownThread(CountDown countDown) {
        this.countDown = countDown;
    }

    public void run() {
        countDown.doCountDown();
    }
}
