package com.app.servletcontext;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EmployServletConfig
 */
//@WebServlet("/emp")
public class EmployServletContext extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String sql="select * from emp where id=? ";
	Connection con=null;
	PreparedStatement ps=null;
	
	public void init(ServletConfig config)throws ServletException{
		System.out.println("init()");
		
		ServletContext context=config.getServletContext();
		
		String driver=context.getInitParameter("driver");
		String url=context.getInitParameter("url");
		String userId=context.getInitParameter("userId");
		String password=context.getInitParameter("password");
		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con=DriverManager.getConnection(url,userId,password);
			ps=con.prepareStatement(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		int id=Integer.parseInt(req.getParameter("id"));
		try {
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				out.println("<h3>Employee Details</h3><hr>");
				out.println("Employee Id : " +rs.getInt(1) +"<br>");
				out.println("Employee Name : " +rs.getString(2)+"<br>");
				out.println("Employee Salary : " +rs.getInt(3));
			}
			else {
				out.println("No data found with id " +id);
			}
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		
	}
	public void destroy() {
		System.out.println("destroy()");
		try {
			if(ps!=null) {
				ps.close();
			}
			if(con!=null) {
				con.close();
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
