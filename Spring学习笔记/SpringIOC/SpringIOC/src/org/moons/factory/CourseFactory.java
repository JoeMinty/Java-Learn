package org.moons.factory;

import org.moons.newinstance.HtmlCourse;
import org.moons.newinstance.ICourse;
import org.moons.newinstance.JavaCourse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//课程工厂
public class CourseFactory {
    //根据名字获取课程
	public static ICourse getCourse(String name) {
        //1.0 工厂模式
//	    if(name.equals("java")) {
//            ICourse course = new JavaCourse();
//	        return course;
//		}else {
//            ICourse course=new HtmlCourse();
//            return course;
//		}

//		//2.0 获取ioc容器
		ApplicationContext conext = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		if(name.equals("java")) {
			return  (ICourse)conext.getBean("javaCourse");//new->从ioc获取
		}else {
			return (ICourse)conext.getBean("htmlCourse");
		}
	}

}
