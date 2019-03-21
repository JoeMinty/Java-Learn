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

用户态空间`（User Space）`和内核态空间`（Kernel Space）`，这是操作系统
层面的基本概念，操作系统内核、硬件驱动等运行在内核态空间，具有相对高的特权；而用户态空间，则是给普通应用和服务使用。

