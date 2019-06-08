
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    	
    	initializeMainMenuComponents();
    	createTopPane(gridPaneTop);
    	createCenterPane(gridPaneCenter);
    	createBottomPane(gridPaneBottom);
    	mainBorderPane.setTop(gridPaneTop);
		
		return mainMenu;
	}
	
	private void initializeMainMenuComponents() {		
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

	private ColorPicker[] createTwoColorPicker() {
		ColorPicker[] cps = { new ColorPicker(), new ColorPicker() };
		return cps;
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
	
	private void createBottomPane(GridPane gp) {
		MainMenuBottomPane bottomPane = new MainMenuBottomPane(
			gp,
			btUpdateFile,
			tfSourceFile,
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
			
		bottomPane.createBottomPane();
	}
	
	public static void setHVGap(GridPane gp) {
    	gp.setHgap(PADDING);
    	gp.setVgap(PADDING);
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
}
