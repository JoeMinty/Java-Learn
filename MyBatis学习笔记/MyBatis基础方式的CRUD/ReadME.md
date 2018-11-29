# `MyBatis`基础方式的`CRUD`

### 复习第一个`MyBatis`配置及入门示例01：

- 0、`mybatis.jar`   `ojdbc.jar`
- 1、`conf.xml` (数据库配置信息、映射文件)
- 2、表-类：映射文件  `mapper.xml`
- 3、测试

### `MyBatis`约定：
输入参数`parameterType` 和 输出参数`resultType `，在形式上都只能有一个

#### 如果输入参数 ：
是简单类型（8个基本类型+String） 是可以使用任何占位符,`#{xxxx}`
如果是对象类型，则必须是对象的属性 `#{属性名}`

#### 输出参数： 
如果返回值类型是一个 对象（如Student），则无论返回一个、还是多个，
在`resultType`都写成`org.moons.entity.Student`
即 `resultType="org.moons.entity.Student"`



### 注意事项：
如果使用的 事务方式为 `jdbc`,则需要手工`commit`提交，即`session.commit();`

