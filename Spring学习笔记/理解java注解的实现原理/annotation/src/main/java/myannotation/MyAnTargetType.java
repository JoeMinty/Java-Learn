package myannotation;

import java.lang.annotation.*;

/**
 * 定义一个可以注解在Class,interface,enum上的注解
 * 增加了@Inherited注解代表允许继承
 * @author moons
 * @date 2019年1月13日
 */
//@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnTargetType {
    /**
     * 定义注解的一个元素 并给定默认值
     * @return
     */
    String value() default "我是定义在类,接口,枚举类上的注解元素value的默认值";
}