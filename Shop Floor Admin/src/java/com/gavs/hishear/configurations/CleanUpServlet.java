/**
 * InitializeServlet.java
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
public class CleanUpServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method to Initialize the Servlet
	 * 
	 * @throws ServletException
	 */
	public void init() throws ServletException {

		// Declare and Initialize the Application Context
		ApplicationContext contextTest = new ClassPathXmlApplicationContext(
				getServletConfig().getInitParameter("contextConfigCleanUp"));
		// Create the Bean Factory and pass the Appl Context
		BeanFactory factoryTest = (BeanFactory) contextTest;
		// Get the Bean to run the Servlet
		CleanUpFile appTest = (CleanUpFile) factoryTest.getBean("cleanUp");

		// Method call to delete the Temp properties file
		appTest.deletePropertyFile();
	}

}
