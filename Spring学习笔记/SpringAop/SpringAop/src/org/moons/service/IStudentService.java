package org.moons.service;

import org.moons.entity.Student;

public interface IStudentService {
	void addStudent(Student student);
	
	void deleteStudentByNo(int stuNo) ;
}
