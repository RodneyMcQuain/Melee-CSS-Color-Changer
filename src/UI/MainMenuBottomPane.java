package UI;
import Models.Background;
import Models.BottomFrame;
import Models.Cursor;
import Models.Format070707;
import Models.Format4248;
import Models.Rules;
import Models.SelectBackground;
import Models.TopFrame;
import Models.TwoColor;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MainMenuBottomPane {
	private GridPane gp;
	
	private SharedUIElements sharedElements;
	
	private Label lblSuccess;
	private SequentialTransition fadeAll;

	public MainMenuBottomPane(GridPane gp, SharedUIElements sharedElements) {
		this.gp = gp;
		this.sharedElements = sharedElements;
	}
	
	public void createBottomPane() {
    	App.setHVGap(gp);
    	gp.setPadding(new Insets(0, App.PADDING, App.PADDING, App.PADDING));
    	Button btUpdateFile = sharedElements.getBtUpdateFile();
    	
    	btUpdateFile.setDisable(true);
    	
    	lblSuccess = new Label("Successful Update");    	
    	lblSuccess.setTextFill(Color.GREEN);
    	lblSuccess.setVisible(false);
    	initializeFadeTransition();
    	
    	gp.add(btUpdateFile, 0, 1);
    	gp.add(lblSuccess, 1, 1);
    	
		btUpdateFile.setOnAction(e -> writeToFile());
	}
	
	private void initializeFadeTransition() {
		final double IN_TRANSITION_SECONDS = .5;
		final int OUT_TRANSITION_SECONDS = 2;
		final int WAIT_SECONDS = 5;
		final int MAX_OPACITY_VALUE = 1;
		
		FadeTransition fadeIn = new FadeTransition(Duration.seconds(IN_TRANSITION_SECONDS));
		fadeIn.setFromValue(0);
		fadeIn.setToValue(MAX_OPACITY_VALUE);
	    fadeIn.setAutoReverse(true);
		
	    FadeTransition fadeOut = new FadeTransition(Duration.seconds(OUT_TRANSITION_SECONDS));
		fadeOut.setDelay(Duration.seconds(WAIT_SECONDS));
		fadeOut.setFromValue(MAX_OPACITY_VALUE);
		fadeOut.setToValue(0);
		fadeOut.setAutoReverse(true);
        
		fadeAll = new SequentialTransition(
    		lblSuccess,
    		fadeIn,
    		fadeOut
		);
        fadeAll.setOnFinished(e -> lblSuccess.setVisible(false));
	}
	
	private void writeToFile() {
		ComboBox<String> cbSelectBackgroundOptions = sharedElements.getCbSelectBackgroundOptions();
		ComboBox<String> cbBackgroundOptions = sharedElements.getCbBackgroundOptions();
		TextField tfSourceFile = sharedElements.getTfSourceFile();
		ColorPicker[] cpBackground = sharedElements.getCpBackground();
		ColorPicker[] cpTopFrame = sharedElements.getCpTopFrame();
		ColorPicker[] cpBottomFrame = sharedElements.getCpBottomFrame();
		ColorPicker[] cpRules = sharedElements.getCpRules();
		ColorPicker[] cpCursor = sharedElements.getCpCursor();
		ColorPicker[] cpSelectBackground1 = sharedElements.getCpSelectBackground1();
		ColorPicker[] cpSelectBackground2 = sharedElements.getCpSelectBackground2();

		String filename = tfSourceFile.getText();

		updateSelectBackgroundFile(
			filename, 
			cpSelectBackground1, 
			cpSelectBackground2, 
			cbSelectBackgroundOptions.getValue()
		);
		
		updateBackground(filename, cpBackground, cbBackgroundOptions.getValue());
		updateTopFrame(filename, cpTopFrame);
		updateBottomFrame(filename, cpBottomFrame);
		updateRules(filename, cpRules);
		updateCursor(filename, cpCursor);
		
		displaySuccess();
		App.setSaved();
	}
	
	private void displaySuccess() {
		if(!lblSuccess.isVisible()) {
			lblSuccess.setVisible(true);
	        fadeAll.playFromStart();
		}
	}
	
	private void updateTopFrame(String filename, ColorPicker[] cpTopFrames) {
		TwoColor twoColor = getTwoColor(cpTopFrames);
		Format070707 topFrame = new TopFrame(twoColor.getPrimaryColor(), twoColor.getSecondaryColor());		
		topFrame.writeColors(filename);
	}
	
	private void updateBottomFrame(String filename, ColorPicker[] cpBottomFrames) {
		TwoColor twoColor = getTwoColor(cpBottomFrames);
		Format070707 bottomFrame = new BottomFrame(twoColor.getPrimaryColor(), twoColor.getSecondaryColor());
		bottomFrame.writeColors(filename);
	}
	
	private void updateRules(String filename, ColorPicker[] cpRules) {
		TwoColor twoColor = getTwoColor(cpRules);
		Format070707 rules = new Rules(twoColor.getPrimaryColor(), twoColor.getSecondaryColor());
		rules.writeColors(filename);
	}
	
	private void updateCursor(String filename, ColorPicker[] cpCursor) {
		TwoColor twoColor = getTwoColor(cpCursor);
		Format070707 cursor = new Cursor(twoColor.getPrimaryColor(), twoColor.getSecondaryColor());
		cursor.writeColors(filename);
	}
	
	private void updateBackground(String filename, ColorPicker[] cpBackgrounds, String selectBackgroundOption) {
		TwoColor twoColor = getTwoColor(cpBackgrounds);
		Background background = new Background(twoColor.getPrimaryColor(), twoColor.getSecondaryColor());	
		boolean isTransparent = selectBackgroundOption == SelectBackgroundUtility.TRANSPARENT ? true : false;
		
		background.writeColors(filename);
		if (isTransparent)
			background.writeTransparent(filename);
		else
			background.writeVisible(filename);
	}
	
	private void updateSelectBackgroundFile(
		String filename, 
		ColorPicker[] cpSelectBackground1, 
		ColorPicker[] cpSelectBackground2, 
		String selectBackgroundOption
	) {
		TwoColor twoColor1 = getTwoColor(cpSelectBackground1);
		TwoColor twoColor2 = getTwoColor(cpSelectBackground2);
		Format4248 selectBackground1 = new SelectBackground(twoColor1.getPrimaryColor(), twoColor1.getSecondaryColor());
		Format4248 selectBackground2 = new SelectBackground(twoColor2.getPrimaryColor(), twoColor2.getSecondaryColor());
		Format4248[] selectBackgroundColors = { selectBackground1, selectBackground2 };
		
		SelectBackgroundUtility.writeByOption(filename, selectBackgroundOption, selectBackgroundColors);
	}
	
	private TwoColor getTwoColor(ColorPicker[] cp) {
		return new TwoColor(cp[0].getValue(), cp[1].getValue());
	}
}
