/*
 * Autor: Lorenz
 * Refactoring: Michael
 * 
 * 
 */


package servlets;


import java.io.IOException;
import java.sql.*;


import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import nutzer.User;

/**
 * Servlet implementation class registration
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Resource(lookup="jdbc/MyRezeptbuchPool")
	private DataSource ds;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Anmerkung: Später muss das weg. Es gibt für PW-Übertragung keine GET Methode
		final String mail = request.getParameter("mail");
		final String name = request.getParameter("name");
		final String prename = request.getParameter("prename");
		final String password = request.getParameter("password");
		final String password_retype = request.getParameter("password_retype");
		
		
		
		if(!password.equals(password_retype)){
			response.getWriter().append("Passwort nicht richtig wiederholt");
		}
		else{
			try{
				final Connection con = ds.getConnection();
				
				//https://www.javatpoint.com/example-of-registration-form-in-servlet
				PreparedStatement ps = con.prepareStatement("insert into users values('" + mail + "','" + name + "','" + prename + "','" + password + "')");
				ps.executeUpdate();
				
				
				User user = new User(mail, name, prename, password);
				
				response.getWriter().append((CharSequence) "Neuer Nutzer: ").append((CharSequence) user.getName()).append((CharSequence) user.getVorname());	
			}
			catch (Exception e){
				response.getWriter().append(e.getMessage());
			}
			
		}
		
//		RequestDispatcher rd = request.getRequestDispatcher("index.html");
//		rd.forward(request, response);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
