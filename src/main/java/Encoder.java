package main.java;

import java.awt.image.BufferedImage;

import java.util.ArrayList;

/** 
 * A small class for encoding/decoding messages hidden in images.
 * @author Tom
 */
public class Encoder {
	
	/** Defines the behaviour for encoding/decoding pixels in an image. */
	private CipherBehaviour behaviour;
	
	/** Default Constructor. Sets the encode/decode behaviour to {@link ColouredBehaviour} */
	public Encoder() {
		this.behaviour = new ColouredBehaviour();
	}
	
	public Encoder(CipherBehaviour behaviour) {
		this.behaviour = behaviour;
	}
	
	public void setBehaviour(CipherBehaviour behaviour) {
		this.behaviour = behaviour;
	}
	
	/**
	 * Takes a byte array, <i>b</i>, and encodes it into a {@link BufferedImage} using the 
	 * behaviour outlined by the objects {@link CipherBehaviour}, <i>behaviour</i>.<br>
	 */
	public BufferedImage encode(byte[] bArr) {
		
		if(bArr == null || bArr.length == 0) throw new IllegalArgumentException("Cannot encode an empty byte array.");
				
		//Image dimensions.
		int width	= (int) Math.sqrt(bArr.length / 3) + 1;
        int height	= width;
		
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        //Defer to strategy/behaviour on how to encode the data.
        behaviour.encode(bArr, image);

		return image;
		
	}
	
	/**
	 * Takes an image, <i>image</i>, and decodes back into a byte array using the 
	 * behaviour outlined by the objects {@link CipherBehaviour}, <i>behaviour</i>.<br>
	 */
	public byte[] decode(BufferedImage image) {
		
		//Retrieve pixel data. (Only good for ~2.1GB sized images)
		int[] pixelData = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		
		//Defer to strategy/behaviour on how to decode the data.
		ArrayList<Byte> imageData =  behaviour.decode(pixelData);
		
		//Unbox Byte list to byte array.
		byte[] byteData = new byte[imageData.size()];
		for(int i = 0; i < byteData.length; i++) {
			byteData[i] = (byte) imageData.get(i);
		}
		
		return byteData;
		
	}

}
