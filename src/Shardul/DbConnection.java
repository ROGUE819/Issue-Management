package Shardul;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException
	{
		try
		{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","shardul","welcome1");
		
		return connection;
	    }
		finally {
			
		}
	}
	
	public static ResultSet executeQuery(String query) throws Exception
	{
		Connection conn = DbConnection.getConnection();
		Statement stmt = conn.createStatement();
		String sql = query;
		ResultSet resultSet = stmt.executeQuery(sql);
		
		return resultSet;
	}
	
	public static void closeConnection(Connection conn, Statement stmt) throws Exception
	{
		conn.close();
		stmt.close();
	}
	
	public static void closePrepConnection(Connection conn, PreparedStatement stmt) throws Exception
	{
		conn.close();
		stmt.close();
	}
}
