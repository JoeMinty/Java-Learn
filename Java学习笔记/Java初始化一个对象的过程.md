# `Java`初始化一个对象的过程

## 代码引入

```
public class Test {

    private String a = "a";

    public Test() {
        test();
    }

    public void test() {
        System.out.println("Test");
        System.out.println(a);
    }

    public static void main(String[] args) {
        Test test = new Child();
    }
}

class Child extends  Test{
    private String a = "b";

    @Override
    public void test() {
        System.out.println("Child");
        System.out.println(a);
    }

    public Child() {}
}

```

输出如下：

```
Child
null

Process finished with exit code 0
```



### 原因分析

`Java`对象创建流程：

- 申请内存
- 初始化0值（初始化0值意思是对象内存设为零值）
- 执行构造方法

该段代码里面`child`继承父类`Test`，`java`里面子类对象创建的时候会自动调用父类的构造方法，也就是说`child`创建的时候先走父类`Test`的构造方法，然后`Test`构造方法执行`test()`，此时因为运行时类型为`Child`，所以进入了`child.test()`方法，这时候`child`的`a`属性覆盖了父类的`a`属性，并且此刻还没到子类`child`初始化内部成员的时候，所以此刻`child.a = null`。



**顺序如下：**

父类内部成员初始化 > 父类构造函数 > 子类成员初始化 > 子类构造函数



**注意：**

无论子类执行的是有参构造函数还是无参构造函数，都只会自动执行父类的无参构造函数





### 代码示例



```
public class Test {

    private String a = "a";

    public Test() {
        test();
    }

    {
        System.out.println("Test");
    }

    public void test() {

        System.out.println(a);
    }

    public static void main(String[] args) {
        Test test = new Child();
    }
}

class Child extends  Test{
    private String a = "b";

    {
        System.out.println("Child");
    }
    
    @Override
    public void test() {

        System.out.println(a);
    }

    public Child() {}
}

输出如下：

Test
null
Child

Process finished with exit code 0
```

