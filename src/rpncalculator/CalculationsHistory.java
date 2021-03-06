package rpncalculator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Hols the functionality related to the history of results.
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class CalculationsHistory {
	Charset utf8 = StandardCharsets.UTF_8;
	ArrayList<Calculation> calculations = null;
	ArrayList<Calculation> calculationsSorted = null;
	Path logFile = null;
	String separator = ":::";

	public CalculationsHistory() {
		this.calculations = new ArrayList<>();
	}
	
	/**
	 * Load the history.
	 * Search for rpnc-history.log in current directory and user's home.
	 * Creates file in current directory if it does not exists.
	 */
	public void loadHistory(){
		String fileName = "rpnc-history.log";
		Path wd = Paths.get(System.getProperty("user.dir"), fileName);
		Path home = Paths.get(System.getProperty("user.home"), fileName);
		System.getProperty("user.home");
		if (Files.exists(wd)){
			this.loadFile(wd.toString());
			logFile = wd;
		}  else if (Files.exists(home)){
			this.loadFile(home.toString());
			logFile = home;
		} else {
			try {
				logFile = wd;
				Files.write(wd,"".getBytes());
			} catch (IOException ex) {
				System.err.println("Could not write to file: " + logFile);
			}
		}
	}

	/**
	 * BigDecimal comparator.
	 * A comparator for BigDecimals.
	 */
	private class BigDecimalComparator implements Comparator<Calculation>{
		private boolean reverse = false;

		/**
		 * Set the order to the reverse.
		 * @param reverse  Set to true for reverse sorting.
		 */
		public void setReverse(boolean reverse) {
			this.reverse = reverse;
		}

		/**
		 * Compares the Result of two Calculation.
		 * @param t The calculation a.
		 * @param t1 The calculation b
		 * @return  1, 0, or -1, according to a greater, equal or less than b.
		 */
		@Override
		public int compare(Calculation t, Calculation t1) {
			BigDecimal a = new BigDecimal(t.getResult());
			BigDecimal b = new BigDecimal(t1.getResult());
			int r = a.compareTo(b);
			if (reverse){
				r *= -1;
			}
			return r ; 
		}

		/**
		 * Get a reversed comparator.
		 * @return A reverse comparator.
		 */
		@Override
		public Comparator<Calculation> reversed() {
			this.setReverse(true);
			return this;
		}
	}

	/**
	 * Print history sorting the calculations by result
	 * @param reverse  If set to true reverse order will be used.
	 */
	public void printOrderedByResult( boolean reverse){
		if(calculations.size() > 0 ){
			calculationsSorted = new ArrayList<>();
			for (Calculation calculation : calculations) {
				if(calculation.getResult().compareTo("Error") != 0 ){
					calculationsSorted.add(calculation);
				}
			}
			Comparator c = new BigDecimalComparator();
			if (reverse) {
				c = c.reversed();
			}
			calculationsSorted.sort(c);
			System.out.println(
					String.format(
							"Sorted by result %s.",
							(reverse)?"descending" : "ascending"
					)
			);
			int tab = this.getLargestExpression().length();
			for (int i = 0; i < calculationsSorted.size(); i++) {
				Calculation cs = calculationsSorted.get(i);
				String output = String.format( "(%s)  %-"+tab+"s = %s",
						this.makeDateString(cs.getDate()),
						cs.getOperation(),
						cs.getResult()

				);
				System.out.println(output);
			}
			
		}
	}
	/**
	 * Print ordered by the date of calculation.
	 */
	public void printOrderedByDate(){
		System.out.println("Sorted by date.");
		if(calculations.size() > 0){
			calculationsSorted = (ArrayList)calculations.clone();
			calculationsSorted.sort((t, t1) -> {
				LocalDateTime a = LocalDateTime.parse(t.getDate());
				LocalDateTime b = LocalDateTime.parse(t1.getDate());
				return a.compareTo(b);
			});
			int tab = this.getLargestExpression().length();
			for (int i = 0; i < calculationsSorted.size(); i++) {
				Calculation cs = calculationsSorted.get(i);
				String output = String.format( "(%s)  %-"+tab+"s = %s",
						this.makeDateString(cs.getDate()),
						cs.getOperation(),
						cs.getResult()

				);
				System.out.println(output);
			}
			
		}
	}

	/**
	 * Parser a line.
	 * @param line Line to parse.
	 * @return  A Calculation object.
	 */
	private Calculation parseLine(String line){
		String [] parts = line.split(this.separator);
		String date = parts[0];
		String operation = parts[1];
		String result = parts[2];
		String info = parts[3];
		String source = parts[4];
		return new Calculation(result, operation, date, info, source);
	}

	/**
	 * Load the history from fileName.
	 * @param fileName The file to use for the history.
	 */
	private void loadFile(String fileName){
		FileInputStream fis = null;
		calculations = new ArrayList<>();
		try {
			fis = new FileInputStream(fileName);
			Scanner sc = new Scanner(fis);
			while (sc.hasNextLine()){
				calculations.add(this.parseLine(sc.nextLine()));
			}
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + fileName);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				System.err.println("Could not write to file: " + fileName);
			}
		}
	}

	/**
	 * Log calculation to log file.
	 * @param c  Calculation to log.
	 */
	public void log( Calculation c ){
		this.calculations.add(c);
		try {
			String output = "";
			output += c.getDate();
			output += separator;
			output += c.getOperation();
			output += separator;
			output += c.getResult();
			output += separator;
			output += c.getInfo();
			output += separator;
			output += c.getSource();
			output += System.lineSeparator();
			Files.write(logFile, output.getBytes(utf8), StandardOpenOption.APPEND );
		} catch (IOException ex) {
			System.err.println("Could not write to file: " + logFile);
		}
	}

	/**
	 * Get the calculations in the history.
	 * @return  The calculations in the history.
	 */
	public ArrayList<Calculation> getCalculations() {
		return calculations;
	}
	/**
	 * Get the largest expression.
	 * @return  The largest expression in the history.
	 */
	private String getLargestExpression(){
		String largest = calculations.get(0).getOperation();
		for (Calculation calculation : calculations) {
			if(calculation.getOperation().length() >= largest.length()){
				largest = calculation.getOperation();
			}
		}
		return largest;
	
	}
	/**
	 * Creates a string to represent a date.
	 * @param localdate A string from a LocalDateTime.
	 * @return  A date string in printable format.
	 */
	private String makeDateString(String localdate){
		return LocalDateTime.parse(localdate).format(
				DateTimeFormatter.ofPattern(
						"dd MMM yyyy HH:MM:ss.SSS"
				)
		);
	
	}
}
