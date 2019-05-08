import javafx.scene.paint.Color;

public class Rules extends Format070707 {
	private final static int RULES_START_OFFSET = 0x348F48;			

	public Rules(Color primaryColor, Color secondaryColor) {
		super(RULES_START_OFFSET, primaryColor, secondaryColor);
	}
	
	public Rules() {
		super(RULES_START_OFFSET);
	}
}
