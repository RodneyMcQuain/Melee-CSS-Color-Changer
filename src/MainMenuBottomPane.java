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
	private Button btUpdateFile;
	private TextField tfSourceFile;
	
	private Label lblSuccess;
	private SequentialTransition fadeAll;
	
	private ComboBox<String> cbBackgroundOptions;
	private ComboBox<String> cbSelectBackgroundOptions;
	
	private ColorPicker[] cpTopFrame;
	private ColorPicker[] cpBottomFrame;
	private ColorPicker[] cpRules;
	private ColorPicker[] cpBackground;
	private ColorPicker[] cpCursor;
	private ColorPicker[] cpSelectBackground1;
	private ColorPicker[] cpSelectBackground2;
	
	public MainMenuBottomPane(
		GridPane gp,
		Button btUpdateFile,
		TextField tfSourceFile,
		ComboBox<String> cbBackgroundOptions,
		ComboBox<String> cbSelectBackgroundOptions,
		ColorPicker[] cpTopFrame,
		ColorPicker[] cpBottomFrame,
		ColorPicker[] cpRules,
		ColorPicker[] cpBackground,
		ColorPicker[] cpCursor,
		ColorPicker[] cpSelectBackground1,
		ColorPicker[] cpSelectBackground2
	) {
		this.gp = gp;
		this.btUpdateFile = btUpdateFile;
		this.tfSourceFile = tfSourceFile;
		
		this.cpTopFrame = cpTopFrame;
		this.cpBottomFrame = cpBottomFrame;
		this.cpRules = cpRules;
		this.cpBackground = cpBackground;
		this.cpCursor = cpCursor;
		this.cpSelectBackground1 = cpSelectBackground1;
		this.cpSelectBackground2 = cpSelectBackground2;
		
		this.cbBackgroundOptions = cbBackgroundOptions;
		this.cbSelectBackgroundOptions = cbSelectBackgroundOptions;
	}
	
	public void createBottomPane() {
    	App.setHVGap(gp);
    	gp.setPadding(new Insets(0, App.PADDING, App.PADDING, App.PADDING));
    	
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
		final int TRANSITION_SECONDS = 2;
		final int WAIT_SECONDS = 5;
		final int MAX_OPACITY_VALUE = 1;
		
		FadeTransition fadeIn = new FadeTransition(Duration.seconds(TRANSITION_SECONDS));
		fadeIn.setFromValue(0);
		fadeIn.setToValue(MAX_OPACITY_VALUE);
	    fadeIn.setAutoReverse(true);
		
	    FadeTransition fadeOut = new FadeTransition(Duration.seconds(TRANSITION_SECONDS));
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
		String filename = tfSourceFile.getText();

		updateSelectBackgroundFile(filename, cbSelectBackgroundOptions.getValue());
		
		updateBackground(filename, cpBackground, cbBackgroundOptions);
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
		Format070707 topFrame = new TopFrame(twoColor.primaryColor, twoColor.secondaryColor);		
		topFrame.writeColors(filename);
	}
	
	private void updateBottomFrame(String filename, ColorPicker[] cpBottomFrames) {
		TwoColor twoColor = getTwoColor(cpBottomFrames);
		Format070707 bottomFrame = new BottomFrame(twoColor.primaryColor, twoColor.secondaryColor);
		bottomFrame.writeColors(filename);
	}
	
	private void updateRules(String filename, ColorPicker[] cpRules) {
		TwoColor twoColor = getTwoColor(cpRules);
		Format070707 rules = new Rules(twoColor.primaryColor, twoColor.secondaryColor);
		rules.writeColors(filename);
	}
	
	private void updateCursor(String filename, ColorPicker[] cpCursor) {
		TwoColor twoColor = getTwoColor(cpCursor);
		Format070707 cursor = new Cursor(twoColor.primaryColor, twoColor.secondaryColor);
		cursor.writeColors(filename);
	}
	
	private void updateBackground(String filename, ColorPicker[] cpBackgrounds, ComboBox<String> cbBackgroundOptions) {
		TwoColor twoColor = getTwoColor(cpBackgrounds);
		Background background = new Background(twoColor.primaryColor, twoColor.secondaryColor);	
		boolean isTransparent = cbBackgroundOptions.getValue() == SelectBackgroundUtility.TRANSPARENT ? true : false;
		
		background.writeColors(filename);
		if (isTransparent)
			background.writeTransparent(filename);
		else
			background.writeVisible(filename);
	}
	
	private void updateSelectBackgroundFile(String filename, String selectBackgroundOption) {
		TwoColor twoColor1 = getTwoColor(cpSelectBackground1);
		TwoColor twoColor2 = getTwoColor(cpSelectBackground2);
		Format4248 selectBackground1 = new SelectBackground(twoColor1.primaryColor, twoColor1.secondaryColor);
		Format4248 selectBackground2 = new SelectBackground(twoColor2.primaryColor, twoColor2.secondaryColor);
		Format4248[] selectBackgroundColors = { selectBackground1, selectBackground2 };
		
		SelectBackgroundUtility.writeByOption(filename, selectBackgroundOption, selectBackgroundColors);
	}
	
	private TwoColor getTwoColor(ColorPicker[] cp) {
		return new TwoColor(cp[0].getValue(), cp[1].getValue());
	}
}
