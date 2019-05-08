import javafx.scene.paint.Color;

public class Cursor extends Format070707 {
	private final static int CURSOR_OFFSET = 0x01005C;
	
	public Cursor(Color primaryColor, Color secondaryColor) {
		super(CURSOR_OFFSET, primaryColor, secondaryColor);
	}
	
	public Cursor() {
		super(CURSOR_OFFSET);
	}
}
