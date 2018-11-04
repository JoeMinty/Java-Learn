## Spring学习之SpringIOC



**`IOC` :控制反转 （`DI`：依赖注入）**

### 1、搭建Spring环境
下载`jar`
http://maven.springframework.org/release/org/springframework/spring/
`spring-framework-4.3.9.RELEASE-dist.zip`
开发spring至少需要使用的`jar`	(5个+1个):

- `spring-aop.jar`		开发`AOP`特性时需要的`JAR`
- `spring-beans.jar`		处理`Bean`的`jar`			`<bean>`
- `spring-context.jar`	处理`spring`上下文的`jar`		`<context>`
- `spring-core.jar`		`spring`核心`jar`
- `spring-expression.jar`	`spring`表达式 
  第三方提供的日志`jar`
- `commons-logging.jar`	日志

### 2、编写配置文件

新建：
`bean configuration`  `applicationContext.xml`



### 3、开发Spring程序(IOC)

	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml") ;
	//执行从springIOC容器中获取一个 id为student的对象
	Student student = (Student)context.getBean("student") ;
可以发现，`springIOC`容器 帮我们`new`了对象，并且给对象赋了值。

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Spring%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/%E5%9B%BE%E7%89%87/new%E6%96%B9%E5%BC%8F%E7%9A%84%E5%88%9B%E5%BB%BA%E5%AF%B9%E8%B1%A1.png" />
</div>

### 4、SpringIOC发展史：
1、


```
Student student = new Student();
student.setXxx();
```

2、
简单工厂

3、IOC （超级工厂）

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Spring%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/%E5%9B%BE%E7%89%87/springioc%E5%AE%B9%E5%99%A8.png
" />
</div>

`IOC`（控制反转）也可以称之为`DI`（依赖注入）：

**控制反转：** 将 创建对象、属性值 的方式 进行了翻转，从`new`、`setXxx()`  翻转为了 从`SpringIOC`容器`getBean()`

**依赖注入：** 将属性值 注入给了属性，将属性 注入给了`bean`，将`bean`注入给了`ioc`容器；

​	
**总结：**`ioc/di` ，无论要什么对象，都可以直接去`Springioc`容器中获取，而不需要自己操作`（new\setXxx()）`

因此之后的`ioc`分为2步：

- 先给`springioc`中存放对象并赋值   
- 2 拿

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Spring%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/%E5%9B%BE%E7%89%87/%E4%BB%8EIoc%E4%B8%AD%E8%8E%B7%E5%8F%96.png" />
</div>

`DI`:依赖注入 ，

`Teacher`  

`Course`  :` cname ` `teacher`

`IOC`容器赋值：如果是简单类型（8个基本+`String`），`value`； 
如果是对象类型，`ref`="需要引用的`id`值"，因此实现了 对象与对象之间的依赖关系

	`context.getBean`(需要获取的`bean`的`id`值)



### 5、依赖注入3种方式：
1、`set`注入：通过`setXxx()`赋值

赋值，默认使用的是 `set方法()`;
依赖注入底层是通过反射实现的。
`<property...>`

2、构造器注入：通过构造方法赋值
 <constructor-arg value="ls" type="String" index="0" name="name"></constructor-arg>

需要注意：如果  <constructor-arg>的顺序 与构造方法参数的顺序不一致，则需要通过`type`或者`index`或`name`指定。

3、`p`命名空间注入
引入p命名空间
​	`xmlns:p="http://www.springframework.org/schema/p"`

<bean id="course" class="org.moons.entity.Course" p:courseHour="300" p:courseName="hadoop" p:teacher-ref="teacher">

**简单类型：**
​	`p:属性名="属性值"`

**引用类型:**

（除了`String`外）：
​	`p:属性名-ref="引用的id"`
注意多个 p赋值的时候 要有空格。

**注意：**
无论是`String`还是`Int/short/long`，在赋值时都是 value="值" ，
因此建议 此种情况 需要配合` name\type`进行区分



示例：
​	注入各种集合数据类型: `List` ` Set` `map` `properties`

`set`、`list`、数组   各自都有自己的标签<set> <list> <array>，但是也可以混着用。




给对象类型赋值`null` ：
```
		<property name="name" >  
				<null/>       -->注意 没有<value>
		</property>
```
赋空值
```
		<property name="name" >  
				<value></value>  
		</property>
```

**在`ioc`中定义`bean`的前提：该`bean`的类 必须提供了 无参构造**




**`value`与`<value>`注入方式的区别：**

 

|                                         | 使用子元素`<value>`注入                                        | 而使用`value`属性注入                     |
| --------------------------------------- | ------------------------------------------------------------ | --------------------------------------- |
| 参数值位置                              | 写在首尾标签（`<value></value>`）的中间(不加双引号)            | 写在`value`的属性值中（必须加双引号）     |
| `type`属性                                | 有（可选）可以通过`type`属性指定数据类型                       | 无                                      |
| 参数值包含特殊字符（<， &）时的处理方法 | 两种处理方法。**一、使用`<![CDATA[ ]]>`标记**二、使用`XML`预定义的实体引用 | 一种处理方法。即使用`XML`预定义的实体引用 |

 

其中，`XML`预定义的实体引用，如表所示。



| 实体引用 | 表示的符号 |
| -------- | ---------- |
| &lt;     | <          |
| &amp;    | &          |
| &gt;     | >          |



### 6、自动装配

自动装配（只适用于 `ref`类型 ）：
​	约定优于配置

自动装配：

```
<bean ... class="org.moons.entity.Course"  autowire="byName|byType|constructor|no" >  byName本质是byId
```



- `byName`:  自动寻找：其他`bean`的`id`值=该`Course`类的属性名
- `byType`:  其他`bean`的类型(`class`)  是否与 该`Course`类的`ref`属性类型一致  （注意，此种方式 必须满足：当前`Ioc`容器中 只能有一个`Bean`满足条件  ）
- `constructor`： 其他`bean`的类型(`class`)  是否与 该`Course`类的构造方法参数 的类型一致；此种方式的本质就是`byType`

可以在头文件中 一次性将该ioc容器的所有bean  统一设置成自动装配：

```
<beans xmlns="http://www.springframework.org/schema/beans"
...
default-autowire="byName">
```



自动装配虽然可以减少代码量，但是会降低程序的可读性，使用时需要谨慎。



### 7、使用注解定义bean：通过注解的形式 将bean以及相应的属性值 放入ioc容器

```
<context:component-scan base-package="org.moons.dao">
```

`</context:component-scan> Spring`在启动的时候，会根据`base-package`在 该包中扫描所有类，查找这些类是否有注解`@Component("studentDao")`,如果有，则将该类 加入`Spring Ioc`容器。

`@Component`细化：

- `dao`层注解：`@Repository`
- `service`层注解：`@Service`
- 控制器层注解：`@Controller`
