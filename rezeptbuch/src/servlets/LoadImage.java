package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class LoadImage
 */
@WebServlet({ "/LoadImage", "/loadimage" })
public class LoadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadImage() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Resource(lookup = "jdbc/MyRezeptbuchPool")
	private DataSource ds;
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final Long id = Long.parseLong(request.getParameter("id"));
		final String table = request.getParameter("table");
		
		try(Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT image from " + table + "  where id=?")){
			
			ps.setLong(1, id);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs!= null && rs.next()){
					Blob image = rs.getBlob("image");
					response.reset();
					long length = image.length();
					response.setHeader("Content-Length", String.valueOf(length));
					
					try(InputStream in = image.getBinaryStream();){
						final int bufferSize = 256;
						byte[] buffer = new byte[bufferSize];
						
						ServletOutputStream out = response.getOutputStream();
						while ((length = in.read(buffer)) != -1){
							out.write(buffer, 0, (int) length);
							
						}
						out.flush();
					}
				}
			}
			catch(Exception e){
				throw new ServletException(e.getMessage());
			}
			con.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
