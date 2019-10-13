package entity;
import org.json.*;

public class Poligon {
	private int id;
	private LatLngLiteral center;
	
	private LatLngLiteral[] paths;
	
	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id=id;
	}
	public LatLngLiteral getCenter(){
		return this.center;
	}
	public void setCenter(LatLngLiteral center){
		this.center=center;
	}
	public LatLngLiteral[] getPaths(){
		return this.paths;
	}
	public void setPath(LatLngLiteral[] paths){
		this.paths=paths;
	}
	
	public Poligon(){
		this.id=0;
		this.center=new LatLngLiteral(0,0);
		LatLngLiteral[] array = {
				new LatLngLiteral(50.05637,36.22948),
				new LatLngLiteral(50.05237,36.22948),
				new LatLngLiteral(50.05037,36.23148),
				new LatLngLiteral(50.05237,36.23348),
				new LatLngLiteral(50.05637,36.23348),
				new LatLngLiteral(50.05837,36.23148)};
		this.paths=array;
	}
	
	public JSONObject toJson(){
		JSONObject result=new JSONObject();
		result.put("center", this.center.ToJson());
		JSONArray pathsArray=new JSONArray();
		for(LatLngLiteral path : paths){
			pathsArray.put(path.ToJson());
		}
		result.put("paths", pathsArray);
		result.put("id",this.id);
		result.put("center", this.center.ToJson());
		return result;
	}
}
