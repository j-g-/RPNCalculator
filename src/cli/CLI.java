/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cli;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rpncalculator.RPNCalculator;

/**
 *
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class CLI {
	static RPNCalculator rpnc  = RPNCalculator.getInstance();
	public static boolean showHelp = false;
	public static boolean showHistory = false;
	public static String historyMode = "normal";
	public static boolean parseFile = false;
	public static boolean interactiveMode = false;
	public static String fileToParse = "normal";

	/**
	 * Initializes the interactive mode to read the standard input.
	 */
	private static void startInteractiveMode() {
		String prompt = ">>> ";
		Scanner sc = new Scanner(System.in);
	    for(;;){
			rpnc.setInput("stdin");
			System.out.print(prompt);
			String line = sc.nextLine();
			if (line.matches("h|help")){
					printHelp();
			} else if (line.matches("exit|quit|q")){
					System.exit(0);
			} else if (line.matches("history.*|hist.*")){
				//Matcher m = 	
				Pattern p =  Pattern.compile("(?:^.*)(history|hist)(?:\\s*)(?<opt>r.*|d.*)*(?:$)");
				Matcher m = p.matcher(line);
				String opt = "";
				if (m.matches()){
					opt = m.group("opt");
				}
				if (opt == null ){
					opt = "";
				}
				if (opt.matches("r.*")){
					rpnc.getCalculationsHistory().printOrderedByResult(true);
				} else if (opt.matches("d.*")){
					rpnc.getCalculationsHistory().printOrderedByDate();
				} else {
					rpnc.getCalculationsHistory().printOrderedByResult(false);
				}
			} else {
					System.out.println(rpnc.parseLine(line));
			}
		}
	}

	/**
	 * Command line arguments parser
	 * @param args Arguments passed to the program.
	 */
	private static void parseArgs(String [] args){
		if (args.length >  0){
			for(String arg : args) {
				if(arg.matches("-h|--help")) {
					showHelp = true;
				} else if(arg.matches("--history|-ho|--history=ord.*")) {
					showHistory = true;
					historyMode = "o"; //ordered
				} else if(arg.matches("-hr|--history=rev.*")) {
					showHistory = true;
					historyMode = "r"; //reversed
				} else if(arg.matches("-hd|--history=da.*")) {
					showHistory = true;
					historyMode = "d";//date
				} else {
					 fileToParse = arg;
					 parseFile   = true;
				}
			}
		} else {
			interactiveMode = true;
		}
	}

	/**
	 * Prints the help for this program.
	 */
	private static void printHelp() {
		String[][] commands = {
			{"help", "Prints this help."},
			{"quit/exit", "Exit program."},
			{"history [r|d]", "Show history. r = reverse result, d = date"}
		};
		String[][] args = {
			{"-h --help", "Print this help."},
			{"-ho", ""},
			{"--history=ordered", "Print sorted history results."},
			{"-hr", ""},
			{"--history=reverse", "Print sorted history results in reverse."},
			{"-hd", ""},
			{"--history=date", "Print sorted history results by date."},
			{"[file]", "File to parse"}
		};
		String help = "";
		help += "********************************************************************************" + System.lineSeparator();
		help += "Arguments" + System.lineSeparator();
		help += "********************************************************************************" + System.lineSeparator();
		help += "\t java -jar RPNCalculator.jar [[options]|[file]] "+ System.lineSeparator();
		help += "Options:"+ System.lineSeparator();
		for (String []arg : args){
			help += String.format("\t%-24s%s",arg[0], arg[1]) + 
					System.lineSeparator();
		}

		help += System.lineSeparator();
		help += System.lineSeparator();
		help += "********************************************************************************" + System.lineSeparator();
		help += "Interactive mode commands" + System.lineSeparator();
		help += "********************************************************************************" + System.lineSeparator();

		for (String []command : commands){
			help += String.format("\t%-16s%s",command[0], command[1]) + 
					System.lineSeparator();
		}
		help += System.lineSeparator();
		help += System.lineSeparator();
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

	/**
	 * Run using the parsed arguments.
	 */
	private static void run(){
		if (interactiveMode){
			startInteractiveMode();
		} else {
			if (showHelp){
				printHelp();
			}
			if (showHistory) {
				switch (historyMode) {
					case "o":
						rpnc.getCalculationsHistory().printOrderedByResult(false);
						break;
					case "r":
						rpnc.getCalculationsHistory().printOrderedByResult(true);
						break;
					case "d":
						rpnc.getCalculationsHistory().printOrderedByDate();
						break;
					default:
						rpnc.getCalculationsHistory().printOrderedByDate();
				}
			}
			if(parseFile){
				rpnc.processFile(fileToParse);
			}
		}
	
	}
	/**
	 * Main method.
	 * @param args  Arguments.
	 */
	public static void main(String[] args) {
		parseArgs(args);
		run();
	}
}
