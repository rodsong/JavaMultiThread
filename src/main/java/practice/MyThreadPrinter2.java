package practice;

/**
 * Created with IntelliJ IDEA.
 * User: rodsong
 * Date: 2014-24-15 15:38
 * To change
 */
public class MyThreadPrinter2 implements Runnable {

    private String name;
    private Object prev;
    private Object self;

    private MyThreadPrinter2(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    @Override
    public void run() {

        int count = 3;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.println(name+"  "+Thread.currentThread().getName());
                    count--;
                    try {
                        Thread.sleep(1L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    self.notify();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        MyThreadPrinter2 pa = new MyThreadPrinter2("A", c, a);
        MyThreadPrinter2 pb = new MyThreadPrinter2("B", a, b);
        MyThreadPrinter2 pc = new MyThreadPrinter2("C", b, c);

        new Thread(pa).start();
        Thread.sleep(2000L);
        new Thread(pb).start();
        Thread.sleep(2000L);
        new Thread(pc).start();
    }
}
