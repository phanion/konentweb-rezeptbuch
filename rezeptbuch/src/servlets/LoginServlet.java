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

	public LoginServlet() {
		super();
	}

	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *      
	 *      
	 * Das Servlet lädt den beim Login angegebenen Nutzer und vergleicht die Passwörter. Bei einer Übereinstimmung wird der User in der Session gespeichert.
	 * 
	 * Wenn der User nicht existiert oder die Passwörter nicht übereinstimmen wird eine Message auf der Loginseite ausgegeben.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

		final String mail = request.getParameter("mail").toLowerCase();
		final String password = request.getParameter("password");
		String message = null;

		HttpSession session = request.getSession();

		try {
			final Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from users where mail=?");
			ps.setString(1, mail);
			ResultSet rs = ps.executeQuery();
			con.close();
			if (rs.next()) {
				if (rs.getString("mail").equals(mail) && rs.getString("password").equals(password)) {
					
					User user = new User(rs.getLong("ID"), rs.getString("mail"), rs.getString("lastName"),
							rs.getString("firstName"), rs.getString("password"), rs.getString("description"));

					session.setAttribute("user", user);

					message = "Der Nutzer " + user.getFirstName() + " " + user.getLastName()
							+ " wurde erfolgreich eingeloggt!";
					
					
					response.sendRedirect("index.jsp");
				} else
					message = "Die Mail-Adresse und das Passwort stimmen nicht überein.";

			} else
				message = "Unter der von Ihnen angegebenen Mail-Adresse existiert kein Nutzer. Bitte registrieren Sie sich zuerst.";
			

			request.setAttribute("message", message);
			RequestDispatcher disp = request.getRequestDispatcher("/jsp/login.jsp");
			disp.forward(request, response);
		} catch (Exception e) {
			response.getWriter().append(e.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
