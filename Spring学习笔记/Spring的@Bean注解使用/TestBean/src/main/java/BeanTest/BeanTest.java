package BeanTest;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

//加上这个注解，这个类会默认搞出一个Bean，名字为beanTest
//@Service
public class BeanTest {

    public void initMethod(){
        System.out.println("moons Bean执行初始化方法");
    }

    public void stop(){
        System.out.println("moons Bean执行结束方法");
    }


    @Bean(name={"moons","alwaywin"},initMethod = "initMethod",destroyMethod = "stop")
    public   BeanTest  getBean(){
        BeanTest bean = new  BeanTest();
        System.out.println("调用方法："+bean);
        return bean;
    }

    @Bean
    public   BeanTest  getBeanNoName(){
        BeanTest bean = new  BeanTest();
        System.out.println("调用使用默认名字的方法："+bean);
        return bean;
    }

}

