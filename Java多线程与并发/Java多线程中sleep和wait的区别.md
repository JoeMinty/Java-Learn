# `Java`多线程中`sleep`和`wait`的区别



## `Sleep()`的特点



对于`sleep()`方法，我们首先要知道该方法是属于`Thread`类中的。而`wait()`方法，则是属于`Object`类中的。

`sleep()`方法导致了程序暂停执行指定的时间，让出`cpu`该其他线程，但是他的监控状态依然保持者，当指定的时间到了又会自动恢复运行状态。

在调用`sleep()`方法的过程中，线程不会释放对象锁。



## `wait()`的特点

而当调用`wait()`方法的时候，线程会放弃对象锁，进入等待此对象的等待锁定池，只有针对此对象调用`notify()`方法后本线程才进入对象锁定池准备，获取对象锁进入运行状态。



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>



## `wait()`和`sleep()`实例

```
/**
 * java中的sleep()和wait()的区别
 * @author moons
 * @date 2018-12-20
 */
public class TestD {

    public static void main(String[] args) {
        new Thread(new Thread1()).start();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(new Thread2()).start();
    }
    
    private static class Thread1 implements Runnable{
        @Override
        public void run(){
            synchronized (TestD.class) {
            System.out.println("enter thread1...");    
            System.out.println("thread1 is waiting...");
            try {
                //调用wait()方法，线程会放弃对象锁，进入等待此对象的等待锁定池
                TestD.class.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("thread1 is going on ....");
            System.out.println("thread1 is over!!!");
            }
        }
    }
    
    private static class Thread2 implements Runnable{
        @Override
        public void run(){
            synchronized (TestD.class) {
                System.out.println("enter thread2....");
                System.out.println("thread2 is sleep....");
                //只有针对此对象调用notify()方法后本线程才进入对象锁定池准备获取对象锁进入运行状态。
                TestD.class.notify();
                //==================
                //区别
                //如果我们把代码：TestD.class.notify();给注释掉，即TestD.class调用了wait()方法，但是没有调用notify()
                //方法，则线程永远处于挂起状态。
                try {
                    //sleep()方法导致了程序暂停执行指定的时间，让出cpu该其他线程，
                    //但是他的监控状态依然保持者，当指定的时间到了又会自动恢复运行状态。
                    //在调用sleep()方法的过程中，线程不会释放对象锁。
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 is going on....");
                System.out.println("thread2 is over!!!");
            }
        }
    }
}
```

**运行效果：**

```
enter thread1...
thread1 is waiting...
enter thread2....
thread2 is sleep....
thread2 is going on....
thread2 is over!!!
thread1 is going on ....
thread1 is over!!!
```



**如果注释掉代码：**

```
TestD.class.notify();
```

**运行效果：**

```
enter thread1...
thread1 is waiting...
enter thread2....
thread2 is sleep....
thread2 is going on....
thread2 is over!!!

且程序一直处于挂起状态。
```



## `wait()`必须在同步代码块中运行



`wait()`和`notify()`因为会对对象的“锁标志”进行操作，所以他们必需在`Synchronized`函数或者 `synchronized block `中进行调用。如果在`non-synchronized `函数或 `non-synchronized block` 中进行调用，虽然能编译通过，但在运行时会发生`IllegalMonitorStateException`的异常。。



## 总结



- 1、每个对象都有一个锁来控制同步访问，`Synchronized`关键字可以和对象的锁交互，来实现同步方法或同步块。`sleep()`方法正在执行的线程主动让出`CPU`（然后`CPU`就可以去执行其他任务），在`sleep`指定时间后`CPU`再回到该线程继续往下执行
  (注意：`sleep`方法只让出了`CPU`，而并不会释放同步资源锁！！！)；

  `wait()`方法则是指当前线程让自己暂时退让出同步资源锁，以便其他正在等待该资源的线程得到该资源进而运行，只有调用了`notify()`方法，之前调用`wait()`的线程才会解除`wait`状态，可以去参与竞争同步资源锁，进而得到执行。
  （注意：`notify`的作用相当于叫醒睡着的人，而并不会给他分配任务，就是说`notify`只是让之前调用`wait`的线程有权利重新参与线程的调度）；

- 2、`sleep()`方法可以在任何地方使用；
  `wait()`方法则只能在同步方法或同步块中使用；

- 3、`sleep()`是线程线程类（`Thread`）的方法，调用会暂停此线程指定的时间，但监控依然保持，不会释放对象锁，到时间自动恢复；
  `wait()`是`Object`的方法，调用会放弃对象锁，进入等待队列，待调用`notify()/notifyAll()`唤醒指定的线程或者所有线程，才会进入锁池，不再次获得对象锁才会进入运行状态；