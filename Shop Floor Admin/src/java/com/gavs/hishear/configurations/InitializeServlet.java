/**
 * InitializeServlet.java
 * 
 */
package com.gavs.hishear.configurations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Pinky S
 */
public class InitializeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		ApplicationContext contextTest = new ClassPathXmlApplicationContext(
				getServletConfig().getInitParameter("contextConfig"));
		BeanFactory factoryTest = (BeanFactory) contextTest;
		InitialFile appTest = (InitialFile) factoryTest.getBean("initialFile");
		appTest.setDBProperties();
	}
}
