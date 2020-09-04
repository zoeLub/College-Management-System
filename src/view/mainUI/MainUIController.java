package view.mainUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import model.user.Users;
import model.database.DatabaseHandler;
import model.interfaces.Utilities;

/**
 * @author Zoe Lubanza
 */

public class MainUIController implements Utilities, Initializable{

	@FXML AnchorPane mainPane;
	@FXML private Button loginBtn;
	@FXML private Button signUpBtn;
	@FXML private TextField userNameField;
	@FXML private PasswordField passworField;
	private Login login;

	/**
	 *
	 * @param evt
	 */
	@FXML public void loginBtnHandler(ActionEvent evt)
	{
		this.logUserIn();
	}

	/**
	 *
	 * @param evt
	 */
	@FXML public void signUpHandler(ActionEvent evt)
	{
		displaySignUpPage();
	}

	/**
	 *
	 */
	private void initializeLogin(Users user)
	{
		login = new Login(user);
	}

	/**
	 *
	 */
    private void logUserIn() {

    	Users user = new Users(userNameField.getText(),passworField.getText(),"");
    	this.initializeLogin(user);

        if (user.getUserStatus().equalsIgnoreCase("admin"))
        	this.displayAdminPage();
        else if (user.getUserStatus().equalsIgnoreCase("user")) {
        	login.getLecturerAndsetStatusActive();
        	login.updateLecturerInDb();
        	this.displayLecturerPage();
        }
        else {
        	Utilities.showError("Invalid credentials.\nTry again or Signup");
        	clearTextFields();
        }
    }

    /**
     *
     */
    private void clearTextFields()
    {
    	userNameField.setText("");
    	passworField.setText("");
    }

	/**
	 *
	 */
	private void displaySignUpPage()
	{
		this.closeStage(mainPane);
		loadFxml("/view/singupUI/SignupUI.fxml", false);
	}

	/**
	 *
	 */
	private void displayAdminPage()
	{
		this.closeStage(mainPane);
		loadFxml("/view/adminUI/AdminUI.fxml", false);
	}

	/**
	 *
	 */
	private void displayLecturerPage()
	{
		this.closeStage(mainPane);
		loadFxml("/view/lecturerUI/LecturerUI.fxml", true);
	}

	/**
	 *
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		new DatabaseHandler();
	}

}
