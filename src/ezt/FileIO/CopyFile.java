/*Author: Yueng Shu Sheng
 * Purpose:This class is to copy the wanted contents from task.txt into tempTask.txt for temporary store
  *After the unwanted contents in task.txt are cleared, it will copy back to the task.txt
*/
package ezt.FileIO;

import java.io.*;


public class CopyFile {

	public void copy(File src, File dst) throws IOException {
	    InputStream in = new FileInputStream(src);
	    OutputStream out = new FileOutputStream(dst);

	    // Transfer bytes from in to out
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = in.read(buf)) > 0) {
	        out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	}
	
}
