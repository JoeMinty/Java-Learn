# 直接用`ConcurrentHashMap`代替`HashMap`引发的问题



读《大型网站系统与Java中间件实践》里边有提到直接将`HashMap`替换成`ConcurrentHashMap`时会有问题，写了一段代码测试一下其中的问题：

## `ConcurrentHashMap`的测试方式

```
package com.github.dockerjava.core.Test.ConcurrentTest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentHashMapDemo {

    private static ConcurrentHashMap<String,Integer> hm = new ConcurrentHashMap<>();

    private static String[] arrays =  {"yy","yy","welcome","java","234","java","1234","yy","welcome","java","234"};
    private static int length = arrays.length;
    //使用CountDownLatch进行线程控制，等到所有的多线程执行完毕了之后，才开始执行main线程，这个防止多线程还没统计完，main线程就退出了，
    //导致统计出错，如果不使用CountDownLatch就要使用Sleep.time(xxx)，卡住主线程
    private static CountDownLatch cd1 = new CountDownLatch(length);

    public static void main(String[] args) {
        
        //设置一个定长线程池，100没什么意义
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        for(String str :arrays) {
            threadPool.execute(new GetNum(str));
        }



        try {
            //main主线程开始等待多线程执行结果
            cd1.await();
        } catch (Exception e) {
            e.printStackTrace();
        }


        for(Map.Entry<String,Integer> entry:hm.entrySet()) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }

        System.out.println(cd1.getCount());
        
        //关闭线程池，否则主线程一直没办法退出
        if(!threadPool.isShutdown()) {
            threadPool.shutdown();
        }

    }

    static class GetNum implements Runnable {
        private String str;

        public GetNum(String str) {
            this.str = str;
        }

        @Override
        public void run() {

                Integer num = hm.get(str);

                if (null == num) {
                    hm.put(str,1);
                } else {
                    hm.put(str,num+1);
                }
  
                cd1.countDown();

        }
    }
}


```

运行结果：

```
yy:2
java:3
234:2
1234:1
welcome:2
0

Process finished with exit code 0
```

## `HashMap`加排他锁的实现

下面使用HashMap加锁然后程序产生正确结果:

```
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HashMapWithLockDemo {

    private static HashMap<String,Integer> hm = new HashMap<>();

    private static String[] arrays =  {"yy","yy","welcome","java","234","java","1234","yy","welcome","java","234"};
    private static int length = arrays.length;

    private static CountDownLatch cd1 = new CountDownLatch(length);

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);


        for(String str :arrays) {
            threadPool.execute(new GetNum(str));
        }



        try {
            cd1.await();
        } catch (Exception e) {
            e.printStackTrace();
        }


        for(Map.Entry<String,Integer> entry:hm.entrySet()) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }

        System.out.println(cd1.getCount());

        if(!threadPool.isShutdown()) {
            threadPool.shutdown();
        }

    }

    static class GetNum implements Runnable {
        private String str;

        public GetNum(String str) {
            this.str = str;
        }

        @Override
        public void run() {
            synchronized (str) {
                Integer num = hm.get(str);

                if (null == num) {
                    hm.put(str,1);
                } else {
                    hm.put(str,num+1);
                }

                cd1.countDown();
            }
        }
    }
}

```

输出结果：

```
yy:3
java:3
234:2
1234:1
welcome:2
0

Process finished with exit code 0

```

## 结果分析

如果对于值的修改要依赖上一次的值，还是直接给`HashMap`加锁实现好了；如果修改的值不依赖上一次的值就用`ConcurrentHashMap`。

这个场景中直接换成`ConcurrentHashMap`计数出错的原因是：

虽然`get`和`put`方法是线程安全的，但这个计数过程要求对整个过程（`get`值判断再`put`）线程安全的，所以不加锁没法实现`get+put`两个连贯动作是原子的，所以导致结果不正确。


