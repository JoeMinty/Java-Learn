# 为什么`Spring`要支持`Autowire`（自动装配）



`Spring`引入`Autowire`（自动装配）机制就是为了解决`<bean>`标签下`<property>`标签过多的问题，`<property>`标签过多会引发三个问题：

- 如果一个`Bean`中要注入的对象过多，比如十几二十个（这是很正常的），那将导致`Spring`配置文件非常冗长，可读性与维护性差；
- 如果一个`Bean`中要注入的对象过多，配置麻烦且一不小心就容易出错；
- 如果一个对象被多个`Bean`引用，如果突然某一天该对象改变了`ID`，那么导致所有引用到的`Bean`都会对应进行修改。

因此，为了解决使用`<property>`标签注入对象过多的问题，`Spring`引入自动装配机制，简化开发者配置难度，降低`xml`文件配置大小。



## 使用`Autowire`去除`<property>`标签

下面来看一下使用`Autowire`去除`<property>`，`autowire`配置有两点处：

- 可以配置在`<beans>`根标签下，表示对全局`<bean>`起作用，属性名为`default-autowire`
- 可以配置在`<bean>`标签下，表示对当前`<bean>`起作用，属性名为`autowire`

通常都是在`<beans>`根标签下配置自动装配比较多，`default-autowire`有四种取值：

- `no`：默认，即不进行自动装配，每一个对象的注入比如依赖一个`<property>`标签
- `byName`：按照`beanName`进行自动装配，使用`setter`注入
- `byType`：按照`bean`类型进行自动装配，使用`setter`注入
- `constructor`：与`byType`差不多，不过最终属性通过构造函数进行注入



这里研究的是去除`<property>`标签，因此第一种不管；`constructor`装配不太常用，因此这里也不管，重点看最常用的`byName`与`byType`，至于具体使用哪种根据自己的业务特点进行相应的设置。

首先看一下`byName`，`byName`意为在`Spring`配置文件中查询`beanName`与属性名一致的`bean`并进行装配，若类型不匹配则报错，`autowire.xml`如果使用`byName`进行属性装配。



接着看一下`byType`的自动装配形式，`byType`意为在`Spring`配置文件中查询与属性类型一致的`bean`并进行装配，若有多个相同类型则报错。



## `byType`装配出现多个相同类型的`bean`及解决方案

​	如果通过`byType`方式为`animal`进行装配却找到了两个符合要求的`bean`，分别为`tiger`与`lion`，这导致了没有唯一的`bean`可以对`animal`进行装配。
​	
​	这个问题有两种解决方案，假如现在我要装配的是`lion`这个`bean`，第一种解决方案是将不需要进行自动装配的`bean`进行排除，对不需要进行自动装配的`bean`设置属性`autowire-candidate="false"`即可;

​	`candidate`顾名思义，即候选人的意思，`autowire-candidate="false"`即这个`bean`我不想让它作为自动装配的候选者，既然`tiger`不是自动装配的候选者，那么`animal`类型在`Spring`容器中能自动装配的也就只有一个`lion`了，`Spring`自动装配`lion`，不会有问题。

​	第一种思路是排除那些不需要作为自动装配候选者的`bean`，第二种思路就从相反逻辑出发，设置当发现有多个候选者的时候优先使用其中的哪个候选者，对要作为自动装配候选者的`bean`设置`primary="true"`即可。