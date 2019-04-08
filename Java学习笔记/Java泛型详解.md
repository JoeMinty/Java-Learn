# `Java`泛型详解

## 结论

总结一条规律：`"Producer Extends,Consumer Super":`

- `"Producer Extends"` --如果你需要一个只读`List`,用它来`produce T`，那么使用`? extends T`。
- `"Consumer Super"` --如果你需要一个只写`List`,用它来`consumer T`，那么使用`? super T`。
- 如果需要同时读取以及写入，那么我们就不能使用通配符了。

一些`Java`集合类的源码，可以发现通常情况下会将两者结合起来一起用，比如像下面这样：

```
public class Collections {
  public static <T> void copy(List<? super T> dest,List<? extends T> src) {
    for(int i=0;i<src.size();i++) {
      dest.set(i,src.get(i));
    }
  }
}

```

## 参考资料

http://www.importnew.com/24029.html#comment-746693
