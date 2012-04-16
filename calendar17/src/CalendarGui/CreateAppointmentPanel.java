package CalendarGui;
import java.awt.event.*;
import javax.swing.*;

import cs113.calendar.control.*;


public class CreateAppointmentPanel extends AppointmentGenericPanel implements ActionListener
{
	JButton create = new JButton("Create");
	JPanel panel = null;
	/**
	 * Creates a panel that offers options for editing appointments, extends the generic appointment class 
	 */
	public CreateAppointmentPanel()
	{
		panel = this;
		create.setBounds(10,390,170,30);
		create.addActionListener(this);
		this.add(create);
	}
	/**
	 * Makes the buttons for the CreateAppointmentPanel work.
	 */
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource()==create)
		{
			String startyear = this.startYear_spinner.getValue().toString();
			String startmonth = this.startMonth_spinner.getValue().toString();
			int a = Integer.parseInt(startmonth);
			startmonth = ""+a;
			String startday = this.startDay_spinner.getValue().toString();
			String starthour = this.startHour_spinner.getValue().toString();
			String startminute = this.startMinute_spinner.getValue().toString();
			String endyear = this.endYear_spinner.getValue().toString();
			String endmonth = this.endMonth_spinner.getValue().toString();
			int b =  Integer.parseInt(endmonth);
			endmonth = ""+b;
			String endday = this.endDay_spinner.getValue().toString();
			String endhour = this.endHour_spinner.getValue().toString();
			String endminute = this.endMinute_spinner.getValue().toString();
			
			String location = this.location_area.getText();
			String description = this.description_area.getText();
			
			int check =control.createAppointment(startyear, startmonth, startday, starthour, startminute, endyear,endmonth,endday,endhour,endminute,location,description);
			
			if(check==0)//called it in here and it result was conflicting,therefore made an appointment
			{
				//create appointment
				JOptionPane.showMessageDialog(create, "Appointment has been created", "Confirmation", JOptionPane.PLAIN_MESSAGE);
			}
			else if(check==1)//returned identical 3
			{
				//time is invalid
				JOptionPane.showMessageDialog(create, "Invalid time", "Error", JOptionPane.PLAIN_MESSAGE);
			}
			else if(check==2)//returned identical 3
			{
				//conflicting appointment
				JOptionPane.showMessageDialog(create, "Conflicting appointment time, but your appointment has been made.", "Warning", JOptionPane.PLAIN_MESSAGE);
			}
			else if(check==3)//kept giving me identical error lol
			{
				//say identical appointment 
				JOptionPane.showMessageDialog(create, "Cannot make identical appointments.", "Error", JOptionPane.PLAIN_MESSAGE);
			}
			else if(check==4)
			{
				//start time and end time cannot be the same. 
			}
			else if(check==5)
			{
				JOptionPane.showMessageDialog(create, "Cannot have the same start and endtime for appointments", "Error", JOptionPane.PLAIN_MESSAGE);
			}
		
		}
		
	}
}