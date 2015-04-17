import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class FileTransfer {

	private static int k = 0;
	public static String Send(String destPath) throws IOException{
		StringBuilder fileData = new StringBuilder(1000);//Constructs a string buffer with no characters in it and the specified initial capacity
		BufferedReader reader = new BufferedReader(new FileReader(destPath));
 
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
 
		reader.close();
 
		String returnStr = fileData.toString();
		//System.out.println(returnStr);
		return returnStr;
	}
	
	public static void Receive(String destPath, String content) {
		FileOutputStream fop = null;
		File file;
		//String content = "This is the text content";
 
		try {
			file = new File(destPath + ".txt");
			fop = new FileOutputStream(file);
			byte[] contentInBytes = content.getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
