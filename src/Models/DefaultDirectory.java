package Models;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DefaultDirectory implements Serializable {
	private static final long serialVersionUID = 1L;
	private String filename;
	private static String SETTINGS_FILENAME = "settings.ser";
	
	public DefaultDirectory(String filename) {
		this.filename = filename;
	}
	
	public static void createSettings() {
		File file = new File(SETTINGS_FILENAME);
		try {
			file.createNewFile();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void serialize() {
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut = null;
		
		try { 
            fileOut = new FileOutputStream(SETTINGS_FILENAME); 
            objOut = new ObjectOutputStream(fileOut); 
  
            objOut.writeObject(this);

            objOut.close(); 
		} catch (IOException ioe) { 
            ioe.printStackTrace();
        } finally {
        	try {
                fileOut.close();
        	} catch (IOException ioe) {
				ioe.printStackTrace();
			} 
    	}
	}
	
	public static String deserialize() {
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		DefaultDirectory defaultDirectory = null;
		
		try {   
			fileIn = new FileInputStream(SETTINGS_FILENAME); 
			objIn = new ObjectInputStream(fileIn); 
	  
			defaultDirectory = (DefaultDirectory) objIn.readObject(); 

        	objIn.close();
		} catch (IOException | ClassNotFoundException ex) { 
			deleteSettings();
            createSettings();
        } finally {
        	try {
				fileIn.close();
        	} catch (IOException ioe) {
				ioe.printStackTrace();
			} 
    	}

		return getDefaultDirectory(defaultDirectory);
	}
	
	private static void deleteSettings() {
        File file = new File(SETTINGS_FILENAME);
        file.delete();
	}
	
	private static String getDefaultDirectory(DefaultDirectory defaultDirectory) {		
		if (defaultDirectory == null)
			return "C:\\";
		else {
			File file = new File(defaultDirectory.filename);
			File parentFile = file.getParentFile();
			return parentFile.toString();
		}		
	}
}
