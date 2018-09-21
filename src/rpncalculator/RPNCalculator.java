/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpncalculator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class RPNCalculator {
	private static RPNCalculator firstInstance = null;
	OperatorsHelper operatorsHelper = null;
	CalculationsHistory calculationsHistory = null;
	String input = "stdin";

	public CalculationsHistory getCalculationsHistory() {
		return calculationsHistory;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	private RPNCalculator() {
		operatorsHelper = new OperatorsHelper();
		calculationsHistory = new CalculationsHistory();
		calculationsHistory.loadHistory();
	}

	public Calculation parseLine(String line){
		String []tokens = line.split("\\s");
		Stack<BigDecimal> numbers = new Stack<>();
		LocalDateTime ldt = LocalDateTime.now();
		String result = "";
		String info = "";
		for (String token : tokens){
			if (token.length() > 0){
				try {
					BigDecimal n = new BigDecimal(token);
					numbers.push(n);
				} catch (NumberFormatException e) {
					Operator op = operatorsHelper.getOperator(token);
					if (op != null){
						try {
							BigDecimal a = numbers.pop();
							BigDecimal b = numbers.pop();
							try {
								BigDecimal r = op.calculate(a, b);
								numbers.push(r);
								result = numbers.peek().toString();
							} catch (ArithmeticException ex) {
								result = "Error";
								info = ex.getMessage();
								break;
							}
						} catch (EmptyStackException emptyStackException ) {
							result = "Error";
							info = "Not enough operands.";
							break;
						}
						
					} else {
						result = "Error";
						info = "Invalid expresion";
						break;
					}
				}
			
			}
		}

		Calculation c = new Calculation(result, line, ldt.toString(), info, this.getInput());
		calculationsHistory.log(c);
		return c;
	}

	public static RPNCalculator getInstance(){
		if (firstInstance == null){
			firstInstance = new RPNCalculator();
		}
		return  firstInstance;
	}

	public void processFile (String fileName){
		this.setInput(fileName);
		try {
			FileInputStream fis = new FileInputStream(fileName);
			Scanner sc = new Scanner(fis);
			while(sc.hasNextLine()){
				System.out.println(this.parseLine(sc.nextLine()));
			}
		} catch (FileNotFoundException ex) {
			System.err.println("Could not find file: "  + fileName);
		}
	}
	
	public static void main(String[] args) {
		RPNCalculator rpnc = new RPNCalculator();
		String line = "1 2 1 3 2 1 PLUS TIMES PLUS DIV PLUS PAPAS";
		System.out.println(rpnc.parseLine(line));
		line = "1 1 3 2 1 PLUS TIMES PLUS DIV ";
		System.out.println(rpnc.parseLine(line));
		line = "1 1 3 2 1 + * + /";
		System.out.println(rpnc.parseLine(line));
		line = "1 1 1 + PLUS PLUS";
		System.out.println(rpnc.parseLine(line));
	}
	
}
