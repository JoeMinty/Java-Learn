#  关联映射、延迟加载

## 日志：`Log4j`

a、`Log4j:	log4j.jar (mybatis.zip中lib中包含此jar)`

b、开启日志，`conf.xml`

```
<settings>
	<!-- 开启日志，并指定使用的具体日志 -->
	<setting name="logImpl" value="LOG4J"/>
</settings>
```

如果不指定，`Mybatis`就会根据以下顺序 寻找日志
`SLF4J →Apache Commons Logging →Log4j 2 → Log4j →JDK logging`

c、.编写配置日志输出文件

```
log4j.properties，内容
log4j.rootLogger=DEBUG, stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

### 日志级别：
`DEBUG<INFO<WARN<ERROR`
如果设置为`info`，则只显示`info`及以上级别的信息；

**建议** 在开发时设置`debug`，在运行时设置为`info`或以上。


可以通过日志信息，相信的阅读`mybatis`执行情况（ 观察`mybatis`实际执行`sql`语句 以及`SQL`中的参数 和返回结果）


## 延迟加载（懒加载）：

一对一、一对多、多对一、多对多
一对多：班级-学生 ，
如果不采用延迟加载  （立即加载），查询时会将 一 和多 都查询，班级、班级中的所有学生。
如果想要  暂时只查询1的一方，  而多的一方 先不查询 而是在需要的时候再去查询 -->延迟加载

一对一：学生、学生证

`mybatis`中使用延迟加载，需要先配置：

```
<settings>
	<!-- 开启延迟加载 -->
	<setting name="lazyLoadingEnabled" value="true"/>
	<!-- 关闭立即加载 -->
	<setting name="aggressiveLazyLoading" value="false"/>
</settings>
```

如果增加了`mapper.xml`,要修改`conf.xml`配置文件（将新增的`mapper.xml`加载进去）

通过`debug`可以发现， 如果程序只需要学生，则只向数据库发送了查询学生的`SQL`；
当我们后续 需要用到学生证的时候，再第二次发送 查询学生证的`SQL`。

一对多：和一对一的延迟加载配置方法相同

### 延迟加载的步骤：先查班级，按需查询学生
1、开启延迟加载`conf.xml`配置`settings`
2、配置`mapper.xml`
​	写2个`Mapper`

班级`mapper.xml`


```
  	<!-- 一对多，带延迟加载 -->
	<select id="queryClassAndStudents"   resultMap="class_student_lazyLoad_map">
		<!-- 11111111先查询班级 -->
		select  c.* from studentclass c
	</select>
	 
<!-- 类-表的对应关系 -->
<resultMap type="studentClass" id="class_student_lazyLoad_map">
			<!-- 因为 type的主类是班级，因此先配置班级的信息-->
			<id  property="classId" column="classId"/>
			<result  property="className" column="className"/>
			<!-- 配置成员属性学生，一对多;属性类型：javaType，属性的元素类型ofType -->
			<!-- 2222222再查班级对应的学生 -->
			<collection property="students" ofType="student" select="org.moons.mapper.StudentMapper.queryStudentsByClassId" column="classid">
				<!-- <id  property="stuNo" column="stuNo"/>
				<result  property="stuName" column="stuName"/>
				<result  property="stuAge" column="stuAge"/>
				 -->
			</collection>
</resultMap>
	
```

即查询 学生的`sql`是通过`select`属性指定，并且通过`column`指定外键

学生`mapper.xml`

```
<!-- 一对多,延迟加载需要的： 查询班级中的所有学生 -->
<select id="queryStudentsByClassId" parameterType="int" resultType="student">
	select * from student where classId = #{classId}
</select>	
```

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E5%BB%B6%E8%BF%9F%E5%8A%A0%E8%BD%BD%E5%AF%B9%E6%AF%94.png" />
</div>
