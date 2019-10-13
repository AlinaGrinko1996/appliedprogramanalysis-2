package api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddArea
 */
@WebServlet("/AddArea")
public class AddArea extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String adress = "http://localhost:8080/CCC_DAL/AddArea";
     
	    public AddArea() {
	        super();
	    }
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String lat=request.getParameter("lat");
		    String lng=request.getParameter("lng");
		    String id=request.getParameter("id");
		    
		    if (lat==null||lng==null||id==null)
		    	return;
		    
		    response.sendRedirect(adress+"?lat="+lat+"&lng="+lng+"&id="+ id);
		}

}
