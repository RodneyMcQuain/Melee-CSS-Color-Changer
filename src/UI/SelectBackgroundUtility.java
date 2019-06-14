package UI;

import Models.Format4248;
import Models.HexRGB;
import Models.SelectBackground;
import javafx.scene.control.ComboBox;

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
		HexRGB[] twoColorRgb1 = { selectBackgroundColors[0].getPrimaryColor(), selectBackgroundColors[0].getSecondaryColor() };
		HexRGB[] twoColorRgb2 = { selectBackgroundColors[1].getPrimaryColor(),  selectBackgroundColors[1].getSecondaryColor() };
		
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
	
	public static void setComboBoxOptions(ComboBox<String> cb) {
		cb.getItems().addAll(
			SelectBackgroundUtility.DO_NOTHING,
			SelectBackgroundUtility.TRANSPARENT,
			SelectBackgroundUtility.VISIBLE,
			SelectBackgroundUtility.RANDOM,
			SelectBackgroundUtility.RANDOM_DIAMOND,
			SelectBackgroundUtility.RANDOM_FULL,
			SelectBackgroundUtility.ONE_COLOR,
			SelectBackgroundUtility.ALTERNATE,
			SelectBackgroundUtility.ALTERNATE_FULL,
			SelectBackgroundUtility.TRI
		);
		cb.setValue(SelectBackgroundUtility.DO_NOTHING);
	}
}
