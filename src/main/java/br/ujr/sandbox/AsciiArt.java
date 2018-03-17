package br.ujr.sandbox;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

public class AsciiArt {
	
	public static void main(String[] args) {
		
		
		int width = 200;
		int height = 25;
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.getGraphics();
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setFont(new Font("SansSerif", Font.BOLD, 24));
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics2D.drawString("UALTER Junior", 8, 21);
		
		String background = "*";
		String foreground = "0";
		printAsciiArt(width, height, bufferedImage, background, foreground);
		
		System.out.println("\n\n");
		background = "*";
		foreground = " ";
		printAsciiArt(width, height, bufferedImage, background, foreground);
		
		System.out.println("\n\n");
		background = " ";
		foreground = "0";
		printAsciiArt(width, height, bufferedImage, background, foreground);
		
		System.out.println("\n\n");
		background = " ";
		foreground = "1";
		printAsciiArt(width, height, bufferedImage, background, foreground);
		
		System.out.println("\n\n");
		background = " ";
		foreground = "0";
		printAsciiArt(width, height, bufferedImage, background, new String[] {"0","1"});
		
		System.out.println("\n\n");
		background = " ";
		foreground = "0";
		printAsciiArt(width, height, bufferedImage, background, new String[] {"A","B", "C","D"});
		
		System.out.println("\n\n");
		background = " ";
		foreground = "0";
		printAsciiArt(width, height, bufferedImage, background, new String[] {"U","A", "L","T","E","R"});
	}

	private static void printAsciiArt(int width, int height, BufferedImage bufferedImage, String background, String ... foreground) {
		Random rand = new Random();
		
		for (int y = 0; y < height; y++) {
		    StringBuilder stringBuilder = new StringBuilder();
		    for (int x = 0; x < width; x++) {
	    		stringBuilder.append(bufferedImage.getRGB(x, y) == -16777216 ? background : foreground[rand.nextInt(foreground.length)]);
		    }
		    if (stringBuilder.toString().trim().isEmpty()) {
		        continue;
		    }
		    System.out.println(stringBuilder);
		}
	}

}
