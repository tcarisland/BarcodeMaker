package barcodemaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Code128Parser {
	
	HashMap<String, Code128Char> table = new HashMap<String, Code128Char>();
	Code128Char checksumTable[] = new Code128Char[103];
	
	public static void main(String args[]) {
		Code128Parser parser = new Code128Parser();
		System.out.println(parser.barcodePattern(args[0]));
	}
	
	public Code128Parser() {
		table = Code128Char.readTable("Code128.csv");
		Set<String> keys = table.keySet();
		for(String s : keys) {
			Code128Char c = table.get(s);
			if(c.getCodeval() >= 0 && c.getCodeval() < 103) {
				checksumTable[c.getCodeval()] = c;
			}
		}
	}
	
	public String barcodePattern(String input) {
		String s = this.parse(input);
		String retval = "";
		for(int i = 0; i < s.length(); i++) {
			if(!Character.isDigit(s.charAt(i)))
				return "";
			int q = Integer.parseInt("" + s.charAt(i));
			for(; q > 0; q--) {
				if(i % 2 == 0) {
					retval += "" + 1;
				} else {
					retval += "" + 0;
				}
			}
		}
		return retval;
	}
	
	public String parse(String input) {
		ArrayList<Code128Char> line = new ArrayList<Code128Char>();
		Code128Char start, stop;
		start = table.get("StartCodeB");
		stop = table.get("Stop");
		line.add(start);
		int sum = start.getCodeval();
		for(int i = 0; i < input.length(); i++) {
			Code128Char c = table.get("" + input.charAt(i));
			sum += c.getCodeval() * (i + 1);
			line.add(c);
		}
		Code128Char checksum = checksumTable[sum % 103];
		line.add(checksum);
		line.add(stop);
		String retval = "";
		for(int i = 0; i < line.size(); i++) {
			retval += line.get(i).getPattern();
		}
		return retval;
	}
	
}
