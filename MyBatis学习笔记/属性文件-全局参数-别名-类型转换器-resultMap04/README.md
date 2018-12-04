# 属性文件-全局参数-别名-类型转换器-resultMap

## 优化
### 1、可以将配置信息 单独放入 `db.properties`文件中，然后再动态引入

```
db.properties：
k=v
```

```
<configuration>

	<properties  resource="db.properties"/>
```

引入之后，使用`${key}`

### 2、MyBatis全局参数


| 参数                      | 简介                                                         | 有效值                             |
| ------------------------- | ------------------------------------------------------------ | ---------------------------------- |
| cacheEnabled              | 在全局范围内，启用或禁用缓存                                 | true（默认）、false                |
| lazyLoadingEnabled        | 在全局范围内启用或禁用延迟加载。当禁用时，所有相关联的对象都将立即加载（热加载）。 | true（默认）、false                |
| aggressiveLazyLoading     | 启用时，有延迟加载属性的对象，在被调用时将会完全加载所有属性（立即加载）。否则，每一个属性都将按需加载（即延迟加载）。 | true（默认）、false                |
| multipleResultSetsEnabled | 允许或禁止执行一条单独的SQL语句后返回多条结果（结果集）；需要驱动程序的支持 | true（默认）、false                |
| autoMappingBehavior       | 指定数据表字段和对象属性的映射方式。NONE：禁止自动映射，只允许手工配置的映射PARTIAL：只会自动映射简单的、没有嵌套的结果FULL：自动映射任何结果（包含嵌套等） | NONE、PARTIAL（默认）、FULL        |
| defaultExecutorType       | 指定默认的执行器。SIMPLE：普通的执行器。REUSE：可以重复使用prepared statements语句。BATCH：可以重复执行语句和批量更新。 | SIMPLE（默认）、REUSE、BATCH       |
| defaultStatementTimeout   | 设置驱动器等待数据库回应的最长时间                           | 以秒为单位的，任意正整数。无默认值 |
| safeRowBoundsEnabled      | 允许或禁止使用嵌套的语句                                     | true、false（默认）                |
| mapUnderscoreToCamelCase  | 当在数据表中遇到有下划线的字段时，自动映射到相应驼峰式形式的Java属性名。例如，会自动将数据表中的stu_no字段，映射到POJO类的stuNo属性。 | true、false（默认）                |
| lazyLoadTriggerMethods    | 指定触发延迟加载的对象的方法                                 | equals、clone、hashCode、toString  |

 

在`conf.xml`中设置

```
<!-- 
<settings>
	<setting name="cacheEnabled" value="false"  />
	<setting name="lazyLoadingEnabled" value="false"  />
</settings>
-->

```

### 3、别名 `conf.xml`
#### a.设置单个别名

#### b.批量设置别名

	<typeAliases>
		<!-- 单个别名 （别名 忽略大小写） -->
		<!-- <typeAlias type="org.moons.entity.Student" alias="student"/> -->
		<!--  批量定义别名  （别名 忽略大小写），以下会自动将该包中的所有类 批量定义别名： 别名就是类名（不带包名，忽略大小写）   -->
		<package name="org.moons.entity"/>
	</typeAliases>


除了自定义别名外，`MyBatis`还内置了一些常见类的别名。


<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/MyBatis%E5%86%85%E7%BD%AE%E5%88%AB%E5%90%8D.png" />
</div>




## 类型处理器（类型转换器）
### 1、`MyBatis`自带一些常见的类型处理器
	int  - number

	
**Mybatis内置的类型处理器如表所示。**

| 类型处理器              | Java类型              | JDBC类型                                                    |
| ----------------------- | --------------------- | ----------------------------------------------------------- |
| BooleanTypeHandler  | Boolean，boolean      | 任何兼容的布尔值                                            |
| ByteTypeHandler         | Byte，byte            | 任何兼容的数字或字节类型                                    |
| ShortTypeHandler        | Short，short          | 任何兼容的数字或短整型                                      |
| IntegerTypeHandler      | Integer，int          | 任何兼容的数字和整型                                        |
| LongTypeHandler         | Long，long            | 任何兼容的数字或长整型                                      |
| FloatTypeHandler        | Float，float          | 任何兼容的数字或单精度浮点型                                |
| DoubleTypeHandler       | Double，double        | 任何兼容的数字或双精度浮点型                                |
| BigDecimalTypeHandler   | BigDecimal            | 任何兼容的数字或十进制小数类型                              |
| StringTypeHandler       | String                | CHAR和VARCHAR类型                                           |
| ClobTypeHandler         | String                | CLOB和LONGVARCHAR类型                                       |
| NStringTypeHandler      | String                | NVARCHAR和NCHAR类型                                         |
| NClobTypeHandler        | String                | NCLOB类型                                                   |
| ByteArrayTypeHandler    | byte[]                | 任何兼容的字节流类型                                        |
| BlobTypeHandler         | byte[]                | BLOB和LONGVARBINARY类型                                     |
| DateTypeHandler         | Date（java.util）     | TIMESTAMP类型                                               |
| DateOnlyTypeHandler     | Date（java.util）     | DATE类型                                                    |
| TimeOnlyTypeHandler     | Date（java.util）     | TIME类型                                                    |
| SqlTimestampTypeHandler | Timestamp（java.sql） | TIMESTAMP类型                                               |
| SqlDateTypeHandler      | Date（java.sql）      | DATE类型                                                    |
| SqlTimeTypeHandler      | Time（java.sql）      | TIME类型                                                    |
| ObjectTypeHandler       | 任意                  | 其他或未指定类型                                            |
| EnumTypeHandler         | Enumeration类型       | VARCHAR。任何兼容的字符串类型，作为代码存储（而不是索引）。 |


### 2、自定义`MyBatis`类型处理器

	java -数据库(jdbc类型)

### 示例：
实体类`Student` :  `boolean`  `stuSex`  	
​	```
​	true:男
​	false：女
​	```

表`student`：	`number`  `stuSex`
​	```
​	1:男
​	0：女
​	```
​	

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E7%B1%BB%E5%9E%8B%E8%BD%AC%E6%8D%A2%E5%99%A8%E7%9A%84%E6%96%B9%E5%90%91.png" />
</div>


### 自定义类型转换器`（boolean -number）`步骤：
#### a.创建转换器：需要实现`TypeHandler`接口
通过阅读源码发现，此接口有一个实现类 `BaseTypeHandler` ，因此 要实现转换器有2种选择：
- i.实现接口`TypeHandler`接口
- ii.继承`BaseTypeHandler`
#### b.配置`conf.xml`


### 需要注意的问题：  `INTEGER`

```
insert into student(stuno,stuname,stuage,graname,stusex) values(#{stuNo},#{stuName},#{stuAge},#{graName} ,#{stuSex ,javaType=boolean  ,jdbcType=INTEGER   } ) 
```

注意`#{stuNo}` 中存放的是 属性值，需要严格区分大小写。

### resultMap可以实现2个功能：
#### 1、类型转换
#### 2、属性-字段的映射关系

```
<select id="queryStudentByStuno" parameterType="int"  resultMap="studentMapping">
		select * from student where stuno = #{stuno}
</select>
```

```
<resultMap type="student" id="studentMapping">
			<!-- 分为主键id 和非主键 result-->
			<id property="id"  column="stuno"  />
			<result property="stuName"  column="stuname" />
			<result property="stuAge"  column="stuage" />
			<result property="graName"  column="graname" />
			<result property="stuSex"  column="stusex"  javaType="boolean" jdbcType="INTEGER"/>
</resultMap>
```
