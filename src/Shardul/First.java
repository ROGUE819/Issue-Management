package Shardul;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class First
 */
@WebServlet("/First")
public class First extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public First() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @param sql 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*response.getWriter().append("Served at: ").append(request.getContextPath());*/
		
		String action = request.getParameter("action");
		String resp = null;
		
		try
		{
			if (action != null && !"".equals(action) && "getAllUser".equals(action))
			{
				getAllUsers();
			}
			if (action != null && !"".equals(action) && "getAllIssues".equals(action))
			{
				resp = getAllIssues(request, null);
			}
			if (action != null && !"".equals(action) && "getIssuesForLoggedInUser".equals(action))
			{
				resp = getIssuesForLoggedInUser(request);
			}
			if (action != null && !"".equals(action) && "getLoggedInUserData".equals(action))
			{
				resp = getLoggedInUserData(request);
			}
			if (action != null && !"".equals(action) && "logout".equals(action))
			{
				logout(request);
			}
			if (action !=null && !"".equals(action) && "login".equals(action))
			{
				resp = login(request);
			}
			if (action !=null && !"".equals(action) && "updateUser".equals(action))
			{
				resp = updateUser(request);
			}
			if (action !=null && !"".equals(action) && "createIssue".equals(action))
			{
				resp = createIssue(request);
			}
			if (action !=null && !"".equals(action) && "updateIssue".equals(action))
			{
				resp = updateIssue(request);
			}
			if (action !=null && !"".equals(action) && "getIssue".equals(action))
			{
				resp = getIssueJSON(request);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resp = "There was some error";
			
		}	
		System.out.println(resp);
		
		response.getWriter().append(resp);	
	}
	
	private String getIssueJSON(HttpServletRequest request) throws Exception
	{
		UserData userData = (UserData) request.getSession().getAttribute("userData");
		IssuesData issue = null;
		
		if(request.getParameter("issueId") != null)
		{
			issue = getIssue(request);
		}
		
		JSONArray issueArray = new JSONArray();
		JSONObject issueField = new JSONObject();
		
		issueField.put("fieldName", "Issue ID");
		issueField.put("id", "issueId");
		issueField.put("type", "label");
		issueField.put("editable", false);
		issueField.put("value", issue != null ? issue.getIssueID() : "");
		
		issueArray.put(issueField);
		
		issueField = new JSONObject();
		issueField.put("fieldName", "Name");
		issueField.put("id", "name");
		issueField.put("type", "textbox");
		issueField.put("editable", true);
		issueField.put("value", issue != null ? issue.getName() : "");
		
		issueArray.put(issueField);
		
		issueField = new JSONObject();
		issueField.put("fieldName", "Overall Status");
		issueField.put("id", "overallStatus");
		issueField.put("type", "dropdown");
		issueField.put("value", issue != null ? issue.getOverallStatus() : "open");
		
		JSONArray comboValues = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("name","Open");
		json.put("value","open");
		comboValues.put(json);
		
		json = new JSONObject();
		json.put("name","Closed");
		json.put("value","closed");
		comboValues.put(json);
		
		issueField.put("comboValues", comboValues);
		
		if(userData.getRole().equals("Tester"))
			issueField.put("editable", true);
		else 
			issueField.put("editable", false);
		
		issueArray.put(issueField);
		
		issueField = new JSONObject();
		issueField.put("fieldName", "Current User");
		issueField.put("id", "currentUser");
		issueField.put("type", "dropdown");
		issueField.put("editable", true);
		issueField.put("value", issue != null ? issue.getCurrentUser() : userData.getFirstName() + " " + userData.getLastName());
		
		comboValues = new JSONArray();
		comboValues = getUsersArray();
		
		issueField.put("comboValues", comboValues);
		
		issueArray.put(issueField);
		
		issueField = new JSONObject();
		issueField.put("fieldName", "Created By");
		issueField.put("id", "createdBy");
		issueField.put("type", "label");
		issueField.put("editable", false);
		issueField.put("value", issue != null ? issue.getCreatedByName() : userData.getFirstName() + " " + userData.getLastName());
		
		issueArray.put(issueField);
		
		issueField = new JSONObject();
		issueField.put("fieldName", "Description");
		issueField.put("id", "description");
		issueField.put("type", "textarea");
		issueField.put("editable", true);
		issueField.put("value", issue != null ? issue.getDescription() : "");
		
		issueArray.put(issueField);
		
		issueField = new JSONObject();
		issueField.put("fieldName", "Date Identified");
		issueField.put("id", "dateIdentified");
		issueField.put("type", "label");
		issueField.put("editable", false);
		issueField.put("value", issue != null ? issue.getDateIdentified() : "");
		
		issueArray.put(issueField);
		
		issueField = new JSONObject();
		issueField.put("fieldName", "Priority");
		issueField.put("id", "priority");
		issueField.put("type", "dropdown");
		issueField.put("value", issue != null ? issue.getPriority() : "low");
		
		comboValues = new JSONArray();
		
		json = new JSONObject();
		json.put("name","Low");
		json.put("value","low");
		comboValues.put(json);
		
		json = new JSONObject();
		json.put("name","Medium");
		json.put("value","medium");
		comboValues.put(json);
		
		json = new JSONObject();
		json.put("name","High");
		json.put("value","high");
		comboValues.put(json);
		
		json = new JSONObject();
		json.put("name","Critical");
		json.put("value","critical");
		comboValues.put(json);
		
		issueField.put("comboValues", comboValues);
		
		issueField.put("editable", true);
		
		issueArray.put(issueField);
		
		issueField = new JSONObject();
		issueField.put("fieldName", "Resolution");
		issueField.put("id", "resolution");
		issueField.put("type", "textarea");
		issueField.put("value", issue != null ? issue.getResolution() : "");
		
		if(userData.getRole().equals("Devloper"))
			issueField.put("editable", true);
		else 
			issueField.put("editable", false);
		
		issueArray.put(issueField);
		
		issueField = new JSONObject();
		issueField.put("fieldName", "Resolved");
		issueField.put("id", "resolved");
		issueField.put("type", "checkbox");
		issueField.put("value", issue != null ? issue.getResolved() : "N");
		
		if(userData.getRole().equals("Devloper"))
			issueField.put("editable", true);
		else 
			issueField.put("editable", false);
		
		issueArray.put(issueField);
		
		issueField = new JSONObject();
		issueField.put("fieldName", "Date Closed");
		issueField.put("id", "dateClosed");
		issueField.put("type", "label");
		issueField.put("editable", false);
		issueField.put("value", issue != null ? issue.getDateClosed() : "");
		
		issueArray.put(issueField);
		
		issueField = new JSONObject();
		issueField.put("fieldName", "Show Stopper");
		issueField.put("id", "showStopper");
		issueField.put("type", "checkbox");
		issueField.put("value", issue != null ? issue.getShowStopper() : "N");
		
		if(userData.getRole().equals("Tester"))
			issueField.put("editable", true);
		else 
			issueField.put("editable", true);
		
		issueArray.put(issueField);
		
		return issueArray.toString();
	}
	
	private JSONArray getUsersArray() throws Exception 
	{
		JSONArray usersArray = new JSONArray();
		JSONObject json = null;
		
		PreparedStatement stmt = null;
		Connection conn = null;
		String query = null;
		
		try
		{
			query = "select FirstName, LastName, UserID from userdata";
			
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				json = new JSONObject();
				json.put("name", rs.getString(1) + " " + rs.getString(2));
				json.put("value", rs.getInt(3));
				
				usersArray.put(json);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		finally 
		{
			DbConnection.closePrepConnection(conn, stmt);
		}
		
		return usersArray;
	}
	
	private IssuesData getIssue(HttpServletRequest request) throws Exception {
		
		PreparedStatement stmt = null;
		Connection conn = null;
		PreparedStatement stmt1 = null;
		Connection conn1 = null;
		IssuesData issue = null;	
		String query = null;
		String createdByQuery = null;
		try
		{			
			query = "select IssueID, Name, Description, Priority, OverallStatus, DateIdentified, DateClosed,"
					+ " ShowStopper, Resolution, Resolved, CreatedBy, CurrentUser from issues where issueID=?";
			
			createdByQuery = "select FirstName, LastName from userdata where UserID=?";
														
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(query);
			int	issueId = Integer.parseInt(request.getParameter("issueId"));
			stmt.setInt(1, issueId);			
									
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				
				issue = new IssuesData();
				issue.setIssueID(rs.getInt(1));
				issue.setName(rs.getString(2));
				issue.setDescription(rs.getString(3));
				issue.setPriority(rs.getString(4));
				issue.setOverallStatus(rs.getString(5));
				issue.setDateIdentified(rs.getDate(6));
				issue.setDateClosed(rs.getDate(7));
				issue.setShowStopper(rs.getString(8));
				issue.setResolution(rs.getString(9));
				issue.setResolved(rs.getString(10));
				issue.setCreatedBy(rs.getInt(11));
				issue.setCurrentUser(rs.getInt(12));
			}
			
			conn1 = DbConnection.getConnection();
			stmt1 = conn1.prepareStatement(createdByQuery);
			int	createdById = issue.getCreatedBy();
			stmt1.setInt(1, createdById);			
									
			ResultSet rs1 = stmt1.executeQuery();
			while(rs1.next()) {
				issue.setCreatedByName(rs1.getString(1) + " " + rs1.getString(2));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		finally 
		{
			DbConnection.closePrepConnection(conn, stmt);
			DbConnection.closePrepConnection(conn1, stmt1);
		}	
		return issue;
	}

	private String updateIssue(HttpServletRequest request) throws Exception {
		
		if(request.getParameter("issueId") == null || "".equals(request.getParameter("issueId")))
		{
			createIssue(request);
		}
		else
		{
			HttpSession session = request.getSession();
			UserData userData = (UserData) session.getAttribute("userData");
			
			String params = request.getParameter("fieldData");
			
			JSONObject dataJSON = new JSONObject(params);
			
			int currentUser = dataJSON.getInt("currentUser");
			String description = dataJSON.getString("description");
			String name = dataJSON.getString("name");
			String overallStatus = "Open";
			String priority = dataJSON.getString("priority");
			String resolution = dataJSON.has("resolution") ? dataJSON.getString("resolution") : "";
			String resloved = dataJSON.has("resolution") ? dataJSON.getString("resolved") : "";
			String showStopper = dataJSON.getString("showStopper");
			
			if("Y".equals(resloved))
			{
				overallStatus = "Closed";
			}
			
			PreparedStatement stmt = null;
			Connection conn = null;
			String query = null;
			
			java.util.Date utilDate = new java.util.Date();
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			
			try
			{
				query = "update issues set Name=?, Description=?, Priority=?, OverallStatus=?, DateClosed=?, "
						+ "ShowStopper=?, "
						+ ("tester".equals(userData.getRole()) ? "" : "Resolution=?, Resolved=?, ")
						+ "CurrentUser=? where issueID=?";
				
				conn = DbConnection.getConnection();
				stmt = conn.prepareStatement(query);

				int i = 1;
				int	issueId = Integer.parseInt(request.getParameter("issueId"));
				stmt.setString(i++, name);
				stmt.setString(i++, description);
				stmt.setString(i++, priority);
				stmt.setString(i++, overallStatus);
				stmt.setDate(i++, sqlDate);
				stmt.setString(i++, showStopper);
				if(!"tester".equals(userData.getRole()))
				{
					stmt.setString(i++, resolution);
					stmt.setString(i++, resloved);
				}
				stmt.setInt(i++, currentUser);
				//userData.getFirstName() + " " + userData.getLastName()
				stmt.setInt(i++, issueId);
			
				ResultSet rs = stmt.executeQuery();
				
				System.out.println("issue updated");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DbConnection.closePrepConnection(conn, stmt);
			}
		}
		return new JSONObject().put("Success", true).toString();
	}

	private String createIssue(HttpServletRequest request) throws Exception {
		String params = request.getParameter("fieldData");
		JSONObject dataObj = new JSONObject(params);
		
		String name = dataObj.getString("name");
		String description = dataObj.getString("description");
		String priority = dataObj.getString("priority");
		String overallStatus = "Open";
		String showStopper = dataObj.getString("showStopper");
				
		PreparedStatement stmt = null;
		Connection conn = null;
		PreparedStatement st = null;
		Connection connect = null;
		
		String query = null;
		int max = 0;
		int maxSeqNo = 0;
		
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		
		try{
			query = "select max(issueID), max(sequenceno) from issues";
			
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				max = rs.getInt(1) + 1;
				maxSeqNo = rs.getInt(2) + 1;
			}
			HttpSession session = request.getSession();
			UserData userData = (UserData) session.getAttribute("userData");
			
			query = "insert into issues (name, priority, description, issueID, dateIdentified, overallStatus, createdBy, currentUser, "
					+ "showstopper, resolution, resolved, sequenceno)"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			connect = DbConnection.getConnection();
			st = connect.prepareStatement(query);
			st.setString(1, name);
			st.setString(2, priority);
			st.setString(3, description);
			st.setInt(4, max);
			st.setDate(5,sqlDate);
			st.setString(6, overallStatus);
			st.setInt(7, userData.getUserID());
			st.setInt(8, userData.getUserID());
			st.setString(9, showStopper);
			st.setString(10, " ");
			st.setString(11, "N");
			st.setInt(12, maxSeqNo);
			
			st.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbConnection.closePrepConnection(conn, stmt);
			DbConnection.closePrepConnection(connect, st);
		}
		return new JSONObject().put("Success", true).toString();
	}

	private void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	private String getLoggedInUserData(HttpServletRequest request) throws JSONException  {
		
		HttpSession session=request.getSession();
		UserData userData = (UserData) session.getAttribute("userData");
		JSONObject respData = new JSONObject();
		respData.put("userName", userData.getFirstName() + " " + userData.getLastName());
		respData.put("userRole", userData.getRole());
		
		return respData.toString();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public String login(HttpServletRequest request) throws Exception
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		UserData user = null;
		
		try
		{
			String sql = "select LoginID, FirstName, LastName, Role, UserID from userdata where LoginID= ? and Password = ?";
			
			String loginid = request.getParameter("login");
			String pass = request.getParameter("pass");
			
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, loginid);
			stmt.setString(2, pass);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				user = new UserData();
				user.setLoginID(rs.getString(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setRole(rs.getString(4));
				user.setUserID(rs.getInt(5));
			}
			HttpSession session=request.getSession();
			if(user != null){
				session.setAttribute("userData",user);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			DbConnection.closePrepConnection(conn, stmt);
		}
		if(user != null){
			System.out.println(user.toJSON());
			return user.toJSON();
		}
		return "";
	}
	
	public String getJsonArrayFromIssueArrayList(ArrayList<IssuesData> issuesList) throws JSONException{
		JSONArray jsonArray = new JSONArray();
		for(int i = 0; i < issuesList.size(); i++){
			jsonArray.put(issuesList.get(i).toJSON());
		}
		return jsonArray.toString();
	}
	
	public String getAllIssues(HttpServletRequest request, String sql) throws Exception
	{
		PreparedStatement stmt = null;
		Connection conn = null;
		IssuesData issue = null;
		ArrayList<IssuesData> issueList = new ArrayList<IssuesData>();
		String query = null;
			
		try
		{
			if(sql == null ){
				query = "select IssueID, Name, Description, Priority, OverallStatus, DateIdentified, DateClosed,"
					+ " ShowStopper, Resolution, Resolved, CreatedBy from issues";
			}
			else{
				query = sql;
			}
							
			HttpSession session = request.getSession();
			UserData userData = (UserData) session.getAttribute("userData");
			
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(query);
			if(sql != null){
				stmt.setInt(1, userData.getUserID());
			}
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				
				issue = new IssuesData();
				issue.setIssueID(rs.getInt(1));
				issue.setName(rs.getString(2));
				issue.setDescription(rs.getString(3));
				issue.setPriority(rs.getString(4));
				issue.setOverallStatus(rs.getString(5));
				issue.setDateIdentified(rs.getDate(6));
				issue.setDateClosed(rs.getDate(7));
				issue.setShowStopper(rs.getString(8));
				issue.setResolution(rs.getString(9));
				issue.setResolved(rs.getString(10));
				issue.setCreatedBy(rs.getInt(11));
				
				issueList.add(issue);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		finally
		{
			DbConnection.closePrepConnection(conn, stmt);
		}
		return getJsonArrayFromIssueArrayList(issueList);
	}
	public String getIssuesForLoggedInUser(HttpServletRequest request) throws Exception
	{
		String sql = "select IssueID, Name, Description, Priority, OverallStatus, DateIdentified, DateClosed,"
				+ " ShowStopper, Resolution, Resolved, CreatedBy from issues where CurrentUser=?";
		
		return getAllIssues(request, sql);
	}
	public void getAllUsers() throws Exception
	{
		Connection conn = null;
		String sql = null;
		PreparedStatement stmt = null;
		try
		{
			System.out.println("In get All users");
			sql = "select LoginId, FirstName, LastName, Role, CreationDate from userdata where LoginID=? and Password=?";
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
				System.out.print(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getString(4));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			DbConnection.closePrepConnection(conn, stmt);
		}
	}
 
	public String updateUser(HttpServletRequest request) throws Exception
	{
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String role = request.getParameter("role");
		String loginId = request.getParameter("loginId");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		UserData user = null;
		
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		try
		{
			String sql = "update userdata set FirstName=? and LastName=? and Role=? and CreationDate=? where LoginId=? ";
			
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, role);
			stmt.setDate(4, sqlDate);
			stmt.setString(5, loginId);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				user = new UserData();
				user.setFirstName(rs.getString(1));
				user.setLastName(rs.getString(2));
				user.setRole(rs.getString(3));
				user.setCreationDate(rs.getDate(4));
				user.setLoginID(rs.getString(5));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			DbConnection.closePrepConnection(conn, stmt);
		}
		return user.toString();
	}
}