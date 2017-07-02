/**
 * @author Lorenz
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

import bean.Comment;
import bean.RezeptBean;
import bean.User;

/**
 * Servlet implementation class AddCommentServlet
 */
@WebServlet({ "/AddCommentServlet", "/addcommentservlet" })
public class AddCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCommentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	@Resource(lookup = "mail/MyMailSession")
	private Session mailSession;

	/**
	 * Das Servlet f�gt zu einem Rezept einen Kommentar hinzu und gibt diesen
	 * Kommentar per JSON an die Seite zur�ck. Beim Erstellen des Kommentars
	 * werden auch alle Abonennten benachrichtigt
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		/**
		 * final User author = (User) session.getAttribute("user"); final Long
		 * recipe = Long.parseLong(request.getParameter("recipe")); final String
		 * comment = request.getParameter("comment");
		 **/

		final Comment comment = new Comment();
		final RezeptBean recipe = new RezeptBean();
		
		request.setCharacterEncoding("UTF-8");

		recipe.setId(Long.parseLong(request.getParameter("recipe")));

		comment.setAuthor((User) session.getAttribute("user"));
		comment.setRecipe(recipe);
		comment.setComment(request.getParameter("comment"));

		try {

			final Connection con = ds.getConnection();
			String[] generatedKeys = new String[] { "id" };

			PreparedStatement ps = con.prepareStatement("INSERT INTO comments(author,recipe,comment) values(?,?,?)",
					generatedKeys);
			ps.setLong(1, comment.getAuthor().getId());
			ps.setLong(2, comment.getRecipe().getId());
			ps.setString(3, comment.getComment());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				comment.setId(rs.getLong(1));
			}

			con.close();

			request.setAttribute("comment", comment);

			sendAboMails(recipe, comment);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/commentResponse.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Autor: Lorenz Es wird eine Mail an die Abbonenten gesendet, wenn ein
	 * Kommentar zum Rezept hinzugef�gt wird. Der Autor des Kommentars soll
	 * dabei keine Mail bekommen.
	 * 
	 * @throws SQLException
	 */
	public void sendAboMails(RezeptBean recipe, Comment comment) throws SQLException {
		final Connection con = ds.getConnection();

		PreparedStatement ps = con.prepareStatement(
				"select users.id, users.firstName, users.lastName, users.mail, recipes.name from users inner join abos on users.id = abos.user inner join recipes on abos.recipe = recipes.id where abos.recipe = ?");
		ps.setLong(1, recipe.getId());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			if (comment.getAuthor().getId() != rs.getLong("id")) {
				MimeMessage message = new MimeMessage(mailSession);

				try {
					message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
					InternetAddress[] address = { new InternetAddress(rs.getString("mail")) };
					message.setRecipients(Message.RecipientType.TO, address);
					message.setSubject("Rezept wurde kommentiert");
					message.setSentDate(new Date());
					message.setContent("Hallo " + rs.getString("firstName") + " " + rs.getString("lastName")
							+ ",<p>das von dir abonnierte Rezept " + rs.getString("name") + " wurde kommentiert.",
							"text/html; charset=utf-8");
					Transport.send(message);
				} catch (MessagingException ex) {
					ex.printStackTrace();
				}
			}
		}
		con.close();
	}

}
