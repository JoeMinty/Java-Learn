# `Java`反射机制

反射的一大好处就是可以允许我们在运行期间获取对象的类型信息

应用反射机制之前，首先我们先来看一下如何获取一个对象对应的反射类`Class`，在`Java`中我们有三种方法可以获取一个对象的反射类。

## 常识

`newInstance()`方法，它会返回`Class`类所真正指代的类的实例，这是什么意思呢？比如说声明`new Dog().getClass().newInstance()`和直接`new Dog()`是等价的。


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
