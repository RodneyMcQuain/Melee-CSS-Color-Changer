package UI;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MainMenuCenterPane {
	private GridPane gp;
	
	private SharedUIElements sharedElements;
	
	private Label lblColor1;
	private Label lblColor2;
	
	public MainMenuCenterPane(
		GridPane gp,
		SharedUIElements sharedElements
	) {
		this.gp = gp;
		
		this.sharedElements = sharedElements;
	}
	
	public void createCenterPane() {
		ComboBox<String> cbBackgroundOptions = sharedElements.getCbBackgroundOptions();
		ComboBox<String> cbSelectBackgroundOptions = sharedElements.getCbSelectBackgroundOptions();

    	App.setHVGap(gp);
    	gp.setPadding(new Insets(App.PADDING, App.PADDING, App.PADDING, App.PADDING));
    
		initialAddToCenterPane(gp);

    	setBackgroundOptions(cbBackgroundOptions);
    	SelectBackgroundUtility.setComboBoxOptions(cbSelectBackgroundOptions);
    	
    	cbSelectBackgroundOptions.setOnAction(e -> setSelectBackgroundUIByOption(cbSelectBackgroundOptions));
    	cbBackgroundOptions.setOnAction(e -> setBackgroundUIByOption(cbBackgroundOptions));
		addActionsToColorPickers();
	}
	
	private void initialAddToCenterPane(GridPane gp) {
		ComboBox<String> cbBackgroundOptions = sharedElements.getCbBackgroundOptions();
		ComboBox<String> cbSelectBackgroundOptions = sharedElements.getCbSelectBackgroundOptions();
		ColorPicker[] cpBackground = sharedElements.getCpBackground();
		ColorPicker[] cpTopFrame = sharedElements.getCpTopFrame();
		ColorPicker[] cpBottomFrame = sharedElements.getCpBottomFrame();
		ColorPicker[] cpRules = sharedElements.getCpRules();
		ColorPicker[] cpCursor = sharedElements.getCpCursor();
		
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
    
	private void setBackgroundUIByOption(ComboBox<String> cbBackgroundOptions) {
		ColorPicker[] cpBackground = sharedElements.getCpBackground();
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
	
	private void setSelectBackgroundUIByOption(ComboBox<String> cbSelectBackgroundOptions) {
		ColorPicker[] cpSelectBackground1 = sharedElements.getCpSelectBackground1();
		ColorPicker[] cpSelectBackground2 = sharedElements.getCpSelectBackground2();
		
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
		ColorPicker[] cpBackground = sharedElements.getCpBackground();
		ColorPicker[] cpTopFrame = sharedElements.getCpTopFrame();
		ColorPicker[] cpBottomFrame = sharedElements.getCpBottomFrame();
		ColorPicker[] cpRules = sharedElements.getCpRules();
		ColorPicker[] cpCursor = sharedElements.getCpCursor();
		ColorPicker[] cpSelectBackground1 = sharedElements.getCpSelectBackground1();
		ColorPicker[] cpSelectBackground2 = sharedElements.getCpSelectBackground2();
		
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
