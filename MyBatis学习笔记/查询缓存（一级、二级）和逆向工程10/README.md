# 查询缓存（一级、二级）和逆向工程



## 查询缓存



###  一级缓存 ：同一个SqlSession对象

`MyBatis`默认开启一级缓存，如果用同样的`SqlSession`对象查询相同的数据，则只会在第一次查询时向数据库发送`SQL`语句，并将查询的结果 放入到`SQLSESSION`中（作为缓存在）；
后续再次查询该同样的对象时，则直接从缓存中查询该对象即可（即省略了数据库的访问）	

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E4%B8%80%E7%BA%A7%E7%BC%93%E5%AD%98.png" />
</div>



### 二级缓存


`MyBatis`默认情况没有开启二级缓存，需要手工打开。

a、`conf.xml`

```
<!-- 开启二级缓存 -->
<setting name="cacheEnabled" value="true"/>
```

b、在具体的`mapper.xml`中声明开启(`studentMapper.xml`中)

```
<mapper namespace="org.moons.mapper.StudentMapper">
	<!-- 声明此namespace开启二级缓存 -->
	<cache/>
```

根据异常提示：`NotSerializableException`可知，`MyBatis`的二级缓存 是将对象 放入硬盘文件中
​	序列化：内存->硬盘
​	反序列化：硬盘->内存
​	
准备缓存的对象，必须实现了序列化接口 （如果开启的缓存`Namespace="org.moons.mapper.StudentMapper"`），可知序列化对象为`Student`，因此需要将`Student`序列化 （序列化`Student`类，以及`Student`的级联属性、和父类）
​	
触发将对象写入二级缓存的时机：`SqlSession`对象的`close()`方法。

`Mybatis`自带二级缓存：【同一个`namespace`】生成的`mapper`对象



**回顾：**`namespace`的值 就是 接口的全类名（包名.类名）， 通过接口可以产生代理对象（`studentMapper`对象）`-->namespace`决定了`studentMapper`对象的产生



**结论：**只要产生的`xxxMapper`对象 来自于同一个`namespace`，则 这些对象 共享二级缓存。



**禁用 ：**`select`标签中`useCache="false"`



```
<!-- 禁用此select的二级缓存:useCache="false"
<select id="queryStudentByStuno" 	parameterType="int"  	resultMap="studentMapping"  useCache="false">
		select * from student where stuno = #{value}
</select>
```





**清理：**

a、.与清理一级缓存的方法相同
`commit()`; （一般执行增删改时 会清理掉缓存；设计的原因 是为了防止脏数据）

在二级缓存中，`commit()`不能是查询自身的`commit`。
​	
`commit`会清理一级和二级缓存；但是 清理二级缓存时，不能是查询自身的`commit`；

b、 在`select`标签中 增加属性 `flushCache="true"`



```
<select id="queryStudentByStuno" 	parameterType="int" flushCache="true" 	resultMap="studentMapping"  useCache="true">
		select * from student where stuno = ${value}
</select>
```





**命中率：**

1:zs :		0%  

2:    		50%

3: 2/3  	0.666

4: 3/4  	0.75

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E4%BA%8C%E7%BA%A7%E7%BC%93%E5%AD%98.png" />
</div>



### 三方提供的二级缓存：

`ehcache、memcache`

要想整合三方提供的二级缓存 （或者自定义二级缓存），必须实现`org.apache.ibatis.cache.Cache`接口，该接口的默认实现类是`PerpetualCache`
​		
整合`ehcache`二级缓存：

a、
​	`ehcache-core.jar`
​	`mybatis-Ehcache.jar`
​	`slf4j-api.jar`
​		
b、编写`ehcache`配置文件`Ehcache.xml`
​	
c、开启`EhCache`二级缓存
​	
在`xxxMapper.xml`中开启

```
<cache  type="org.mybatis.caches.ehcache.EhcacheCache">
	<!-- 通过property覆盖Ehcache.xml中的值 -->
	<property name="maxElementsInMemory" value="2000"/>
	<property name="maxElementsOnDisk" value="3000"/>
</cache>
```


## 逆向工程

表、类、接口、`mapper.xml`四者密切相关，因此 当知道一个的时候  其他三个应该可以自动生成。

表->其他三个


### 实现步骤：

```
a.  mybatis-generator-core.jar、mybatis.jar、ojdbc.jar
b.  逆向工程的配置文件generator.xml
c.  执行
```

<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/MyBatis%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/MyBatis%E5%9B%BE%E7%89%87/%E9%80%86%E5%90%91%E5%B7%A5%E7%A8%8B.png" />
</div>
