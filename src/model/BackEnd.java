package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashMap;

public class BackEnd {
	final String URL = "jdbc:mysql://127.0.0.1/insertSimulator";
	final String user = "root";
	final String password = "";  // can't expose my password, sorry
	private Connection connection;
	private boolean eventAdded;
	private HashMap<String, Integer> eventsNameToId = new HashMap<String, Integer>();

	public BackEnd(){
		try{
			connection = DriverManager.getConnection(URL, user, password);
			this.eventAdded=false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean getEventAdded() {
		return this.eventAdded;
	}

	public int getIdbyEvent(String eventName) {
		if(!this.eventsNameToId.containsKey(eventName)) {
			try {
				Statement myStmt = connection.createStatement();
				int newID = this.eventsNameToId.size()+1;
				String insert = "insert into MainTable(ID, Name)" +
						"values ("+newID+", '"+eventName+"')";
				myStmt.executeUpdate(insert);
				this.eventAdded=true;
				this.eventsNameToId.put(eventName, newID);
				return this.eventsNameToId.get(eventName);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.eventAdded=false;
		return this.eventsNameToId.get(eventName);
	}

	public void insert(String selectedTable, String eventName, String eventDate, String eventSurname) {
		try {
			Statement myStmt = connection.createStatement();
			int eventID = getIdbyEvent(eventName);
			String insert=null;

			switch(selectedTable) {
			case "SubTable0":
				insert = "insert into SubTable0(EventID, EvDate)" +
						"values (" + eventID + ", DATE '" + eventDate + "')";
				break;
			case "SubTable1":
				insert = "INSERT into SubTable1(EventID, Surname)" +
						"values (" + eventID + ", '" + eventSurname + "')";
				break;
			case "SubTable2":
				insert = "insert into SubTable2(EventID)" +
						"values (" + eventID + ")";
				break;
			}
			if (insert==null) throw new Exception("please select the required information");
			myStmt.executeUpdate(insert);
		}
		catch (SQLIntegrityConstraintViolationException e) {
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String Query(String selectedTable) {
		StringBuilder result = new StringBuilder();
		try{
			Statement myStmt = connection.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from " + selectedTable);
			switch(selectedTable) {
			case "SubTable0":
				result.append("SubTable0 \n");
				while(myRs.next()) {
					result.append("EventID: " + myRs.getString("EventID") + "\t EvDate: " + myRs.getString("EvDate") + "\n");
				}
				break;
			case "SubTable1":
				result.append("SubTable1 \n");
				while(myRs.next()) {
					result.append("EventID: " + myRs.getString("EventID") + "\t Surname: " + myRs.getString("Surname") + "\n");
				}
				break;
			case "SubTable2":
				result.append("SubTable2 \n");
				while(myRs.next()) {
					result.append("EventID: " + myRs.getString("EventID") + "\n");
				}
				break;
			case "MainTable":
				result.append("MainTable \n");
				while(myRs.next()) {
					result.append("ID: " + myRs.getString("ID") + "\t Name: " + myRs.getString("Name") + "\n");
				}
				break;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result.toString();
	}
}
