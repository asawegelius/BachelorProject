package se.wegelius;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DBTestAddEvent {
	public static Connection conn;
	public static Date dd;
	public static String ds;

	public static void main(String[] args) {
		dd = new Date();
		ds = dateToMySQL(dd);
		System.out.println("Check DB Driver: " + CheckDBDriver());
		System.out.println("Get DB Connection: " + GetDBConnection());
		System.out.println("Add Event: " + AddEventDB(1,"TestEvent"));
		System.out.println("Add Election: " + addElectionDB("Formand",1,1,1,1));
		System.out.println("Add Option: "+addOptionDB("Anna", 1,1));
		System.out.println("Add Option: "+addOptionDB("Bettina", 1,2));
		System.out.println("Add Election: " + addElectionDB("Næst Formand",1,2,1,1));
		System.out.println("Add Option: "+addOptionDB("Christina", 2,3));
		System.out.println("Add Option: "+addOptionDB("Dorthe", 2,4));
		System.out.println("Add Election: " + addElectionDB("Bestyrelse",1,3,2,3));
		System.out.println("Add Option: "+addOptionDB("Elise", 3,5));
		System.out.println("Add Option: "+addOptionDB("Fiona", 3,6));
		System.out.println("Add Option: "+addOptionDB("Gunnel", 3,7));
		System.out.println("Add Option: "+addOptionDB("Helle", 3,8));
		System.out.println("Add Election: " + addElectionDB("Bestyrelses Suppleanter",1,4,3,4));
		System.out.println("Add Option: "+addOptionDB("Inger", 4,9));
		System.out.println("Add Option: "+addOptionDB("Jytte", 4,10));
		System.out.println("Add Option: "+addOptionDB("Karina", 4,11));
		System.out.println("Add Option: "+addOptionDB("Lotte", 4,12));
		System.out.println("Close DB Connection: " + CloseDBConnection());
	}

	public static boolean addOptionDB(String s, int ElectionId,int OptionNumber) {
		boolean result = false;		
		try {
			Statement st = (Statement) conn.createStatement();
			st.executeUpdate("INSERT INTO options (option_id,the_option,election_id) VALUE ("+
					OptionNumber+",'" + s + "'," + ElectionId + ")");
			result = true;
		} catch (Exception e) {
			System.err.println(e);
		}
		return result;
	}

	public static boolean addElectionDB(String s, int EventId,int ElectionId,int minVotes, int MaxVotes) {
		boolean result = false;
		try {
			Statement st = (Statement) conn.createStatement();
			st.executeUpdate("INSERT INTO elections (election_id,post,date,event_id,min_votes,max_votes) VALUE ("+
					ElectionId +",'" + s + "','" + ds + "'," + EventId + ","+minVotes+","+MaxVotes+")");
			result = true;
		} catch (Exception e) {
			System.err.println(e);
		}
		return result;
	}
	
	
	private static boolean AddEventDB(int eventId,String EventName) {
		boolean result = false;
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://188.181.85.75/speedvoterdb", "voter_admin",
					"voter_admin");
			Statement st = (Statement) conn.createStatement();
			st.executeUpdate("INSERT INTO events (event_id,event_name,start_date,end_date) VALUE ("
					+ eventId + ",'"+EventName + "','" + ds + "','" + ds + "')");
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
