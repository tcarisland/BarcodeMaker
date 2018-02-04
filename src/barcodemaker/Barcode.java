package barcodemaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Barcode {

	String code;
	int widthSum, xOffset, yOffset, barWidth, barHeight;
	boolean dimensionsSet;
	
	public Barcode(String code) {
		this.code = Barcode.removeNonNumeric(code);
		this.widthSum = 0;
		xOffset = yOffset = 10;
		barWidth = 1;
		barHeight = 50;
	}

	public Barcode(String code, String barWidth, String barHeight) {
		this.code = Barcode.removeNonNumeric(code);
		this.xOffset = 0;
		this.yOffset = 0;
		this.barWidth = Barcode.parse(barWidth);
		this.barHeight = Barcode.parse(barHeight);
	}
	
	public Barcode(String code, String xOffset, String yOffset, String barWidth, String barHeight) {
		this.code = Barcode.removeNonNumeric(code);
		this.xOffset = Barcode.parse(xOffset);
		this.yOffset = Barcode.parse(yOffset);
		this.barWidth = Barcode.parse(barWidth);
		this.barHeight = Barcode.parse(barHeight);
	}	
	
	public static String convertPattern(String s) {
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
	
	public static int parse(String s) {
		String n = Barcode.removeNonNumeric(s);
		if(n.length() != 0)
			return Integer.parseInt(n);
		else
			return 0;
	}
	
	public static String removeNonNumeric(String s) {
		return s.replaceAll("[^\\d]", "");
	}

	public String createBarcodeStandaloneSVG() {
		String s = "";
		String rectangles = this.createBarcodeRectangles();
		s += "<?xml version=\"1.0\" standalone=\"no\"?>\n";
		s += "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n";
		s += "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"" + (widthSum + 10) + "\" height=\"" +(barHeight + 10)+ "\">\n";
		s += rectangles;
		s += "</svg>\n";
		return s;
	}
	
	public String createBarcodeInlineSVG() {
		String rectangles = this.createBarcodeRectangles();
		return ""
				+ "<svg "
				+ "height=\""
				+ (barHeight + 10)
				+ "\" width=\""
				+ (widthSum + 10)
				+ ">\n"
				+ rectangles
				+ "\n</svg>";
	
	}
	
	public String createBarcodeRectangles() {
		return this.createBarcodeRectangles(xOffset, yOffset, barWidth, barHeight);
	}
	
	public String createBarcodeRectangles(int xOffset, int yOffset, int barWidth, int barHeight) {
		String rectangles = "";
		widthSum = xOffset;
		for(int i = 0; i < code.length(); i++) {
			int width = Integer.parseInt("" + code.charAt(i)) * barWidth;
			int barcolor = 0;
			if(i % 2 == 1) {
				barcolor = 255;
			}
			String fill = "fill:rgb(" +barcolor+ ", " +barcolor+ ", " +barcolor+ ")";
			rectangles += "<rect x=\"" +widthSum+ "\" y=\"" + yOffset + "\" width=\"" + width + "\" height=\"" +barHeight+ "\" style=\"" +fill+ "\" />\n";
			widthSum += width;
		}
		return rectangles;
	}
	
	public void writeSVG(String filename) {
		try {
			PrintWriter output = new PrintWriter(new File(filename));
			output.write(this.createBarcodeStandaloneSVG());
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
