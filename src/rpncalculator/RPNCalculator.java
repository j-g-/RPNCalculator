package rpncalculator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Reverse Polish Notation calculator implementation.
 * Singleton class.
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class RPNCalculator {
	/**
	 * The only instance of RPNCalculator to be created.
	 */
	private static RPNCalculator firstInstance = null;
	/**
	 * Operators Helper instance.
	 */
	OperatorsHelper operatorsHelper = null;
	/**
	 * The history handler instance.
	 */
	CalculationsHistory calculationsHistory = null;
	/**
	 * The source of the RPN expressions.
	 */
	String input = "stdin";

	/**
	 * Get the CalculationsHistory instance.
	 * @return The CalculationsHistory instance.
	 */
	public CalculationsHistory getCalculationsHistory() {
		return calculationsHistory;
	}

	/**
	 * Get the source of the RPN expressions.
	 * @return  The source of the RPN expressions.
	 */
	public String getInput() {
		return input;
	}

	/**
	 * Source of the RPN expressions.
	 * @param input  The source of the RPN expressions.
	 */
	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * Private Constructor.
	 */
	private RPNCalculator() {
		operatorsHelper = new OperatorsHelper();
		calculationsHistory = new CalculationsHistory();
		calculationsHistory.loadHistory();
	}

	/**
	 * Parsers and performs calculation for a line.
	 * @param line Lie to parse
	 * @return The Calculation results.
	 */
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

	/**
	 * Returns the only instance of RPNCalculator.
	 * @return  The RPNCalculator instance used for this program.
	 */
	public static RPNCalculator getInstance(){
		if (firstInstance == null){
			firstInstance = new RPNCalculator();
		}
		return  firstInstance;
	}

	/**
	 * Process a file of RPN expresions.
	 * @param fileName  The file to process.
	 */
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
}
