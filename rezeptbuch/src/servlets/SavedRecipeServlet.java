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
import javax.sql.DataSource;

import bean.RezeptBean;
import bean.User;

/**
 * Servlet implementation class SavedRecipeServlet
 */
@WebServlet({ "/SavedRecipeServlet", "/savedrecipes" })
public class SavedRecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SavedRecipeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute("user");
		
		try {
			List<RezeptBean> rList = getSavedRecipes(u);
			request.setAttribute("recipes", rList);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("noentrymessage", "Sie haben sich noch keine Rezepte gemerkt!");
		
		RequestDispatcher disp = request.getRequestDispatcher("/jsp/showrecipes.jsp");
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
	 * Gibt alle Rezepte in einer Liste zurück, die ein spezifizierter User gemerkt hat
	 * 
	 * @param user Ein User
	 * @return Liste von RezeptBeans
	 * @throws SQLException bei einem Datenbankfehler
	 */
	public List<RezeptBean> getSavedRecipes(User user) throws SQLException {
		List<RezeptBean> recipes = new ArrayList<RezeptBean>();

		try (Connection con = ds.getConnection();
				PreparedStatement pStatement = con.prepareStatement(
						"select r.* from rezeptbuch.recipes r join rezeptbuch.abos a on (r.id = a.recipe) where a.user = ?");
				) {
			pStatement.setLong(1, user.getId());
			ResultSet rs = pStatement.executeQuery();

			while (rs.next()) {
				RezeptBean recipe = new RezeptBean();

				recipe.setId(rs.getLong("ID"));
				recipe.setName(rs.getString("name"));
				recipe.setRatingCount(rs.getInt("ratingCount"));
				recipe.setRatingSum(rs.getInt("ratingSum"));
				recipe.setFilename(rs.getString("filename"));
				recipe.setCreated(rs.getTimestamp("created"));
				recipe.setModified(rs.getTimestamp("modified"));

				recipes.add(recipe);
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}

		return recipes;
	}
}