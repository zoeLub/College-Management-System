package model.students;

/**
 * @author Zoe Lubanza
 */

public class Student {
	private int studentNum;
	private String surname;

	public Student(int studentNum, String surname)
	{
		this.studentNum = studentNum;
		this.surname = surname;
	}

	/**
	 *
	 * @return
	 */
	public int getStudentNum() {
		return studentNum;
	}

	/**
	 *
	 * @param studentNum
	 */
	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
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

}
