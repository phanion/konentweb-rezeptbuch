/**
 * Autor: Lorenz
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
@WebServlet("/EditProfileServlet")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		final Long id = Long.parseLong(request.getParameter("id"));
		final String mail = request.getParameter("mail").toLowerCase();
		final String lastName = request.getParameter("lastName");
		final String firstName = request.getParameter("firstName");

		String message = null;

		User sessionUser = (User) session.getAttribute("user");
		if (sessionUser.getID() != id) {
			message = "Bitte melden Sie sich erneut an!";

			request.setAttribute("message", message);
			RequestDispatcher disp = request.getRequestDispatcher("/jsp/login.jsp");
			disp.forward(request, response);

		}

		else {

			User user = new User();
			user.setFirstName(firstName);
			user.setID(id);
			user.setLastName(lastName);
			user.setMail(mail);

			if (!userUnique(user.getMail())) {
				message = "Es existierte bereits ein User unter dieser Mailadresse";

				request.setAttribute("message", message);

			} else {
				try {
					updateUser(user);
					message = "Deine persönlichen Daten wurden erfolgreich geändert!";
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void updateUser(User user) throws SQLException {
		Connection con = ds.getConnection();

		PreparedStatement ps = con.prepareStatement("update users SET mail=?, firstName=?, lastName=? WHERE id=?");

		ps.setString(1, user.getMail());
		ps.setString(2, user.getFirstName());
		ps.setString(3, user.getLastName());
		ps.setLong(4, user.getID());

		ps.executeUpdate();

		con.close();

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

}
