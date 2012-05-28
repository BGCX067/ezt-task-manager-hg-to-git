package ezt.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import com.melloware.jintellitype.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Event;
import ezt.DetectInput.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class UI extends JFrame implements HotkeyListener, IntellitypeListener{

	private JPanel contentPane;
	private JTextField commandBox;
	private JLabel lblFilterBy;
	private JLabel lblNewLabel;
	private JLabel lblMediumhighPriority;
	private JLabel lblLowPriority;
	JInternalFrame internalFrame;
	private boolean success = true;
	private boolean firstInitPane = true;
	private boolean firstEventPane = true, isSearch = false, noSearch=false;	
	private String[] columnNames = {"", "Description", "Priority", "Alert","ID"};
	private String[] columnNamesEvent = {"ID",""};
	private int countFocus=0;
	private boolean isAdd = false, refreshTask = false, refreshEvent = false;
	private int noOfInputAdd = 0, noOfInputSearch=0, shortCut=1, countSearch =1;
	private String concateAddInput = "", searchVar="";
	private static final int CTRL_D = 90;
	private static UI frame, frame2;
	JTable tableDay;
	JTable tableWeek;
	JTable tableMonth;
	JTable tableSearch;
	JTable tableEvent, tableEvent2;
	JTabbedPane tabbedPane;
	JTabbedPane tabbedPane_1;
	JScrollPane scrollPane;
	JScrollPane scrollPane_1;
	JScrollPane scrollPane_2;
	JScrollPane scrollPane_3;
	DefaultTableModel tableModel;
	
	DetectInput detectInput;
	
	String id="", eventID="";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					//initiate ctrl+d to show the program
					JIntellitype.getInstance().registerSwingHotKey(CTRL_D, Event.CTRL_MASK, (int) 'D');
				     				      
					frame = new UI();//frame which hold the task UI
					frame.setVisible(false);
					
					frame2 = new UI();//frame which hold the shortcut key command
					frame2.initJIntellitype();
					frame2.setVisible(false);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	   public void onHotKey(int aIdentifier) {
		   
		      if(Integer.toString(aIdentifier).equalsIgnoreCase("90") && shortCut == 1){
		    	  try{
		    	  
		    		  frame.setVisible(true);//show the task UI if ctrl+d pressed
		    		  
		    		  shortCut +=1;
		    		  
		    	  }catch(Exception ex){System.out.println(ex);}
		    	  
		      }else if(Integer.toString(aIdentifier).equalsIgnoreCase("90") && shortCut == 2){
		    	  try{
		    	 
		    		  frame.setVisible(false);//hide the task UI if ctrl+D pressed again
		    		  
		    		  shortCut -=1;
		    		  
		    	  }catch(Exception ex){System.out.println(ex);}
		      }
		   }
	   
	   public void onIntellitype(int aCommand) {

	      
	   }

	   public void initJIntellitype() {
	      try {

	         // initialize JIntellitype with the frame so all windows commands can
	         // be attached to this window
	         JIntellitype.getInstance().addHotKeyListener(this);
	         JIntellitype.getInstance().addIntellitypeListener(this);
	         
	      } catch (RuntimeException ex) {
	         System.out.println(ex);
	      }
	   }
	   
	public UI() {
				
		setTitle("EZ Task Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 570);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		
		JButton btnNewButton_3 = new JButton("Update");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			
			@Override//Print the update command in the textbox if update button clicked 
			public void mouseClicked(MouseEvent arg0) {				
				
				detectInput = new DetectInput();
								
				commandBox.setText(detectInput.concateUpdateString(id));
				
				internalFrame.setVisible(false);
			}
		});
		btnNewButton_3.setBounds(20, 83, 89, 23);
		internalFrame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Delete");
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			
			@Override//Print the delete command in the textbox if delete button clicked
			public void mouseClicked(MouseEvent e) {
				commandBox.setText("delete,"+id);
				internalFrame.setVisible(false);
			}
		});
		btnNewButton_4.setBounds(119, 83, 89, 23);
		internalFrame.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Status");
		btnNewButton_5.addMouseListener(new MouseAdapter() {
			
			@Override//Print the update status command in the textbox if update status button clicked
			public void mouseClicked(MouseEvent e) {
				commandBox.setText("status,"+id+",Non Active");
				internalFrame.setVisible(false);
			}
		});
		btnNewButton_5.setBounds(219, 83, 89, 23);
		internalFrame.getContentPane().add(btnNewButton_5);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(UI.class.getResource("/ezt/UI/logo.png")));//program logo
		label.setBounds(10, 11, 277, 50);
		contentPane.add(label);
		
		JLabel lblCommand = new JLabel("Command");
		lblCommand.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblCommand.setBounds(26, 470, 89, 14);
		contentPane.add(lblCommand);
		
		lblFilterBy = new JLabel("Filter By:");
		lblFilterBy.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblFilterBy.setBounds(465, 268, 65, 20);
		contentPane.add(lblFilterBy);
		
		lblNewLabel = new JLabel("High Priority");
		lblNewLabel.setIcon(new ImageIcon(UI.class.getResource("/ezt/UI/red light.png")));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel.setBounds(540, 268, 106, 20);
		contentPane.add(lblNewLabel);
		
		lblMediumhighPriority = new JLabel("Medium Priority");
		lblMediumhighPriority.setIcon(new ImageIcon(UI.class.getResource("/ezt/UI/yellow light.png")));
		lblMediumhighPriority.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblMediumhighPriority.setBounds(540, 287, 120, 20);
		contentPane.add(lblMediumhighPriority);
		
		lblLowPriority = new JLabel("Low Priority");
		lblLowPriority.setIcon(new ImageIcon(UI.class.getResource("/ezt/UI/green light.png")));
		lblLowPriority.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblLowPriority.setBounds(540, 306, 120, 20);
		contentPane.add(lblLowPriority);
				
		commandBox = new JTextField();
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
				countFocus++;
				if(commandBox.getText().equalsIgnoreCase("Please enter your command here.") || countFocus ==1){
				commandBox.setText("");
				
				}
			}
		});
		
		commandBox.setBounds(26, 487, 416, 20);
		contentPane.add(commandBox);
		commandBox.setColumns(10);
		
		initPane();//initiate the task panel
		callEventPane();//initiate the event panel
		
		commandBox.addKeyListener(new KeyAdapter() {
			@Override//if enter key pressed, UI will send command to the DetectInput class
			public void keyPressed(KeyEvent e) {
					
				if(e.getKeyChar() == e.VK_ENTER) { 				
					
					try{
						
						String input = commandBox.getText();	
						
						if(input.replaceAll(" ", "").equalsIgnoreCase("")){
							
							JOptionPane.showMessageDialog(null, "No command received, please enter command in the command box.", "Message", 1);
							
						}else if(input.replaceAll(" ", "").equalsIgnoreCase("s") || 
								input.replaceAll(" ", "").equalsIgnoreCase("search") || countSearch ==2){
						
							if(countSearch==2){noOfInputSearch++;}
							
							if(countSearch==1){
								commandBox.setText("Description:");
								countSearch++;
							}
							
							if(noOfInputSearch == 1 && countSearch == 2){
									
								searchVar = commandBox.getText().substring(12);
								
								tabbedPane.remove(0);
								tabbedPane.remove(0);
								tabbedPane.remove(0);
							
								initPane();
								
								//auto open the search result pane if a search word is entered by user
								tabbedPane.setSelectedIndex(3);
										
								success = true;
							
								noSearch = true;
								
								countSearch --;
								
								commandBox.setText("");
								
								noOfInputSearch --;
							}
							
						}else{
							
							//print some default add task command to user
							if(input.equalsIgnoreCase("a") || input.equalsIgnoreCase("add")  || isAdd == true){
							
								isAdd = true;
								
								noOfInputAdd ++;
								
								if(noOfInputAdd == 1){
									
									commandBox.setText("Description:");
									
								}else if(noOfInputAdd == 2){
									
									concateAddInput += "add," + (commandBox.getText()).substring(12);
								
									commandBox.setText("Date:from DD-MMM-YY to DD-MMM-YY");
									
								}else if(noOfInputAdd == 3){
								
									concateAddInput += "," + (commandBox.getText()).substring(5);
									
									commandBox.setText("Time:nil/hh-hh");
									
								}else if(noOfInputAdd == 4){
																	
									//if user enter nil for time of the task, it considers as an event, else a task
									if(commandBox.getText().substring(5).equalsIgnoreCase("nil")){
										
										refreshEvent = true;
										
									}else{
										
										refreshTask = true;
										
									}
										
									concateAddInput += "," + (commandBox.getText()).substring(5);
									
									commandBox.setText("Priority:high/medium/low");
									
								}else if(noOfInputAdd == 5){
									
									concateAddInput += "," + (commandBox.getText()).substring(9);
									
									commandBox.setText("Alert:yes/no");
									
								}else if(noOfInputAdd == 6){
									
									concateAddInput += "," + (commandBox.getText()).replace(" ", "").substring(6);
									
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
							
							if(success == true && isAdd == false){
								
								JOptionPane.showMessageDialog(null, "Action performed successfully.", "Message", 1);
								
								//refresh the task panel after command issue successfully 
								if(refreshTask == true){
									
									tabbedPane.remove(0);
									tabbedPane.remove(0);
									tabbedPane.remove(0);
								
									initPane();
									
									
									refreshTask = false;
									
								}
								
								//refresh the event panel after command issue successfully
								if(refreshEvent == true){
									
									tabbedPane_1.remove(0);									
									
									callEventPane();
									
									refreshEvent = false;
								}
									
							}else if(success == false && isAdd == false){
								
								JOptionPane.showMessageDialog(null, "Action performed failed.", "Message", 1);
								
							}
							
						}
						
					}catch(Exception ex){JOptionPane.showMessageDialog(null, "Action performed failed.", "Message", 1);}
					
				} 
				}
			
		});		
		
	}


	//method to construct the task display pane
	public void initPane(){
		
		try{
		if(firstInitPane == true){
			
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 13));
			tabbedPane.setBounds(26, 94, 416, 349);
			contentPane.add(tabbedPane);
			
			firstInitPane = false;
			
		}
		
		detectInput = new DetectInput();				
				  
		//create table day & set some row color corresponding to the priority
		tableDay = new JTable(detectInput.allTaskDay(),columnNames){
			  public Component prepareRenderer(TableCellRenderer renderer,int Index_row, int Index_col) {
				  
				  Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
				  			  	
				    //set the row color which corresponding to the task priority
			  		if(tableDay.getValueAt(Index_row, 2).toString().equalsIgnoreCase("high"))  {
			  			comp.setBackground(Color.red);
			  		}else if(tableDay.getValueAt(Index_row, 2).toString().equalsIgnoreCase("medium"))  {
			  			comp.setBackground(Color.yellow);
			  		}else if(tableDay.getValueAt(Index_row, 2).toString().equalsIgnoreCase("low"))  {
			  			comp.setBackground(Color.green);
			  		}else{
			  			comp.setBackground(Color.white);
			  		}
			  	
			  		return comp;
		}};		
		
		tableDay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//create table week & set some row color corresponding to the priority
		tableWeek = new JTable(detectInput.allTaskWeek(),columnNames){
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
		tableMonth = new JTable(detectInput.allTaskMonth(),columnNames){
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
		
		tableDay.getSelectionModel().addListSelectionListener(new RowListenerDay());
		tableWeek.getSelectionModel().addListSelectionListener(new RowListenerWeek());
		tableMonth.getSelectionModel().addListSelectionListener(new RowListenerMonth());
				
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
			
		//create table day & set some row color corresponding to the priority
		tableSearch = new JTable(detectInput.searchTask(searchVar),columnNames){
			  public Component prepareRenderer(TableCellRenderer renderer,int Index_row, int Index_col) {
				  
				  Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
				  			  	
				    //set the row color which corresponding to the task priority
			  		if(tableSearch.getValueAt(Index_row, 2).toString().equalsIgnoreCase("high"))  {
			  			comp.setBackground(Color.red);
			  		}else if(tableSearch.getValueAt(Index_row, 2).toString().equalsIgnoreCase("medium"))  {
			  			comp.setBackground(Color.yellow);
			  		}else if(tableSearch.getValueAt(Index_row, 2).toString().equalsIgnoreCase("low"))  {
			  			comp.setBackground(Color.green);
			  		}else{
			  			comp.setBackground(Color.white);
			  		}
			  	
			  		return comp;
			}};		
			
			tableSearch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableSearch.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		
			
			tableSearch.setRowHeight(25);
			
			//tableSearch.getColumnModel().getColumn(0).setMinWidth(50);
			
			tableSearch.getSelectionModel().addListSelectionListener(new RowListenerSearch());
					
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
			tabbedPane_1.setBounds(465, 357, 195, 150);
			contentPane.add(tabbedPane_1);
			
			firstEventPane = false;
			
		}
		
		detectInput = new DetectInput();
		
		tableEvent = new JTable(detectInput.allEventToday(),columnNamesEvent);
		tableEvent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableEvent.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tableEvent.setRowHeight(25);
		tableEvent.getColumnModel().getColumn(0).setMaxWidth(0);		
		tableEvent.getSelectionModel().addListSelectionListener(new RowListenerEvent());		
		tableEvent.setShowGrid(false);
		
		scrollPane_3 = new JScrollPane(tableEvent);
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		tabbedPane_1.addTab("Events Today", null, scrollPane_3, null);
		
	}
	
	
	//EventListener for each data row in the Search Task Panel
    private class RowListenerSearch implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
        	
        	if((tableSearch.getValueAt(tableSearch.getSelectedRows()[0], 1)!="")){
        		
        		if (event.getValueIsAdjusting()) {
        			return;
        		}
           
        		outputSelectionSearch();
            
        		countFocus+=2;
            
        		ShowMessageDialog();
           
        		}
        	}
    }
    
	//EventListener for each data row in the Day Task Panel
    private class RowListenerDay implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
        	
        	if((tableDay.getValueAt(tableDay.getSelectedRows()[0], 1)!="")){
        		
        		if (event.getValueIsAdjusting()) {
        			return;
        		}
           
        		outputSelectionDay();
            
        		countFocus+=2;
            
        		ShowMessageDialog();
           
        		}
        	}
    }
    
  //EventListener for each data row in the Week Task Panel
    private class RowListenerWeek implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
        	if((tableWeek.getValueAt(tableWeek.getSelectedRows()[0], 0)!=null)){
        		
        		if (event.getValueIsAdjusting()) {
        			return;
        		}
           
        		outputSelectionWeek();
            
        		countFocus+=2;
            
        		ShowMessageDialog();
           
        		}
        	}
    }
 
  //EventListener for each data row in the Month Task Panel
    private class RowListenerMonth implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
        	if((tableMonth.getValueAt(tableMonth.getSelectedRows()[0], 0)!=null)){
        		
        		if (event.getValueIsAdjusting()) {
        			return;
        		}
           
        		outputSelectionMonth();
            
        		countFocus+=2;
            
        		ShowMessageDialog();
           
        		}
        	}
    }
    
    //EventListener for each data row in the Event Panel
    private class RowListenerEvent implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
        	if((tableEvent.getValueAt(tableEvent.getSelectedRows()[0], 0)!=null)){
        		
        		if (event.getValueIsAdjusting()) {
        			return;
        		}
           
        		outputSelectionEvent();
            
        		countFocus+=2;
            
        		ShowMessageDialog();
           
        		}
        	}
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
   
   
    public void ShowMessageDialog(){
    	
    	internalFrame.setVisible(true);//the frame which contains update, delete and update status button
    }
}
