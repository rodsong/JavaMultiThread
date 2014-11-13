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
class NumberThread implements Runnable {
    private Object b;
    private Object a;

    public NumberThread(Object b, Object a) {
        this.b = b;
        this.a = a;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 4; i++) {
            synchronized (b) {
                synchronized (a) {
                    System.out.print(i);
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    a.notify();

                }
                try {
                    b.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


class UpperCharThread implements Runnable {
    private String[] up = {"A", "B", "C", "D"};
    private Object c;
    private Object b;

    public UpperCharThread(Object c, Object b) {
        this.c = c;
        this.b = b;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            synchronized (c) {
                synchronized (b) {
                    System.out.print(up[i]);
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   b.notify();

                }
                try {
                    c.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class LowerCharThread implements Runnable {
    private String[] lower = {"a", "b", "c", "d"};
    private Object a;
    private Object c;

    public LowerCharThread(Object a, Object c) {
        this.a = a;
        this.c = c;
    }

    @Override

    public void run() {
        for (int i = 0; i < 4; i++) {
            synchronized (a) {
                synchronized (c) {
                    System.out.print(lower[i]);
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   c.notify();

                }
                try {
                    a.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}


public class Demo {
    public static void main(String[] args) throws InterruptedException {
        final Object a = new Object();
        final Object b = new Object();
        final Object c = new Object();


        Thread ta = new Thread(new NumberThread(c, a)); //释放a，c等待
        Thread tb = new Thread(new UpperCharThread(a, b)); //释放c，b等待
        Thread tc = new Thread(new LowerCharThread(b, c));//释放b，a等待
        ta.start();
        tb.start();
        tc.start();

        ta.join();
        tb.join();
        tc.join();
        System.out.println("ok");
    }
}