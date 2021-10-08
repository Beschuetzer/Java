package major.adam;

import static major.adam.ThreadColor.ANSI_BLUE;

public class AnotherThread extends Thread{
    @Override
    public void run() {
        System.out.println(ANSI_BLUE + "Hello from " + currentThread().getName());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException exception) {
            System.out.println(ANSI_BLUE + "A different thread woke me up");

            //returning here causes the thread to terminate when it is interrupted
            return;
        }

        System.out.println("Three seconds have passed and I'm awake.");
    }

//    //interrupt gets called
//    @Override
//    public void interrupt() {
//        System.out.println(currentThread().getName() + " interrupted " + this.getName());
//        return;
//    }



}
