import javafx.scene.paint.Color;

public class HexRGB {
	protected int red;
	protected int green;
	protected int blue;
	
	public HexRGB(Color color) {
		String hex = String.format("#%02X%02X%02X",
	            (int)(color.getRed() * 255),
	            (int)(color.getGreen() * 255),
	            (int)(color.getBlue() * 255 ))
				.toUpperCase();
		
		red = Integer.parseInt(hex.substring(1, 3), 16);
		green = Integer.parseInt(hex.substring(3, 5), 16);
		blue = Integer.parseInt(hex.substring(5, 7), 16);
	}
}
