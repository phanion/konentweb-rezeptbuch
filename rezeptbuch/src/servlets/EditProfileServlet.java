/**
 * Autor: Lorenz
 * Refactoring: Florian
 */

package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
 * Servlet implementation class EditProfileServlet
 */
@WebServlet({ "/EditProfileServlet", "/profiledata" })
public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditProfileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Servlet zum �ndern des Nutzers
	 * wie Registration-Servlet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");

		request.setAttribute("user", u);

		RequestDispatcher rd = request.getRequestDispatcher("/jsp/profiledata.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		final Long id = Long.parseLong(request.getParameter("id"));
		final String mail = request.getParameter("mail").toLowerCase();
		final String lastName = request.getParameter("nachname");
		final String firstName = request.getParameter("vorname");
		final String description = request.getParameter("beschreibung");

		String message = null;

		User sessionUser = (User) session.getAttribute("user");
		if (sessionUser == null || sessionUser.getId() != id) {
			message = "Bitte melden Sie sich erneut an!";

			request.setAttribute("message", message);

			RequestDispatcher disp = request.getRequestDispatcher("/jsp/login.jsp");
			disp.forward(request, response);

		}

		else {

			User user = new User();
			user.setFirstName(firstName);
			user.setId(id);
			user.setLastName(lastName);
			user.setMail(mail);
			user.setDescription(description);

			if (!userUnique(user.getMail(), user.getId())) {
				message = "Es existierte bereits ein User unter dieser Mailadresse";

				request.setAttribute("message", message);

			} else {
				try {
					updateUser(user);
					session.setAttribute("user", user);
					message = "Deine pers�nlichen Daten wurden erfolgreich ge�ndert!";
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			request.setAttribute("message", message);

			RequestDispatcher disp = request.getRequestDispatcher("/jsp/profile.jsp");
			disp.forward(request, response);
		}
	}

	public void updateUser(User user) throws SQLException {
		Connection con = ds.getConnection();

		PreparedStatement ps = con.prepareStatement(
				"update rezeptbuch.users SET mail=?, firstName=?, lastName=?, description=? WHERE id=?");

		ps.setString(1, user.getMail());
		ps.setString(2, user.getFirstName());
		ps.setString(3, user.getLastName());
		ps.setString(4, user.getDescription());
		ps.setLong(5, user.getId());

		ps.executeUpdate();

		con.close();

	}

	public boolean userUnique(String mail, Long id) throws ServletException {
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement("select mail, id from rezeptbuch.users where mail=?;")) {

			ps.setString(1, mail);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				// die Mail darf nicht bereits bei einem ANDEREN User (daher ID Pr�fung) vorhanden sein
				if (rs.getString(1).toLowerCase().equals(mail.toLowerCase()) && (rs.getLong(2) != id)) {
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

}
