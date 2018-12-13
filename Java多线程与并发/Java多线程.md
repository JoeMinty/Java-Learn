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
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>

