import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileUtility {
	public static RandomAccessFile createRandomAccessFile(String filename) {
		RandomAccessFile raf = null;

		try {
			raf = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return raf;
	}
	
	public static void closeRandomAccessFile(RandomAccessFile raf) {
		try {
			raf.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
