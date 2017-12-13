package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.File;

import java.util.ArrayList;


/** 
 * A small class for encoding/decoding messages hidden in images.
 * @author Tom
 */
public class Encoder {
		
	/**
	 * Overloaded method for {@link #encode(File)}
	 * Takes a byte array, <i>b</i>, and encodes it into a {@link BufferedImage}.<br>
	 * Each pixel of the image contains 3 characters 
	 * 
	 */
	public static BufferedImage encode(byte[] bArr) {
		
		if(bArr == null || bArr.length == 0) throw new IllegalArgumentException("Cannot encode an empty byte array.");
		
		int bPointer = 0; //Current byte to be encoded.
		
		//Calculate width and height for image.
		int width	= (int) Math.sqrt(bArr.length / 3) + 1;
        int height	= width;
		
        //Our image.
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		//Brush used to draw onto the image.
        Graphics brush = image.getGraphics();
	
        //Iterate over each pixel...
		int x = 0, y = 0;
		breakpoint:
		for(; y < height; y++) {
        	for(x = 0; x < width; x++) {
        		
        		int colour = 0;
        		for(int i = 2; i != -1; i--) {
        			try {
        				colour += (int) (bArr[bPointer++]) << (i * 8); //Concatenate 3 bytes to form the colour for the pixel.
        			} catch(ArrayIndexOutOfBoundsException e) {
        				//Finish drawing the pixel and escape.
        				brush.setColor(new Color(colour, false));
        				brush.fillRect(x, y, 1, 1);
        				break breakpoint;
        			}
        		}
        			
        		brush.setColor(new Color(colour, false));
        		brush.fillRect(x, y, 1, 1);
        			
        	}
        }
		
        brush.dispose();
		image.flush();
		
		return image;
	
	}
	
	public static byte[] decode(BufferedImage image) {
		
		int[] pixelData = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		ArrayList<Byte> imageData = new ArrayList<Byte>(pixelData.length / 3);
		
		breakpoint: //Jump point to escape parsing.
		for(int pixel : pixelData) {
			for(int i = 2; i != -1; i--) { //Iterate over R, G, and B bytes.
				
				//Extract and append byte data.
				byte b = (byte) (pixel >>> (i * 8));
				imageData.add(b);

				//Terminate parsing on a 0/null terminator, 
				if(b == 0)
					break breakpoint;
				
			}
		}
		
		byte[] byteData = new byte[imageData.size()];
		for(int i = 0; i < byteData.length; i++) {
			byteData[i] = (byte) imageData.get(i);
		}
		
		return byteData;
		
	}

}
