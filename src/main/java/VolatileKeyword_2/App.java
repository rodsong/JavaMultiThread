package VolatileKeyword_2;

/**
 * Volatile Keyword, “… the volatile modifier guarantees that any thread that
 * reads a field will see the most recently written value.” - Josh Bloch
 *
 * Codes with minor comments are from http://www.caveofprogramming.com/youtube/
 * also freely available at
 * https://www.udemy.com/java-multithreading/?couponCode=FREE
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
import java.util.Scanner;

class Processor extends Thread {

    private  volatile boolean running;

    public void run() {
        while (!running) {
            System.out.println("Running"+Thread.currentThread().getName());

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        running = true;
    }
}

public class App {

    public static void main(String[] args) {
        Processor pro = new Processor();
        Processor pro2 = new Processor();

        pro2.start();
        pro.start();

        // Wait for the enter key
        System.out.println("Enter something to stop the thread,\nVolatile variable running will be forced to true :");
        new Scanner(System.in).nextLine();
        System.out.println("停止");
//        pro2.shutdown();
//        pro.shutdown();
    }
}
