package org.moons.test;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.moons.entity.Student;
import org.moons.mapper.StudentMapper;

public class Test {
		//查询单个学生（使用了转换器）
		public static void queryStudentByStunoWithConverter() throws IOException {
			//Connection -  SqlSession操作MyBatis
					//conf.xml - > reader
					Reader reader = Resources.getResourceAsReader("conf.xml") ;
					//reader  ->SqlSession
					
					//可以通过build的第二参数 指定数据库环境
					SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
					SqlSession session = sessionFacotry.openSession() ;
					
					StudentMapper studentMapper = session.getMapper(StudentMapper.class) ;
					Student student = studentMapper.queryStudentByStunoWithConverter(1) ;//接口中的方法->SQL语句
					
					System.out.println(student);
					session.close();
		}

	//查询单个学生
	public static void queryStudentByStuno() throws IOException {
		//Connection -  SqlSession操作MyBatis
				//conf.xml - > reader
				Reader reader = Resources.getResourceAsReader("conf.xml") ;
				//reader  ->SqlSession
				
				//可以通过build的第二参数 指定数据库环境
				SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
				SqlSession session = sessionFacotry.openSession() ;
				
				StudentMapper studentMapper = session.getMapper(StudentMapper.class) ;
				Student student = studentMapper.queryStudentByStuno(2) ;//接口中的方法->SQL语句
				
				System.out.println(student);
				session.close();
	}
	
		//查询单个学生byName
		public static void queryStudentByStuname() throws IOException {
			//Connection -  SqlSession操作MyBatis
					//conf.xml - > reader
					Reader reader = Resources.getResourceAsReader("conf.xml") ;
					//reader  ->SqlSession
					
					//可以通过build的第二参数 指定数据库环境
					SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
					SqlSession session = sessionFacotry.openSession() ;
					
					StudentMapper studentMapper = session.getMapper(StudentMapper.class) ;
					Student student = studentMapper.queryStudentByStuname("ww53") ;//接口中的方法->SQL语句
					
					System.out.println(student);
					session.close();
		}
	
	  //查询全部学生
 		public static void queryAllStudents() throws IOException {
			//Connection -  SqlSession操作MyBatis
					//conf.xml - > reader
					Reader reader = Resources.getResourceAsReader("conf.xml") ;
					//reader  ->SqlSession
					//可以通过build的第二参数 指定数据库环境
					SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
					SqlSession session = sessionFacotry.openSession() ;
					
					
//					List<Student> students = session.selectList(statement ) ;
					StudentMapper studentMapper = session.getMapper( StudentMapper.class) ;
					List<Student> students = studentMapper.queryAllStudents() ;//接口的方法->SQL
					
					System.out.println(students);
					session.close();
		}
		
 		//查询全部学生，并且根据姓名排序
 		public static void queryStudentOrderByColumn() throws IOException {
 			//Connection -  SqlSession操作MyBatis
 			//conf.xml - > reader
 			Reader reader = Resources.getResourceAsReader("conf.xml") ;
 			//reader  ->SqlSession
 			//可以通过build的第二参数 指定数据库环境
 			SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
 			SqlSession session = sessionFacotry.openSession() ;
 			
 			
 			StudentMapper studentMapper = session.getMapper( StudentMapper.class) ;
 			List<Student> students = studentMapper.queryStudentOrderByColumn("stuno") ;//接口的方法->SQL
 			
 			System.out.println(students);
 			session.close();
 		}
 		
 		//根据姓名或年龄查询学生（输入参数HashMap）
 		public static  void queryStudentBystuageOrstuNameWithHashMap() throws IOException {
 			//Connection -  SqlSession操作MyBatis
 			//conf.xml - > reader
 			Reader reader = Resources.getResourceAsReader("conf.xml") ;
 			//reader  ->SqlSession
 			//可以通过build的第二参数 指定数据库环境
 			SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
 			SqlSession session = sessionFacotry.openSession() ;
 			
 			
 			StudentMapper studentMapper = session.getMapper( StudentMapper.class) ;
 			
 			Map<String,Object> studentMap = new HashMap<>();
 			studentMap.put("stuAge", 24) ;
 			studentMap.put("stuName", "zs") ;
 			

 			List<Student> students = studentMapper.queryStudentBystuageOrstuNameWithHashMap (studentMap) ;//接口的方法->SQL
 			
 			System.out.println(students);
 			session.close();
 		}
 		//根据姓名或年龄查询学生
 		public static  void queryStudentBystuageOrstuName() throws IOException {
 			//Connection -  SqlSession操作MyBatis
 			//conf.xml - > reader
 			Reader reader = Resources.getResourceAsReader("conf.xml") ;
 			//reader  ->SqlSession
 			//可以通过build的第二参数 指定数据库环境
 			SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
 			SqlSession session = sessionFacotry.openSession() ;
 			
 			
 			StudentMapper studentMapper = session.getMapper( StudentMapper.class) ;
 			Student student = new Student();
 			student.setStuAge(24);
 			student.setStuName("w");
 			List<Student> students = studentMapper.queryStudentBystuageOrstuName(student) ;//接口的方法->SQL
 			
 			System.out.println(students);
 			session.close();
 		}
// 		//根据存储过程查询某个年级的学生总数
// 		public static  void queryCountByGradeWithProcedure() throws IOException {
// 			//Connection -  SqlSession操作MyBatis
// 			//conf.xml - > reader
// 			Reader reader = Resources.getResourceAsReader("conf.xml") ;
// 			//reader  ->SqlSession
// 			//可以通过build的第二参数 指定数据库环境
// 			SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
// 			SqlSession session = sessionFacotry.openSession() ;
// 			
// 			
// 			StudentMapper studentMapper = session.getMapper( StudentMapper.class) ;
// 			//通过map给 存储过程指定输入参数
// 			Map<String,Object> params = new HashMap<>();
// 			params.put("gName", "g1") ;//指定存储过程的输入参数gName的值是g1
// 			
//// 			int count= studentMapper.queryCountByGradeWithProcedure(params) ;//gName :s1
// 			
// 			studentMapper.queryCountByGradeWithProcedure(params);//调用存储过程，并传入输入参数
// 			
// 			//获取存储过程的输出参数
// 			Object count = params.get("scount") ;
// 			
// 			System.out.println(count);
// 			session.close();
// 		}
 		//根据存储过程查询某个年级的学生总数
 		public static  void queryCountByGradeWithProcedure() throws IOException {
 			//Connection -  SqlSession操作MyBatis
 			//conf.xml - > reader
 			Reader reader = Resources.getResourceAsReader("conf.xml") ;
 			//reader  ->SqlSession
 			//可以通过build的第二参数 指定数据库环境
 			SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
 			SqlSession session = sessionFacotry.openSession() ;
 			
 			
 			StudentMapper studentMapper = session.getMapper( StudentMapper.class) ;
 			//通过map给 存储过程指定输入参数
 			Map<String,Object> params = new HashMap<>();
 			params.put("gName", "g1") ;//指定存储过程的输入参数gName的值是g1
 			
// 			int count= studentMapper.queryCountByGradeWithProcedure(params) ;//gName :s1
 			
 			studentMapper.queryCountByGradeWithProcedure(params);//调用存储过程，并传入输入参数
 			
 			//获取存储过程的输出参数
 			Object count = params.get("scount") ;
 			
 			System.out.println(count);
 			session.close();
 		}
 		//根据学号 删除学生（存错过程）
 		public static  void deleteStudentByStunoWithProcedure() throws IOException {
 			//Connection -  SqlSession操作MyBatis
 			//conf.xml - > reader
 			Reader reader = Resources.getResourceAsReader("conf.xml") ;
 			//reader  ->SqlSession
 			//可以通过build的第二参数 指定数据库环境
 			SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
 			SqlSession session = sessionFacotry.openSession() ;
 			StudentMapper studentMapper = session.getMapper( StudentMapper.class) ;
 			Map<String,Object> map = new HashMap<>();
 			map.put("sno", 3) ;
 			studentMapper.deleteStuBynoWithProcedure( map) ;
 			 session.commit();
 			session.close();
 		}
 		
 		
 		 //增加学生
 		public static void addStudent() throws IOException {
 			//Connection -  SqlSession操作MyBatis
					//conf.xml - > reader
					Reader reader = Resources.getResourceAsReader("conf.xml") ;
					//reader  ->SqlSession
					//可以通过build的第二参数 指定数据库环境
					SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
					SqlSession session = sessionFacotry.openSession() ;
					
//					String statement = "org.moons.entity.studentMapper."+"addStudent";
					Student student = new Student(13,"ww3",23,"s3");
					
					
//					int count = session.insert(statement, student );//statement：指定执行的SQL    student：sql中需要的参数 （ ? ? ? ）
					StudentMapper studentMapper = session.getMapper(StudentMapper.class);
					studentMapper.addStudent(student);
					
					session.commit(); //提交事务
					
					System.out.println("增加成功");
					session.close();
		}
 		
 		 //增加学生（带转换器）
 		public static void addStudentWithConverter() throws IOException {
 			//Connection -  SqlSession操作MyBatis
					//conf.xml - > reader
					Reader reader = Resources.getResourceAsReader("conf.xml") ;
					//reader  ->SqlSession
					//可以通过build的第二参数 指定数据库环境
					SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
					SqlSession session = sessionFacotry.openSession() ;
					
//					String statement = "org.moons.entity.studentMapper."+"addStudent";
					Student student = new Student(63,"ww53",23,"s3");
					student.setStuSex(true);//1
					
					
//					int count = session.insert(statement, student );//statement：指定执行的SQL    student：sql中需要的参数 （ ? ? ? ）
					StudentMapper studentMapper = session.getMapper(StudentMapper.class);
					studentMapper.addStudentWithConverter(student);
					
					session.commit(); //提交事务
					
					System.out.println("增加成功");
					session.close();
		}
 		
 		 //删除学生
 		public static void delteStudentByStuno() throws IOException {
 			//Connection -  SqlSession操作MyBatis
					//conf.xml - > reader
					Reader reader = Resources.getResourceAsReader("conf.xml") ;
					//reader  ->SqlSession
					//可以通过build的第二参数 指定数据库环境
					SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
					SqlSession session = sessionFacotry.openSession() ;
					
//					String statement = "org.moons.entity.studentMapper."+"deleteStudentByStuno";
//					
//					int count = session.delete(statement,3) ;
					StudentMapper studentMapper = session.getMapper(StudentMapper.class);
					studentMapper.deleteStudentByStuno(13);
					
					session.commit(); //提交事务
					
					System.out.println("删除成功");
					session.close();
		}
	
 		 //修改学生
 		public static void updateStudentByStuno() throws IOException {
 			//Connection -  SqlSession操作MyBatis
					//conf.xml - > reader
					Reader reader = Resources.getResourceAsReader("conf.xml") ;
					//reader  ->SqlSession
					//可以通过build的第二参数 指定数据库环境
					SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader,"development") ;
					SqlSession session = sessionFacotry.openSession() ;
					
//					String statement = "org.moons.entity.studentMapper."+"updateStudentByStuno";
					//修改的参数
					Student student = new Student();
					//修改哪个人，where stuno =2 
//					student.setStuNo(2);
					//修改成什么样子？
					student.setStuName("ls");
					student.setStuAge(24);
					student.setGraName("s1");
					//执行
//					int count = session.update(statement,student) ;
					StudentMapper studentMapper = session.getMapper(StudentMapper.class);
					studentMapper.updateStudentByStuno(student);
					
					session.commit(); //提交事务
					
					System.out.println("修改成功");
					session.close();
		}	
	
	public static void main(String[] args) throws IOException {
//		queryStudentByStunoWithConverter();
//		queryStudentByStuno();
//		queryStudentByStuname();
//		queryStudentOrderByColumn();
//		queryStudentBystuageOrstuName();
//		queryStudentBystuageOrstuNameWithHashMap();
//		queryCountByGradeWithProcedure();
		deleteStudentByStunoWithProcedure();
//		queryStudentByaddress();
//		addStudentWithConverter();
//		queryAllStudents();
//		addStudent();
//		delteStudentByStuno();
//		updateStudentByStuno();
//		queryAllStudents();
	}
}