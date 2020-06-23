package Models;

import javafx.scene.paint.Color;

public class SinglePlayerTopFrame extends Format070707 {
	private final static int TOP_FRAME_START_OFFSET = 0x382F04;

	public SinglePlayerTopFrame(Color primaryColor, Color secondaryColor) {
		super(TOP_FRAME_START_OFFSET, primaryColor, secondaryColor);
	}
	
	public SinglePlayerTopFrame() {
		super(TOP_FRAME_START_OFFSET);
	}
}
