package main.java;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Client {

	public static void main(String[] args) throws IOException {

		if(args.length != 2)
			throw new IllegalArgumentException("Improper number of arguments. Expected 2, provided: " + args.length);
		
		File source			= new File(args[0]);
		File destination	= new File(args[1]);
		
		byte[] arr = null;
		BufferedImage image = null;
		
		if(!source.canRead() || !source.isFile())
			throw new IllegalArgumentException("Source cannot be read is or not a file.");
		
		if(source.getPath().toLowerCase().endsWith(".png")) { //Decoding an image.
			
			image = ImageIO.read(source);
			arr = Encoder.decode(image);
			
			FileOutputStream os = new FileOutputStream(destination);
			os.write(arr);
			os.close();
			
			System.out.println(source.getAbsolutePath() + " decoded into " + destination.getAbsolutePath());
			
		} else { //Encoding a source.
			
			arr = new byte[(int) source.length()];
			FileInputStream fs = new FileInputStream(source);
			
			fs.read(arr);
			image = Encoder.encode(arr);
			
			fs.close();
			ImageIO.write(image, "png", destination);
			
			System.out.println(source.getAbsolutePath() + " encoded into " + destination.getAbsolutePath());
			
		}

	}

}
