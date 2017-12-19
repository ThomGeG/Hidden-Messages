package main.java;

import java.util.ArrayList;
import java.awt.image.BufferedImage;

public interface CipherBehaviour {

	public void				encode(byte[] message, BufferedImage img);
	public ArrayList<Byte>	decode(int[] pixels);
	
}
