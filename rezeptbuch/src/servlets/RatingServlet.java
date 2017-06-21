/**
 * @author Lorenz, michael
 */

package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * Servlet implementation class Rating
 */
@WebServlet({ "/Rating", "/RatingServlet", "/ratingservlet" })
public class RatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RatingServlet() {
		super();
	}

	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final Long recipeId = Long.parseLong(request.getParameter("recipe"));
		final Integer newRating = Integer.parseInt(request.getParameter("rating"));

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		try {
			// Erst Rating setzen, dann bewertetes Rezept laden
			setRating(recipeId, user.getId(), newRating);
			RezeptBean recipe = loadRecipe(recipeId);
			recipe.setId(Long.parseLong(request.getParameter("recipe")));

			// Returns ave
			response.getWriter().append(recipe.calculateRatingInt().toString());

		} catch (SQLException e) {
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

	/**
	 * Eine Bewertung wird von einem User zu einem Rezept hinzugefügt
	 * <p>
	 * Wenn das Rezept durch den Nutzer schon einmal bewertet wurde, wird die
	 * alte Bewertung aktualisiert. Wenn es eine neue Bewertung durch den User
	 * ist, wird eine neue Bewertung erstellt.
	 * 
	 * @param recipeID
	 *            ID des zu bewertenden Rezeptes
	 * @param userID
	 *            ID des bewertenden Users
	 * @param rating
	 *            Wert der Bewertung, zwischen 0 und 5.
	 * @throws SQLException
	 *             bei einem Datenbankfehler
	 */
	public void setRating(Long recipeID, Long userID, Integer rating) throws SQLException {
		final Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from ratings where recipe=? AND evaluator=?");
		ps.setLong(1, recipeID);
		ps.setLong(2, userID);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Integer currentRating = rs.getInt("rating");

			PreparedStatement loadRecipeRating = con.prepareStatement("select ratingSum from recipes where id=?");
			loadRecipeRating.setLong(1, recipeID);
			ResultSet recipeRating = loadRecipeRating.executeQuery();

			if (recipeRating.next()) {
				Integer ratingSum = recipeRating.getInt("ratingSum");
				PreparedStatement setRecipeRating = con.prepareStatement("update recipes set ratingSum=? where id=?");
				setRecipeRating.setInt(1, ratingSum - currentRating + rating);
				setRecipeRating.setLong(2, recipeID);
				setRecipeRating.executeUpdate();

				PreparedStatement updateRating = con
						.prepareStatement("update ratings set rating=? where evaluator=? and recipe=?");
				updateRating.setInt(1, rating);
				updateRating.setLong(2, userID);
				updateRating.setLong(3, recipeID);
				updateRating.executeUpdate();
			}

		} else {
			PreparedStatement loadRecipeRating = con
					.prepareStatement("select ratingSum, ratingCount from recipes where id=?");
			loadRecipeRating.setLong(1, recipeID);

			ResultSet recipeRating = loadRecipeRating.executeQuery();
			if (recipeRating.next()) {
				Integer recipeRatingSum = recipeRating.getInt("ratingSum");
				Integer recipeRatingCount = recipeRating.getInt("ratingCount");

				PreparedStatement setRecipeRating = con
						.prepareStatement("update recipes set ratingSum=?, ratingCount=? where id=?");
				setRecipeRating.setInt(1, recipeRatingSum + rating);
				setRecipeRating.setInt(2, recipeRatingCount + 1);
				setRecipeRating.setLong(3, recipeID);
				setRecipeRating.executeUpdate();

				PreparedStatement setRating = con
						.prepareStatement("insert into ratings(recipe, evaluator, rating) values(?,?,?)");
				setRating.setLong(1, recipeID);
				setRating.setLong(2, userID);
				setRating.setInt(3, rating);
				setRating.executeUpdate();
			}
		}
		con.close();

	}

	public RezeptBean loadRecipe(Long id) throws SQLException {
		Connection con = ds.getConnection();

		PreparedStatement ps = con.prepareStatement("select * from recipes where id=?");
		ps.setLong(1, id);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			RezeptBean recipe = new RezeptBean();

			recipe.setId(rs.getLong("id"));
			recipe.setRatingCount(rs.getInt("ratingCount"));
			recipe.setRatingSum(rs.getInt("ratingSum"));

			con.close();

			return recipe;
		}
		con.close();

		return null;
	}

}
