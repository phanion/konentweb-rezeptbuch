/*
 * Autor: Lorenz
 * Refactoring: Michael
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

	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Anmerkung: Später muss das weg. Es gibt für PW-Übertragung keine GET
		// Methode
		final String mail = request.getParameter("mail").toLowerCase();
		final String lastName = request.getParameter("lastName");
		final String firstName = request.getParameter("firstName");
		final String password = request.getParameter("password");
		final String password_retype = request.getParameter("password_retype");
		String message = null;
		
		HttpSession session = request.getSession();
		
		if (!password.equals(password_retype)) {
			message = "Die eingegebenen Passwörter stimmen nicht überein!";
		}
		
		else {
			
			try {
				
				final Connection con = ds.getConnection();
				// https://www.mkyong.com/jdbc/jdbc-statement-example-select-list-of-the-records/
				Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery("select mail from users where mail='" + mail + "';");
				
				if (rs.next()) {
					if (rs.getString(1).equals(mail)) {
						message = "Der User existiert bereits!";
						
					}
				}
				
				else {
					String[] generatedKeys = new String[] {"id"};
					// https://www.javatpoint.com/example-of-registration-form-in-servlet
					PreparedStatement ps = con.prepareStatement("insert into users(mail,lastName,firstName,password) values(?,?,?,?)",generatedKeys);
					ps.setString(1,mail);
					ps.setString(2, lastName);
					ps.setString(3, firstName);
					ps.setString(4, password);
					
					
					ps.executeUpdate();
					
					User user = new User(mail, lastName, firstName, password);
					session.setAttribute("user", user);
					
					message = "Der User " + user.getFirstName() + " " + user.getLastName() + " wurde erfolgreich angelegt!";
				
				}
				
				
				
				
			}
			
			catch (Exception e) {
				response.getWriter().append(e.getMessage());
			}

		}

		//http://stackoverflow.com/questions/6452537/servlet-send-response-to-jsp
		request.setAttribute("message", message);
		RequestDispatcher disp = request.getRequestDispatcher("/jsp/registration.jsp");
		disp.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
