import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.paint.Color;

public class Format4248 extends TwoColorFormat {
	public Format4248(int startOffset, Color primaryColor, Color secondaryColor) {
		super(primaryColor, secondaryColor);
		
		this.startOffset = startOffset;
	}
	
	public Format4248(int startOffset) {
		super();
		
		this.startOffset = startOffset;
	}
	
	@Override
	public int getPrimaryColorOffset() {
		return startOffset - 0x000010;
	}
	
	@Override
	public int getSecondaryColorOffset() {
		return startOffset - 0x00000C;
	}
	
	@Override
	public int getTransparencyOffset() {
		return startOffset - 0x000004;
	}
	
	public void writeTransparency(String filename, boolean isTransparent) {
		RandomAccessFile raf = Utility.createRandomAccessFile(filename);
		
		try {
		    raf.seek(getTransparencyOffset());
		    if (isTransparent) {
		    	raf.write(0x00);
		    	raf.write(0x00);
		    	raf.write(0x00);
		    	raf.write(0x00);
		    } else {
		    	raf.write(0x3F);
		    	raf.write(0x6A);
		    	raf.write(0x3D);
		    	raf.write(0x71);
		    }
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			Utility.closeRandomAccessFile(raf);
		}
	}
	
	public static boolean is4248Format(RandomAccessFile raf, int offset) {
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
