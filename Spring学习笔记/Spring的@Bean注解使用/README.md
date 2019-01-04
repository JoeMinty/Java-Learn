# `Spring`的`@Bean`注解使用



## `@Bean的定义`

`Spring`的`@Bean`注解用于告诉方法，产生一个`Bean`对象，然后这个`Bean`对象交给`Spring`管理。产生这个`Bean`对象的方法`Spring`只会调用一次，随后这个`Spring`将会将这个`Bean`对象放在自己的`IOC`容器中。



```
@Service
public class BeanTest {
    

    @Bean
    public   BeanTest  getBean(){
        BeanTest bean = new  BeanTest();
        System.out.println("调用方法："+bean);
        return bean;
    }

}

public class Main {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        Object bean1 = context.getBean("getBean");

        System.out.println(bean1);
        Object bean2 = context.getBean("getBean");
        System.out.println(bean2);
    }
}

```



输出：

```
调用方法：Spring.BeanTest@5a4041cc
Spring.BeanTest@5a4041cc
Spring.BeanTest@5a4041cc
```

默认bean的名称就是其方法名。但是也可以指定名称：

```
 @Bean(name={"moons","alwaywin"},initMethod = "initMethod",destroyMethod = "stop")
    public   BeanTest  getBean(){
        BeanTest bean = new  BeanTest();
        System.out.println("调用方法："+bean);
        return bean;
    }

```



## 注解`@Bean`的源码解析

首先我们看一下`@Bean`注解的源代码如下：

```
package org.springframework.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.core.annotation.AliasFor;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
    @AliasFor("name")
    String[] value() default {};

    @AliasFor("value")
    String[] name() default {};

    Autowire autowire() default Autowire.NO;

    String initMethod() default "";

    String destroyMethod() default "(inferred)";
}

```



我们发现`@Bean`注解的`@Target`是`ElementType.METHOD, ElementType.ANNOTATION_TYPE`也就说`@Bean`注解可以在使用在方法上，以及一个注释类型声明



具体参数如下：



- `value`-- `bean`别名和`name`是相互依赖关联的，`value`,`name`如果都使用的话值必须要一致
- `name` -- `bean`名称，如果不写会默认为注解的方法名称
- `autowire` -- 自定装配默认是不开启的，建议尽量不要开启，因为自动装配不能装配基本数据类型、字符串、数组等，这是自动装配设计的局限性，以及自动装配不如显示依赖注入精确
- `initMethod` -- `bean`的初始化之前的执行方法，该参数一般不怎么用，因为可以完全可以在代码中实现
- `destroyMethod` -- `bean`销毁执行的方法



**注意：**

- `@Configuration`相当于配置文件的`<beans>`

- `@Bean` 相当于`<bean>`

