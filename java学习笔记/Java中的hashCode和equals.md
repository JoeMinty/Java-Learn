# `Java`中的`hashCode`和`equals`异同



## 一、`hashCode()`和`equals()`是什么？

`hashCode()`方法和`equals()`方法的作用其实一样，在`Java`里都是用来对比两个对象释放相等一致。



## 二、`hashCode()`和`equals()`的区别

下边从两个角度介绍了他们的区别：一个是性能，一个是可靠性。他们之间的主要区别也基本体现在这里。



**1、`equals()`既然已经能实现对比的功能了，为什么还要`hashCode()`呢？**

因为重写的`equals()`里一般比较的比较全面比较复杂，这样效率就比较低，而利用`hashCode()`进行对比，则只要生成一个`hash`值进行比较就可以了，效率很高。



**2、`hashCode()`既然效率这么高为什么还要`equals()`呢？**

因为`hashCode()`并不是完全可靠，有时候不同的对象他们生成的`hashcode`也会一样（生成`hash`值得公式可能存在的问题），所以`hashCode()`只能说是大部分时候可靠，并不是绝对可靠，所以我们可以得出：

- `equals()`相等的两个对象他们的`hashCode()`肯定相等，也就是用`equals()`对比是绝对可靠的。
- `hashCode()`相等的两个对象他们的`equals()`不一定相等，也就是`hashCode()`不是绝对可靠的。



## 三、`hashCode()`和`equals()`使用的注意事项



1、对于需要大量并且快速的对比的话如果都用`equals()`去做显然效率太低，所以解决方式是，每当需要对比的时候，首先用`hashCode()`去对比，如果`hashCode()`不一样，则表示这两个对象肯定不相等（也就是不必再用`equals()`去再对比了）,如果`hashCode()`相同，此时再对比他们的`equals()`，如果`equals()`也相同，则表示这两个对象是真的相同了，这样既能大大提高了效率也保证了对比的绝对正确性！



2、这种大量的并且快速的对象对比一般使用的`hash`容器中，比如`HashSet`,`HashMap`,`HashTable`等等，比如`HashSet`里要求对象不能重复，则他内部必然要对添加进去的每个对象进行对比，而他的对比规则就是像上面说的那样，先`hashCode()`，如果`hashCode()`相同，再用`equals()`验证，如果`hashCode()`都不同，则肯定不同，这样对比的效率就很高了。



3、然而`hashCode()`和`equals()`一样都是基本类`Object`里的方法，而和`equals()`一样，`Object`里`hashCode()`里面只是返回当前对象的地址，如果是这样的话，那么我们相同的一个类，`new`两个对象，由于他们在内存里的地址不同，则他们的`hashCode（）`不同，所以这显然不是我们想要的，所以我们必须重写我们类的`hashCode()`方法，即一个类，在`hashCode()`里面返回唯一的一个`hash`值，比如下面：



```
Class Person {
    int num;
    String name;
    
    public int hashCode() {
        return num*name.hashCode();
    }
    
}
```

由于标识这个类的是他的内部的变量`num`和`name`,所以我们就根据他们返回一个`hash`值，作为这个类的唯一`hash`值。

所以如果我们的对象要想放进`hashSet`，并且发挥`hashSet`的特性（即不包含一样的对象），则我们就要重写我们类的`hashCode()`和`equals()`方法了。像`String`,`Integer`等这种类内部都已经重写了这两个方法。

当然如果我们只是平时想对比两个对象 是否一致，则只重写一个`equals()`，然后利用`equals()`去对比也行的。

## 四、扩展


**1、阿里巴巴开发规范明确规定：**


- 只要重写 `equals`，就必须重写 `hashCode`；
- 因为 `Set` 存储的是不重复的对象，依据 `hashCode` 和 `equals` 进行判断，所以` Set` 存储的对象必须重写这两个方法；
- 如果自定义对象做为 `Map `的键，那么必须重写 `hashCode` 和 `equals`；
- `String` 重写了 `hashCode` 和 `equals `方法，所以我们可以非常愉快地使用 `String`对象作为 `key `来使用；

**2、什么时候需要重写？**


一般的地方不需要重载`hashCode`，只有当类需要放在`HashTable`、`HashMap`、`HashSet`等等`hash`结构的集合时才会重载`hashCode`。


**3、那么为什么要重载`hashCode`呢？**


如果你重写了`equals`，比如说是基于对象的内容实现的，而保留`hashCode`的实现不变，那么很可能某两个对象明明是“相等”，而`hashCode`却不一样。

这样，当你用其中的一个作为键保存到`hashMap`、`hashTable`或`hashSet`中，再以“相等的”找另一个作为键值去查找他们的时候，则根本找不到。



**4、为什么`equals()`相等，`hashCode`就一定要相等，而`hashCode`相等，却不要求`equals`相等?**


- 因为是按照`hashCode`来访问小内存块，所以`hashCode`必须相等。
- `HashMap`获取一个对象是比较`key`的`hashCode`相等和`equals`为`true`。

之所以`hashCode`相等，却可以`equal`不等，就比如`ObjectA`和`ObjectB`他们都有属性`name`，那么`hashCode`都以`name`计算，所以`hashCode`一样，但是两个对象属于不同类型，所以`equals`为`false`。



**5、为什么需要`hashCode`?**


- 通过`hashCode`可以很快的查到小内存块。
- 通过`hashCode`比较比`equals`方法块，当`get`时先比较`hashCode`，如果`hashCode`不同，直接返回`false`。
