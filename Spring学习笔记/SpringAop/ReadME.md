## Spring AOP

### 一.使用注解实现事务（声明式事务）
**目标：** 通过事务 使以下方法 要么全成功、要么全失败

```
public void addStudent()
{
	//增加班级
	//增加学生
	//crdu
}
```



#### a. jar包
`spring-tx-4.3.9.RELEASE`

`ojdbc.jar`

`commons-dbcp.jar`  连接池使用到数据源

`commons-pool.jar`  连接池

`spring-jdbc-4.3.9.RELEASE.jar`

`aopalliance.jar `

#### b.配置
`jdbc\mybatis\spring`
增加事务`tx`的命名空间

`<!-- 增加对事务的支持 -->`
`<tx:annotation-driven transaction-manager="txManager"  />`

#### c.使用

将需要 成为事务的方法 前增加注解：
`@Transactional(readOnly=false,propagation=Propagation.REQUIRED)`



### 二、@Transactional注解的属性

 

| 属性                     | 类型                                       | 说明                                                         |
| ------------------------ | ------------------------------------------ | ------------------------------------------------------------ |
| `propagation`            | 枚举型：`Propagation`                      | （可选）事务传播行为。例如：`propagation=Propagation.REQUIRES_NEW`详见后文 |
| `readOnly`               | 布尔型                                     | 是否为只读型事务。例如：`readOnly=false`                     |
| `isolation`              | 枚举型：`isolation`                        | （可选）事务隔离级别。例如：`isolation=Isolation.READ_COMMITTED` |
| `timeout`                | `int`型（单位：秒）                        | 事务超时时间。例如：`timeout=20`                             |
| `rollbackFor`            | 一组`Class`类的实例，必须继承自`Throwable` | 一组异常类，遇到时必须进行回滚。例如：`rollbackFor={SQLException.class,ArithmeticException.class}` |
| `rollbackForClassName`   | 一组`Class`类的名称，必须继承自`Throwable` | 一组异常类名，遇到时必须进行回滚。例如：`rollbackForClassName={"SQLException","ArithmeticException"}` |
| `noRollbackFor`          | 一组`Class`类的实例，必须继承自`Throwable` | 一组异常类，遇到时必须不回滚。                               |
| `noRollbackForClassName` | 一组`Class`类的名称，必须继承自`Throwable` | 一组异常类名，遇到时必须不回滚。                             |



### 三、用四个词来表示事务

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Spring%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/%E5%9B%BE%E7%89%87/%E5%A3%B0%E6%98%8E%E5%BC%8F%E4%BA%8B%E7%89%A9.PNG" />
</div>

​	在传统的软件开发中，人们创建了一个术语来描述事务：`ACID`。简单来说，`ACID`表示4个特性。



- **原子性（Atomic）**：事务是由一个或多个活动所组成的一个工作单元。原子性确保事务中的所有操作全部发生或全部不发生。如果所有的活动都成功了，事务也就成功了。如果任意一个活动失败了，整个事务也失败并回滚。

- **一致性（Consistent）**:一旦事务完成（不管成功还是失败），系统必须确保它所建模的业务处于一致的状态。现实的数据不应该被破坏。

- **隔离性（Isolated）**：事务允许多个用户对相同的数据进行操作，每个用户的操作不会与其他用户纠缠在一起。因此，事务应该被彼此隔离，避免发生同步读写相同数据的事情（注意的是，隔离性往往涉及到锁定数据库中的行或表）。

- **持久性（Durable）**:一旦事务完成，事务的结果应该持久化，这样就能从任何的系统崩溃中恢复过来。这一般会涉及讲结果存储到数据库或其他形式的持久化存储中。


### 四、`Propagation` （事务的传播属性）

`Propagationkey`属性确定代理应该给哪个方法增加事务行为。这样的属性最重要的部份是传播行为。有以下选项可供使用：

- `PROPAGATION_REQUIRED`--支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。

- `PROPAGATION_SUPPORTS`--支持当前事务，如果当前没有事务，就以非事务方式执行。


- `PROPAGATION_MANDATORY`--支持当前事务，如果当前没有事务，就抛出异常。


- `PROPAGATION_REQUIRES_NEW`--新建事务，如果当前存在事务，把当前事务挂起。


- `PROPAGATION_NOT_SUPPORTED`--以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。


- `PROPAGATION_NEVER`--以非事务方式执行，如果当前存在事务，则抛出异常。

#### 1： `PROPAGATION_REQUIRED`
加入当前正要执行的事务不在另外一个事务里，那么就起一个新的事务
​	比如说，`ServiceB.methodB`的事务级别定义为`PROPAGATION_REQUIRED`, 那么由于执行`ServiceA.methodA`的时候，`ServiceA.methodA`已经起了事务，这时调用`ServiceB.methodB`，`ServiceB.methodB`看到自己已经运行在`ServiceA.methodA`的事务内部，就不再起新的事务。而假如`ServiceA.methodA`运行的时候发现自己没有在事务中，他就会为自己分配一个事务。
​	这样，在`ServiceA.methodA`或者在`ServiceB.methodB`内的任何地方出现异常，事务都会被回滚。即使`ServiceB.methodB的`事务已经被提交，但是`ServiceA.methodA`在接下来`fail`要回滚，`ServiceB.methodB`也要回滚

#### 2： `PROPAGATION_SUPPORTS`
如果当前在事务中，即以事务的形式运行，如果当前不再一个事务中，那么就以非事务的形式运行

#### 3： `PROPAGATION_MANDATORY`
必须在一个事务中运行。也就是说，他只能被一个父事务调用。否则，他就要抛出异常

#### 4：` PROPAGATION_REQUIRES_NEW`
这个就比较绕口了。 比如我们设计`ServiceA.methodA`的事务级别为`PROPAGATION_REQUIRED`，`ServiceB.methodB`的事务级别为`PROPAGATION_REQUIRES_NEW`，那么当执行到`ServiceB.methodB`的时候，`ServiceA.methodA`所在的事务就会挂起，`ServiceB.methodB`会起一个新的事务，等待`ServiceB.methodB`的事务完成以后，他才继续执行。他与`PROPAGATION_REQUIRED` 的事务区别在于事务的回滚程度了。因为`ServiceB.methodB`是新起一个事务，那么就是存在两个不同的事务。如果`ServiceB.methodB`已经提交，那么`ServiceA.methodA`失败回滚，`ServiceB.methodB`是不会回滚的。如果`ServiceB.methodB`失败回滚，
如果他抛出的异常被`ServiceA.methodA`捕获，`ServiceA.methodA`事务仍然可能提交。

#### 5： `PROPAGATION_NOT_SUPPORTED`
当前不支持事务。比如`ServiceA.methodA`的事务级别是`PROPAGATION_REQUIRED` ，而`ServiceB.methodB`的事务级别是`PROPAGATION_NOT_SUPPORTED` ，
那么当执行到`ServiceB.methodB`时，`ServiceA.methodA`的事务挂起，而他以非事务的状态运行完，再继续`ServiceA.methodA`的事务。

#### 6： `PROPAGATION_NEVER`
不能在事务中运行。假设`ServiceA.methodA`的事务级别是`PROPAGATION_REQUIRED`， 而`ServiceB.methodB`的事务级别是`PROPAGATION_NEVER` ，
那么`ServiceB.methodB`就要抛出异常了。

#### 7： `PROPAGATION_NESTED`
理解`Nested`的关键是`savepoint`。他与`PROPAGATION_REQUIRES_NEW`的区别是，PROPAGATION_REQUIRES_NEW`另起一个事务，将会与他的父事务相互独立，
而`Nested`的事务和他的父事务是相依的，他的提交是要等和他的父事务一块提交的。也就是说，如果父事务最后回滚，他也要回滚的。
而`Nested`事务的好处是他有一个`savepoin`t。

*****************************************
```
ServiceA {

/**
* 事务属性配置为 PROPAGATION_REQUIRED
*/
void methodA() {
try {
	//savepoint
	ServiceB.methodB(); //PROPAGATION_NESTED 级别
} catch (SomeException) {
	// 执行其他业务, 如 ServiceC.methodC();
	}
}


```



********************************************
也就是说`ServiceB.methodB`失败回滚，那么`ServiceA.methodA也`会回滚到`savepoint`点上，`ServiceA.methodA`可以选择另外一个分支，比如`ServiceC.methodC`，继续执行，来尝试完成自己的事务。
但是这个事务并没有在EJB标准中定义。

 

 

### 五、`Spring`事务的隔离级别

 1. `ISOLATION_DEFAULT`： 这是一个`PlatfromTransactionManager`默认的隔离级别，使用数据库默认的事务隔离级别。另外四个与`JDBC`的隔离级别相对应

 2. `ISOLATION_READ_UNCOMMITTED`： 这是事务最低的隔离级别，它充许令外一个事务可以看到这个事务未提交的数据。这种隔离级别会产生脏读，不可重复读和幻像读。

 3. `ISOLATION_READ_COMMITTED`： 保证一个事务修改的数据提交后才能被另外一个事务读取。另外一个事务不能读取该事务未提交的数据，可以阻止脏读，但是幻读或不可重复读仍有可能发生。

 4. `ISOLATION_REPEATABLE_READ`： 这种事务隔离级别可以防止脏读，不可重复读。但是可能出现幻像读。它除了保证一个事务不能读取另一个事务未提交的数据外，还保证了避免下面的情况产生(不可重复读)。对同一个字段的多次读取结果是一致的，除非数据是被本事务自己所修改。可以阻止脏读和不可重复读，但幻读仍有可能发生。

 5. `ISOLATION_SERIALIZABLE `: 这是花费最高代价但是最可靠的事务隔离级别。事务被处理为顺序执行。除了防止脏读，不可重复读外，还避免了幻像读。

 
#### 数据库的4大隔离级别

- `read uncommitted` （读未提交）B事务可以读取到A事务未提交的修改。有脏读的问题
- `read committed` （读已提交）B事务不能读取其他事务未commit的修改。有不可重复读，幻读的问题
- `repeated read` (可重复读) 事务第一次读和第二次读，结果不受其他事务影响，结果是一样的。有幻读的问题
- `Serializeble` （串行化）幻读，比如事务A对表T所有行进行`update`，再次查询，发现有些行没有`update`，原因是这些行是新增行，是其他事务添加的，导致不会修改新增的行。串行化，就是顺序执行事务。这个隔离界别要慎用，因为及其影响数据库吞吐量，未能使用到`CPU`的高性能。

隔离界别，从低到高。级别越高，数据库处理多事务的规则越严格，意味着要牺牲性能为代价。不同的数据库，默认的数据隔离级别有区别，大多数据库默认是`repeated read`。`mysql`默认是`read committed`。


### 六、什么是脏数据，脏读，不可重复读，幻觉读？



- **脏读（Dirty reads）:** 脏读发生在一个事务读取了另一个事务改写但尚未提交的数据时。如果改写在稍后被回滚了，那么第一个事务获取的数据就是无效的。


- **不可重复读（Nonrepeatable read）:** 不可重复读发生在一个事务执行相同的查询两次或两次以上，但是每次都得到不同的数据时。这通常是因为另一个并发事务在两次查询期间更新了数据。

- **幻觉读（Phantom read）:** 幻读与不可重复读类似。它发生在一个事务（T1）读取了几行数据，接着另一个并发事务(T2)插入了一些数据时。在随后的查询中，第一个事务（T1）就会发现多了一些原本不存在的记录。

 


### 七、AOP：面向方面编程(基于实现接口的方式)

一个普通的类	-->	有特定功能的类  

- a.继承类  

- b.实现接口  

- c.注解  

- d.配置

```
public class MyFilter exntends/implements Xx
{

}

```




类 --> "通知" ：实现接口

#### 前置通知实现步骤：

- `a.jar`
  ​	`aopaliance.jar`
  ​	`aspectjweaver.jar`

- b.配置

- c.编写
  ​	`aop`：每当之前`add()`之前 自动执行一个方法`log()`;

`addStudent();`  业务方法（`IStudentService.java`中的  `addStudent()`）
`before()`;  自动执行的通知，即`aop`前置通知

```
public class Xxx
{
	@Test
	a(){}
}
```



如果出现异常：类似

`java.lang.NoClassDefFoundError: org/apache/commons/pool/impl/GenericObjectPool`
则说明缺少`jar`



#### 后置通知：
- a.通知类  ，普通实现接口
- b.业务类、业务方法
  ​	`StudentServiceImpl`中的`addStudent()`
- c.配置：
  ​	将业务类、通知 纳入`springIOC`容器
  ​	定义切入点（一端）、定义通知类（另一端），通过`pointcut-ref`将两端连接起来



#### 异常通知：
根据异常通知接口的定义可以发现，异常通知的实现类 必须编写以下方法：

```
public void afterThrowing([Method, args, target], ThrowableSubclass)：
```

```
a.public void afterThrowing(Method, args, target, ThrowableSubclass)
b.public void afterThrowing( ThrowableSubclass)
```



#### 环绕通知： 

在目标方法的前后、异常发生时、最终等各个地方都可以 进行的通知，最强大的一个通知；
可以获取目标方法的 全部控制权（目标方法是否执行、执行之前、执行之后、参数、返回值等）

在使用环绕通知时，目标方法的一切信息 都可以通过`invocation`参数获取到。

环绕通知 底层是通过**拦截器**实现的。

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Spring%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/%E5%9B%BE%E7%89%87/%E7%8E%AF%E7%BB%95%E9%80%9A%E7%9F%A5.png" />
</div>



### 八、基于注解方式实现AOP通知



- a.jar
  ​	与 实现接口 的方式相同
- b.配置
  ​	将业务类、通知 纳入`SpringIOC`容器
  ​	开启注解对`AOP`的支持`<aop:aspectj-autoproxy></aop:aspectj-autoproxy>`
  ​	业务类 `addStudent` -  通知 
- c.编写

通知：

```
@Aspect  //声明该类 是一个 通知
public class LogBeforeAnnotation  {

}
```

**注意：**通过注解形式 将对象增加到 `IOC`容器时，需要设置 扫描器

`<context:component-scan base-package="org.moons.aop"></context:component-scan>`

**扫描器** 会将 指定的包 中的 `@Cmponent` `@Service`  `@Respository`  ` @Controller`修饰的类产生的对象 增加到`IOC`容器中。

`@Aspect`不需要 加入扫描器，只需要开启即可：`<aop:aspectj-autoproxy></aop:aspectj-autoproxy>`


通过注解形式 实现的`aop`，如果想获取 目标对象的一些参数，则需要使用一个对象：`JoinPoint`



#### **注解形式的返回值：**



- a.声明返回值 的参数名：

```
@AfterReturning( pointcut= "execution(public * addStudent(..))" ,returning="returningValue" ) 

public void myAfter(JoinPoint jp,Object returningValue) {
//returningValue是返回值，但需要告诉spring
System.out.println("返回值："+returningValue );
}
```

注解形式实现`Aop`时，通知的方法的参数不能多、少

实现接口形式、注解形式 只捕获声明的特定类型的异常，而其他类型异常不捕获。
`catch()`



#### 表达式`expression`的常见示例如表所示。

 

`expression="execution(…)"` 

| 举例                                                         | 含义                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| `public boolean addStudent(org.moons.entity.Student))`       | 所有返回类型为`boolean`、参数类型为`org.moons.entity.Student`的`addStudent()`方法。 |
| `public boolean org.moons.service.IStudentService.addStudent(org.moons.entity.Student)` | `org.moons.service.IStudentService`类（或接口）中的`addStudent()`方法，并且返回类型是`boolean`、参数类型是`org.moons.entity.Student` |
| `public * addStudent(org.moons.entity.Student)`              | `"*"`代表任意返回类型                                        |
| `public void *( org.moons.entity.Student)`                   | "*"代表任意方法名                                            |
| `public void addStudent(..)`                                 | `".."`代表任意参数列表                                       |
| `* org.moons.service.*.*(..)`                                | `org.moons.service.IStudentService`包中，包含的所有方法（不包含子包中的方法） |
| `* org.moons.service..*.*(..)`                               | `org.moons.service.IStudentService`包中，包含的所有方法（包含子包中的方法） |






### 九、基于Schema配置的方式实现AOP通知
类似与实现接口的方式

接口方式通知：`public class LogAfter implements AfterReturningAdvice`
`Schema`方式通知：

- a.编写一个普通类  `public class LogAfter {}`  
- b.将该类 通过配置，转为一个"通知"
  ​	

#### 如果要获取目标对象信息：
**注解、`Schema`：**`JoinPoint`
**接口：**`Method method, Object[] args, Object target`

`Schema`形式 和注解形式相似，**不同之处：** 注解形式 使用了注册`@After`，  `Schmema`形式进行了多余的配置

