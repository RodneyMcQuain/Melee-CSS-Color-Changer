import java.io.File;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {
	private final String SELECT_BACKGROUND_DO_NOTHING = "Do Nothing";
	private final String SELECT_BACKGROUND_ALL = "All";
	private final String SELECT_BACKGROUND_ALTERNATE = "Alternate";
	private final String SELECT_BACKGROUND_RANDOM = "Random";
	private final String SELECT_BACKGROUND_TRANSPARENT = "Transparent";
	private final String SELECT_BACKGROUND_VISIBLE = "Visible";
	
	public static void main(String[] args) {
        Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
    	Scene mainMenu = createMainMenu(primaryStage);
    	
	    primaryStage.setTitle("Melee CSS Color Changer");
	    primaryStage.setScene(mainMenu);
	    primaryStage.show();
    }
	
	private Scene createMainMenu(Stage stage) {
		BorderPane main = new BorderPane();
    	GridPane gridPaneCenter = new GridPane();
    	gridPaneCenter.setHgap(10);
    	gridPaneCenter.setVgap(10);
    	gridPaneCenter.setPadding(new Insets(10, 10, 10, 10));
    	GridPane gridPaneBottom = new GridPane();
    	gridPaneBottom.setHgap(10);
    	gridPaneBottom.setVgap(10);
    	gridPaneBottom.setPadding(new Insets(10, 10, 10, 10));
    	
    	Scene mainMenu =  new Scene(main, 900, 350);
    	main.setCenter(gridPaneCenter);
    	main.setBottom(gridPaneBottom);
    	FileChooser fcSourceFile = new FileChooser();
    	final String PRIMARY_COLOR = "Primary Color";
    	final String SECONDARY_COLOR = "Secondary Color";
    	Label lblPrimaryColor = new Label(PRIMARY_COLOR);
    	Label lblSecondaryColor = new Label(SECONDARY_COLOR);
    	Label lblPrimaryColor2 = new Label(PRIMARY_COLOR);
    	Label lblSecondaryColor2 = new Label(SECONDARY_COLOR);
    	Label lblTransparent = new Label("Transparent?");
    	Label lblTopFrame = new Label("Top Frame: ");
    	Label lblBottomFrame = new Label("Bottom Frame: ");
    	Label lblRules = new Label("Rules: ");
    	Label lblBackground = new Label("Background: ");
    	Label lblSelectBackground = new Label("Selects in Background: ");
    	ColorPicker[] cpTopFrame = { new ColorPicker(), new ColorPicker() };
    	ColorPicker[] cpBottomFrame = { new ColorPicker(), new ColorPicker() };
    	ColorPicker[] cpRules = { new ColorPicker(), new ColorPicker() };
    	ColorPicker[] cpBackground = { new ColorPicker(), new ColorPicker() };
    	ColorPicker[] cpSelectBackground1 = { new ColorPicker(), new ColorPicker() };
    	ColorPicker[] cpSelectBackground2 = { new ColorPicker(), new ColorPicker() };
    	Button btUpdateFile = new Button("Update File");
    	Button btChooseFile = new Button("Choose a File to Modify");
    	TextField tfSourceFile = new TextField();
    	CheckBox chbBackgroundTransparency = new CheckBox();
		GridPane.setHalignment(chbBackgroundTransparency, HPos.CENTER);
    	
    	ComboBox<String> cbSelectBackgroundOptions = new ComboBox<String>();
    	cbSelectBackgroundOptions.getItems().addAll(
			SELECT_BACKGROUND_DO_NOTHING,
			SELECT_BACKGROUND_ALL,
			SELECT_BACKGROUND_ALTERNATE,
			SELECT_BACKGROUND_RANDOM,
			SELECT_BACKGROUND_TRANSPARENT,
			SELECT_BACKGROUND_VISIBLE
		);
    	cbSelectBackgroundOptions.setValue(SELECT_BACKGROUND_DO_NOTHING);
    	
    	//Add to center pane
    	gridPaneCenter.add(lblPrimaryColor, 1, 0);
    	gridPaneCenter.add(lblSecondaryColor, 2, 0);
    	gridPaneCenter.add(lblTransparent, 3, 0);

    	gridPaneCenter.add(lblTopFrame, 0, 1);
    	gridPaneCenter.add(cpTopFrame[0], 1, 1);
    	gridPaneCenter.add(cpTopFrame[1], 2, 1);

    	gridPaneCenter.add(lblBottomFrame, 0, 2);
    	gridPaneCenter.add(cpBottomFrame[0], 1, 2);
    	gridPaneCenter.add(cpBottomFrame[1], 2, 2);

    	gridPaneCenter.add(lblRules, 0, 3);
    	gridPaneCenter.add(cpRules[0], 1, 3);
    	gridPaneCenter.add(cpRules[1], 2, 3);

    	gridPaneCenter.add(lblBackground, 0, 4);
    	gridPaneCenter.add(cpBackground[0], 1, 4);
    	gridPaneCenter.add(cpBackground[1], 2, 4);
    	gridPaneCenter.add(chbBackgroundTransparency, 3, 4);

    	gridPaneCenter.add(lblSelectBackground, 4, 0);
    	gridPaneCenter.add(cbSelectBackgroundOptions, 5, 0);
    	
    	
    	//Add to bottom pane
    	gridPaneBottom.add(tfSourceFile, 0, 0, 20, 1);
    	gridPaneBottom.add(btChooseFile, 20, 0);
    	gridPaneBottom.add(btUpdateFile, 0, 1);
    	btUpdateFile.setDisable(true);
    	
    	//Set actions
    	cbSelectBackgroundOptions.setOnAction(e -> {
    		String option = cbSelectBackgroundOptions.getValue();
    		
			gridPaneCenter.getChildren().remove(lblPrimaryColor2);
			gridPaneCenter.getChildren().remove(lblSecondaryColor2);
			gridPaneCenter.getChildren().remove(cpSelectBackground1[0]);
			gridPaneCenter.getChildren().remove(cpSelectBackground1[1]);
			gridPaneCenter.getChildren().remove(cpSelectBackground2[0]);
			gridPaneCenter.getChildren().remove(cpSelectBackground2[1]);
    		
    		if (option.equals(SELECT_BACKGROUND_ALL)) { 
    	    	gridPaneCenter.add(lblPrimaryColor2, 4, 1);
    	    	gridPaneCenter.add(lblSecondaryColor2, 5, 1);
    			gridPaneCenter.add(cpSelectBackground1[0], 4, 1);
    	    	gridPaneCenter.add(cpSelectBackground1[1], 5, 1);
    		} else if (option.equals(SELECT_BACKGROUND_ALTERNATE)) {
    	    	gridPaneCenter.add(lblPrimaryColor2, 4, 1);
    	    	gridPaneCenter.add(lblSecondaryColor2, 5, 1);
    	    	gridPaneCenter.add(cpSelectBackground1[0], 4, 2);
    	    	gridPaneCenter.add(cpSelectBackground1[1], 5, 2);
		    	gridPaneCenter.add(cpSelectBackground2[0], 4, 3);
		    	gridPaneCenter.add(cpSelectBackground2[1], 5, 3);
    		}
		});
    	
    	btChooseFile.setOnAction(e -> { 
    		File file = fcSourceFile.showOpenDialog(stage.getScene().getWindow());
    		
    		if (file != null) {
    			btUpdateFile.setDisable(false);
	    		String filename = file.toString();
	    		tfSourceFile.setText(filename);
	    		
	    		Format070707 topFrame = new TopFrame();
	    		Format070707 bottomFrame = new BottomFrame();
	    		Format070707 rules = new Rules();
	    		Format4248 background = new Background();
	    		
	    		setColorPickerColor(filename, cpTopFrame, topFrame);
	    		setColorPickerColor(filename, cpBottomFrame, bottomFrame);
	    		setColorPickerColor(filename, cpRules, rules);
	    		setColorPickerColor(filename, cpBackground, background);
				setCheckBoxTick(filename, chbBackgroundTransparency, background);
    		} else {
    			btUpdateFile.setDisable(true);
    		}
		});
    	
		btUpdateFile.setOnAction(e -> {
			String filename = tfSourceFile.getText();

			updateSelectBackgroundFile(filename, cbSelectBackgroundOptions.getValue(), cpSelectBackground1, cpSelectBackground2);
			
			updateBackground(filename, cpBackground, chbBackgroundTransparency);
			updateTopFrame(filename, cpTopFrame);
			updateBottomFrame(filename, cpBottomFrame);
			updateRules(filename, cpRules);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Update Dialog");
			alert.setHeaderText(null);
			alert.setContentText("File updated successfully.");
			alert.showAndWait();
		});
		
		return mainMenu;
	}
	
	private void updateBackground(String filename, ColorPicker[] cpBackgrounds, CheckBox chbBackground) {
		TwoColor twoColor = getTwoColor(cpBackgrounds);
		Format4248 background = new Background(twoColor.primaryColor, twoColor.secondaryColor);	
		boolean isTransparent = chbBackground.isSelected();
		
		background.writeColors(filename);
		background.writeTransparency(filename, isTransparent);
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
	
	private void setColorPickerColor(String filename, ColorPicker[] cp, TwoColorFormat f) {
		Color color1 = f.readColors(filename, f.getPrimaryColorOffset());
		cp[0].setValue(color1);	
		
		Color color2 = f.readColors(filename, f.getSecondaryColorOffset());
		cp[1].setValue(color2);	
	}
	
	private void setCheckBoxTick(String filename, CheckBox cp, Format4248 f) {
		boolean isTransparent = f.readTransparency(filename);
		System.out.println(isTransparent);
		cp.setIndeterminate(false);
		cp.setSelected(isTransparent);
	}
	
	public boolean updateSelectBackgroundFile(
			String filename,
			String selectBackgroundOption, 
			ColorPicker[] cpSelectBackground1, 
			ColorPicker[] cpSelectBackground2
	) 
	{
		TwoColor twoColor1 = getTwoColor(cpSelectBackground1);
		Format4248 selectBackground1 = new SelectBackground(twoColor1.primaryColor, twoColor1.secondaryColor);
		TwoColor twoColor2 = getTwoColor(cpSelectBackground2);
		Format4248 selectBackground2 = new SelectBackground(twoColor2.primaryColor, twoColor2.secondaryColor);

		selectBackgroundOption(filename, selectBackgroundOption, selectBackground1, selectBackground2);

		return true;
	}
	
	private void selectBackgroundOption(String filename, String option, Format4248 selectBackground1, Format4248 selectBackground2) {
		if (option.equals(SELECT_BACKGROUND_RANDOM))
			SelectBackground.writeRandomSelectBackgroundColor(filename);
		else if (option.equals(SELECT_BACKGROUND_ALL)) 
			SelectBackground.writeAllSelectBackgroundColor(filename, selectBackground1.primaryColor, selectBackground1.secondaryColor);
		else if (option.equals(SELECT_BACKGROUND_ALTERNATE)) {
			HexRGB[] rgb1 = { selectBackground1.primaryColor, selectBackground1.secondaryColor };
			HexRGB[] rgb2 = { selectBackground2.primaryColor, selectBackground2.secondaryColor };
			SelectBackground.writeAlternateSelectBackgroundColor(filename, rgb1, rgb2);
		} else if (option.equals(SELECT_BACKGROUND_TRANSPARENT))
			SelectBackground.writeTransparentSelectBackgroundColor(filename);
		else if (option.equals(SELECT_BACKGROUND_VISIBLE))
			SelectBackground.writeVisibleSelectBackgroundColor(filename);
	}
	
	private TwoColor getTwoColor(ColorPicker[] cp) {
		return new TwoColor(cp[0].getValue(), cp[1].getValue());
	}
}
