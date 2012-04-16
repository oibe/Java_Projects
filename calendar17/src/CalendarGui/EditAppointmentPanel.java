package CalendarGui;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;

import cs113.calendar.control.control;
import cs113.calendar.model.appointment;


public class EditAppointmentPanel extends AppointmentGenericPanel implements ActionListener
{	
	JButton save = new JButton("Save");
	JButton delete = new JButton("Delete");
	String namea;
	public EditAppointmentPanel()
	{
		namea = "name of edit panel";
		save.setBounds(10,390,80,30);
		delete.setBounds(100,390,80,30);
		save.addActionListener(this);
		delete.addActionListener(this);
		this.add(save);
		this.add(delete);
	}
	public void actionPerformed(ActionEvent e)
	{
		String starty = startYear_spinner.getValue().toString();
		String startmo =startMonth_spinner.getValue().toString();
		String startd = startDay_spinner.getValue().toString();
		String starth = startHour_spinner.getValue().toString();
		String startm = startMinute_spinner.getValue().toString();
		String endy = endYear_spinner.getValue().toString();
		String endmo = endMonth_spinner.getValue().toString();
		String endd = endDay_spinner.getValue().toString();
		String endh = endHour_spinner.getValue().toString();
		String endm = endMinute_spinner.getValue().toString();
		
		int row = SearchPanel.table.getSelectedRow();
		if(row == -1)
		{
			return;
		}
		
		String starttime = SearchPanel.data[row][0];
		String endtime = SearchPanel.data[row][1];
		String location = SearchPanel.data[row][2];
		String description = SearchPanel.data[row][3];
			
		String[] dateOneFull = starttime.split(" ");
		String[] dateTwoFull = endtime.split(" ");
		
		String[] dateOneHalf = dateOneFull[0].split("/");
		String[] dateTwoHalf = dateTwoFull[0].split("/");
		
		String[] dateOneTime = dateOneFull[1].split(":");
		String[] dateTwoTime = dateTwoFull[1].split(":");
		
		String oldstart;
		String oldend;
		String oldstarttime;
		String oldendtime;
		
		if(dateOneHalf[0].length()==1) {oldstart = "0"+dateOneHalf[0]+ "/";}
		else{oldstart = dateOneHalf[0] + "/";}
		if(dateOneHalf[1].length()==1) {oldstart = oldstart + "0"+dateOneHalf[1]+ "/";}
		else{oldstart =oldstart + dateOneHalf[1] + "/";}
		oldstart =oldstart +  dateOneHalf[2];
		
		if(dateOneTime[0].length()==1) {oldstarttime ="0"+dateOneTime[0]+ ":";}
		else{oldstarttime =dateOneTime[0] + ":";}
		if(dateOneTime[1].length()==1) {oldstarttime =oldstarttime + "0"+dateOneTime[1];}
		else{oldstarttime =oldstarttime + dateOneTime[1];}
		
		
		
		if(dateTwoHalf[0].length()==1) {oldend = "0"+dateTwoHalf[0]+ "/";}
		else{oldend = dateTwoHalf[0] + "/";}
		if(dateTwoHalf[1].length()==1) {oldend = oldend + "0"+dateTwoHalf[1]+ "/";}
		else{oldend =oldend + dateTwoHalf[1] + "/";}
		oldend =oldend +  dateTwoHalf[2];
		
		if(dateTwoTime[0].length()==1) {oldendtime ="0"+dateTwoTime[0]+ ":";}
		else{oldendtime =dateTwoTime[0] + ":";}
		if(dateTwoTime[1].length()==1) {oldendtime =oldendtime + "0"+dateTwoTime[1];}
		else{oldendtime =oldendtime + dateTwoTime[1];}
		
		/*System.out.println(oldstart);
		System.out.println(oldend);
		System.out.println(oldstarttime);
		System.out.println(oldendtime);*/
		
		
		
		
		
		int check = control.createAppointment(starty, startmo, startd, starth, startm, endy, endmo, endd, endh, endm, location, description);
		
		if(check ==0)
		{
			//appointment created
			control.removeAppointment(oldstart, oldend, oldstarttime, oldendtime, description, location);
		}
		else if(check == 2)
		{
			//conflicting time
			control.removeAppointment(oldstart, oldend, oldstarttime, oldendtime, description, location);
		}
		else if(check == 3)
		{
			//identical
		}
		else
		{
			//invalid time number "1"
		}
		
		/*control.createAppointment(dateOneHalf[2], dateOneHalf[0], dateOneHalf[1], 
				dateOneTime[0], dateOneTime[1], dateTwoHalf[2], dateTwoHalf[0], dateTwoHalf[1], 
				dateTwoTime[0], dateTwoTime[1], location, description);
		
		
		

		System.out.println(dateOneHalf[0]);
		System.out.println(dateTwoHalf[0]);
		System.out.println(dateOneHalf[1]);
		System.out.println(dateTwoHalf[1]);
		System.out.println(dateOneHalf[2]);
		System.out.println(dateTwoHalf[2]);
		
		
		System.out.println(dateOneTime[0]);
		System.out.println(dateTwoTime[0]);
		System.out.println(dateOneTime[1]);
		System.out.println(dateTwoTime[1]);
		Calendar firstCalendar = Calendar.getInstance();
		Calendar secondCalendar = Calendar.getInstance();
		firstCalendar.set(Calendar.SECOND,0);
		secondCalendar.set(Calendar.SECOND,0);
		firstCalendar.set(Calendar.MILLISECOND, 0);
		secondCalendar.set(Calendar.MILLISECOND, 0);
		
		firstCalendar.set(Integer.parseInt(dateOneHalf[2]),Integer.parseInt(dateOneHalf[0]),
				Integer.parseInt(dateOneHalf[1]),Integer.parseInt(dateOneTime[0]),Integer.parseInt(dateOneTime[1]));
	
		secondCalendar.set(Integer.parseInt(dateTwoHalf[2]),Integer.parseInt(dateTwoHalf[0]),
				Integer.parseInt(dateTwoHalf[1]),Integer.parseInt(dateTwoTime[0]),Integer.parseInt(dateTwoTime[1]));*/
		

		
		
		
		
	}
}