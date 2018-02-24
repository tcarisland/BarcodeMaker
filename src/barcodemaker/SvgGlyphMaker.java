package barcodemaker;

import java.util.HashMap;
import java.util.Set;

public class SvgGlyphMaker {

	public static void main(String args[]) {
		HashMap<String, Code128Char> table = Code128Char.readTable("Code128.csv");
		Set<String> keys = table.keySet();
		for(String s : keys) {
			Code128Char c = table.get(s);
			System.out.println(c.getCharval() + " " + c.getPattern());
			String filename = c.getCharval();
			if(filename.length() == 1 && Character.isLowerCase(filename.charAt(0)))
				filename += "l";
			else if (filename.length() == 1)
				filename = "00_" + c.getAsciival();
			filename += ".svg";
			Barcode b = new Barcode(c.getPattern(), "16", "1024");
			b.writeSVG(filename);
		}
	}
	
}
