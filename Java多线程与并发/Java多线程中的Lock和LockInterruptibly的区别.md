# `Java`多线程中的`Lock`和`LockInterruptibly`的区别

## `Lock`接口的线程请求锁的几个方法：

- `lock()`, 拿不到`lock`就不罢休，不然线程就一直`block`。 比较无赖的做法。
- `tryLock()`，马上返回，拿到`lock`就返回`true`，不然返回`false`。 比较潇洒的做法。
- 带时间限制的`tryLock()`，拿不到`lock`，就等一段时间，超时返回`false`。比较聪明的做法。

## 下面的`lockInterruptibly()`就稍微难理解一些:

先说说线程的打扰机制，每个线程都有一个打扰标志。这里分两种情况，

1. 线程在`sleep`或`wait,join`， 此时如果别的进程调用此进程的`interrupt()`方法，此线程会被唤醒并被要求处理`InterruptedException`；(`thread`在做`IO`操作时也可能有类似行为，见`java thread api`)

2. 此线程在运行中，则不会收到提醒。但是 此线程的 “打扰标志”会被设置， 可以通过`isInterrupted()`查看并 作出处理。

`lockInterruptibly()`和上面的第一种情况是一样的， 线程在请求`lock`并被阻塞时，如果被`interrupt`，则“此线程会被唤醒并被要求处理`InterruptedException`”。

## 几个简单的示例，验证一下：

### 1). `lock()`忽视`interrupt()`, 拿不到锁就 一直阻塞：

```
@Test
public void test3() throws Exception{
    final Lock lock=new ReentrantLock();
    lock.lock();
    Thread.sleep(1000);
    Thread t1=new Thread(new Runnable(){
        @Override
        public void run() {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" interrupted.");
        }
    });
    t1.start();
    Thread.sleep(1000);
    t1.interrupt();
    Thread.sleep(1000000);
}
```


### 2). `lockInterruptibly()`会响应打扰 并`catch`到`InterruptedException`

```
@Test
public void test4() throws Exception{
    final Lock lock=new ReentrantLock();
    lock.lock();
    Thread.sleep(1000);
    Thread t1=new Thread(new Runnable(){
        @Override
        public void run() {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName()+" interrupted.");
            }
        }
    });
    t1.start();
    Thread.sleep(1000);
    t1.interrupt();
    Thread.sleep(1000000);
}
```

### 3). 以下实验验证：当线程已经被打扰了（`isInterrupted()`返回`true`）。则线程使用`lock.lockInterruptibly()`，直接会被要求处理`InterruptedException`。

```
@Test
public void test5() throws Exception{
    final Lock lock=new ReentrantLock();
    Thread t1=new Thread(new Runnable(){
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+" interrupted.");
            }
        }
    });
    t1.start();
    t1.interrupt();
    Thread.sleep(10000000);
}   
```
