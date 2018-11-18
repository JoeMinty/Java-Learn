package org.moons.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LogAround  implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object result  = null ;
		//������1...
		try {
			//������2...
			System.out.println("�û���֪ͨʵ�ֵ�[ǰ��֪ͨ]...");
			
			// invocation.proceed() ֮ǰ�Ĵ��룺ǰ��֪ͨ
			 result  = invocation.proceed() ;//������Ŀ�귽����ִ��  ��addStudent()
			//result ����Ŀ�귽��addStudent()�����ķ���ֵ
//			 invocation.proceed() ֮��Ĵ��룺����֪ͨ
			System.out.println("�û���֪ͨʵ�ֵ�[����֪ͨ]...:");
			System.out.println("-----------------Ŀ�����target"+invocation.getThis()+",���õķ�������"+invocation.getMethod().getName()+",�����Ĳ���������"+invocation.getArguments().length+",����ֵ��"+result);
			

			
		}catch(Exception e) {
			//������3...
			//�쳣֪ͨ
			System.out.println("�û���֪ͨʵ�ֵ�[�쳣֪ͨ]...");
		}
		
		return result;//Ŀ�귽���ķ���ֵ
	}

}
