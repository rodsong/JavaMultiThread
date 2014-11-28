package practice;

/**
 * 为了演示同步方法的使用，构建了一个信用卡账户，起初信用额为100w，
 * 然后模拟透支、存款等多个操作。
 * <p/>
 * 显然银行账户User对象是个竞争资源，而多个并发操作的是账户方法oper(int x)，
 * 当然应该在此方法上加上同步，并将账户的余额设为私有变量，禁止直接访问。
 */
public class Test1 {
    public static void main(String[] args) {
        User u = new User("张三", 100);
        MyThread t1 = new MyThread("线程A", u, 20);
        MyThread t2 = new MyThread("线程B", u, -60);
        MyThread t3 = new MyThread("线程C", u, -80);
        MyThread t4 = new MyThread("线程D", u, -30);
        MyThread t5 = new MyThread("线程E", u, 32);
        MyThread t6 = new MyThread("线程F", u, 21);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}

class MyThread extends Thread {
    private User u;
    private int y = 0;

    MyThread(String name, User u, int y) {
        super(name);
        this.u = u;
        this.y = y;
    }

    public void run() {
        u.oper(y);
    }
}

class User {
    private String code;
    private int cash;

    User(String code, int cash) {
        this.code = code;
        this.cash = cash;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 业务方法
     *
     * @param x 添加x万元
     */
    public synchronized void oper(int x) {
        try {
            Thread.sleep(10L);
            this.cash += x;
            System.out.println(Thread.currentThread().getName() + "运行结束，增加“" + x + "”，当前用户账户余额为：" + cash);
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "code='" + code + '\'' +
                ", cash=" + cash +
                '}';
    }
}