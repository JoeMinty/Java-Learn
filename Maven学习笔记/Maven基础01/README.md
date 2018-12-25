# `Maven`基础



## `Maven`的作用

- 增加第三方`Jar`   (`commons-fileupload.jar`   `commons-io.jar`)
- `jar`包之间的依赖关系 （`commons-fileupload.jar` 自动关联下载所有依赖的`Jar`，并且不会冲突）

- 将项目拆分成若干个模块
<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Maven%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/Maven%E5%9B%BE%E7%89%87/%E9%A1%B9%E7%9B%AE%E6%A8%A1%E5%9D%97.png" />
</div>

## `Maven`概念
- 是一个基于Java平台的 自动化构建工具
- `make`-`ant`-`maven`-`gradle`

**清理：** 删除编译的结果，为重新编译做准备。

**编译：**`.java->.class`

**测试：** 针对于 项目中的关键点进行测试，亦可用 项目中的测试代码 去测试开发代码；

**报告:** 将测试的结果 进行显示

**打包：** 将项目中包含的多个文件 压缩成一个文件， 用于安装或部署。 （`java`项目-`jar`、`web`项目-`war`）

**安装：** 将打成的包  放到  本地仓库，供其他项目使用。

**部署：** 将打成的包  放到  服务器上准备运行。
-- 将`java`、`js`、`jsp`等各个文件 进行筛选、组装，变成一个 可以直接运行的项目
-- 大米->米饭


`Eclipse`中部署的`web`项目可以运行
将`Eclipse`中的项目，复制到`tomcat/webapps`中则不能运行
项目可以在`webappas`中直接运行
​	
`Eclipse`的项目 ，在部署时 会生成一个 对应的 部署项目(在`tpwebapps`)，区别在于： 部署项目 没有源码文件`src(java)`只有编译后的`class`文件和`jsp`文件
因为二者目录结构不一致，因此`tomcat`中无法直接运行 `Eclipse`中复制过来的项目 （因为 如果要在`tomcat`中运行一个项目，则该项目 必须严格遵循`tomcat`的目录结构）
​	
`Eclipse`中的项目 要在`tomcat`中运行，就需要部署： 

- a.通过`Eclipse`中`Add and Remove`按钮进行部署
- b.将`Web`项目打成一个`war`包，然后将该`war`包复制到`tomcat/webapps`中 即可执行运行


 自动化构建工具`maven`：将 原材料（`java`、`js`、`css`、`html`、图片）->产品（可发布项目）

 编译-打包-部署-测试   --> 自动构建



## 下载配置`Maven`

- a.配置`JAVA_HOME`
- b.配置`MAVEN_HOME    :    D:\apache-maven-3.5.3\bin`
	`M2_HOME`
- c.配置`path`
	`%MAVEN_HOME%\bin`
- d.验证
	`mvn -v`
- e.配置本地仓库  `maven`目录`/conf/settings.xml`
	
	默认本地仓库 ：`C:/Users/MOONS/.m2/repository`
	
	修改本地仓库：`<localRepository>D:/mvnrep</localRepository>`
	
## 使用`Maven`

**约定 优于 配置**

硬编码方式：`job.setPath("d:\\abc") ;`

配置方式：
​	`job`
​	`conf.xml     <path>d:\\abc</path>`

约定：使用默认值`d:\\abc`
​	`job`


### `Maven`约定的目录结构：
项目
```
-src				
	--main			：程序功能代码
		--java		：java代码  (Hello xxx)
		--resources ：资源代码、配置代码
	--test			：测试代码
		--java			
		--resources	
pom.xml
```

```
	<groupId>域名翻转.大项目名</groupId>
	<groupId>org.moons.maven</groupId>
	
	<artifactId>子模块名</artifactId>
	<artifactId>HelloWorld</artifactId>


	<version>版本号</version>
	<version>0.0.1-SNAPSHOT</version>
```


### 依赖：
`commons-fileupload.jar --> commons-io.jar`

`A`中的某些类 需要使用`B`中的某些类，则称为`A`依赖于`B`

在`Maven`项目中，如果要使用 一个当时存在的`Jar`或模块，则可以通过 依赖实现（去本地仓库、中央仓库去寻找）

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Maven%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/Maven%E5%9B%BE%E7%89%87/%E6%9C%AC%E5%9C%B0%E4%BB%93%E5%BA%93.png" />
</div>




## `Maven`相关命令

执行`mvn`：  必须在`pom.xml`所在目录中执行

`Maven`常见命令： （第一次执行命令时，因为需要下载执行该命令的基础环境，所以会从中央仓库下载该环境到本地仓库）

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Maven%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/Maven%E5%9B%BE%E7%89%87/jar.png" />
</div>



编译：  (`Maven`基础组件 ，基础`Jar`)
- `mvn compile`   --只编译`main`目录中的`java`文件
- `mvn test`     测试
- `mvn package`          打成`jar/war`
- `mvn install`  将开发的模块 放入本地仓库，供其他模块使用 （放入的位置 是通过配置在`pom.xml`中的坐标决定的）

- `mvn clean`  删除`target`目录（删除编译文件的目录）
- 运行`mvn`命令，必须在`pom.xml`文件所在目录

