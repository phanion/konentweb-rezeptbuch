/*
 * Autor: Lorenz
 * Refactoring: Michael
 * 
 * 
 */

package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import bean.User;

/**
 * Servlet implementation class registration
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;

	@Resource(lookup = "mail/MyMailSession")
	private Session mailSession;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationServlet() {
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

		final String mail = request.getParameter("mail").toLowerCase();
		final String lastName = request.getParameter("lastName");
		final String firstName = request.getParameter("firstName");
		final String password = request.getParameter("password");
		final String password_retype = request.getParameter("password_retype");
		String message = null;

		HttpSession session = request.getSession();

		if (!password.equals(password_retype)) {
			message = "Die eingegebenen Passwörter stimmen nicht überein!";
		}

		else {

			try {

				if (!userUnique(mail)) {
					message = "Der Nutzer existiert bereits!";

				}

				else {

					User user = new User();

					user.setMail(mail);
					user.setLastName(lastName);
					user.setFirstName(firstName);
					user.setPassword(password);

					session.setAttribute("user", user);

					if (createUser(user)) {
						message = "Der Nutzer wurde erfolgreich angelegt!";
						sendWelcomeEmail(user.getMail(), user.getFirstName() + " " + user.getLastName());

					} else {
						message = "Der User konnte nicht angelegt werden!";

					}

				}

			}

			catch (Exception e) {
				response.getWriter().append(e.getMessage());
			}

		}

		// http://stackoverflow.com/questions/6452537/servlet-send-response-to-jsp
		request.setAttribute("message", message);
		String target = (session.getAttribute("user") != null) ? "/index.jsp" : "/jsp/registration.jsp";
		RequestDispatcher disp = request.getRequestDispatcher(target);

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

	/**
	 * PrÃ¼ft, ob die eingegebene E-Mail (und der zugehÃ¶rige Nutzer) bereits in
	 * der Datenbank vorhanden sind.
	 * 
	 * @param mail
	 *            Ein String einer E-Mail-Adresse
	 * @return true, falls die E-Mail noch nicht in der Datenbank vorhanden ist,
	 *         false, falls sie bereits vorhanden ist.
	 * @throws ServletException
	 *             wenn ein Datenbank-Fehler auftritt
	 */
	public boolean userUnique(String mail) throws ServletException {
		try {
			final Connection con = ds.getConnection();

			PreparedStatement ps = con.prepareStatement("select mail from users where mail=?;");
			ps.setString(1, mail);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getString(1).equals(mail)) {
					return false;

				}
				return true;
			}
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException(e.getMessage());
		}

	}

	/**
	 * Schreibt einen User in die Datenbank
	 * 
	 * @param user
	 *            Der eingelesene User
	 * @return true, wenn Der User erfolgreich in die Datebank eingetragen wurde
	 * @throws ServletException
	 *             Bei einem Datenbankfehler
	 */
	public boolean createUser(User user) throws ServletException {
		try {
			final Connection con = ds.getConnection();

			String[] generatedKeys = new String[] { "id" };

			PreparedStatement ps;

			ps = con.prepareStatement("insert into users(mail,lastName,firstName,password) values(?,?,?,?)",
					generatedKeys);

			ps.setString(1, user.getMail());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getPassword());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				user.setId(rs.getLong(1));
			}
			con.close();
			return true;

		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException(e.getMessage());
		}
	}


	/**
	 * Sendet eine Willkommensnachricht an einen neuen Nutzer
	 * <p>
	 * @see https://dzone.com/articles/sending-email-using-javamail
	 * 
	 * @param recipientMail Die E-Mail-Adresse des neuen Nutzers
	 * @param recipientName Der Name des neuen Nutzers
	 */
	public void sendWelcomeEmail(String recipientMail, String recipientName) {
		MimeMessage message = new MimeMessage(mailSession);

		try {
			message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
			InternetAddress[] address = { new InternetAddress(recipientMail) };
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject("Herzlich Willkommen");
			message.setSentDate(new Date());
			message.setContent("Hallo " + recipientName + "<p>Herzlich Willkommen bei Rezeptbuch.", "text/html; charset=utf-8");
			Transport.send(message);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}

}

