package ezt.UI;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import ezt.DetectInput.Global;
import java.awt.Robot;

//this is the very first class to call to run the program, it will activate the program and create system tray
public class Ezt {
	
    public static void main(String[] args) {
       
        try {
                       
            UI ui = new UI();
            ui.main(args);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        
        }
        
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        //adding TrayIcon.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        
    	//Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(createImage("images/bulk.png", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();

        // Create a popup menu components
        MenuItem displayMenu = new MenuItem("Open Task Manager");//show the task manager
        MenuItem aboutItem = new MenuItem("About");//show the program about
        MenuItem infoItem = new MenuItem("Key Config");//show the key configuration manager
        MenuItem helpItem = new MenuItem("Help");//show the help menu
        MenuItem exitItem = new MenuItem("Exit");//exit the program

        //Add components to popup menu
        popup.add(displayMenu);
        popup.add(infoItem);
        popup.add(helpItem);
        popup.add(aboutItem);
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "This dialog box is run from System Tray");
            }
        });

        displayMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	Robot robot;
            	
				try {
				
					robot = new Robot();			

	                // Simulate a key press to activate the task manager
	                robot.keyPress(KeyEvent.VK_CONTROL);
	                robot.keyPress(Global.activateKey);
	                robot.keyRelease(Global.activateKey);
	                robot.keyRelease(KeyEvent.VK_CONTROL);

				} catch (AWTException e1) {
					
					e1.printStackTrace();
				}
            }
        });
        
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Ez Todo Manager\n\nVersion: 0.2\n\n(c) Copyright CS2103 Group J1 2012.\n\nAll rights reserved.");
            }
        });
        
        infoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frameConfig fc = new frameConfig();
            	fc.setVisible(true);           	
                
            }
        });

        helpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	            	
				try {	
					//initiate the desktop object to open the user guide html
					java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
					
					//location of the user guide html
			        java.net.URI uri = new java.net.URI("UserGuide.html");
			        
			        //open the user guide html
			        desktop.browse( uri );
			            
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
            }
        });
        
        //create error message id error found in tray icon initiation
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuItem item = (MenuItem)e.getSource();
                
                
                if ("Error".equals(item.getLabel())) {
                    
                    trayIcon.displayMessage("Ez Todo Manager",
                            "This is an error message", TrayIcon.MessageType.ERROR);

                } else if ("Warning".equals(item.getLabel())) {
                    
                    trayIcon.displayMessage("Ez Todo Manager",
                            "This is a warning message", TrayIcon.MessageType.WARNING);

                } else if ("Info".equals(item.getLabel())) {
                
                    trayIcon.displayMessage("Ez Todo Manager",
                            "This is an info message", TrayIcon.MessageType.INFO);

                } else if ("None".equals(item.getLabel())) {
                   
                    trayIcon.displayMessage("Ez Todo Manager",
                            "This is an ordinary message", TrayIcon.MessageType.NONE);
                }
            }
        };

        infoItem.addActionListener(listener);

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }

    //Obtain the tray icon image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = Ezt.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
