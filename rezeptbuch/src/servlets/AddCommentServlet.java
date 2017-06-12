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

import bean.Comment;
import bean.RezeptBean;
import bean.User;

/**
 * Servlet implementation class AddCommentServlet
 */
@WebServlet({ "/AddCommentServlet", "/addcommentservlet" })
public class AddCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCommentServlet() {
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
		/**final User author = (User) session.getAttribute("user");
		final Long recipe = Long.parseLong(request.getParameter("recipe"));
		final String comment = request.getParameter("comment");**/
		
		final Comment comment = new Comment();
		final RezeptBean recipe = new RezeptBean();
		
		
		recipe.setId(Long.parseLong(request.getParameter("recipe")));
		
		comment.setAuthor((User) session.getAttribute("user"));
		comment.setRecipe(recipe);
		comment.setComment(request.getParameter("comment"));
		
		

		try {
			
			
			final Connection con = ds.getConnection();
			String[] generatedKeys = new String[] { "id" };
			
			
			PreparedStatement ps = con.prepareStatement("INSERT INTO comments(author,recipe,comment) values(?,?,?)",generatedKeys);
			ps.setLong(1, comment.getAuthor().getID());
			ps.setLong(2, comment.getRecipe().getId());
			ps.setString(3, comment.getComment());

			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				comment.setId(rs.getLong(1));
			}
			
			con.close();
			
			session.setAttribute("comment", comment);
			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/commentResponse.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
