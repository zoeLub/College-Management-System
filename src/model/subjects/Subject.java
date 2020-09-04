package model.subjects;

/**
 * 01 August 2020
 *
 * @author Zoe Lubanza
 */

public class Subject {
	private String code;
	private String title;
	private int lecturerNum;
	private double credit;

	public Subject(String code, String title, int lecturerNum, double credit)
	{
		this.code = code;
		this.title = title;
		this.lecturerNum = lecturerNum;
		this.credit = credit;
	}

	/**
	 *
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 *
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 *
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 *
	 * @return
	 */
	public int getLecturerNum() {
		return lecturerNum;
	}

	/**
	 *
	 * @param lecturerNum
	 */
	public void setLecturerNum(int lecturerNum) {
		this.lecturerNum = lecturerNum;
	}

	/**
	 *
	 * @return
	 */
	public double getCredit() {
		return credit;
	}

	/**
	 *
	 * @param credit
	 */
	public void setCredit(double credit) {
		this.credit = credit;
	}



}
