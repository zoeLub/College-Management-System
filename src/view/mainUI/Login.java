package view.mainUI;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.database.DatabaseHandler;
import model.interfaces.Utilities;
import model.lecturers.Lecturer;
import model.user.Users;

/**
 * @author Zoe Lubanza
 */

public class Login implements Utilities{
	
	private Users user;
	private Lecturer lecturer;
	private DatabaseHandler dbHandler = DatabaseHandler.getInstance();
	
	/**
	 * 
	 * @param user
	 */
	public Login(Users user)
	{
		this.user = user;
		this.lecturer = new Lecturer(0,"","","","");
		getUserCredentials();
	}
	
	/**
	 * 
	 */
    private void getUserCredentials() {
    	
        ResultSet resultSet = dbHandler.SelectEntity("SELECT * FROM Users WHERE userName = '" + user.getUsername() + "' AND user_pwd = '" +
                user.getUserPassword() + "'");
        try {
			while(resultSet.next()) {
				user.setUsername(resultSet.getString(1));
				user.setUserPassword(resultSet.getString(2));
				user.setUserStatus(resultSet.getString(3));
			}
		} catch (SQLException e) {Utilities.showError("Could not create User.\n"+e);}
    }
    
    /**
     * 
     */
    public void getLecturerAndsetStatusActive()
    {
    	ResultSet resultSet = dbHandler.SelectEntity("SELECT * FROM Lecturer WHERE LecUsername = '" + user.getUsername() + "' AND LecPassword = '" +
                user.getUserPassword() + "'");
    	
        try {
    			while(resultSet.next()) {
    				lecturer.setStaffNumber(Integer.parseInt(resultSet.getString(1)));
    				lecturer.setSurname(resultSet.getString(2));
    				lecturer.setUsername(resultSet.getString(3));
    				lecturer.setPassword(resultSet.getString(4));
    				lecturer.setStatus("active");
    			}
    		} catch (SQLException e) {Utilities.showError("Could not create lecturer.\n"+e);}
    }
    
    /**
     * 
     * @param lecturer
     */
    public void updateLecturerInDb() {
    	dbHandler.updateLecturer("UPDATE Lecturer SET Staff_num = ?," 
		 		+"LecSurname = ?," 
		 		+"LecUsername = ?," 
		 		+"LecPassword = ?,"
		 		+"status = ?"
		 		+"WHERE Staff_num = ?" , lecturer);
    }

}