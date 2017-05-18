/**
 * Autor: Lorenz
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
 * Servlet implementation class Rating
 */
@WebServlet("/Rating")
public class Rating extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Rating() {
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
		final Long recipe = Long.parseLong(request.getParameter("recipe"));
		final Integer rating = Integer.parseInt(request.getParameter("rating"));

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		try {
			setRating(recipe, user.getID(), rating);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		RequestDispatcher disp = request.getRequestDispatcher("/LoadRecipeServlet?id=" + recipe);
		disp.forward(request, response);

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

	public void setRating(Long recipe, Long user, Integer rating) throws SQLException {
		final Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from ratings where recipe=? AND evaluator=?");
		ps.setLong(1, recipe);
		ps.setLong(2, user);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Integer currentRating = rs.getInt("rating");

			PreparedStatement loadRecipeRating = con.prepareStatement("select ratingSum from recipes where id=?");
			loadRecipeRating.setLong(1, recipe);
			ResultSet recipeRating = loadRecipeRating.executeQuery();

			if (recipeRating.next()) {
				Integer ratingSum = recipeRating.getInt("ratingSum");
				PreparedStatement setRecipeRating = con.prepareStatement("update recipes set ratingSum=? where id=?");
				setRecipeRating.setInt(1, ratingSum - currentRating + rating);
				setRecipeRating.setLong(2, recipe);
				setRecipeRating.executeUpdate();

				PreparedStatement updateRating = con
						.prepareStatement("update ratings set rating=? where evaluator=? and recipe=?");
				updateRating.setInt(1, rating);
				updateRating.setLong(2, user);
				updateRating.setLong(3, recipe);
				updateRating.executeUpdate();
			}

		} else {
			PreparedStatement loadRecipeRating = con
					.prepareStatement("select ratingSum, ratingCount from recipes where id=?");
			loadRecipeRating.setLong(1, recipe);

			ResultSet recipeRating = loadRecipeRating.executeQuery();
			if (recipeRating.next()) {
				Integer recipeRatingSum = recipeRating.getInt("ratingSum");
				Integer recipeRatingCount = recipeRating.getInt("ratingCount");

				PreparedStatement setRecipeRating = con
						.prepareStatement("update recipes set ratingSum=?, ratingCount=? where id=?");
				setRecipeRating.setInt(1, recipeRatingSum + rating);
				setRecipeRating.setInt(2, recipeRatingCount + 1);
				setRecipeRating.setLong(3, recipe);
				setRecipeRating.executeUpdate();

				PreparedStatement setRating = con
						.prepareStatement("insert into ratings(recipe, evaluator, rating) values(?,?,?)");
				setRating.setLong(1, recipe);
				setRating.setLong(2, user);
				setRating.setInt(3, rating);
				setRating.executeUpdate();
			}
		}

	}

}
