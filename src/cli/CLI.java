/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cli;

import java.util.Scanner;
import rpncalculator.RPNCalculator;

/**
 *
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class CLI {
	static RPNCalculator rpnc  = RPNCalculator.getInstance();
	private static void interactiveMode() {
		String prompt = ">>> ";
		Scanner sc = new Scanner(System.in);
	    for(;;){
			rpnc.setInput("stdin");
			System.out.print(prompt);
			String line = sc.nextLine();
			switch (line) {
				case "help":
					printHelp();
					break;
				case "exit":
				case "quit":
					System.exit(0);
					break;
				case "history":
					rpnc.getCalculationsHistory().printOrderedByDate();
					break;
				default:
					System.out.println(rpnc.parseLine(line));
			}
		}
	}

	private static void printHelp() {
		String[][] commands = {
			{"help", "Prints this help."},
			{"quit/exit", "Exit program."},
			{"history", "Show history."}
		};
		String help = "";
		help += "********************************************************************************" + System.lineSeparator();
		help += "Commands" + System.lineSeparator();
		help += "********************************************************************************" + System.lineSeparator();

		for (String []command : commands){
			help += String.format("\t%-16s%s",command[0], command[1]) + 
					System.lineSeparator();
		}
		help += "********************************************************************************" + System.lineSeparator();
		help += "Expresions" + System.lineSeparator();
		help += "********************************************************************************" + System.lineSeparator();
		help += "\tEvaluates expresions of the form:"+ System.lineSeparator();
		help += "\t\tnum1 num2  num3 ... operator1 operator2 ..." +
				System.lineSeparator();
		help += "\tExample:" + System.lineSeparator();
		help += "\t\t1 2 3 ... PLUS TIMES ..." +
				System.lineSeparator() ;
		help += "\tOperators:" + System.lineSeparator();
		help += "\t\tPLUS or +" + System.lineSeparator() ;
		help += "\t\tLESS or -" + System.lineSeparator() ;
		help += "\t\tTIMES or * x" + System.lineSeparator() ;
		help += "\t\tDIV or /" + System.lineSeparator() ;
		System.out.println(help);
	}

	public static void main(String[] args) {
		if (args.length == 1){
			switch (args[0]) {
				case "-h":
				case "--help":
				case "help":
					printHelp();
					break;
				default:
					rpnc.processFile(args[0]);
			}
		} else if (args.length == 0){
			interactiveMode();
		}
	}


}
