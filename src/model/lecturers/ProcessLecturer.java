package model.lecturers;

import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.database.DatabaseHandler;
import model.interfaces.Utilities;

/**
 * @author Zoe Lubanza
 */

public class ProcessLecturer implements Utilities{
	private String path;
	private DatabaseHandler dbHandler = DatabaseHandler.getInstance();
	
	/**
	 * 
	 * @param list
	 */
    public void uploadLecturersToDatabase(List<Lecturer> list) {
        list.stream().parallel()
                .forEach(this::storeLecturersIntoDb);
    }
    
    /**
     * 
     * @param lecturer
     */
    private void storeLecturersIntoDb(Lecturer lecturer) {
    	dbHandler.insertLecturer("INSERT or IGNORE INTO Lecturer values (?,?,?,?,?)",lecturer);
    }

	/**
	 *
	 * @return
	 */
	public List<Lecturer> storeLecturerIntoList()
	{
		return Utilities.getFileLines(Paths.get(path)).stream()
				.map(Utilities.tokenize(","))
				.map(createLecturer())
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @return
	 */
	private Function<String[],Lecturer> createLecturer()
	{
		return stringArr -> new Lecturer(Integer.parseInt(stringArr[0]),stringArr[1],"","","");
	}

	/**
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
