package org.moons.mapper;

import java.util.List;

import org.moons.entity.StudentClass;

//操作Mybatis的接口
public interface StudentClassMapper {
	//查询全部班级
	List<StudentClass> queryClassAndStudents();
}
