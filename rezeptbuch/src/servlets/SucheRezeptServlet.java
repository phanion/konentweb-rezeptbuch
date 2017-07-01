package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

/**
 * Servlet implementation class SucheRezeptServlet
 */
@WebServlet("/SucheRezeptServlet")
public class SucheRezeptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	/**
	 * Gibt eine Liste mit Rezepten zurück, die den eingegebenen Namen
	 * enthalten.
	 * <p>
	 * Dafür wird eine Datenbankabfrage durchgeführt, und die Treffer in einer
	 * Liste von RezeptBeans abgespeichert.
	 * 
	 * @param rezept
	 *            Der Name/Namensbestandteil eines Rezepts, wird mit den
	 *            Rezeptnamen der Rezept-Datenbank abgeglichen
	 * @return Eine Liste von RezeptBeans
	 * @throws ServletException
	 */
	private List<RezeptBean> search(String rezept) throws ServletException {
		rezept = (rezept == null || rezept == "") ? "%" : "%" + rezept + "%";
		List<RezeptBean> rezepte = new ArrayList<RezeptBean>();

		// DB-Zugriff
		try (Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT * FROM recipes WHERE name LIKE ?")) {

			pstmt.setString(1, rezept);
			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					RezeptBean rezBean = new RezeptBean();

					Long id = Long.valueOf(rs.getLong("id"));
					rezBean.setId(id);

					String rezName = rs.getString("name");
					rezBean.setName(rezName);

					Integer durPrep = rs.getInt("durationPreparation");
					rezBean.setDurationPreparation(durPrep);

					Integer durCook = rs.getInt("durationCooking");
					rezBean.setDurationCooking(durCook);

					Integer difficulty = rs.getInt("difficulty");
					rezBean.setDifficulty(difficulty);

					String description = rs.getString("description");
					rezBean.setDescription(description);

					rezepte.add(rezBean);
				}
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}

		return rezepte;
	}

	public SucheRezeptServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Servlet zur Entgegennahme von Formularinhalten, Lesen der Daten in
		// einer DB und Generierung
		// eines Beans zur Weitergabe der Formulardaten an eine JSP

		request.setCharacterEncoding("UTF-8"); // In diesem Format erwartet das
												// Servlet jetzt die
												// Formulardaten
		String rezept = request.getParameter("rezept");

		// DB-Zugriff
		List<RezeptBean> rezepte = search(rezept);

		// Scope "Request"
		request.setAttribute("rezepte", rezepte);

		// Weiterleiten an JSP
		final RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/rezepte_gefunden.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
