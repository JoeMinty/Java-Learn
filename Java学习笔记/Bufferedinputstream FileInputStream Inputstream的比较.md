# BufferedInputStream FileInputStream InputStream的比较



## `BufferedInputStream`和`FileInputStream`定义



`BufferedInputStream`类相比`InputStream`类，提高了输入效率，增加了输入缓冲区的功能;

不带缓冲的操作，每读一个字节就要写入一个字节，由于涉及磁盘的`IO`操作相比内存的操作要慢很多，所以不带缓冲的流效率很低

带缓冲的流，可以一次读很多字节，但不向磁盘中写入，只是先放到内存里。等凑够了缓冲区大小的时候一次性写入磁盘，这种方式可以减少磁盘操作次数，速度就会提高很多。

`InputStream`流是指将字节序列从外设或外存传递到应用程序的流；`BufferedInputStream`流是指读取数据时，数据首先保存进入缓冲区，其后的操作直接在缓冲区中完成。



继承关系是这样的：

```
Java.lang.Object
   --Java.io.InputStrean
        --Java.io.FilterInputStream
              --Java.io.BufferedInputStream
```



`FileInputStream`是字节流，`BufferedInputStream`是字节缓冲流，使用`BufferedInputStream`读资源比`FileInputStream`读取资源的效率高（`BufferedInputStream`的`read`方法会读取尽可能多的字节），且`FileInputStream`对象的`read`方法会出现阻塞；



`BufferedInputStream`比`FileInputStream`多了一个缓冲区，执行`read`时先从缓冲区读取，当缓冲区数据读完时再把缓冲区填满。

当每次读取的数据量很小时，`FileInputStream`每次都是从硬盘读入，而`BufferedInputStream`大部分是从缓冲区读入。读取内存速度比读取硬盘速度快得多，因此`BufferedInputStream`效率高。

`BufferedInputStream`的默认缓冲区大小是`8192`字节。当每次读取数据量接近或远超这个值时，两者效率就没有明显差别了。



## `BufferedInputStream`和`FileInputStream`效率对比



在`FileOperator`类的`copyWithFileStream`方法实现了使用`FileInputStream`和`FileOutputStream`复制文件，`copyWithBufferedStream`方法实现了使用`BufferedInputStream`和`BufferedOutputStream`复制文件。



### 实验代码：

```
/*
 * Copyright (c) 2018 xxx.com. All Rights Reserved.
 */

package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author moons
 * @version 0
 */
public class FileOperator {

    /** buffer size in bytes */
    final static int BUFFER_SIZE = 100;

    /**
     * copy file using FileInputStream & FileOutputStream

     * @param src copy from
     * @param dest copy to

     * @return;
     */
    public static void copyWithFileStream(File src, File dest){
        FileInputStream input = null;
        FileOutputStream output = null;

        try {
            input = new FileInputStream(src);
            output = new FileOutputStream(dest);

            byte[] buffer = new byte[BUFFER_SIZE];
            int copySize;

            while ((copySize = input.read(buffer)) > 0){
                output.write(buffer, 0, copySize);
                output.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * copy file using BufferedInputStream & BufferedOutputStream

     * @param src copy from file
     * @param dest copy to file

     * @return;
     */
    public static void copyWithBufferedStream(File src, File dest){
        BufferedInputStream bufferedInput = null;
        BufferedOutputStream bufferedOutput = null;
        try {
            bufferedInput = new BufferedInputStream(new FileInputStream(src));
            bufferedOutput = new BufferedOutputStream(new FileOutputStream(dest));

            byte[] buffer = new byte[BUFFER_SIZE];
            int copySize;

            while ((copySize = bufferedInput.read(buffer)) > 0){
                bufferedOutput.write(buffer, 0, copySize);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedInput.close();
                bufferedOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class FileOperatorTest{
    public static void main(String args[]){
        File src = new File("test.txt");
        File dest = new File("copyTest.txt");

        try {
            if (!dest.exists()){
                dest.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //test copy using FileStream
        int startTime = System.currentTimeMillis();

        FileOperator.copyWithFileStream(src, dest);

        int endTime = System.currentTimeMillis();
        System.out.println("Copy file using FileStream takes : " + (endTime - startTime) + " ms.");

        //test copy using BufferedStream
        startTime = System.currentTimeMillis();

        FileOperator.copyWithBufferedStream(src, dest);

        endTime = System.currentTimeMillis();
        System.out.println("Copy file using BufferedStream takes : " + (endTime - startTime) + " ms.");
    }
}

```



### 运行结果：

测试文件大小约为`900M`，以下是在设定`BUFFER_SIZE`为不同值时的一次执行结果：

```
BUFFER_SIZE = 100
Copy file using FileStream takes: 42680 ms.
Copy file using BufferedStream takes: 2407 ms.

BUFFER_SIZE = 8192
Copy file using FileStream takes: 1689 ms.
Copy file using BufferedStream takes: 1654 ms.

BUFFER_SIZE = 1000000
Copy file using FileStream takes: 957 ms.
Copy file using BufferedStream takes: 929 ms.
```

### 对时间效率差异的解释：



`BufferedInputStream`比`FileInputStream`多了一个缓冲区，执行`read`时先从缓冲区读取，当缓冲区数据读完时再把缓冲区填满。

因此，当每次读取的数据量很小时，`FileInputStream`每次都是从硬盘读入，而`BufferedInputStream`大部分是从缓冲区读入。读取内存速度比读取硬盘速度快得多，因此`BufferedInputStream`效率高。`BufferedInputStream`的默认缓冲区大小是`8192`字节。当每次读取数据量接近或远超这个值时，两者效率就没有明显差别了。

`BufferedOutputStream`和`FileOutputStream`同理，差异更明显一些。



 