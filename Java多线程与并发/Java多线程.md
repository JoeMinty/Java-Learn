# Java多线程



## Java中创建线程三种方式



- 继承`Thread`类创建线程
- 实现`Runnable`接口创建线程
- 使用`Callable`和`Future`创建线程



## 并发工具包-`Concurreent`



- `JDK1.5`以后为我们专门提供了一个并发工具包`java.util.concurrent`。

- `java.util.concurrent`包含许多线程安全、测试良好、高性能的并发构建块。

  创建`concurrent`的目的就是要实现`Collection`框架对数据结构所执行的并发操作。通过提供一组可靠的、高性能并发构建块，开发人员可以提高并发类的线程安全、可伸缩性、性能、可读性和可靠性。



## 创建线程的三种方式对比



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91/Java%E5%A4%9A%E7%BA%BF%E7%A8%8B%E4%B8%8E%E5%B9%B6%E5%8F%91%E7%9A%84%E5%9B%BE%E7%89%87/%E5%88%9B%E5%BB%BA%E7%BA%BF%E7%A8%8B%E7%9A%84%E4%B8%89%E7%A7%8D%E6%96%B9%E5%BC%8F%E5%AF%B9%E6%AF%94.PNG" />
</div>


## `Synchronized`线程同步机制



### 代码中的同步机制



- `synchronized`(同步锁) 关键字的作用就是利用一个特定的对象设置一个锁`lock`(绣球)，在多线程(游客)并发访问的时候，同时只允许一个线程（游客）可以获得这个锁，执行特定的代码（迎娶新娘）。执行后释放锁，继续由其他线程争强。



### `Synchronize`的使用场景



- `Synchronize`可以使用在以下三种场景，对应不同锁对象：
  - `synchronized`代码块 ---> 任意对象即可
  - `synchronized`方法 ---> `this` 当前对象
  - `synchronized`静态方法 --> 该类的字节码对象



## 线程的五种状态



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>





## 死锁的产生

**死锁产生的原因**



- 死锁是在多线程情况下最严重的问题，在多线程对公共资源（文件、数据）等进行操作时，彼此不释放自己的资源，而去试图操作其他线程的资源，而形成交叉引用，就会产生死锁。


<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>



- 解决死锁最根本的建议是：
  - 尽量减少公共资源的引用，用完马上释放
  - 用完马上释放公共资源
  - 减少`synchronized`使用，采用“副本”方式替代



## 重新认识线程安全



### 线程安全



- 在拥有共享数据的多条线程并行执行的程序中，线程安全的代码会通过同步机制保证各个线程都可以正常且正确的执行，不会出现数据污染等意外情况。

- 通过`synchronized`使线程变得安全



### 线程安全与不安全的区别



- 线程安全
  - 优点：可靠
  - 缺点：执行速度慢
  - 使用建议：需要线程共享时使用
- 线程不安全
  - 优点：速度快
  - 缺点：可能与预期不符
  - 使用建议：在线程内部使用，无需线程间共享



### 线程（不）安全的类



- `Vector`是线程安全的，`ArrayList`、`LinkedList`是线程不安全的
- `Properties`是线程安全的，`HashSet`、`TreeSet`是不安全的
- `StringBuffer`是线程安全的，`StringBuilder`是线程不安全的
- `HashTable`是线程安全的，`HashMap`是线程不安全的



