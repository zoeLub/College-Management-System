package view.singupUI;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import model.database.DatabaseHandler;
import model.interfaces.Utilities;
import model.lecturers.Lecturer;
import model.user.Users;

/**
 * @author Zoe Lubanza
 */

public class SignUp implements Utilities{
	
	private Lecturer lecturer;
	private Users user;
	private int count;
	private DatabaseHandler dbHandler = DatabaseHandler.getInstance();
	
	/**
	 * 
	 * @param user
	 */
	public SignUp(Lecturer lecturer, Users user, Label label)
	{
		this.lecturer = lecturer;
		this.user = user;
		pullLecturerIDandDecideAction(label);
	}
	
	/**
	 * 
	 * @param user
	 */
	private void addUserToDb(Users user)
	{
		dbHandler.insertUser(user);
	}
	
	/**
	 * 
	 */
	private void updateLecturerTable()
	{
		dbHandler.updateLecturer("UPDATE Lecturer SET Staff_num = ?," 
		 		+"LecSurname = ?," 
		 		+"LecUsername = ?," 
		 		+"LecPassword = ?,"
		 		+"status = ?"
		 		+ "WHERE Staff_num = ?" , lecturer);
	}
	
	/**
	 * 
	 */
    private void pullLecturerIDandDecideAction(Label label) {
    	
    	ResultSet resultSet = dbHandler.SelectEntity("SELECT * FROM Lecturer WHERE Staff_num = '" + lecturer.getStaffNumber() + "'");
    	
        try {        	
        	int staffID = 0;
        	
        	while(resultSet.next()) {
        		staffID = Integer.parseInt(resultSet.getString(1));
        		lecturer.setStaffNumber(staffID);
        		lecturer.setSurname(resultSet.getString(2));
			}
        	
			if(canSignUp(staffID)) {				
				this.addUserToDb(user);
				lecturer.setUsername(user.getUsername());
        		lecturer.setPassword(user.getUserPassword());
				this.updateLecturerTable();
				this.timedNotification(label,"Sign Up Successful !");
			}
			else this.timedNotification(label,"Staff number not found !");
		} catch (SQLException e) {Utilities.showError("Could not get the staff Number from the database.\n"+e);}        
    }
    
    /**
     * 
     * @param staffID
     * @return
     */
    private boolean canSignUp(int staffID)
    {
    	return lecturer.getStaffNumber() == staffID;
    }
    
    /**
     * This method will display the successful signUp notification for 5 Seconds
     * @param label
     */
    public void timedNotification(Label label, String message)
    {
    	label.setVisible(true);
    	label.setText(message);
        count = 0;
        Timeline timeline = new Timeline();

        if (timeline != null) timeline.stop();

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(1), event -> {
                    count++;
                    if (count == 5){
                        count = 0;
                        timeline.stop();
                        label.setVisible(false);
                    }

                }));
        timeline.playFromStart();
    }
    
}
