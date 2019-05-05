import javafx.scene.paint.Color;

public class Format070707 extends TwoColorFormat {
	public Format070707(int startOffset, Color primaryColor, Color secondaryColor) {
		super(primaryColor, secondaryColor);
		
		primaryColorOffset = getPrimaryColorOffset(startOffset);
		secondaryColorOffset = getSecondaryColorOffset(startOffset);
	}
	
	public Format070707(int startOffset) {
		super();
		
		primaryColorOffset = getPrimaryColorOffset(startOffset);
		secondaryColorOffset = getSecondaryColorOffset(startOffset);
	}
	
	private int getPrimaryColorOffset(int startOffset) {
		return startOffset + 0x000004;
	}
	
	private int getSecondaryColorOffset(int startOffset) {
		return startOffset + 0x000008;
	}
}
