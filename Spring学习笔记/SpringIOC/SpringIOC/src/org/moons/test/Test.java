package org.moons.test;

import org.moons.entity.AllCollectionType;
import org.moons.entity.Course;
import org.moons.entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {


	public static void springIoc() {
		//1、传统方法创建对象，获取类的对象
//		Student student = new Student();
//		student.setStuNo(1);
//		student.setStuName("zs");
//		student.setStuAge(23);
//		System.out.println(student);

		//2、Spring上下文对象：context
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		//执行从springIOC容器中获取一个 id为student的对象
		Student student = (Student)context.getBean("student") ;
		System.out.println(student);
		//以上步骤可以分解成以下两部
		//1.new
		//2.对象属性的复制

	}

	//传统1.0版本获取课程的方法
	public static void learnCourse() {
		Student student = new Student();
		student.learnHtml();
		student.learnJava();
	}

	//传统2.0工厂模式获取课程的方法
	public static void learnCourseWithFactory() {
		Student student = new Student();
		student.learn("java");

	}

	//传统3.0从IOC超级工厂中获取课程的方法
	public static void learnCourseWithIoC() {
		ApplicationContext conext = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		//从IOC获取学生对象
		Student student = (Student)conext.getBean("student") ;
		student.learn("javaCourse");
	}

		public static void testDI() {
		ApplicationContext conext = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		Course course = (Course)conext.getBean("course") ;
		course.showInfo();
	}

	public static void collectionDemo() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		AllCollectionType type = (AllCollectionType)context.getBean("collectionDemo") ;
		System.out.println(type);
	}




	public static void main(String[] args) {

//		springIoc();

//		learnCourse();
//		learnCourseWithFactory();
//		learnCourseWithIoC();
		testDI();
//		collectionDemo();



	}
}

