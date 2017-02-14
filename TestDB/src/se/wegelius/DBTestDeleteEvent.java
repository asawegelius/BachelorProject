package se.wegelius;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DBTestDeleteEvent {
	public static Connection conn;
	public static Date dd;
	public static String ds;

	public static void main(String[] args) {
		dd = new Date();
		ds = dateToMySQL(dd);
		System.out.println("Check DB Driver: " + CheckDBDriver());
		System.out.println("Get DB Connection: " + GetDBConnection());
		System.out.println("Delete Event: " + DeleteEventDB(1));
		System.out.println("Close DB Connection: " + CloseDBConnection());
	}

	private static boolean DeleteEventDB(int eventId) {
		boolean result = false;
		try {
			Statement st = (Statement) conn.createStatement();
			st.executeUpdate("DELETE FROM events WHERE event_id=" + eventId);
			result = true;
		} catch (Exception e) {
			System.err.println(e);
		}
		return result;
	}

	private static boolean CloseDBConnection() {
		boolean result = false;
		try {
			conn.close();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static boolean GetDBConnection() {
		boolean result = false;
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://188.181.85.75/speedvoterdb", "voter_admin",
					"voter_admin");
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean CheckDBDriver() {
		boolean result = false;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			result = true;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String dateToMySQL(Date d) {
		return String.format("%tY-%<tm-%<td", d);
	}
}
