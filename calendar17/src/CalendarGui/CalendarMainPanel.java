package CalendarGui;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;

import cs113.calendar.control.control;
import cs113.calendar.model.appointment;

public class CalendarMainPanel extends JPanel implements MouseListener, ActionListener
{
	 static public JPanel[] useable_array = new JPanel[42];
	 int update =0;
	 Color panel_color = new Color(255,255,255,50);
	 Color previous_color= null;
	 JPanel ref=null;
	 static int day;
	 static int month;
	 static int year;
	 static public Calendar temp_calendar;
	 
	 /**
	  * This method implements the MouseClicked Interface, and sets our calendar up so that when someone clicks on a day in the month displayed,
	  * the appointments for that day come up.
	  */
	public void mouseClicked(MouseEvent e)
	{
		int calc;
		ArrayList<appointment> conflictList = new ArrayList<appointment>();
		if(update==0)
		{
		ref = (JPanel)e.getSource();
		previous_color = ref.getBackground();
		ref.setBackground(new Color(255,0,0,50));
		update++;
		System.out.println(year);
		numberPanel reference = (numberPanel)e.getSource();
		int panelspot =reference.number;
		//calc =(panelspot-day)+1;
		//System.out.println((panelspot-day)+1);
		Calendar beginning = Calendar.getInstance();
		Calendar ending = Calendar.getInstance();
		beginning.set(year,month+1,(panelspot-day)+1,0,0);
		ending.set(year,month+1,(panelspot-day)+1,23,59);
		appointment temp = new appointment();
		temp.setDate(beginning,ending);
		
		
		conflictList = SearchOptions.searchByTime(control.currentUser.appointmentList, temp);
		System.out.println(conflictList);
		SearchPanel.redrawTable(conflictList);
		this.repaint();
		}
		else
		{

			ref.setBackground(previous_color);
			JPanel new_ref =(JPanel) e.getSource();
			previous_color = new_ref.getBackground();
			ref = new_ref;
			ref.setBackground(new Color(255,0,0,50));
			this.repaint();
			
			numberPanel reference = (numberPanel)e.getSource();
			int panelspot =reference.number;
			//System.out.println((panelspot-day)+1);
			Calendar beginning = Calendar.getInstance();
			Calendar ending = Calendar.getInstance();
			beginning.set(year,month+1,((panelspot-day)+1),0,0);
			ending.set(year,month+1,((panelspot-day)+1),23,59);
			appointment temp = new appointment();
			temp.setDate(beginning,ending);
			conflictList = SearchOptions.searchByTime(control.currentUser.appointmentList, temp);
			System.out.println(temp);
			SearchPanel.redrawTable(conflictList);
		
		}
	}
	/**
	 * This constructor implements the acutal Calendar displayed in the main panel.
	 * @param a
	 */
	public CalendarMainPanel(Calendar a)
	{
		this.setLayout(null);
		this.setOpaque(false);
		int xdim =490;
		int ydim = 300;
		this.setBounds(CalendarFrame.left+50, 95, 490,300);
		
		int cellwidth = xdim/7;
		int cellheight = ydim/6;
		int starty=0;
		numberPanel[][] panel = new numberPanel[6][7];
		int h = 0;
		for(int i = 0; i < 6;i++)
		{
			
			int startx = 0;
			for(int k = 0; k< 7;k++)
			{
				panel[i][k] = new numberPanel();
				
				panel[i][k].setSize(cellwidth,cellheight);
				panel[i][k].setLocation(startx,starty);
				panel[i][k].setBorder(BorderFactory.createLineBorder(Color.black));
				panel[i][k].number = h;
				//panel[i][k].addMouseListener(this);
				this.add(panel[i][k]);
				useable_array[h]=panel[i][k];
				panel[i][k].setBackground(panel_color);
				//panel[i][k].setOpaque(false);
				startx = startx + cellwidth;
				h++;
			}
			starty = starty  + cellheight;
		}
		
		initializeCalendar(a);
		this.repaint();
	}
	
	/**
	 * This method makes it so the main calender has days with labels, also blacks out days and adds Mouse Listeners on the Calendar grid created.
	 * @param instance
	 */
	public void initializeCalendar(Calendar instance)
	{
		temp_calendar = instance;
		temp_calendar.set(Calendar.DATE,1);
		
		day = temp_calendar.get(temp_calendar.DAY_OF_WEEK)-1;
		year = temp_calendar.get(temp_calendar.YEAR);
		month = temp_calendar.get(temp_calendar.MONTH);
		int daysinmonth = findDaysInMonth(month);
		temp_calendar = Calendar.getInstance();
		int date = temp_calendar.get(temp_calendar.DATE);
		
		int previousmonthdays=findDaysInMonth(month-1);
		int label_number = previousmonthdays-day+1;
		int trial = 0;
		for(int v = 0; v < day;v++)
		{
			if(month ==0 && trial==0)
			{
				
				previousmonthdays= 31;
				label_number = previousmonthdays-day+1;
				trial++;
				
			}
			JLabel label = new JLabel(""+label_number);
			label.setForeground(new Color(255,255,255));
			useable_array[v].add(label);
			useable_array[v].setBackground(new Color(0,0,0));
			useable_array[v].setBorder(BorderFactory.createLineBorder(Color.white));
			useable_array[v].setEnabled(false);
			label_number++;
		}
		int k = 1;
		for(int z= day;z <daysinmonth+day;z++)
		{
			if(k == date)
			{
				useable_array[z].setBackground(new Color(255,255,0,120));
			}
			JLabel label = new JLabel(""+k);
			useable_array[z].addMouseListener(this);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setForeground(new Color(0,0,0));
			useable_array[z].add(label);
			k++;
		}
		int n = 1;
		for(int b = daysinmonth+day; b < 42;b++)
		{
			JLabel label = new JLabel(""+n);
			n++;
			label.setForeground(new Color(255,255,255));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			useable_array[b].add(label);
			useable_array[b].setBackground(new Color(0,0,0));
			useable_array[b].setBorder(BorderFactory.createLineBorder(Color.white));
		
		}
		
		this.repaint();
	}
	/**
	 * given a month integer it finds how many days are in that month
	 * @param month
	 * @return
	 */
	public static int findDaysInMonth(int month)
	{
		int daysinmonth= 0;
		switch(month){
		case 0: daysinmonth = 31; break;
		case 1: daysinmonth = 28; break;
		case 2: daysinmonth = 31; break;
		case 3: daysinmonth = 30; break;
		case 4: daysinmonth = 31; break;
		case 5: daysinmonth = 30; break;
		case 6: daysinmonth = 31; break;
		case 7: daysinmonth = 31; break;
		case 8: daysinmonth = 30; break;
		case 9: daysinmonth = 31; break;
		case 10: daysinmonth = 30;  break;
		case 11: daysinmonth = 31; break;	
		}
		return daysinmonth;
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
				
	}
	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
/**
 * Gives the panels displayed in the calendar grid numbers so that they can be used in implementation.
 * @author oibe
 *
 */
class numberPanel extends JPanel
{
	public int number;
	/**
	 * creates a panel with an integer in it.
	 */
	numberPanel()
	{
		
	}
}


