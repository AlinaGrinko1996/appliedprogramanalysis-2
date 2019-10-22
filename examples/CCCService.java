package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.LatLngLiteral;
import entity.Poligon;
import entity.SensorsData;

public class CCCService {
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public CCCService(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ccc_v", "root", "");
			st = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SensorsData selectByNumber(String number){
		String id = "";
		String CO2 = "";
		String NH3 = "";
		String smoke = "";
		String sound = "";
		try {
			String query = "SELECT * FROM `sensors` WHERE `number`= \"" + number + "\"";
			rs = st.executeQuery(query);

			while (rs.next()) {
				id = rs.getString("number");
				CO2 = rs.getString("co2");
				NH3 = rs.getString("nh3");
				smoke = rs.getString("smoke");
				sound = rs.getString("sound");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new SensorsData(id, CO2, NH3, smoke, sound);
	}
	
	public void insertArea(String id, String lng, String lat){
		try{
			String query = "INSERT INTO `areas`(`id`, `lng`, `lat`) VALUES (\""
					+ id + "\",\"" + lng + "\",\"" + lat + "\")";
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Poligon> getPoligons(){	
		String id = "";
		String lat = "";
		String lng = "";
		
		List<Poligon> result=new ArrayList<Poligon>();
		try {
			String query = "SELECT DISTINCT * FROM `areas`";
			rs = st.executeQuery(query);
			
			while (rs.next()) {
				id = rs.getString("id");
				lat = rs.getString("lat");
				lng = rs.getString("lng");
				
				Poligon temporary=new Poligon(Integer.parseInt(id),
						                      new LatLngLiteral( Double.parseDouble(lat),
					                                             Double.parseDouble(lng)));
				
				result.add(temporary);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
