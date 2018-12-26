# `Maven`继承、聚合、`web`部署



## 打包方式
- `java`工程--`jar`

- `web`项目--`war`

- 父工程--`pom`


## 继承实现步骤

- 1.建立父工程： 父工程的打包方式为`pom `

- 2.在父工程的`pom.xml`中编写依赖：

```
  <dependencyManagement>
    	<dependencies>
    		<dependency>
```

- 3.子类:

```
    <!-- 给当前工程 继承一个父工程：1、加入父工程坐标gav   
    2、当前工程的Pom.xml到父工程的Pom.xml之间的 相对路径  -->
<parent>
	<!-- 1加入父工程坐标gav -->
	<groupId>org.moons.maven</groupId>
	<artifactId>B</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<!-- 2当前工程的Pom.xml到父工程的Pom.xml之间的 相对路径 --> 
	<relativePath>../B/pom.xml</relativePath>
</parent>
```

- 4.在子类中 需要声明 ：使用那些父类的依赖

```
<dependency>
	<!-- 声明：需要使用到父类的junit （只需要ga） -->
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
</dependency>
```



## 聚合



`Maven`项目能够识别的： 自身包含、本地仓库中的

`Maven2`依赖于`Maven1`，则在执行时：必须先将`Maven1`加入到本地仓库`(install)`，之后才能执行`Maven2`

以上 前置工程的`install`操作，可以交由“聚合” 一次性搞定。。。

### 聚合的使用

在一个总工程中配置聚合： （聚合的配置 只能配置在（打包方式为`pom`）的`Maven`工程中）

```
<modules>
	<!--项目的根路径  -->
	<module>../Maven1</module>
	<module>../Maven2</module>
</modules>
```
配置完聚合之后，以后只要操作总工程，则会自动操作 改聚合中配置过的工程

**注意：**`clean`命令 是删除`target`目录，并不是清理`install`存放入的本地仓库

### 聚合：



​	`Maven`将一个大工程拆分成 若干个子工程（子模块）

​	聚合可以将拆分的多个子工程 合起来

### 继承：
​	父->子工程,可以通过父工程  统一管理依赖的版本



## 部署`Web`工程：`war`

通过`maven`直接部署运行`web`项目：

- a.配置`cargo`
- b. `maven`命令：`deploy`

`Maven`部署`web`工程:

```
<build>	
	<finalName>WebProjectName</finalName>
	<plugins>
		<plugin>
			<groupId>org.codehaus.cargo</groupId>
			<artifactId>cargo-maven2-plugin</artifactId>
			<version>1.4.9</version>
		<configuration>
			<container>
			<containerId>tomcat8-5-30</containerId>
			<home>D:\study\apache-tomcat-8.5.30</home>
			</container>
		<configuration>
		<type>existing</type>
		<home>D:\study\apache-tomcat-8.5.30</home>
		<!-- 默认值8080 -->
	<properties>
		<cargo.servlet.port>8888</cargo.servlet.port>
	</properties>
	</configuration>
		</configuration>
			<executions>  
				<execution>  
					<id>cargo-run</id>  
					<phase>install</phase>  
					<goals>  
						<goal>run</goal>  
					</goals>  
				</execution>  
			</executions>
		</plugin>
	</plugins>
</build>
```



实际开发中，开发人员 将自己的项目开发完毕后  打成`war`包(`package`) 交给实施人员去部署；



**`Maven`中央仓库地址**

http://www.mvnrepository.com





