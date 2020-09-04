package model.marks;

import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.database.DatabaseHandler;
import model.interfaces.Utilities;

/**
 * @author Zoe Lubanza
 */

public class ProcessMarks implements Utilities{
	private String path;
	private DatabaseHandler dbHandler = DatabaseHandler.getInstance();
	
	/**
	 * 
	 * @param Set
	 */
    public void uploadMarksToDatabase(Set<Mark> Set) {
        Set.stream().parallel()
                .forEach(this::storeMarksIntoDb);
    }
    
    /**
     * 
     * @param lecturer
     */
    private void storeMarksIntoDb(Mark mark) {
    	dbHandler.insertMark("INSERT or IGNORE INTO Mark values (?,?,?)",mark);
    }

	/**
	 *
	 * @return
	 */
	public Set<Mark> storeMarkIntoSet()
	{
		return Utilities.getFileLines(Paths.get(path)).stream()
				.map(Utilities.tokenize(";"))
				.map(createMark())
				.collect(Collectors.toSet());
	}

	/**
	 *
	 * @return
	 */
	private Function<String[],Mark> createMark()
	{
		return stringArr -> new Mark(Integer.parseInt(stringArr[0]),stringArr[1],Double.parseDouble(stringArr[2]));
	}

	/**
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}
}