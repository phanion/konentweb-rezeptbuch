package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ErstellenServlet")
public class ErstellenServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
		request.setCharacterEncoding("UTF-8");
		
		// Parameter von dem Request werden geholt
		final String zutat = request.getParameter("zutaten");
		final String beschreibung = request.getParameter("beschreibung");
		
		final String bild = request.getParameter("bild");
		
		// Response vorbereiten
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		final PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<body>");
		out.println("<h4>" + zutat + " und " + beschreibung + "<h4>");
		out.println("</body>");
		out.println("</html>");
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doGet(request, response);
		
	}
}