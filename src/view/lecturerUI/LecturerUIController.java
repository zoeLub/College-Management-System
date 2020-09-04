package view.lecturerUI;

import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import model.database.DatabaseHandler;
import model.interfaces.Utilities;
import model.lecturers.Lecturer;
import model.marks.ProcessMarks;
import model.result.Result;

/**
 * @author Zoe Lubanza
 */

public class LecturerUIController implements Utilities,Initializable{
	@FXML private AnchorPane root;
	@FXML private AnchorPane resultPane;
	@FXML private Label user;
	@FXML private Button logoutBtn;
	@FXML private Button uploadMarks;
	@FXML private Button viewMarks;
	@FXML private Button showPassrate;
	@FXML private Button showResult;
	@FXML private PieChart pieChart;
	@FXML private LineChart<String,Number> lineChart;
	@FXML private CategoryAxis xAxis;
	@FXML private NumberAxis yAxis;
	@FXML private TableView<Result> resultTableView;
	@FXML private TableColumn<Result, Integer> studNum;
	@FXML private TableColumn<Result, String> studSurname;
	@FXML private TableColumn<Result, String> subjCode;
	@FXML private TableColumn<Result, Double> mark;
	@FXML private TableColumn<Result, Double> credit;
	@FXML ComboBox<String> comboBox;
	private ObservableList<Result> finalResultList =  FXCollections.observableArrayList();
	private ObservableList<String> subjectList =  FXCollections.observableArrayList();
	private List<Double> listOfMark = new ArrayList<>();
	private FileChooser fileChooser;
	private File file = null;;
	private Lecturer lecturer;
	private ProcessMarks processMark = new ProcessMarks();
	private DatabaseHandler dbHandler = DatabaseHandler.getInstance();
	private double distinction, pass, fail;
	
	/**
	 * 
	 * @param e
	 */
	@FXML public void logoutHandler(ActionEvent e)
	{
		displayMainPage();
	}
	
	/**
	 * Action when upload marks is clicked
	 * @param e
	 */
	@FXML public void uploadMarksHandler(ActionEvent e)
	{
		fileChooser = this.defaultDirectory(fileChooser, "marks  Lecturer "+lecturer.getStaffNumber()+".csv");
		
		file = fileChooser.showOpenDialog(null);
		
		if(file != null) {
			processMark.setPath(file.getPath());
			processMark.uploadMarksToDatabase(processMark.storeMarkIntoSet());
			if(finalResultList.size()<1) this.getResultFromDbAndAddToList();				
		}
	}
	
	/**
	 * 
	 * @param e
	 */
	@FXML public void viewMarksHandler(ActionEvent e)
	{
		populateMarkTable();
		displayTable();
	}
	
	/**
	 * 
	 * @param e
	 */
	@FXML public void showPassRateHandler(ActionEvent e)
	{
		listOfMark.clear();
		this.getMarkFromDbAndAddToList();
		this.processPassRate();
		
		if(listOfMark.size()<1) Utilities.showError("Please upload the marks first");
		else {
			this.clearChart();
			this.fillPieChart();
			this.displayPieChart();
		}
	}
	
	/**
	 * 
	 * @param e
	 */
	@FXML public void showResultHandler(ActionEvent e)
	{
		if(finalResultList.size()<1) Utilities.showError("Please upload the marks first");
		else {
			clearChart();
			fillLineChart();
			displayLineChart();
		}
	}
	
	/**
	 * fill the table with the results
	 */
	private void populateMarkTable()
	{
		studNum.setCellValueFactory(new PropertyValueFactory<>("studNum"));
		studSurname.setCellValueFactory(new PropertyValueFactory<>("studSurname"));
		subjCode.setCellValueFactory(new PropertyValueFactory<>("subjCode"));
		mark.setCellValueFactory(new PropertyValueFactory<>("mark"));
		credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
		
		if(finalResultList.size()>1) resultTableView.setItems(finalResultList);
		else Utilities.showError("Please upload the marks first");
	}
	
	/**
	 * 
	 */
	private void getResultFromDbAndAddToList()
	{
		ResultSet resultSet = dbHandler.SelectEntity("SELECT std.Sudent_num, std.stdSurname, sub.Sbjt_code, m.mark, sub.credit "
				+ "FROM Subject AS sub "
				+ "INNER JOIN Mark AS m "
				+ "ON sub.Sbjt_code = m.Sbjt_code "
				+ "INNER JOIN Student AS std "
				+ "ON m.Sudent_num = std.Sudent_num "
				+ "WHERE Staff_num = '" +lecturer.getStaffNumber()+ "';");
        try {
			while(resultSet.next()) {
				finalResultList.add(new Result(resultSet.getInt(1),resultSet.getString(2),
						resultSet.getString(3),resultSet.getDouble(4),resultSet.getDouble(5)));				
			}
		} catch (SQLException e) {Utilities.showError("Could not get Result from the database.\n"+e);}
	}
	
	/**
	 * 
	 */
	private void getSubjectCodeFromDbAndAddToList()
	{
		ResultSet resultSet = dbHandler.SelectEntity("SELECT Sbjt_code "
				+ "FROM Subject "
				+ "WHERE Staff_num = '" +lecturer.getStaffNumber()+ "';");
		try {
			while(resultSet.next())	subjectList.add(resultSet.getString(1));
			this.populateComboBox(subjectList);			
		} catch (SQLException e) {Utilities.showError("Could not get Subject code from the database.\n"+e);}
	}
	
	/**
	 * 
	 * @param list
	 */
	private void populateComboBox(ObservableList<String> list)
	{
		comboBox.getItems().addAll(list);
		comboBox.getSelectionModel().select(0);
	}
		
	/**
	 * 
	 */
	private void getMarkFromDbAndAddToList()
	{
        ResultSet resultSet = dbHandler.SelectEntity("SELECT * FROM Mark "
        		+ "WHERE Sbjt_code = '"+comboBox.getSelectionModel().getSelectedItem()+"';");
        try {
			while(resultSet.next()) listOfMark.add(resultSet.getDouble(3));
		} catch (SQLException e) {Utilities.showError("Could not get marks from database.\n"+e);}
	}
	
	/**
	 * 
	 */
	private void fillPieChart()
	{
		PieChart.Data slice1 = new PieChart.Data("Distinction", distinction);
		PieChart.Data slice2 = new PieChart.Data("Pass", pass);
		PieChart.Data slice3 = new PieChart.Data("Fail" , fail);
		
		pieChart.getData().add(slice1);
		pieChart.getData().add(slice2);
		pieChart.getData().add(slice3);
		pieChart.setTitle("Student Pass Rate ("+comboBox.getSelectionModel().getSelectedItem()+")");
	}
	
	/**
	 * 
	 */
	private void fillLineChart()
	{
		xAxis.setLabel("Student Name");
		yAxis.setLabel("Mark");
		xAxis.setStyle("-fx-font-weight: bold; -fx-font-size:14px;");
		yAxis.setStyle("-fx-font-weight: bold; -fx-font-size:14px;");
		
		yAxis.setAutoRanging(false);
	    yAxis.setLowerBound(0);
	    yAxis.setUpperBound(100);
	    yAxis.setTickUnit(25);
		
		XYChart.Series<String,Number> dataSeries = new Series<String,Number>();
		dataSeries.setName(comboBox.getSelectionModel().getSelectedItem());
		
		finalResultList.stream()
		.filter(result -> result.getSubjCode().equals(comboBox.getSelectionModel().getSelectedItem()))
		.forEach(result ->  dataSeries.getData().add(new XYChart.Data<String,Number>(result.getStudSurname(), result.getMark())));

		lineChart.getData().add(dataSeries);		
	}
	
	/**
	 * 
	 */
	private void clearChart()
	{
		lineChart.getData().clear();
        pieChart.getData().clear();
	}
	
	/**
	 * 
	 */
	private void displayPieChart()
	{
		pieChart.setVisible(true);
		resultTableView.setVisible(false);
		lineChart.setVisible(false);
	}
	
	/**
	 * 
	 */
	private void displayTable()
	{
		resultTableView.setVisible(true);
		pieChart.setVisible(false);
		lineChart.setVisible(false);
	}
	
	/**
	 * 
	 */
	private void displayLineChart()
	{
		lineChart.setVisible(true);
		pieChart.setVisible(false);
		resultTableView.setVisible(false);
	}
	
	/**
	 * This method computes the pass rate of the students
	 */
	private void processPassRate()
	{
		this.resetCounters();
		
		listOfMark.stream()
		.filter(this::isDistinction)
		.forEach(mark -> this.distinction++);
		
		listOfMark.stream()
		.filter(this::isPass)
		.forEach(mark -> this.pass++);
		
		listOfMark.stream()
		.filter(this::isFail)
		.forEach(mark -> this.fail++);
	}
	
	/**
	 * 
	 */
	private void resetCounters()
	{
		distinction = 0;
		pass = 0;
		fail = 0;
	}
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	private boolean isDistinction(double number)
	{
		return number>=75;
	}
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	private boolean isFail(double number)
	{
		return number<50;
	}
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	private boolean isPass(double number)
	{
		return number>=50 && number<75;
	}
	
	/**
	 * get which lecturer is currently logged in
	 * in order to display informations related to him only
	 */
    private void getActiveLecturer(){
    	
        ResultSet resultSet = dbHandler.SelectEntity("SELECT * FROM Lecturer WHERE status = '"+"active" + "'");
        try {
			while(resultSet.next()) lecturer = new Lecturer(resultSet.getInt(1),resultSet.getString(2),
						resultSet.getString(3),resultSet.getString(4),resultSet.getString(5));
		} catch (SQLException e) {Utilities.showError("Could not get Lecturer info from the database.\n"+e);}
    }
	
	/**
	 * Close current stage and display the main page
	 */
	private void displayMainPage()
	{
		this.closeStage(root);
		this.loadFxml("/view/mainUI/Main.fxml", false);
	}
	
	/**
	 * 
	 */
	private void setUserDetails()
	{
		user.setText("User: "+lecturer.getSurname()+"("+lecturer.getStaffNumber()+")");
	}

	/**
	 * 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.getActiveLecturer();
		this.setUserDetails();
		this.getResultFromDbAndAddToList();
		this.getSubjectCodeFromDbAndAddToList();
	}
	
}
