/**
 * @author Lorenz
 */

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

import bean.RezeptBean;
import bean.User;

/**
 * Servlet implementation class DeleteRecipeServlet
 */
@WebServlet("/DeleteRecipeServlet")
public class DeleteRecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRecipeServlet() {
        super();
        
    }
    
    @Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * Das Servlet prüft zunöchst, ob der eingeloggte Nutzer mit dem Ersteller des Rezepts übereinstimmt. Wenn ja: Löschen der Einträge aus den einzelnen Tabellen.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RezeptBean recipe = new RezeptBean();
		
		recipe.setId(Long.parseLong(request.getParameter("recipe")));
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		
		try {
			if (checkIfUserIsCreator(user, recipe)) {
				deleteAbos(recipe);
				deleteComments(recipe);
				deleteIngredients(recipe);
				deleteRatings(recipe);
				deleteRecipe(recipe);
				
				//RequestDispatcher disp = request.getRequestDispatcher("/index.jsp");
				//disp.forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			RequestDispatcher disp = request.getRequestDispatcher("/jsp/errorpage.jsp");
			disp.forward(request, response);
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	
	public boolean checkIfUserIsCreator(User user, RezeptBean recipe) throws SQLException{
		Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM recipes WHERE id=?");
		ps.setLong(1, recipe.getId());

		ResultSet rs = ps.executeQuery();
		
		con.close();
		
		while(rs.next()){
			if(rs.getLong("creator") == user.getId()){
				return true;
			}
			else{
				return false;
			}
				}
		return false;
	}
	
	
	public void deleteComments(RezeptBean recipe) throws SQLException{
		Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM comments WHERE recipe=?");
		ps.setLong(1, recipe.getId());
		
		ps.executeUpdate();
		
		con.close();
		return;

	}
	
	public void deleteAbos(RezeptBean recipe) throws SQLException{
		Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM abos WHERE recipe=?");
		ps.setLong(1, recipe.getId());
		
		ps.executeUpdate();
		
		con.close();
		return;
	}
	
	public void deleteIngredients(RezeptBean recipe) throws SQLException{
		Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM ingredients WHERE recipe=?");
		ps.setLong(1, recipe.getId());
		
		ps.executeUpdate();
		
		con.close();
		return;
	}
	
	public void deleteRatings(RezeptBean recipe) throws SQLException{
		Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM ratings WHERE recipe=?");
		ps.setLong(1, recipe.getId());
		
		ps.executeUpdate();
		
		con.close();
		return;
	}
	
	public void deleteRecipe(RezeptBean recipe) throws SQLException{
		Connection con = ds.getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM recipes WHERE id=?");
		ps.setLong(1, recipe.getId());
		
		ps.executeUpdate();
		
		con.close();
		return;
	}

}
