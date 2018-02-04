package barcodemaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Code128Char {
	
	private int codeval, asciival;
	private String charval, pattern;
		
	public Code128Char(String codeval, String charval, String asciival, String pattern) {
		this.codeval = this.parse(codeval);
		if(charval.equalsIgnoreCase("space")) {
			this.charval = " ";
		} else {
			this.charval = charval;
		}
		this.setAsciival(this.parse(asciival));
		this.pattern = pattern;
	}

	public static HashMap<String, Code128Char> readTable(String filename) {
		HashMap<String, Code128Char> table = null;
		try {
			table = new HashMap<String, Code128Char>();
			Scanner in = new Scanner(new File(filename));
			while(in.hasNextLine()) {
				String line[] = in.nextLine().split("\\s+");
				if(line.length >= 4) {
					//System.out.println(line[0] + " " + line[1] +" "+ line[2] +" "+ line[3]);
					Code128Char c = new Code128Char(line[0], line[1], line[2], line[3]);
					table.put(c.getCharval(), c);
				}
			}
			in.close();
			return table;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return table;
	}
	
	public int parse(String s) {
		int retval = -1;
		try {
			retval = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			retval = -1;
		}
		return retval;
	}
	
	public int getCodeval() {
		return codeval;
	}

	public void setCodeval(int codeval) {
		this.codeval = codeval;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public int getAsciival() {
		return asciival;
	}

	public void setAsciival(int asciival) {
		this.asciival = asciival;
	}

	public String getCharval() {
		return charval;
	}

	public void setCharval(String charval) {
		this.charval = charval;
	}
	
}
