package Shardul;

import java.sql.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class UserData {
	private String loginID = null;
	private String firstName = null;
	private String lastName = null;
	private String role = null;
	private Date creationDate = null;   
	private int userID = 0;
	
	public String getLoginID() {
		return loginID;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String toJSON() throws JSONException{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("firstName", this.getFirstName());
		jsonObject.put("lastName", this.getLastName());
		jsonObject.put("loginID", this.getLoginID());
		jsonObject.put("role", this.getRole());
		jsonObject.put("creationDate", this.getCreationDate());
		jsonObject.put("userID", this.getUserID());
		
		return jsonObject.toString();
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
