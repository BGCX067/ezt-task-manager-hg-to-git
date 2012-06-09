package ezt.Help;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;

//program help frame
public class Help extends JFrame {

	private static final long serialVersionUID = 1L;	

	public Help() throws Exception {
		
		setTitle("Help");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(450, 200, 499, 467);
		JPanel contentPaneHelp = new JPanel();
		contentPaneHelp.setBackground(SystemColor.menu);
		contentPaneHelp.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneHelp);
		getContentPane().setLayout(null);
		contentPaneHelp.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 483, 431);
		contentPaneHelp.add(scrollPane);
		
		JEditorPane tp = new JEditorPane();
		tp.setEditable(false);
		tp.setEditorKit(null);
		tp.setPage(Help.class.getResource("User Guide.htm"));//display the help html
		scrollPane.setViewportView(tp);	     
		
    } 
}