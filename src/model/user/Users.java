package model.user;

/**
 * @author Zoe Lubanza
 */

public class Users {

	private String username;
	private String userPassword;
	private String userStatus;

	/**
	 *
	 * @param username
	 * @param password
	 * @param userStatus
	 */
	public Users(String username, String password, String userStatus)
	{
		this.username = username;
		this.userPassword = password;
		this.userStatus = userStatus;
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
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 *
	 * @param userPassword
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 *
	 * @return
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 *
	 * @param userStatus
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

}