# 线程池中`shutdown`，`awaitTermination`，`shutdownNow`的作用和区别
 

今天在写代码的时候看见了这个用法，记录比较一下三者的区别

```
ThreadPoolExecutor tp =new ThreadPoolExecutor(1,1,60,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(count));
...
tp.shutdown();
       
try{
   tp.awaitTermination(1,TimeUnit.DAYS);
} catch (InterruptedException e) {
   e.printStackTrace();
}

```

`shutdown`和`awaitTermination`为接口`ExecutorService`定义的两个方法，一般情况配合使用来关闭线程池。

## 关闭方法简介

### `shutdown`方法

`shutdown`方法：平滑的关闭`ExecutorService`，当此方法被调用时，`ExecutorService`停止接收新的任务并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。没有返回值，这个方法不会把正在执行会已经提交的线程给强制终止掉，但是也不会等着它们执行完，当调用的时候线程池即被关闭。


### `awaitTermination`方法

`awaitTermination`方法：接收人`timeout`和`TimeUnit`两个参数，用于设定超时时间及单位。当等待超过设定时间时，会监测`ExecutorService`是否已经关闭，若关闭则返回`true`，否则返回`false`。一般情况下会和`shutdown`方法组合使用。


### `shutdownNow`方法

`shutdownNow`方法：阻止新来的任务提交，同时会中断当前正在运行的线程，另外它还将workQueue中的任务给移除，并将这些任务添加到列表中进行返回，一般情况不建议使用。

## 获取关闭情况的方法简介

接口`ExecutorService`还提供了三种获取线程关闭情况的方法，现在分别介绍一下三者的区别

### `isShutdown`方法

`isShutDown`当调用`shutdown()`方法后返回为`true`；它是不管线程池中的任务是否全部执行完毕。

### `isTerminated`方法

`isTerminated`当调用`shutdown()`方法后，并且所有提交的任务完成后返回为`true`。

### `isTerminating`方法

`isTerminating`当调用`shutdown()`方法后，并且有提交的任务没有完成后返回为`true`。

### 代码示例

```
package com.github.dockerjava.core.Test.importnew;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Demo05 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        final List<Integer> l = new LinkedList<>();
        int count = 2000;
        ThreadPoolExecutor tp =new ThreadPoolExecutor(2000,2000,60,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(count));

        final Random random = new Random();

        for(int i =0 ;i<count;i++){
            tp.submit(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    l.add(random.nextInt());
                }
            });
        }

        if (!tp.isShutdown()) {
            tp.shutdown();
            System.out.println("come in !tp.isShutdown");
        }

        System.out.println("tp.isTerminated(): "+tp.isTerminated());

        System.out.println("tp.isTerminating(): "+tp.isTerminating());

        System.out.println();

        try{
            tp.awaitTermination(1,TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() - startTime);

    }
}

```


## 参考资料

https://www.cnblogs.com/siriusckx/articles/3989057.html

https://www.jianshu.com/p/b5e2283e869c
