## JAVA i++线程非安全测试

### 初始代码

```
package org.moons.test;

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
    
    public  void incr(){
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
                            }
                            main.incr();
                            count.countDown();
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


```

**输出如下：**

```
9971
9947
9943
9965
9932
9957
9936
9973
9950
9973

Process finished with exit code 0
```

### i++线程非安全原理

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/java%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/Java%E5%AD%A6%E4%B9%A0%E5%9B%BE%E7%89%87/java%20i%2B%2B%E9%9D%9E%E5%AE%89%E5%85%A8.png" />
</div>

i++ 操作分为三步，第一步取i的值，第二步 让i加1 ，第三步更新i的值

如图主线程主有个i ,每个子线程中有一个i的拷备，当主线程i=0是，如果三个子线程同时读取到i的值为0，每个线程各自给i=i+1，然后分别写入主线程，这样三个线程执行后，虽然执行了3次i++ ，不过i的值为1.

### 线程安全做法一 synchronized

```
 public synchronized void incr(){
        i++;
    }
```

### 线程安全做法二 Lock

```
private Lock lock=new ReentrantLock();
    
    public void incr(){
        lock.lock();
        try{
            i++;
        }finally {
            lock.unlock();
        }
        
    }
```

**输出均是：**

```
10000
10000
10000
10000

Process finished with exit code 0
```
