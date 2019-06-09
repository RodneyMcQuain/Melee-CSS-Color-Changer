package Models;
import javafx.scene.paint.Color;

public class TopFrame extends Format070707 {
	private final static int TOP_FRAME_START_OFFSET = 0x349008;

	public TopFrame(Color primaryColor, Color secondaryColor) {
		super(TOP_FRAME_START_OFFSET, primaryColor, secondaryColor);
	}
	
	public TopFrame() {
		super(TOP_FRAME_START_OFFSET);
	}
}
