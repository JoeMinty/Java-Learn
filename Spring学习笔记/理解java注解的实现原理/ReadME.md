# 深入理解`Java`注解的实现原理

- **什么是注解**
- **注解的用途**
- **注解使用演示**
- **注解的实现原理**

##  **1、什么是注解**

注解也叫**元数据**，例如我们常见的`@Override`和`@Deprecated`，注解是`JDK1.5`版本开始引入的一个特性，用于对代码进行说明，可以对包、类、接口、字段、方法参数、局部变量等进行注解



一般常用的注解可以分为三类：

1. **一类是Java自带的标准注解**，包括`@Override`（标明重写某个方法）、`@Deprecated`（标明某个类或方法过时）和`@SuppressWarnings`（标明要忽略的警告），使用这些注解后编译器就会进行检查。
2. **一类为元注解，元注解是用于定义注解的注解**，包括`@Retention`（标明注解被保留的阶段）、`@Target`（标明注解使用的范围）、`@Inherited`（标明注解可继承）、`@Documented`（标明是否生成`javadoc`文档）
3. **一类为自定义注解**，可以根据自己的需求定义注解



## **2、注解的用途**



在看注解的用途之前，有必要简单的介绍下`XML`和注解区别，

- 注解：是一种分散式的元数据，与源代码紧绑定。

- `xml`：**是一种集中式的元数据，与源代码无绑定**

当然网上存在各种`XML`与注解的辩论哪个更好，这里不作评论和介绍，主要介绍一下注解的主要用途:

1. 生成文档，通过代码里标识的元数据生成`javadoc`文档。
2. 编译检查，通过代码里标识的元数据让编译器在编译期间进行检查验证。
3. 编译时动态处理，编译时通过代码里标识的元数据动态处理，例如动态生成代码。
4. 运行时动态处理，运行时通过代码里标识的元数据动态处理，例如使用反射注入实例



## **3、注解使用演示**



这边总共定义了4个注解来演示注解的使用



1. 定义一个可以注解在`Class`,`interface`,`enum`上的注解，
2. 定义一个可以注解在`METHOD`上的注解
3. 定义一个可以注解在`FIELD`上的注解
4. 定义一个可以注解在`PARAMETER`上的注解

具体代码见文件中



## **4、注解的实现原理**



以上只抽取了注解的其中几种类型演示，下面让我们一起来看看他们是怎么工作的

**让我们先看一下实现注解三要素：**

**1，注解声明;**

**2，使用注解的元素;**

**3，操作注解使其起作用(注解处理器)**



**注解声明**



首先我们让看一下`java`中的元注解（也就是上面提到的注解的注解），总共有4个如下：

- `@Target`,
- `@Retention`,
- `@Documented`,
- `@Inherited`

这4个元注解都是在`jdk`的`java.lang.annotation`包下面



### **`@Target`**



`Target`说明的是`Annotation`所修饰的对象范围，源码如下:



```
package java.lang.annotation;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Target {
    /**
     * Returns an array of the kinds of elements an annotation type
     * can be applied to.
     * @return an array of the kinds of elements an annotation type
     * can be applied to
     */
    ElementType[] value();
}
```

其中只有一个元素`ElementType`，再看看它的源码如下：

```
package java.lang.annotation;

public enum ElementType {
    /** Class, interface (including annotation type), or enum declaration */
    TYPE,

    /** Field declaration (includes enum constants) */
    FIELD,

    /** Method declaration */
    METHOD,

    /** Formal parameter declaration */
    PARAMETER,

    /** Constructor declaration */
    CONSTRUCTOR,

    /** Local variable declaration */
    LOCAL_VARIABLE,

    /** Annotation type declaration */
    ANNOTATION_TYPE,

    /** Package declaration */
    PACKAGE,

    /**
     * Type parameter declaration
     *
     * @since 1.8
     */
    TYPE_PARAMETER,

    /**
     * Use of a type
     *
     * @since 1.8
     */
    TYPE_USE
}


```

`ElementType`是一个枚举类定义注解可以作用的类型上，上面例子中演示了`TYPE`，`FIELD`，`METHOD`，`PARAMETER` 4种可以作用的目标。

### **`@Retention`**



定义了该`Annotation`被保留的时间长短：某些`Annotation`仅出现在源代码中，而被编译器丢弃；而另一些却被编译在`class`文件中；编译在`class`文件中的`Annotation`可能会被虚拟机忽略，而另一些在`class`被装载时将被读取（请注意并不影响`class`的执行，因为`Annotation`与`class`在使用上是被分离的）。使用这个元注解可以对 `Annotation`的"生命周期"限制



### **`@Documented`**



`@Documented`用于描述其它类型的`annotation`应该被作为被标注的程序成员的公共`API`，因此可以被例如`javadoc`此类的工具文档化。`Documented`是一个标记注解，没有成员



### **`@Inherited`**

`@Inherited `元注解是一个标记注解，`@Inherited`阐述了某个被标注的类型是被继承的。如果一个使用了`@Inherited`修饰的`annotation`类型被用于一个`class`，则这个`annotation`将被用于该`class`的子类



**注意：**

​	`@Inherited annotation`类型是被标注过的`class`的子类所继承。类并不从它所实现的接口继承`annotation`，方法并不从它所重载的方法继承`annotation`。

​	当`@Inherited annotation`类型标注的`annotation`的`Retention`是`RetentionPolicy.RUNTIME`，则反射`API`增强了这种继承性。如果我们使用`java.lang.reflect`去查询一个`@Inherited annotation`类型的`annotation`时，反射代码检查将展开工作：检查`class`和其父类，直到发现指定的`annotation`类型被发现，或者到达类继承结构的顶层。

​	这边针对这个例子方便理解，在以上`MyAnTargetType`注解类中增加`@Inherited`注解，如下：



```
package myannotation;

import java.lang.annotation.*;

/**
 * 定义一个可以注解在Class,interface,enum上的注解
 * 增加了@Inherited注解代表允许继承
 * @author moons
 * @date 2019年1月13日
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnTargetType {
    /**
     * 定义注解的一个元素 并给定默认值
     * @return
     */
    String value() default "我是定义在类,接口,枚举类上的注解元素value的默认值";
}
```



增加一个子类`ChildAnnotationTest`继承`AnnotationTest`测试如下：



```
package test;

import myannotation.MyAnTargetType;

/**
 * 增加一个子类继承AnnotationTest 演示@Inherited注解允许继承
 *
 * @author moons
 * @date 2019年1月13日
 */
public class ChildAnnotationTest extends AnnotationTest {
    public static void main(String[] args) {
        // 获取类上的注解MyAnTargetType

        MyAnTargetType t = ChildAnnotationTest.class.getAnnotation(MyAnTargetType.class);
        System.out.println("类上的注解值 === "+t.value());
    }
}
```



运行如下：



```
类上的注解值 === 我是定义在类,接口,枚举类上的注解元素value的默认值
```



说明已经获取到了父类`AnnotationTest`的注解了

如果`MyAnTargetType`去掉`@Inherited`注解运行则报错如下：



```
Exception in thread "main" java.lang.NullPointerException
	at test.ChildAnnotationTest.main(ChildAnnotationTest.java:16)
```



### **使用注解的元素**



使用注解没什么好说的就是在你需要的地方加上对应的你写好的注解就行



### **注解处理器**



这个是注解使用的核心了，前面我们说了那么多注解相关的，那到底`java`是如何去处理这些注解的呢

从`getAnnotation`进去可以看到`java.lang.class`实现了**`AnnotatedElement`**方法





```
MyAnTargetType t = AnnotationTest.class.getAnnotation(MyAnTargetType.class);
```



```
public final class Class<T> implements java.io.Serializable,
                              GenericDeclaration,
                              Type,
                              AnnotatedElement
```



`java.lang.reflect.AnnotatedElement `接口是所有程序元素（`Class`、`Method`和`Constructor`）的父接口，所以程序通过反射获取了某个类的`AnnotatedElement`对象之后，程序就可以调用该对象的如下四个个方法来访问Annotation信息：

　　**方法1：**`<T extends Annotation> T getAnnotation(Class<T> annotationClass)`: 返回改程序元素上存在的、指定类型的注解，如果该类型注解不存在，则返回`null`。
　　**方法2：**`Annotation[] getAnnotations()`:返回该程序元素上存在的所有注解。
　    **方法3：**`boolean isAnnotationPresent(Class<?extends Annotation> annotationClass)`:判断该程序元素上是否包含指定类型的注解，存在则返回`true`，否则返回`false`.
　　**方法4：**`Annotation[] getDeclaredAnnotations()`：返回直接存在于此元素上的所有注解。与此接口中的其他方法不同，该方法将忽略继承的注释。（如果没有注释直接存在于此元素上，则返回长度为零的一个数组。）该方法的调用者可以随意修改返回的数组；这不会对其他调用者返回的数组产生任何影响