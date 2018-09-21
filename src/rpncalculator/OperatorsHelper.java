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
public class OperatorsHelper {
	Operator addition = new Addition();
	Operator substraction = new Substraction();
	Operator multiplication = new Multiplication();
	Operator division = new Division();

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
