import javafx.scene.paint.Color;

public class HexRGB {
	//RR GG BB format
	protected int red;
	protected int green;
	protected int blue;
	final int HEX_BASE = 16;
	
	public HexRGB(Color color) {
		String hex = String.format("#%02X%02X%02X",
	            (int)(color.getRed() * 255),
	            (int)(color.getGreen() * 255),
	            (int)(color.getBlue() * 255 ))
				.toUpperCase();
		
		red = getRedDigits(hex);
		green = getGreenDigits(hex);
		blue = getBlueDigits(hex);
	}
	
	private int getRedDigits(String hex) {
		return Integer.parseInt(hex.substring(1, 3), HEX_BASE);
	}
	
	private int getGreenDigits(String hex) {
		return Integer.parseInt(hex.substring(3, 5), HEX_BASE);
	}
	
	private int getBlueDigits(String hex) {
		return Integer.parseInt(hex.substring(5, 7), HEX_BASE);
	}
}
