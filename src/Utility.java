import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class Utility {
	public static RandomAccessFile createRandomAccessFile(String filename) {
		RandomAccessFile raf = null;

		try {
			raf = new RandomAccessFile(filename, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return raf;
	}
}
