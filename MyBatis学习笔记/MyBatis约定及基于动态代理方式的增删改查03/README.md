# MyBatis约定及基于动态代理方式的增删改查

## 二、`mapper`动态代理方式的`crud` （`MyBatis`接口开发）:


**原则：约定优于配置**

### 硬编码方式



```
abc.java
Configuration conf = new Configuration();
con.setName("myProject") ;
```

### 配置方式：



```
abc.xml   
<name>myProject</name>
```



约定：默认值就是`myProject`



### 具体实现的步骤：

1、基础环境：`mybatis.jar/ojdbc.jar、conf.xml、mapper.xml`

2、（不同之处）
​	约定的目标： 省略掉`statement`,即根据约定 直接可以定位出`SQL`语句
#### a.接口，接口中的方法必须遵循以下约定：
- 1、方法名和`mapper.xml`文件中标签的`id`值相同
- 2、方法的 输入参数 和`mapper.xml`文件中标签的` parameterType`类型一致 (如果`mapper.xml`的标签中没有`parameterType`，则说明方法没有输入参数)
- 3、方法的返回值和`mapper.xml`文件中标签的 `resultType`类型一致 （无论查询结果是一个 还是多个`（student、List<Student>）`，在`mapper.xml`标签中的`resultType`中只写 一个`（Student）`；如果没有`resultType`，则说明方法的返回值为`void`）

除了以上约定，要实现接口中的方法和`Mapper.xml`中`SQL`标签一一对应，还需要以下1点：
`namespace`的值 ，就是 接口的全类名（ 接口 - `mapper.xml` 一一对应）


#### 匹配的过程：（约定的过程）
- 1、根据 接口名 找到 `mapper.xml`文件（根据的是`namespace=接口全类名`）
- 2、根据 接口的方法名 找到 `mapper.xml`文件中的`SQL`标签 （`方法名=SQL标签Id值`）

以上2点可以保证： 当我们调用接口中的方法时，
程序能自动定位到 某一个`Mapper.xml`文件中的`sql`标签



**习惯：**`SQL`映射文件（`mapper.xml`） 和 接口放在同一个包中 （注意修改`conf.xml`中加载`mapper.xml`文件的路径）




#### 以上，可以通过接口的方法->SQL语句

执行：

```
StudentMapper studentMapper = session.getMapper(StudentMapper.class) ;
studentMapper.方法();
```

通过`session`对象获取接口`（session.getMapper(接口.class);）`，再调用该接口中的方法，程序会自动执行该方法对应的`SQL`。
