# `JDK`并发工具包-`JUC`



## Java并发工具包与连接池



### `java.util.concurrent`

- 并发是伴随着多核处理器的诞生而产生的，为了充分利用硬件资源，诞生了多线程技术。但是多线程又存在资源竞争的问题，引发了同步和互斥的问题，`JDK 1.5`推出的`java.util.concurrent`（并发工具包）来解决这些问题。



### `new Thread`的弊端



- `new Thread()`新建对象、性能差
- 线程缺乏统一管理，可能无限制的新建线程，相互竞争，严重时会占用过多系统资源导致司机或`OOM`



### `ThreadPool`-线程池



- 重用存在的线程，减少创建对象、消亡的开销
- 线程总数可控，提高资源的利用率
- 避免过多资源竞争，避免阻塞
- 提供额外功能，定时执行、定期执行、监控等。



### 线程池的种类



- 在`java.util.concurrent`中，提供了工具类`Executors`（调度器）对象来创建线程池，可创建的线程池有四种：
  - `CachedThreadPool`- 可缓存线程池
  - `FixedThreadPool`- 定长线程池
  - `SingleThreadExecutor`-单线程池
  - `ScheduledThreadPool`-调度线程池



### 线程池的经典应用



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/%E7%BA%BF%E7%A8%8B%E6%B1%A0%E7%9A%84%E7%BB%8F%E5%85%B8%E5%BA%94%E7%94%A8.PNG" />
</div>




## `JUC`之`CountDownLatch`倒计时锁



### `CountDownLatch`-倒计时锁



- `CountDownLatch`倒计时锁特别适合“总-分任务”，例如多线程计算后的数据汇总
- `CountDownLatch`类位于`java.util.concurrent(J.U.C)`包下，利用它可以实现类似计数器的功能。比如有一个任务`A`，它要等待其他三个任务执行完毕之后才能执行，此时就可以利用`CountDownLatch`来实现这种功能了。



### `CountDownLatch`执行原理



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/CountDownLatch%E6%89%A7%E8%A1%8C%E5%8E%9F%E7%90%86.PNG" />
</div>



## `JCU`之`Semaphore`信号量



- `Semaphore`信号量经常用于限制获取某种资源的线程数量。下面举个例子，比如说跑到上有五个跑道，一个跑道一次只能有一个学生在上面跑步，一旦所有跑道都在使用，那么后面的学生就需要等待，知道有一个学生不跑了。



## `JCU`之`CyclicBarrier`循环屏障



### `CyclicBarrier`循环屏障



- `CyclicBarrier`是一个同步工具类，它允许一组线程互相等待，直到到达某个公共屏障点。与`CountDownLatch`不同的是该`barrier`在释放等待线程厚可以重用，所以称它为循环`(Cyclic)`的屏障`(Barrier)`。


<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/CyclicBarrier%E5%BE%AA%E7%8E%AF%E5%B1%8F%E9%9A%9C.PNG" />
</div>

### `CyclicBarrier`的应用场景



- `CyclicBarrier`适用于多线程必须同时开始的场景

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/CyclicBarrier%E7%9A%84%E5%BA%94%E7%94%A8%E5%9C%BA%E6%99%AF.PNG" />
</div>




## `JUC`之`ReentrantLock`重入锁



### 什么是重入锁



- 重入锁是指任意线程在获取到锁之后，再次获取该锁而不会被该锁所阻塞
- `ReentrantLock`设计的目标是用来替代`synchronized`关键字



### `ReentrantLock`与`synchronized`的区别



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/ReentrantLock%E4%B8%8Esynchronized%E7%9A%84%E5%8C%BA%E5%88%AB.PNG" />
</div>



## `JUC`之`Condition`线程等待与唤醒



### `Condition`条件唤醒



- 我们在并行程序中，避免不了某些线程要按预先规定好的顺序执行，例如：先新增再修改，先买后卖，先进后出......，对于这类场景，使用`JUC`的`Condition`对象再合适不过了。
- `JUC`中提供了`Condition`对象，用于让指定线程等待与唤醒，按预期顺序执行。它必须和`ReentrantLock`重入锁配合使用。
- `Condition`用于替代`wait()/notify()`方法
  - `notify`只能在同步代码块中执行，而`Condition`可以唤醒指定的线程，这有利于更好的控制并发程序。



### `Condition`核心方法



- `await()`- 阻塞当前线程，直到`singal`唤醒
- `signal()`- 唤醒被`await`的线程，从中断处继续执行
- `signalAll()` - 唤醒所有被`await()`阻塞的线程



## `JUC`之`Callable&Future`



### `Callable&Future`



- `Callable`和`Runnable`一样代表着任务，区别在于`Callable`有返回值并且可以抛出异常。
- `Future`是一个接口。它用于表示异步计算的结果。提供了检查计算是否完成的方法，以等待计算的完成，并获取计算的结果。



## `JUC`之同步容器



### 请写出线程安全的类



- `Vector`是线程安全的，`ArrayList`、`LinkedList`是线程不安全的
- `Properties`是线程安全的，`HashSet`、`TreeSet`是不安全的
- `StringBuffer`是线程安全的，`StringBuilder`是线程不安全的
- `HashTable`是线程安全的，`HashMap`是线程不安全的



### 线程安全-并发容器



- `ArrayList` -->`CopyOnWriteArrayList`--写复制列表
- `HashSet`--> `CopyOnWriteArraySet` --写复制集合
- `HashMap` --> `ConcurrentHashMap` -- 分段锁映射



### `CopyOnWriteArrayList`并发原理

- `CopyOnWriteArrayList`通过“副本”解决并发问题



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/CopyWriteArrayList%E5%B9%B6%E5%8F%91%E5%8E%9F%E7%90%86.PNG" />
</div>

### `ConcurrentHashMap`

- `ConcurrentHashMap`采用“分段锁”的方式



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/ConcurrentHashMap.PNG" />
</div>





## `JUC`之`Atomic`包与`CAS`算法



### 回顾原子性

- 原子性：是指一个操作或多个操作要么全部执行，且执行的过程不会被任何因素打断，要么就都不执行。



### `Atomic`包



- `Atomic`包是`java.util.concurrent`下的另一个专门为线程安全设计的`Java`包，包含多个原子操作类。

- `Atomic`常用类

  - `AtomicInteger`

  - `AtomicIntegerArray`

  - `AtomicBoolean`

  - `AtomicLong`

  - `AtomicLongArray`




### 白话`CAS`算法



- 锁是用来做并发最简单的方式，当然其代价也是最高的。独占锁是一种悲观锁，`synchronized`就是一种独占锁，它假设最坏的情况，并且只有1在确保其他线程不会造成干扰的情况下执行，会导致其他所有需要锁的线程挂起，等待持有锁的线程释放锁。
- 所谓乐观锁就是，每次不加锁而是假设没有冲突而去完成某项操作，如果因为冲突失败就重试，直到成功为止。其中`CAS`（比较与交换，`Compare And Swap`）是一种最有名的无锁算法。



### 白话`C(Compare)A(And)Swap`算法

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/CAS.PNG" />
</div>



### `Atomic`的应用场景



- 虽然基于`CAS`的线程安全机制很好很高效，但要说的是，并非所有线程安全都可以用这样的方法来实现，这只适合一些粒度比较小型，如计数器这样的需求用起来才有效，否则也不会有锁的存在了。



## 课程总结



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>

