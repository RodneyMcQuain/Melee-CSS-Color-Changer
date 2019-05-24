import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.paint.Color;

public class SelectBackground extends Format4248 {
	final static int SELECT_BACKGROUND_OFFSET_START = 0x000984;
	final static int SELECT_BACKGROUND_OFFSET_END = 0x0019D8;
	final static int[] SELECT_BACKGROUND_TRANSPARENCY_OFFSETS = { 0x3E, 0xEE, 0xEE, 0xFA };
	
	public SelectBackground(Color primaryColor, Color secondaryColor) {
		super(SELECT_BACKGROUND_OFFSET_START, primaryColor, secondaryColor);
	}
	
	public SelectBackground() {
		super(SELECT_BACKGROUND_OFFSET_START);
	}
	
	public static void writeTransparent(String filename) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);

		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i)) {
					Format4248 format = new Format4248(i);

					raf.seek(format.getTransparencyOffset());
			    	raf.write(0x00);
			    	raf.write(0x00);
			    	raf.write(0x00);
			    	raf.write(0x00);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
	}
	
	public static void writeVisible(String filename) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);

		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i)) {
					Format4248 format = new Format4248(i);

					raf.seek(format.getTransparencyOffset());
					for (int offset : SELECT_BACKGROUND_TRANSPARENCY_OFFSETS)
						raf.write(offset);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
	}
	
	public static void writeRandom(String filename) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);

		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i)) {
					HexRGB rgb = getRandomColor();
					
					writeSelectBackgroundToFile(raf, i, rgb, rgb);
				}
			}
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
	}
	
	public static void writeRandomFull(String filename) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);
		final int NUMBER_OF_CHARACTERS_IN_SELECT = 6;
		int currentCharacter = 1;
		HexRGB random = getRandomColor();
		
		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i)) {											
					writeSelectBackgroundToFile(raf, i, random, random);
					

					if (currentCharacter == NUMBER_OF_CHARACTERS_IN_SELECT) {
						currentCharacter = 1; //reset
						random = getRandomColor();
					} else {
						currentCharacter++;
					}
				}
			} 
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
	}
	
	public static void writeAlternateFull(String filename, HexRGB[] rgb1, HexRGB[] rgb2) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);
		final int NUMBER_OF_CHARACTERS_IN_SELECT = 6;
		int currentCharacter = 0;
		
		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i)) {											
					currentCharacter++;

					if (currentCharacter <= NUMBER_OF_CHARACTERS_IN_SELECT) {
						writeSelectBackgroundToFile(raf, i, rgb1[0], rgb1[1]);
					} else if (currentCharacter <= NUMBER_OF_CHARACTERS_IN_SELECT * 2) {
						writeSelectBackgroundToFile(raf, i, rgb2[0], rgb2[1]);

						if (currentCharacter == NUMBER_OF_CHARACTERS_IN_SELECT * 2)
							currentCharacter = 0; //reset
					}
				}
			} 
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
	}
	
	public static void writeRandomDiamond(String filename) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);

		int j = 0;
		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i)) {
					HexRGB rgb;
					if (j % 2 == 0)
						rgb = getRandomColor();	
					else
						rgb = new HexRGB(Color.BLACK);
						
					writeSelectBackgroundToFile(raf, i, rgb, rgb);
					j++;
				}
			}
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
	}
	
	public static void writeOneColor(String filename, HexRGB rgb1, HexRGB rgb2) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);

		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i))
					writeSelectBackgroundToFile(raf, i, rgb1, rgb2);
			}
		} finally {
			try {
				raf.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	public static void writeAlternate(String filename, HexRGB[] rgb1, HexRGB[] rgb2) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);

		int j = 0;
		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i)) {
					if (j % 2 == 0)
						writeSelectBackgroundToFile(raf, i, rgb1[0], rgb1[1]);
					else
						writeSelectBackgroundToFile(raf, i, rgb2[0], rgb2[1]);
					
					j++;
				}
			}
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
	}
	
	public static void writeTri(String filename, HexRGB[] rgb1, HexRGB[] rgb2) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);

		int j = 0;
		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i)) {
					if (j % 3 == 0)
						writeSelectBackgroundToFile(raf, i, rgb1[0], rgb1[1]);
					else
						writeSelectBackgroundToFile(raf, i, rgb2[0], rgb2[1]);
					
					j++;
				}
			}
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
	}
	
	private static void writeSelectBackgroundToFile(RandomAccessFile raf, int offset, HexRGB rgb1, HexRGB rgb2) {
		try {
			Format4248 format = new Format4248(offset);

			raf.seek(format.getPrimaryColorOffset());
			raf.write(rgb1.red);
			raf.write(rgb1.green);
			raf.write(rgb1.blue);
	    	
			raf.seek(format.getSecondaryColorOffset());
			raf.write(rgb2.red);
			raf.write(rgb2.green);
			raf.write(rgb2.blue);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static HexRGB getRandomColor() {
		double red = Math.random();
		double green = Math.random();
		double blue = Math.random();
		Color color = new Color (red, green, blue, 1);
		
		return new HexRGB(color);
	}
}
