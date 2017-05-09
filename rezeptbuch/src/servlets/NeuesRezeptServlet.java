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

@WebServlet("/NeuesRezeptServlet")
public class NeuesRezeptServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {



		// Parameter von dem Request werden geholt
		final String name = request.getParameter("name");
		final String zutaten = request.getParameter("zutaten");
		final String description = request.getParameter("description");
		final Integer durationPreparation = request.getParameter("durationPreparation").equals("") ? null : Integer.parseInt(request.getParameter("durationPreparation"));
		final Integer durationCooking = request.getParameter("durationCooking").equals("") ? null : Integer.parseInt(request.getParameter("durationCooking"));
		final Integer difficulty = request.getParameter("difficulty").equals("") ? null : Integer.parseInt(request.getParameter("difficulty"));
		final Integer servings = request.getParameter("servings").equals("") ? null : Integer.parseInt(request.getParameter("servings"));
		
		

		HttpSession session = request.getSession();

		RezeptBean rezept = new RezeptBean();

		rezept.setCreator((User) session.getAttribute("user"));
		rezept.setName(name);
		rezept.setDescription(description);
		rezept.setZutatenText(zutaten);
		rezept.setDifficulty(difficulty);
		rezept.setDurationCooking(durationCooking);
		rezept.setDurationPreparation(durationPreparation);
		rezept.setServings(servings);
		
		request.setAttribute("rezept", rezept);
		
		try {
			createRezept(rezept);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.getWriter().append(rezept.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}
	
	public void createRezept(RezeptBean rezept) throws ServletException, SQLException{
		final Connection con = ds.getConnection();

		String[] generatedKeys = new String[] { "id" };

		PreparedStatement ps = con.prepareStatement("insert into recipes(name,creator,description,difficulty,durationCooking,durationPreparation,servings) values(?,?,?,?,?,?,?)",generatedKeys);


		ps.setString(1, rezept.getName());
		ps.setInt(2, rezept.getCreator().getID());
		ps.setString(3, rezept.getDescription());
		ps.setInt(4, rezept.getDifficulty());
		ps.setInt(5, rezept.getDurationCooking());
		ps.setInt(6, rezept.getDurationPreparation());
		ps.setInt(7, rezept.getServings());
		
		ps.executeUpdate();

		ResultSet rs = ps.getGeneratedKeys();
		while (rs.next()) {
			rezept.setId(rs.getInt(1));
		}

	}
}