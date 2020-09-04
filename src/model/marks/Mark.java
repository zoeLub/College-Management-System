package model.marks;

/**
 * @author Zoe Lubanza
 */

public class Mark {
	private int stdNumber;
	private String subjectCode;
	private double mark;
	private Mark instance;

	/**
	 *
	 * @param studNumber
	 * @param subjectCode
	 * @param mark
	 */
	public Mark(int studNumber, String subjectCode, double mark)
	{
		this.stdNumber = studNumber;
		this.subjectCode = subjectCode;
		this.mark = mark;
	}

	/**
	 *
	 * @return
	 */
	public int getStdNumber() {
		return stdNumber;
	}

	/**
	 *
	 * @param stdNumber
	 */
	public void setStdNumber(int stdNumber) {
		this.stdNumber = stdNumber;
	}

	/**
	 *
	 * @return
	 */
	public String getSubjectCode() {
		return subjectCode;
	}

	/**
	 *
	 * @param subjectCode
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	/**
	 *
	 * @return
	 */
	public double getMark() {
		return mark;
	}

	/**
	 *
	 * @param mark
	 */
	public void setMark(double mark) {
		this.mark = mark;
	}

	public Mark getInstance() {
		return instance;
	}
	
	

}
