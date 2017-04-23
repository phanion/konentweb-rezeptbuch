package servlets;

import java.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class registration
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Anmerkung: Später muss das weg. Es gibt für PW-Übertragung keine GET Methode
		
		final String name = request.getParameter("name");
		final String prename = request.getParameter("prename");
		final String mail = request.getParameter("mail");
		final String password = request.getParameter("password");
		final String password_retype = request.getParameter("password_retype");
		
		if(!password.equals(password_retype)){
			response.getWriter().append("Passwort nicht richtig wiederholt");
		}
		else{
			User user = new User(mail, name, prename, password);
			response.getWriter().append((CharSequence) user.getName()).append((CharSequence) user.getVorname());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
