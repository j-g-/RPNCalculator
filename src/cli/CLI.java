package cli;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rpncalculator.RPNCalculator;

/**
 * Command line interface. 
 * Takes arguments or enters an interactive mode.
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
		printBanner();
		String prompt = ">>> ";
		Scanner sc = new Scanner(System.in);
	    for(;;){
			rpnc.setInput("stdin");
			System.out.print(prompt);
			String line = sc.nextLine();
			if (line.matches("h|help")){
				//Show help
				printHelp();
			} else if (line.matches("exit|quit|q")){
				// Exit program
				System.exit(0);
			} else if (line.matches("file .*|load .*|f .*|l .*")){
				// Process files
				String[] fileNames = line.split("\\s");
				if (fileNames.length > 0) {
					for (int i = 1; i < fileNames.length; i++) {
						String url = fileNames[i];
						url = url.replaceFirst("^~", System.getProperty("user.home"));
						if (Files.exists(Paths.get(url))){
							rpnc.processFile(url);
						} else{
							System.err.println("File specified deos not exists, provide a valid file path.");
						}
					}
				} else {
					System.err.println("No file specified, please, provide a file path.");
				}
			} else if (line.matches("history.*|hist.*")){
				// Show history
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
		String ls = System.lineSeparator();
		String[][] commands = {
			{"help", "Prints this help."},
			{"quit/exit", "Exit program."},
			{"file|f fileNames...", ""},
			{"load|l fileNames...", "Process the files containing RPN expressions."},
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
		help += "********************************************************************************" + ls;
		help += "Arguments" + ls;
		help += "********************************************************************************" + ls;
		help += "\t java -jar RPNCalculator.jar [[options]|[file]] "+ ls;
		help += "Options:"+ ls;
		for (String []arg : args){
			help += String.format("\t%-24s%s",arg[0], arg[1]) + 
					ls;
		}

		help += ls;
		help += ls;
		help += "********************************************************************************" + ls;
		help += "Interactive mode commands" + ls;
		help += "********************************************************************************" + ls;

		for (String []command : commands){
			help += String.format("\t%-24s%s",command[0], command[1]) + 
					ls;
		}
		help += ls;
		help += ls;
		help += "********************************************************************************" + ls;
		help += "Expresions" + ls;
		help += "********************************************************************************" + ls;
		help += "\tEvaluates expresions of the form:"+ ls;
		help += "\t\tnum1 num2  num3 ... operator1 operator2 ..." +
				ls;
		help += "\tExample:" + ls;
		help += "\t\t1 2 3 ... PLUS TIMES ..." +
				ls ;
		help += "\tOperators:" + ls;
		help += "\t\tPLUS or +" + ls ;
		help += "\t\tLESS or -" + ls ;
		help += "\t\tTIMES or * x" + ls ;
		help += "\t\tDIV or /" + ls ;
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
	 * Prints the application banner.
	 */
	public static void printBanner(){
		String ls = System.lineSeparator();
		String banner =
				"*********************************************************************************"+ls+
				"*         ___    ___    _  __  _____                                            *"+ls+
				"*        / _ \\  / _ \\  / |/ / / ___/                                            *"+ls+
				"*       / , _/ / ___/ /    / / /__                                              *"+ls+
				"*      /_/|_| /_/    /_/|_/  \\___/                                              *"+ls+
				"*                                                                               *"+ls+
				"*                              Reverse Polish Notation Calculator               *"+ls+
				"*                              by: J. Garcia, u0x004a at gmail.com              *"+ls+
				"*                                                                               *"+ls+
				"*********************************************************************************"+ls+
				"Please enter a RPN expression or command, enter h or help to see commands." + ls;

		System.out.println(banner);
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
