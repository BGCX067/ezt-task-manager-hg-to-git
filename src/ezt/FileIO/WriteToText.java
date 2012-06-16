/*Author: Yueng Shu Sheng
 * Purpose:to write/append the task into task.txt
*/
package ezt.FileIO;

import java.io.*;

//this class is to write/append the task into task.txt
public class WriteToText {

	private static final String fileName = "Task.txt";
	private static final String fileName2 = "wordList.txt";	
	
	public static void write(Object task){

		try{
	        
            OutputStreamWriter writer = new OutputStreamWriter(
                  new FileOutputStream(fileName, true), "UTF-8");
            BufferedWriter fbw = new BufferedWriter(writer);
            fbw.write(task.toString());
            fbw.newLine();
            fbw.close();
            
            SortFileRecord s = new SortFileRecord();
    		s.sort();
    		
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
		
	}	

	//save the new keyword from user for auto complete
	public void writeWordList(String word){

		try{
	        
            OutputStreamWriter writer = new OutputStreamWriter(
                  new FileOutputStream(fileName2, true), "UTF-8");
            BufferedWriter fbw = new BufferedWriter(writer);
            fbw.write(word);
            fbw.newLine();
            fbw.close();
    		
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
		
	}	

}
