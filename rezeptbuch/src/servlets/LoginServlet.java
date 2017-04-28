/*
 * Autor: Lorenz
 * 
 * 
 */


package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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
		// TODO Auto-generated method stub
		final String mail = request.getParameter("mail").toLowerCase();
		final String password = request.getParameter("password");
		
		try{
			final Connection con = ds.getConnection();
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select mail, password from users where mail='" + mail + "';");
			if (rs.next()) {
				if (rs.getString("mail").equals(mail) && rs.getString("password").equals(password)) {

					response.getWriter().append("Der Nutzer wurde erfolgreich eingeloggt!");
				}
				else response.getWriter().append("Der Nutzer konnte nicht eingeloggt werden! Aber existiert!");
				
			}
			else response.getWriter().append("Der Nutzer konnte nicht eingeloggt werden. Nutzer existiert nicht!");
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
