package api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetSensorsData")
public class GetSensorsData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetSensorsData() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sensorsNumber=request.getParameter("number");
		if (sensorsNumber==null){
			return;
		}		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.sendRedirect("http://localhost:8080/CCC_DAL/GetSensorsData?number="+
				                                                             sensorsNumber);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
