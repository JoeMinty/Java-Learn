# 线程本地存储（`Thread Local Storage`）

## 前言

如果一段代码中所需要的数据必须与其他代码共享，那就看看这些共享数据的代码是否能保证在同一个线程中执行。如果能保证，我们就可以把共享数据的可见范围限制在同一个线程之内，这样，无须同步也能保证线程之间不出现数据争用的问题。

符合这种特点的应用并不少见，大部分使用消费队列的架构模式（如“生产者-消费者”模式）都会将产品的消费过程尽量在一个线程中消费完。其中最重要的一个应用实例就是经典 `Web` 交互模型中的“一个请求对应一个服务器线程”（`Thread-per-Request`）的处理方式，这种处理方式的广泛应用使得很多 `Web`服务端应用都可以使用线程本地存储来解决线程安全问题。

可以使用 `java.lang.ThreadLocal` 类来实现线程本地存储功能。

这是 `Java` 提供的一种保存线程私有信息的机制，因为其在整个线程生命周期内有效，所以可以方便地在一个线程关联的不同业务模块之间传递信息，比如事务
`ID`、`Cookie` 等上下文相关信息。

## 代码示例

对于以下代码，`thread1` 中设置 `threadLocal` 为 1，而 `thread2` 设置 `threadLocal` 为 2。过了一段时间之后，`thread1` 读取 `threadLocal` 依然是 1，不受 `thread2` 的影响。

```

public class ThreadLocalExample {
    public static void main(String[] args) {
        ThreadLocal threadLocal = new ThreadLocal();
        Thread thread1 = new Thread(() -> {
            threadLocal.set(1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get());
            threadLocal.remove();
        });
        Thread thread2 = new Thread(() -> {
            threadLocal.set(2);
            threadLocal.remove();
        });
        thread1.start();
        thread2.start();
    }
}

```

## `ThreadLocl`的原理

每个 `Thread` 都有一个 `ThreadLocal.ThreadLocalMap` 对象。

```
/* ThreadLocal values pertaining to this thread. This map is maintained
 * by the ThreadLocal class. */
ThreadLocal.ThreadLocalMap threadLocals = null;
```

当调用一个 `ThreadLocal` 的 `set(T value)` 方法时，先得到当前线程的 `ThreadLocalMap` 对象，然后将 `ThreadLocal->value` 键值对插入到该 `Map` 中。

```
public void set(T value) {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
}

```

`get()` 方法类似。


```
public T get() {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null) {
            @SuppressWarnings("unchecked")
            T result = (T)e.value;
            return result;
        }
    }
    return setInitialValue();
}
```

`ThreadLocal` 从理论上讲并不是用来解决多线程并发问题的，因为根本不存在多线程竞争。


## 注意
在一些场景 (尤其是使用线程池) 下，由于 `ThreadLocal.ThreadLocalMap` 的底层数据结构导致 `ThreadLocal` 有内存泄漏的情况，应该尽可能在每次使用 `ThreadLocal` 后手动调用 `remove()`，以避免出现 `ThreadLocal` 经典的内存泄漏甚至是造成自身业务混乱的风险。
