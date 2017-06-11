/**
 * 
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import bean.RezeptBean;

/**
 * @author michael
 *
 */
public class DatabaseFunctions {

	/**
	 * 
	 */
	public DatabaseFunctions() {
		// TODO Auto-generated constructor stub
	}

	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}



	/**
	 * Gibt eine Liste mit Rezepten zurück, die das eingegebene Suchwort enthalten.
	 * <p>
	 * Dafür wird eine Datenbankabfrage durchgeführt, und die Treffer in einer
	 * Liste von RezeptBeans abgespeichert.
	 * 
	 * @param searchstring
	 *            Das Suchwort wird mit dem Namensbestandteils eines Autors, Rezepts, 
	 *            oder einer Rezepbeschreibung, wird  in der Rezept-Datenbank abgeglichen.
	 * @return Eine Liste von RezeptBeans
	 * @throws ServletException bei einem Servlet-Fehler
	 * @throws SQLException by einem Datenbank-Fehler
	 */
	public List<RezeptBean> search(String searchstring) throws ServletException, SQLException {
		
		DataSource ds = getDataSource();
			
		searchstring = (searchstring == null || searchstring == "") ? "%" : "%" + searchstring + "%";
		
		List<RezeptBean> rezepte = new ArrayList<RezeptBean>();
		
		// DB-Zugriff
		try (Connection con = ds.getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT * FROM v_rezeptuser WHERE CONCAT_WS('', authorfirstname, authorlastname, recipename, description) LIKE ?")) {

			pstmt.setString(1, searchstring);
			
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

}
