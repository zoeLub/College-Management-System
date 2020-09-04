package view.adminUI;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import model.interfaces.Utilities;
import model.lecturers.ProcessLecturer;
import model.students.ProcessStudent;
import model.subjects.ProcessSubject;

/**
 * @author Zoe Lubanza
 */

public class AdminUIController implements Utilities{
	@FXML AnchorPane mainPane;
	@FXML Button backTomain;
	@FXML Button uploadStudent;
	@FXML Button uploadLecturers;
	@FXML Button uploadSubjects;
	private FileChooser fileChooser;
	private File file = null;
	private ProcessStudent processStudent = new ProcessStudent();
	private ProcessSubject processSubject = new ProcessSubject();
	private ProcessLecturer processlecturer = new ProcessLecturer();

	@FXML public void backTomainHandler(ActionEvent e)
	{
		displayMainPage();
	}

	/**
	 * 
	 * @param e
	 */
	@FXML public void uploadStudents(ActionEvent e)
	{
		fileChooser = this.defaultDirectory(fileChooser, "students.csv");
		
		file = fileChooser.showOpenDialog(null);
		
		if(file != null) {
			try{
				processStudent.setPath(file.getPath());
				processStudent.uploadStudentsToDatabase(processStudent.storeStudentIntoSet());
			}catch(Exception ex){}
		}
	}
	
	/**
	 * 
	 * @param e
	 */
	@FXML public void uploadLecturers(ActionEvent e)
	{
		fileChooser = this.defaultDirectory(fileChooser, "lecturers.csv");
		
		file = fileChooser.showOpenDialog(null);
		
		if(file != null) {
			try{
				processlecturer.setPath(file.getPath());
				processlecturer.uploadLecturersToDatabase(processlecturer.storeLecturerIntoList());	
			}catch(Exception ex){}
		}
	}
	
	/**
	 * 
	 * @param e
	 */
	@FXML public void uploadSubjects(ActionEvent e)
	{
		fileChooser = this.defaultDirectory(fileChooser, "subject.csv");
		
		file = fileChooser.showOpenDialog(null);
		
		if(file != null) {
			try{
				processSubject.setPath(file.getPath());
				processSubject.uploadSubjectToDatabase(processSubject.storeSubjectIntoList());	
			}catch(Exception ex){}
		}
	}

	/**
	 *
	 */
	private void displayMainPage()
	{
		this.closeStage(mainPane);
		this.loadFxml("/view/mainUI/Main.fxml", false);
	}

}
