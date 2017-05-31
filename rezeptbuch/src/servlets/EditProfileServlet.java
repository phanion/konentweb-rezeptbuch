/**
 * Autor: Lorenz
 */

package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
			
			try {
				updateUser(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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

}
