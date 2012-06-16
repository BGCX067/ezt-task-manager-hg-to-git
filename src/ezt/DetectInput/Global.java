/*Author: Yueng Shu Sheng
 * Purpose: to store global variables
*/
package ezt.DetectInput;

import javax.swing.JInternalFrame;



//store the global variables
public class Global {

	public static String reminderDesc="", dateTemp="", timeTemp="", priorityTemp="", alertTemp="", tempOldDesc="", exportCheck="";
	
	public static int shortCut=1,calendarPrevMKey,calendarNextMKey,calendarYrKey;
	
	public static boolean showDay = false, showEvent=false;
	
	public static char dayPanelKey, weekPanelKey,monthPanelKey,searchPanelKey,eventPanelKey,calendarDateKey,
			 commandKey, activateKey;

	public static JInternalFrame internalFrame_1;
	
}
