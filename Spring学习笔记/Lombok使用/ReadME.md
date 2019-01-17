# `Lombok`的简单使用



`Lombok`是一个可以通过简单的注解形式来帮助我们简化消除一些必须有但显得很臃肿的`Java`代码的工具，通过使用对应的注解，可以在编译源码的时候生成对应的方法。

然后引入`lombok`的`jar`包

```
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.16.14</version>
</dependency>
```
下面介绍几个常用的注解

##  `@Getter and @Setter`

你可以用`@Getter / @Setter`注释任何字段（当然也可以注释到类上的），让`lombok`自动生成默认的`getter / setter`方法。
默认生成的方法是`public`的，如果要修改方法修饰符可以设置`AccessLevel`的值，例如：`@Getter(access = AccessLevel.PROTECTED)`

## `@ToString`

生成`toString()`方法，默认情况下，它会按顺序（以逗号分隔）打印你的类名称以及每个字段。可以这样设置不包含哪些字段`@ToString(exclude = "id") / @ToString(exclude = {"id","name"})`
如果继承的有父类的话，可以设置`callSuper` 让其调用父类的`toString()`方法，例如：`@ToString(callSuper = true)`

## `@NoArgsConstructor, @RequiredArgsConstructor, @AllArgsConstructor`

### `@NoArgsConstructor`

`@NoArgsConstructor`生成一个无参构造方法。当类中有`final`字段没有被初始化时，编译器会报错，此时可用`@NoArgsConstructor(force = true)`，然后就会为没有初始化的`final`字段设置默认值 `0 / false / null`。对于具有约束的字段（例如`@NonNull`字段），不会生成检查或分配，因此请注意，正确初始化这些字段之前，这些约束无效。

### `@RequiredArgsConstructor`

`@RequiredArgsConstructor`会生成构造方法（可能带参数也可能不带参数），如果带参数，这参数只能是以`final`修饰的未经初始化的字段，或者是以`@NonNull`注解的未经初始化的字段
`@RequiredArgsConstructor(staticName = "of")`会生成一个`of()`的静态方法，并把构造方法设置为私有的

### `@AllArgsConstructor `

`@AllArgsConstructor `生成一个全参数的构造方法

## `@Data`

`@Data` 包含了 `@ToString、@EqualsAndHashCode、@Getter / @Setter和@RequiredArgsConstructor`的功能

## `@Synchronized`

给方法加上同步锁



## `@NonNull`

```
public class NonNullExample extends Something {
  private String name;
  
  public NonNullExample(@NonNull Person person) {
    super("Hello");
    this.name = person.getName();
  }
}
```

翻译成`Java`程序是：

```
public class NonNullExample extends Something {
  private String name;
  
  public NonNullExample(@NonNull Person person) {
    super("Hello");
    if (person == null) {
      throw new NullPointerException("person");
    }
    this.name = person.getName();
  }
}
```



## `@Cleanup`

```
public class CleanupExample {
  public static void main(String[] args) throws IOException {
    @Cleanup InputStream in = new FileInputStream(args[0]);
    @Cleanup OutputStream out = new FileOutputStream(args[1]);
    byte[] b = new byte[10000];
    while (true) {
      int r = in.read(b);
      if (r == -1) break;
      out.write(b, 0, r);
    }
  }
}
```



翻译成`Java`程序是：

```
public class CleanupExample {
  public static void main(String[] args) throws IOException {
    InputStream in = new FileInputStream(args[0]);
    try {
      OutputStream out = new FileOutputStream(args[1]);
      try {
        byte[] b = new byte[10000];
        while (true) {
          int r = in.read(b);
          if (r == -1) break;
          out.write(b, 0, r);
        }
      } finally {
        if (out != null) {
          out.close();
        }
      }
    } finally {
      if (in != null) {
        in.close();
      }
    }
  }
}
```

