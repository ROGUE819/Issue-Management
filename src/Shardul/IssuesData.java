package Shardul;

import java.sql.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class IssuesData {
	private int issueID = 0;
	private String name = null;
	private String description = null;
	private String priority = null;
	private String overallStatus = null;
	private Date dateIdentified = null;
	private Date dateClosed = null;
	private String showStopper = null;
	private String resolution = null;
	private String resolved = null;
	private int currentUser = 0;
	private int createdBy = 0;
	private String createdByName = null;
	
	
	public int getIssueID() {
		return issueID;
	}
	public void setIssueID(int issueID) {
		this.issueID = issueID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getOverallStatus() {
		return overallStatus;
	}
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}
	public Date getDateIdentified() {
		return dateIdentified;
	}
	public void setDateIdentified(Date dateIdentified) {
		this.dateIdentified = dateIdentified;
	}
	public Date getDateClosed() {
		return dateClosed;
	}
	public void setDateClosed(Date dateClosed) {
		this.dateClosed = dateClosed;
	}
	public String getShowStopper() {
		return showStopper;
	}
	public void setShowStopper(String showStopper) {
		this.showStopper = showStopper;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getResolved() {
		return resolved;
	}
	public void setResolved(String resolved) {
		this.resolved = resolved;
	}
	public int getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(int currentUser) {
		this.currentUser = currentUser;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String toJSONString() throws JSONException{
		return toJSON().toString();
	}
	
	public JSONObject toJSON() throws JSONException{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("issueID", this.getIssueID());
		jsonObject.put("name", this.getName());
		jsonObject.put("description", this.getDescription());
		jsonObject.put("priority", this.getPriority());
		jsonObject.put("overallStatus", this.getOverallStatus());
		jsonObject.put("dateIdentified", this.getDateIdentified());
		jsonObject.put("dateClosed", this.getDateClosed());
		jsonObject.put("showStopper",this.getShowStopper());
		jsonObject.put("resolution", this.getResolution());
		jsonObject.put("resolved", this.getResolved());
		jsonObject.put("currentUser", this.getCurrentUser());
		jsonObject.put("createdBy", this.getCreatedBy());
		
		
		return jsonObject;
	}
}
