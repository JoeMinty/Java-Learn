import java.util.concurrent.Semaphore;


public class AbnormalSemaphoreSample {
    public static void main(String[] args) throws InterruptedException {
        //permits 这个参数注意一下：允许初始可用许可证数量，目前应用中有-2个。
        Semaphore semaphore = new Semaphore(-2);

        //想要获取许可证的有10个线程，只有都获得了线程了，才能执行完。
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new MyWorker(semaphore));
            t.start();
        }
        System.out.println("Action...GO!");

        //semaphore.release();
        //线程抛出各种异常，都别忘了在finally中释放信号量；
        //如果释放的比获取的信号量还多，例如获取了2个，释放了5次，那么当前信号量就动态的增加为5了，要注意,这里之前一共有-2个，所以释放完会有3个证书。
        semaphore.release(5);
        System.out.println("Wait for permits off");

        //semaphore.availablePermits() 获取当前程序中剩余的证书数量
        while (semaphore.availablePermits()!=0) {
            Thread.sleep(100L);
        }
        System.out.println("Action...GO again!");

        //下面这个就要释放7个证书，才能把剩下的线程全部执行完
        semaphore.release(7);

    }
}
class MyWorker implements Runnable {

    private Semaphore semaphore;
    public MyWorker(Semaphore semaphore) {
        this.semaphore = semaphore;
    }
    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread()+" Executed!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
