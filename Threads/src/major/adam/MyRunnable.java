package major.adam;

public class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println(ThreadColor.ANSI_CYAN + "Hello from MyRunnable");
    }
}
