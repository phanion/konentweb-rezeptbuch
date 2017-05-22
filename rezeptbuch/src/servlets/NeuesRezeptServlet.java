/**
 * Autor: Florian, Lorenz
 */

package servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import bean.RezeptBean;
import bean.User;

@WebServlet("/NeuesRezeptServlet")
//Bild-Upload einschränken
@MultipartConfig(
		maxFileSize =1024*1024*15,
		maxRequestSize =1024*1024*5,
		location="/tmp",
		fileSizeThreshold=1024*1024)
public class NeuesRezeptServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");


		// Parameter von dem Request werden geholt
		final String name = request.getParameter("name");
		final String[] ingredients = request.getParameterValues("zutatenZutat");
		final String[] quantities = request.getParameterValues("zutatenMenge");
		final String[] units = request.getParameterValues("zutatenEinheit");
		final String description = request.getParameter("description");
		final Integer durationPreparation = Integer.parseInt(request.getParameter("durationPreparation"));
		final Integer durationCooking = Integer.parseInt(request.getParameter("durationCooking"));
		final Integer difficulty = Integer.parseInt(request.getParameter("difficulty"));
		final Integer servings = Integer.parseInt(request.getParameter("servings"));
		
		String message = null;
		
		
		//response.getWriter().append(ingredients[0] + " " + quantities[0] + " " + units[0]);
		
		HttpSession session = request.getSession();

		RezeptBean rezept = new RezeptBean();

		rezept.setCreator((User) session.getAttribute("user"));
		rezept.setName(name);
		rezept.setDescription(description);
		rezept.setDifficulty(difficulty);
		rezept.setDurationCooking(durationCooking);
		rezept.setDurationPreparation(durationPreparation);
		rezept.setServings(servings);
		
		if(ingredients != null){
		for(int i = 0; i < ingredients.length; i++){
			rezept.addIngredient(ingredients[i], units[i], Integer.parseInt(quantities[i]));
		}}
		
		//File-Behandlung
		Part filepart = request.getPart("image");
		rezept.setFilename(filepart.getSubmittedFileName());
		
		//Bild in Bean speichern
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				InputStream in = filepart.getInputStream() ) {
			int i = 0;
			while ((i = in.read()) != -1) {
				baos.write(i);
			}
			rezept.setImage(baos.toByteArray());
			baos.flush();
		} catch (IOException ex) {
			throw new ServletException(ex.getMessage());
		}
		
		request.setAttribute("rezept", rezept);
		
		try {
			createRezept(rezept);
			insertIngredients(rezept);
			message = "Das Rezept wurde erfolgreich angelegt!";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("message", message);
		
		//http://stackoverflow.com/questions/12021087/passing-data-between-two-servlets
		getServletContext().getRequestDispatcher("/LoadRecipeServlet");
		RequestDispatcher disp = request.getRequestDispatcher("/LoadRecipeServlet?id=" + rezept.getId());
		disp.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}
	
	public RezeptBean createRezept(RezeptBean rezept) throws SQLException{
		final Connection con = ds.getConnection();

		String[] generatedKeys = new String[] { "id" };

		PreparedStatement ps = con.prepareStatement("insert into recipes(name,creator,description,difficulty,durationCooking,durationPreparation,servings,filename,image, ratingCount, ratingSum) values(?,?,?,?,?,?,?,?,?,?,?)",generatedKeys);


		ps.setString(1, rezept.getName());
		ps.setLong(2, rezept.getCreator().getID());
		ps.setString(3, rezept.getDescription());
		ps.setInt(4, rezept.getDifficulty());
		ps.setInt(5, rezept.getDurationCooking());
		ps.setInt(6, rezept.getDurationPreparation());
		ps.setInt(7, rezept.getServings());
		ps.setString(8, rezept.getFilename());
		ps.setBytes(9, rezept.getImage());
		ps.setInt(10, 0);
		ps.setInt(11, 0);
		
		ps.executeUpdate();

		ResultSet rs = ps.getGeneratedKeys();
		while (rs.next()) {
			rezept.setId(rs.getLong(1));
		}
		con.close();
		return rezept;

	}
	
	public void insertIngredients(RezeptBean rezept) throws SQLException{
		final Connection con = ds.getConnection();
		
		for(int i = 0; i < rezept.getIngredients().size(); i++){
		PreparedStatement ps = con.prepareStatement("insert into ingredients(recipe, ingredient, unit, quantity) values(?,?,?,?)");
		
		ps.setLong(1, rezept.getId());
		ps.setString(2, rezept.getIngredients().get(i).getIngredient());
		ps.setString(3, rezept.getIngredients().get(i).getUnit());
		ps.setInt(4, rezept.getIngredients().get(i).getQuantity());		
		
		ps.executeUpdate();
		}
		con.close();
		
	}
}