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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {
	private final int TOP_FRAME_START_OFFSET = 0x349008;
	private final int BOTTOM_FRAME_START_OFFSET = 0x348E88;
	private final int RULES_START_OFFSET = 0x348F48;		
	private final int BACKGROUND_START_OFFSET = 0x000958;		
	
	public static void main(String[] args) throws IOException {
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
    	GridPane gridPane = new GridPane();
    	gridPane.setHgap(10);
    	gridPane.setVgap(10);
    	gridPane.setPadding(new Insets(10, 10, 10, 10));
    	
    	Scene mainMenu =  new Scene(gridPane, 400, 300);
    	FileChooser fcSourceFile = new FileChooser();
    	Label lblPrimaryColor = new Label("Primary Color");
    	Label lblSecondaryColor = new Label("Secondary Color");
    	Label lblTopFrame = new Label("Top Frame: ");
    	Label lblBottomFrame = new Label("Bottom Frame: ");
    	Label lblRules = new Label("Rules: ");
    	Label lblBackground = new Label("Background: ");
    	ColorPicker[] cpTopFrame = { new ColorPicker(), new ColorPicker() };
    	ColorPicker[] cpBottomFrame = { new ColorPicker(), new ColorPicker() };
    	ColorPicker[] cpRules = { new ColorPicker(), new ColorPicker() };
    	ColorPicker[] cpBackground = { new ColorPicker(), new ColorPicker() };
    	Button btUpdateFile = new Button("Update File");
    	Button btChooseFile = new Button("Choose a File to Modify");
    	TextField tfSourceFile = new TextField();
    	
    	gridPane.add(lblPrimaryColor, 1, 0);
    	gridPane.add(lblSecondaryColor, 2, 0);

    	gridPane.add(lblTopFrame, 0, 1);
    	gridPane.add(cpTopFrame[0], 1, 1);
    	gridPane.add(cpTopFrame[1], 2, 1);
    	
    	gridPane.add(lblBottomFrame, 0, 2);
    	gridPane.add(cpBottomFrame[0], 1, 2);
    	gridPane.add(cpBottomFrame[1], 2, 2);
    	
    	gridPane.add(lblRules, 0, 3);
    	gridPane.add(cpRules[0], 1, 3);
    	gridPane.add(cpRules[1], 2, 3);
    	
    	gridPane.add(lblBackground, 0, 4);
    	gridPane.add(cpBackground[0], 1, 4);
    	gridPane.add(cpBackground[1], 2, 4);
    	
    	gridPane.add(tfSourceFile, 0, 5, 2, 1);
    	gridPane.add(btChooseFile, 2, 5);
    	gridPane.add(btUpdateFile, 0, 6);
    	btUpdateFile.setDisable(true);
    	
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
			boolean isUpdated = updateFile(tfSourceFile.getText(), cpTopFrame, cpBottomFrame, cpRules, cpBackground);
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
	
	public boolean updateFile(String filename, 
			ColorPicker[] cpTopFrame, 
			ColorPicker[] cpBottomFrame, 
			ColorPicker[] cpRules, 
			ColorPicker[] cpBackground) {

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
