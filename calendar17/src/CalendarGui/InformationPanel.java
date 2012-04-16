package CalendarGui;
import javax.swing.*;

import cs113.calendar.control.control;

import java.awt.*;
import java.util.Calendar;

public class InformationPanel extends JPanel
{
	JLabel nameSecond = new JLabel(control.currentUser.firstName);
	JLabel dateSecond;
	
	/**
	 * Initializes a panel that displays the current  date and time.
	 */
	public InformationPanel()
	{
		nameSecond.setBounds(120,5,100,20);
		Calendar in = Calendar.getInstance();
		dateSecond = new JLabel(in.get(Calendar.MONTH)+"/"+in.get(Calendar.DAY_OF_MONTH)+"/"+in.get(Calendar.YEAR));
		this.setLayout(null);
		dateSecond.setBounds(120,25,1000,20);
		this.setBorder(BorderFactory.createLineBorder(Color.black));

		this.setLayout(null);
		this.setBounds(CalendarFrame.left+50,5,240,50);
		JLabel name = new JLabel("Name:");
		JLabel date = new JLabel("Current Date:");
		this.add(dateSecond);
		this.add(nameSecond);
		name.setBounds(10,5,100,20);
		date.setBounds(10,25,100,20);
		this.add(name);
		this.add(date);
		
		
	}
}