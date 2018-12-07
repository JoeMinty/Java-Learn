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



**`<where>`**

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



**`<foreach>`**
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
​	Mybatis :SQL片段 

- a、提取相似代码
- b、引用

**代码：**

