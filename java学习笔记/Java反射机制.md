# `Java`反射机制

什么试`java`反射机制？

当程序运行时，允许改变程序结构或变量类型，这种语言称为**动态语言**。我们认为`java`并不是动态语言，但是它却有一个非常突出的动态相关机制，俗称：**反射**。

IT行业里这么说，没有反射研究没有框架，现有的框架都是以反射为基础。在实际项目开发中，用的最多的是框架，填的最多的是类，反射这一概念就是将框架和类揉在一起的调和剂。所以反射才是接触项目开发的敲门砖！


## 常识

- `newInstance()`方法，它会返回`Class`类所真正指代的类的实例，这是什么意思呢？比如说声明`new Dog().getClass().newInstance()`和直接`new Dog()`是等价的。

- 反射的一大好处就是可以允许我们在运行期间获取对象的类型信息

- 什么是`Class`类？ 
在面向对象的世界里，万事万物皆是对象。
而在`java`语言中，`static`修饰的东西不是对象，但是它属于类。普通的数据类型不是对象，例如：`int a =5 `;它不是面向对象，但是他有包装类`Integer`或者封装类来弥补了它。
除了以上两种不是面向对象，其余的包括类也有它的面向对象，类是`java.lang.Class`的实例化对象（注意Class是大写）。也就是说：
`Class A{}`
当我创建了A类，那么类A本身就是一个对象，谁的对象？`java.lang.Class`的实例对象。

## 获取一个对象的反射类的三种方式

### 通过`getClass`方法

在`Java`中，每一个`Object`都有一个`getClass()`方法，通过`getClass`方法我们可以获取到这个对象对应的反射类：

```
String s = "moons";
Class<?> c = s.getClass();
```
### 通过`forName`方法

我们也可以调用`Class`类的静态方法`forName()`：
```
Class<?> c = Class.forName("java.lang.String");
```

### 使用`.class`
或者我们也可以直接使用`.class`：

```
Class<?> c = String.class;
```

## 反射的应用

### 获取类型信息

### 与注解相结合

### 解决泛型擦除




## 参考资料

http://www.importnew.com/tag/%E5%8F%8D%E5%B0%84

这几篇写的稍微清晰一点:

http://www.importnew.com/24042.html

http://www.importnew.com/21235.html

http://www.importnew.com/23560.html
