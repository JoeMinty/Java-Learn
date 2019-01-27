# `Interface in Java 8(Default/Static methods)`

在 `jdk1.7`之前，在` Interface`中只能 `declare method`， 是不可以 `define method`的。所以在` jdk 1.8` 里面有什么不一样呢。



## `Default method`

在`1.7` 里我们这样定义一个 `interface `并且` declare` 一个` method` :

```
public interface Play{
    void show();
}
```



`show()`方法它默认也就是 `public abstract` 的。如果我们要定义一个 `abstract class `，像这样：

```
public abstract class AbstractPlay {
    public abstract  void show();

    public boolean isTrue(){
        return true;
    }
}
```

你可以声明一个 `abstract method`， 也可以 `define` 一个 `method`，都是 `OK` 的，但是在 `Interface`里面，只能 `declare` 一个 `abstract` 的 `method`，这其实就是 `interface` 和 `abstract class` 的主要区别。

但是到` Jdk 1.8`，有了 `Stream API` ,这是个什么鬼 
假如有这么一种情境，想扩展一个 `interface`（这个 `interface` 已经被很多地方实现用到了）的特性，如果是在这个原始的` interface `里面添加一个方法，让它变得更强大，你会怎么做？ 直接添加？ 如果是这样，意味着之前实现这个` interface`的` class` 就要全部重新实现这个接口，这样真的好么？这样不好。

所以` jdk1.8` 里面有一个 `default key word` 可以很友好的解决这个问题：

```
public interface TestDefaultMethod {
	void add();
	default  void show(){
	}
}
```

就像这样，可以在一个` interface`里面 `define` 一个 `method` ， `amazing `对不对。`ok `, 其实这里有点问题需要补充一下，我们知道在` java` 当中是不允许多重 `inheritance`的，因为多重 `inheritance`会` create` 一个叫做 `diamond problem` , 就是说如果`class C inherit class A `和 `class B` , 而 `A `和 `B` 中有一个完全一样的方法` show()`； 这个时候` class C` 一个` instance `叫做 `obj`， 当`obj.show()`的时候，它就不知道去` invoke` 哪个` show()`，对么。

当如果是两个` Interface A` 和` interface B` 的时候呢，并且在` A `和 `B`中没有用 `default `关键字` define`了两个完全相同的 `show()`；`class C implements `这两个 `interface`，那么在 `C `是必须要去 `rewrite show()`；因为 `A `和 `B `中只是 `declare `了 `show()`,并没有 `define show()`，所以这个时候是不会有冲突的。 然后` C `的` instance obj`就可以去` obj.show()`，这是很正常的情况，`1.7`之前都是这么干的。那么现在如果使用了 `default` 关键字 `define` 了两个完全相同的 `show()`，这个时候的情况就和前面的 `diamond problem `很像了，这个时候也会` error`的，所以这个问题在 `Jdk 1.8`里同样存在，解决这个问题的办法是， 在 `C` 中` rewrite show()`方法就好了。所以如果只是单个` Implements` 一个`interface `(这个`interface `中使用` default define `一个 `show()`),这个时候 `obj.show()`可以直接调用，不用在` C `中 `rewrite`，如果` implements` 两个 `Interface` ,并且两个`interface` 中含有使用 `default define `的两个相同的 `show()`方法，这个时候必须要在` C `中重新` rewrite show()`。

再把事情复杂点：

```
public interface I {
    default  void show(){
        System.out.println("A");
    }
}

public class C {
    public void show(){
        System.out.println("C");
    }
}

public class A extends C implements I{

}

```

`class A` 的 `instance obj`， `obj.show()`的时候会出现什么情况呢？答案是输出 `C` ，**因为`class` 比` interface `有更多的 `power`** ，这个时候 `class C` 中的 `show()` 会隐藏掉 `interface I `中的 `show()`。

还有一个小问题：如果在` Interface `中使用 `default define` 一个 `Object` 中已经存在的方法，可以么？

```
public interface I {
    default boolean equals (Object o){
        return true;
    }
}
```

记住，这样是不行的。



## `Static method`

在` jdk1.7`之前是不允许在 `interface `中使用` static method`的。可是` jdk1.8 `就可以了，像下面这样：

```
public interface StaticKeyWordTest {

    static void sayHi(){
        System.out.println("Hi static !");
    }

    static void main(String... args){
        StaticKeyWordTest.sayHi();
    }
}

```

