package ReentrantLocks_10;

/**
 * Source:http://www.journaldev.com/2377/java-lock-example-and-concurrency-lock-vs-synchronized
 *
 * Lock: This is the base interface for Lock API. It provides all the features
 * of synchronized keyword with additional ways to create different Conditions
 * for locking, providing timeout for thread to wait for lock. Some of the
 * important methods are lock() to acquire the lock, unlock() to release the
 * lock, tryLock() to wait for lock for a certain period of time, newCondition()
 * to create the Condition etc.
 *
 * ReentrantLock: This is the most widely used implementation class of Lock
 * interface. This class implements the Lock interface in similar way as
 * synchronized keyword. (see App.java for more)
 *
 * Condition: Condition objects are similar to Object wait-notify model with
 * additional feature to create different sets of wait. A Condition object is
 * always created by Lock object. Some of the important methods are await() that
 * is similar to wait() and signal(), signalAll() that is similar to notify()
 * and notifyAll() methods.
 *
 * Codes with minor comments are from http://www.caveofprogramming.com/youtube/
 * also freely available at
 * https://www.udemy.com/java-multithreading/?couponCode=FREE
 *
 * @author Z.B. Celik <celik.berkay@gmail.com>
 */
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public void firstThread() throws InterruptedException {
        lock.lock();
        System.out.println("Waiting ....");
        cond.await();
        System.out.println("Woken up!");

        try {
            increment();

        } finally {
         /*   可以看到 Lock 和 synchronized 有一点明显的区别 —— lock 必须在 finally 块中释放。
            否则，如果受保护的代码将抛出异常，锁就有可能永远得不到释放！这一点区别看起来可能没什么，
            但是实际上，它极为重要。忘记在 finally 块中释放锁，可能会在程序中留下一个定时炸弹，
            当有一天炸弹爆炸时，您要花费很大力气才有找到源头在哪。而使用同步，JVM 将确保锁会获得自动释放。*/
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        System.out.println("Press the return key!");
        new Scanner(System.in).nextLine();
        System.out.println("Got return key!");
        cond.signal();
        try {
            increment();
        } finally {
            lock.unlock();//should be written to unlock Thread whenever signal() is called
        }
    }

    public void finished() {
        System.out.println("Count is: " + count);
    }
}
