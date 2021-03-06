package test.java;

import org.junit.Test;

import main.java.ColouredBehaviour;
import main.java.Encoder;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.awt.image.BufferedImage;

public class EncoderTest {
	
	/** Helper function to generate a bounded random number */
	public static int rand(int low, int high){
		return (int) (Math.random() * (high - low + 1)) + low;
	}
	
	/** Helper function to promote stuff. */
	public void encodeAndAssert(byte[] b) {
	
		System.out.println("--V-- Test --V--");
		
		Encoder e = new Encoder();
		
		BufferedImage encodedImage	= e.encode(b);
		byte[] decodedBytes			= e.decode(encodedImage);
		
		System.out.println("Expected: " + Arrays.toString(b));
		System.out.println("Actual:   " + Arrays.toString(decodedBytes));
		
		assertArrayEquals(b, decodedBytes);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNull() {
		encodeAndAssert(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyArray() {
		encodeAndAssert(new byte[0]);
	}
	
	@Test
	public void testUnderflow() {
		encodeAndAssert(new byte[] {33, 0});
	}
	
	@Test
	public void testSinglePixel() {
		encodeAndAssert(new byte[] {65, 66, 0});
	}
	
	@Test
	public void testBasicTriplet() {
		encodeAndAssert(new byte[] {72, 69, 76, 80, 77, 69, 80, 76, 0});
	}
	
	@Test
	public void testTriplet() {	
		encodeAndAssert(new byte[] {72, 69, 76, 80, 32, 77, 69, 32, 80, 76, 69, 65, 83, 0});
	}
	
	@Test
	public void testLongTriplet() {
		
		byte[] b = new byte[rand(30, 300)];
		
		for(int i = 0; i < b.length; i++)
			b[i] = (byte) rand(32, 126);
		
		b[b.length - 1] = 0;
		
		encodeAndAssert(b);
		
	}
	
	@Test
	public void testLongNotTriplet() {
		
		byte[] b = new byte[rand(30, 300)];
		
		if(b.length % 3 == 0) b = new byte[b.length + 1];
		
		for(int i = 0; i < b.length; i++)
			b[i] = (byte) rand(32, 126);
		
		b[b.length - 1] = 0;
		
		encodeAndAssert(b);
		
	}

}
