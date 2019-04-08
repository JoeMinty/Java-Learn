# 更优雅地关闭资源 - `try-with-resource`及其异常抑制

## 为什么要关闭资源

在`Java`编程过程中，如果打开了外部资源（文件、数据库连接、网络连接等），我们必须在这些外部资源使用完毕后，手动关闭它们。因为外部资源不由`JVM`管理，无法享用`JVM`的垃圾回收机制，如果我们不在编程时确保在正确的时机关闭外部资源，就会导致外部资源泄露，紧接着就会出现文件被异常占用，数据库连接过多导致连接池溢出等诸多很严重的问题。

## 传统的`try cache finally`

在`java7`之前，我们可以使用`finally`来确保资源被关闭，例如:


```
static String readFirstLineFromFileWithFinallyBlock(String path) throws IOException {
  BufferedReader br = new BufferedReader(new FileReader(path));
  try {
    return br.readLine();
  } finally {
    if (br != null) 
        br.close();
  }
}
```

但是如果`br.readLine()`有异常，`br.close()`有异常抛出，由于一次只能抛一次异常，`try`的异常会被抑制，`finally`的异常会被抛出，导致异常丢失。以下是具体演示：

```
public class Connection implements AutoCloseable {

    public void sendData() throws Exception {
        throw new Exception("send data");
    }
    @Override
    public void close() throws Exception {
        throw new Exception("close");
    }
}

```

```
public class TryWithResource {

    public static void main(String[] args) {
        try{
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test()throws Exception{
        Connection conn = null;
        try{
            conn = new Connection();
            conn.sendData();
        } finally{
            if(conn !=null) {
                conn.close();
            }
        }
    }
}

```

运行结果如下：

```
java.lang.Exception: close
	at Connection.close(Connection.java:10)
	at TryWithResource.test(TryWithResource.java:24)
	at TryWithResource.main(TryWithResource.java:5)

Process finished with exit code 0
```

## 用`try-with-resources`实现

`JAVA 7`之后，我们盼来福音，再也不需要在`finally`面写一堆`if（xxx !=null）xxx.close()`的代码，只要资源实现了`AutoCloseable`或者`Closeable`接口，`try-with-resources`能帮其自动关闭。将以上的代码改写为`try-with-resources`

```
try (BufferedReader br = new BufferedReader(...) ) {
	//do sometinhg 
}
catch(IOException|XEception e) {
	// Handle it
}

```

```
public class TryWithResource {

    public static void main(String[] args) {
        try(Connection conn =new Connection()) {
            conn.sendData();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
```

运行结果如下：

```
java.lang.Exception: send data
	at Connection.sendData(Connection.java:6)
	at TryWithResource.main(TryWithResource.java:10)
	Suppressed: java.lang.Exception: close
		at Connection.close(Connection.java:10)
		at TryWithResource.main(TryWithResource.java:11)

Process finished with exit code 0

```

可以看到，代码编写也十分简洁。虽然我们没有用`finally`的代码块写明`close()`，但是程序仍然会自动执行`close()`方法，并且异常信息也没有丢失。信息中多了一个`Suppressed`的提示，附带上`close`的异常。这个异常其实由两个异常组成， 执行`close()`的异常是被`Suppressed`的异常。

## 原理是什么呢？

`try-with-resource`并不是`JVM`虚拟机的新增功能，只是`JDK`实现了一个语法糖，当你将上面代码反编译后会发现，其实对`JVM`虚拟机而言，它看到的依然是之前的写法

反编译了`TryWithResource`的`class`文件，代码如下:

```
public static void main(String[] args) {
        try {
            Connection conn = new Connection();
            Throwable localThrowable3 = null;
            try {
                conn.sendData();
            } catch (Throwable localThrowable1) {
                localThrowable3 = localThrowable1;
                throw localThrowable1;
            } finally {
                if (conn != null) if (localThrowable3 != null) try {
                    conn.close();
                } catch (Throwable localThrowable2) {
                    localThrowable3.addSuppressed(localThrowable2);
                }
                else conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

```

## 异常抑制

通过反编译的代码，大家可能注意到代码中有一处对异常的特殊处理：

`localThrowable3.addSuppressed(localThrowable2);`

这是`try-with-resource`语法涉及的另外一个知识点，叫做**异常抑制**。当对外部资源进行处理（例如读或写）时，如果遭遇了异常，且在随后的关闭外部资源过程中，又遭遇了异常，那么你`catch`到的将会是对外部资源进行处理时遭遇的异常，关闭资源时遭遇的异常将被“抑制”但不是丢弃，通过异常的`getSuppressed`方法，可以提取出被抑制的异常。

## `try-with-resources`也支持声明多个资源

```
public static void main(String[] args) {
        try (FileInputStream fin = new FileInputStream(new File("input.txt")); 
             FileOutputStream fout = new FileOutputStream(new File("out.txt"));
             GZIPOutputStream out = new GZIPOutputStream(fout)) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = fin.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

```
资源关闭会按声明时的相反顺序被执行。

## 总结

1、当一个外部资源的句柄对象实现了`AutoCloseable`接口，`JDK7`中便可以利用`try-with-resource`语法更优雅的关闭资源，消除板式代码。

2、`try-with-resource`时，如果对外部资源的处理和对外部资源的关闭均遭遇了异常，"关闭异常"将被抑制，"处理异常"将被抛出，但"关闭异常"并没有丢失，而是存放在"处理异常"的被抑制的异常列表中。


## 参考文献

http://www.importnew.com/30000.html
