# 使用`Spring AOP`和自定义注解进行参数检查


## 引言

​	使用`SpringMVC`作为`Controller`层进行`Web`开发时，经常会需要对`Controller`中的方法进行参数检查。本来`SpringMVC`自带`@Valid`和`@Validated`两个注解可用来检查参数，但只能检查参数是`bean`的情况，对于参数是`String`、`Long`等`Java`自带类型的就不适用了（但是还可以用`@NotNull`、`@NotBlank`、`@NotEmpty`等），而且有时候这两个注解又突然失效了（没有仔细去调查过原因）。对此，其实我们自己也可以利用`Spring`的`AOP`和自定义注解，自己写一个参数校验的功能。

## 代码示例

### 自定义注解：

**`ValidParam.java：`**

```
package com.moons.paramcheck.annotation;

import java.lang.annotation.*;

/**
 * 标注在参数bean上，表示需要对该参数校验
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidParam {


}
```



**`NotNull.java：`**

```
package com.moons.paramcheck.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNull {

    String msg() default "字段不能为空";

}

```



**`NotEmpty.java：`**



```
package com.moons.paramcheck.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotEmpty {

    String msg() default "字段不能为空";

}

```

### 切面类：

**`ParamCheckAspect.java：`**

...

### 参数JavaBean

**`StudentParam.java：`**

...

### 验证参数校验的Controller

**`TestController.java：`**

...