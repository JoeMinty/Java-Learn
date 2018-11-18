package org.moons.factory;

import org.moons.newinstance.ICourse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//�γ̹���
public class CourseFactory {
	//�������ֻ�ȡ�γ�
//	public static ICourse getCourse(String name) {
//		//��ȡioc����
//		ApplicationContext conext = new ClassPathXmlApplicationContext("applicationContext.xml") ;
//		if(name.equals("java")) {
//			return  (ICourse)conext.getBean("javaCourse");//new->��ioc��ȡ
//		}else {
//			return (ICourse)conext.getBean("htmlCourse");
//		}
//	}
	
}
