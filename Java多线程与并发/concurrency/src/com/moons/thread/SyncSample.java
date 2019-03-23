package com.moons.thread;

import java.util.Random;

public class SyncSample {
    public static void main(String[] args) {
        Couplet c = new Couplet();
        for(int i = 0 ; i < 10000 ; i++){
            new Thread(){
                public void run(){
                    int r = new Random().nextInt(2);
                    if(r % 2 == 0){
                        c.first();
                    }else{
                        c.second();
                    }
                }
            }.start();
        }
    }
}
class Couplet{
    Object lock = new Object(); //锁对象
    public synchronized  void first(){
//        synchronized (lock) { //同步代码块，在同一时间只允许有一个线程执行访问这个方法
            System.out.printf("华");
            System.out.printf("为");
            System.out.printf("公");
            System.out.printf("司");
            System.out.println();
//        }
    }
    public  void second(){
        synchronized (Couplet.class) { //因为两个同步代码指向了同一把锁lock，所以在同一个时间内只允许有一个代码块执行，其他等待
            System.out.printf("吉");
            System.out.printf("林");
            System.out.printf("大");
            System.out.printf("学");
            System.out.println();
        }

    }
}
