package barcodemaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class EAN {

	HashMap<String, String[]> table = new HashMap<String, String[]>();
	String lgr;
	String number;
	String binaryString;
	int checksum;
	
	public static void main(String args[]) {
		EAN code = new EAN("198604168230");
		//EAN code = new EAN("123949192392");
		System.out.println("Number: " + code.getNumber());
		System.out.println("Checksum: " + code.getChecksum());
		System.out.println("Binary String: " + code.getBinaryString());
		System.out.println("Sequence: " + EAN.binToDecSeq(code.getBinaryString()));		
		Barcode b = new Barcode(EAN.binToDecSeq(code.getBinaryString()));
		String s = b.createBarcodeStandaloneSVG();
		PrintWriter out;
		try {
			out = new PrintWriter(new File("EAN.svg"));
			out.println(s);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String binToDecSeq(String seq) {
		String s = "";
		int sum = 1;
		for(int i = 1; i < seq.length(); i++) {
			if(seq.charAt(i) != seq.charAt(i - 1)) {
				s = s + sum;
				sum = 1;
			} else {
				sum++;
			}
			if(i == seq.length() - 1) {
				s = s + sum;
			}
		}			
		return s;
	}
	
	public EAN(String number) {
		this.number = number.substring(0, 12);
		this.checksum = EAN.calculateChecksum(this.number);
		this.number = number + checksum;
		this.table = EAN.makeTable();
		this.lgr = table.get("LGR")[Integer.parseInt("" + number.charAt(0))];
		System.out.println("LGR: " + lgr);
		this.binaryString = EAN.createBinaryString(this.number, lgr, table);
	}
	
	public static String createBinaryString(String number, String lgr, HashMap<String, String[]> table) {
		String binaryString = "";
		lgr = lgr + "RRRRRR";
		number = number.substring(1);
		binaryString += "101";
		for(int i = 0; i < number.length(); i++) {
			binaryString += table.get("" + lgr.charAt(i))[Character.getNumericValue(number.charAt(i))];
			if(i == 5)
				binaryString += "01010";
		}
		binaryString += "101";
		return binaryString;
	}
	
	public static HashMap<String, String[]> makeTable() {
		HashMap<String, String[]> table = new HashMap<String, String[]>();
		table.put("L", new String[] { "0001101", "0011001", "0010011", "0111101", "0100011", "0110001",
				"0101111", "0111011", "0110111", "0001011" });
		table.put("R", new String[] { "1110010", "1100110", "1101100", "1000010", "1011100", "1001110",
				"1010000", "1000100", "1001000", "1110100" });
		table.put("G", new String[] { "0100111", "0110011", "0011011", "0100001", "0011101", "0111001",
				"0000101", "0010001", "0001001", "0010111" });
		table.put("LGR", new String[] { "LLLLLLRRRRRR", "LLGLGGRRRRRR", "LLGGLGRRRRRR", "LLGGGLRRRRRR", "LGLLGGRRRRRR",
				"LGGLLGRRRRRR", "LGGGLLRRRRRR", "LGLGLGRRRRRR", "LGLGGLRRRRRR", "LGGLGLRRRRRR" });
		return table;
	}
	
	public static int calculateChecksum(String twelveDigits) {
		int checksum = 0;
		for(int i = 0; i < twelveDigits.length() && i < 12; i++) {
			String s = "" + twelveDigits.charAt(i);
			if(i % 2 == 0) {
				checksum += Integer.parseInt(s) * 1;
			} else {
				checksum += Integer.parseInt(s) * 3;
			}
		}
		checksum = 10 - (checksum % 10);
		return checksum;
	}

	public String getBinaryString() {
		return binaryString;
	}

	public void setBinaryString(String binaryString) {
		this.binaryString = binaryString;
	}

	public int getChecksum() {
		return checksum;
	}

	public void setChecksum(int checksum) {
		this.checksum = checksum;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
