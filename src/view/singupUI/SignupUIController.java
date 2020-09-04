package view.singupUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import model.interfaces.Utilities;
import model.lecturers.Lecturer;
import model.user.Users;

/**
 * @author Zoe Lubanza
 */


public class SignupUIController implements Utilities{
	
	@FXML AnchorPane mainPane;
	@FXML Button backToMain;
	@FXML Button singUpBtn;
	@FXML TextField staffIdTextField;
	@FXML TextField usernameTextField;
	@FXML PasswordField passwordField;
	@FXML Label notificationLabel;
		
	
	/**
	 * 
	 * @param e
	 */
	@FXML public void backTomainHandler(ActionEvent e)
	{
		displayMainPage();
	}
	
	/**
	 * 
	 * @param e
	 */
	@FXML public void singUpBtnHandler(ActionEvent e)
	{
		this.SignLecturerUp();
	}
	
	/**
	 * 
	 */
	private void displayMainPage()
	{
		this.closeStage(mainPane);
		this.loadFxml("/view/mainUI/Main.fxml", false);
	}

	/**
	 * 
	 */
	private void SignLecturerUp()
	{
		String username = usernameTextField.getText();
		String password = passwordField.getText();
		String staffID = staffIdTextField.getText();
		
		try {
			Lecturer lecturer = new Lecturer(Integer.parseInt(staffID),"",username,password,"");
			Users user = new Users(username,password,"user");
			new SignUp(lecturer,user,notificationLabel);
		}catch(NumberFormatException e) {Utilities.showError("Invalid input");}
			
		
	}
}
