
# `Java`多线程`Exchanger`介绍

`Exchanger`,从名字上理解就是交换。`Exchanger`用于在两个线程之间进行数据交换。线程会阻塞在`Exchanger`的`exchange`方法上，直到另外一个线程也到了同一个`Exchanger`的`exchange`方法时，二者进行交换，然后两个线程会继续执行自身相关的代码。

## 代码示例


```
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerDemo {

    public static void main(String[] args) {
        final Exchanger<List<Integer>> exchanger = new Exchanger<>();

        new Thread(){
            @Override
            public void run() {
                List<Integer> l = new ArrayList<>(3);
                l.add(1);
                l.add(2);
                l.add(3);

                try {

                    Thread.sleep(4000);
                    l = exchanger.exchange(l);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Thread1: " + l);
            }
        }.start();


        new Thread(){
            @Override
            public void run() {
                List<Integer> l = new ArrayList<>(2);
                l.add(4);
                l.add(5);

                try {
                    l = exchanger.exchange(l);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Thread2: " + l);
            }
        }.start();
    }
}


```
