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

新建：`bean configuration`  `applicationContext.xml`



### 3、开发Spring程序(IOC)

	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml") ;
	//执行从springIOC容器中获取一个 id为student的对象
	Student student = (Student)context.getBean("student") ;
可以发现，`springIOC`容器 帮我们`new`了对象，并且给对象赋了值。



### 4、SpringIOC发展史：
1、


```
Student student = new Student();
student.setXxx();
```

2、
简单工厂

3、

IOC （超级工厂）



`IOC`（控制反转）也可以称之为`DI`（依赖注入）：
**控制反转：**将 创建对象、属性值 的方式 进行了翻转，从`new`、`setXxx()`  翻转为了 从`SpringIOC`容器`getBean()`
**依赖注入：**将属性值 注入给了属性，将属性 注入给了`bean`，将`bean`注入给了`ioc`容器；

​	
**总结：**`ioc/di` ，无论要什么对象，都可以直接去`Springioc`容器中获取，而不需要自己操作`（new\setXxx()）`

因此之后的`ioc`分为2步：

- 先给`springioc`中存放对象并赋值   
- 2 拿



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