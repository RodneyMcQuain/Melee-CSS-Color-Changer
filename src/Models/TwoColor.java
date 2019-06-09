package Models;
import javafx.scene.paint.Color;

public class TwoColor {
	private Color primaryColor;
	private Color secondaryColor;
	
	public TwoColor(Color primaryColor, Color secondaryColor) {
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
	}

	public Color getPrimaryColor() {
		return primaryColor;
	}

	public Color getSecondaryColor() {
		return secondaryColor;
	}
}
