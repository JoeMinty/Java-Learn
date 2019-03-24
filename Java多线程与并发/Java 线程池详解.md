# `Java`线程池详解

## 线程池的继承关系

```
Executor ==> 接口



```

## 什么时候启用非核心线程

当初`sun`的线程池模式要设计成队列满了才能创建非核心线程？类比其他类似池的功能实现，很多都是设置最小数最大数，达到最大数才向等待队列里加入，比如有的连接
池实现。

```

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
       ThreadPoolExecutor t =  new ThreadPoolExecutor(1,10,1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(3), new ThreadPoolExecutor.DiscardPolicy());

       for(int i= 0;i<20;i++) {
           t.execute(new Runnable() {
               @Override
               public void run() {
                   System.out.println(Thread.currentThread()+ " Hello World");
               }
           });
       }

    }
}

```

上述代码的核心线程是1，非核心线程数量是10，队列的大小是3，所以当多线程运行的时候，先把核心线程使用，然后把队列占满之后，才会启用非核心线程，如果这个例子中，把队列的大小修改为20，那么永远不会启用非核心线程。


## 参考链接

http://www.importnew.com/29813.html


https://www.cnblogs.com/yulinfeng/p/7039979.html
