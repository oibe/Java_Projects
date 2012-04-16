package cs113.calendar.model;
/**
 * @author Akhil Gopinath
 */
import java.util.Calendar;
import java.io.*;

public class appointment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3293018020443617529L;
	public String description;
	public String location;
	public Calendar startDate = null;
	public Calendar endDate = null;
	
	/**
	 * creates an appointment object.
	 */
	public appointment()
	{	
		
	}
	
	
	/**
	 * sets the Calendar objects for appointments.
	 * @param one
	 * @param two
	 */
	public void setDate(Calendar one, Calendar two)
	{
		startDate = one;
		endDate = two;
	}
	
	/**
	 * sets the description for appointments.
	 * @param aDescription
	 */
	public void setDescription(String aDescription) {description=aDescription;}
	/**
	 * sets the location for appointments.
	 * @param alocation
	 */
	public void setLocation(String alocation) {location=alocation;}
	
	/**
	 * gets the start year in a calendar object, which is inside an appointent.
	 * @return
	 */
	public int getStartYear() {return startDate.get(Calendar.YEAR);}
	/**
	 * gets the start month in a calendar object, which is inside an appointent.
	 * @return
	 */
	public int getStartMonth() {return startDate.get(Calendar.MONTH);}
	/**
	 * gets the start day in a calendar object, which is inside an appointent.
	 * @return
	 */
	public int getStartDay() {return startDate.get(Calendar.DATE);}
	/**
	 * gets the start hour in a calendar object, which is inside an appointent.
	 * @return
	 */
	public int getStartHour() {return startDate.get(Calendar.HOUR);}
	/**
	 * gets the start minute in a calendar object, which is inside an appointent.
	 * @return
	 */
	public int getStartMinute() {return startDate.get(Calendar.MINUTE);}
	/**
	 * gets the end year in a calendar object, which is inside an appointent.
	 * @return
	 */
	public int getEndYear() {return endDate.get(Calendar.YEAR);}
	/**
	 * gets the end month in a calendar object, which is inside an appointent.
	 * @return
	 */
	public int getEndMonth() {return endDate.get(Calendar.MONTH);}
	/**
	 * gets the end day in a calendar object, which is inside an appointent.
	 * @return
	 */
	public int getEndDay() {return endDate.get(Calendar.DATE);}
	/**
	 * gets the end hour in a calendar object, which is inside an appointent.
	 * @return
	 */
	public int getEndHour() {return endDate.get(Calendar.HOUR);}
	/**
	 * gets the end minute in a calendar object, which is inside an appointent.
	 * @return
	 */
	public int getEndMinute() {return endDate.get(Calendar.MINUTE);}
	
	/**
	 * gets the Description for an appointment Object
	 * @return
	 */
	public String getDescription() {return description;}
	/**
	 * gets the location for an appointment object
	 * @return
	 */
	public String getlocation() {return location;}
	
	/**
	 * gets startDate calendar object from an appointment	
	 * 
	 * @return
	 */
	public Calendar getFullStart() {return startDate;}
	/**
	 * gets the full endDate from an appointment object.
	 * @return
	 */
	public Calendar getFullEnd() {return endDate;}
	
}
