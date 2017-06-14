package com.jst.system.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import com.jst.util.PropertyUtil;

public class InitServlet extends HttpServlet implements ServletContextListener{

	public static String CURRENT_PROJECT_PATH = null;
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContext) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
		CURRENT_PROJECT_PATH = servletContext.getServletContext().getRealPath("/");
		PropertyUtil._setProperties("web.properties");
		
	}

	
}
