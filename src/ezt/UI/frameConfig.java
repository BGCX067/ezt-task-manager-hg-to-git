package ezt.UI;

import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ezt.DetectInput.Global;
import ezt.keyConfig.KeyConfig;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

//Key Configuration Manager frame
public class frameConfig extends JFrame {
 
	private static final long serialVersionUID = 1L;
	private static JComboBox comboBoxYear, comboBoxSearch, comboBoxNext, comboBoxPrev, comboBoxCMD, comboBoxDate, comboBoxEvent
	, comboBoxMonth,comboBoxWeek,comboBoxDay,comboBoxShow;

	public frameConfig() {
		
		setTitle("EZ Task Manager Key Configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(450, 200, 357, 467);
		JPanel contentPaneReminder = new JPanel();
		contentPaneReminder.setBackground(SystemColor.menu);
		contentPaneReminder.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneReminder);
		getContentPane().setLayout(null);
		contentPaneReminder.setLayout(null);
		
		JLabel lblTaskManagerShowhide = new JLabel("Task Manager Show/Hide:              Ctrl +");
		lblTaskManagerShowhide.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTaskManagerShowhide.setBounds(10, 11, 236, 14);
		contentPaneReminder.add(lblTaskManagerShowhide);
		
		JLabel lblSelectDayPanel = new JLabel("Select Day Panel:                          Ctrl +");
		lblSelectDayPanel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectDayPanel.setBounds(10, 46, 236, 14);
		contentPaneReminder.add(lblSelectDayPanel);
		
		JLabel lblSelectWeekPanel = new JLabel("Select Week Panel:                       Ctrl +");
		lblSelectWeekPanel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectWeekPanel.setBounds(10, 79, 236, 14);
		contentPaneReminder.add(lblSelectWeekPanel);
		
		JLabel lblSelectMonthPanel = new JLabel("Select Month Panel:                      Ctrl +");
		lblSelectMonthPanel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectMonthPanel.setBounds(10, 112, 236, 14);
		contentPaneReminder.add(lblSelectMonthPanel);
		
		JLabel lblSelectCalendarDate = new JLabel("Select Calendar Date:                    Ctrl +");
		lblSelectCalendarDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectCalendarDate.setBounds(10, 208, 236, 14);
		contentPaneReminder.add(lblSelectCalendarDate);
			
		JLabel lblSelectCommandBox = new JLabel("Select Command Box:                    Ctrl +");
		lblSelectCommandBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectCommandBox.setBounds(10, 246, 236, 14);
		contentPaneReminder.add(lblSelectCommandBox);
		
		JLabel lblSelectEventPanel = new JLabel("Select Event Panel:                       Ctrl +");
		lblSelectEventPanel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectEventPanel.setBounds(10, 145, 236, 14);
		contentPaneReminder.add(lblSelectEventPanel);
		
		JLabel lblSelectCalendarPrevious = new JLabel("Select Calendar Previous Month:");
		lblSelectCalendarPrevious.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectCalendarPrevious.setBounds(10, 277, 236, 14);
		contentPaneReminder.add(lblSelectCalendarPrevious);
		
		JLabel lblSelectCalendarNext = new JLabel("Select Calendar Next Month:");
		lblSelectCalendarNext.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectCalendarNext.setBounds(10, 314, 236, 14);
		contentPaneReminder.add(lblSelectCalendarNext);
		
		JLabel lblSelectCalendarYear = new JLabel("Select Calendar Year:");
		lblSelectCalendarYear.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectCalendarYear.setBounds(10, 348, 236, 14);
		contentPaneReminder.add(lblSelectCalendarYear);
		
		//initiate the global variable of the shortcut keys
		Global defaultKey = new Global();
		
		//combo box for show/hide task manager shorcut key selection
		comboBoxShow = new JComboBox();
		comboBoxShow.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}));
		comboBoxShow.setBounds(256, 9, 57, 20);
		contentPaneReminder.add(comboBoxShow);
		
		int n = 0;
		int keyInt = 0;
		
		n = comboBoxShow.getItemCount();
		
		for (int i = 0; i < n; i++) {
		
			Object currentObject = comboBoxShow.getItemAt(i);
			
		    if(currentObject.toString().equals(String.valueOf(defaultKey.activateKey))) {		    	
		    	
		    	comboBoxShow.setSelectedIndex(i);//auto get selected of the current setted key
		
		    }
		
		}

		//combo box for day panel shorcut key selection
		comboBoxDay = new JComboBox();
		comboBoxDay.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}));
		comboBoxDay.setBounds(256, 44, 57, 20);
		contentPaneReminder.add(comboBoxDay);
		
		n = comboBoxDay.getItemCount();
		
		for (int i = 0; i < n; i++) {
		
			Object currentObject = comboBoxDay.getItemAt(i);
			
		    if(currentObject.toString().equals(String.valueOf(defaultKey.dayPanelKey))) {		    	
		    	
		    	comboBoxDay.setSelectedIndex(i);
		
		    }
		
		}

		//combo box for week panel shorcut key selection
		comboBoxWeek = new JComboBox();
		comboBoxWeek.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}));
		comboBoxWeek.setBounds(256, 77, 57, 20);
		contentPaneReminder.add(comboBoxWeek);
		
		n = comboBoxWeek.getItemCount();
		
		for (int i = 0; i < n; i++) {
		
			Object currentObject = comboBoxWeek.getItemAt(i);
			
		    if(currentObject.toString().equals(String.valueOf(defaultKey.weekPanelKey))) {		    	
		    	
		    	comboBoxWeek.setSelectedIndex(i);
		
		    }
		
		}
		
		//combo box for month panel shorcut key selection
		comboBoxMonth = new JComboBox();
		comboBoxMonth.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}));
		comboBoxMonth.setBounds(256, 110, 57, 20);
		contentPaneReminder.add(comboBoxMonth);
		
		n = comboBoxMonth.getItemCount();
		
		for (int i = 0; i < n; i++) {
		
			Object currentObject = comboBoxMonth.getItemAt(i);
			
		    if(currentObject.toString().equals(String.valueOf(defaultKey.monthPanelKey))) {		    	
		    	
		    	comboBoxMonth.setSelectedIndex(i);
		
		    }
		
		}
		
		//combo box for event panel shorcut key selection
		comboBoxEvent = new JComboBox();
		comboBoxEvent.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}));
		comboBoxEvent.setBounds(256, 143, 57, 20);
		contentPaneReminder.add(comboBoxEvent);
		
		n = comboBoxEvent.getItemCount();
		
		for (int i = 0; i < n; i++) {
		
			Object currentObject = comboBoxEvent.getItemAt(i);
			
		    if(currentObject.toString().equals(String.valueOf(defaultKey.eventPanelKey))) {		    	
		    	
		    	comboBoxEvent.setSelectedIndex(i);
		
		    }
		
		}
		
		//combo box for calendar date shorcut key selection
		comboBoxDate = new JComboBox();
		comboBoxDate.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}));
		comboBoxDate.setBounds(256, 206, 57, 20);
		contentPaneReminder.add(comboBoxDate);
		
		n = comboBoxDate.getItemCount();
		
		for (int i = 0; i < n; i++) {
		
			Object currentObject = comboBoxDate.getItemAt(i);
			
		    if(currentObject.toString().equals(String.valueOf(defaultKey.calendarDateKey))) {		    	
		    	
		    	comboBoxDate.setSelectedIndex(i);
		
		    }
		
		}
		
		//combo box for command box shorcut key selection
		comboBoxCMD = new JComboBox();
		comboBoxCMD.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}));
		comboBoxCMD.setBounds(256, 244, 57, 20);
		contentPaneReminder.add(comboBoxCMD);
		
		n = comboBoxCMD.getItemCount();
		
		for (int i = 0; i < n; i++) {
		
			Object currentObject = comboBoxCMD.getItemAt(i);
			
		    if(currentObject.toString().equals(String.valueOf(defaultKey.commandKey))) {		    	
		    	
		    	comboBoxCMD.setSelectedIndex(i);
		
		    }
		
		}
		
		//combo box for calendar previous month shorcut key selection
		comboBoxPrev = new JComboBox();
		comboBoxPrev.setModel(new DefaultComboBoxModel(new String[] {"Page Up", "Page Down", "Home", "End", "F9", "F10", "F11"}));
		comboBoxPrev.setBounds(212, 275, 101, 20);
		contentPaneReminder.add(comboBoxPrev);
		
		n = comboBoxPrev.getItemCount();
		
		for (int i = 0; i < n; i++) {
		
			Object currentObject = comboBoxPrev.getItemAt(i);
			
			if(currentObject.toString().equals("Page Up")) keyInt = 33;
			else if(currentObject.toString().equals("Page Down")) keyInt = 34;
			else if(currentObject.toString().equals("Home")) keyInt = 36;
			else if(currentObject.toString().equals("Insert")) keyInt = 155;
			else if(currentObject.toString().equals("End")) keyInt = 35;
			else if(currentObject.toString().equals("F9")) keyInt = 120;
			else if(currentObject.toString().equals("F10")) keyInt = 121;
			else if(currentObject.toString().equals("F11")) keyInt = 122;

			
		    if(keyInt == defaultKey.calendarPrevMKey) {		    	
		    	
		    	comboBoxPrev.setSelectedIndex(i);
		
		    }
		
		}
		
		//combo box for calendar next month shorcut key selection
		comboBoxNext = new JComboBox();
		comboBoxNext.setModel(new DefaultComboBoxModel(new String[] {"Page Up", "Page Down", "Home", "End", "F9", "F10", "F11"}));
		comboBoxNext.setBounds(212, 312, 101, 20);
		contentPaneReminder.add(comboBoxNext);
		
		n = comboBoxNext.getItemCount();
		
		for (int i = 0; i < n; i++) {
		
			Object currentObject = comboBoxNext.getItemAt(i);
			
			if(currentObject.toString().equals("Page Up")) keyInt = 33;
			else if(currentObject.toString().equals("Page Down")) keyInt = 34;
			else if(currentObject.toString().equals("Home")) keyInt = 36;
			else if(currentObject.toString().equals("Insert")) keyInt = 155;
			else if(currentObject.toString().equals("End")) keyInt = 35;
			else if(currentObject.toString().equals("F9")) keyInt = 120;
			else if(currentObject.toString().equals("F10")) keyInt = 121;
			else if(currentObject.toString().equals("F11")) keyInt = 122;

			
		    if(keyInt == defaultKey.calendarNextMKey) {		    	
		    	
		    	comboBoxNext.setSelectedIndex(i);
		
		    }
		
		}
		
		//combo box for search panel shorcut key selection
		comboBoxSearch = new JComboBox();
		comboBoxSearch.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}));
		comboBoxSearch.setBounds(256, 174, 57, 20);
		contentPaneReminder.add(comboBoxSearch);
		
		n = comboBoxSearch.getItemCount();
		
		for (int i = 0; i < n; i++) {
		
			Object currentObject = comboBoxSearch.getItemAt(i);
			
		    if(currentObject.toString().equals(String.valueOf(defaultKey.searchPanelKey))) {		    	
		    	
		    	comboBoxSearch.setSelectedIndex(i);
		
		    }
		
		}
		
		//combo box for calendar year shorcut key selection
		comboBoxYear = new JComboBox();
		comboBoxYear.setModel(new DefaultComboBoxModel(new String[] {"Page Up", "Page Down", "Home", "End", "F9", "F10", "F11"}));
		comboBoxYear.setBounds(212, 346, 101, 20);
		contentPaneReminder.add(comboBoxYear);
		
		n = comboBoxYear.getItemCount();
		
		for (int i = 0; i < n; i++) {
		
			Object currentObject = comboBoxYear.getItemAt(i);
			
			if(currentObject.toString().equals("Page Up")) keyInt = 33;
			else if(currentObject.toString().equals("Page Down")) keyInt = 34;
			else if(currentObject.toString().equals("Home")) keyInt = 36;
			else if(currentObject.toString().equals("Insert")) keyInt = 155;
			else if(currentObject.toString().equals("End")) keyInt = 35;
			else if(currentObject.toString().equals("F9")) keyInt = 120;
			else if(currentObject.toString().equals("F10")) keyInt = 121;
			else if(currentObject.toString().equals("F11")) keyInt = 122;

			
		    if(keyInt == defaultKey.calendarYrKey) {		    	
		    	
		    	comboBoxYear.setSelectedIndex(i);
		
		    }
		
		}
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyChar() == e.VK_ENTER) { //if user using keyboard
					
					wrKey();//call the save new key setting method
					
					setVisible(false);//hide the config frame
					
					JOptionPane.showMessageDialog(null, "Shortcut key setting updated. Program restarting to apply setting.", "Message", 1);
					
					Restart restartApp = new Restart();//restart the program to apply new key setting
					
					try {
						restartApp.restartApplication(null);
					} catch (IOException ex) {
						
						ex.printStackTrace();
					}
				
				}
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {//if user using mouse

					wrKey();//call the save new key setting method
					
					setVisible(false);//hide the config frame
					
					JOptionPane.showMessageDialog(null, "Shortcut key setting updated. Program restarting to apply setting.", "Message", 1);
					
					Restart restartApp = new Restart();//restart the program to apply new key setting
					
					try {
						restartApp.restartApplication(null);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
			}
		});
		
		btnNewButton.setBounds(125, 392, 89, 23);
		contentPaneReminder.add(btnNewButton);
		
		JLabel lblSelectSearchPanel = new JLabel("Select Search Panel:                      Ctrl +");
		lblSelectSearchPanel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSelectSearchPanel.setBounds(10, 176, 236, 14);
		contentPaneReminder.add(lblSelectSearchPanel);
			
		setVisible(false);		
		
    } 
	
	//save new key setting method
	public static void wrKey(){
		String prevKey = "", nextKey="", yearKey="";
		KeyConfig keyConfig = new KeyConfig();
		
		if(comboBoxPrev.getSelectedItem().toString().equals("Page Up")) prevKey = "33";
		else if(comboBoxPrev.getSelectedItem().toString().equals("Page Down")) prevKey = "34";
		else if(comboBoxPrev.getSelectedItem().toString().equals("Home")) prevKey = "36";
		else if(comboBoxPrev.getSelectedItem().toString().equals("Insert"))	prevKey = "155";
		else if(comboBoxPrev.getSelectedItem().toString().equals("End")) prevKey = "35";
		else if(comboBoxPrev.getSelectedItem().toString().equals("F9")) prevKey = "120";
		else if(comboBoxPrev.getSelectedItem().toString().equals("F10")) prevKey = "121";
		else if(comboBoxPrev.getSelectedItem().toString().equals("F11")) prevKey = "122";

		
		if(comboBoxNext.getSelectedItem().toString().equals("Page Up")) nextKey = "33";
		else if(comboBoxNext.getSelectedItem().toString().equals("Page Down")) nextKey = "34";
		else if(comboBoxNext.getSelectedItem().toString().equals("Home")) nextKey = "36";
		else if(comboBoxNext.getSelectedItem().toString().equals("Insert"))	nextKey = "155";
		else if(comboBoxNext.getSelectedItem().toString().equals("End")) nextKey = "35";
		else if(comboBoxNext.getSelectedItem().toString().equals("F9")) nextKey = "120";
		else if(comboBoxNext.getSelectedItem().toString().equals("F10")) nextKey = "121";
		else if(comboBoxNext.getSelectedItem().toString().equals("F11")) nextKey = "122";

		
		if(comboBoxYear.getSelectedItem().toString().equals("Page Up")) yearKey = "33";
		else if(comboBoxYear.getSelectedItem().toString().equals("Page Down")) yearKey = "34";
		else if(comboBoxYear.getSelectedItem().toString().equals("Home")) yearKey = "36";
		else if(comboBoxYear.getSelectedItem().toString().equals("Insert"))	yearKey = "155";
		else if(comboBoxYear.getSelectedItem().toString().equals("End")) yearKey = "35";
		else if(comboBoxYear.getSelectedItem().toString().equals("F9")) yearKey = "120";
		else if(comboBoxYear.getSelectedItem().toString().equals("F10")) yearKey = "121";
		else if(comboBoxYear.getSelectedItem().toString().equals("F11")) yearKey = "122";

		
		try{
			//call the save new key setting IO method
			keyConfig.writeKey("~"+comboBoxShow.getSelectedItem().toString()+"~"+comboBoxWeek.getSelectedItem().toString()+
			"~"+comboBoxDay.getSelectedItem().toString()+"~"+comboBoxMonth.getSelectedItem().toString()+"~"+comboBoxDate.getSelectedItem().toString()+"~"+
			comboBoxEvent.getSelectedItem().toString()+"~"+comboBoxCMD.getSelectedItem().toString()+
			"~"+comboBoxSearch.getSelectedItem().toString()+"~"+prevKey+"~"+
			nextKey+"~"+yearKey);
			
		}catch(Exception ex){ex.printStackTrace();}
	}
} 