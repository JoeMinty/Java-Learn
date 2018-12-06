## 输出参数为简单类型、对象类型、HashMap及resultMap使用07

### 输出参数resultType 

1、简单类型（8个基本+`String`）
2、输出参数为实体对象类型
3、输出参数为实体对象类型的集合 ：虽然输出类型为集合，但是`resultType`依然写集合的元素类型`（resyltType="Student"）`
4、输出参数类型为`HashMap`
	--`HashMap`本身是一个集合，可以存放多个元素，
	 但是根据提示发现  返回值为`HashMap`时  ，查询的结果只能是1个学生`（no,name）`；
**结论：**
一个`HashMap`对应一个学生的多个元素（多个属性）  `[一个map，一个学生]`

二维数组

```
{
	{1,zs,23,xa},    -一个HashMap对象
	{2,ls,24,bj}, 
	{3,ww,25,tj}
}
```

### `resultType`
`resultMap`:实体类的属性、数据表的字段： 类型、名字不同时`（stuno,id）`
**注意：**当属性名 和字段名 不一致时，除了使用`resultMap`以外，还可以使用`resultType+HashMap`

#### a、resultMap

```
<resultMap type="student" id="queryStudentByIdMap">
		<!-- 指定类中的属性 和 表中的字段 对应关系 -->
		<id property="stuNo"  column="id" />
		<result property="stuName" column="name" />
</resultMap>
```
#### b、resultType+HashMap
`select`表的字段名 "类的属性名" `from...` 来制定字段名 和属性名的对应关系

```
<select id="queryStudentByIdWithHashMap" 	 parameterType="int" resultType="student" >
	select id "stuNo",name "stuName" from student where id = #{id}
</select>
```

**注意:**  如果如果10个字段，但发现 某一个字段结果始终为默认值`（0，0.0，null）`，则可能是表的字段和类的属性名字写错。