import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {
	private final int TOP_FRAME_START_OFFSET = 0x349008;
	private final int BOTTOM_FRAME_START_OFFSET = 0x348E88;
	private final int RULES_START_OFFSET = 0x348F48;		
	private final int BACKGROUND_START_OFFSET = 0x000958;		
	private final String SELECT_BACKGROUND_DO_NOTHING = "Do Nothing";
	private final String SELECT_BACKGROUND_ALL = "All";
	private final String SELECT_BACKGROUND_ALTERNATE = "Alternate";
	private final String SELECT_BACKGROUND_RANDOM = "Random";
	private final String SELECT_BACKGROUND_TRANSPARENT = "Transparent";

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
    	
    	Scene mainMenu =  new Scene(main, 700, 350);
    	main.setCenter(gridPaneCenter);
    	main.setBottom(gridPaneBottom);
    	FileChooser fcSourceFile = new FileChooser();
    	final String PRIMARY_COLOR = "Primary Color";
    	final String SECONDARY_COLOR = "Secondary Color";
    	Label lblPrimaryColor = new Label(PRIMARY_COLOR);
    	Label lblSecondaryColor = new Label(SECONDARY_COLOR);
    	Label lblPrimaryColor2 = new Label(PRIMARY_COLOR);
    	Label lblSecondaryColor2 = new Label(SECONDARY_COLOR);
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
    	ComboBox<String> cbSelectBackgroundOptions = new ComboBox<String>();
    	cbSelectBackgroundOptions.getItems().addAll(
			SELECT_BACKGROUND_DO_NOTHING,
			SELECT_BACKGROUND_ALL,
			SELECT_BACKGROUND_ALTERNATE,
			SELECT_BACKGROUND_RANDOM,
			SELECT_BACKGROUND_TRANSPARENT
		);
    	cbSelectBackgroundOptions.setValue(SELECT_BACKGROUND_DO_NOTHING);
    	
    	gridPaneCenter.add(lblPrimaryColor, 1, 0);
    	gridPaneCenter.add(lblSecondaryColor, 2, 0);

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
    	
    	gridPaneCenter.add(lblSelectBackground, 4, 0);
    	gridPaneCenter.add(cbSelectBackgroundOptions, 5, 0);
    	
    	gridPaneBottom.add(tfSourceFile, 0, 0, 20, 1);
    	gridPaneBottom.add(btChooseFile, 20, 0);
    	gridPaneBottom.add(btUpdateFile, 0, 1);
    	btUpdateFile.setDisable(true);
    	
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
	    		
	    		Format070707 topFrame = new Format070707(TOP_FRAME_START_OFFSET);
	    		Format070707 bottomFrame = new Format070707(BOTTOM_FRAME_START_OFFSET);
	    		Format070707 rules = new Format070707(RULES_START_OFFSET);
	    		Format4248 background = new Format4248(BACKGROUND_START_OFFSET);
	    		
	    		setColorPickerColor(filename, cpTopFrame, topFrame);
	    		setColorPickerColor(filename, cpBottomFrame, bottomFrame);
	    		setColorPickerColor(filename, cpRules, rules);
	    		setColorPickerColor(filename, cpBackground, background);
    		} else {
    			btUpdateFile.setDisable(true);
    		}
		});
    	
		btUpdateFile.setOnAction(e -> {
			String filename = tfSourceFile.getText();

			updateSelectBackgroundFile(filename, cbSelectBackgroundOptions.getValue(), cpSelectBackground1, cpSelectBackground2);
			
			boolean isUpdated = updateFile(
					filename, 
					cpTopFrame, 
					cpBottomFrame, 
					cpRules, 
					cpBackground 
			);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Update Dialog");
			alert.setHeaderText(null);
			if (isUpdated)
				alert.setContentText("File updated successfully.");
			else
				alert.setContentText("Error while updating file, please restart the program.");
			
			alert.showAndWait();
		});
		
		return mainMenu;
	}
	
	private void setColorPickerColor(String filename, ColorPicker[] cp, TwoColorFormat f) {
		Color color1 = f.read(filename, f.primaryColorOffset);
		cp[0].setValue(color1);	
		
		Color color2 = f.read(filename, f.secondaryColorOffset);
		cp[1].setValue(color2);	
	}
	
	public boolean updateSelectBackgroundFile(
			String filename,
			String selectBackgroundOption, 
			ColorPicker[] cpSelectBackground1, 
			ColorPicker[] cpSelectBackground2
	) 
	{
		Format4248 selectBackground1 = createFormat4248(BACKGROUND_START_OFFSET, cpSelectBackground1);	
		Format4248 selectBackground2 = createFormat4248(BACKGROUND_START_OFFSET, cpSelectBackground2);	

		selectBackgroundOption(filename, selectBackgroundOption, selectBackground1, selectBackground2);

		return true;
	}
	
	private void selectBackgroundOption(String filename, String option, Format4248 selectBackground1, Format4248 selectBackground2) {
		if (option.equals(SELECT_BACKGROUND_RANDOM))
			Format4248.writeRandomSelectBackgroundColor(filename);
		else if (option.equals(SELECT_BACKGROUND_ALL)) 
			Format4248.writeAllSelectBackgroundColor(filename, selectBackground1.primaryColor, selectBackground1.secondaryColor);
		else if (option.equals(SELECT_BACKGROUND_ALTERNATE)) {
			HexRGB[] rgb1 = { selectBackground1.primaryColor, selectBackground1.secondaryColor };
			HexRGB[] rgb2 = { selectBackground2.primaryColor, selectBackground2.secondaryColor };
			Format4248.writeAlternateSelectBackgroundColor(filename, rgb1, rgb2);
		} else if (option.equals(SELECT_BACKGROUND_TRANSPARENT))
			Format4248.writeTransparentSelectBackgroundColor(filename);
	}
	
	public boolean updateFile(
			String filename, 
			ColorPicker[] cpTopFrame, 
			ColorPicker[] cpBottomFrame, 
			ColorPicker[] cpRules, 
			ColorPicker[] cpBackground
	) 
	{
		Format070707 topFrame = createFormat070707(TOP_FRAME_START_OFFSET, cpTopFrame);		
		Format070707 bottomFrame = createFormat070707(BOTTOM_FRAME_START_OFFSET, cpBottomFrame);	
		Format070707 rules = createFormat070707(RULES_START_OFFSET, cpRules);	
		Format4248 background = createFormat4248(BACKGROUND_START_OFFSET, cpBackground);	
		
		TwoColorFormat[] twoColorFormats = { topFrame, bottomFrame, rules, background };
		boolean isUpdated = true;
		for (TwoColorFormat format : twoColorFormats) {
			boolean temp = format.write(filename);
			if (!temp)
				isUpdated = false;
		}
	
		return isUpdated;
	}
	
	private Format070707 createFormat070707(int address, ColorPicker[] cp) {
		return new Format070707(address, cp[0].getValue(), cp[1].getValue());
	}
	
	private Format4248 createFormat4248(int address, ColorPicker[] cp) {
		return new Format4248(address, cp[0].getValue(), cp[1].getValue());
	}
}
