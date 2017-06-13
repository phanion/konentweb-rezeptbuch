/**
 * Autor: Lorenz
 * 
 */

package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * Servlet implementation class EditRecipeServlet
 */
@WebServlet({ "/EditRecipeServlet", "/EditRecipe" })
public class EditRecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditRecipeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// Parameter von dem Request werden geholt
		final Long recipeID = Long.parseLong(request.getParameter("id"));
		final String[] ingredients = request.getParameterValues("zutatenZutat");
		final String[] quantities = request.getParameterValues("zutatenMenge");
		final String[] units = request.getParameterValues("zutatenEinheit");
		final String description = request.getParameter("description");
		final Integer durationPreparation = Integer.parseInt(request.getParameter("durationPreparation"));
		final Integer durationCooking = Integer.parseInt(request.getParameter("durationCooking"));
		final Integer difficulty = Integer.parseInt(request.getParameter("difficulty"));
		final Integer servings = Integer.parseInt(request.getParameter("servings"));
		final Long creatorID = Long.parseLong(request.getParameter("creatorID"));
		String message = null;

		HttpSession session = request.getSession();

		RezeptBean rezept = new RezeptBean();
		rezept.setId(recipeID);
		rezept.setDescription(description);
		rezept.setDifficulty(difficulty);
		rezept.setDurationCooking(durationCooking);
		rezept.setDurationPreparation(durationPreparation);
		rezept.setServings(servings);

		if (ingredients != null) {
			for (int i = 0; i < ingredients.length; i++) {
				rezept.addIngredient(ingredients[i], units[i], Integer.parseInt(quantities[i]));
			}
		}

		/* File-Behandlung
		Part filepart = request.getPart("image");
		rezept.setFilename(filepart.getSubmittedFileName());

		// Bild in Bean speichern
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); InputStream in = filepart.getInputStream()) {
			int i = 0;
			while ((i = in.read()) != -1) {
				baos.write(i);
			}
			rezept.setImage(baos.toByteArray());
			baos.flush();
		} catch (IOException ex) {
			throw new ServletException(ex.getMessage());
		}*/

		request.setAttribute("rezept", rezept);

		User sessionUser = (User) session.getAttribute("user");

		if (sessionUser.getID() == creatorID) {
			try {
				updateRecipe(rezept);
				replaceIngredients(rezept);
				message = "Das Rezept wurde erfolgreich geändert!";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			message = "Sie sind nicht berechtigt das Rezept zu ändern.";
		}
		request.setAttribute("message", message);

		getServletContext().getRequestDispatcher("/LoadRecipeServlet");
		RequestDispatcher disp = request.getRequestDispatcher("/LoadRecipeServlet?id=" + rezept.getId());
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

	public void updateRecipe(RezeptBean recipe) throws SQLException {
		final Connection con = ds.getConnection();

		PreparedStatement ps = con.prepareStatement(
				"UPDATE recipes SET description=?, difficulty=?, durationCooking=?, durationPreparation=?, servings=? where id=?");

		ps.setString(1, recipe.getDescription());
		ps.setInt(2, recipe.getDifficulty());
		ps.setInt(3, recipe.getDurationCooking());
		ps.setInt(4, recipe.getDurationPreparation());
		ps.setInt(5, recipe.getServings());
		ps.setLong(6, recipe.getId());
		
		ps.executeUpdate();

		con.close();

	}

	public void replaceIngredients(RezeptBean recipe) throws SQLException {
		final Connection con = ds.getConnection();

		PreparedStatement delete = con.prepareStatement("delete from ingredients where recipe=?");
		delete.setLong(1, recipe.getId());
		delete.executeUpdate();

		for (int i = 0; i < recipe.getIngredients().size(); i++) {
			PreparedStatement ps = con.prepareStatement("insert into ingredients(recipe, ingredient, unit, quantity) values(?,?,?,?)");

			ps.setLong(1, recipe.getId());
			ps.setString(2, recipe.getIngredients().get(i).getIngredient());
			ps.setString(3, recipe.getIngredients().get(i).getUnit());
			ps.setInt(4, recipe.getIngredients().get(i).getQuantity());

			ps.executeUpdate();
		}
		con.close();

	}

}
