package practice;


/*TODO:
        NumberThread 类是负责循环输出数字 1-26 的线程一
        UpperCharThread 类是负责循环输出大写字母 A-Z 的线程二
        LowerCharThread 类是负责循环输出小写字母 a-z 的线程三
        现在要实现的是一次输出 数字、大写字母、小写
        如：
        1 A a
        2 B b
        . . .
        . . .
        26 Z z*/
class NumberThread2 implements Runnable {
    private Object o;

    public NumberThread2(Object o) {
        this.o = o;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 4; i++) {
            synchronized (o) {
                System.out.print(i);
                o.notify();
            }

        }
    }
}


class UpperCharThread2 implements Runnable {
    private String[] up = {"A", "B", "C", "D"};
    private Object o;

    public UpperCharThread2(Object o) {
        this.o = o;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            synchronized (o) {
                System.out.print(up[i]);
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                o.notify();
            }

        }
    }
}

class LowerCharThread2 implements Runnable {
    private String[] lower = {"a", "b", "c", "d"};
    private Object o;

    public LowerCharThread2(Object o) {
        this.o = o;
    }

    @Override

    public void run() {
        for (int i = 0; i < 4; i++) {
            synchronized (o) {
                System.out.println(lower[i]);
                o.notify();
            }
        }
    }
}


public class Demo2 {
    public static void main(String[] args) throws InterruptedException {
        final Object o = new Object();
        Thread ta = new Thread(new NumberThread2(o)); //释放a，c等待
        Thread tb = new Thread(new UpperCharThread2(o)); //释放c，b等待
        //Thread tc = new Thread(new LowerCharThread2(o));//释放b，a等待
        ta.start();
        tb.start();
       // tc.start();

        ta.join();
        tb.join();
       // tc.join();
        System.out.println("ok");
    }
}