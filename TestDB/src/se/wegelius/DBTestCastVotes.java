package se.wegelius;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DBTestCastVotes {
	public static Connection conn;
	public static Date dd;
	public static String ds;

	public static void main(String[] args) {
		dd = new Date();
		ds = dateToMySQL(dd);
		System.out.println("Check DB Driver: " + CheckDBDriver());
		System.out.println("Get DB Connection: " + GetDBConnection());
		System.out.println("Add Voter: " + AddVoterDB("Test000000000000",1));
		System.out.println("Add Voter: " + AddVoterDB("Test000000000001",1));
		System.out.println("Add Voter: " + AddVoterDB("Test000000000002",1));
		System.out.println("Add Voter: " + AddVoterDB("Test000000000003",1));
		System.out.println("Add Voter: " + AddVoterDB("Test000000000004",1));
		System.out.println("Cast Vote: " + CastVoteDB("Test000000000000",1,1));
		System.out.println("Cast Vote: " + CastVoteDB("Test000000000001",1,2));
		System.out.println("Cast Vote: " + CastVoteDB("Test000000000002",1,1));
		System.out.println("Cast Vote: " + CastVoteDB("Test000000000003",1,2));
		System.out.println("Cast Vote: " + CastVoteDB("Test000000000004",1,1));
		System.out.println("Close DB Connection: " + CloseDBConnection());
	}

	private static boolean AddVoterDB(String pwd, int eventId) {
		boolean result = false;
		try {
			Statement st = (Statement) conn.createStatement();
			st.executeUpdate("INSERT INTO passwords (password,fk_event_id,active) VALUE ('"
					+ pwd + "'," + eventId + ",1)");
			result = true;
		} catch (Exception e) {
			System.err.println(e);
		}
		return result;
	}

	private static boolean CastVoteDB(String pwd, int optionId, int electionId) {
		boolean result = false;
		try {
			Statement st = (Statement) conn.createStatement();
			st.executeUpdate("INSERT INTO votes (password_fk,option_id_fk,election_id_fk) VALUE ('"
					+ pwd + "'," + optionId + "," + electionId + ")");
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
