## Java的序列化和反序列化

### 序列化

**序列化**：对象的寿命通常随着生成该对象的程序的终止而终止，有时候需要把在内存中的各种对象的状态（也就是实例变量，不是方法）保存下来，并且可以在需要时再将对象恢复。虽然你可以用你自己的各种各样的方法来保存对象的状态，但是Java给你提供一种应该比你自己的好的保存对象状态的机制，那就是序列化。

**总结**：Java 序列化技术可以使你将一个对象的状态写入一个Byte 流里（序列化），并且可以从其它地方把该Byte 流里的数据读出来（反序列化）。

[序列化的官方文档](https://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html)

### 序列化的用途

- 想把的内存中的对象状态保存到一个文件中或者数据库中时候
- 想把对象通过网络进行传播的时候



### 如何序列化

只要一个类实现Serializable接口，那么这个类就可以序列化了。

例如有一个 Person类，实现了Serializable接口，那么这个类就可以被序列化了。

```
class Person implements Serializable{   
    private static final long serialVersionUID = 1L; //一会就说这个是做什么的
    String name;
    int age;
    public Person(String name,int age){
        this.name = name;
        this.age = age;
    }   
    public String toString(){
        return "name:"+name+"\tage:"+age;
    }
}
```

通过`ObjectOutputStream` 的`writeObject()`方法把这个类的对象写到一个地方（文件），再通过`ObjectInputStream` 的`readObject()`方法把这个对象读出来。

```
import java.io.*;

public class WriteReadFile {

    public static void main(String[] args){
        File file = new File("out.txt");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(fos);
                Person person = new Person("moons", 24);
                System.out.println(person);
                oos.writeObject(person);            //写入对象
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    oos.close();
                } catch (IOException e) {
                    System.out.println("oos关闭失败："+e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件："+e.getMessage());
        } finally{
            try {
                fos.close();
            } catch (IOException e) {
                System.out.println("fos关闭失败："+e.getMessage());
            }
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(fis);
                try {
                    Person person = (Person)ois.readObject();   //读出对象
                    System.out.println(person);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    ois.close();
                } catch (IOException e) {
                    System.out.println("ois关闭失败："+e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件："+e.getMessage());
        } finally{
            try {
                fis.close();
            } catch (IOException e) {
                System.out.println("fis关闭失败："+e.getMessage());
            }
        }
    }
}

```

输出结果为：

```
name:moons	age:24
name:moons	age:24
```

结果完全一样。如果我把Person类中的implements Serializable 去掉，Person类就不能序列化了。此时再运行上述程序，就会报java.io.NotSerializableException异常。



**注意：**



如果序列中的属性包括对象属性，需要如下处理。

```
class Person implements Serializable {
	...
    String name;
    int age;
    Dog dog; //其中Dog是一个类，那么如果想要实例化Dog这个属性，则需要让Dog这个类也实现Serializable接口

    public Dog getDog() {
        return dog;
    }
    ...
}
```



### serialVersionUID

注意到上面程序中有一个 `serialVersionUID` ，实现了`Serializable`接口之后，程序就会提示你增加一个 `serialVersionUID`，虽然不加的话上述程序依然能够正常运行。

序列化 ID 在 程序下提供了两种生成策略

- 一个是固定的 `1L`
- 一个是随机生成一个不重复的 `long` 类型数据（实际上是使用 `JDK` 工具，根据类名、接口名、成员方法及属性等来生成）

简单看一下 `Serializable`接口的说明:

如果用户没有自己声明一个`SerialVersionUID`,接口会默认生成一个`SerialVersionUID` ,但是强烈建议用户自定义一个`SerialVersionUID`,因为默认的`SerialVersinUID`对于`class`的细节非常敏感，反序列化时可能会导致`InvalidClassException`这个异常。



下面给出一个例子看一下，如果使用默认的`SerialVersinUID`会出现什么问题：

```
import java.io.Serializable;

class Person implements Serializable {
	//先把这个注释掉，使用默认的SerialVersionUID
    //private static final long serialVersionUID = 2L; 
    String name;
    int age;
   
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "name:" + name + "\tage:" + age;
    }
}
```

然后去序列化和反序列化它 

```
import java.io.*;

public class WriteReadFile {

    public static void main(String[] args){
        File file = new File("out.txt");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(fos);
                Person person = new Person("moons", 24);
                System.out.println(person);
                oos.writeObject(person);            //写入对象
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    oos.close();
                } catch (IOException e) {
                    System.out.println("oos关闭失败："+e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件："+e.getMessage());
        } finally{
            try {
                fos.close();
            } catch (IOException e) {
                System.out.println("fos关闭失败："+e.getMessage());
            }
        }

        FileInputStream fis = null;
        try {

            fis = new FileInputStream(file);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(fis);
                try {
                    Person person = (Person)ois.readObject();   //读出对象
                    System.out.println(person);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    ois.close();
                } catch (IOException e) {
                    System.out.println("ois关闭失败："+e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件："+e.getMessage());
        } finally{
            try {
                fis.close();
            } catch (IOException e) {
                System.out.println("fis关闭失败："+e.getMessage());
            }
        }
    }
}

```

结果输出如下，正常序列化反序列化：

```
name:moons	age:24
name:moons	age:24
```

如果我们先正常序列化，然后在反序列化之前修改了Person类会怎样呢？

```
import java.io.Serializable;

class Person implements Serializable {
    //private static final long serialVersionUID = 2L; //一会就说这个是做什么的
    String name;
    int age;
   	//添加一个country字段
    String country;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "name:" + name + "\tage:" + age;
    }
}
```

 再重新运行WriteReadFile程序的反序列化的过程，把序列化过程先注释掉，运行结果报错如下：

```
java.io.InvalidClassException: learn.Person; local class incompatible: stream classdesc serialVersionUID = -4107076385568707100, local class serialVersionUID = 5729179741812731226
	at java.io.ObjectStreamClass.initNonProxy(ObjectStreamClass.java:699)
	at java.io.ObjectInputStream.readNonProxyDesc(ObjectInputStream.java:1885)
	at java.io.ObjectInputStream.readClassDesc(ObjectInputStream.java:1751)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2042)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1573)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:431)
	at learn.WriteReadFile.main(WriteReadFile.java:48)

```

可以看到，当我们修改`Person`类的时候，`Person`类对应的`SerialversionUID`也变化了，而序列化和反序列化就是通过对比其`SerialversionUID`来进行的，一旦`SerialversionUID`不匹配，反序列化就无法成功。在实际的生产环境中，我们可能会建一系列的中间`Object`来反序列化我们的`pojo`，为了解决这个问题，我们就需要在实体类中自定义`SerialversionUID`。 

```
private static final long serialVersionUID = -5809782578272943999L;
```

这样不管我们序列化之后如何更改我们的`Person`（不删除原有字段），最终都可以反序列化成功。



### 静态变量序列化

串行化只能保存对象的非静态成员交量，不能保存任何的成员方法和静态的成员变量，而且串行化保存的只是变量的值，对于变量的任何修饰符都不能保存。

如果把Person类中的name定义为static类型的话，试图重构，就不能得到原来的值，只能得到null。说明对静态成员变量值是不保存的。这其实比较容易理解，序列化保存的是对象的状态，静态变量属于类的状态，因此 序列化并不保存静态变量。



### transient使用小结

- 一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。
- transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。变量如果是用户自定义类变量，则该类需要实现Serializable接口。
- 被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化。

第三点可能有些人很迷惑，因为发现在`Person`类中的`name`字段前加上`static`关键字后，程序运行结果依然不变，即`static`类型的`name`也读出来为“moons”了，这不与第三点说的矛盾吗？实际上是这样的：第三点确实没错（一个静态变量不管是否被`transient`修饰，均不能被序列化），反序列化后类中`static`型变量`name`的值为当前`JVM`中对应`static`变量的值，这个值是`JVM`中的不是反序列化得出的，下面我来简单证明下：

```
import java.io.Serializable;

class Person implements Serializable {
    private static final long serialVersionUID = 2L; //一会就说这个是做什么的
    static String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "name:" + name + "\tage:" + age;
    }
}
```

```
import java.io.*;

public class WriteReadFile {

    public static void main(String[] args){
        File file = new File("out.txt");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(fos);
                Person person = new Person("moons", 24);
                System.out.println(person);
                oos.writeObject(person);            //写入对象
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    oos.close();
                } catch (IOException e) {
                    System.out.println("oos关闭失败："+e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件："+e.getMessage());
        } finally{
            try {
                fos.close();
            } catch (IOException e) {
                System.out.println("fos关闭失败："+e.getMessage());
            }
        }

        FileInputStream fis = null;
        try {
            Person.name="peng";
            fis = new FileInputStream(file);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(fis);
                try {
                    Person person = (Person)ois.readObject();   //读出对象
                    System.out.println(person);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    ois.close();
                } catch (IOException e) {
                    System.out.println("ois关闭失败："+e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件："+e.getMessage());
        } finally{
            try {
                fis.close();
            } catch (IOException e) {
                System.out.println("fis关闭失败："+e.getMessage());
            }
        }
    }
}

```

输出如下：

```
name:moons	age:24
name:peng	age:24

Process finished with exit code 0
```

这说明反序列化后类中`static`型变量`name`的值为当前`JVM`中对应`static`变量的值，为修改后`peng`，而不是序列化时的值`moons`。



### transient使用细节——被transient关键字修饰的变量真的不能被序列化吗？

```
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * @descripiton Externalizable接口的使用
 *
 * @author moons
 * @date 2018-10-19
 *
 */
public class ExternalizableTest implements Externalizable {

    private transient String content = "是的，我将会被序列化，不管我是否被transient关键字修饰";

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(content);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        content = (String) in.readObject();
    }

    public static void main(String[] args) throws Exception {

        ExternalizableTest et = new ExternalizableTest();
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(
                new File("test")));
        out.writeObject(et);

        ObjectInput in = new ObjectInputStream(new FileInputStream(new File(
                "test")));
        et = (ExternalizableTest) in.readObject();
        System.out.println(et.content);

        out.close();
        in.close();
    }
}
```



`content`变量会被序列化吗？好吧，我把答案都输出来了，是的，运行结果就是：

------

是的，我将会被序列化，不管我是否被transient关键字修饰

------

这是为什么呢，不是说类的变量被`transient`关键字修饰以后将不能序列化了吗？

我们知道在`Java`中，对象的序列化可以通过实现两种接口来实现，若实现的是`Serializable`接口，则所有的序列化将会自动进行，若实现的是`Externalizable`接口，则没有任何东西可以自动序列化，需要在`writeExternal`方法中进行手工指定所要序列化的变量，这与是否被`transient`修饰无关。因此第二个例子输出的是变量`content`初始化的内容，而不是`null`。

### 序列化中的继承问题

- 当一个父类实现序列化，子类自动实现序列化，不需要显式实现`Serializable`接口。
- 一个子类实现了 `Serializable` 接口，它的父类都没有实现 `Serializable` 接口，要想将父类对象也序列化，就需要让父类也实现`Serializable` 接口。

第二种情况中：如果父类不实现 `Serializable`接口的话，就需要有默认的无参的构造函数。这是因为一个 Java 对象的构造必须先有父对象，才有子对象，反序列化也不例外。在反序列化时，为了构造父对象，只能调用父类的无参构造函数作为默认的父对象。因此当我们取父对象的变量值时，它的值是调用父类无参构造函数后的值。在这种情况下，在序列化时根据需要在父类无参构造函数中对变量进行初始化，否则的话，父类变量值都是默认声明的值，如 `int` 型的默认是 0，`string` 型的默认是` null`。

例如：

```
import java.io.Serializable;

class People{
    int num;
    public People(){}           //默认的无参构造函数，没有进行初始化
    public People(int num){     //有参构造函数
        this.num = num;
    }
    public String toString(){
        return "num:"+num;
    }
}
class Person extends People implements Serializable {

    private static final long serialVersionUID = 1L;

    String name;
    int age;

    public Person(int num,String name,int age){
        super(num);             //调用父类中的构造函数
        this.name = name;
        this.age = age;
    }
    public String toString(){
        return super.toString()+"\tname:"+name+"\tage:"+age;
    }
}
```

在一端写出对象的时候

```
    //调用带参数的构造函数num=20,name = "moons",age =22
    Person person = new Person(20,"moons", 24); 
    System.out.println(person);
    oos.writeObject(person);                  //写出对象
```

输出为：

```
num:20	name:moons	age:24
num:0	name:moons	age:24

Process finished with exit code 0
```

发现由于父类中无参构造函数并没有对`num`初始化，所以`num`使用默认值为0。



### 总结

序列化给我们提供了一种技术，用于保存对象的变量。以便于传输。虽然也可以使用别的一些方法实现同样的功能，但是`java`给我们提供的方法使用起来是非常方便的。以上仅仅是我的一些理解以及网上一些博客的总结，仅是做记录之用，不足之处还请指正。
