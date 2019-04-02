# 多线程`Thread join`与`yield`

记录一下多线程中`join`和`yield`的一些用法

## `Thread join`的简单用法

看一下下面这段代码：

```
package com.example.demo.mythread;

public class MyThread extends Thread {
    
    @Override
    public void run () {
        
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("test");
    }
    
    public static void main(String[] args) {
        Thread t = new MyThread();
        t.start();

        System.out.println("main");

        try {
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("main2");
    }
}

```

**输出：**

```
main
test
main2

Process finished with exit code 0
```

这段代码里面，t是子进程，`main`是主进程，`join`的作用就是把多线程变成串行执行，谁调用谁等待，在这段代码里面是`main`进程先调用的t进程，那么就是`main`进程一直等待，直到t进程执行完毕。

`join`还有一个用法，如果把上述代码改成`t.join(2000)` 那么运行结果会变成这样：

**输出：**

```
main
main2
test

Process finished with exit code 0
```

原因是加上等待时间，如果t进程不能在2秒之内执行完，那么`main`进程就要接着执行了。

### `join`的源码

```
public final synchronized void join(long millis)
    throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (millis == 0) {
            while (isAlive()) {
                wait(0);
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);
                now = System.currentTimeMillis() - base;
            }
        }
    }
```

`join` 底层还是运用`wait`，所以`join`的作用是谁调用，谁等待。

## `Thread yield` 的用法

### `Thread yield` 源码

```
/**
     * A hint to the scheduler that the current thread is willing to yield
     * its current use of a processor. The scheduler is free to ignore this
     * hint.
     *
     * <p> Yield is a heuristic attempt to improve relative progression
     * between threads that would otherwise over-utilise a CPU. Its use
     * should be combined with detailed profiling and benchmarking to
     * ensure that it actually has the desired effect.
     *
     * <p> It is rarely appropriate to use this method. It may be useful
     * for debugging or testing purposes, where it may help to reproduce
     * bugs due to race conditions. It may also be useful when designing
     * concurrency control constructs such as the ones in the
     * {@link java.util.concurrent.locks} package.
     */
    public static native void yield();
```

`yield：`英文意思是：屈服，退让。
在线程中也有类似意思，意思是当前线程退让出使用权，把运行机会交给线程池中拥有相同优先级的线程。线程调用了`yield`方法，该线程就从运行状态变为可运行状态

对静态方法 `Thread.yield()` 的调用声明了当前线程已经完成了生命周期中最重要的部分，可以切换给其它线程来执行。该方法只是对线程调度器的一个建议，而且也只是建议具有相同优先级的其它线程可以运行。

```
public void run() {
    Thread.yield();
}
```
