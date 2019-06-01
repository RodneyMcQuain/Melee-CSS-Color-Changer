
public class SelectBackgroundUtility {
	public final static String DO_NOTHING = "Do Nothing";
	public final static String ONE_COLOR = "One Color";
	public final static String ALTERNATE = "Alternate";
	public final static String ALTERNATE_FULL = "Alternate Full";
	public final static String TRI = "Tri";
	public final static String RANDOM = "Random";
	public final static String RANDOM_DIAMOND = "Random Diamond";
	public final static String RANDOM_FULL = "Random Full";
	public final static String TRANSPARENT = "Transparent";
	public final static String VISIBLE = "Visible";
	
	public static void writeByOption(String filename, String option, Format4248[] selectBackgroundColors) {
		HexRGB[] twoColorRgb1 = { selectBackgroundColors[0].primaryColor, selectBackgroundColors[0].secondaryColor };
		HexRGB[] twoColorRgb2 = { selectBackgroundColors[1].primaryColor,  selectBackgroundColors[1].secondaryColor };
		
		switch (option) {
			case SelectBackgroundUtility.RANDOM:
				SelectBackground.writeRandom(filename);
				break;
			case SelectBackgroundUtility.RANDOM_DIAMOND:
				SelectBackground.writeRandomDiamond(filename);
				break;
			case SelectBackgroundUtility.RANDOM_FULL:
				SelectBackground.writeRandomFull(filename);
				break;
			case SelectBackgroundUtility.ONE_COLOR:
				SelectBackground.writeOneColor(filename, twoColorRgb1);
				break;
			case SelectBackgroundUtility.TRANSPARENT:
				SelectBackground.writeTransparent(filename);
				break;
			case SelectBackgroundUtility.VISIBLE:
				SelectBackground.writeVisible(filename);
				break;
			case SelectBackgroundUtility.ALTERNATE:
				SelectBackground.writeAlternate(filename, twoColorRgb1, twoColorRgb2);
				break;
			case SelectBackgroundUtility.ALTERNATE_FULL:
				SelectBackground.writeAlternateFull(filename, twoColorRgb1, twoColorRgb2);
				break;
			case SelectBackgroundUtility.TRI:
				SelectBackground.writeTri(filename, twoColorRgb1, twoColorRgb2);
				break;
		}
	}
}
