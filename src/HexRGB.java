import javafx.scene.paint.Color;

public class HexRGB {
	//RR GG BB format
	protected int red;
	protected int green;
	protected int blue;
	
	final int HEX_BASE = 16;
	final int RGB_MAX = 255;
	
	public HexRGB(Color color) {
		String hex = String.format("%02X%02X%02X",
	            (int)(color.getRed() * RGB_MAX),
	            (int)(color.getGreen() * RGB_MAX),
	            (int)(color.getBlue() * RGB_MAX ))
				.toUpperCase();
		
		red = getRedDigits(hex);
		green = getGreenDigits(hex);
		blue = getBlueDigits(hex);
	}
	
	private int getRedDigits(String hex) {
		return Integer.parseInt(hex.substring(0, 2), HEX_BASE);
	}
	
	private int getGreenDigits(String hex) {
		return Integer.parseInt(hex.substring(2, 4), HEX_BASE);
	}
	
	private int getBlueDigits(String hex) {
		return Integer.parseInt(hex.substring(4, 6), HEX_BASE);
	}
}
