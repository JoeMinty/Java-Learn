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

### 三、`Propagation` （事务的传播属性）

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

 

 

### 四、`Spring`事务的隔离级别

 1. `ISOLATION_DEFAULT`： 这是一个`PlatfromTransactionManager`默认的隔离级别，使用数据库默认的事务隔离级别。另外四个与`JDBC`的隔离级别相对应
 
 2. `ISOLATION_READ_UNCOMMITTED`： 这是事务最低的隔离级别，它充许令外一个事务可以看到这个事务未提交的数据。这种隔离级别会产生脏读，不可重复读和幻像读。
 
 3. `ISOLATION_READ_COMMITTED`： 保证一个事务修改的数据提交后才能被另外一个事务读取。另外一个事务不能读取该事务未提交的数据
 
 4. `ISOLATION_REPEATABLE_READ`： 这种事务隔离级别可以防止脏读，不可重复读。但是可能出现幻像读。它除了保证一个事务不能读取另一个事务未提交的数据外，还保证了避免下面的情况产生(不可重复读)。对同一个字段的多次读取结果是一致的，除非数据是被本事务自己所修改。可以阻止脏读和不可重复读，但幻读仍有可能发生。
 
 5. `ISOLATION_SERIALIZABLE `: 这是花费最高代价但是最可靠的事务隔离级别。事务被处理为顺序执行。除了防止脏读，不可重复读外，还避免了幻像读。

 

 

### 五、什么是脏数据，脏读，不可重复读，幻觉读？

**脏读:** 指当一个事务正在访问数据，并且对数据进行了修改，而这种修改还没有提交到数据库中，这时，另外一个事务也访问这个数据，然后使用了这个数据。因为这个数据是还没有提交的数据， 那么另外一个事务读到的这个数据是脏数据，依据脏数据所做的操作可能是不正确的。

**不可重复读:** 指在一个事务内，多次读同一数据。在这个事务还没有结束时，另外一个事务也访问该同一数据。那么，在第一个事务中的两次读数据之间，由于第二个事务的修改，那么第一个事务两次读到的数据可能是不一样的。这样就发生了在一个事务内两次读到的数据是不一样的，因此称为是不可重复读。
          
**幻觉读:** 指当事务不是独立执行时发生的一种现象，例如第一个事务对一个表中的数据进行了修改，这种修改涉及到表中的全部数据行。同时，第二个事务也修改这个表中的数据，这种修改是向表中插入一行新数据。那么，以后就会发生操作第一个事务的用户发现表中还有没有修改的数据行，就好象发生了幻觉一样。

 
