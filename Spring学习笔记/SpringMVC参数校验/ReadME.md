# `SpringMVC`参数校验



## `SpringMVC`的`POST`请求的`RequestBody`体校验

### 1、搭建`Web`工程并引入`hibernate-validate`依赖

使用`SpringMVC`时配合`hibernate-validate`进行参数的合法性校验，能节省一定的代码量。

```
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>xxx</version>
</dependency>
```

### 2、使用校验注解标准在属性上`(DTO)`



<div align="center">
<img src="https://github.com/ZP-AlwaysWin/Java-Learn/blob/master/Spring%E5%AD%A6%E4%B9%A0%E7%AC%94%E8%AE%B0/%E5%9B%BE%E7%89%87/validated%E6%A0%A1%E9%AA%8C%E5%B1%9E%E6%80%A7%E5%88%86%E7%B1%BB.jpg" />
</div>





每个注解都有`message`属性，该属性用于填写校验失败时的异常描述信息，当校验失败时可以获取对应的`message`属性值。

```
public class User {

    @NotNull(message="id不能为空!")
    private Integer id;
    ...

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
	...

    public User() {
        super();
    }

}
```

### 3.控制层中使用`DTO`接收参数并使用`@Validated/@Valid`注解开启对参数的校验


- `@Validated`注解表示开启`Spring`的校验机制，支持分组校验,声明在入参上。

- `@Valid`注解表示开启`Hibernate`的校验机制，不支持分组校验,声明在入参上。

- 在`DTO`后面紧跟`BindingResult`对象，那么当参数不符合时，能通过该对象直接获取不符合校验的`message`描述信息。

- 若使用了`@Validated/@Valid`注解开启校验，但`DTO`后面没有紧跟`BindingResult`对象，那么当参数不符合时，将直接返回`400 Bad Request`状态码。 

```
@RestController
public class BaseController {

    @RequestMapping("/test")
    public User test(@Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println(error.getDefaultMessage());
            }
        }
        return user;
    }

}
```

### 4.关于`@Valid`和`@Validated`的区别联系
#### 1.包位置

- `@Valid: javax.validation`， 是`javax`，也是就是`jsr303`中定义的规范注解
- `@Validated: org.springframework.validation.annotation`， 是`Spring`自己封装的注解。
#### 2.功能
`@Valid`就不用说了，是`jsr303`的规范。我们打开`@Validated`的源码，可以看到以下注释，

```
Variant of JSR-303's {@link javax.validation.Valid}, supporting the
specification of validation groups. Designed for convenient use with
Spring's JSR-303 support but not JSR-303 specific.
```

大致意思就是说`@Validated`是`@Valid`的一个变种，扩展了`@Valid`的功能，支持`group`分组校验的写法。
那么我们对于`@Valid`和`@Validated`就可以这么理解：

- 能用`@Valid`的地方通常可以用`@Validated`替代。
- 需要使用分组校验的时候使用`@Validated`注解。



### 5.分组校验

每个校验注解都有`group`属性用于指定校验所属的组，其值是`Class`数组，在`Controller`中使用`@Validated`注解开启对参数的校验时若指定要进行校验的组，那么只有组相同的属性才会被进行校验(默认全匹配)

```
Class<?>[] groups() default { };
```
一般定义标识接口作为组资源

```
public interface GroupA {

}

public interface GroupB {

}
```
使用校验注解标注在属性上并进行分组

```
public class UserGroup {

    @NotNull(message="id不能为空!",groups = {GroupA.class})
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	...
    public UserGroup() {
        super();
    }

}
```

后端一般只返回重名等错误描述信息，对于非空、字符长度、手机邮箱合法性校验等由前端进行判断并提示，后端校验不通过时不返回错误描述信息，所以不需要使用`BindingResult`获取错误描述，当参数不符合时直接返回`400 Bad Request`请求。

## `SpringMVC`的`GET`请求的`RequestParam`校验

### 一、概述

`Spring BeanValidation`可以用来校验我们客户的提交的参数对应的`Bean`对象。但是在很多情况，我们的参数只有一个简单字符串或者是数字型参数(采用`@RequestParam`注解)，要想使用`SpringValidation`还需要把这简单的参数包装成对象，甚是麻烦。下面我们看下，怎么样才能使`@RequestParam`注解的参数也可以使用`Spring Validation`呢。

### 二、方案

1、`Spring`容器注入`MethodValidationPostProcessor`对象

```
<bean class="org.springframework.validation.beanvalidation.MethodValidationPostProcessor"/>
```

2、使用`@Validated`注解在对应的`Controller`中加上`@Validated`注解

3、使用校验注解

```
@RestController
@Validated
public class TestController {

    @RequestMapping(value = "/getvalidateds",method = RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String test(@NotBlank(message = "姓名不能为空") @RequestParam("name") String name) {
         return name
     }
}

```

4、全局校验异常捕捉


```
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(ConstraintViolationException e){
        for(ConstraintViolation<?> s:e.getConstraintViolations()){
            return s.getInvalidValue()+": "+s.getMessage();
        }
        return "请求参数不合法";
    }
}

```


## `SpringMVC`的请求`URL`校验

### 参考资料

这两个链接是通过切面实现全局捕捉参数校验，具备参考价值
https://www.jianshu.com/p/29a51941df2a
https://www.jianshu.com/p/420f7d8735ef
https://segmentfault.com/a/1190000014454607
