package CalendarGui;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import cs113.calendar.control.control;
import cs113.calendar.model.appointment;

public class SearchPanel extends JPanel
{
	
	static public String data[][];
	static public String col[] = {"Start Time","End Time","Location","Description"};
	static public DefaultTableModel model = new DefaultTableModel(data,col);
	static public JTable table = new JTable(model);
	JLabel label = new JLabel("[Date]'s Appointments/[Week]'s Appointments");
	/**
	 * initializes a panel to display appointments in a day, also need to click 
	 * an appointment in this panel and press edit in order to edit appointments.
	 */
	public SearchPanel()
	{
		this.setLayout(null);
		Font font = label.getFont();
		label.setFont(new Font(font.getName(),font.getStyle(),12));
		this.setBounds(CalendarFrame.left+50,400,490,170);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0,0,490,19);
		this.add(label);
		
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(0,20,490,150);
		pane.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(pane);
	}
	public static void redrawTable(ArrayList<appointment> list)
	{
		data = new String[control.currentUser.appointmentList.size()][4];
		if(list == null)
		{
			return;
		}
		model.setRowCount(0);
		ArrayList<appointment> listToUse=list;
		for (int i=0; i<listToUse.size(); i++)
		{
			String starttime = control.calendarToString(listToUse.get(i).startDate);
			String endtime = control.calendarToString(listToUse.get(i).endDate);
			String location = listToUse.get(i).location;
			String description = listToUse.get(i).description;
			//System.out.println("size of appointment list in control "+control.currentUser.appointmentList.size());
			String firstCheck = starttime.substring(0,starttime.indexOf("/"));
			String secondCheck = endtime.substring(0,endtime.indexOf("/"));
			if(firstCheck.equals("0"))
			{
				starttime= starttime.substring(starttime.indexOf("/"));
				starttime = "12"+starttime;
				starttime= starttime.substring(0,starttime.indexOf(" ")-4)+CalendarMainPanel.year +starttime.substring(starttime.indexOf(" "));
			}
			if(secondCheck.equals("0"))
			{
				endtime = endtime.substring(endtime.indexOf("/"));
				endtime = "12"+endtime;
				endtime= endtime.substring(0,endtime.indexOf(" ")-4)+CalendarMainPanel.year +endtime.substring(endtime.indexOf(" "));
			}
			data[i][0]=starttime;
			data[i][1]=endtime;
			data[i][2]=location;
			data[i][3]=description;
			model.addRow(data[i]);
			
		}
	}
}