package CalendarGui;

import java.awt.Color;
import java.util.Calendar;

import javax.swing.*;

public class CurrentMonthPanel extends JPanel
{
	static public String label;
	
	/**
	 * initializes a panel that displays the current month and year selected.
	 * @param month
	 * @param year
	 */
	public CurrentMonthPanel(int month,int year)
	{
		this.label= getMonthName(month)+" "+year;
		JLabel monthLabel = new JLabel(label);
		this.setBounds(300,35,190,25);
		this.setVisible(true);
		this.setBackground(new Color(255,255,255,50));
		monthLabel.setBounds(0,0,20,180);
		this.add(monthLabel);
	}
	/**
	 * Given a month number gets the name of a month.
	 * @param month
	 * @return
	 */
	public static String getMonthName(int month)
	{
		String mo = null;
		switch(month)
		{
			case 0: mo = "January"; break;
			case 1: mo =  "February"; break;
			case 2: mo =  "March"; break;
			case 3: mo =  "April"; break;
			case 4: mo =  "May"; break;
			case 5: mo =  "June"; break;
			case 6: mo =  "July"; break;
			case 7: mo =  "August"; break;
			case 8: mo =  "September"; break;
			case 9: mo =  "October"; break;
			case 10: mo =  "November"; break;
			case 11: mo =  "December"; break;
		}
		return mo;
	}
	
}