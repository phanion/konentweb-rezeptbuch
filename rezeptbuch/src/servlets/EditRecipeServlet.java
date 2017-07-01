/**
 * Autor: Lorenz
 * Refactoring: Florian
 */

package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

	@Resource(lookup = "mail/MyMailSession")
	private Session mailSession;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditRecipeServlet() {
		super();
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
		final String recipeName = request.getParameter("recipeName");
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
		rezept.setName(recipeName);

		if (ingredients != null) {
			for (int i = 0; i < ingredients.length; i++) {
				rezept.addIngredient(ingredients[i], units[i], Integer.parseInt(quantities[i]));
			}
		}

		/*
		 * File-Behandlung Part filepart = request.getPart("image");
		 * rezept.setFilename(filepart.getSubmittedFileName());
		 * 
		 * // Bild in Bean speichern try (ByteArrayOutputStream baos = new
		 * ByteArrayOutputStream(); InputStream in = filepart.getInputStream())
		 * { int i = 0; while ((i = in.read()) != -1) { baos.write(i); }
		 * rezept.setImage(baos.toByteArray()); baos.flush(); } catch
		 * (IOException ex) { throw new ServletException(ex.getMessage()); }
		 */

		request.setAttribute("rezept", rezept);

		User sessionUser = (User) session.getAttribute("user");

		if (sessionUser.getId() == creatorID) {
			try {
				updateRecipe(rezept);
				replaceIngredients(rezept);
				sendAboMails(rezept);
				message = "Das Rezept wurde erfolgreich geändert!";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
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

		doGet(request, response);
	}

	public void updateRecipe(RezeptBean recipe) throws SQLException {
		final Connection con = ds.getConnection();

		PreparedStatement ps = con.prepareStatement(
				"UPDATE recipes SET description=?, difficulty=?, durationCooking=?, durationPreparation=?, servings=?, name=? where id=?");

		ps.setString(1, recipe.getDescription());
		ps.setInt(2, recipe.getDifficulty());
		ps.setInt(3, recipe.getDurationCooking());
		ps.setInt(4, recipe.getDurationPreparation());
		ps.setInt(5, recipe.getServings());
		ps.setString(6, recipe.getName());
		
		ps.setLong(7, recipe.getId());

		ps.executeUpdate();

		con.close();

	}

	public void replaceIngredients(RezeptBean recipe) throws SQLException {
		final Connection con = ds.getConnection();

		PreparedStatement delete = con.prepareStatement("delete from ingredients where recipe=?");
		delete.setLong(1, recipe.getId());
		delete.executeUpdate();

		for (int i = 0; i < recipe.getIngredients().size(); i++) {
			PreparedStatement ps = con
					.prepareStatement("insert into ingredients(recipe, ingredient, unit, quantity) values(?,?,?,?)");

			ps.setLong(1, recipe.getId());
			ps.setString(2, recipe.getIngredients().get(i).getIngredient());
			ps.setString(3, recipe.getIngredients().get(i).getUnit());
			ps.setInt(4, recipe.getIngredients().get(i).getQuantity());

			ps.executeUpdate();
		}
		con.close();

	}

	/**
	 * Autor: Lorenz 
	 * 
	 * Es werden die Abonnenten des Rezepts aus der Datenbank
	 * geladen und per Mail über die Änderung informiert.
	 * 
	 * @throws SQLException
	 */
	public void sendAboMails(RezeptBean recipe) throws SQLException {
		final Connection con = ds.getConnection();

		PreparedStatement ps = con.prepareStatement(
				"select users.firstName, users.lastName, users.mail, recipes.name from users inner join abos on users.id = abos.user inner join recipes on abos.recipe = recipes.id where abos.recipe = ?");
		ps.setLong(1, recipe.getId());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			MimeMessage message = new MimeMessage(mailSession);

			try {
				message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
				InternetAddress[] address = { new InternetAddress(rs.getString("mail")) };
				message.setRecipients(Message.RecipientType.TO, address);
				message.setSubject("Rezept wurde aktualisiert");
				message.setSentDate(new Date());
				message.setContent(
						"Hallo " + rs.getString("firstName") + " " + rs.getString("lastName")
								+ "<p>Das von Ihnen abonnierte Rezept " + rs.getString("name") + " wurde aktualisiert.",
						"text/html; charset=utf-8");
				Transport.send(message);
			} catch (MessagingException ex) {
				ex.printStackTrace();
			}
		}
		con.close();
	}

}
