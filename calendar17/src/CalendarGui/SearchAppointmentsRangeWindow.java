package CalendarGui;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.*;
import cs113.calendar.control.*;
import cs113.calendar.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SearchAppointmentsRangeWindow extends JFrame
{
	static public String data[][];
	static public String col[] = {"Start Time","End Time","Location","Description"};
	static public DefaultTableModel model = new DefaultTableModel(data,col);
	JTable table = new JTable(model);
	
	/**
	 * initializes our window for  searching appointments
	 */
	public SearchAppointmentsRangeWindow()
	{
		
		this.setBounds(300,100,700,600);
		JPanel panel = new JPanel();
		this.add(panel);
		panel.setLayout(null);
		this.setVisible(true);
		
		data = new String[control.currentUser.appointmentList.size()][4];
		//redrawTable(control.currentUser.appointmentList);
		JScrollPane pane = new JScrollPane(table);
		pane.setBorder(BorderFactory.createLineBorder(Color.black));
		pane.setBounds(10,10,360,560);
		SearchOptions searchpanel = new SearchOptions();
		
		panel.add(pane);
		panel.add(searchpanel);
		this.repaint();
		this.validate();
		
	}
	public static void redrawTable(ArrayList<appointment> list)
	{
		if(list == null)
		{
			return;
		}
		/*for(int k= 0;k <list.size();k++)
		{
			int tempMonthStart = list.get(k).startDate.get(Calendar.MONTH);
			int tempMonthEnd = list.get(k).endDate.get(Calendar.MONTH);
			tempMonthStart++;
			tempMonthEnd++;
		}*/
		model.setRowCount(0);
		ArrayList<appointment> listToUse=list;
		for (int i=0; i<listToUse.size(); i++)
		{
			
			
			String starttime = control.calendarToString(listToUse.get(i).startDate);
			String endtime = control.calendarToString(listToUse.get(i).endDate);
			String location = listToUse.get(i).location;
			String description = listToUse.get(i).description;
			
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
		/*for(int k= 0;k <list.size();k++)
		{
			int tempMonthStart = list.get(k).startDate.get(Calendar.MONTH);
			int tempMonthEnd = list.get(k).endDate.get(Calendar.MONTH);
			tempMonthStart--;
			tempMonthEnd--;
		}*/
	}
	
}
class SearchOptions extends AppointmentGenericPanel 
{
	String[] combo_choices = {"Time","Location","Description","Description and Location","Every Parameter"};
	JComboBox box = new JComboBox(combo_choices);
	static public JButton search = new JButton("Search");
	public SearchOptions()
	{
		this.setBounds(388,10,200,560);
		JLabel label = new JLabel("Search must contain:");
		label.setBounds(10,380,190,30);
		search.setBounds(55,470,120,30);
		search.addActionListener(this);
		this.add(label);
		this.add(search);
		box.setBounds(10,410,170,20);
		this.add(box);
		
	}
	public void actionPerformed(ActionEvent e)
	{
		ArrayList<appointment> firstStageConflictList = new ArrayList<appointment>();
		ArrayList<appointment> secondStageConflictList = new ArrayList<appointment>();
		ArrayList<appointment> thirdStageConflictList = new ArrayList<appointment>();
		
			int starty = Integer.parseInt(startYear_spinner.getValue().toString());
			int startmo = Integer.parseInt(startMonth_spinner.getValue().toString());
			int startd = Integer.parseInt(startDay_spinner.getValue().toString());
			int starth = Integer.parseInt(startHour_spinner.getValue().toString());
			int startm = Integer.parseInt(startMinute_spinner.getValue().toString());
			int endy = Integer.parseInt(endYear_spinner.getValue().toString());
			int endmo = Integer.parseInt(endMonth_spinner.getValue().toString());
			int endd = Integer.parseInt(endDay_spinner.getValue().toString());
			int endh = Integer.parseInt(endHour_spinner.getValue().toString());
			int endm = Integer.parseInt(endMinute_spinner.getValue().toString());
			String location = location_area.getText();
			String description = description_area.getText();
			
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			boolean existsCheck = true;
			
			start.set(starty,startmo,startd,starth,startm);
			end.set(endy,endmo,endd,endh,endm);
			appointment temp = new appointment();
			temp.setDate(start,end);
			String[] location_array = location.split(" ");
			String[] description_array = description.split(" ");
			if(e.getSource() == search && box.getSelectedItem().equals("Every Parameter"))
			{
				firstStageConflictList = searchByTime(control.currentUser.appointmentList, temp);
				secondStageConflictList = searchByLocation(firstStageConflictList, location_array);
				thirdStageConflictList = searchByDescription(secondStageConflictList, description_array);
				SearchAppointmentsRangeWindow.redrawTable(thirdStageConflictList);
			
			}
			else if(e.getSource()==search && box.getSelectedItem().equals("Description and Location"))
			{
				secondStageConflictList = searchByLocation(control.currentUser.appointmentList, location_array);
				thirdStageConflictList = searchByDescription(secondStageConflictList, description_array);
				SearchAppointmentsRangeWindow.redrawTable(thirdStageConflictList);
			}
			else if(e.getSource()==search && box.getSelectedItem().equals("Location"))
			{
				secondStageConflictList = searchByLocation(control.currentUser.appointmentList, location_array);
				SearchAppointmentsRangeWindow.redrawTable(secondStageConflictList);
			}
			else if(e.getSource()==search && box.getSelectedItem().equals("Description"))
			{
				thirdStageConflictList = searchByDescription(control.currentUser.appointmentList, description_array);
				SearchAppointmentsRangeWindow.redrawTable(thirdStageConflictList);
			}
			else if(e.getSource()==search && box.getSelectedItem().equals("Time"))
			{
				firstStageConflictList = searchByTime(control.currentUser.appointmentList, temp);
				SearchAppointmentsRangeWindow.redrawTable(firstStageConflictList);
			}
	}
	public static ArrayList<appointment> searchByTime(ArrayList<appointment> list, appointment temp)
	{
		ArrayList<appointment> conflictList = new ArrayList<appointment>();
		for(int i = 0; i< control.currentUser.appointmentList.size();i++)
		{
			if(control.conflictingAppointments(temp,control.currentUser.appointmentList.get(i))==true)
			{
				System.out.println("temp location " +temp.location);
				conflictList.add(control.currentUser.appointmentList.get(i));
			}
		}
		return conflictList;
	}
	public static ArrayList<appointment> searchByLocation(ArrayList<appointment> list,String[] location_array)
	{
		boolean existsCheck = true;
		ArrayList<appointment> conflictList = new ArrayList<appointment>();
		for(int p = 0; p < list.size();p++)
		{
			existsCheck = true;
			for(int k = 0; k < location_array.length;k++)
			{
				
				if(list.get(p).location.contains(location_array[k])==false)
				{
					existsCheck = false;
					break;
				}
			}
			if(existsCheck==true)
			{
				//System.out.println(existsCheck);
				
				conflictList.add(list.get(p));
			}
			
			
		}
		return conflictList;
	}
	public static ArrayList<appointment> searchByDescription(ArrayList<appointment> list,String[] description_array)
	{
		boolean existsCheck = true;
		ArrayList<appointment> conflictList = new ArrayList<appointment>();
		for(int p = 0;p < list.size();p++)
		{
			existsCheck = true;
			for(int k = 0; k < description_array.length;k++)
			{
				if(list.get(p).description.contains(description_array[k])==false)
				{
					existsCheck = false;
					break;
				}
			}
			if(existsCheck==true)
			{
				conflictList.add(list.get(p));
			}
			
		}
		return conflictList;
	}
}
