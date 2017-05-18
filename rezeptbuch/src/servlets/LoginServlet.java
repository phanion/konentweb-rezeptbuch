/*
 * Autor: Lorenz
 * 
 * 
 */


package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import bean.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		final String mail = request.getParameter("mail").toLowerCase();
		final String password = request.getParameter("password");
		String message = null;
		
		HttpSession session = request.getSession();
		
		try{
			final Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from users where mail=?");
			ps.setString(1, mail);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("mail").equals(mail) && rs.getString("password").equals(password)) {
					User user = new User(rs.getLong("ID"), rs.getString("mail"), rs.getString("lastName"), rs.getString("firstName"), rs.getString("password"));
					
					session.setAttribute("user", user);
					
					message = "Der Nutzer " + user.getFirstName() + " " + user.getLastName() + " wurde erfolgreich eingeloggt!";
					
					RequestDispatcher disp = request.getRequestDispatcher("/index.jsp");
					disp.forward(request, response);
				}
				else message = "Der Nutzer konnte nicht eingeloggt werden! Aber existiert!";
				
			}
			else message = "Der Nutzer konnte nicht eingeloggt werden. Nutzer existiert nicht!";
			//http://stackoverflow.com/questions/6452537/servlet-send-response-to-jsp
			request.setAttribute("message", message);
			RequestDispatcher disp = request.getRequestDispatcher("/jsp/login.jsp");
			disp.forward(request, response);
		}
		catch(Exception e){
			response.getWriter().append(e.toString());
		}
				
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	


}
