package CalendarGui;
import javax.swing.*;

import cs113.calendar.control.control;
import cs113.calendar.model.backend;

import java.awt.event.*;
import java.awt.*;

public class miniBox extends JPanel implements ActionListener
{
	JButton search;
	JButton create;
	JButton edit;
	JButton options;
	JButton logout;
	
	static public Login openingscreen;
	//Alignment
	int right = 15;
	
	/**
	 * Creates a panel that stores functional buttons.
	 */
	public miniBox()
	{
		this.setLayout(null);
		this.setBounds(590,5,200,130);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setOpaque(false);
		
		search = new JButton("Search Appointments");
		search.addActionListener(this);
		search.setBounds(right,5,175,20);
		this.add(search);
		
		edit = new JButton("Edit Appointments");
		edit.addActionListener(this);
		edit.setBounds(right,55,175,20);
		this.add(edit);
		
		
		create = new JButton("Create Appointment");
		create.addActionListener(this);
		create.setBounds(right,30,175,20);
		this.add(create);
		
		
		
		options = new JButton("Options");
		options.addActionListener(this);
		options.setBounds(right,80,175,20);
		this.add(options);
		
		
		logout = new JButton("Logout");
		logout.addActionListener(this);
		logout.setBounds(right,105,175,20);
		this.add(logout);
		
		
				
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == options)
		{
			CalendarFrame.panel.remove(CalendarFrame.current_panel);
			CalendarFrame.panel.add(CalendarFrame.options);
			CalendarFrame.current_panel = CalendarFrame.options;
			CalendarFrame.panel.repaint();
			CalendarFrame.panel.validate();
		}
		else if(e.getSource() == edit)
		{
			CalendarFrame.panel.remove(CalendarFrame.current_panel);
			CalendarFrame.panel.add(CalendarFrame.ep);
			CalendarFrame.current_panel = CalendarFrame.ep;
			CalendarFrame.panel.repaint();
			CalendarFrame.panel.validate();
		}
		else if(e.getSource()== create)
		{
			CalendarFrame.panel.remove(CalendarFrame.current_panel);
			CalendarFrame.panel.add(CalendarFrame.cp);
			CalendarFrame.current_panel = CalendarFrame.cp;
			CalendarFrame.panel.repaint();
			CalendarFrame.panel.validate();
		}
		else if(e.getSource()== search)
		{
			SearchAppointmentsRangeWindow sarw = new SearchAppointmentsRangeWindow();
			CalendarFrame.panel.repaint();
			CalendarFrame.panel.validate();
		}
		else if(e.getSource()==logout)
		{
			int res = JOptionPane.showConfirmDialog(null, "Logout and Save Changes?");
			System.out.println(res);
			
			if (res==0)
			{
				//selected yes
				backend.writeToMemory(control.currentUser.ID);
				openingscreen = new Login();
				Login.main_frame.dispose();
				
			}
			if (res==1)
			{
				//selected no
				openingscreen = new Login();
				Login.main_frame.dispose();
			}
		}
	}	
}