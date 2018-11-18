package org.moons.dao.impl;

import org.moons.dao.IStudentDao;
import org.moons.entity.Student;
import org.springframework.stereotype.Repository;
/*
 * 
 * <bean id="studentDao" class="org.moons.dao.StudentDaoImpl">
 */
//@Component("studentDao")
@Repository
public class StudentDaoImpl  implements IStudentDao{
	public void addStudent(Student student) {
		System.out.println("����ѧ��...");
	}
}
