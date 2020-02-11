package api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.SensorsData;

/**
 * Servlet implementation class SRForCoordinate
 */
@WebServlet("/SRForCoordinate")
public class SRForCoordinate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SRForCoordinate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String lat=request.getParameter("lat");
	    String lng=request.getParameter("lng");

	    
	    if (lat==null||lng==null)
	    	return;
	    String adress = "http://10.0.2.2:8080/CCC_DAL/SRForCoordinate";
		response.addHeader("Access-Control-Allow-Origin", "*");
	    response.sendRedirect(adress+"?lat="+lat+"&lng="+lng);
//	    
//	    SensorsData sd=new SensorsData("1","2","3","4","5");
//		response.getWriter().println(sd.toJSON());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
