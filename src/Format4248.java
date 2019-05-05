import javafx.scene.paint.Color;

public class Format4248 extends TwoColorFormat {
	public Format4248(int startOffset, Color primaryColor, Color secondaryColor) {
		super(primaryColor, secondaryColor);
		
		primaryColorOffset = getPrimaryColorOffset(startOffset);
		secondaryColorOffset = getSecondaryColorOffset(startOffset);
	}
	
	public Format4248(int startOffset) {
		super();
		
		primaryColorOffset = getPrimaryColorOffset(startOffset);
		secondaryColorOffset = getSecondaryColorOffset(startOffset);
	}
	
	
	private int getPrimaryColorOffset(int startOffset) {
		return startOffset - 0x000010;
	}
	
	private int getSecondaryColorOffset(int startOffset) {
		return startOffset - 0x00000C;
	}
}
