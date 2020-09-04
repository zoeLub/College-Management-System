package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.interfaces.Utilities;
import model.lecturers.Lecturer;
import model.marks.Mark;
import model.students.Student;
import model.subjects.Subject;
import model.user.Users;

/**
 * @author Zoe Lubanza
 */

public class DatabaseHandler implements Utilities{
	//instance variables
	private final static String URL = "jdbc:sqlite:CollegeSystem.sqlite";
	private static DatabaseHandler dbHandler = null;

	/**
	 * Constructor
	 */
	public DatabaseHandler() {
		connection();
		createLecturerTable();
		createStudentTable();
		createSubjectTable();
		createMarkTable();
		createUsersTable();
		defaultUser();
		updateLecturerStatus("inactive");
	}

	/**
	 * get an instance of DatabaseHandler
	 * @return
	 */
	public static DatabaseHandler getInstance()
	{
		return dbHandler == null? dbHandler=new DatabaseHandler():dbHandler;
	}

	/**
	 * Establish the connection to the dB
	 */
     private static void connection() {
    	 try(Connection connection = DriverManager.getConnection(URL)){}
    	 catch (SQLException e) { Utilities.showError("SQL Connection Failure");}
	 }

     /**
      *add the admin into the database
      */
     private void defaultUser()
     {
    	 this.insertUser(new Users("Zoe","123z","admin"));
     }

     /**
      *
      * @return
      */
     private Connection connect()
     {
	     Connection connetion = null;
	     try {connetion = DriverManager.getConnection(URL);}
	     catch (SQLException e) {Utilities.showError(e.getMessage());}

	     return connetion;
	  }

     /**
      * Create the Lecturer table
      */
	 private static void createLecturerTable()
	 {
	     String query = "CREATE TABLE IF NOT EXISTS Lecturer(\n"
	             + "	Staff_num LONG PRIMARY KEY NOT NULL,\n"
	             + "	LecSurname VARCHAR NOT NULL,\n"
	             + "	LecUsername VARCHAR,\n"
	             + "	LecPassword VARCHAR,\n"
	             + "	status VARCHAR\n"
	             + ")";

	     try (Connection connection = DriverManager.getConnection(URL);Statement statement = connection.createStatement()) {
	    	 statement.execute(query);
	     } catch (SQLException e) { Utilities.showError("Could not create Lecturer table\n"+e); }
	 }

     /**
      * Create the Student table
      */
	 private static void createStudentTable()
	 {
	     String query = "CREATE TABLE IF NOT EXISTS Student(\n"
	             + "	Sudent_num LONG PRIMARY KEY NOT NULL,\n"
	             + "	stdSurname VARCHAR\n"
	             + ")";

	     try (Connection connection = DriverManager.getConnection(URL);Statement statement = connection.createStatement()) {
	    	 statement.execute(query);
	     } catch (SQLException e) { Utilities.showError("Could not create Student table\n"+e); }
	 }

     /**
      * Create the Subject table
      */
	 private static void createSubjectTable()
	 {
	     String query = "CREATE TABLE IF NOT EXISTS Subject(\n"
	             + "	Sbjt_code VARCHAR(10) PRIMARY KEY NOT NULL,\n"
	             + "	Sbjt_title VARCHAR NOT NULL,\n"
	             + "	Staff_num LONG NOT NULL,\n"
	             + "	credit DOUBLE NOT NULL\n"
	             + ")";

	     try (Connection connection = DriverManager.getConnection(URL);Statement statement = connection.createStatement()) {
	    	 statement.execute(query);
	     } catch (SQLException e) { Utilities.showError("Could not create Subject table\n"+e); }
	 }

     /**
      * Create the Mark table
      */
	 private static void createMarkTable()
	 {
	     String query = "CREATE TABLE IF NOT EXISTS Mark(\n"
	    		 + "	Sudent_num LONG NOT NULL,\n"
	             + "	Sbjt_code VARCHAR(10) NOT NULL,\n"
	             + "	mark DOUBLE NOT NULL\n"
	             + ")";

	     try (Connection connection = DriverManager.getConnection(URL);Statement statement = connection.createStatement()) {
	    	 statement.execute(query);
	     } catch (SQLException e) { Utilities.showError("Could not create Mark table\n"+e); }
	 }

     /**
      * Create the Users table
      */
	 private static void createUsersTable()
	 {
	     String query = "CREATE TABLE IF NOT EXISTS Users(\n"
	    		 + "	userName VARCHAR PRIMARY KEY NOT NULL,\n"
	             + "	user_pwd VARCHAR NOT NULL,\n"
	             + "	userStatus VARCHAR(5) NOT NULL\n"
	             + ")";

	     try (Connection connection = DriverManager.getConnection(URL);Statement statement = connection.createStatement()) {
	    	 statement.execute(query);
	     } catch (SQLException e) { Utilities.showError("Could not create Users table\n"+e); }
	 }

	 /**
	  * Insert a new row into the users table
	  * @param user
	  */
	 public void insertUser(Users user) {
	     String sql = "INSERT OR IGNORE INTO Users(userName,user_pwd,userStatus) VALUES(?,?,?)";
	     try (Connection conn = this.connect();
	          PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, user.getUsername());
	            pstmt.setString(2, user.getUserPassword());
	            pstmt.setString(3, user.getUserStatus());
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	 }
	 
	 /**
	  * Insert a row into Student table
	  * @param query
	  * @param student
	  */
	 public void insertStudent(String query, Student student) {
	 	try {
	 		PreparedStatement statement = this.connect().prepareStatement(query);
	 		statement.setLong(1, student.getStudentNum());
	 		statement.setString(2, student.getSurname());
	 		statement.executeUpdate();
	 	} catch (SQLException e) {
	 		Utilities.showError("Could not insert Student.\n"+e.getMessage());
	 	}
	 }
	 
	 /**
	  * Insert a row into lecturer table
	  * @param query
	  * @param lecturer
	  */
	 public void insertLecturer(String query, Lecturer lecturer) {
		 	try {
		 		PreparedStatement statement = this.connect().prepareStatement(query);
		 		statement.setLong(1, lecturer.getStaffNumber());
		 		statement.setString(2, lecturer.getSurname());
		 		statement.setString(3, lecturer.getUsername());
		 		statement.setString(4, lecturer.getPassword());
		 		statement.setString(5, lecturer.getStatus());
		 		statement.executeUpdate();
		 	} catch (SQLException e) {
		 		Utilities.showError("Could not insert Lecturers.\n"+e.getMessage());
		 	}
		 }
	 
	 /**
	  * Insert a row into Subject table
	  * @param query
	  * @param subject
	  */
	 public void insertSubject(String query, Subject subject) {
		 	try {
		 		PreparedStatement statement = this.connect().prepareStatement(query);
		 		statement.setString(1, subject.getCode());
		 		statement.setString(2, subject.getTitle());
		 		statement.setLong(3, subject.getLecturerNum());
		 		statement.setDouble(4, subject.getCredit());
		 		statement.executeUpdate();
		 	} catch (SQLException e) {
		 		Utilities.showError("Could not insert Subject.\n"+e.getMessage());
		 	}
		 }

	 /**
	  * Insert a row into Mark table
	  * @param query
	  * @param mark
	  */
	 public void insertMark(String query, Mark mark)
	 {
		 try {
		 		PreparedStatement statement = this.connect().prepareStatement(query);
		 		statement.setInt(1, mark.getStdNumber());
		 		statement.setString(2, mark.getSubjectCode());
		 		statement.setDouble(3, mark.getMark());
		 		statement.executeUpdate();
		 	} catch (SQLException e) {
		 		Utilities.showError("Could not insert Marks.\n"+e.getMessage());
		 	}
	 }
	 
	 /**
	  * update Lecturer table
	  * @param lecturer
	  */
	 public void updateLecturer(String query, Lecturer lecturer)
	 {
		 try {
		 		PreparedStatement statement = this.connect().prepareStatement(query);
		 		statement.setLong(1, lecturer.getStaffNumber());
		 		statement.setString(2, lecturer.getSurname());
		 		statement.setString(3, lecturer.getUsername());
		 		statement.setString(4, lecturer.getPassword());
		 		statement.setString(5, lecturer.getStatus());
		 		statement.setLong(6, lecturer.getStaffNumber());
		 		statement.executeUpdate();
		 	} catch (SQLException e) {
		 		Utilities.showError("Could not update Lecturers.\n"+e.getMessage());
		 	}
	 }
	 
	 /**
	  * 
	  * @param status
	  */
	 private void updateLecturerStatus(String status)
	 {
		 String query = "UPDATE Lecturer SET status = ?" ;
		 try {
		 		PreparedStatement statement = this.connect().prepareStatement(query);
		 		statement.setString(1, status);
		 		statement.executeUpdate();
		 	} catch (SQLException e) {
		 		Utilities.showError("Could not update status.\n"+e.getMessage());
		 	}
	 }
	 
	 /**
	  *
	  * @param query
	  * @return
	  */
	 public ResultSet SelectEntity(String query) {
	        ResultSet result;
	        try {
	        	PreparedStatement statement = this.connect().prepareStatement(query);
	            result = statement.executeQuery();
	        } catch (SQLException ex) {
				Utilities.showError("Select Query Failure");
	            return null;
	        }
	        return result;
	 }

}