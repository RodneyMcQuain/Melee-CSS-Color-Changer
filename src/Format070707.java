import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.paint.Color;

public class Format070707 extends TwoColorFormat {
	public Format070707(int startOffset, Color primaryColor, Color secondaryColor) {
		super(primaryColor, secondaryColor);
		
		this.startOffset = startOffset;
	}
	
	public Format070707(int startOffset) {
		super();

		this.startOffset = startOffset;
	}
	
	@Override
	public int getPrimaryColorOffset() {
		return startOffset + 0x000004;
	}
	
	@Override
	public int getSecondaryColorOffset() {
		return startOffset + 0x000008;
	}
	
	@Override
	public int getTransparencyOffset() {
		return startOffset + 0x000003;
	}
	
	@Override
	public void writeTransparency(String filename, boolean isTransparent) {
		RandomAccessFile raf = Utility.createRandomAccessFile(filename);
		
		try {
		    raf.seek(getTransparencyOffset());
		    if (isTransparent)
		    	raf.write(0x00);
		    else
		    	raf.write(0x07);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			Utility.closeRandomAccessFile(raf);
		}
	}
}
