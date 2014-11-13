package WaitAndNotify_8;

public class NotifyTest {
    private String flag[] = { "true" };

    class NotifyThread extends Thread {
        public NotifyThread(String name) {
            super(name);
        }

        public void run() {
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (flag) {
                flag[0] = "false";
                flag.notify();
            }
            System.out.println(getName() + "OK!");
        }
    };

    class WaitThread extends Thread {
        public WaitThread(String name) {
            super(name);
        }

        public void run() {
            synchronized (flag) {
                while (flag[0] != "false") {
                    System.out.println(getName() + " begin waiting!");
                    long waitTime = System.currentTimeMillis();
                    try {
                        flag.wait();
                        //flag.wait(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waitTime = System.currentTimeMillis() - waitTime;
                    System.out.println(getName() + "wait time :" + waitTime);
                }
                System.out.println(getName() + " end waiting!");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Thread Run!");
        NotifyTest test = new NotifyTest();
        NotifyThread notifyThread = test.new NotifyThread("notify01");
        WaitThread waitThread01 = test.new WaitThread("waiter01");
        WaitThread waitThread02 = test.new WaitThread("waiter02");
        WaitThread waitThread03 = test.new WaitThread("waiter03");
        notifyThread.start(); //sleep 3，等待其他线程


        /*  notifyAll使所有原来在该对象上等待被notify的线程统统退出wait的状态，
      变成等待该对象上的锁，一旦该对象被解锁，他们就会去竞争。
        notify则文明得多他只是选择一个wait状态线程进行通知，并使它获得该对象上的锁，
        但不惊动其他同样在等待被该对象notify的线程们，
        当第一个线程运行完毕以后释放对象上的锁此时如果该对象没有再次使用notify语句，
        则即便该对象已经空闲，其他wait状态等待的线程由于没有得到该对象的通知，
        继续处在wait状态，直到这个对象发出一个notify或notifyAll，它们等待的是被notify或notifyAll，而不是锁。*/
        waitThread01.start();
        waitThread02.start();
        waitThread03.start();

    }

}