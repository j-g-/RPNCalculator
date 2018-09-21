/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpncalculator;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.LocalAttribute;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class CalculationsHistory {
	Charset utf8 = StandardCharsets.UTF_8;
	ArrayList<Calculation> calculations = null;
	ArrayList<Calculation> calculationsSorted = null;
	Path logFile = null;
	String separator = ":::";
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

	private class BigDecimalComparator implements Comparator<Calculation>{
		private boolean reverse = false;

		public void setReverse(boolean reverse) {
			this.reverse = reverse;
		}

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

		@Override
		public Comparator<Calculation> reversed() {
			this.setReverse(true);
			return this;
		}
	}
	public void printOrderedByResult( boolean reverse){
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
		for (int i = 0; i < calculationsSorted.size(); i++) {
			Calculation cs = calculationsSorted.get(i);
			System.out.println(cs.getResult()+" = " +cs.getOperation());
		}
	}
	public void printOrderedByDate(){
		calculationsSorted = (ArrayList)calculations.clone();
		calculationsSorted.sort((t, t1) -> {
			LocalDateTime a = LocalDateTime.parse(t.getDate());
			LocalDateTime b = LocalDateTime.parse(t1.getDate());
			return a.compareTo(b); //To change body of generated lambdas, choose Tools | Templates.
		});
		for (Calculation cs : calculationsSorted) {
			System.out.println(cs.getDate()+ "    "+ cs.getOperation()+" = " +cs.getResult());
		}
	
	}

	private Calculation parseLine(String line){
		String [] parts = line.split(this.separator);
		String date = parts[0];
		String operation = parts[1];
		String result = parts[2];
		String info = parts[3];
		String source = parts[4];
		return new Calculation(result, operation, date, info, source);
	}

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

	public void log( Calculation c ){
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
}
