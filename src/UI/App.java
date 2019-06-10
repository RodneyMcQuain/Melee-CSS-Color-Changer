package UI;

import java.util.Optional;

import Models.DefaultDirectory;
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

	private final static String APP_VERSION = "1.1";
	private final static String TITLE = "Melee CSS Color Changer - v" + APP_VERSION;
	public final static int STAGE_WIDTH = 550;
	
	private static boolean isUnsaved = false;
	
	private static Stage stage;
	private BorderPane mainBorderPane;
	private GridPane gridPaneTop;
	private GridPane gridPaneCenter;
	private GridPane gridPaneBottom;
	
	private SharedUIElements sharedElements;
	
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
		
    	TextField tfSourceFile = new TextField();

    	ComboBox<String> cbBackgroundOptions = new ComboBox<String>();
		ComboBox<String> cbSelectBackgroundOptions = new ComboBox<String>();
		ColorPicker[] cpTopFrame = createTwoColorPicker();
		ColorPicker[] cpBottomFrame = createTwoColorPicker();
		ColorPicker[] cpRules = createTwoColorPicker();
		ColorPicker[] cpBackground = createTwoColorPicker();
		ColorPicker[] cpCursor = createTwoColorPicker();
		ColorPicker[] cpSelectBackground1 = createTwoColorPicker();
		ColorPicker[] cpSelectBackground2 = createTwoColorPicker();
		
    	Button btUpdateFile = new Button("Update File");

    	sharedElements = new SharedUIElements(
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
			sharedElements
		);
		
		topPane.createTopPane();
	}
	
	private void createCenterPane(GridPane gp) {
		MainMenuCenterPane centerPane = new MainMenuCenterPane(
			gp,
			sharedElements
		);
		
		centerPane.createCenterPane();
	}
	
	private void createBottomPane(GridPane gp) {
		MainMenuBottomPane bottomPane = new MainMenuBottomPane(
			gp,
			sharedElements
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
