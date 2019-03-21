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
