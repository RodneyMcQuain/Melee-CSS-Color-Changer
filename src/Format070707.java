import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.paint.Color;

public class Format070707 extends TwoColorFormat {
	public Format070707(int startOffset, Color primaryColor, Color secondaryColor) {
		super(primaryColor, secondaryColor);
		
		primaryColorOffset = getPrimaryColorOffset(startOffset);
		secondaryColorOffset = getSecondaryColorOffset(startOffset);
		transparencyOffset = getTransparencyOffset(startOffset);
	}
	
	public Format070707(int startOffset) {
		super();
		
		primaryColorOffset = getPrimaryColorOffset(startOffset);
		secondaryColorOffset = getSecondaryColorOffset(startOffset);
		transparencyOffset = getTransparencyOffset(startOffset);
	}
	
	private int getPrimaryColorOffset(int startOffset) {
		return startOffset + 0x000004;
	}
	
	private int getSecondaryColorOffset(int startOffset) {
		return startOffset + 0x000008;
	}
	
	private int getTransparencyOffset(int startOffset) {
		return startOffset + 0x000003;
	}
	
	@Override
	public void writeTransparency(String filename, boolean isTransparent) {
		RandomAccessFile raf = Utility.createRandomAccessFile(filename);
		
		try {
		    raf.seek(transparencyOffset);
		    if (isTransparent)
		    	raf.write(0x00);
		    else
		    	raf.write(0x07);
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
}
