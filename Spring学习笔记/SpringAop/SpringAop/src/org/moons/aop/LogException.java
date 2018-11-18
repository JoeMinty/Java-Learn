package org.moons.aop;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

public class LogException implements ThrowsAdvice {
	//�쳣֪ͨ�ľ��巽��
	public void afterThrowing(Method method, Object[] args ,Object target, NullPointerException ex)//ֻ����NullPointerException���͵��쳣
	{
		System.out.println("00000000000�쳣֪ͨ��Ŀ�����:"+target+",��������"+method.getName()+",�����Ĳ���������"+args.length+",�쳣����:"+ex.getMessage());
	}
}
