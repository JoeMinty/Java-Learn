# `Spring`中`bean`的`Scope`



​	`Spring`容器中的`bean`具备不同的`Scope`，最开始只有`singleton`和`prototype`，但是在2.0之后，又引入了三种类型：`request`、`session`和`global session`，不过这三种类型只能在`Web`应用中使用。

​	在定义`bean`的时候，可以通过指定`<bean>`的`singleton`或者`Scope`属性来指定相应对象的`Scope`，例如：



```
<bean id="testMock" class="org.test.javadu.TestMock" scope="prototype"/>
```

或者

```
<bean id="testMock" class="org.test.javadu.TestMock" singleton="false"/>
```

 <div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Spring%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/%E5%9B%BE%E7%89%87/%E4%BA%94%E7%A7%8D%E7%B1%BB%E5%9E%8B.PNG" />
</div>



## 1. `singleton`

​	配置中的`bean`定义可以看作是一个模板，容器会根据这个模板来构造对象。`bean`定义中的`Scope`语义会决定：容器将根据这个模板构造多少对象实例，又该让这个对象实例存活多久。标记为拥有`singleton scope`的对象定义，在`Spring`的`IoC`容器中只存在一个对象实例，所有该对象的引用都共享这个实例。该实例从容器启动，并因为第一次被请求而初始化之后，将一直存活到容器退出，也就是说，它与`IoC`容器"几乎"拥有相同的"寿命"。

 <div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Spring%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/%E5%9B%BE%E7%89%87/singleton.PNG" />
</div>



## 2.`prototype`

​	针对声明为拥有`prototype scope`的`bean`定义，容器在接到该类型对象的请求的时候，会每次都重新生成一个新的实例对象给请求方。虽然这种类型的对象的实例化以及属性设置等工作都是由容器负责的，但是只要准备完毕，并且对象实例返回请求方之后，容器就不再拥有当前返回对象的引用，请求方需要自己负责当前返回对象的后继的声明周期的管理工作，例如该对象的销毁。也就是说，容器每次返回给请求方一个新的实例对象后，就任由这个对象"自生自灭"了。

​	对于那些请求方不能共享使用的对象类型，应该将其`bean`定义的`scope`设置为`prototype`。这样，每个请求方可以得到自己专有的一个对象实例。通常，声明为`prototype`的对象都是一些有状态的，比如保存每个顾客信息的对象。

​	从`Spring`参考文档下的这幅图片，可以再次了解`prototype scope`的`bean`定义，在实例化对象和注入依赖的时候，它的具体语义是什么样子。

 <div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Spring%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/%E5%9B%BE%E7%89%87/scope.PNG" />
</div>



## 3. `Request`

表示每个`request`作用域内的请求只创建一个实例。



## 4.`Session`

表示每个`session`作用域内的请求只创建一个实例。



## 5.`GlobalSession`

这个只在`porlet`的`web`应用程序中才有意义，它映射到`porlet`的`global`范围的`session`，如果普通的`web`应用使用了这个`Scope`，容器会把它作为普通的`session`作用域的`Scope`创建。



## 6.在创建`bean`的时候如何指定呢？

### `xml`方式


```
<bean id="student" class="Student" scope="prototype" />
```


### 注解方式


```
@Component

@Scope("prototype")

public class Student{

}
```

