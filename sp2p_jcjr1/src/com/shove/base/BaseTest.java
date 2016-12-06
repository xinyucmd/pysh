package com.shove.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.chainsaw.Main;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * 测试基类
 */
public class BaseTest extends AbstractDependencyInjectionSpringContextTests {
	public static Log log = LogFactory.getLog(BaseTest.class);
	public String[] getConfigLocations() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		return new String[] { "classpath:beans_all.xml" };
	}
	
	public static void main(String[] args) {
		StringBuffer command = new StringBuffer();
		command.append("SELECT a.id, (c.usableSum+c.freezeSum) totalSum,c.usableSum,DATE_FORMAT(a.repayDate,'%Y-%m-%d') repayDate,");
		command.append(" (a.stillPrincipal+a.stillInterest-a.hasPI) forPI,a.lateFI,(a.stillPrincipal+a.stillInterest-a.hasPI+a.lateFI) needSum");
		command.append(" FROM t_repayment a LEFT JOIN t_borrow b ON a.borrowId=b.id LEFT JOIN t_user c ON b.publisher=c.id where a.id="+1+" limit 0,1");
		
		System.out.println(command);
	}
}
