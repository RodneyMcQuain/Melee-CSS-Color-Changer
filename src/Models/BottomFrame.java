package Models;
import javafx.scene.paint.Color;

public class BottomFrame extends Format070707 {
	private final static int BOTTOM_FRAME_START_OFFSET = 0x348E88;

	public BottomFrame(Color primaryColor, Color secondaryColor) {
		super(BOTTOM_FRAME_START_OFFSET, primaryColor, secondaryColor);
	}
	
	public BottomFrame() {
		super(BOTTOM_FRAME_START_OFFSET);
	}
}
