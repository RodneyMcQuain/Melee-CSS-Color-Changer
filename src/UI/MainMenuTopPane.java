package UI;
import java.io.File;

import Models.Background;
import Models.BottomFrame;
import Models.Cursor;
import Models.DefaultDirectory;
import Models.Format070707;
import Models.Format4248;
import Models.Rules;
import Models.TopFrame;
import Models.TwoColorFormat;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainMenuTopPane {
	private BorderPane mainMenu;
	private GridPane topPane;
	private GridPane centerPane;
	private GridPane bottomPane;

	private SharedUIElements sharedElements;
	
	private Stage stage;
	private FileChooser fcSourceFile;
	private Button btChooseFile;
	private Label lblProvideValidFile;
	
	public MainMenuTopPane(
		Stage stage,
		BorderPane mainMenu,
		GridPane topPane,
		GridPane centerPane,
		GridPane bottomPane,
		SharedUIElements sharedElements
	) {
		this.stage = stage;
		this.mainMenu = mainMenu;
		this.topPane = topPane;
		this.centerPane = centerPane;
		this.bottomPane = bottomPane;
		this.sharedElements = sharedElements;
	}
	
	public void createTopPane() {
		TextField tfSourceFile = sharedElements.getTfSourceFile();
		
    	App.setHVGap(topPane);
    	topPane.setPadding(new Insets(App.PADDING, App.PADDING, 0, App.PADDING));

    	btChooseFile = new Button("Choose a File to Modify");
    	fcSourceFile = new FileChooser();
    	lblProvideValidFile = new Label("Please provide a valid file.");
    	lblProvideValidFile.setTextFill(Color.RED);
    	GridPane.setHalignment(lblProvideValidFile, HPos.CENTER);
    	
    	tfSourceFile.setPrefWidth(App.STAGE_WIDTH - 175);
    	formatFileChooserForUsdFiles(fcSourceFile);
    	setInitialDirectory(fcSourceFile);

    	topPane.add(lblProvideValidFile, 0, 0, 2, 1);
    	topPane.add(tfSourceFile, 0, 1);
    	topPane.add(btChooseFile, 1, 1);
	
		tfSourceFile.textProperty().addListener(e -> setUIByFileValidity());
    	btChooseFile.setOnAction(e -> chooseFile());
	}
	
	private void setUIByFileValidity() {
		TextField tfSourceFile = sharedElements.getTfSourceFile();

		String filename = tfSourceFile.getText();
		File file = new File(filename);
		topPane.getChildren().remove(lblProvideValidFile);
		
		if (isValidFile(file)) {
			setUIByOffsets(filename);

			setValidFileUI();
		} else {
			topPane.getChildren().add(lblProvideValidFile);
			
			setInvalidFileUI();
		}
	}
	
	private boolean isValidFile(File file) {
		return file != null
				&& file.exists()
				&& file.getName().matches("^(.*sd)");
	}
	
	private void setValidFileUI() {
		Button btUpdateFile = sharedElements.getBtUpdateFile();

		final int VALID_FILE_STAGE_HEIGHT = 460;
		stage.setHeight(VALID_FILE_STAGE_HEIGHT);
		btUpdateFile.setDisable(false);
		mainMenu.setCenter(centerPane);
    	mainMenu.setBottom(bottomPane);
	}
	
	private void setInvalidFileUI() {
		Button btUpdateFile = sharedElements.getBtUpdateFile();

		stage.setHeight(App.INVALID_FILE_STAGE_HEIGHT + 40);
		btUpdateFile.setDisable(true);
		mainMenu.setCenter(null);
    	mainMenu.setBottom(null);
	}
	
	private void chooseFile() {
		if (!isFileChange())
			return;
		
		File file = fcSourceFile.showOpenDialog(mainMenu.getScene().getWindow());
		
		if (file != null) {
			TextField tfSourceFile = sharedElements.getTfSourceFile();
			String filename = file.toString();
    		tfSourceFile.setText(filename);
    		
    		new DefaultDirectory(filename).serialize();
        	fcSourceFile.setInitialDirectory(file.getParentFile());
        	App.setSaved();
		}
	}
	
	private boolean isFileChange() {
		if (App.getIsUnsaved()) {
			boolean isFileChange = !App.unsavedConfirmationAlert("change the file");
			if (isFileChange)
				return false;
		}
		
		return true;
	}
	
	private void setUIByOffsets(String filename) {
		ComboBox<String> cbBackgroundOptions = sharedElements.getCbBackgroundOptions();
		ColorPicker[] cpBackground = sharedElements.getCpBackground();
		ColorPicker[] cpTopFrame = sharedElements.getCpTopFrame();
		ColorPicker[] cpBottomFrame = sharedElements.getCpBottomFrame();
		ColorPicker[] cpRules = sharedElements.getCpRules();
		ColorPicker[] cpCursor = sharedElements.getCpCursor();
		
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
		setBackgroundComboBox(filename, cbBackgroundOptions, cpBackground, background);
	}
	
	private void setColorPickerColor(String filename, ColorPicker[] cp, TwoColorFormat f) {
		Color color1 = f.readColors(filename, f.getPrimaryColorOffset());
		cp[0].setValue(color1);	
		
		Color color2 = f.readColors(filename, f.getSecondaryColorOffset());
		cp[1].setValue(color2);	
	}
	
	private void setBackgroundComboBox(
		String filename, 
		ComboBox<String> cbBackgroundOptions, 
		ColorPicker[] cpBackground, 
		Format4248 f
	) {
		boolean isTransparent = f.isTransparent(filename);
		
		if (isTransparent) {
			cbBackgroundOptions.setValue(SelectBackgroundUtility.TRANSPARENT);
			centerPane.getChildren().remove(cpBackground[0]);
			centerPane.getChildren().remove(cpBackground[1]);
			centerPane.getChildren().remove(cbBackgroundOptions);
			centerPane.add(cbBackgroundOptions, 1, 4);		
		} else {
			cbBackgroundOptions.setValue(App.SPECIFY_COLOR);
		}
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
}
