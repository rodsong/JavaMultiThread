
1. 关键字 synchronized, volatile
   既然volatile关键字已经实现了线程间数据同步，又要synchronized干什么呢？

2. volatile和synchronized的区别：
   1).volatile本质是在告诉jvm当前变量在寄存器（工作内存）中的值是不确定的，需要从主存中读取；
      synchronized则是锁定当前变量，只有当前线程可以访问该变量，其他线程被阻塞住。
      volatile只是在线程内存(寄存器)和“主”内存间同步某个变量的值
     synchronized通过锁定和解锁某个监视器同步所有变量的值。显然synchronized要比volatile消耗更多资源。

   2).volatile仅能使用在变量级别；synchronized则可以使用在变量、方法、和类级别的

   3).volatile仅能实现变量的修改可见性，并能不能保证原子性(如果一段代码被认为是Atomic，则表示这段代码在执行过程中，是不能被中断的),
      而synchronized则可以保证变量的修改可见性和原子性

   4).volatile不会造成线程的阻塞；synchronized可能会造成线程的阻塞。

   5).volatile标记的变量不会被编译器优化；synchronized标记的变量可以被编译器优化

3.   thread.start():执行run方法;
     thread.join()：主线程在被加入的线程执行完成后再继续执行，当前线程挂起，执行新加入线程。

4. 所对象 synchronized, lock


5. ExecutorService 线程池
    executer.submit();
    executer.shutdown();
    executer.awaitTermination();

6. CountDownLatch类是一个同步计数器,构造时传入int参数,该参数就是计数器的初始值，
   在一些应用场合中，需要等待某个条件达到要求后才能做后面的事情；同时当线程都完成后也会触发事件，以便进行后面的操作。
   这个时候就可以使用CountDownLatch。CountDownLatch最重要的方法是countDown()和await()，
   前者主要是倒数一次，后者是等待倒数到0，如果没有到达0，就只有阻塞等待了

7. 线程安全的容器BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
         1)add(anObject):把anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则招聘异常

                2)offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false.

                3)put(anObject):把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断直到BlockingQueue里面有空间再继续.

                4)poll(time):取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,取不到时返回null

                5)take():取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到Blocking有新的对象被加入为止

        其中：BlockingQueue 不接受null 元素。试图add、put 或offer 一个null 元素时，某些实现会抛出NullPointerException。null 被用作指示poll 操作失败的警戒值。


8.9.   Obj.wait()，与Obj.notify()必须要与synchronized(Obj)一起使用，
     也就是wait,与notify是针对已经获取了Obj锁进行操作，
     从语法角度来说就是Obj.wait(),Obj.notify必须在synchronized(Obj){...}语句块内.
     任何一个时刻，对象的控制权（monitor）只能被一个线程拥有。
     无论是执行对象的wait、notify还是notifyAll方法，必须保证当前运行的线程取得了该对象的控制权（monitor）
     如果在没有控制权的线程里执行对象的以上三种方法，就会报java.lang.IllegalMonitorStateException异常。
     JVM基于多线程，默认情况下不能保证运行时线程的时序性.

      notifyAll使所有原来在该对象上等待被notify的线程统统退出wait的状态，
      变成等待该对象上的锁，一旦该对象被解锁，他们就会去竞争。
      notify则文明得多他只是选择一个wait状态线程进行通知，并使它获得该对象上的锁，
      但不惊动其他同样在等待被该对象notify的线程们，
      当第一个线程运行完毕以后释放对象上的锁此时如果该对象没有再次使用notify语句，
      则即便该对象已经空闲，其他wait状态等待的线程由于没有得到该对象的通知，
      继续处在wait状态，直到这个对象发出一个notify或notifyAll，它们等待的是被notify或notifyAll，而不是锁。
      void wait() 在其他线程调用此对象的 notify() 方法或者 notifyAll()方法前，导致当前线程等待.
      void wait(long timeout)在其他线程调用此对象的notify() 方法 或者 notifyAll()方法，或者未过指定的时间量，导致当前线程等待，
      超过指定时间，线程会尝试自动获取该对象锁。

      调用sleep()和yield()的时候锁并没有被释放，而调用wait()将释放锁。从而使线程所在对象中的其他shnchronized数据可被别的线程使用。


(1)、常用的wait方法有wait()和wait(long timeout);
	void wait() 在其他线程调用此对象的 notify() 方法或者 notifyAll()方法前，导致当前线程等待。
	void wait(long timeout)在其他线程调用此对象的notify() 方法 或者 notifyAll()方法，或者超过指定的时间量前，导致当前线程等待。
	wait()后，线程会释放掉它所占有的“锁标志”，从而使线程所在对象中的其他shnchronized数据可被别的线程使用。

	wait()h和notify()因为会对对象的“锁标志”进行操作，所以他们必需在Synchronized函数或者 synchronized block 中进行调用。如果在non-synchronized 函数或 non-synchronized block 中进行调用，虽然能编译通过，但在运行时会发生IllegalMonitorStateException的异常。。

(2)、Thread.sleep(long millis)必须带有一个时间参数。
	sleep(long)使当前线程进入停滞状态，所以执行sleep()的线程在指定的时间内肯定不会被执行；
	sleep(long)可使优先级低的线程得到执行的机会，当然也可以让同优先级的线程有执行的机会；
	sleep(long)是不会释放锁标志的。

(3)、yield()没有参数
	sleep 方法使当前运行中的线程睡眠一段时间，进入不可以运行状态，这段时间的长短是由程序设定的，yield方法使当前线程让出CPU占有权，但让出的时间是不可设定的。
	yield()也不会释放锁标志。
	实际上，yield()方法对应了如下操作；先检测当前是否有相同优先级的线程处于同可运行状态，如有，则把CPU的占有权交给次线程，否则继续运行原来的线程，所以yield()方法称为“退让”，它把运行机会让给了同等级的其他线程。

	sleep 方法允许较低优先级的线程获得运行机会，但yield（）方法执行时，当前线程仍处在可运行状态，所以不可能让出较低优先级的线程此时获取CPU占有权。在一个运行系统中，如果较高优先级的线程没有调用sleep方法，也没有受到I/O阻塞，那么较低优先级线程只能等待所有较高优先级的线程运行结束，方可有机会运行。

	yield()只是使当前线程重新回到可执行状态，所有执行yield()的线程有可能在进入到可执行状态后马上又被执行，所以yield()方法只能使同优先级的线程有执行的机会。

10. Concurrency Lock vs synchronized
    参考，http://www.ibm.com/developerworks/cn/java/j-jtp10264/index.html

    ReentrantLock,可以实现synchronized所实现的功能
    await();signal();

    线程锁(ReentrantLock)、线程通信状态(Condition)

11.信号量 Semaphore

Semaphore当前在多线程环境下被扩放使用，操作系统的信号量是个很重要的概念，在进程控制方面都有应用。
Java 并发库 的Semaphore 可以很轻松完成信号量控制，Semaphore可以控制某个资源可被同时访问的个数，
通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
比如在Windows下可以设置共享文件的最大客户端访问个数。

12. Callable Future
    是1.5之后加的内容。
    Callable是类似于Runnable的接口，实现Callable接口的类和实现Runnable的类都是可被其他线程执行的任务。

    Callable和Runnable的区别如下：
    I    Callable定义的方法是call，而Runnable定义的方法是run。
    II   Callable的call方法可以有返回值，而Runnable的run方法不能有返回值。
    III  Callable的call方法可抛出异常，而Runnable的run方法不能抛出异常。

13.Daemon线程

在Java中有两类线程：用户线程 (User Thread)、守护线程 (Daemon Thread)。

所谓守护 线程，是指在程序运行的时候在后台提供一种通用服务的线程，比如垃圾回收线程就是一个很称职的守护者，并且这种线程并不属于程序中不可或缺的部分。因 此，当所有的非守护线程结束时，程序也就终止了，同时会杀死进程中的所有守护线程。反过来说，只要任何非守护线程还在运行，程序就不会终止。

用户线程和守护线程两者几乎没有区别，唯一的不同之处就在于虚拟机的离开：如果用户线程已经全部退出运行了，只剩下守护线程存在了，虚拟机也就退出了。 因为没有了被守护者，守护线程也就没有工作可做了，也就没有继续运行程序的必要了。

将线程转换为守护线程可以通过调用Thread对象的setDaemon(true)方法来实现。在使用守护线程时需要注意一下几点：

(1) thread.setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。你不能把正在运行的常规线程设置为守护线程。

(2) 在Daemon线程中产生的新线程也是Daemon的。

(3) 守护线程应该永远不去访问固有资源，如文件、数据库，因为它会在任何时候甚至在一个操作的中间发生中断。

 TODO:
 NumberThread 类是负责循环输出数字 1-26 的线程一
 UpperCharThread 类是负责循环输出大写字母 A-Z 的线程二
 LowerCharThread 类是负责循环输出小写字母 a-z 的线程三
 现在要实现的是一次输出 数字、大写字母、小写
 如：
 1 A a
 2 B b
 . . .
 . . .
 26 Z z
