/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpncalculator;

/**
 *
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class Calculation {
	private String result;
	private String operation;
	private String date;
	private String info;
	private String source;

	public Calculation(String result, String operation, 
			String date, String info, String source) {
		this.result = result;
		this.operation = operation;
		this.date = date;
		this.info = info;
		this.source = source;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		info = (info.length() > 0) ?  " Info: "+ info : "";
		return operation + " = " + result + info;
	}
	
}
