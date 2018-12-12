# Java多线程与并发基础知识



## 什么是并发

- 并发就是指程序同时处理多个任务的能力。
- 并发编程的根源在于对多任务情况下对访问资源的有效控制。



## 程序、进程与线程



- 程序是静态的概念，`windows`下通常指`exe`文件。
- 进程是动态的概念，是程序在运行状态，进程说明程序在内存的边界。
- 线程是进程内的一个“基本任务”，每个线程都有自己的功能，是`CPU`分配与调度的基本单位。



## 并发与并行



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>



## 同步和异步

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>





## 临界区



- 临界区用来表示一种公共资源与共享数据，可以被多个线程使用。
- 同一时间只能有一个线程访问临界区（阻塞状态），其他资源必须等待。



## 死锁、饥饿、活锁



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>



## 线程安全



在拥有共享数据的多条线程并行执行的程序中，线程安全的代码会通过同步机制保证各个线程都可以正常且正确的执行，不会出现数据污染等意外情况。



## 线程安全三个特性



- 原子性

  - 即一个操作或者多个操作，要么全部执行并且执行的过程不会被任何因素打断，要么都不执行。

    `i=i+1`

- 可见性

  - 当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改的值。



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>

​	

- 有序性
  - 如果在本线程内观察，所有的操作都是有序的；如果在一个线程观察另一个线程，所有的操作都是无序的。



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>