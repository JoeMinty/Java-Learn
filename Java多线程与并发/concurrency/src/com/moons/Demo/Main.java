package com.moons.Demo;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private int i;
    public int getI() {
        return i;
    }
    public void setI(int i) {
        this.i = i;
    }

    public  synchronized void incr(){
        i++;
    }

    public static void main(String[] args) throws InterruptedException {

        for(int j=0;j<10;j++){
            final Main main=new Main();
            //CountDownLatch是一个同步辅助类，在jdk5中引入，它允许一个或多个线程等待其他线程操作完成之后才执行。
            final CountDownLatch count = new CountDownLatch(10000);
            for(int i=0;i<100;i++){
                new Thread(new Runnable() {
                    public void run() {
                        // TODO Auto-generated method stub
                        for(int j=0;j<100;j++){
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }finally {
                                main.incr();
                                count.countDown();
                            }

                        }
                    }
                }).start();

            }
            //主线程等待子线程结束
            count.await();
            System.out.println(main.getI());
        }

    }

}


