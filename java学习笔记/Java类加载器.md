# `Java`类加载器

## `Java`类加载器的种类

- 引导类加载器（`bootstrap class loader`）：只加载 `JVM` 自身需要的类，包名为 `java`、`javax`、`sun` 等开头。
- 扩展类加载器（`extensions class loader`）：加载 `JAVA_HOME/lib/ext` 目录下或者由系统变量 `-Djava.ext.dir` 指定位路径中的类库。
- 应用程序类加载器（`application class loader`）：加载系统类路径 `java -classpath` 或 `-Djava.class.path` 下的类库。
- 自定义类加载器（`java.lang.classloder`）：继承 `java.lang.ClassLoader` 的自定义类加载器。

**注意：**
`-Djava.ext.dirs` 会覆盖 `Java` 本身的 `ext` 设置，造成 `JDK` 内建功能无法使用。
可以像下面这样指定参数：
```
-Djava.ext.dirs=./plugin:$JAVA_HOME/jre/lib/ext。
```

## `Java`类加载器之间的关系如下：

- 启动类加载器，`C++`实现，没有父类。
- 扩展类加载器（`ExtClassLoader`），`Java` 实现，父类加载器为 `null`。
- 应用程序类加载器（`AppClassLoader`），`Java` 实现，父类加载器为 `ExtClassLoader` 。
- 自定义类加载器，父类加载器为`AppClassLoader`。


## 参考资料

http://www.importnew.com/30567.html
