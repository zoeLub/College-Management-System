package model.result;

/**
 * @author Zoe Lubanza
 */

public class Result {
	private int studNum;
	private String studSurname;
	private String subjCode;
	private double mark;
	private double credit;
	
	public Result(int studNum, String studSurname, String subjCode, double mark, double credit)
	{
		this.studNum = studNum;
		this.studSurname = studSurname;
		this.subjCode = subjCode;
		this.mark = mark;
		this.credit = credit;
	}

	/**
	 * 
	 * @return
	 */
	public int getStudNum() {
		return studNum;
	}

	/**
	 * 
	 * @return
	 */
	public String getStudSurname() {
		return studSurname;
	}

	/**
	 * 
	 * @return
	 */
	public String getSubjCode() {
		return subjCode;
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
	 * @return
	 */
	public double getCredit() {
		return credit;
	}

}
