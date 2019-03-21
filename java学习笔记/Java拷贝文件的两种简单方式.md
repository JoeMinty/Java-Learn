# `Java`拷贝文件的两种简单方式


## 代码示例

```


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class Main01 {

    public static void copyFileByStream(File source,File dest) throws IOException {

        try(InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = is.read(buffer)) > 0 ) {
                os.write(buffer,0,length);
            }

        }
    }

    public static void copyFileByChannel(File source,File dest) throws IOException {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();FileChannel targetChannel = new FileOutputStream(dest).getChannel();) {
            for(long count = sourceChannel.size();count>0;) {
                long transferred = sourceChannel.transferTo(sourceChannel.position(),count,targetChannel);

                sourceChannel.position(sourceChannel.position() + transferred);
                count -= transferred;
            }
        }
    }
}



```

## 两种实现的效率

对于`Copy`的效率，这个其实与操作系统和配置等情况相关，总体上来说，`NIO transferTo\From`的方式可能更快，因为它更能利用现代操作系统底层机制，避免不必要拷贝和上下文切换。

### 上下文切换

用户态空间（`User Space`）和内核态空间（`Kernel Space`），这是操作系统
层面的基本概念，操作系统内核、硬件驱动等运行在内核态空间，具有相对高的特权；而用户态空间，则是给普通应用和服务使用。



当我们使用输入输出流进行读写时，实际上是进行了多次上下文切换，比如应用读取数据时，先在内核态将数据从磁盘读取到内核缓存，再切换到用户态将数据从内核缓存读取到用户缓存。

写入操作也是类似，仅仅是步骤相反，参考图如下：

 <div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/java%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/Java%E5%AD%A6%E4%B9%A0%E5%9B%BE%E7%89%87/%E8%BE%93%E5%85%A5%E8%BE%93%E5%87%BA%E6%B5%81%E5%A4%8D%E5%88%B6.PNG" />
</div>







所以，这种方式会带来一定的额外开销，可能会降低 `IO`效率。
而基于` NIO transferTo `的实现方式，在` Linux `和` Unix `上，则会使用到**零拷贝技术**，数据传输
并不需要用户态参与，省去了上下文切换的开销和不必要的内存拷贝，进而可能提高应用拷贝性
能。



**注意**:`transferTo` 不仅仅是可以用在文件拷贝中，与其类似的，例如读取磁盘文件，然后
进行 `Socket`发送，同样可以享受这种机制带来的性能和扩展性提高。`transferTo` 的传输过程是：



 <div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/java%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/Java%E5%AD%A6%E4%B9%A0%E5%9B%BE%E7%89%87/%E9%9B%B6%E6%8B%B7%E8%B4%9D%E6%8A%80%E6%9C%AF.PNG" />
</div>
