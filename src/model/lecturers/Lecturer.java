package model.lecturers;

/**
 * @author Zoe Lubanza
 */

public class Lecturer {
	private int staffNumber;
	private String surname;
	private String username;
	private String password;
	private String status;

	/**
	 * Default Constructor
	 * @param staffNum
	 * @param sName
	 */
	public Lecturer(int staffNum, String sName, String username, String password, String status)
	{
		this.staffNumber = staffNum;
		this.surname = sName;
		this.username = username;
		this.password = password;
		this.status = status;
	}

	/**
	 *
	 * @return
	 */
	public int getStaffNumber() {
		return staffNumber;
	}

	/**
	 *
	 * @param staffNumber
	 */
	public void setStaffNumber(int staffNumber) {
		this.staffNumber = staffNumber;
	}

	/**
	 *
	 * @return
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 *
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
