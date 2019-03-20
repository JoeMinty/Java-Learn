# `Java`中的`String.intern()`方法



## 前言

在学习《深入理解Java虚拟机》的书时，又看到了2-7对`String.inter()`返回引用测试的章节。大体内容如下：

```
public class RuntimeConstanPoolOOM {
    public static void main(String[] args) {
        String str1=new StringBuilder("计算机").append("软件").toString();

        System.out.println(str1.intern() == str1);

        String str2=new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
```



这段代码在`JDK 1.6`中运行，会得到两个`false`,而在`JDK 1.7`中运行，会得到一个`true`和一个`false`。产生差异的原因是：在`JDK 1.6`中，`intern()`方法会把首次遇到的字符串实例复制到永久代中，返回的也是永久代中这个字符串实例的引用，而由`StringBuilder`创建的字符串实例在堆上，所以必然不是同一个引用，将返回`false`。而在`JDK 1.7`(以及部分其他虚拟机，例如` JRockit`)的`intern()`实现不会再复制实例，只是在常量池中记录首次出现的实例引用，因此`intern()`返回的引用和由`StringBuilder`创建的那个字符串实例是同一个。对`str2`比较返回`false`是因为`"java"`这个字符串在执行`StringBuilder.toString()`之前已经出现过，字符串常量池中已经有它的引用了，不符合"首次出现的原则"，而"计算机软件"这个字符串则是首次出现的，因此返回`true`。



## 加深理解



### 一、

`new String`都是在堆上创建字符串对象。当调用 `intern() `方法时，编译器会将字符串添加到常量池中（`StringTable`维护），并返回指向该常量的引用。 



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/java%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/Java%E5%AD%A6%E4%B9%A0%E5%9B%BE%E7%89%87/StringIntern01.png" />
</div>

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/java%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/Java%E5%AD%A6%E4%B9%A0%E5%9B%BE%E7%89%87/StringIntern02.png" />
</div>



### 二、

通过字面量赋值创建字符串（如：`String str="twm"`）时，会先在常量池中查找是否存在相同的字符串，若存在，则将栈中的引用直接指向该字符串；若不存在，则在常量池中生成一个字符串，再将栈中的引用指向该字符串。



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/java%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/Java%E5%AD%A6%E4%B9%A0%E5%9B%BE%E7%89%87/StringIntern03.png" />
</div>



### 三、

常量字符串的`"+"`操作，编译阶段直接会合成为一个字符串。如`String str="JA"+"VA"`，在编译阶段会直接合并成语句`String str="JAVA"`，于是会去常量池中查找是否存在`"JAVA"`,从而进行创建或引用



### 四、

对于`final`字段，编译期直接进行了常量替换（而对于非`final`字段则是在运行期进行赋值处理的）。 

```
final String str1="ja"; 
final String str2="va"; 
String str3=str1+str2; 
```

在编译时，直接替换成了`String str3="ja"+"va"`，根据第三条规则，再次替换成`String str3="JAVA"`



### 五、



常量字符串和变量拼接时（如：`String str3=baseStr + "01";`）会调用`StringBuilder.append()`在堆上创建新的对象。



###  六、

`JDK 1.7`后，`intern`方法还是会先去查询常量池中是否有已经存在，如果存在，则返回常量池中的引用，这一点与之前没有区别，区别在于，如果在常量池找不到对应的字符串，则不会再将字符串拷贝到常量池，而只是在常量池中生成一个对原字符串的引用。简单的说，就是往常量池放的东西变了：原来在常量池中找不到时，复制一个副本放到常量池，`1.7`后则是将在堆上的地址引用复制到常量池。 



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/java%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/Java%E5%AD%A6%E4%B9%A0%E5%9B%BE%E7%89%87/StringIntern04.png" />
</div>



举例说明：

```
String str2 = new String("str")+new String("01");
str2.intern();
String str1 = "str01";
System.out.println(str2==str1);
```



在`JDK 1.7`下，当执行`str2.intern();`时，因为常量池中没有`"str01"`这个字符串，所以会在常量池中生成一个对堆中的`"str01"`的**引用(注意这里是引用 ，就是这个区别于`JDK 1.6`的地方。在`JDK1.6`下是生成原字符串的拷贝)**，而在进行`String str1 = "str01";`字面量赋值的时候，常量池中已经存在一个引用，所以直接返回了该引用，因此`str1`和`str2`都指向堆中的同一个字符串，返回`true`。

```
String str2 = new String("str")+new String("01");
String str1 = "str01";
str2.intern();
System.out.println(str2==str1);
```


将中间两行调换位置以后，因为在进行字面量赋值（`String str1 = “str01″`）的时候，常量池中不存在，所以`str1`指向的常量池中的位置，而`str2`指向的是堆中的对象，再进行`intern`方法时，对`str1`和`str2`已经没有影响了，所以返回`false`。



## 常见试题解答

有了对以上的知识的了解，我们现在再来看常见的面试或笔试题就很简单了： 

- Q：下列程序的输出结果：

```
String s1 = "abc"; 
String s2 = "abc"; 
System.out.println(s1 == s2); 
```

- A：`true`，均指向常量池中对象。



- Q：下列程序的输出结果： 

```
String s1 = new String("abc"); 
String s2 = new String("abc"); 
System.out.println(s1 == s2); 
```

- A：`false`，两个引用指向堆中的不同对象。



- Q：下列程序的输出结果： 

```
String s1 = "abc"; 
String s2 = "a"; 
String s3 = "bc"; 
String s4 = s2 + s3; 
System.out.println(s1 == s4); 
```

- A：`false`，因为`s2+s3`实际上是使用`StringBuilder.append`来完成，会生成不同的对象。



- Q：下列程序的输出结果：

```
String s1 = "abc"; 
final String s2 = "a"; 
final String s3 = "bc"; 
String s4 = s2 + s3; 
System.out.println(s1 == s4); 
```

- A：`true`，因为`final`变量在编译后会直接替换成对应的值，所以实际上等于`s4="a"+"bc"`，而这种情况下，编译器会直接合并为`s4="abc"`，所以最终`s1==s4`。



- Q：下列程序的输出结果： 

```
String s = new String("abc"); 
String s1 = "abc"; 
String s2 = new String("abc"); 
System.out.println(s == s1.intern()); 
System.out.println(s == s2.intern()); 
System.out.println(s1 == s2.intern()); 
```

- A：`false，false，true`。

