package entity;
import org.json.JSONObject;

public class LatLngLiteral{

	private Double lat;
	private Double lng;
	
	public LatLngLiteral(double lt, double lg){
		this.lat=lt;
		this.lng=lg;
	}
	
	public JSONObject ToJson() {
		JSONObject result=new org.json.JSONObject();
		result.put("lat", this.lat);
		result.put("lng", this.lng);
		return result;
	}
}
