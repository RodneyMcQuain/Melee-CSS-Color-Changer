
import java.util.Optional;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {
	public final static int PADDING = 10;
	public final static String SPECIFY_COLOR = "Specify Color";
	public final static int INVALID_FILE_STAGE_HEIGHT = 75;

	private final static String APP_VERSION = "1.0";
	private final static String TITLE = "Melee CSS Color Changer - v" + APP_VERSION;
	public final static int STAGE_WIDTH = 550;
	
	private static boolean isUnsaved = false;
	
	private static Stage stage;
	private BorderPane mainBorderPane;
	private GridPane gridPaneTop;
	private GridPane gridPaneCenter;
	private GridPane gridPaneBottom;

	private ColorPicker[] cpTopFrame;
	private ColorPicker[] cpBottomFrame;
	private ColorPicker[] cpRules;
	private ColorPicker[] cpBackground;
	private ColorPicker[] cpCursor;
	private ColorPicker[] cpSelectBackground1;
	private ColorPicker[] cpSelectBackground2;

	private Button btUpdateFile;
	private TextField tfSourceFile;
	
	private ComboBox<String> cbBackgroundOptions;
	private ComboBox<String> cbSelectBackgroundOptions;

	private Label lblSuccess;
	
	private SequentialTransition fadeAll;
	
	public static void main(String[] args) {
		DefaultDirectory.createSettings();
        Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
    	stage = primaryStage;
		Scene mainMenu = createMainMenu();
    	
		stage.setTitle(TITLE);
		stage.setScene(mainMenu);
	    stage.setOnCloseRequest(e -> verifyAppClose(e));
		stage.show();
    }

	private void verifyAppClose(WindowEvent e) {
		if (isUnsaved) {
			if (!unsavedConfirmationAlert("close the application"))
				e.consume(); //stops the app from closing
		}
	}
	
	private Scene createMainMenu() {
		mainBorderPane = new BorderPane();
    	Scene mainMenu =  new Scene(mainBorderPane, STAGE_WIDTH, INVALID_FILE_STAGE_HEIGHT);
    	
    	initializeUIComponents();
    	createTopPane(gridPaneTop);
    	createCenterPane(gridPaneCenter);
    	createBottomPane(gridPaneBottom);
    	mainBorderPane.setTop(gridPaneTop);

		btUpdateFile.setOnAction(e -> writeToFile());
		
		return mainMenu;
	}
	
	private void initializeUIComponents() {		
		gridPaneTop = new GridPane();
		gridPaneCenter = new GridPane();
    	gridPaneBottom = new GridPane();
		
    	tfSourceFile = new TextField();

    	cbBackgroundOptions = new ComboBox<String>();
		cbSelectBackgroundOptions = new ComboBox<String>();
		initializeColorPickers();
		
    	btUpdateFile = new Button("Update File");
	}
	
	private void initializeColorPickers() {
		cpTopFrame = createTwoColorPicker();
		cpBottomFrame = createTwoColorPicker();
		cpRules = createTwoColorPicker();
		cpBackground = createTwoColorPicker();
		cpCursor = createTwoColorPicker();
		cpSelectBackground1 = createTwoColorPicker();
		cpSelectBackground2 = createTwoColorPicker();
	}

	private void createTopPane(GridPane gp) {
		MainMenuTopPane topPane = new MainMenuTopPane(
			stage,
			mainBorderPane,
			gp,
			gridPaneCenter,
			gridPaneBottom,
			btUpdateFile,
			tfSourceFile,
			cbBackgroundOptions,
			cpTopFrame,
			cpBottomFrame,
			cpRules,
			cpBackground,
			cpCursor
		);
		
		topPane.createTopPane();
	}
	
	private void createCenterPane(GridPane gp) {
		MainMenuCenterPane centerPane = new MainMenuCenterPane(
			gp,
			cbBackgroundOptions,
			cbSelectBackgroundOptions,
			cpTopFrame,
			cpBottomFrame,
			cpRules,
			cpBackground,
			cpCursor,
			cpSelectBackground1,
			cpSelectBackground2
		);
		
		centerPane.createCenterPane();
	}
	
	
    private ColorPicker[] createTwoColorPicker() {
    	ColorPicker[] cps = { new ColorPicker(), new ColorPicker() };
    	return cps;
	}
	
	private void createBottomPane(GridPane gp) {
    	setHVGap(gp);
    	gp.setPadding(new Insets(0, PADDING, PADDING, PADDING));
    	
    	btUpdateFile.setDisable(true);
    	
    	lblSuccess = new Label("Successful Update");    	
    	lblSuccess.setTextFill(Color.GREEN);
    	lblSuccess.setVisible(false);
    	initializeFadeTransition();
    	
    	gp.add(btUpdateFile, 0, 1);
    	gp.add(lblSuccess, 1, 1);
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
	
	public static void setHVGap(GridPane gp) {
    	gp.setHgap(PADDING);
    	gp.setVgap(PADDING);
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
		setSaved();
	}
	
	private void displaySuccess() {
		if(!lblSuccess.isVisible()) {
			lblSuccess.setVisible(true);
	        fadeAll.playFromStart();
		}
	}
	
	public static void setSaved() {
		isUnsaved = false;
		stage.setTitle(TITLE);
	}
	
	public static void setUnsaved() {
		isUnsaved = true;
		stage.setTitle(TITLE + " *");
	}
	
	public static boolean getIsUnsaved() {
		return isUnsaved;
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
	
	public static boolean unsavedConfirmationAlert(String action) {
		Alert closeApp = new Alert(AlertType.CONFIRMATION);
		closeApp.setTitle("Close Application");
		closeApp.setHeaderText(null);
		closeApp.setContentText("You have unsaved changes are you sure you want to " + action + "?");
		Optional<ButtonType> optionSelected = closeApp.showAndWait();
		if (optionSelected.get() == ButtonType.CANCEL)
			return false;
		
		return true;
	}
	
	private TwoColor getTwoColor(ColorPicker[] cp) {
		return new TwoColor(cp[0].getValue(), cp[1].getValue());
	}
}
