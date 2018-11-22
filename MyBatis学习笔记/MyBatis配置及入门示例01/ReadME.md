

### MyBatis配置及入门示例




#### MyBatis简单概念以及使用

`MyBatis:`
​	`ibatis:Apache`
​	`2010 ibatis-> google colde ,Mybatis`

`MyBatis`可以简化`JDBC`操作，实现数据的持久化 。
​	

`ORM:Object Relational Mapping`
​	    `person`对象   `person`表

`ORM`：概念 
`MyBatis`是`ORM`的一个实现/`Hibernate` 
`ORM`可以使开发人员  像操作对象一样 操作数据库表。

开发`MyBatis`程序从步骤：

1.配置`MyBatis`

`conf.xml:`配置数据库信息 和 需要加载的映射文件

表 - 类

映射文件`xxMapper.xml`  :增删改查标签<select>

测试类：

`session.selectOne("需要查询的SQL的namespace.id","SQL的参数值");`

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/orm%E8%A1%A8%E4%B8%8E%E5%AF%B9%E8%B1%A1%E6%98%A0%E5%B0%84.png" />
</div>




#### 建表语句

` create table person (id int, name varchar(20),age int);`

`insert into person values(1,'zp' ,23)`



#### 遇到的问题总结

**注意：**

如果在加载`conf.xml`文件的时候报如下加载不到配置文件的错误：

```
Exception in thread "main" java.io.IOException: Could not find resource conf.xml
	at org.apache.ibatis.io.Resources.getResourceAsStream(Resources.java:104)
	at org.apache.ibatis.io.Resources.getResourceAsStream(Resources.java:91)
	at org.apache.ibatis.io.Resources.getResourceAsReader(Resources.java:149)
	at org.moons.entity.TestMyBatis.main(TestMyBatis.java:15)

Process finished with exit code 1
```

**原因：**

`IDEA`是不会编译`src`的`java`目录的`xml`文件，所以在`MyBatis`的配置文件中找不到`xml`文件！（也有可能是`Maven`构建项目的问题，网上教程很多项目是普通的`Java Web`项目，所以可以放到`src`下面也能读取到）



**解决办法：**



**解决方案1：**

不将`xml`放到`src`目录下面，将`xxxMapper.xml`放到`Maven`构建的`resource`目录下面！



**解决方案2：** 
在`Maven`的`pom`文件中，添加下面代码：

```
<build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
        </resources>
    </build>
```

