
import java.io.File;
import java.util.Optional;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.HPos;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {
	public final static int PADDING = 10;
	public final static String SPECIFY_COLOR = "Specify Color";

	private final static String APP_VERSION = "1.0";
	private final static String TITLE = "Melee CSS Color Changer - v" + APP_VERSION;
	private final int STAGE_WIDTH = 550;
	private final int INVALID_FILE_STAGE_HEIGHT = 75;
	
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

	private FileChooser fcSourceFile;
	private Button btUpdateFile;
	private Button btChooseFile;
	private TextField tfSourceFile;
	
	private ComboBox<String> cbBackgroundOptions;
	private ComboBox<String> cbSelectBackgroundOptions;

	private Label lblProvideValidFile;
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
    	
    	gridPaneTop = new GridPane();
		gridPaneCenter = new GridPane();
    	gridPaneBottom = new GridPane();
    	createTopPane(gridPaneTop);
    	createCenterPane(gridPaneCenter);
    	createBottomPane(gridPaneBottom);
    	mainBorderPane.setTop(gridPaneTop);

		tfSourceFile.textProperty().addListener(e -> setUIByFileValidity());
    	btChooseFile.setOnAction(e -> chooseFile());
		btUpdateFile.setOnAction(e -> writeToFile());
		
		return mainMenu;
	}
	
	private void createTopPane(GridPane gp) {
    	setHVGap(gp);
    	gp.setPadding(new Insets(PADDING, PADDING, 0, PADDING));

    	lblProvideValidFile = new Label("Please provide a valid file.");
    	lblProvideValidFile.setTextFill(Color.RED);
    	GridPane.setHalignment(lblProvideValidFile, HPos.CENTER);
    	
    	btChooseFile = new Button("Choose a File to Modify");
    	tfSourceFile = new TextField();
    	tfSourceFile.setPrefWidth(STAGE_WIDTH - 175);
    	fcSourceFile = new FileChooser();
    	formatFileChooserForUsdFiles(fcSourceFile);
    	setInitialDirectory(fcSourceFile);

    	gp.add(lblProvideValidFile, 0, 0, 2, 1);
    	gp.add(tfSourceFile, 0, 1);
    	gp.add(btChooseFile, 1, 1);
	}
	
	private void createCenterPane(GridPane gp) {
		cbBackgroundOptions = new ComboBox<String>();
		cbSelectBackgroundOptions = new ComboBox<String>();
		initializeColorPickers();
		
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
		
		centerPane.createCenterPane(gp);
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
	
    private ColorPicker[] createTwoColorPicker() {
    	ColorPicker[] cps = { new ColorPicker(), new ColorPicker() };
    	return cps;
	}
	
	private void createBottomPane(GridPane gp) {
    	setHVGap(gp);
    	gp.setPadding(new Insets(0, PADDING, PADDING, PADDING));
    	
    	btUpdateFile = new Button("Update File");
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
	
	private void formatFileChooserForUsdFiles(FileChooser fc) {
		fc.setTitle("Open MnSlChr.*sd File");
		fc.getExtensionFilters().addAll(
			new ExtensionFilter(".usd, .0sd, etc. Files", "*.*sd")
		);
	}
	
	private void setInitialDirectory(FileChooser fc) {
    	String defaultDirectory = DefaultDirectory.deserialize();
    	fc.setInitialDirectory(new File(defaultDirectory));
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
	
	private void setSaved() {
		isUnsaved = false;
		stage.setTitle(TITLE);
	}
	
	public static void setUnsaved() {
		isUnsaved = true;
		stage.setTitle(TITLE + " *");
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
	
	private void setUIByFileValidity() {
		String filename = tfSourceFile.getText();
		File file = new File(filename);
		gridPaneTop.getChildren().remove(lblProvideValidFile);
		
		if (isValidFile(file)) {
			setUIByOffsets(filename);

			setValidFileUI();
		} else {
			gridPaneTop.getChildren().add(lblProvideValidFile);
			
			setInvalidFileUI();
		}
	}
	
	private boolean isValidFile(File file) {
		return file != null
				&& file.exists()
				&& file.getName().matches("^(.*sd)");
	}
	
	private void setValidFileUI() {
		final int VALID_FILE_STAGE_HEIGHT = 460;
		stage.setHeight(VALID_FILE_STAGE_HEIGHT);
		btUpdateFile.setDisable(false);
		mainBorderPane.setCenter(gridPaneCenter);
    	mainBorderPane.setBottom(gridPaneBottom);
	}
	
	private void setInvalidFileUI() {
		stage.setHeight(INVALID_FILE_STAGE_HEIGHT + 40);
		btUpdateFile.setDisable(true);
		mainBorderPane.setCenter(null);
    	mainBorderPane.setBottom(null);
	}
	
	private void setUIByOffsets(String filename) {
		Format070707 topFrame = new TopFrame();
		Format070707 bottomFrame = new BottomFrame();
		Format070707 rules = new Rules();
		Format4248 background = new Background();
		Format070707 cursor = new Cursor();

		// Read offsets and set UI elements
		setColorPickerColor(filename, cpTopFrame, topFrame);
		setColorPickerColor(filename, cpBottomFrame, bottomFrame);
		setColorPickerColor(filename, cpRules, rules);
		setColorPickerColor(filename, cpBackground, background);
		setColorPickerColor(filename, cpCursor, cursor);
		setBackgroundComboBox(filename, cbBackgroundOptions, background);
	}
	
	private void setColorPickerColor(String filename, ColorPicker[] cp, TwoColorFormat f) {
		Color color1 = f.readColors(filename, f.getPrimaryColorOffset());
		cp[0].setValue(color1);	
		
		Color color2 = f.readColors(filename, f.getSecondaryColorOffset());
		cp[1].setValue(color2);	
	}
	
	private void setBackgroundComboBox(String filename, ComboBox<String> cb, Format4248 f) {
		boolean isTransparent = f.isTransparent(filename);
		
		if (isTransparent) {
			cb.setValue(SelectBackgroundUtility.TRANSPARENT);
			gridPaneCenter.getChildren().remove(cpBackground[0]);
			gridPaneCenter.getChildren().remove(cpBackground[1]);
			gridPaneCenter.getChildren().remove(cbBackgroundOptions);
			gridPaneCenter.add(cbBackgroundOptions, 1, 4);		
		} else {
			cb.setValue(SPECIFY_COLOR);
		}
	}
    	
	private void chooseFile() {
		if (!isFileChange())
			return;
		
		File file = fcSourceFile.showOpenDialog(mainBorderPane.getScene().getWindow());
		
		if (file != null) {
    		String filename = file.toString();
    		tfSourceFile.setText(filename);
    		
    		new DefaultDirectory(filename).serialize();
        	fcSourceFile.setInitialDirectory(file.getParentFile());
        	setSaved();
		}
	}
	
	private boolean isFileChange() {
		if (isUnsaved) {
			boolean isFileChange = !unsavedConfirmationAlert("change the file");
			if (isFileChange)
				return false;
		}
		
		return true;
	}
	
	private boolean unsavedConfirmationAlert(String action) {
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
