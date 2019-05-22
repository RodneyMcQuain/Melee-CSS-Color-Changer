import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.paint.Color;

abstract class TwoColorFormat {
	protected HexRGB primaryColor;
	protected HexRGB secondaryColor;
	protected int startOffset;

	public TwoColorFormat(Color primary, Color secondary) {
		primaryColor = new HexRGB(primary);
		secondaryColor = new HexRGB(secondary);
	}
	
	public TwoColorFormat() {}

	public abstract int getPrimaryColorOffset();
	public abstract int getSecondaryColorOffset();
	public abstract int getTransparencyOffset();
	public abstract void writeTransparency(String filename, boolean isTransparent);
	
	public void writeColors(String filename) {
		writeToFile(filename, getPrimaryColorOffset(), this.primaryColor);
		writeToFile(filename, getSecondaryColorOffset(), this.secondaryColor);
	}
	
	private void writeToFile(String filename, final int writeAddress, HexRGB rgb) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);
		
		try {
		    raf.seek(writeAddress);
		    raf.write(rgb.red);
		    raf.write(rgb.green);
		    raf.write(rgb.blue);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
	}
	
	public Color readColors(String filename, final int readAddress) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);
		
		Color color = null;
		try {
		    raf.seek(readAddress);
		    double red = raf.read() / 255.0;
		    double green = raf.read() / 255.0;
		    double blue = raf.read() / 255.0;
		    color = new Color(red, green, blue, 1);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
		
		return color;
	}
	
	public boolean readTransparency(String filename) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);
		
		try {
		    raf.seek(getTransparencyOffset());
		    if (raf.read() == 0x00 && raf.read() == 0x00 && raf.read() == 0x00 && raf.read() == 0x00)
		    	return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
		
		return false;
	}
}
