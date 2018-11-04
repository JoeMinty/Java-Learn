package org.moons.dao;

import org.moons.entity.Student;
import org.springframework.stereotype.Repository;
/*
 * 
 * <bean id="studentDao" class="org.moons.dao.StudentDaoImpl">
 */
//@Component("studentDao")
@Repository
public class StudentDaoImpl {
	public void addStudent(Student student) {

		System.out.println("增加学生...");
	}
}
