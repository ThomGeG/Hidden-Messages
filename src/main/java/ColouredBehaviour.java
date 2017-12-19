package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * ColouredBehaviour that encodes 3 bytes of data into the red, green, and blue components of a pixel. 
 * @author Tom
 */
public class ColouredBehaviour implements CipherBehaviour {

	@Override
	public void encode(byte[] bArr, BufferedImage img) {
		
		Graphics brush = img.getGraphics();

        //Iterate over each pixel...
		int x = 0, y = 0, bPointer = 0;
		breakpoint:
		for(; y < img.getHeight(); y++) {
        	for(x = 0; x < img.getWidth(); x++) {
        		
        		int colour = 0;
        		for(int i = 2; i != -1; i--) {//R, G, B components
        			try {
        				colour += (int) (bArr[bPointer++]) << (i * 8); //Concatenate 3 bytes to form the colour for the pixel.
        			} catch(ArrayIndexOutOfBoundsException e) {
        				//Finish drawing the pixel and escape.
        				brush.setColor(new Color(colour, false));
        				brush.fillRect(x, y, 1, 1);
        				break breakpoint;
        			}
        		}
        		
        		//Paint the pixel.
        		brush.setColor(new Color(colour, false));
        		brush.fillRect(x, y, 1, 1);
        			
        	}
        }
		
		brush.dispose();
		
	}

	@Override
	public ArrayList<Byte> decode(int[] pixelData) {
		
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
		
		
		return imageData;
		
	}

}
