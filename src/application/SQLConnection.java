package application;
import java.sql.*;

public class SQLConnection {
	Connection connection;
	Statement query;
	String host;
	String username;
	String password;
	
	public SQLConnection(String db_host, String db_username, String db_password, String database) {
		this.host = db_host;
		this.username = db_username;
		this.password = db_password;
		String url = "jdbc:mysql://" + this.host + "/" + database + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		try {
			this.connection = DriverManager.getConnection(url, this.username, this.password);
			this.query = connection.createStatement();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public ResultSet executeQuery(String q) {
		try {
			if (! this.connection.isClosed()) {
				return query.executeQuery(q);
			}
			else {
				System.out.println("ERROR: Connection is already closed!");
				return null;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public int executeUpdate(String q) {
		try {
			if (! this.connection.isClosed()) {
				return query.executeUpdate(q);
			}
			else {
				System.out.println("ERROR: Connection is already closed!");
				return -1;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	public int createTable(String name, String vars) {
		if (name.length() > 0 && vars.length() > 0) {
			String q = "CREATE TABLE IF NOT EXISTS " + name + " (id INT(255) NOT NULL AUTO_INCREMENT, ";
			q = q + vars;
			q = q + ", PRIMARY KEY(id))";
			try {
				return this.query.executeUpdate(q);
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return -1;
			}
		}
		else {
			return -1;
		}
	}
	public int deleteTable(String name) {
		try {
			return this.query.executeUpdate("DROP TABLE " + name + ";");
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	public int updateTable(String name, String set, String where) {
		try {
			String q = "UPDATE " + name + " SET " + set + " WHERE " + where + ";";
			return this.query.executeUpdate(q);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	public int insertInto(String name, String names, String values) {
		try {
			String q = "INSERT INTO " + name + " (" + names + ")" + " VALUES (" + values + ");";
			return this.query.executeUpdate(q);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	public void close() {
		try {
			this.connection.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
