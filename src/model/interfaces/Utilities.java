package model.interfaces;

import static java.nio.file.Files.readAllLines;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author Zoe Lubanza
 */

public interface Utilities {

	/**
	 * 
	 * @param fileChooser
	 * @param fileName
	 * @return
	 */
	default FileChooser defaultDirectory(FileChooser fileChooser, String fileName) 
	{
		fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(Paths.get(".").toAbsolutePath().toFile());
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("File", fileName));
		return fileChooser;
	}

	/**
	 * Displays an error message to the user
	 * @param msg message
	 */
	public static void showError(String msg)
	{
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	/**
	 * this method closes a specific stage
	 * @param node parent node
	 */
	default void closeStage(Node node)
	{
		((Stage)node.getScene().getWindow()).close();
	}

	/**
	 * given a string, this function splits the string by the delimiter
	 * @param delimiter
	 * @return array of string
	 */
	static Function<String,String[]> tokenize(String delimiter)
	{
		return string -> string.split(delimiter);
	}

	/**
	 *
	 * @param path
	 * @return
	 */
    static List<String> getFileLines(Path path)  {
        List<String> list = new ArrayList<>();
        try {
            list  = readAllLines(path);
        } catch (IOException e) {
            showError("Could not read the file");
        }
        return  list;
    }

    /**
     *
     * @param fxml file relative path
     * @param resizable choose whether to resize a stage or not
     */
	default void loadFxml(String fxml, boolean resizable)
	{
		Parent root;
		Stage stage = new Stage();
		try {
			root = FXMLLoader.load(getClass().getResource(fxml));
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("CoMaSys");
	        stage.setResizable(resizable);
	        stage.show();
		} catch(Exception e) {showError("FXML loading failure");}
	}

}