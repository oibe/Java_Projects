package cs113.calendar.model;

import java.util.ArrayList;
import cs113.calendar.util.quicksort;
import java.io.*;

public class user implements Serializable{
	private static final long serialVersionUID = 3293018020443617529L;

	public String ID="a";
	public String firstName;
	public String middleName;
	public String lastName;
	public String password = "";
	public String currentTheme = "Icy-Moon";
	public ArrayList<appointment> appointmentList = new ArrayList<appointment>();
	
	public user(String anID) {ID=anID;}
	/**
	 * setsuser parameters.
	 * @param first
	 * @param middle
	 * @param last
	 */
	public void setName(String first, String middle, String last)
	{
		firstName= first;
		middleName= middle;
		lastName= last;
	}
	
	public void setPassword(String pass) {password=pass;}
	public void setTheme(String theme) {currentTheme=theme;}
	
	public String getFirstName() {return firstName;}
	public String getMiddleName() {return middleName;}
	public String getLastName() {return lastName;}
	public String getID() {return ID;}
	public String getPassword() {return password;}
	public ArrayList<appointment> getAppointments() {return appointmentList;}
	public String getTheme() {return currentTheme;}
	
	
	public void addAppointment(appointment anAppointment)
	{
		ArrayList<appointment> tempAppointmentList = new ArrayList<appointment>(); //temporarily holds appointments
		
		if (this.appointmentList.size()==0)
		{
			this.appointmentList.add(anAppointment);
			return;
		}
	
		for (int x=0; x<this.appointmentList.size(); x++)  //Cycles through appointments until it finds an appointment that the appointment to be added comes before.
		{
			if ( (this.appointmentList.get(x).getFullStart()).before(anAppointment.getFullStart()) ) //compares start dates
			{
				tempAppointmentList.add(anAppointment);
				for (int y=x; y<this.appointmentList.size(); y++)
				{
					tempAppointmentList.add(appointmentList.get(y));
				}
				this.appointmentList=tempAppointmentList;
				return;
			}
			else if ( (this.appointmentList.get(x).getFullStart()).equals(anAppointment.getFullStart()) ) //if start dates are equal
				 {
					if ( (this.appointmentList.get(x).getFullEnd()).before(anAppointment.getFullEnd()) ) //compares end dates
					{
						tempAppointmentList.add(anAppointment);
						for (int y=x; y<this.appointmentList.size(); y++)
						{
							tempAppointmentList.add(appointmentList.get(y));
						}
						this.appointmentList=tempAppointmentList;
						return;
					}
					else if ( (this.appointmentList.get(x).getFullEnd()).equals(anAppointment.getFullEnd()) ) //if end dates are equal
						 {
							if ( (this.appointmentList.get(x).getlocation()).compareTo(anAppointment.getlocation())>0 ) //compares locations
							{
								tempAppointmentList.add(anAppointment);
								for (int y=x; y<this.appointmentList.size(); y++)
								{
									tempAppointmentList.add(appointmentList.get(y));
								}
								this.appointmentList=tempAppointmentList;
								return;
							}
							else if ( (this.appointmentList.get(x).getlocation()).compareTo(anAppointment.getlocation())==0 ) //if locations are equal
								 {
									if ( (this.appointmentList.get(x).getDescription()).compareTo(anAppointment.getDescription())>0 ) // compares descriptions
									{
										tempAppointmentList.add(anAppointment);
										for (int y=x; y<this.appointmentList.size(); y++)
										{
											tempAppointmentList.add(appointmentList.get(y));
										}
										this.appointmentList=tempAppointmentList;
										return;
									}
									else if ( (this.appointmentList.get(x).getDescription()).compareTo(anAppointment.getDescription())==0 ) // if descriptions are equal does nothing and ends.
										 {
											return;
										 }
								 }
						 }
				 }
			tempAppointmentList.add(appointmentList.get(x));
		}
		tempAppointmentList.add(anAppointment);
		this.appointmentList=tempAppointmentList;
		
	}
	
}