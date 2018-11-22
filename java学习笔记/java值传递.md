### Java值传递

#### Java值传递概念

Java只有值传递

**栈：** 栈中存储的值，基本类型，引用类型，方法；

**堆：** 堆中存储的值，实例对象(`new` 的东西；`new Person()`)。凡是有`new`就有堆，`new`就是在堆中开辟空间产生对象。

**Java中的八种基本数据类型：**

Java语言提供了八种基本类型。六种数字类型（四个整数型（默认是`int` 型），两个浮点型（默认是`double` 型）），一种字符类型，还有一种布尔型。

`byte`、`short`、`int`、`long`、`float`、`double`、`boolean`、`char`

#### **实例1:**

```
class Person{
    int age;
}

public class TestValueJava {


    public static void aMethod(int num1,Person per1,String name){
        num1=11;
        per1.age =11;
        name="peng";
    }

    public static void main(String[] args){
        int num= 10; //基本类型变量
        String name="zhang";

        Person per=new Person(); //引用类型变量

        per.age=10;

        aMethod(num,per,name);

        System.out.println(num+","+per.age+","+name);


    }

}


输出如下：
10,11,zhang

Process finished with exit code 0


```

#### **小结1：**
1、如果a()方法中的基本类型变量（8个）x 传入到b()方法中，并在b()方法中修改了，则a()方法中的x保持不变；

2、如果a()方法中的引用类型变量x 传入到b()方法中，并在b()方法中修改了，则a()方法中的x 与b保持一致；

3、`int[] nums`;数组也会改变，因为数组也是也是引型变量。



#### 实例2：

```
public class StringDemo2 {

    public static void main(String[] args) {
        String str="A";
        StringBuffer sbB = new StringBuffer("B");
        StringBuffer sbC = new StringBuffer("C");

        change(str,sbB,sbC);
        System.out.println(str+","+sbB+","+sbC);
        

    }

    static void change(String str1,StringBuffer sbB1,StringBuffer sbC1){
        str1=str1+"1";
        sbB1.append("1");
        sbC1=new StringBuffer("c1");
    }
}

输出如下：
A,B1,C

Process finished with exit code 0

```

#### 小结2：

1、`String`:`final`类型的，当传入`change()`方法时，`main`中的`str`和`change()`中的`str1`时指向同一个`"A"`；但是，当`str1`的值发生改变是，因为`str1`是`final`类型的，因此`str1`是脱离原先的`"A"`。而指向新的值`"A1"`。



2、`sbB`是`StringBuffer`: 是普通的引用类型，如果有2个引用（`sbB`,`sbB1`），则该2个引用始终指向同一个值，`"B"`,任何一个引用对`"B"`进行修改，都会影响最终的值。



3、`sbC`虽然也是`StringBuffer`; 但是因为`new StringBuffer("c1")`,因此又产生了一个新的引用（与原来的引用已经断开了）
