package major.adam;

public class Main {
    private static Object lock = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Worker(ThreadColor.ANSI_PURPLE), "Priority 1");
        Thread t2 = new Thread(new Worker(ThreadColor.ANSI_BLUE), "Priority 2");
        Thread t3 = new Thread(new Worker(ThreadColor.ANSI_GREEN), "Priority 3");
        Thread t4 = new Thread(new Worker(ThreadColor.ANSI_RED), "Priority 4");
        Thread t5 = new Thread(new Worker(ThreadColor.ANSI_CYAN), "Priority 5");

        //priority is more of a suggestion (but possibly less so for more computationally-complex tasks)
//        t1.setPriority(1);
//        t2.setPriority(2);
//        t3.setPriority(3);
//        t4.setPriority(4);
//        t5.setPriority(5);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }

    private static class Worker implements Runnable {
        private int runCount = 0;
        private String threadColor;

        public Worker(String threadColor) {
            this.threadColor = threadColor;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                synchronized (lock) {
                    runCount++;
                    System.out.printf(threadColor + "%s: runCount = %d\n", Thread.currentThread().getName(), runCount);
                    //execute critical section of code
                }
            }
        }
    }
}
