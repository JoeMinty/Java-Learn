# `Maven`应用



## 依赖


```
A.jar -> B.jar

<dependency>
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
	<version>4.0</version>
	<scope>test</scope>
</dependency>

```

### 1.依赖的范围、依赖的有效性

	`compile`(默认)  、`test`、  `provided`

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>

|                              | **`compile`** | **`test`** | **`provided`** |
| ---------------------------- | ----------- | -------- | ------------ |
| 编译                         | √           | ×        | √            |
| 测试                         | √           | √        | √            |
| **部署(运行）**          | √       | ×            |×|



**`Maven`在编译、测试、运行项目时，各自使用一套`classpath`**



### 2.依赖排除

`A.jar ->B.jar`
当我们通过`maven`引入`A.jar`时，会自动引入`B.jar`
`A.jar(x.java ,y.java,z.java)     B.jar(p.java  c.java  i.java)`
`A.jar`和`B.jar`之间的 依赖的本质：`z.java ->c.java`

```
<!-- 排除依赖 beans -->
<exclusions>
	<exclusion>
		<groupId>org.springframework</groupId>
		<artifactId>spring-beans</artifactId>
	</exclusion>
</exclusions>
```

- a、`commons-fileupload.jar`  `commons-io.jar`  :虽然我们实际开发时，认为二者`jar`必须关联，但是`maven`可能不这么认为。
- b、如果`X.jar`依赖于`Y.jar`，但是在引入`X.jar`之前  已经存在了`Y.jar`，则`maven`不会再在 引入`X.jar`时 引入`Y.jar`

### 3、依赖的传递性

`A.jar->B.jar->C.jar`

要使` A.jar ->C.jar` :当且仅当 `B.jar `依赖于`C.jar`的范围是`compile`



### 4、依赖原则：为了防止冲突

- a、路径最短优先原则
- b、路径长度相同：
  - i.在同一个`pom.xml`文件中有2个相同的依赖（覆盖）：后面声明的依赖会覆盖前面声明的依赖 （严禁使用本情况，严禁在同一个`pom`中声明2个版本不同的依赖）
  - ii.如果是不同的 `pom.xml`中有2个相同的依赖（优先）：则先声明的依赖 ，会覆盖后声明的依赖

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>

### 5、多个`maven`项目（模块）之间如何 依赖： p项目 依赖于->q项目

- 1.  p项目` install` 到本地仓库
- 2.  q项目 依赖：

```
<!-- 本项目  依赖于HelloWorld2项目 -->
<dependency>
	<groupId>org.moons.maven</groupId>
	<artifactId>HelloWorld2</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```



## `Maven`生命周期

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>

### 生命周期和构建的关系：



生命周期中的顺序：`a b c d e `
当我们执行c命令，则实际执行的是 a b c 



### 生命周期包含的阶段：3个阶段

`clean lifecycle` ：清理
`pre-clean` 、 `clean`、  `post-clean`

`default lifecycle` ：默认(常用)


`site lifecycle`：站点
`pre-site`、`site`   `post-site` `site-deploy`



## 统一项目的JDK：



1、`build path`:删除旧版本，增加新版本

2、右键项目-属性-`Project Factors` -`java version` 改版本  （之前存在要改的版本）

3、通过`maven`统一`JDK`版本：

```
<profiles>
	<profile>  
		<id>jdk-18</id>  
		<activation>  
		<activeByDefault>true</activeByDefault>  
		<jdk>1.8</jdk>  
		</activation>  
	<properties>  
		<maven.compiler.source>1.8</maven.compiler.source>  
		<maven.compiler.target>1.8</maven.compiler.target>  				<maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>  
	</properties>   
	 </profile>  
</profiles>
```






​	