package Models;
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
}
