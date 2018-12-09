# 动语态SQL、foreach、输入参数为集合属性、集合、数组、动态数组

//查询全部

`String statement = "select stuno,stuname from student";`

//根据年龄查询学生

`String statement = "select stuno,stuname from student where stuage = #{stuage}"; `


//根据姓名和年龄查询学生

```
String statement = "select stuno,stuname from student where stuname = #{stuName} and stuage = #{stuage} "; 

select stuno,stuname from student where  stuname = #{stuName} and  stuage = #{stuAge}
```



## `<where>`

```
<select id="queryStuByNOrAWishSQLTag" parameterType="student"	resultType="student" >
		select stuno,stuname,stuage from student
		<where>
			<!-- <if test="student有stuname属性 且不为null"> -->
			<if test="stuName !=null and stuName!=''  "> 
				and stuname = #{stuName}
			</if>
			<if test="stuAge !=null and stuAge!=0  "> 
				 and stuage = #{stuAge}
			</if>
		</where>
</select>
```


`<where>`会自动处理第一个`<if>`标签中的`and`，但不会处理之后`<if>`中的`and`



## `<foreach>`

查询学号为1、2、53的学生信息

```
ids = {1,2,53};

select stuno,stuname from student  where stuno in (1,2,53) 
```



`<foreach>`迭代的类型：数组、对象数组、集合、属性(`Grade`类： `List<Integer> ids`)

属性`(Grade类： List<Integer> ids)`


```
<select id="queryStudentsWithNosInGrade"  parameterType="grade" resultType="student">
	  	select * from student 
	  	<where>
	  		 <if test="stuNos!=null and stuNos.size>0">
	  		 	<foreach collection="stuNos" open=" and  stuno in (" close=")" 
	  		 		item="stuNo" separator=",">   
	  		 		#{stuNo}
	  		 	</foreach>
	  		 </if>
	  	
	  	</where>
	
</select>
```

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/foreach.png" />
</div>

**简单类型的数组:**

无论编写代码时，传递的是什么参数名`(stuNos)`，在`mapper.xml`中 必须用`array`代替该数组


**集合：**

无论编写代码时，传递的是什么参数名`(stuNos)`，在`mapper.xml`中 必须用`list`代替该数组

**对象数组：**

`Student[] students = {student0,student1,student2}`  每个`student`包含一个学号属性

**注意的几点：**

```
parameterType="Object[]" 
<foreach collection="array" open=" and  stuno in (" close=")" 			item="student" separator=",">   
	#{student.stuNo}
</foreach>

```

`SQL`片段：
​	java：方法
​	数据库：存储过程、存储函数
​	`Mybatis` :`SQL`片段 

- a、提取相似代码
- b、引用

## 关联查询：

###  一对一:
- a.业务扩展类
核心：用`resultType`指定类的属性包含多表查询的所有字段
	
- b.`resultMap`

1、通过 属性成员 将2个类建立起联系
2、

```
<resultMap type="student" id="student_card_map">
			<!-- 学生的信息 -->
			<id  property="stuNo" column="stuNo"/>
			<result property="stuName" column="stuName" />
			<result property="stuAge" column="stuAge" />
			<!-- 一对一时，对象成员使用 association映射;javaType指定该属性的类型-->
			<association property="card" javaType="StudentCard" >
					<id property="cardId" column="cardId"/>
					<result property="cardInfo" column="cardInfo"/>
			</association>
			
	</resultMap>
```

一对一：`association`
一对多：`collection`



### 一对多:

表：`student studentclass`  (关联：`classid`)
类：`student studentClas`  (关联：`List<Student> students` )




```
select  c.*,s.* from student s inner join studentclass c  on c.classid = s.classid where c.classid = 1;
```

一对多

（`MyBatis`:多对一，多对多的本质就是  一对多的变化）



**代码：**

https://github.com/ZP-AlwaysWin/Java-Learn/tree/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/%E8%BE%93%E5%87%BA%E5%8F%82%E6%95%B0%E4%B8%BA%E7%AE%80%E5%8D%95%E7%B1%BB%E5%9E%8B%E3%80%81%E5%AF%B9%E8%B1%A1%E7%B1%BB%E5%9E%8B%E3%80%81HashMap%E5%8F%8AresultMap%E4%BD%BF%E7%94%A807/moon07




