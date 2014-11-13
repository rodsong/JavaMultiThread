package DaemonSample15;

import java.io.IOException;

public class DaemonThreadTest extends Thread{

    public DaemonThreadTest() {
    }

    public void run(){
        for(int i = 1; i <= 100;i++){
            try{
                Thread.sleep(100);

            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
            System.out.println(i);
        }
    }
    public static void main(String [] args){
        DaemonThreadTest test = new DaemonThreadTest();
        // 如果不设置daemon，那么线程将输出100后才结束
        //在test未结束前，执行下面的输入操作，则test终止执行，因为jvm中只剩下守护线程时会终止
        test.setDaemon(true);
        test.start();
        System.out.println("isDaemon = " +test.isDaemon());

        //接受输入，使程序在此停顿，一旦接收到用户输入，main线程结束，守护线程自动结束
        try {
            System.in.read();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.print(Thread.currentThread().getName()+" 结束");
    }
}
