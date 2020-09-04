package model.students;

import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.database.DatabaseHandler;
import model.interfaces.Utilities;

/**
 * @author Zoe Lubanza
 */
public class ProcessStudent implements Utilities{
	private String path;
	private DatabaseHandler dbHandler = DatabaseHandler.getInstance();
	
	/**
	 * 
	 * @param Set
	 */
    public void uploadStudentsToDatabase(Set<Student> Set) {
        Set.stream().parallel()
                .forEach(this::storeStudentsIntoDb);
    }
    
    /**
     * 
     * @param student
     */
    private void storeStudentsIntoDb(Student student) {
    	dbHandler.insertStudent("INSERT or IGNORE INTO Student values (?,?)",student);
    }

	/**
	 *
	 * @return
	 */
	public Set<Student> storeStudentIntoSet()
	{
		return Utilities.getFileLines(Paths.get(path)).stream()
				.map(Utilities.tokenize(";"))
				.map(createStudent())
				.collect(Collectors.toSet());
	}

	/**
	 *
	 * @return
	 */
	private Function<String[],Student> createStudent()
	{
		return stringArr -> new Student(Integer.parseInt(stringArr[0]),stringArr[1]);
	}

	public void setPath(String path) {
		this.path = path;
	}

}
