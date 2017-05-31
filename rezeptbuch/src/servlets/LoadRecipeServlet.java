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
import bean.Comment;
import bean.Ingredient;

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
	
	/**
	 * TODO: JavaDoc ausfüllen
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws ServletException
	 */
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
			recipe.setFilename(rs.getString("filename"));
			
			recipe.setCreator(loadUser(rs.getLong("creator")));
			
			recipe.setIngredients(loadIngredients(recipe.getId()));
			recipe.setComments(loadComments(recipe));
			
			con.close();
			return recipe;
	}
		con.close();
		return null;
	}
	
	/**
	 * TODO: Ausfüllen
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public User loadUser(Long id) throws SQLException{
		User user = new User();

		final Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("select firstName, lastName from users where id=?");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setID(id);
			con.close();
			return user;
		}

		con.close();
		return null;
	}
	
	/**
	 * TODO: Ausfüllen
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<Ingredient> loadIngredients(Long id) throws SQLException{
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		final Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from ingredients where recipe=?");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			ingredients.add(new Ingredient(rs.getString("ingredient"),rs.getInt("quantity"), rs.getString("unit")));
		}
		con.close();
		return ingredients;
		
	}
	
	/**
	 * TODO: Ausfüllen
	 * @param recipe
	 * @return
	 * @throws SQLException
	 */
	public List<Comment> loadComments(RezeptBean recipe) throws SQLException{
		List<Comment> comments = new ArrayList<Comment>();
		
		final Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from comments where recipe=?");
		ps.setLong(1, recipe.getId());
		ResultSet rs = ps.executeQuery();
		
		
		while(rs.next()){
			comments.add(new Comment(rs.getLong("ID"), loadUser(rs.getLong("author")),rs.getString("comment"), recipe));
			
		}
		con.close();
		return comments;
		
	}
	

}
