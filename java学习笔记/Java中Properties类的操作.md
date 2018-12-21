# `Java`中`Properties`类的操作



## 一、`Java Properties`类

 	`Java`中有个比较重要的类`Properties`（`Java.util.Properties`），主要用于读取`Java`的配置文件，各种语言都有自己所支持的配置文件，配置文件中很多变量是经常改变的，这样做也是为了方便用户，让用户能够脱离程序本身去修改相关的变量设置。

​	像`Python`支持的配置文件是`.ini`文件，同样，它也有自己读取配置文件的类`ConfigParse`，方便程序员或用户通过该类的方法来修改`.ini`配置文件。

​	在`Java`中，其配置文件常为`.properties`文件，格式为文本文件，文件的内容的格式是“键=值”的格式，文本注释信息可以用"#"来注释。

`Properties`类继承自`Hashtable`，如下：

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>



它提供了几个主要的方法：

- 1、`getProperty ( String key)`，用指定的键在此属性列表中搜索属性。也就是通过参数`key` ，得到 `key` 所对应的` value`。

- 2、`load ( InputStream inStream)`，从输入流中读取属性列表（键和元素对）。通过对指定的文件（比如说上面的` xx.properties `文件）进行装载来获取该文件中的所有键 - 值对。以供`getProperty ( String key) 来`搜索。

- 3、` setProperty ( String key, String value)` ，调用 `Hashtable `的方法` put` 。他通过调用基类的`put`方法来设置 键 - 值对。

- 4．` store ( OutputStream out, String comments)`，以适合使用 load 方法加载到 Properties 表中的格式，将此` Properties `表中的属性列表（键和元素对）写入输出流。与` load `方法相反，该方法将键 - 值对写入到指定的文件中去。

- 5、`clear ()`，清除所有装载的 键 - 值对。该方法在基类中提供。

 

## 二、`Java`读取`Properties`文件



`Java`读取`Properties`文件的方法有很多；

但是最常用的还是通过`java.lang.Class`类的`getResourceAsStream(String name)`方法来实现，如下可以这样调用：

`InputStream in = getClass().getResourceAsStream("资源Name");`

作为我们写程序的，用此一种足够。

或者下面这种也常用：

`InputStream in = new BufferedInputStream(new FileInputStream(filepath));`



## 三、相关实例



下面列举几个实例，加深对`Properties`类的理解和记忆。

我们知道，`Java`虚拟机（`JVM`）有自己的系统配置文件（`system.properties`），我们可以通过下面的方式来获取。

### 1、获取`JVM`的系统属性

```
import java.util.Properties;

public class ReadJVM {
    public static void main(String[] args) {
        Properties pps = System.getProperties();
        pps.list(System.out);
    }
}
```



**输出结果：**

```
-- listing properties --
java.runtime.name=Java(TM) SE Runtime Environment
sun.boot.library.path=C:\Program Files\Java\jdk1.8.0_181\jr...
java.vm.version=25.181-b13
java.vm.vendor=Oracle Corporation
java.vendor.url=http://java.oracle.com/
...
```



### 2、随便新建一个配置文件（`moons.properties`）

```
name=moons
Weight=160
Height=180
```



```
public class getProperties {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Properties pps = new Properties();
        pps.load(new BufferedInputStream(new FileInputStream("moons.properties")));
        Enumeration enum1 = pps.propertyNames();//得到配置文件的名字
        while(enum1.hasMoreElements()) {
            String strKey = (String) enum1.nextElement();
            String strValue = pps.getProperty(strKey);
            System.out.println(strKey + "=" + strValue);
        }
    }
}}
```



### 3、一个比较综合的实例

- 根据`key`读取`value`

- 读取`properties`的全部信息

- 写入新的`properties`信息



```
package com.moons.Demo;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

//关于Properties类常用的操作
public class TestProperties {
    //根据Key读取Value
    public static String GetValueByKey(String filePath, String key) {
        Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            pps.load(in);
            String value = pps.getProperty(key);
            System.out.println(key + " = " + value);
            return value;

        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //读取Properties的全部信息
    public static void GetAllProperties(String filePath) throws IOException {
        Properties pps = new Properties();
        InputStream in = new BufferedInputStream(new FileInputStream(filePath));
        pps.load(in);
        Enumeration en = pps.propertyNames(); //得到配置文件的名字

        while(en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = pps.getProperty(strKey);
            System.out.println(strKey + "=" + strValue);
        }

    }

    //写入Properties信息
    public static void WriteProperties (String filePath, String pKey, String pValue) throws IOException {
        Properties pps = new Properties();

        InputStream in = new BufferedInputStream(new FileInputStream(filePath));
        //从输入流中读取属性列表（键和元素对）
        pps.load(in);
        //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
        //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
        OutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
        pps.setProperty(pKey, pValue);
        //以适合使用 load 方法加载到 Properties 表中的格式，
        //将此 Properties 表中的属性列表（键和元素对）写入输出流
        pps.store(out, "Update " + pKey + " name");
    }

    public static void main(String [] args) throws IOException{
        //String value = GetValueByKey("moons.properties", "name");
        //System.out.println(value);
        //GetAllProperties("moons.properties");
        WriteProperties("moons.properties","long", "212");
    }
}
```

**输出结果：**

```
moons.properties中文件的数据为：

#Update long name
#Fri Dec 21 22:52:07 CST 2018
name=moons
Weight=160
long=212
Height=180
```

