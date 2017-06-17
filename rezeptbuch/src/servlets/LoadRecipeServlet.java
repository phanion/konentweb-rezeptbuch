package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import bean.Ingredient;
import bean.RezeptBean;
import bean.User;

/**
 * Servlet implementation class LoadRecipeServlet
 */
@WebServlet("/LoadRecipeServlet")
public class LoadRecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String message = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoadRecipeServlet() {
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
		request.setCharacterEncoding("UTF-8");
		final Long id = Long.parseLong(request.getParameter("id"));

		try {
			RezeptBean recipe = loadRecipeFromDB(id);

			HttpSession session = request.getSession();
			User sessionUser = (User) session.getAttribute("user");

			if (sessionUser != null) {
				request.setAttribute("aboStatus", loadAbo(recipe, sessionUser));
				request.setAttribute("currentRating", loadRating(recipe, sessionUser));
			}

			request.setAttribute("rezept", recipe);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher disp = request.getRequestDispatcher("/jsp/rezept_anzeigen.jsp");
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

	/**
	 * Sucht in der Datenbank nach Rezepten zu einer Rezept-ID, und gibt ein
	 * gefundenes Rezept zurück.
	 * 
	 * @param id
	 *            Des Rezept nach dem gesucht wird
	 * @return Rezept, wenn ein Rezept gefunden wurde, null, wenn kein Rezept
	 *         mit der gesuchten ID gefunden wurde.
	 * @throws SQLException
	 *             bei einem Datenbankfehler
	 * @throws ServletException
	 *             bei einem Servletfehler
	 */
	public RezeptBean loadRecipeFromDB(Long id) throws SQLException, ServletException {
		final Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("select recipes.*, users.firstName, users.lastName "
				+ "from recipes " + "inner join users on users.id = recipes.creator " + "where recipes.id=?");

		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			RezeptBean recipe = new RezeptBean();
			User user = new User();
			recipe.setId(rs.getLong("recipes.id"));
			recipe.setName(rs.getString("recipes.name"));
			recipe.setDescription(rs.getString("recipes.description"));
			recipe.setDifficulty(rs.getInt("recipes.difficulty"));
			recipe.setDurationCooking(rs.getInt("recipes.durationCooking"));
			recipe.setDurationPreparation(rs.getInt("recipes.durationPreparation"));
			recipe.setRatingSum(rs.getInt("recipes.ratingSum"));
			recipe.setRatingCount(rs.getInt("recipes.ratingCount"));
			recipe.setServings(rs.getInt("recipes.servings"));
			recipe.setImage(rs.getBytes("recipes.image"));
			recipe.setFilename(rs.getString("recipes.filename"));
			recipe.setCreated(rs.getTimestamp("created"));
			recipe.setModified(rs.getTimestamp("modified"));

			user.setFirstName(rs.getString("users.firstName"));
			user.setLastName(rs.getString("users.lastName"));
			user.setId(rs.getLong("recipes.creator"));
			recipe.setCreator(user);

			recipe.setIngredients(loadIngredients(recipe.getId()));
			recipe.setComments(loadComments(recipe));

			con.close();
			return recipe;
		}
		con.close();
		return null;
	}

	/**
	 * Sucht in der Datenbank nach Nutzern zu einer Nutzer-ID, und gibt einen
	 * gefundenen Nutzer zurück.
	 * 
	 * @param id
	 *            Des Users nach dem gesucht wird
	 * @return User, wenn ein User gefunden wurde, null, wenn kein User mit der
	 *         ID gefunden wurde.
	 * @throws SQLException
	 *             bei einem Datenbankfehler
	 */
	public User loadUser(Long id) throws SQLException {
		User user = new User();

		final Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("select firstName, lastName from users where id=?");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setId(id);
			con.close();
			return user;
		}

		con.close();
		return null;
	}

	/**
	 * Sucht in der Datenbank nach allen Zutaten zu einer Rezept-ID, und gibt
	 * diese in einer Liste zurück
	 * 
	 * @param id
	 *            ID-Schlüssel des Rezepts
	 * @return Liste von Zutaten
	 * @throws SQLException
	 *             bei einem Datenbankfehler
	 */
	public List<Ingredient> loadIngredients(Long id) throws SQLException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();

		final Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from ingredients where recipe=?");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			ingredients.add(new Ingredient(rs.getString("ingredient"), rs.getInt("quantity"), rs.getString("unit")));
		}
		con.close();
		return ingredients;

	}

	/**
	 * Sucht in der Datenbank nach allen Kommentaren zu einem Rezept, und gibt
	 * diese in einer Liste zurück
	 * 
	 * @param recipe
	 *            Ein RezeptBean, dessen Kommentare gesucht werden
	 * @return Eine Liste von Kommentaren
	 * @throws SQLException
	 *             bei einem Datenbankfehler
	 */
	public List<Comment> loadComments(RezeptBean recipe) throws SQLException {
		List<Comment> comments = new ArrayList<Comment>();

		final Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from comments where recipe=?");
		ps.setLong(1, recipe.getId());
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			comments.add(
					new Comment(rs.getLong("ID"), loadUser(rs.getLong("author")), rs.getString("comment"), recipe));

		}
		con.close();
		return comments;

	}

	/**
	 * Schlägt in der Datenbank nach, ob ein User ein Rezept bewertet hat
	 * 
	 * @param recipe
	 *            Ein Rezeptbean
	 * @param user
	 *            Ein User(bean)
	 * @return den Wert der Bewertung, falls es einen passenden Eintrag gibt, 0,
	 *         falls nicht bewertet wurde.
	 * @throws SQLException
	 *             bei einem Datenbankfehler
	 */
	public int loadRating(RezeptBean recipe, User user) throws SQLException {
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM ratings WHERE recipe=? AND evaluator=?")) {

			ps.setLong(1, recipe.getId());
			ps.setLong(2, user.getId());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getInt("rating");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Schlägt in der Datenbank nach, ob ein User ein Rezept abonniert hat
	 * 
	 * @param recipe
	 *            Ein Rezeptbean
	 * @param user
	 *            Ein User(bean)
	 * @return true, falls es einen passenden Eintrag gibt, false, falls nicht
	 * @throws SQLException
	 *             bei einem Datenbankfehler
	 */
	public boolean loadAbo(RezeptBean recipe, User user) throws SQLException {
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM abos WHERE recipe=? AND user=?")) {
			ps.setLong(1, recipe.getId());
			ps.setLong(2, user.getId());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
