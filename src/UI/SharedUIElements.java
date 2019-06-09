package UI;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SharedUIElements {
	private Button btUpdateFile;
	private TextField tfSourceFile;
	
	private ComboBox<String> cbBackgroundOptions;
	private ComboBox<String> cbSelectBackgroundOptions;

	private ColorPicker[] cpTopFrame;
	private ColorPicker[] cpBottomFrame;
	private ColorPicker[] cpRules;
	private ColorPicker[] cpBackground;
	private ColorPicker[] cpCursor;
	private ColorPicker[] cpSelectBackground1;
	private ColorPicker[] cpSelectBackground2;
	
	public SharedUIElements(
		Button btUpdateFile,
		TextField tfSourceFile,
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
		this.btUpdateFile = btUpdateFile;
		this.tfSourceFile = tfSourceFile;
		
		this.cbBackgroundOptions = cbBackgroundOptions;
		this.cbSelectBackgroundOptions = cbSelectBackgroundOptions;
		
		this.cpTopFrame = cpTopFrame;
		this.cpBottomFrame = cpBottomFrame;
		this.cpRules = cpRules;
		this.cpBackground = cpBackground;
		this.cpCursor = cpCursor;
		this.cpSelectBackground1 = cpSelectBackground1;
		this.cpSelectBackground2 = cpSelectBackground2;		
	}

	public ColorPicker[] getCpTopFrame() {
		return cpTopFrame;
	}

	public ColorPicker[] getCpBottomFrame() {
		return cpBottomFrame;
	}

	public ColorPicker[] getCpRules() {
		return cpRules;
	}

	public ColorPicker[] getCpBackground() {
		return cpBackground;
	}

	public ColorPicker[] getCpCursor() {
		return cpCursor;
	}

	public ColorPicker[] getCpSelectBackground1() {
		return cpSelectBackground1;
	}

	public ColorPicker[] getCpSelectBackground2() {
		return cpSelectBackground2;
	}

	public Button getBtUpdateFile() {
		return btUpdateFile;
	}

	public TextField getTfSourceFile() {
		return tfSourceFile;
	}

	public ComboBox<String> getCbBackgroundOptions() {
		return cbBackgroundOptions;
	}

	public ComboBox<String> getCbSelectBackgroundOptions() {
		return cbSelectBackgroundOptions;
	}
}
