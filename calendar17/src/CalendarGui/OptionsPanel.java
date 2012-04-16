package CalendarGui;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import CalendarGui.CalendarFrame.ImagePanel;
import cs113.calendar.model.*;
import cs113.calendar.control.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.EventListener;

public class OptionsPanel extends GenericPanel 
{

	JButton saveall = new JButton("Save All Changes");
	
	JLabel first = new JLabel("First Name");
	JLabel middle = new JLabel("Middle Name");
	JLabel last = new JLabel("Last Name");
	JLabel current = new JLabel("Current Password");
	JLabel new_password = new JLabel("New Password");
	JLabel new_password_retype = new JLabel("New Password Re-entered");
	JTextField first_text = new JTextField(6);
	JTextField middle_text = new JTextField(6);
	JTextField last_text =new JTextField(6);
	JPasswordField current_text=new JPasswordField(6);
	JPasswordField new_password_text=new JPasswordField(6);
	JPasswordField new_password_retype_text=new JPasswordField(6);
	
	String[] array = (new File("Themes")).list(); 
	JComboBox lang_box = new JComboBox(array);

	public OptionsPanel()
	{
		super();
		//this.setBackground(new Color(0,0,0));
		this.setBorder(BorderFactory.createLineBorder(Color.white));
		this.setOpaque(false);
		this.add(first);
		first.setBounds(10,10,100,10);
		this.add(middle);
		first.setForeground(new Color(255,255,255));
		middle.setBounds(10,40,100,10);
		this.add(last);
		middle.setForeground(new Color(255,255,255));
		last.setBounds(10,70,100,10);
		last.setForeground(new Color(255,255,255));
		this.add(first_text);
		first_text.setBounds(100, 10, 80, 18);
		this.add(middle_text);
		middle_text.setBounds(100,40,80,18);
		this.add(last_text);
		last_text.setBounds(100,70,80,18);
		this.add(current);
		current.setBounds(10,120,100,10);
		current.setForeground(new Color(255,255,255));
		this.add(new_password);
		new_password.setBounds(10,150,100,10);
		new_password.setForeground(new Color(255,255,255));
		this.add(new_password_retype);
		new_password_retype.setBounds(10,180,100,10);
		new_password_retype.setForeground(new Color(255,255,255));
		this.add(current_text);
		current_text.setBounds(110, 120, 80, 18);
		this.add(new_password_text);
		new_password_text.setBounds(110, 150, 80, 18);
		this.add(new_password_retype_text);
		new_password_retype_text.setBounds(110,180,80,18);
		JLabel label = new JLabel("Theme");
		label.setBounds(10,230,70,20);
		this.add(label);
		label.setForeground(new Color(255,255,255));
		
		
		lang_box.setBounds(110,230,60,20);
		lang_box.addActionListener(this);
		
		this.add(lang_box);
		
	
		saveall.setBounds(10,360,180,30);
		saveall.addActionListener(this);
		//this.add(saveall);
		
		//first_text.setText(control.currentUser.firstName);
		//middle_text.setText(control.currentUser.middleName);
		//last_text.setText(control.currentUser.lastName);
		//current_text.setText(control.currentUser.password);
	}
	public void actionPerformed(ActionEvent i)
	{
	     
		if(i.getSource() == lang_box)
		{
			JComboBox cb = (JComboBox)i.getSource();
	        String newTheme = (String)cb.getSelectedItem();
	        System.out.println (newTheme);
	        CalendarFrame.icon=  new ImageIcon("Themes/"+newTheme);
	        CalendarFrame.panel.img= CalendarFrame.icon.getImage();
	        CalendarFrame.panel.repaint();

		}
		
		if(i.getSource() == saveall)
		{
			System.out.println("first "+control.currentUser.firstName);
			System.out.println("middle "+control.currentUser.middleName);
			System.out.println("last "+ control.currentUser.lastName);
			System.out.println("password " +control.currentUser.password);
			String firstname = first_text.getText();
			String middlename = middle_text.getText();
			String lastname = last_text.getText();
			String currentpassword = new String(current_text.getPassword());
			String newpassword = new String(new_password_text.getPassword());
			String newpassword_retype = new String(new_password_text.getPassword());
		
			
			/*if(control.currentUser.password==null && currentpassword == null)
			{
				control.currentUser.password= "a";
				currentpassword = "a";
			}*/
			
			//System.out.println("current user "+ control.currentUser.firstName);
			//System.out.println("current password " +currentpassword);
			//System.out.println("current user password "+ control.currentUser.password);
			//System.out.println("new password "+ newpassword);
			
				if(control.currentUser.password.equals(currentpassword)&& newpassword.equals(newpassword_retype))
				{
					control.currentUser.firstName = firstname;
					control.currentUser.middleName = middlename;
					control.currentUser.lastName = lastname;
					control.currentUser.password= newpassword;	
					
					
					//show confirmation dialogue
				}
				else
				{
					current_text.setText("");
					new_password_text.setText("");
					new_password_retype_text.setText("");
					
					//show popup dialogue
				}
		}	
		
		
	}
}