package application;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Datenbank {
	public static final String db_host = "87.98.243.201";
	public static final String db_name = "samp6612_battleshipgame";
	public static final String db_user = "samp6612";
	public static final String db_password = "B4n4n3*123";
	public static final SQLConnection mysqli = new SQLConnection(db_host, db_user, db_password, db_name);
	
	public static LoginStatus LoginUser(String Username, String Password)
	{
		System.out.print("Login: [" + Username + "] [");
		for (int i = 0; i < Password.length(); i++) {
			if (i > 4) {
				System.out.print("*");
			}
			else {
				System.out.print(Password.charAt(i));
			}
		}
		System.out.print("]" + "\n");
		
		
		ResultSet login = mysqli.executeQuery("SELECT name, passwort, online FROM spieler WHERE name=\"" + Username.toLowerCase() + "\" LIMIT 1");
		
		if (login != null) {
			try {
				while (login.next()) {
					String Password_DB = login.getString("passwort");
					boolean online = login.getBoolean("online");
					if (Password.equals(Password_DB) && ! online) 
					{
						int success = mysqli.updateTable("spieler", "online=1", "name=\"" + Username.toLowerCase() + "\"");
						if (success > 0) {
							System.out.println("SUCCEED");
							login.close();
							return LoginStatus.SUCCEED;
						}
						else {
							System.out.println("FAILED 1");
							login.close();
							return LoginStatus.FAILED;
						}
					}
					else {
						System.out.println("FAILED 2");
						login.close();
						return LoginStatus.FAILED;
					}
				}
				System.out.println("FAILED 3");
				login.close();
				return LoginStatus.FAILED;
			}
			catch (Exception ex) {
				System.out.println("UNKNOWN");
				return LoginStatus.UNKNOWN;
			}
		}
		else {
			return LoginStatus.UNKNOWN;
		}
	}
	public static RegisterStatus RegisterUser(String Username, String Password)
	{
		if(UsernameExists(Username)) return RegisterStatus.FAILED_NAME_DUPLICATE;
		if(Password.length() <= 4) return RegisterStatus.FAILED_PASSWORD_UNSAFE;
		
		int register = mysqli.insertInto("spieler", "name, passwort, online", "\"" + Username + "\", \"" + Password + "\", 0");
		
		if (register > 0) {
			return RegisterStatus.SUCCEED;
		}
		else {
			return RegisterStatus.FAILED_UNKNOWN_REASON;
		}
	}
	public static ObservableList<Spiel> loadUnfinishedGames(int spielerid)
	{
		ResultSet unfinishedGames = mysqli.executeQuery("SELECT * FROM spiel WHERE spieler1id = " + spielerid + " OR spieler2id = " + spielerid);
		ObservableList<Spiel> lList = FXCollections.observableArrayList();
		try 
		{
			while(unfinishedGames.next())
			{
				lList.add(new Spiel(unfinishedGames.getInt("id"), unfinishedGames.getInt("spieler1id"), unfinishedGames.getInt("spieler2id"), unfinishedGames.getInt("winner")));
			}
			unfinishedGames.close();
			for(Spiel s : lList)
			{
				if(s.getSpieler1() != null) s.setSpieler1(Datenbank.loadUser(s.getSpieler1().getId()));
				if(s.getSpieler2() != null) s.setSpieler2(Datenbank.loadUser(s.getSpieler2().getId()));
			}
			return lList;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Fehler in: loadunfinishedGames(int spielerid) (" + spielerid + ")");
			return null;
		}
	}
	public static boolean UsernameExists(String Username)
	{
		ResultSet check = mysqli.executeQuery("SELECT name FROM spieler WHERE name=\"" + Username.toLowerCase() + "\"");
		
		if (check != null) {
			try {
				while (check.next()) {
					check.close();
					return true;
				}
				return false;
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
		else {
			return false;
		}
	}
	public static int UsersOnline() {
		try {
			ResultSet result = mysqli.executeQuery("SELECT count(id) as count FROM spieler WHERE online=true");
			
			while (result.next()) {
				int online = result.getInt(1);
				result.close();
				return online;
			}
			result.close();
			return -1;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	public static Boolean UserOnline(String Username) {
		try {
			ResultSet check = mysqli.executeQuery("SELECT online FROM spieler WHERE name=\"" + Username + "\"");
			
			while (check.next()) {
				boolean isonline = check.getBoolean("online");
				check.close();
				return isonline;
			}
			check.close();
			return false;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	public static List<Spieler> loadAllUsers() throws SQLException
	{
		ResultSet check = mysqli.executeQuery("SELECT * FROM spieler");
		List<Spieler> lList = new ArrayList<Spieler>();
		while(check.next())
		{
			lList.add(new Spieler(check.getInt("id"), check.getString("name"), check.getInt("spiele"), check.getInt("gewonnen"), check.getInt("verloren"), check.getBoolean("online")));
		}
		check.close();
		return lList;
	}
	public static Spieler loadUser(String Username) {
		try {
			ResultSet check = mysqli.executeQuery("SELECT * FROM spieler WHERE name=\"" + Username + "\"");
			
			if(check.next())
			{
				Spieler lSpieler = new Spieler(check.getInt("id"), check.getString("name"), check.getInt("spiele"), check.getInt("gewonnen"), check.getInt("verloren"), check.getBoolean("online"));	
				check.close();
				return lSpieler;
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}
	public static Spieler loadUser(int id) {
		try {
			ResultSet check = mysqli.executeQuery("SELECT * FROM spieler WHERE id = " + id);
			
			if(check.next())
			{
				Spieler lSpieler = new Spieler(check.getInt("id"), check.getString("name"), check.getInt("spiele"), check.getInt("gewonnen"), check.getInt("verloren"), check.getBoolean("online"));	
				check.close();
				return lSpieler;
		
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}
	public static boolean setOffline(String Username)
	{
		System.out.println("Datenbank.setOffline(" + Username + ")");
		int success = mysqli.updateTable("spieler", "online=0", "name=\"" + Username.toLowerCase() + "\"");
		if (success > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	public static List<Spielzug> loadGame(int spielid)
	{
		ResultSet check = mysqli.executeQuery("SELECT * FROM spielzug WHERE spielid = " + spielid);
		List<Spielzug> lList = new ArrayList<Spielzug>();
		try {
			while(check.next())
			{
				try {
					lList.add(new Spielzug(check.getInt("zugid"), check.getInt("spielid"), check.getInt("spielerid"), check.getInt("x"), check.getInt("y"), check.getInt("art")));
				} catch (SQLException e) {
					System.out.println("Fehler\n\"loadGame(int spielid)\" (1)");
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println("Fehler\n\"loadGame(int spielid)\" (1)");
			e.printStackTrace();
		}
		return lList;
	}
	public static Spiel loadSpiel(int spielid)
	{
		ResultSet check = mysqli.executeQuery("SELECT * FROM spiel WHERE id = " + spielid);
		
		try {
			while(check.next())
			{
				try {
					return new Spiel(check.getInt("id"), check.getInt("spieler1id"), check.getInt("spieler2id"), check.getInt("winner"));
				} catch (SQLException e) {
					System.out.println("Fehler\n\"loadSpiel(int spielid)\" (1)");
					e.printStackTrace();
					return null;
				}
			}
		} catch (SQLException e) {
			System.out.println("Fehler\n\"loadSpiel(int spielid)\" (1)");
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
