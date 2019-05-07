import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.paint.Color;

public class TwoColorFormat {
	protected HexRGB primaryColor;
	protected HexRGB secondaryColor;
	protected int primaryColorOffset;
	protected int secondaryColorOffset;
	protected int transparencyOffset;

	public TwoColorFormat(Color primary, Color secondary) {
		primaryColor = new HexRGB(primary);
		secondaryColor = new HexRGB(secondary);
	}
	
	public TwoColorFormat() {}
	
	public void writeColors(String filename) {
		writeToFile(filename, this.primaryColorOffset, this.primaryColor);
		writeToFile(filename, this.secondaryColorOffset, this.secondaryColor);
	}
	
	private void writeToFile(String filename, final int writeAddress, HexRGB rgb) {
		RandomAccessFile raf = Utility.createRandomAccessFile(filename);
		
		try {
		    raf.seek(writeAddress);
		    raf.write(rgb.red);
		    raf.write(rgb.green);
		    raf.write(rgb.blue);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	public Color read(String filename, final int readAddress) {
		RandomAccessFile raf = Utility.createRandomAccessFile(filename);
		
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
			try {
				raf.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		return color;
	}
}
