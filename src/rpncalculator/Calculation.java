/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpncalculator;

/**
 * Holds the information about a calculation.
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class Calculation {
	/**
	 * The result of the calculation.
	 */
	private String result;
	/**
	 * The RPN expression.
	 */
	private String operation;
	/**
	 * The Date it was calculated.
	 */
	private String date;
	/**
	 * Info about errors;
	 */
	private String info;
	/**
	 * Source of expression.
	 */
	private String source;

	/**
	 * Simple Constructor.
	 * @param result The result.
	 * @param operation The RPN expression.
	 * @param date The date of calculation.
	 * @param info The info about errors.
	 * @param source  The source of the expression.
	 */
	public Calculation(String result, String operation, 
			String date, String info, String source) {
		this.result = result;
		this.operation = operation;
		this.date = date;
		this.info = info;
		this.source = source;
	}

	/**
	 * Get the result of this operation.
	 * @return The result.
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Set the result of the operation.
	 * @param result  The result to set.
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Get the RPN expression.
	 * @return The RPN expression.
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * Set the RPN expression.
	 * @param operation  The RPN expression to set.
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * Get the date.
	 * @return THe date it was calculated.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Set the date.
	 * @param date The date of calculation to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Get the info about errors.
	 * @return The info about errors.
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * Set the info about errors.
	 * @param info The info to set about errors.
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * Get the source of the RPN expression.
	 * @return The source of the RPN expression.
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Set the source of the RPN expression.
	 * @param source  The source of the RPN expression.
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Transform to a String.
	 * @return 
	 */
	@Override
	public String toString() {
		info = (info.length() > 0) ?  " Info: "+ info : "";
		return operation + " = " + result + info;
	}
	
}
