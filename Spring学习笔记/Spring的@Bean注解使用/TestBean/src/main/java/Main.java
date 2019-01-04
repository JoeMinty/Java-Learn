import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    //@SuppressWarnings用于抑制编译器产生警告信息
    @SuppressWarnings("unused")
    public static void main(String[] args) {

        //加载所有Bean定义从配置文件中
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        //加载单独的类
        //ApplicationContext context = new AnnotationConfigApplicationContext(BeanTest.BeanTest.class);


        //获取当前Spring IOC容器的所有Bean
        String[] allBeanNames = context.getBeanDefinitionNames();

        System.out.println("开始打印当前IOC容器所有的bean");
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
        System.out.println("结束打印当前IOC容器所有的bean");


        Object bean1 = context.getBean("moons");

        System.out.println(bean1);
        Object bean2 = context.getBean("alwaywin");
        System.out.println(bean2);
        //加载单个类的时候没有该方法
        context.close();

    }
}
