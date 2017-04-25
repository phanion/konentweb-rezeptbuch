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
		final String rezept = request.getParameter("rezept");
		final String zutaten = request.getParameter("zutaten");
		final String beschreibung = request.getParameter("beschreibung");
		
		//final String bild = request.getParameter("bild");
		
		// Response vorbereiten
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		final PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<body>");
		out.println("<h2> Das Rezept " + rezept + " wurde erstellt!</h2>");
		out.println("<h3> Zutaten: </h3>" + zutaten);
		out.println("<h3> Beschreibung </h3>" + beschreibung);
		out.println("</body>");
		out.println("</html>");
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doGet(request, response);
		
	}
}