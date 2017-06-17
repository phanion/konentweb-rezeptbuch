/**
 * @author Lorenz
 * 
 */

package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import bean.RezeptBean;
import bean.User;

/**
 * Servlet implementation class AboHandlingServlet
 */
@WebServlet("/AboHandlingServlet")
public class AboHandlingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AboHandlingServlet() {
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
		HttpSession session = request.getSession();

		final User user = (User) session.getAttribute("user");
		final RezeptBean recipe = new RezeptBean();
		final String action = request.getParameter("action");

		recipe.setId(Long.parseLong(request.getParameter("recipe")));

		if (action.equals("addAbo")) {
			try {
				addAbo(user, recipe);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.getWriter().append((CharSequence) e);
			}
		} else {
			if (action.equals("deleteAbo")) {
				try {
					deleteAbo(user, recipe);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response.getWriter().append((CharSequence) e);
				}
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void addAbo(User user, RezeptBean recipe) throws SQLException {
		final Connection con = ds.getConnection();

		PreparedStatement ps = con.prepareStatement("INSERT INTO abos(recipe, user) values(?,?)");
		ps.setLong(1, recipe.getId());
		ps.setLong(2, user.getId());

		ps.executeUpdate();

		con.close();

	}

	public void deleteAbo(User user, RezeptBean recipe) throws SQLException {
		final Connection con = ds.getConnection();

		PreparedStatement ps = con.prepareStatement("DELETE FROM abos WHERE recipe=? AND user=?");

		ps.setLong(1, recipe.getId());
		ps.setLong(2, user.getId());

		ps.executeUpdate();

		con.close();

	}

}
