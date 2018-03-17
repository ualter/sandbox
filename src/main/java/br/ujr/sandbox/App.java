package br.ujr.sandbox;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		//hexaOperations();
		
		int width = 200;
		int height = 400;
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.getGraphics();
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setFont(new Font("SansSerif", Font.BOLD, 24));
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics2D.drawString("UALTER Junior", 12, 24);
		
		for (int y = 0; y < height; y++) {
		    StringBuilder stringBuilder = new StringBuilder();
		 
		    
		    for (int x = 0; x < width; x++) {
		    	stringBuilder.append(bufferedImage.getRGB(x, y) == -16777216 ? "*" : "0");
		    }
		 
		    if (stringBuilder.toString().trim().isEmpty()) {
		        continue;
		    }
		 
		    System.out.println(stringBuilder);
		}
		
		
		
	}

	private static void hexaOperations() {
		int hexValue1 = 0x6800;
		int hexValue2 = 0x4d2;
		
		
		/*System.out.println(hexValue1);
		System.out.println(hexValue2);*/
		
		"N".chars().forEach(c -> System.out.println(Integer.toHexString(c)));
		"T".chars().forEach(c -> System.out.println(Integer.toHexString(c)));
		"L".chars().forEach(c -> System.out.println(Integer.toHexString(c)));
		"M".chars().forEach(c -> System.out.println(Integer.toHexString(c)));
		"S".chars().forEach(c -> System.out.println(Integer.toHexString(c)));
		"S".chars().forEach(c -> System.out.println(Integer.toHexString(c)));
		"P".chars().forEach(c -> System.out.println(Integer.toHexString(c)));
		
//		System.out.println(convertHex("1,0".charAt(0)));
		
		System.out.println(Long.parseLong("01000000",16));
		System.out.println(Integer.decode("0x01000000")) ;
	}
	
	public static String convertHex(char c) {
		return Integer.toHexString(c);
	}
}
