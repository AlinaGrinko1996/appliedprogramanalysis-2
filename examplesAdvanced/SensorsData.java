package entity;

import org.json.JSONObject;

public class SensorsData {
    private String id;
    private String CO2;
    private String NH3;
    private String smoke;
    private String sound;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCO2() {
		return CO2;
	}
	public void setCO2(String cO2) {
		CO2 = cO2;
	}
	public String getNH3() {
		return NH3;
	}
	public void setNH3(String nH3) {
		NH3 = nH3;
	}
	public String getSmoke() {
		return smoke;
	}
	public void setSmoke(String smoke) {
		this.smoke = smoke;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
    
    public SensorsData(String id, String CO2, String NH3, String smoke, String sound){ 	
    	this.id=id;
    	this.CO2=CO2;
    	this.NH3=NH3;
    	this.smoke=smoke;
    	this.sound=sound;
    }
    
    public JSONObject toJSON(){
    	JSONObject object=new JSONObject();
    	object.put("id", this.id);
    	object.put("CO2", this.CO2);
    	object.put("NH3", this.NH3);
    	object.put("smoke", this.smoke);
    	object.put("sound", this.sound);
		return object;
    	
    }
}
