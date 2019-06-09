package Models;
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.paint.Color;

public class Background extends Format4248 {
	private final static int BACKGROUND_START_OFFSET = 0x000958;	
	final static int[] BACKGROUND_ORIGINAL_TRANSPARENCY_VALUES = { 0x3F, 0x6A, 0x3D, 0x71 };
	final static int[] BACKGROUND_TRANSPARENT_VALUES = { 0x00, 0x00, 0x00, 0x00 };
	
	public Background(Color primaryColor, Color secondaryColor) {
		super(BACKGROUND_START_OFFSET, primaryColor, secondaryColor);
	}
	
	public Background() {
		super(BACKGROUND_START_OFFSET);
	}
	
	public void writeTransparent(String filename) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);
		
		try {
		    raf.seek(getTransparencyOffset());
		    for (int value : BACKGROUND_TRANSPARENT_VALUES)
		    	raf.write(value);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
	}
	
	public void writeVisible(String filename) {
		RandomAccessFile raf = RandomAccessFileUtility.createRandomAccessFile(filename);
		
		try {
		    raf.seek(getTransparencyOffset());		    
		    for (int value : BACKGROUND_ORIGINAL_TRANSPARENCY_VALUES)
		    	raf.write(value);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			RandomAccessFileUtility.closeRandomAccessFile(raf);
		}
	}
}
