package org.moons.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class LogSchema {
	
	//����֪ͨ����  :JoinPoint������ע��
	public void afterReturning(JoinPoint jp,Object returnValue) throws Throwable {
		System.out.println("��������������������������֪ͨ��Ŀ�����"+jp.getThis()+",���õķ�������"+jp.getSignature().getName()+",�����Ĳ���������"+jp.getArgs().length+"�������ķ���ֵ��"+returnValue);
	}
	
	public void before() {
		System.out.println("����������������������ǰ��֪ͨ...");
	}
	
	public void whenException(JoinPoint jp,NullPointerException e) {
		System.out.println(">>>>>>>>>>>>>>>>�쳣��" +e.getMessage());
	}
	//ע�⣺����֪ͨ �᷵��Ŀ�귽���ķ���ֵ����˷���ֵΪObject
	public Object around(ProceedingJoinPoint jp)    {
		System.out.println("''''''''''''''''''����֪ͨ��ǰ��֪ͨ");
		Object result = null ; 
		try {
			 result = jp.proceed() ;//ִ�з���
			 System.out.println("'''''''''"+jp.getSignature().getName()+","+result);
			System.out.println("''''''''''''''''''����֪ͨ������֪ͨ");
		}catch(Throwable e) {
			System.out.println("''''''''''''''''''����֪ͨ���쳣֪ͨ");
		}
		return result ;
	}

}
