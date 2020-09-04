package model.subjects;

import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.database.DatabaseHandler;
import model.interfaces.Utilities;

/**
 * @author Zoe Lubanza
 */

public class ProcessSubject implements Utilities{
	private String path;
	private DatabaseHandler dbHandler = DatabaseHandler.getInstance();
	
	/**
	 * 
	 * @param list
	 */
    public void uploadSubjectToDatabase(List<Subject> list) {
        list.stream().parallel()
                .forEach(this::storeSubjectIntoDb);
    }
    
    /**
     * 
     * @param subject
     */
    private void storeSubjectIntoDb(Subject subject) {
    	dbHandler.insertSubject("INSERT or IGNORE INTO Subject values (?,?,?,?)",subject);
    }

	/**
	 *
	 * @return
	 */
	public List<Subject> storeSubjectIntoList()
	{
		return Utilities.getFileLines(Paths.get(path)).stream()
				.map(Utilities.tokenize(";"))
				.map(createSubject())
				.collect(Collectors.toList());
	}

	/**
	 *
	 * @return
	 */
	private Function<String[],Subject> createSubject()
	{
		return stringArr -> new Subject(stringArr[0],stringArr[1],Integer.parseInt(stringArr[2]),Double.parseDouble(stringArr[3]));
	}

	public void setPath(String path) {
		this.path = path;
	}


}
