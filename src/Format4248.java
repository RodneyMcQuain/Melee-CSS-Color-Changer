import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

import javafx.scene.paint.Color;

public class Format4248 extends TwoColorFormat {
	final static int BACKGROUND_SELECT_OFFSET_START = 0x000984;
	final static int BACKGROUND_SELECT_OFFSET_END = 0x0019D8;
	
	public Format4248(int startOffset, Color primaryColor, Color secondaryColor) {
		super(primaryColor, secondaryColor);
		
		primaryColorOffset = getPrimaryColorOffset(startOffset);
		secondaryColorOffset = getSecondaryColorOffset(startOffset);
		transparencyOffset = getTransparencyOffset(startOffset);
	}
	
	public Format4248(int startOffset) {
		super();
		
		primaryColorOffset = getPrimaryColorOffset(startOffset);
		secondaryColorOffset = getSecondaryColorOffset(startOffset);
		transparencyOffset = getTransparencyOffset(startOffset);
	}
	
	
	private int getPrimaryColorOffset(int startOffset) {
		return startOffset - 0x000010;
	}
	
	private int getSecondaryColorOffset(int startOffset) {
		return startOffset - 0x00000C;
	}
	
	private int getTransparencyOffset(int startOffset) {
		return startOffset - 0x000004;
	}
	
	public static boolean writeTransparentSelectBackgroundColor(String filename) {
		RandomAccessFile raf = null;

		try {
			raf = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			for (int i = BACKGROUND_SELECT_OFFSET_START; i <= BACKGROUND_SELECT_OFFSET_END; i += 0x1) {
				if (is4248Format(raf, i)) {
					Format4248 format = new Format4248(i);

					raf.seek(format.transparencyOffset);
			    	raf.write(0x00);
			    	raf.write(0x00);
			    	raf.write(0x00);
			    	raf.write(0x00);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				raf.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		return true;
	}
	
	public static boolean writeVisibleSelectBackgroundColor(String filename) {
		RandomAccessFile raf = null;

		try {
			raf = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			for (int i = BACKGROUND_SELECT_OFFSET_START; i <= BACKGROUND_SELECT_OFFSET_END; i += 0x1) {
				if (is4248Format(raf, i)) {
					Format4248 format = new Format4248(i);

					raf.seek(format.transparencyOffset);
			    	raf.write(0x3E);
			    	raf.write(0xEE);
			    	raf.write(0xEE);
			    	raf.write(0xFA);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				raf.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		return true;
	}
	
	public static boolean writeRandomSelectBackgroundColor(String filename) {
		RandomAccessFile raf = null;

		try {
			raf = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		boolean isSuccess = true;
		try {
			for (int i = BACKGROUND_SELECT_OFFSET_START; i <= BACKGROUND_SELECT_OFFSET_END; i += 0x1) {
				if (is4248Format(raf, i)) {
					double red = Math.random();
					double green = Math.random();
					double blue = Math.random();
					Color color = new Color (red, green, blue, 1);
					
					HexRGB rgb = new HexRGB(color);
					
					isSuccess = writeSelectBackgroundToFile(raf, i, rgb, rgb);
				}
				
				if (!isSuccess)
					return false;
			}
		} finally {
			try {
				raf.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		return true;
	}
	
	public static boolean writeAllSelectBackgroundColor(String filename, HexRGB rgb1, HexRGB rgb2) {
		RandomAccessFile raf = null;

		try {
			raf = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		boolean isSuccess = true;
		try {
			for (int i = BACKGROUND_SELECT_OFFSET_START; i <= BACKGROUND_SELECT_OFFSET_END; i += 0x1) {
				if (is4248Format(raf, i))
					isSuccess = writeSelectBackgroundToFile(raf, i, rgb1, rgb2);
				if (!isSuccess)
					return false;
			}
		} finally {
			try {
				raf.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		return true;
	}
	
	public static boolean writeAlternateSelectBackgroundColor(String filename, HexRGB[] rgb1, HexRGB[] rgb2) {
		RandomAccessFile raf = null;

		try {
			raf = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int j = 0;
		boolean isSuccess = true;
		try {
			for (int i = BACKGROUND_SELECT_OFFSET_START; i <= BACKGROUND_SELECT_OFFSET_END; i += 0x1) {
				if (is4248Format(raf, i)) {
					if (j % 2 == 0)
						isSuccess = writeSelectBackgroundToFile(raf, i, rgb1[0], rgb1[1]);
					else
						isSuccess = writeSelectBackgroundToFile(raf, i, rgb2[0], rgb2[1]);
					
					j++;
				}
				
				if (!isSuccess)
					return false;
			}
		} finally {
			try {
				raf.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		return true;
	}
	
	private static boolean writeSelectBackgroundToFile(RandomAccessFile raf, int offset, HexRGB rgb1, HexRGB rgb2) {
		try {
			raf.seek(offset);
			Format4248 format = new Format4248(offset);

			raf.seek(format.primaryColorOffset);
	    	raf.write(rgb1.red);
	    	raf.write(rgb1.green);
	    	raf.write(rgb1.blue);
	    	
			raf.seek(format.secondaryColorOffset);
	    	raf.write(rgb2.red);
	    	raf.write(rgb2.green);
	    	raf.write(rgb2.blue);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private static boolean is4248Format(RandomAccessFile raf, int offset) {
		try {
			raf.seek(offset);

			if (raf.read() == 0x42 && raf.read() == 0x48)
				return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
