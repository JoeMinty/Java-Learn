package org.moons.entity;

import org.moons.factory.CourseFactory;
import org.moons.newinstance.HtmlCourse;
import org.moons.newinstance.ICourse;
import org.moons.newinstance.JavaCourse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Student {
	private int stuNo ; 
	private String stuName ; 
	private int stuAge ;
	public int getStuNo() {
		return stuNo;
	}
	public void setStuNo(int stuNo) {
		this.stuNo = stuNo;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public int getStuAge() {
		return stuAge;
	}
	public void setStuAge(int stuAge) {
		this.stuAge = stuAge;
	}
	
	@Override
	public String toString() {
		return this.stuNo+","+this.stuName+","+this.stuAge;
	}
	
	
	
	
	public void learn(String name) {//ѧϰ�κογ�  java
		//1.���Լ���д�ļ򵥹����л�ȡ �γ�
//		ICourse course = CourseFactory.getCourse(name) ;
		//course���� ����name�õ�����Ӧ�Ŀγ�
		
		
		//ֱ�Ӵ�ioc�����л�ȡ
		//2.��SPringIOC�ṩ�ĳ��������л�ȡ �γ̣�֮ǰ���ù�bean��
		ApplicationContext conext = new ClassPathXmlApplicationContext("applicationContext.xml") ;
		ICourse course = (ICourse)conext.getBean(name);
		
		course.learn();
		
		
		
	}
	
	//ѧϰJava�γ�
	public void learnJava() {
		ICourse course = new JavaCourse();
		course.learn(); 
	}
	
	
	//ѧϰHtml�γ�
	public void learnHtml() {
	
		ICourse course = new  HtmlCourse() ;
		course.learn(); 
	}
}
