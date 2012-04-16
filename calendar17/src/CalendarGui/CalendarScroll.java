package CalendarGui;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;

import cs113.calendar.control.control;
import cs113.calendar.model.appointment;

/**
 * creates the Calendar slider that so that user is able to scroll through months.
 * @author Akhil Gopinath
 *
 */
class CalendarScroll extends JPanel implements ActionListener
 {
	
	CalendarMainPanel cmp;
	JButton previous = new JButton("previous");
	static public JButton next = new JButton("next");
	int i = 0;
	/**
	 * initializes a CalendarScroll object
	 */
	public CalendarScroll()
	{
		this.setBackground(new Color(255,255,255,100));
		this.setLayout(null);
		this.setBounds(300,5,190,25);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		previous.setBounds(2,2,90,20);
		previous.addActionListener(this);
		next.setBounds(110,2,70,20);
		next.addActionListener(this);
		this.add(previous);
		this.add(next);
		this.setVisible(true);
	}
	/**
	 * implements the buttons using the ActionListener Interface, so the next button will go to the next month, and the previous
	 * button will go to the previous month.
	 */
	public void actionPerformed(ActionEvent e)
	{
		this.repaint();
		if(e.getSource()==next)
		{
			
			if(i == 0)
			{
			i++;
			Calendar temp = Calendar.getInstance();
			int mo = temp.get(Calendar.MONTH)+i;
			temp.set(Calendar.MONTH,mo);
			
			cmp = new CalendarMainPanel(temp);
			CalendarFrame.panel.remove(CalendarFrame.main_panel);
			CalendarFrame.main_panel=cmp;
			CalendarFrame.panel.add(CalendarFrame.main_panel);
			
			CurrentMonthPanel p = new CurrentMonthPanel(temp.get(Calendar.MONTH),temp.get(Calendar.YEAR));
			CalendarFrame.panel.remove(CalendarFrame.currmop);
			CalendarFrame.currmop = p;
			CalendarFrame.panel.add(CalendarFrame.currmop);
			CalendarFrame.currmop.validate();
			
			CalendarFrame.panel.repaint();
			CalendarFrame.panel.validate();
			
			}
			else
			{
				i++;
				Calendar temp = Calendar.getInstance();
				int mo = temp.get(Calendar.MONTH)+i;
				temp.set(Calendar.MONTH,mo);
				
				cmp = new CalendarMainPanel(temp);
				CalendarFrame.panel.remove(CalendarFrame.main_panel);
				CalendarFrame.main_panel=cmp;
				CalendarFrame.panel.add(CalendarFrame.main_panel);
				CurrentMonthPanel p = new CurrentMonthPanel(temp.get(Calendar.MONTH),temp.get(Calendar.YEAR));
				CalendarFrame.panel.remove(CalendarFrame.currmop);
				CalendarFrame.currmop = p;
				CalendarFrame.panel.add(CalendarFrame.currmop);
				CalendarFrame.currmop.validate();
				CalendarFrame.panel.repaint();
				CalendarFrame.panel.validate();
				
			}
			//CalendarFrame.reBuild();
		}
		else if(e.getSource()==previous) 
		{
			
				
			i--;
			Calendar temp = Calendar.getInstance();
			int mo = temp.get(Calendar.MONTH)+i;
			temp.set(Calendar.MONTH,mo);
			
			cmp = new CalendarMainPanel(temp);
			CalendarFrame.panel.remove(CalendarFrame.main_panel);
			CalendarFrame.main_panel=cmp;
			CalendarFrame.panel.add(CalendarFrame.main_panel);
			CurrentMonthPanel p = new CurrentMonthPanel(temp.get(Calendar.MONTH),temp.get(Calendar.YEAR));
			CalendarFrame.panel.remove(CalendarFrame.currmop);
			CalendarFrame.currmop = p;
			CalendarFrame.panel.add(CalendarFrame.currmop);
			CalendarFrame.currmop.validate();
			CalendarFrame.panel.repaint();
			CalendarFrame.panel.validate();
			
			
			
		}
	}
	 
}
 