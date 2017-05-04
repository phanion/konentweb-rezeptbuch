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
import java.sql.SQLException;
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
	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

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

				if (!userUnique(mail)) {
					message = "Der Nutzer existiert bereits!";
					
				}

				else {

					User user = new User();

					user.setMail(mail);
					user.setLastName(lastName);
					user.setFirstName(firstName);
					user.setPassword(password);

					session.setAttribute("user", user);
					
					if(createUser(user)){
						message = "Der User " + user.getFirstName() + " " + user.getLastName()
							+ " wurde mit ID " + user.getID() + " erfolgreich angelegt!";
						
					}
					else{
						message = "Der User konnte nicht angelegt werden!";
						
					}

				}

			}

			catch (Exception e) {
				response.getWriter().append(e.getMessage());
			}

		}
		
		// http://stackoverflow.com/questions/6452537/servlet-send-response-to-jsp
		request.setAttribute("message", message);
		String target = (session.getAttribute("user") != null) ? "/index.jsp" : "/jsp/registration.jsp";
		RequestDispatcher disp = request.getRequestDispatcher(target);
		
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

	public boolean userUnique(String mail) throws ServletException {
		try {
			final Connection con = ds.getConnection();

			PreparedStatement ps = con.prepareStatement("select mail from users where mail=?;");
			ps.setString(1, mail);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getString(1).equals(mail)) {
					return false;

				}
				return true;
			}
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException(e.getMessage());
		}
		
		
	}

	public boolean createUser(User user) throws ServletException {
		try {
			final Connection con = ds.getConnection();

			String[] generatedKeys = new String[] { "id" };

			PreparedStatement ps;

			ps = con.prepareStatement("insert into users(mail,lastName,firstName,password) values(?,?,?,?)",
					generatedKeys);

			ps.setString(1, user.getMail());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getPassword());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				user.setID(rs.getString(1));
			}
			return true;

		} 
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException(e.getMessage());
		}
	}

}
