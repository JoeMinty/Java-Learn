# 入参为HashMap、MyBatis调用存储过程执行CRUD



**输入对象为HashMap：**

`where stuage= #{stuAge}`

用`map`中`key`的值 匹配 占位符`#{stuAge}`，如果匹配成功 就用`map`的`value`替换占位符

## `mybatis`调用存储过程

```
<select id="queryCountByGradeWithProcedure" statementType="CALLABLE" parameterType="HashMap" >
{
CALL queryCountByGradeWithProcedure(
#{gName,jdbcType=VARCHAR,mode=IN},
#{scount,jdbcType=INTEGER,mode=OUT}
) 
}	
</select>
```



其中 通过`statementType="CALLABLE"`设置`SQL`的执行方式是存储过程。 存储过程的输入参数`gName`需要通过`HashMap`来指定
在使用时，通过`hashmap`的`put`方法传入输入参数的值；通过`hashmap`的`Get`方法 获取输出参数的值。



### 若使用的`Oracle`数据库要注意`Jar`问题：

`ojdbc6.jar`不能在调存储过程时打回车、`tab`，但是`ojdbc7.jar`可以。



### 如果报错： 

`No enum constant org.apache.ibatis.type.JdbcType.xx`，则说明`mybatis`不支持`xx`类型，需要查表，查数据库、`JDBC`、java代码的映射表。


存储过程 无论输入参数是什么值，语法上都需要 用`map`来传递该值；

只要 是 ` <transactionManager type="JDBC" />`，则增删改`CUD`都需要手工`commit` 



### 正常使用创建`MyBatis`的过程：

`mapper.xml`->`mapper`接口->测试方法