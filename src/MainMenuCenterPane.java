import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MainMenuCenterPane {
	private GridPane gp;
	
	private ColorPicker[] cpTopFrame;
	private ColorPicker[] cpBottomFrame;
	private ColorPicker[] cpRules;
	private ColorPicker[] cpBackground;
	private ColorPicker[] cpCursor;
	private ColorPicker[] cpSelectBackground1;
	private ColorPicker[] cpSelectBackground2;
	
	private ComboBox<String> cbBackgroundOptions;
	private ComboBox<String> cbSelectBackgroundOptions;
	
	private Label lblColor1;
	private Label lblColor2;
	
	public MainMenuCenterPane(
		GridPane gp,
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
	
	public void createCenterPane() {
    	App.setHVGap(gp);
    	gp.setPadding(new Insets(App.PADDING, App.PADDING, App.PADDING, App.PADDING));
    
		initialAddToCenterPane(gp);

    	setBackgroundOptions(cbBackgroundOptions);
    	SelectBackgroundUtility.setComboBoxOptions(cbSelectBackgroundOptions);
    	
    	cbSelectBackgroundOptions.setOnAction(e -> setSelectBackgroundUIByOption());
    	cbBackgroundOptions.setOnAction(e -> setBackgroundUIByOption());
		addActionsToColorPickers();
	}
	
	private void initialAddToCenterPane(GridPane gp) {
    	Label lblPrimaryColor = new Label("Primary Color");
    	Label lblSecondaryColor = new Label("Secondary Color");
    	Label lblTopFrame = new Label("Top Frame: ");
    	Label lblBottomFrame = new Label("Bottom Frame: ");
    	Label lblRules = new Label("Rules: ");
    	Label lblBackground = new Label("Background: ");
    	Label lblCursor = new Label("Cursor: ");
    	Label lblSelectBackground = new Label("Selects in Background: ");
		lblColor1 = new Label("\tColor 1: ");
		lblColor2 = new Label("\tColor 2: ");
    	
		gp.add(lblPrimaryColor, 1, 0);
    	gp.add(lblSecondaryColor, 2, 0);

    	gp.add(lblTopFrame, 0, 1);
    	gp.add(cpTopFrame[0], 1, 1);
    	gp.add(cpTopFrame[1], 2, 1);

    	gp.add(lblBottomFrame, 0, 2);
    	gp.add(cpBottomFrame[0], 1, 2);
    	gp.add(cpBottomFrame[1], 2, 2);

    	gp.add(lblRules, 0, 3);
    	gp.add(cpRules[0], 1, 3);
    	gp.add(cpRules[1], 2, 3);

    	gp.add(lblBackground, 0, 4);
    	gp.add(cpBackground[0], 1, 4);
    	gp.add(cpBackground[1], 2, 4);
    	gp.add(cbBackgroundOptions, 3, 4);

    	gp.add(lblCursor, 0, 5);
    	gp.add(cpCursor[0], 1, 5);
    	gp.add(cpCursor[1], 2, 5);
    	
    	gp.add(lblSelectBackground, 0, 6);
    	gp.add(cbSelectBackgroundOptions, 1, 6, 2, 1);
	}
	
	private void setBackgroundOptions(ComboBox<String> cb) {
		cb.getItems().addAll(
        		App.SPECIFY_COLOR,
    			SelectBackgroundUtility.TRANSPARENT
		);
		cb.setValue(App.SPECIFY_COLOR);
	}
    
	private void setBackgroundUIByOption() {
		String option = cbBackgroundOptions.getValue();
		
		gp.getChildren().remove(cpBackground[0]);
		gp.getChildren().remove(cpBackground[1]);
		gp.getChildren().remove(cbBackgroundOptions);
		
		switch(option) {
			case App.SPECIFY_COLOR:
	        	gp.add(cpBackground[0], 1, 4);
	        	gp.add(cpBackground[1], 2, 4);
	        	gp.add(cbBackgroundOptions, 3, 4);		
				break;
			case SelectBackgroundUtility.TRANSPARENT:
	        	gp.add(cbBackgroundOptions, 1, 4);		
				break;
		}
		
		App.setUnsaved();
	}
	
	private void setSelectBackgroundUIByOption() {
		String option = cbSelectBackgroundOptions.getValue();
		
		gp.getChildren().remove(cpSelectBackground1[0]);
		gp.getChildren().remove(cpSelectBackground1[1]);
		gp.getChildren().remove(cpSelectBackground2[0]);
		gp.getChildren().remove(cpSelectBackground2[1]);
		gp.getChildren().remove(lblColor1);
		gp.getChildren().remove(lblColor2);
		
		switch (option) {
			case SelectBackgroundUtility.ONE_COLOR:
    	    	gp.add(lblColor1, 0, 7);
    			gp.add(cpSelectBackground1[0], 1, 7);
    	    	gp.add(cpSelectBackground1[1], 2, 7);
				break;
			case SelectBackgroundUtility.ALTERNATE:
			case SelectBackgroundUtility.ALTERNATE_FULL:
			case SelectBackgroundUtility.TRI:
    	    	gp.add(lblColor1, 0, 7);
				gp.add(cpSelectBackground1[0], 1, 7);
    	    	gp.add(cpSelectBackground1[1], 2, 7);
    	    	gp.add(lblColor2, 0, 8);
		    	gp.add(cpSelectBackground2[0], 1, 8);
		    	gp.add(cpSelectBackground2[1], 2, 8);
				break;
		}
		
		App.setUnsaved();
	}
	
	private void addActionsToColorPickers() {
		ColorPicker[][] twoDimColorPickers = {
				cpTopFrame,
				cpBottomFrame,
				cpRules,
				cpBackground,
				cpCursor,
				cpSelectBackground1,
				cpSelectBackground2
		};
		
		for (ColorPicker[] colorPickers : twoDimColorPickers) {
			colorPickers[0].setOnAction(e -> App.setUnsaved());
			colorPickers[1].setOnAction(e -> App.setUnsaved());
		}		
	}
}
