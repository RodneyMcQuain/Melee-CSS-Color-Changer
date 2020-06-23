package Models;

import javafx.scene.paint.Color;

public class SinglePlayerBottomFrame extends Format070707 {
	private final static int BOTTOM_FRAME_START_OFFSET = 0x382E44;

	public SinglePlayerBottomFrame(Color primaryColor, Color secondaryColor) {
		super(BOTTOM_FRAME_START_OFFSET, primaryColor, secondaryColor);
	}
	
	public SinglePlayerBottomFrame() {
		super(BOTTOM_FRAME_START_OFFSET);
	}
}
