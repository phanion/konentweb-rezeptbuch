package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class NewSearchServlet
 * 
 * @author michael
 */
@WebServlet(description = "Handhabt eine Suche nach Rezepten", urlPatterns = { "/newsearch", "/NewSearchServlet" })
public class NewSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewSearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Bei Aufruf via GET-Methode wird die Suchseite zurückgeliefert, so kann
	 * auch über einen Direktlink eine Suche ausgeführt werden.
	 * <p>
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("--------------" + request.getParameter("searchstring") + "---------------");

		// HttpSession session = request.getSession();
		request.setAttribute("searchstring", request.getParameter("searchstring"));

		RequestDispatcher disp = request.getRequestDispatcher("/jsp/rezeptsuche2.jsp");
		disp.forward(request, response);
	}

	/**
	 * Bei Aufruf via POST-Methode wird nur ein Fragment mit den Suchergebnissen
	 * zurückgeliefert, nicht die gesamte Seite.
	 * <p>
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String searchstring = request.getParameter("searchstring");
		List<HashMap<String, Object>> treffer = new ArrayList<HashMap<String, Object>>();
		try {
			treffer = search(searchstring);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("treffer", treffer);

		RequestDispatcher disp = request.getRequestDispatcher("/jsp/searchresults.jsp");
		disp.forward(request, response);

	}

	/**
	 * Gibt eine Liste mit Rezepten zurück, die den eingegebenen Suchstring
	 * enthalten.
	 * <p>
	 * Dafür wird eine Datenbankabfrage durchgeführt, und die Treffer in einer
	 * Liste von RezeptBeans (aktuell: HashMaps) abgespeichert. Es werden
	 * Autorname, Rezeptname, Rezeptbeschreibung und Rezeptzutaten durchsucht.
	 * Die Ergebnisliste wird nach der Anzahl der Treffer sortiert.
	 * 
	 * @param searchstring
	 *            Der Suchbegriff der mit den Rezepten der Rezept-Datenbank
	 *            abgeglichen wird.
	 * @return Eine Liste von RezeptBeans (aktuell: HashMaps)
	 * @throws ServletException
	 *             bei einem Servletfehler
	 * @throws SQLException
	 *             bei eiem Datenbankfehler
	 */
	protected List<HashMap<String, Object>> search(String searchstring) throws ServletException, SQLException {
		List<HashMap<String, Object>> trefferMapListe = new ArrayList<HashMap<String, Object>>();

		searchstring = (searchstring == null || searchstring == "") ? "%" : "%" + searchstring + "%";

		try (Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT "
						+ "authorid, authorfirstname, authorlastname, recipeid, recipename, description, durationPreparation, durationCooking, difficulty, servings, filename "
						+ "FROM v_rezeptuser "
						+ "WHERE concat_ws(' ', authorfirstname, authorlastname, recipename, description, ingredient) LIKE ? "
						+ "group by recipeid " + "order by count(recipeid) desc")) {

			pstmt.setString(1, searchstring);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					HashMap<String, Object> trefferMap = new HashMap<String, Object>();

					trefferMap.put("authorID", rs.getLong("authorid"));
					trefferMap.put("authorFullName",
							rs.getString("authorfirstname") + " " + rs.getString("authorlastname"));
					trefferMap.put("recipeID", rs.getLong("recipeid"));
					trefferMap.put("recipeName", rs.getString("recipename"));
					trefferMap.put("recipeDescription", rs.getString("description"));
					trefferMap.put("prepDuration", rs.getInt("durationPreparation"));
					trefferMap.put("cookDuration", rs.getInt("durationCooking"));
					trefferMap.put("difficulty", rs.getInt("difficulty"));
					trefferMap.put("servings", rs.getInt("servings"));
					// trefferMap.put("image", rs.getBlob("image"));
					trefferMap.put("filename", rs.getString("filename"));
					// Zutat kann mit der bestehenden Abfrage nicht in die
					// Ergebnisliste geholt werden
					// trefferMap.put("ingredient", rs.getString("ingredient"));

					trefferMapListe.add(trefferMap);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return trefferMapListe;
	}

}
