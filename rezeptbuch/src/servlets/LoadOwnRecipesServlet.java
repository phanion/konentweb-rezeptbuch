/**
 * @author Lorenz
 */

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

import bean.RezeptBean;
import bean.User;

/**
 * Servlet implementation class LoadOwnRecipesServlet
 */
@WebServlet({ "/LoadOwnRecipesServlet", "/ownrecipes" })
public class LoadOwnRecipesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    @Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;
    
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadOwnRecipesServlet() {
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
			List<RezeptBean> recipes = loadRecipes(u);
			request.setAttribute("recipes", recipes);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("noentrymessage", "Sie noch keine Rezepte erstellt!");
		
		RequestDispatcher disp = request.getRequestDispatcher("/jsp/showrecipes.jsp");
		disp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public List<RezeptBean> loadRecipes(User user) throws SQLException{
		List<RezeptBean> recipes = new ArrayList<RezeptBean>();
		
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM recipes WHERE creator=?")) {
			ps.setLong(1, user.getId());
			ResultSet rs = ps.executeQuery();

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
		}
		
		return recipes;
	}

}
