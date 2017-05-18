package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import classes.Ingredient;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		final Long id = Long.parseLong(request.getParameter("id"));
		
		try {
			RezeptBean recipe = loadRecipeFromDB(id);
			
			request.setAttribute("rezept", recipe);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		RequestDispatcher disp = request.getRequestDispatcher("/jsp/rezept_anzeigen.jsp");
		disp.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public RezeptBean loadRecipeFromDB(Long id) throws SQLException, ServletException{
		final Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from recipes where id=?;");
		
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			RezeptBean recipe = new RezeptBean();
			recipe.setId(rs.getLong("id"));
			recipe.setName(rs.getString("name"));
			recipe.setDescription(rs.getString("description"));
			recipe.setDifficulty(rs.getInt("difficulty"));
			recipe.setDurationCooking(rs.getInt("durationCooking"));
			recipe.setDurationPreparation(rs.getInt("durationPreparation"));
			recipe.setRatingSum(rs.getInt("ratingSum"));
			recipe.setRatingCount(rs.getInt("ratingCount"));
			recipe.setServings(rs.getInt("servings"));
			recipe.setImage(rs.getBytes("image"));
			
			recipe.setCreator(loadCreator(rs.getLong("creator")));
			
			recipe.setIngredients(loadIngredients(recipe.getId()));
			
			
			
			return recipe;
	}
		return null;
	}
	
	public User loadCreator(Long id) throws SQLException{
		User creator = new User();

		final Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("select firstName, lastName from users where id=?");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			creator.setFirstName(rs.getString("firstName"));
			creator.setLastName(rs.getString("lastName"));
			con.close();
			return creator;
		}

		
		
		con.close();
		return null;
	}
	
	
	public List<Ingredient> loadIngredients(Long id) throws SQLException{
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		final Connection con = ds.getConnection();
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("select * from ingredients where recipe='" + id + "';");
		
		while(rs.next()){
			ingredients.add(new Ingredient(rs.getString("ingredient"),rs.getInt("quantity"), rs.getString("unit")));
		}
		con.close();
		return ingredients;
		
	}
	

}
