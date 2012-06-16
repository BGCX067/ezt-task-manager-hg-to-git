/*Author: Yueng Shu Sheng
 * Purpose:This is the UI class for others team members whose computer doesn't support shortcut key function
*/
package ezt.UI;

import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Robot;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Event;
import ezt.DetectInput.*;
import ezt.Export.Export;
import ezt.Reminder.AePlayWave;
import ezt.keyConfig.KeyConfig;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

//This is the UI class for others team members whose computer doesn't support shortcut key function
public class UI_2 extends JFrame{

	private static JPanel contentPane;
	final JTextField commandBox;
	private JLabel lblFilterBy;
	private JLabel lblNewLabel;
	private JLabel lblMediumhighPriority;
	private JLabel lblLowPriority;
	private JTextField exportFilename;
	static JInternalFrame internalFrame;
	private boolean success = true;
	private boolean firstInitPane = true;
	private boolean firstEventPane = true, isSearch = false, noSearch=false;	
	private boolean noFocus = false,isAdd = false, refreshTask = false, refreshEvent = false, isPrioritySearch = false, isNormalSearch=false, isStatusSearch = false;
	private int noOfInputAdd = 0, noOfInputSearch=0, countSearch =1;
	private static String concateAddInput = "", searchVar="", todayDate="";
	private String[] columnNames = {"", "Description", "Priority", "Alert","ID"};
	private String[] columnNamesEvent = {"ID",""};
	private int countFocus=0;

	private static UI_2 frame;
	JTable tableDay;
	static JTable tableWeek;
	JTable tableMonth;
	JTable tableSearch;
	JTable tableEvent, tableEvent2;
	static JTabbedPane tabbedPane;
	JTabbedPane tabbedPane_1;
	JScrollPane scrollPane;
	JScrollPane scrollPane_1;
	JScrollPane scrollPane_2;
	JScrollPane scrollPane_3;
	DefaultTableModel tableModel;	
	DetectInput detectInput;
	String id="", eventID="";
	static JLabel lblMonth, lblYear;
	static JButton btnNewButton_3,btnNewButton_4,btnNewButton_5;
	static JButton btnPrev, btnNext;
	static JTable tblCalendar;
	static JComboBox cmbYear;
	static JFrame frmMain;
	static Container pane;
	static DefaultTableModel mtblCalendar; //Table model
	static JScrollPane stblCalendar; //The scrollpane
	static JPanel pnlCalendar;
	static JLabel hintlbl;
	static int realYear, realMonth, realDay, currentYear, currentMonth;


		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		Calendar todayDate = Calendar.getInstance();
		todayDate.set(Calendar.HOUR_OF_DAY, 0);
		todayDate.set(Calendar.MINUTE, 0);
		todayDate.set(Calendar.SECOND, 0);
		todayDate.set(Calendar.MILLISECOND, 0);
		Date td = todayDate.getTime();
		
		//set a scheduler to run the reminder check every hour
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {	            	
		            	
		            	DetectInput remind = new DetectInput();
		            	if(remind.runReminder()) {new frameReminder();}		            	
		            	
		            }
		        }, 
		       td, 3600000 //refresh in every 10 secs for demonstration purpose, 10000 
		);
		
		//3600000-every hour will check the task today to remind or not remind again
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					KeyConfig keyConfig = new KeyConfig();
					keyConfig.getKey();
					
					
					
					frame = new UI_2();//frame which hold the task UI
					frame.setVisible(false);
					
										
				} catch (Exception e) {
			        System.out.println("Error in UI: "+e);
			    }		
				
			}
		});
	}

	  
	
	public void resetVar(){
		
		success = true;
		firstInitPane = true;
		firstEventPane = true;
		isSearch = false;
		noSearch=false;	
		isAdd = false;
		refreshTask = false;
		refreshEvent = false;
		isPrioritySearch = false;
		isNormalSearch=false;
		isStatusSearch = false;
		noOfInputAdd = 0;
		noOfInputSearch=0;
		Global.shortCut=1;
		countSearch =1;
		concateAddInput = "";
		searchVar="";
		todayDate="";

	}   
	
	public UI_2() {
				
		setTitle("EZ Task Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 80, 760, 680);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);	
		DetectInput searchWord = new DetectInput();
		
		
		internalFrame = new JInternalFrame("Actions");
		internalFrame.setClosable(true);
		
		internalFrame.setBounds(313, 26, 341, 148);
		contentPane.add(internalFrame);
		internalFrame.getContentPane().setLayout(null);
		internalFrame.setVisible(false);
		JLabel lblPleaseSelectYour = new JLabel("Please select your action:");
		lblPleaseSelectYour.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPleaseSelectYour.setBounds(10, 21, 181, 14);
		internalFrame.getContentPane().add(lblPleaseSelectYour);
		
		btnNewButton_3 = new JButton("Update");
		btnNewButton_3.addKeyListener(new KeyAdapter() {
			@Override//if enter key pressed, UI will send command to the DetectInput class
			public void keyPressed(KeyEvent e) {
					
				if(e.getKeyChar() == e.VK_ENTER) { 	
					
					detectInput = new DetectInput();
					
					commandBox.setText(detectInput.concateUpdateString(id));
					
					commandBox.requestFocusInWindow();
					
					internalFrame.setVisible(false);
				
				}
			}});
		
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			
			@Override//Print the update command in the textbox if update button clicked 
			public void mouseClicked(MouseEvent arg0) {				
				
				detectInput = new DetectInput();
								
				commandBox.setText(detectInput.concateUpdateString(id));
				
				commandBox.requestFocusInWindow();
				
				internalFrame.setVisible(false);
			}
		});
		btnNewButton_3.setBounds(20, 83, 89, 23);
		internalFrame.getContentPane().add(btnNewButton_3);
		
		btnNewButton_4 = new JButton("Delete");
		
		btnNewButton_4.addKeyListener(new KeyAdapter() {
			@Override//if enter key pressed, UI will send command to the DetectInput class
			public void keyPressed(KeyEvent e) {
					
				if(e.getKeyChar() == e.VK_ENTER) { 	
					
					commandBox.setText("delete,"+id);
					commandBox.requestFocusInWindow();
					internalFrame.setVisible(false);
				
				}
			}});
		
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			
			@Override//Print the delete command in the textbox if delete button clicked
			public void mouseClicked(MouseEvent e) {
				commandBox.setText("delete,"+id);
				commandBox.requestFocusInWindow();
				internalFrame.setVisible(false);
			}
		});
		btnNewButton_4.setBounds(119, 83, 89, 23);
		internalFrame.getContentPane().add(btnNewButton_4);
		
		btnNewButton_5 = new JButton("Status");
		
		btnNewButton_5.addKeyListener(new KeyAdapter() {
			@Override//if enter key pressed, UI will send command to the DetectInput class
			public void keyPressed(KeyEvent e) {
					
				if(e.getKeyChar() == e.VK_ENTER) { 	
					commandBox.setText("status,"+id+",Non Active");
					commandBox.requestFocusInWindow();
					internalFrame.setVisible(false);
				
				}
			}});
		
		btnNewButton_5.addMouseListener(new MouseAdapter() {
			
			@Override//Print the update status command in the textbox if update status button clicked
			public void mouseClicked(MouseEvent e) {
				commandBox.setText("status,"+id+",Non Active");
				commandBox.requestFocusInWindow();
				internalFrame.setVisible(false);
			}
		});
		btnNewButton_5.setBounds(219, 83, 89, 23);
		internalFrame.getContentPane().add(btnNewButton_5);
		
		Global.internalFrame_1 = new JInternalFrame("Export");
		Global.internalFrame_1.setBounds(100, 300, 450, 200);
		contentPane.add(Global.internalFrame_1);
		Global.internalFrame_1.getContentPane().setLayout(null);		
		
		JLabel lblPleaseEnterThe = new JLabel("Please enter filename:");
		lblPleaseEnterThe.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPleaseEnterThe.setBounds(10, 11, 322, 14);
		Global.internalFrame_1.getContentPane().add(lblPleaseEnterThe);
		
		exportFilename = new JTextField();
		exportFilename.setBounds(10, 36, 200, 20);
		Global.internalFrame_1.getContentPane().add(exportFilename);
		exportFilename.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Please select which to export:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 11, 321, 150);
		Global.internalFrame_1.getContentPane().add(lblNewLabel_1);
		
		JButton btnHtml = new JButton("HTML");
		btnHtml.setBounds(20, 130, 89, 20);
		btnHtml.addKeyListener(new KeyAdapter() {
			@Override//if enter key pressed, UI will send command to the DetectInput class
			public void keyPressed(KeyEvent e) {
					
				if(e.getKeyChar() == e.VK_ENTER) { 	
        			
					Export export = new Export();
				
					try{
					
						export.ExportHTML(exportFilename.getText());  
											
						Global.exportCheck = "";
						
					}
				
					catch (Exception exx) {System.out.println(exx);
					}
					Global.internalFrame_1.setVisible(false);
				
				}
			}});
		
		btnHtml.addMouseListener(new MouseAdapter() {
			
			@Override//Print the update status command in the textbox if update status button clicked
			public void mouseClicked(MouseEvent e) {
				
				
    			
				Export export = new Export();
			
				try{
				
					export.ExportHTML(exportFilename.getText());  
										
					Global.exportCheck = "";
					
				}
			
				catch (Exception exx) {System.out.println(exx);
				}
				Global.internalFrame_1.setVisible(false);
			}
		});
		
		Global.internalFrame_1.getContentPane().add(btnHtml);
		
		JButton btnText = new JButton("Text");
		btnText.setBounds(136, 130, 89, 23);
		btnText.addKeyListener(new KeyAdapter() {
			@Override//if enter key pressed, UI will send command to the DetectInput class
			public void keyPressed(KeyEvent e) {
					
				if(e.getKeyChar() == e.VK_ENTER) { 	
					
					
        			
					Export export = new Export();
				
					try{
					
						export.ExportTXT(exportFilename.getText()); 
											
						Global.exportCheck = "";
						
					}
				
					catch (Exception exx) {System.out.println(exx);
					}
					Global.internalFrame_1.setVisible(false);
				
				}
			}});
		
		btnText.addMouseListener(new MouseAdapter() {
			
			@Override//Print the update status command in the textbox if update status button clicked
			public void mouseClicked(MouseEvent e) {
				
				
    			
				Export export = new Export();
			
				try{
				
					export.ExportTXT(exportFilename.getText());  
										
					Global.exportCheck = "";
					
				}
			
				catch (Exception exx) {System.out.println(exx);
				}
				Global.internalFrame_1.setVisible(false);
			}
		});
		
		Global.internalFrame_1.getContentPane().add(btnText);
		
		JButton btnBoth = new JButton("Both");
		btnBoth.setBounds(251, 130, 89, 23);
		btnBoth.addKeyListener(new KeyAdapter() {
			@Override//if enter key pressed, UI will send command to the DetectInput class
			public void keyPressed(KeyEvent e) {
					
				if(e.getKeyChar() == e.VK_ENTER) { 	
					
					
        			
					Export export = new Export();
				
					try{
					
						export.ExportHTML(exportFilename.getText());  
						export.ExportTXT(exportFilename.getText()); 
											
						Global.exportCheck = "";
						
					}
				
					catch (Exception exx) {System.out.println(exx);
					}
					Global.internalFrame_1.setVisible(false);
				
				}
			}});
		
		btnBoth.addMouseListener(new MouseAdapter() {
			
			@Override//Print the update status command in the textbox if update status button clicked
			public void mouseClicked(MouseEvent e) {
				
				
    			
				Export export = new Export();
			
				try{
				
					export.ExportHTML(exportFilename.getText());  
					export.ExportTXT(exportFilename.getText());
										
					Global.exportCheck = "";
					
				}
			
				catch (Exception exx) {System.out.println(exx);
				}
				Global.internalFrame_1.setVisible(false);
			}
		});
		
		Global.internalFrame_1.getContentPane().add(btnBoth);
		Global.internalFrame_1.setVisible(false);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(UI.class.getResource("/ezt/UI/logo.png")));//program logo
		label.setBounds(10, 11, 277, 50);
		contentPane.add(label);
		
		JLabel lblCommand = new JLabel("Command");
		lblCommand.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblCommand.setBounds(26, 550, 89, 14);
		contentPane.add(lblCommand);
		
		lblFilterBy = new JLabel("Search By:");
		lblFilterBy.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblFilterBy.setBounds(459, 380, 81, 20);
		contentPane.add(lblFilterBy);
		
		lblNewLabel = new JLabel("High Priority");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				searchClick("-phigh");
				
			}
		});
		lblNewLabel.setIcon(new ImageIcon(UI.class.getResource("/ezt/UI/red light.png")));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel.setBounds(537, 380, 106, 20);
		contentPane.add(lblNewLabel);
		
		lblMediumhighPriority = new JLabel("Medium Priority");
		lblMediumhighPriority.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				searchClick("-pmedium");
			}
		});
		lblMediumhighPriority.setIcon(new ImageIcon(UI.class.getResource("/ezt/UI/yellow light.png")));
		lblMediumhighPriority.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblMediumhighPriority.setBounds(537, 399, 120, 20);
		contentPane.add(lblMediumhighPriority);
		
		lblLowPriority = new JLabel("Low Priority");
		lblLowPriority.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				searchClick("-plow");
			}
		});
		lblLowPriority.setIcon(new ImageIcon(UI.class.getResource("/ezt/UI/green light.png")));
		lblLowPriority.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblLowPriority.setBounds(537, 418, 120, 20);
		contentPane.add(lblLowPriority);
		
		commandBox = new JTextField();
		
		boolean strictMatching = false;//set command box not only allow user to key in matched keyword in word list  
		     
		//create auto complete for command box
		//the saved word list is in the wordList.txt
		AutoCompleteDecorator.decorate(commandBox,searchWord.readWordList(),strictMatching);  
		
		commandBox.setText("Please enter your command here");
		commandBox.addFocusListener(new FocusAdapter() {
			@Override//default text in textbox if it is unfocused
			public void focusLost(FocusEvent arg0) {
				commandBox.setText("Please enter your command here.");
				
				isAdd = false;
				noOfInputAdd = 0;
			}
			@Override//clear default text in textbox if it is focused
			public void focusGained(FocusEvent e) {
				if(commandBox.hasFocus() && noFocus == true){
					btnNewButton_3.requestFocusInWindow();
					noFocus=false;
				}
				
				countFocus++;
				if(commandBox.getText().equalsIgnoreCase("Please enter your command here.") || countFocus ==1){
				commandBox.setText("");
				
				}
			}
		});
		
		commandBox.setBounds(26, 575, 416, 20);
		contentPane.add(commandBox);
		commandBox.setColumns(10);
		
		contentPane.setLayout(null); //Apply null layout

		//Create controls
		lblMonth = new JLabel ("January");
		lblYear = new JLabel ("Change year:");
		cmbYear = new JComboBox();
		btnPrev = new JButton ("<<");
		btnNext = new JButton (">>");
		mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		pnlCalendar = new JPanel(null);

		//Set border
		pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
		
		//Register action listeners
		btnPrev.addActionListener(new btnPrev_Action());
		btnPrev.addKeyListener(new KeyAdapter() {
			@Override//if enter key pressed, UI will send command to the DetectInput class
			public void keyPressed(KeyEvent e) {
					
				if(e.getKeyChar() == e.VK_ENTER) {
					if (currentMonth == 0){ //Back one year
						currentMonth = 11;
						currentYear -= 1;
					}
					else{ //Back one month
						currentMonth -= 1;
					}
					refreshCalendar(currentMonth, currentYear);
				}
			}});
				
		btnNext.addActionListener(new btnNext_Action());
		btnNext.addKeyListener(new KeyAdapter() {
			@Override//if enter key pressed, UI will send command to the DetectInput class
			public void keyPressed(KeyEvent e) {
					
				if (currentMonth == 11){ //Foward one year
					currentMonth = 0;
					currentYear += 1;
				}
				else{ //Foward one month
					currentMonth += 1;
				}
				refreshCalendar(currentMonth, currentYear);
			}});
		cmbYear.addActionListener(new cmbYear_Action());
		
		//Add controls to pane
		contentPane.add(pnlCalendar);
		pnlCalendar.add(lblMonth);
		pnlCalendar.add(lblYear);
		pnlCalendar.add(cmbYear);
		pnlCalendar.add(btnPrev);
		pnlCalendar.add(btnNext);
		pnlCalendar.add(stblCalendar);
		
		//Set bounds
		pnlCalendar.setBounds(459, 117, 260, 250);
		lblMonth.setBounds(130-lblMonth.getPreferredSize().width/2, 25, 80, 25);
		lblYear.setBounds(10, 220, 80, 20);
		cmbYear.setBounds(170, 220, 80, 20);
		btnPrev.setBounds(10, 25, 50, 25);
		btnNext.setBounds(200, 25, 50, 25);
		stblCalendar.setBounds(10, 50, 240, 147);
		
		
		//Get real month/year
		GregorianCalendar cal = new GregorianCalendar(); //Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		realMonth = cal.get(GregorianCalendar.MONTH); //Get month
		realYear = cal.get(GregorianCalendar.YEAR); //Get year
		currentMonth = realMonth; //Match month and year
		currentYear = realYear;
		
		//Add headers
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		for (int i=0; i<7; i++){
			mtblCalendar.addColumn(headers[i]);
		}
		
		tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background

		//No resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
		tblCalendar.setRowHeight(20);
		
		hintlbl = new JLabel("");
		hintlbl.setBounds(26, 604, 416, 20);
		contentPane.add(hintlbl);
		
		mtblCalendar.setColumnCount(7);
		mtblCalendar.setRowCount(6);
		
		//Populate table
		for (int i=realYear-100; i<=realYear+100; i++){
			cmbYear.addItem(String.valueOf(i));
		}
		
		//Refresh calendar
		refreshCalendar (realMonth, realYear); //Refresh calendar
		
		initPane(todayDate);//initiate the task panel
		callEventPane();//initiate the event panel
		
		commandBox.addKeyListener(new KeyAdapter() {
			@Override//if enter key pressed, UI will send command to the DetectInput class
			public void keyPressed(KeyEvent e) {
					
				if(e.getKeyChar() == e.VK_ENTER) { 				
					
					try{
						
						String input = commandBox.getText();	
						
						if(input.replaceAll(" ", "").equalsIgnoreCase("")){
							
							JOptionPane.showMessageDialog(null, "No command received, please enter command in the command box.", "Message", 1);
							
						}else if((isStatusSearch != true && isNormalSearch != true) && (input.replaceAll(" ", "").equalsIgnoreCase("s-p") || countSearch ==2)){
						
							isPrioritySearch = true;
							
							if(countSearch==2){noOfInputSearch++;}
							
							if(countSearch==1){
								commandBox.setText("Priority:");
								countSearch++;
							}
							
							if(noOfInputSearch == 1 && countSearch == 2){
									
								searchVar = "-p"+commandBox.getText().substring(9);
								
															
								initPane(todayDate);
								
								int w =0;
								w = tabbedPane.getTabCount();
								
								for(;w>4;w--){
								
									tabbedPane.remove(0);
								}								

								//auto open the search result pane if a search word is entered by user
								tabbedPane.setSelectedIndex(3);
										
								isPrioritySearch = false;
								
								success = true;
							
								noSearch = true;
								
								countSearch --;
								
								commandBox.setText("");
								
								noOfInputSearch --;
								
								searchVar="";
							}
							
						}else if((isPrioritySearch != true && isStatusSearch!=true) && (input.replaceAll(" ", "").equalsIgnoreCase("s") || countSearch ==2)){
						
							isNormalSearch = true;
							
							if(countSearch==2){noOfInputSearch++;}
							
							if(countSearch==1){
								commandBox.setText("Description:");
								countSearch++;
							}
							
							if(noOfInputSearch == 1 && countSearch == 2){
									
								searchVar = commandBox.getText().substring(12);
															
								initPane(todayDate);
								
								int w =0;
								w = tabbedPane.getTabCount();
								
								for(;w>4;w--){
								
									tabbedPane.remove(0);
								}	
								
								//auto open the search result pane if a search word is entered by user
								tabbedPane.setSelectedIndex(3);
								
								isNormalSearch = false;
								
								success = true;
							
								noSearch = true;
								
								countSearch --;
								
								commandBox.setText("");
								
								noOfInputSearch --;
								
								searchVar="";
							}
							
						}else if((isPrioritySearch != true && isNormalSearch != true) && input.replaceAll(" ", "").equalsIgnoreCase("s-s") || countSearch ==2){
						
							isStatusSearch = true;
							
							if(countSearch==2){noOfInputSearch++;}
							
							if(countSearch==1){
								commandBox.setText("Status:");
								countSearch++;
							}
							
							if(noOfInputSearch == 1 && countSearch == 2){
									
								searchVar = "-s"+commandBox.getText().substring(7);
							
								initPane(todayDate);
								
								int w =0;
								w = tabbedPane.getTabCount();
								
								for(;w>4;w--){
								
									tabbedPane.remove(0);
								}	
								
								//auto open the search result pane if a search word is entered by user
								tabbedPane.setSelectedIndex(3);
								
								isStatusSearch = false;
								
								success = true;
							
								noSearch = true;
								
								countSearch --;
								
								commandBox.setText("");
								
								noOfInputSearch --;
								
								searchVar="";
							}
							
						}else{
							
							if(input.substring(0,2).equalsIgnoreCase("em")){//set email address to receive email alert
				  				
								DetectInput setEmails = new DetectInput();
													
								success =  setEmails.setEmail(input.substring(2));
								
								   
							}else if(input.substring(0,2).equalsIgnoreCase("hp")){//set hp no to receive sms alert
				  				
								DetectInput setHpNo = new DetectInput();
													
								success =  setHpNo.setHpNo(input.substring(2));
								
								   //print some default add task command to user
							}else if(input.equalsIgnoreCase("a") || input.equalsIgnoreCase("add")  || isAdd == true){
							
								isAdd = true;
								
								noOfInputAdd ++;
															
								if(noOfInputAdd == 1){
									
									commandBox.setText("Description:");
									
								}else if(noOfInputAdd == 2){
									
									concateAddInput += "add," + (commandBox.getText()).substring(12);
								
									commandBox.setText("Date (From):");
									hintlbl.setText("Hints: DD-MMM-YY");
									
								}else if(noOfInputAdd == 3){
									
									concateAddInput += ",from " + (commandBox.getText()).substring(12);
								
									commandBox.setText("Date (To):");
									hintlbl.setText("Hints: DD-MMM-YY");
									
								}else if(noOfInputAdd == 4){
									
									concateAddInput += " to " + (commandBox.getText()).substring(10) ;
									
									commandBox.setText("Time:");
									hintlbl.setText("Hints: nil/hh-hh");
									
								}else if(noOfInputAdd == 5){
																	
									//if user enter nil for time of the task, it considers as an event, else a task
									if(commandBox.getText().substring(5).equalsIgnoreCase("nil")){
										
										refreshEvent = true;
										
									}else{
										
										refreshTask = true;
										
									}
										
									concateAddInput += "," + (commandBox.getText()).substring(5);
									
									commandBox.setText("Priority:");
									hintlbl.setText("Hints: high/medium/low");
									
								}else if(noOfInputAdd == 6){
									
									concateAddInput += "," + (commandBox.getText()).substring(9);
									
									commandBox.setText("Alert:");
									hintlbl.setText("Hints: yes/no");
									
								}else if(noOfInputAdd == 7){
									
									concateAddInput += "," + (commandBox.getText()).replace(" ", "").substring(6);
									
									hintlbl.setText("");
									
									noOfInputAdd = 0;
																	
									isAdd = false;
									
									DetectInput detectInput = new DetectInput();							
									
									success = detectInput.detect(concateAddInput);								
									
									concateAddInput="";
								}
								
							}else{
								
								detectInput = new DetectInput();									
								
								if(detectInput.isEvent(id)){
									
									refreshEvent = true;
									
								}else{
									
									refreshTask = true;
									
								}
								
								success = detectInput.detect(input);
								
							}
							
							if(success == true && isAdd == false && !Global.exportCheck.equalsIgnoreCase("export")){
								
								JOptionPane.showMessageDialog(null, "Action performed successfully.", "Message", 1);
								
								//refresh the task panel after command issue successfully 
								if(refreshTask == true){
									
									tabbedPane.remove(0);
									tabbedPane.remove(0);
									tabbedPane.remove(0);
								
									initPane(todayDate);
									
									
									refreshTask = false;
									
								}
								
								//refresh the event panel after command issue successfully
								if(refreshEvent == true){
								
									tabbedPane_1.remove(0);									
									
									callEventPane();
									
									refreshEvent = false;
								}
									
							}else if(success == false && isAdd == false && !Global.exportCheck.equalsIgnoreCase("export")){
								
								JOptionPane.showMessageDialog(null, "Action performed failed. Syntax error or time conflict with previous tasks.", "Message", 1);					
								
								
							}
							
						}
						
					}catch(Exception ex){JOptionPane.showMessageDialog(null, "Action performed failed. Syntax error or time conflict with previous tasks.", "Message", 1);}
					
				} 
				}
			
		});		
		
	}

	public void searchClick(String input){
		
		searchVar = input;		
		
		initPane(todayDate);
		
		int w =0;
		w = tabbedPane.getTabCount();
		
		for(;w>4;w--){
		
			tabbedPane.remove(0);
		}								

		//auto open the search result pane if a search word is entered by user
		tabbedPane.setSelectedIndex(3);
		
	}
	
	public void refreshCalendar(int month, int year){		
		
		//Variables
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som; //Number Of Days, Start Of Month
			
		//Allow/disallow buttons
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
		if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
		lblMonth.setText(months[month]); //Refresh the month label (at the top)
		lblMonth.setBounds(130-lblMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
		cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
		
		//Clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				mtblCalendar.setValueAt(null, i, j);
			}
		}
		
		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			mtblCalendar.setValueAt(i, row, column);
			
		}

		//Apply renderers
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	}

   class tblCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 0 || column == 6){ //Week-end
				setBackground(new Color(184, 148, 127));
			}
			else{ //Week
				setBackground(new Color(255, 255, 255));
			}
			if (value != null){
				if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
					setBackground(new Color(220, 220, 255));
				}
			}

			if (value!=null && selected == true){
                setBackground(new Color(255,10,0));
                
                String month = "", day="";
                                
               if(value.toString().length()==1){ 
                	day = "0" + value.toString();
                }else{ 
                	day = value.toString();
                }
               
                if(currentMonth+1 == 1) month="Jan";
                else if(currentMonth+1 == 2) month="Feb";
                else if(currentMonth+1 == 3) month="Mar";
                else if(currentMonth+1 == 4) month="Apr";
                else if(currentMonth+1 == 5) month="May";
                else if(currentMonth+1 == 6) month="Jun";
                else if(currentMonth+1 == 7) month="Jul";
                else if(currentMonth+1 == 8) month="Aug";
                else if(currentMonth+1 == 9) month="Sep";
                else if(currentMonth+1 == 10) month="Oct";
                else if(currentMonth+1 == 11) month="Nov";
                else if(currentMonth+1 == 12) month="Dec";
                                              
                todayDate = value.toString() + "-" + month + "-" + (currentYear-2000);
                               
                tabbedPane.remove(0);
				tabbedPane.remove(0);
				tabbedPane.remove(0);
			
				initPane(todayDate);
				
				tabbedPane_1.remove(0);									
				
				callEventPane();
				
			}
			
			setBorder(null);
			setForeground(Color.black);
			return this;  
		}
	}

	 class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 0){ //Back one year
				currentMonth = 11;
				currentYear -= 1;
			}
			else{ //Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 11){ //Foward one year
				currentMonth = 0;
				currentYear += 1;
			}
			else{ //Foward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
   class cmbYear_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (cmbYear.getSelectedItem() != null){
				String b = cmbYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}
	
	//method to construct the task display pane
	public void initPane(String todayDate){
		
		try{
			
			
		if(firstInitPane == true){
			
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 13));
			tabbedPane.setBounds(26, 94, 416, 430);
			contentPane.add(tabbedPane);
			
			firstInitPane = false;
			
		}
		
		detectInput = new DetectInput();				
				
		//create table day & set some row color corresponding to the priority
		tableDay = new JTable(detectInput.allTaskDay(todayDate),columnNames){
			  public Component prepareRenderer(TableCellRenderer renderer,int Index_row, int Index_col) {
				  
				  Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
				 	  	
				    //set the row color which corresponding to the task priority
			  		if(tableDay.getValueAt(Index_row, 2).toString().equalsIgnoreCase("high"))  {			  			
			  			comp.setBackground(Color.red);
			  		}else if(tableDay.getValueAt(Index_row, 2).toString().equalsIgnoreCase("medium"))  {
			  			comp.setBackground(Color.yellow);
			  			
			  		}else if(tableDay.getValueAt(Index_row, 2).toString().equalsIgnoreCase("low"))  {
			  			comp.setBackground(Color.green);
			  		}else if(tableDay.getValueAt(Index_row, 2).toString().equalsIgnoreCase("  "))  {
			  			comp.setBackground(Color.white);
			  		}else if(tableDay.getValueAt(Index_row, 2).toString().contains(" "))  {			  			
			  			comp.setBackground(Color.gray);			  			
			  		}else{
			  			comp.setBackground(Color.white);
			  		}
			  	
			  		return comp;
			  		
			  }};		
		
		
		
		tableDay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//create table week & set some row color corresponding to the priority
		tableWeek = new JTable(detectInput.allTaskWeek(todayDate),columnNames){
			  public Component prepareRenderer(TableCellRenderer renderer,int Index_row, int Index_col) {
				  
				  Component comp = super.prepareRenderer(renderer, Index_row, Index_col);

				  if(tableWeek.getValueAt(Index_row, 2)!=null){
				    //set the row color which corresponding to the task priority
			  		if(tableWeek.getValueAt(Index_row, 2).toString().equalsIgnoreCase("high"))  {
			  			comp.setBackground(Color.red);
			  		}else if(tableWeek.getValueAt(Index_row, 2).toString().equalsIgnoreCase("medium"))  {
			  			comp.setBackground(Color.yellow);
			  		}else if(tableWeek.getValueAt(Index_row, 2).toString().equalsIgnoreCase("low"))  {
			  			comp.setBackground(Color.green);
			  		}else if(tableDay.getValueAt(Index_row, 2).toString().contains(" "))  {			  			
			  			comp.setBackground(Color.gray);			  			
			  		}else{
			  			comp.setBackground(Color.white);
			  		}
				  }else{
					  comp.setBackground(Color.white);
				  }
				  
			  		return comp;
		}};
		
		tableWeek.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//create table month & set some row color corresponding to the priority
		tableMonth = new JTable(detectInput.allTaskMonth(todayDate),columnNames){
			  public Component prepareRenderer(TableCellRenderer renderer,int Index_row, int Index_col) {
				  
				  Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
				  			  	
				  if(tableMonth.getValueAt(Index_row, 2)!=null){
				    //set the row color which corresponding to the task priority
			  		if(tableMonth.getValueAt(Index_row, 2).toString().equalsIgnoreCase("high"))  {
			  			comp.setBackground(Color.red);
			  		}else if(tableMonth.getValueAt(Index_row, 2).toString().equalsIgnoreCase("medium"))  {
			  			comp.setBackground(Color.yellow);
			  		}else if(tableMonth.getValueAt(Index_row, 2).toString().equalsIgnoreCase("low"))  {
			  			comp.setBackground(Color.green);
			  		}else if(tableDay.getValueAt(Index_row, 2).toString().contains(" "))  {			  			
			  			comp.setBackground(Color.gray);			  			
			  		}else{
			  			comp.setBackground(Color.white);
			  		}
				  }else{
			  			comp.setBackground(Color.white);
			  		}
				  
			  		return comp;
		}};
		
		tableMonth.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tableDay.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tableWeek.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tableMonth.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		
		tableDay.setRowHeight(25);
		tableWeek.setRowHeight(25);
		tableMonth.setRowHeight(25);
		
		tableDay.getColumnModel().getColumn(0).setMinWidth(50);
		tableDay.getColumnModel().getColumn(1).setMinWidth(180);
		tableDay.getColumnModel().getColumn(2).setMaxWidth(50);
		tableDay.getColumnModel().getColumn(3).setMaxWidth(50);
		tableDay.getColumnModel().getColumn(4).setMaxWidth(0);		
		
		//if a space issued on a task record row, an action frame will prompt
		tableDay.addKeyListener(new KeyAdapter() {
    		public void keyPressed(KeyEvent e) {
    		if(e.getKeyChar() == e.VK_SPACE) {
    			
    	      	if((tableDay.getValueAt(tableDay.getSelectedRows()[0], 1)!="  ")){          		
               
            		outputSelectionDay();
                
            		countFocus+=2;	
    			
          		    ShowMessageDialog();
            		
    	      }}}});
		
		//if left mouse click issued on a task record row, an action frame will prompt
		tableDay.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent arg0) {
    		    			
    	      	if((tableDay.getValueAt(tableDay.getSelectedRows()[0], 1)!="  ")){
            		               
            		outputSelectionDay();
                
            		countFocus+=2;	
            		
          		    ShowMessageDialog();
            		
    	      }}});
		
		//if a space issued on a task record row, an action frame will prompt
		tableWeek.addKeyListener(new KeyAdapter() {
    		public void keyPressed(KeyEvent e) {
    		if(e.getKeyChar() == e.VK_SPACE) {
    			
    	      	if((tableWeek.getValueAt(tableWeek.getSelectedRows()[0], 1)!="  ")){
            		               
            		outputSelectionWeek();
                
            		countFocus+=2;	
    			
          		    ShowMessageDialog();
            		
    	      }}}});

		//if left mouse click issued on a task record row, an action frame will prompt
		tableWeek.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent arg0) {
    		    			
    	      	if((tableWeek.getValueAt(tableWeek.getSelectedRows()[0], 1)!="  ")){
            		               
            		outputSelectionWeek();
                
            		countFocus+=2;	
    			
          		    ShowMessageDialog();
            		
    	      }}});
		
		//if a space issued on a task record row, an action frame will prompt
		tableMonth.addKeyListener(new KeyAdapter() {
    		public void keyPressed(KeyEvent e) {
    		if(e.getKeyChar() == e.VK_SPACE) {
    			
    	      	if((tableMonth.getValueAt(tableMonth.getSelectedRows()[0], 1)!="  ")){
            		               
            		outputSelectionMonth();
                
            		countFocus+=2;	
    			
          		    ShowMessageDialog();
            		
    	      }}}});
		
		//if left mouse click issued on a task record row, an action frame will prompt
		tableMonth.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent arg0) {
    		    			
    	      	if((tableMonth.getValueAt(tableMonth.getSelectedRows()[0], 1)!="  ")){
            		       
            		outputSelectionMonth();
                
            		countFocus+=2;	
    			
          		    ShowMessageDialog();
            		
    	      }}});		
						
		tableWeek.getColumnModel().getColumn(0).setMinWidth(50);
		tableWeek.getColumnModel().getColumn(1).setMinWidth(180);
		tableWeek.getColumnModel().getColumn(2).setMaxWidth(50);
		tableWeek.getColumnModel().getColumn(3).setMaxWidth(50);
		tableWeek.getColumnModel().getColumn(4).setMaxWidth(0);
				
		tableMonth.getColumnModel().getColumn(0).setMinWidth(50);
		tableMonth.getColumnModel().getColumn(1).setMinWidth(180);
		tableMonth.getColumnModel().getColumn(2).setMaxWidth(50);
		tableMonth.getColumnModel().getColumn(3).setMaxWidth(50);
		tableMonth.getColumnModel().getColumn(4).setMaxWidth(0);		

		//own defined comparator for sorting the priority
		Comparator<String> intComp = new Comparator<String>() {
            public int compare(String o1, String o2) {
                
            	String a="",b="";
            	
                if (o1.equalsIgnoreCase("high")) a="1"; 
                else if (o1.equalsIgnoreCase("medium")) a="2";
                else if (o1.equalsIgnoreCase("low")) a="3";
                
                if (o2.equalsIgnoreCase("high")) b="1"; 
                else if (o2.equalsIgnoreCase("medium")) b="2";
                else if (o2.equalsIgnoreCase("low")) b="3";
                
                return a.compareTo(b);
                	                	
            }
        };
        
        //apply table sort model to each table
		TableRowSorter<TableModel> sorterDay = new TableRowSorter<TableModel>(tableDay.getModel());
		TableRowSorter<TableModel> sorterWeek = new TableRowSorter<TableModel>(tableWeek.getModel());
		TableRowSorter<TableModel> sorterMonth = new TableRowSorter<TableModel>(tableMonth.getModel());
		
		//assign the pre-defined priority comparator to each table's column priority, the rest of column will
		//be using the default sort method		
		sorterDay.setComparator(2, intComp);
		sorterWeek.setComparator(2, intComp);
		sorterMonth.setComparator(2, intComp);		
			
		scrollPane = new JScrollPane(tableDay);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		tabbedPane.addTab("Day", null, scrollPane, null);
		
		scrollPane_1 = new JScrollPane(tableWeek);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		tabbedPane.addTab("Week", null, scrollPane_1, null);
		
		scrollPane_2 = new JScrollPane(tableMonth);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		tabbedPane.addTab("Month", null, scrollPane_2, null);
		
		if(!searchVar.equalsIgnoreCase("")){			
			
			//create table search & set some row color corresponding to the priority
			tableSearch = new JTable(detectInput.searchTask(searchVar),columnNames);
			
			tableSearch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableSearch.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		
			tableSearch.setRowHeight(25);

			tableSearch.addKeyListener(new KeyAdapter() {
	    		public void keyPressed(KeyEvent e) {
	    		if(e.getKeyChar() == e.VK_SPACE) {//if a space issued on a task record row, an action frame will prompt
	    			
	    	      	if((tableSearch.getValueAt(tableSearch.getSelectedRows()[0], 1)!="  ")){
	            			               
	            		outputSelectionSearch();
	                
	            		countFocus+=2;	
	    			
	          		    ShowMessageDialog();
	            		
	    	      }}}});
			
			//if left mouse click issued on a task record row, an action frame will prompt
			tableSearch.addMouseListener(new MouseAdapter() {
	    		public void mouseClicked(MouseEvent arg0) {
	    		    			
	    	      	if((tableSearch.getValueAt(tableSearch.getSelectedRows()[0], 1)!="  ")){
	            		
	            		outputSelectionSearch();
	                
	            		countFocus+=2;	
	    			
	          		    ShowMessageDialog();
	            		
	    	      }}});		
								
			tableSearch.getColumnModel().getColumn(0).setMinWidth(50);
			tableSearch.getColumnModel().getColumn(1).setMinWidth(180);
			tableSearch.getColumnModel().getColumn(2).setMaxWidth(50);
			tableSearch.getColumnModel().getColumn(3).setMaxWidth(50);
			tableSearch.getColumnModel().getColumn(4).setMaxWidth(0);
					
			scrollPane_3 = new JScrollPane(tableSearch);
			scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				
			tabbedPane.addTab("Search", null, scrollPane_3, null);
			
			
		}
		
		if(searchVar.equalsIgnoreCase("") && noSearch == true){
			tabbedPane.remove(0);
			tabbedPane.setSelectedIndex(0);			
			noSearch = false;
			
		}
		searchVar="";
		
		}catch(Exception ex){System.out.println(ex);}
	}

	//method to construct the event display pane
	public void callEventPane(){
			
		if(firstEventPane == true){
			
			tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane_1.setFont(new Font("Tahoma", Font.BOLD, 13));
			tabbedPane_1.setBounds(459, 450, 260, 150);
			contentPane.add(tabbedPane_1);
			
			firstEventPane = false;
			
		}
		
		detectInput = new DetectInput();
		
		tableEvent = new JTable(detectInput.allEventToday(todayDate),columnNamesEvent);
		
		//if a space issued on a task record row, an action frame will prompt
		tableEvent.addKeyListener(new KeyAdapter() {
    		public void keyPressed(KeyEvent e) {
    		if(e.getKeyChar() == e.VK_SPACE) {
    			
    	      	if((tableEvent.getValueAt(tableEvent.getSelectedRows()[0], 1)!="  ")){
            		    	      		     
            		outputSelectionEvent();
                
            		countFocus+=2;	
            		    			
          		    ShowMessageDialog();
            		
    	      }}}});
		
		//if left mouse click issued on a task record row, an action frame will prompt
		tableEvent.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent arg0) {
    		    			
    	      	if((tableEvent.getValueAt(tableEvent.getSelectedRows()[0], 1)!="  ")){
            		               
            		outputSelectionEvent();
                
            		countFocus+=2;	
            		
          		    ShowMessageDialog();
          		    
            		
    	      }}});	
		
		
		
		tableEvent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableEvent.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tableEvent.setRowHeight(25);
		tableEvent.getColumnModel().getColumn(0).setMaxWidth(0);		
		
		
		//tableEvent.getSelectionModel().addListSelectionListener(new RowListenerEvent());		
		tableEvent.setShowGrid(false);
		
		scrollPane_3 = new JScrollPane(tableEvent);
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
		tabbedPane_1.addTab("Events Today", null, scrollPane_3, null);
		
	}

    //get the task id in search task panel
    private void outputSelectionSearch() {
    	
        for (int c : tableSearch.getSelectedRows()) {
        	
        	Object o = tableSearch.getValueAt(c, 4);
            id = o.toString();
        }    	
               
    }
    
    //get the task id in day task panel
    private void outputSelectionDay() {
    	
        for (int c : tableDay.getSelectedRows()) {
        	
        	Object o = tableDay.getValueAt(c, 4);
            id = o.toString();
        }    	
               
    }

    //get the task id in week task panel
   private void outputSelectionWeek() {
    	
        for (int c : tableWeek.getSelectedRows()) {
            
        	Object o = tableWeek.getValueAt(c, 4);
            id = o.toString();
        }
        
    }
   
   //get the task id in month task panel
   private void outputSelectionMonth() {
       
       for (int c : tableMonth.getSelectedRows()) {
           
       	Object o = tableMonth.getValueAt(c, 4);
           id = o.toString();
       } 
    
   }
   
   //get the task id in event panel
   private void outputSelectionEvent() {
	   
       for (int c : tableEvent.getSelectedRows()) {
           
       	Object o = tableEvent.getValueAt(c, 0);
           id = o.toString();
       }
       
   }
   
    //the frame which contains update, delete and update status button  
    public void ShowMessageDialog(){
    	
    	internalFrame.setVisible(true);  	
    	
    	if(frame.getFocusOwner().getName()==null){btnNewButton_3.requestFocusInWindow();}
    	
    	noFocus = true;
    }
}//end of frame UI class




