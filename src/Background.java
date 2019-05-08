import javafx.scene.paint.Color;

public class Background extends Format4248 {
	private final static int BACKGROUND_START_OFFSET = 0x000958;	
	
	public Background(Color primaryColor, Color secondaryColor) {
		super(BACKGROUND_START_OFFSET, primaryColor, secondaryColor);
	}
	
	public Background() {
		super(BACKGROUND_START_OFFSET);
	}
}
