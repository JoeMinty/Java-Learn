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

