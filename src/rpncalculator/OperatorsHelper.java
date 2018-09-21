/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpncalculator;

/**
 * Operators Helper class.
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class OperatorsHelper {
	/**
	 * Addition instance.
	 */
	Operator addition = new Addition();
	/**
	 * Subtraction instance.
	 */
	Operator substraction = new Subtraction();
	/**
	 * Multiplication instance.
	 */
	Operator multiplication = new Multiplication();
	/**
	 * Division instance.
	 */
	Operator division = new Division();

	/**
	 * Get the instance for the String operator.
	 * @param operator Oprerator string token.
	 * @return  The operator instance.
	 */
	public Operator getOperator(String operator){
		Operator op = null;
		switch (operator) {
			case "+":
			case "PLUS":
				op = addition;
				break;
			case "-":
			case "LESS":
				op = substraction;
				break;
			case "*":
			case "x":
			case "TIMES":
				op = multiplication;
				break;
			case "/":
			case "DIV":
				op = division;
				break;
			default:
				System.err.println("Invalid expresion: " + operator);
		}
		return op;
	}
}
