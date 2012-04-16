package cs113.calendar.control;

import java.util.ArrayList;

import CalendarGui.Login;
import cs113.calendar.model.appointment;
import cs113.calendar.model.backend;
import cs113.calendar.model.user;
import java.io.*;
import java.util.Calendar;
/**
 * 
 * @author Onwukike Ibe
 *
 */
public class control {
	
	public static Calendar startCalendar;
	public static Calendar endCalendar;
	public static user currentUser = null;
	public static ArrayList<user> usersInSystem = new ArrayList<user>();
	static ArrayList<appointment> conflictinglist = new ArrayList<appointment>();
	
	/**
	 * Converts a calendar object to a string.
	 * @param object
	 * @return
	 */
	public static String calendarToString(Calendar object)
	{
		String year = ""+object.get(object.YEAR);
		String month = ""+object.get(object.MONTH);
		String day = ""+object.get(object.DAY_OF_MONTH);
		String hour = ""+object.get(object.HOUR);
		String minute = ""+object.get(object.MINUTE);
		String result = month+"/"+day+"/"+year+" "+ hour+":"+minute;
		return result;
	}
	/**
	 * Returns true if a calendar object is equal to another calendar object, returns false otherwise.
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean calendarEqual(Calendar start, Calendar end)
	{
		/*System.out.println();
		System.out.println("EQUAL");
		System.out.println(calendarToString(start));
		System.out.println(calendarToString(end));
		System.out.println();*/
		int starty = start.get(start.YEAR);
		int startmo = start.get(start.MONTH);
		int startd = start.get(start.DAY_OF_MONTH);
		int starth = start.get(start.HOUR);
		int startm = start.get(start.MINUTE);
		int endy = end.get(end.YEAR);
		int endmo = end.get(end.MONTH);
		int endd = end.get(end.DAY_OF_MONTH);
		int endh = end.get(end.HOUR);
		int endm = end.get(end.MINUTE);
		
		if(starty == endy &&
		startmo == endmo &&
		startd == endd &&
		starth == endh &&
		startm == endm
		)
		{
			return true;
		}
		return false;
	}
	/**
	 * finds all conflicting appointments.
	 * @param one
	 * @param two
	 * @return
	 */
	public static boolean conflictingAppointments(appointment one, appointment two)
	{
		Calendar startOne = one.startDate;
		Calendar startTwo = two.startDate;
		Calendar endOne = one.endDate;
		Calendar endTwo = two.endDate;
		
		if(
				//one
			(startOne.after(startTwo)== true && endOne.before(endTwo)==true)
			||
			//two
			(startTwo.after(startOne)==true &&endTwo.before(endOne)==true)
			||
			//three
			(endOne.after(startTwo)==true && endOne.before(endTwo)==true)
			||
			//four
			(startOne.before(endTwo)==true && startOne.after(startTwo)==true)
			||
			//five
			(calendarEqual(startOne,startTwo)==true && calendarEqual(endOne,endTwo)==true)
			||
			//six
			(calendarEqual(startOne,endTwo)==true || calendarEqual(endOne,startTwo)==true || calendarEqual(endOne,endTwo)==true || calendarEqual(startOne,startTwo))
		)
		{
			//System.out.println("boolean "+calendarEqual(startOne,startTwo));
			/*System.out.println("s1 "+calendarToString(startOne));
			System.out.println("s2 "+calendarToString(startTwo));
			System.out.println("e1 "+calendarToString(endOne));
			System.out.println("e2 "+calendarToString(endTwo));
			System.out.println("START");
			System.out.println("block one "+(startOne.after(startTwo)== true && endOne.before(endTwo)==true));
			System.out.println("block two "+(startTwo.after(startOne)==true &&endTwo.before(endOne)==true));
			System.out.println("block three "+(endOne.after(startTwo)==true && endOne.before(endTwo)==true));
			System.out.println("block four "+(startOne.before(endTwo)==true && startOne.after(startTwo)==true));
			System.out.println("block five "+(calendarEqual(startOne,startTwo)==true && calendarEqual(endOne,endTwo)==true));
			System.out.println("block six "+(calendarEqual(startOne,endTwo)==true || calendarEqual(endOne,startTwo)==true || calendarEqual(endOne,endTwo)==true || calendarEqual(startOne,startTwo)));
			System.out.println("first "+calendarEqual(startOne,endTwo));
			System.out.println("second "+calendarEqual(endOne,startTwo));
			System.out.println("third "+calendarEqual(endOne,endTwo));
			System.out.println("fourth "+calendarEqual(startOne,startTwo));
			System.out.println("start of first "+ calendarToString(startOne));
			System.out.println("start of second "+ calendarToString(startTwo));*/
			return true;
		}
		return false;
	}
	
	
	/**
	 * creates a user with the given parameters.
	 * @param anID
	 * @param first
	 * @param middle
	 * @param last
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public static boolean createUser(String anID, String first, String middle, String last, String password) throws IOException
	{
		if(anID.equals(""))
		{
			return false;
		}
		if(new File("user",anID).exists()==true)
		{
			return false;
		}
		else
		{
			user tempUser = new user (anID);
			tempUser.setName(first, middle, last);
			tempUser.setPassword(password);
			try{backend.createUser(tempUser);}
			catch (IOException e){e.printStackTrace();System.out.println("There is an exception");}
			return true;
		}
	}
	
	/**
	 * adds a user to the arraylist of users
	 * @param temp_user
	 * @return
	 */
	public static boolean addUserToList(user temp_user)
	{
		//ADDS A USER TO AN ARRAYLIST
		if(usersInSystem.size() == 0)
		{
			usersInSystem.add(temp_user);
			return true;
		}
		else
		{
			for(int i = 0; i < usersInSystem.size();i++)
			{
				if(temp_user.ID.compareTo(usersInSystem.get(i).ID)==0)
				{
					return false;
				}
				else if(temp_user.ID.compareTo(usersInSystem.get(i).ID)>0)
				{
					if(i == (usersInSystem.size()-1))
					{
						usersInSystem.add(i+1, temp_user);
						return true;
					}
				}
				else
				{
					if(i==0)
					{
						usersInSystem.add(0, temp_user);
						return true;
					}
					else
					{
						usersInSystem.add(i,temp_user);
						return true;
					}
				}
				
			}
		}
		return false;
	}
	/**
	 * calls the backend for deleting users.
	 * @param anID
	 * @return
	 */
	public static boolean deleteUser(String anID)
	{
		if(backend.deleteUser(anID)==true)
		{
			return true;
		}
		else
		{
			return false;
		}
			
	}
	/**
	 * checks to see if the user exists and loads them into current user spot.
	 * @param userID
	 * @return
	 */
	public static boolean login(String userID)
	{
		if(userID.equals(""))
		{
			return false;
		}
		File check = new File("users",userID);
		if(check.exists()==true)
		{
			String password = new String(Login.passwordField.getPassword());
			user temp_user = backend.readUserFromMemory(userID);
			if(password.equals(temp_user.password))
			{
				currentUser = temp_user; 
				return true;
			}
			return false;
		}
		else
		{
			System.out.println(userID);
			return false;
		}
	}
	public static void logout()
	{
		backend.writeToMemory(currentUser.ID);	
			return;	
		
	}
	public static String[] listUsersInSystem()
	{
		return backend.listUsers();
	}
	public static boolean userSaved(user temp_user)
	{
		//not sure what this means we need to ask him.
		return false;
	}
	
	
	
	/**
	 * Creates an appointment to be stored and checks, for valid appointment entries.
	 * @param startYear
	 * @param startMonth
	 * @param startDay
	 * @param startHour
	 * @param startMinute
	 * @param endYear
	 * @param endMonth
	 * @param endDay
	 * @param endHour
	 * @param endMinute
	 * @param location
	 * @param description
	 * @return
	 */
	public static int createAppointment(String startYear, String startMonth, String startDay,String startHour, String startMinute,String endYear, String endMonth, String endDay,String endHour,String endMinute,String location,String description)
	{	
		
		Calendar new_appointment_start = Calendar.getInstance();
		Calendar new_appointment_end = Calendar.getInstance();
		boolean check = false;
		
		int starty = Integer.parseInt(startYear);
		int startmo = Integer.parseInt(startMonth);
		int startd = Integer.parseInt(startDay);
		int starth = Integer.parseInt(startHour);
		int startm = Integer.parseInt(startMinute);
		int endy = Integer.parseInt(endYear);
		int endmo = Integer.parseInt(endMonth);
		int endd = Integer.parseInt(endDay);
		int endh = Integer.parseInt(endHour);
		int endm = Integer.parseInt(endMinute);
		System.out.println("start month "+startmo);
		if(starty == endy &&
		startmo == endmo &&
		startd == endd &&
		starth == endh &&
		startm == endm)
		{
			return 5;
		}
			
		
		new_appointment_start.set(starty, startmo, startd, starth, startm);
		new_appointment_end.set(endy, endmo, endd, endh, endm);
		
		appointment tempAppoint = new appointment();
		tempAppoint.setDate(new_appointment_start,new_appointment_end);
		
		if(new_appointment_end.before(new_appointment_start)==false)
		{
			if(currentUser.appointmentList.isEmpty()==true)
			{
				appointment newAppoint = new appointment();
				newAppoint.setDescription(description);
				
				newAppoint.setLocation(location);
				newAppoint.setDate(new_appointment_start,new_appointment_end);
				currentUser.appointmentList.add(newAppoint);
				return 0;
			}
			
			Calendar startappoint;
			Calendar endappoint;
			for(int i = 0;i< currentUser.appointmentList.size();i++)
			{
				System.out.println("size "+currentUser.appointmentList.size()+"i "+i);
				startappoint = currentUser.appointmentList.get(i).startDate;
				
				endappoint = currentUser.appointmentList.get(i).endDate;
				if
				(
				(starty == startappoint.get(startappoint.YEAR)) &&
				(startmo == startappoint.get(startappoint.MONTH)) &&
				(startd == startappoint.get(startappoint.DAY_OF_MONTH)) &&
				(starth == startappoint.get(startappoint.HOUR)) &&
				(startm == startappoint.get(startappoint.MINUTE)) &&
				(endy == endappoint.get(endappoint.YEAR)) &&
				(endmo == endappoint.get(endappoint.MONTH)) &&
				(endd == endappoint.get(endappoint.DAY_OF_MONTH)) &&
				(endh == endappoint.get(endappoint.HOUR)) &&
				(endm == endappoint.get(endappoint.MINUTE)) &&
				(location.equals(currentUser.appointmentList.get(i).location)) &&
				(description.equals(currentUser.appointmentList.get(i).description))
				)
				{
					
					return 3;
				}
			}
			
			for(int k = 0; k < currentUser.appointmentList.size();k++)
			{
				
				
				if
				(conflictingAppointments(tempAppoint,currentUser.appointmentList.get(k))==true)
				{
					conflictinglist.add(currentUser.appointmentList.get(k));
					check = true;
				}
				
			}
			if(check == true)
			{
				appointment newAppoint = new appointment();
				newAppoint.setDescription(description);
				newAppoint.setLocation(location);
				newAppoint.setDate(new_appointment_start,new_appointment_end);
				currentUser.appointmentList.add(newAppoint);
				//PRINT CONFLICTING APPOINTMENTS TO WHERE THEY NEED TO BE
				conflictinglist.clear();
				check = false;
				return 2;
			}
			appointment newAppoint = new appointment();
			newAppoint.setDescription(description);
			newAppoint.setLocation(location);
			newAppoint.setDate(new_appointment_start,new_appointment_end);
			currentUser.appointmentList.add(newAppoint);
			return 0;
		}
		else
		{
			return 1;
		}
	}
	/**
	 * used in the simple view implementation
	 * @return
	 */
	public static boolean printAppointments()
	{
		if(currentUser == null)
		{
			return false;
		}
		
		return true;
	}
	/**
	 * used to remove an appointment.
	 * @param start
	 * @param end
	 * @param starttime
	 * @param endtime
	 * @param description
	 * @param location
	 * @return
	 */
	public static boolean removeAppointment(String start, String end,String starttime,String endtime,String description,String location)
	{
		ArrayList<appointment> list = currentUser.appointmentList;
		
		int startmonth = Integer.parseInt(start.substring(0,2));
		int startday =Integer.parseInt(start.substring(3,5));
		int startyear = Integer.parseInt(start.substring(6));
		int starthour = Integer.parseInt(starttime.substring(0,2));
		int startminute =Integer.parseInt(starttime.substring(3));
		
		int endmonth = Integer.parseInt(end.substring(0,2));
		int endday =Integer.parseInt(end.substring(3,5));
		int endyear = Integer.parseInt(end.substring(6));
		int endhour = Integer.parseInt(endtime.substring(0,2));
		int endminute =Integer.parseInt(endtime.substring(3));
		
		for(int i = 0; i < list.size();i++)
		{
			if((description.equals(list.get(i).description)) &&
			   (location.equals(list.get(i).location)) &&
			   (startmonth==(list.get(i).getStartMonth())) &&
			   (startday==(list.get(i).getStartDay())) &&
			   (startyear==(list.get(i).getStartYear())) &&
			   (starthour==(list.get(i).getStartHour())) &&
			   (startminute==(list.get(i).getStartMinute())) &&
			   (endmonth==(list.get(i).getEndMonth())) &&
			   (endday==(list.get(i).getEndDay())) &&
			   (endyear==(list.get(i).getEndYear())) &&
			   (endhour==(list.get(i).getEndHour())) &&
			   (endminute==(list.get(i).getEndMinute()))
			  )
			{
				list.remove(i);
				return true;
			}
		}
		
		return false;
	}
}