package servlets;

import java.io.IOException;
import java.sql.Blob;
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

import com.sun.research.ws.wadl.Request;

import bean.RezeptBean;

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
		List<Object> treffer = new ArrayList<Object>();
		try {
			treffer = search(searchstring);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("treffer",  treffer);
		
		RequestDispatcher disp = request.getRequestDispatcher("/jsp/searchresults.jsp");
		disp.forward(request, response);
	}

	/**
	 * Gibt eine Liste mit Rezepten zurück, die den eingegebenen Namen
	 * enthalten.
	 * <p>
	 * Dafür wird eine Datenbankabfrage durchgeführt, und die Treffer in einer
	 * Liste von RezeptBeans abgespeichert.
	 * 
	 * @param searchstring
	 *            Der Name/Namensbestandteil eines Rezepts, wird mit den
	 *            Rezeptnamen der Rezept-Datenbank abgeglichen
	 * @return Eine Liste von RezeptBeans
	 * @throws ServletException
	 */
	protected List<Object> search(String searchstring) throws ServletException, SQLException {

		searchstring = (searchstring == null || searchstring == "") ? "%" : "%" + searchstring + "%";

		List<Object> trefferListe = new ArrayList<Object>();

		// DB-Zugriff
		try (Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(
						"SELECT * FROM v_rezeptuser WHERE CONCAT_WS('', authorfirstname, authorlastname, recipename, description) LIKE ?")) {

			pstmt.setString(1, searchstring);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					Object treffer = new Object() {
						public final Long authorID = rs.getLong("authorid");
						public final String authorFullName = rs.getLong("authorfirstname") + " "
								+ rs.getString("authorlastname");
						public final Long recipeID = rs.getLong("recipeid");
						public final String recipeName = rs.getString("recipename");
						public final String recipedescription = rs.getString("description");
						public final Integer prepDuration = rs.getInt("durationPreparation");
						public final Integer cookDuration = rs.getInt("durationCooking");
						public final Integer difficulty = rs.getInt("difficulty");
						public final Integer servings = rs.getInt("servings");
						public final Blob image = rs.getBlob("image");
						public final String filename = rs.getString("filename");
					};

					trefferListe.add(treffer);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} 
		return trefferListe;
	}
}
