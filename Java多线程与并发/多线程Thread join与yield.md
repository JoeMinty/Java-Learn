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

这段代码里面，t是子进程，`main`是主进程，`join`的作用就是然多线程变成串行执行，谁调用谁等待，在这段代码里面是`main`进程先调用的t进程，那么就是`main`进程一直等待，直到t进程执行完毕。

`join`还有一个用法，如果把上述代码改成`t.join(2000)` 那么运行结果机会变成这样：

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

