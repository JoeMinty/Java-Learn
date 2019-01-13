package test;

import myannotation.MyAnTargetType;

/**
 * 增加一个子类继承AnnotationTest 演示@Inherited注解允许继承
 *
 * @author moons
 * @date 2019年1月13日
 */
public class ChildAnnotationTest extends AnnotationTest {
    public static void main(String[] args) {
        // 获取类上的注解MyAnTargetType

        MyAnTargetType t = ChildAnnotationTest.class.getAnnotation(MyAnTargetType.class);
        System.out.println("类上的注解值 === "+t.value());
    }
}