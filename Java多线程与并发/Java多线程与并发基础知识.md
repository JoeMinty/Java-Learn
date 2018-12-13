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
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/%E5%B9%B6%E5%8F%91%E4%B8%8E%E5%B9%B6%E8%A1%8C.PNG" />
</div>



## 同步和异步

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/%E5%90%8C%E6%AD%A5%E5%92%8C%E5%BC%82%E6%AD%A5.PNG" />
</div>





## 临界区



- 临界区用来表示一种公共资源与共享数据，可以被多个线程使用。
- 同一时间只能有一个线程访问临界区（阻塞状态），其他资源必须等待。



## 死锁、饥饿、活锁



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/%E6%AD%BB%E9%94%81%E9%A5%A5%E9%A5%BF%E6%B4%BB%E9%94%81.PNG" />
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
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/%E7%BA%BF%E7%A8%8B%E5%8F%AF%E8%A7%81%E6%80%A7%E4%B8%8D%E8%B6%B3%E7%9A%84%E6%A1%88%E4%BE%8B.PNG" />
</div>

​	

- 有序性
  - 如果在本线程内观察，所有的操作都是有序的；如果在一个线程观察另一个线程，所有的操作都是无序的。



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/%E6%9C%89%E5%BA%8F%E6%80%A7%E7%9A%84%E6%A1%88%E4%BE%8B.PNG" />
</div>


## `Java`内存模型--`Java Memory Model`



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/Java%E5%86%85%E5%AD%98%E6%A8%A1%E5%9E%8B.PNG" />
</div>
