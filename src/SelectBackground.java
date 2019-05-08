import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.paint.Color;

public class SelectBackground extends Format4248 {
	final static int SELECT_BACKGROUND_OFFSET_START = 0x000984;
	final static int SELECT_BACKGROUND_OFFSET_END = 0x0019D8;

	public SelectBackground(Color primaryColor, Color secondaryColor) {
		super(SELECT_BACKGROUND_OFFSET_START, primaryColor, secondaryColor);
	}
	
	public SelectBackground() {
		super(SELECT_BACKGROUND_OFFSET_START);
	}
	
	public static boolean writeTransparentSelectBackgroundColor(String filename) {
		RandomAccessFile raf = Utility.createRandomAccessFile(filename);

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
			return false;
		} finally {
			Utility.closeRandomAccessFile(raf);
		}
		
		return true;
	}
	
	public static boolean writeVisibleSelectBackgroundColor(String filename) {
		RandomAccessFile raf = Utility.createRandomAccessFile(filename);

		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i)) {
					Format4248 format = new Format4248(i);

					raf.seek(format.getTransparencyOffset());
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
			Utility.closeRandomAccessFile(raf);
		}
		
		return true;
	}
	
	public static boolean writeRandomSelectBackgroundColor(String filename) {
		RandomAccessFile raf = Utility.createRandomAccessFile(filename);

		boolean isSuccess = true;
		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i)) {
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
			Utility.closeRandomAccessFile(raf);
		}
		
		return true;
	}
	
	public static boolean writeAllSelectBackgroundColor(String filename, HexRGB rgb1, HexRGB rgb2) {
		RandomAccessFile raf = Utility.createRandomAccessFile(filename);

		boolean isSuccess = true;
		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i))
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
		RandomAccessFile raf = Utility.createRandomAccessFile(filename);

		int j = 0;
		boolean isSuccess = true;
		try {
			for (int i = SELECT_BACKGROUND_OFFSET_START; i <= SELECT_BACKGROUND_OFFSET_END; i += 0x1) {
				if (Format4248.is4248Format(raf, i)) {
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
			Utility.closeRandomAccessFile(raf);
		}
		
		return true;
	}
	
	private static boolean writeSelectBackgroundToFile(RandomAccessFile raf, int offset, HexRGB rgb1, HexRGB rgb2) {
		try {
			raf.seek(offset);
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
			return false;
		}
		
		return true;
	}
}
