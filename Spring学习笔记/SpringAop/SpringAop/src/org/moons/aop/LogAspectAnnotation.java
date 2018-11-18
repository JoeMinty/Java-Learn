package org.moons.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
//<bean id="logAnnotation" class="org.moons.aop.LogAspectAnnotation">
//@Component("logAnnotation")   //��LogAspectAnnotation����springIOC������
@Aspect //������һ��֪ͨ
public class LogAspectAnnotation  {
	
	//ǰ��֪ͨ
	@Before("execution(public * addStudent(..))") //���ԣ������е�
	public void myBefore(JoinPoint jp) {
		System.out.println("��ע����ʽ-ǰ��֪ͨ����Ŀ�����"+jp.getTarget()+",��������"+jp.getSignature().getName() +",�����б�"+ jp.getArgs().length  );
	}
	//����֪ͨ
	@AfterReturning( pointcut= "execution(public * addStudent(..))" ,returning="returningValue" ) 
	public void myAfter(JoinPoint jp,Object returningValue) {//returningValue�Ƿ���ֵ������Ҫ����spring
		System.out.println("��ע����ʽ-����֪ͨ����Ŀ�����"+jp.getTarget()+",��������"+jp.getSignature().getName() +",�����б�"+  jp.getArgs().length+",����ֵ��"+returningValue );
	}
	
	/*����֪ͨ ,����ProceedingJoinPoint
	@Around("execution(public * addStudent(..))")
	public void myAround(ProceedingJoinPoint jp  ) {
		//����֮ǰ��ǰ��֪ͨ
		System.out.println("�������ơ�����֮ǰ��ǰ��֪ͨ");
		
		try {
			//����ִ��ʱ
			jp.proceed() ;//ִ�з���
	
			//����֮ǰ֮�󣺺���֪ͨ
			System.out.println("�������ơ�����֮ǰ֮�󣺺���֪ͨ");
		}catch(Throwable e) {
			//�����쳣ʱ���쳣֪ͨ
			System.out.println("�������ơ������쳣ʱ���쳣֪ͨ");
		}finally {
			//����֪ͨ
			System.out.println("�������ơ�����֪ͨ");
		}
	}*/
	
	//�쳣֪ͨ:���ֻ�����ض����͵��Ѵ����У������ͨ���ڶ�������ʵ�֣�e
	@AfterThrowing(pointcut= "execution(public * addStudent(..))",throwing="e")
	public void myException(JoinPoint pj, NullPointerException e) {//���쳣֪ͨ ֻ�Ჶ��NullPointerException���͵��쳣
		System.out.println("&&&&&&��ע����ʽ-�쳣֪ͨ��----e:"+e.getMessage());
	}
	
	//����֪ͨ
	@After("execution(public * addStudent(..))")
	public void myAfter() {
		System.out.println("��[myAfter]ע����ʽ-����֪ͨ-----֪ͨ��----");
	}
	
	
}
