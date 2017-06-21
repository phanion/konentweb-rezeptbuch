/**
 * Autor: Lorenz
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
import javax.sql.DataSource;

import bean.RezeptBean;
import bean.User;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<RezeptBean> recipeList = new ArrayList<RezeptBean>(loadRecipes());
			
			request.setAttribute("recipeList", recipeList);

			RequestDispatcher disp = request.getRequestDispatcher("/jsp/IndexServletResponse.jsp");
			disp.forward(request, response);
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public List<RezeptBean> loadRecipes() throws SQLException{
		Connection con = ds.getConnection();
		
		PreparedStatement ps = con.prepareStatement("select recipes.*, users.firstName, users.lastName from recipes inner join users on users.id = recipes.creator order by id desc limit 10");
		
		ResultSet rs = ps.executeQuery();
		
		List<RezeptBean> recipeList = new ArrayList<RezeptBean>();
		while(rs.next()){
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
			
			recipeList.add(recipe);
		}
		con.close();
	
		return recipeList;
		
	}
	

	
	

}


