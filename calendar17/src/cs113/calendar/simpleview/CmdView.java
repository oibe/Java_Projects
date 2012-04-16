package cs113.calendar.simpleview;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import cs113.calendar.control.*;
import cs113.calendar.model.appointment;
/**
 * 
 * @author Onwukike Ibe
 *
 */
public class CmdView
{
	public static void main(String[] args) 
	{
		String input = args[0].toUpperCase();
		
		int command=0;
		
		if (input.equals("LOGIN")) command=1;
		if (input.equals("LISTUSERS")) command=2;
		if (input.equals("ADDUSER")) command=3;
		if (input.equals("DELETEUSER")) command=4;
		
		switch (command) {														//* SWITCH >>command Begin<<
		
		case 1:																	//* command case1: Login and begin interactive command
			if (control.login(args[1])==true)									//* Checks if user exists
			{
				System.out.print("Logged in as: ");
				System.out.println(args[1]);
				Scanner sc = new Scanner(System.in);							//* Declare inputer variable
				String tempInput = sc.nextLine();								//* Takes input from user
				String[] interactiveInput = tempInput.split(" ");				//* Splits the input by spaces and stores it into an array
				interactiveInput[0]=interactiveInput[0].toUpperCase();			//* Upper-cases everything
				int interactiveCommand=0;										//* (Re)sets the case statement variable to zero.
				System.out.println(interactiveInput[0]);
				
				if (interactiveInput[0].equals("CREATE")) interactiveCommand=1;
				if (interactiveInput[0].equals("DELETE")) interactiveCommand=2;
				if (interactiveInput[0].equals("LIST")) interactiveCommand=3;
				if (interactiveInput[0].equals("LOGOUT")) interactiveCommand=4;
				
				while (interactiveCommand!=4)									//* Loop which runs until user selects logout begins
				{
					switch (interactiveCommand) {								//* SWITCH >>interactiveCommand Begin<<
					
					case 1:														//* interactiveCommand Case1: Create Appointment.
						System.out.println(interactiveCommand);
						int errorCheck=0;/*(control.createAppointment(				//* errorCheck will be a
										interactiveInput[3].substring(0,10),	//* 0: Appointment created successfully.
								        interactiveInput[4].substring(0,10),	//* 1: Appointment did not have a valid start and end date.
								        interactiveInput[3].substring(11,16),	//* 2: There is a conflict between appointments
								        interactiveInput[4].substring(11,16),	//* 3: Appointment already exists.
								        interactiveInput[1],interactiveInput[2])//*
								        );*/
						
						switch (errorCheck){									//* SWITCH >>errorCheck Begin<<
						case 0:													//* Case when appointment exists.
							
							System.out.println(args[1]);
							System.out.print(interactiveInput[1]);
							System.out.print(" ");
							System.out.print(interactiveInput[2]);
							System.out.print(" ");
							System.out.print(interactiveInput[3]);
							System.out.print(" ");
							System.out.println(interactiveInput[4]);			
							break;
						case 1:													//* Case when invalid starting and ending dates.
							System.out.print("invalid starting and ending date.");
							break;
						case 2:													//* Case when appointments are conflicting.
							System.out.println(" i get here");
							ArrayList<appointment> conflictlist = null;/*(control.findConflict(						//* Gets a list of appointments
																   interactiveInput[3].substring(0,10),			//* that conflicts with entered
																   interactiveInput[4].substring(0,10),			//* appointment.
																   interactiveInput[3].substring(11,16),		//*
																   interactiveInput[4].substring(11,16),		//*
																   interactiveInput[1],interactiveInput[2])		//*
																   );		*/									//*
							
							for(int i = 0; i<= conflictlist.size();i++)			//* Loop to print list of conflicting appointments
							{
								appointment holder = conflictlist.get(i);
								System.out.print("conflicts with ");
								System.out.print(holder.description);
								System.out.print(" ");
								System.out.print(holder.location);
								System.out.print(" ");
								
								System.out.print(holder.getStartMonth()+"/"+holder.getStartDay()+"/"+holder.getStartYear()+"-");
								System.out.print(holder.getEndHour()+":"+holder.getStartMinute());
								System.out.print(" ");
								System.out.print(holder.getEndMonth()+"/"+holder.getEndDay()+"/"+holder.getEndYear()+"-");
								System.out.println(holder.getEndHour()+":"+holder.getEndMinute());
							}
							
							break;
						case 3:													//* Case when appointment already exists.
							System.out.print("appointment exists for user ");
							System.out.println(args[1]);
							System.out.print(interactiveInput[1]);
							System.out.print(" ");
							System.out.print(interactiveInput[2]);
							System.out.print(" ");
							System.out.print(interactiveInput[3]);
							System.out.print(" ");
							System.out.println(interactiveInput[4]);	
							break;
						default: System.out.println("System Error, invalid interactive command Number");break;
						
						}														//* SWITCH >>errorCheck End<<
						break;
					case 2:														//* interactiveCommand Case2: Delete Appointment.
						System.out.println(interactiveCommand);
						if (control.removeAppointment(interactiveInput[3].substring(0,10),					//* Checks to see if appointment 
													  interactiveInput[4].substring(0,10),					//* was removed.
													  interactiveInput[3].substring(11,16),					//*
													  interactiveInput[4].substring(11,16),					//*
													  interactiveInput[1],interactiveInput[2])==true)		//*
						{
							System.out.print("deleted appointment from user ");
							System.out.print(args[1]);
							System.out.println(":");
							System.out.print(interactiveInput[1]);
							System.out.print(" ");
							System.out.print(interactiveInput[2]);
							System.out.print(" ");
							System.out.print(interactiveInput[3]);
							System.out.print(" ");
							System.out.print(interactiveInput[4]);
						}
						else {System.out.print("Appointment does not exist.");}
						break;
						
					case 3:														//* interactiveCommand Case3: List Appointments.
						for(int i = 0; i< control.currentUser.appointmentList.size();i++)
						{
							appointment holder = control.currentUser.appointmentList.get(i);
							System.out.print(holder.description + " ");
							System.out.print(holder.location + " ");
							System.out.print("Starting time: ");
							
							System.out.print(holder.getStartMonth()+"/"+
											 holder.getStartDay()+"/"+
											 holder.getStartYear() + " ");
							
							if (holder.getStartHour()<10) {System.out.print("0" + holder.getStartHour() + ":");}
							else {System.out.print(holder.getStartHour() + ":");}
							if (holder.getStartMinute()<10) {System.out.print("0" + holder.getStartMinute());}
							else {System.out.print(holder.getStartMinute());}
							
							System.out.print(" Ending time: ");
							System.out.print(holder.getEndMonth()+"/"+
											 holder.getEndMonth()+"/"+
											 holder.getEndYear()+ " ");
							if (holder.getEndHour()<10) {System.out.print("0" + holder.getEndHour() + ":");}
							else {System.out.print(holder.getEndHour() + ":");}
							if (holder.getEndMinute()<10) {System.out.print("0" + holder.getEndMinute());}
							else {System.out.println(holder.getEndMinute());}
						}	
						control.printAppointments();
						
						break;
					default: System.out.println("Invalid Input.");break;		//* interactiveCommand Default: bad input.
					}															//* SWITCH >>interactiveCommand End<<
					
					tempInput = sc.nextLine();
					interactiveInput = tempInput.split(" ");
					interactiveInput[0]=interactiveInput[0].toUpperCase();
					System.out.println(interactiveInput[0]);
					if (interactiveInput[0].equals("CREATE")) interactiveCommand=1;
					if (interactiveInput[0].equals("DELETE")) interactiveCommand=2;
					if (interactiveInput[0].equals("LIST")) interactiveCommand=3;
					if (interactiveInput[0].equals("LOGOUT")) interactiveCommand=4;
				}															//* Loop which runs until user selects logout ends			
			}
			else {System.out.print("User not found.");}						//* If user not found it responds with this and exists.
			break;
		case 2:																//* command case2: List Users in System
			String[] users=control.listUsersInSystem();
			for(int i=0; i < users.length; i++)
			    System.out.println( users[i] );
			break;
		case 3:																//* command case3: Create User
			if ((args.length)==5)
				try 
			    {
					if (control.createUser(args[1], args[2], args[3], args[4],"monkey")==false)
					System.out.println("That user ID has been taken, or is invalid.");
					else {
							System.out.print("created user ");
							System.out.print(args[1]);
							System.out.print(" with name ");
							System.out.print(args[2]);
							System.out.print(" ");
							System.out.print(args[3]);
							System.out.print(" ");
							System.out.println(args[4]);		
						 }
				}
			    catch (IOException e) {e.printStackTrace();}
			else if ((args.length)==4)
				 try 
				 {
					if (control.createUser(args[1], args[2], "", args[3],"wrench")==false)
					System.out.println("That user ID has been taken, or is invalid.");
					else {
							System.out.print("created user ");
							System.out.print(args[1]);
							System.out.print(" with name ");
							System.out.print(args[2]);
							System.out.print(" ");
							System.out.println(args[3]);
						 }
						
				 }
				 catch (IOException e) {e.printStackTrace();}
				 else {System.out.println("Invalid Input.");}
			break;
		case 4:																//* command case3: Delete User
			if (control.deleteUser(args[1])==true) {System.out.print("deleted user "); System.out.println(args[1]);}
			else {System.out.print("User not found.");}
			break;
		default: System.out.println("Invalid Input.");break;				//* command Default: Bad input.
		
		}																	//* SWITCH >>command End<<

		
		
		/*
		try {
			control.bootUp();
		} catch (IOException e) {
			
		}
		System.out.println("Welcome to E-Calendar!");
		System.out.println("Options:");
		System.out.println("Create a user: 'create'");
		System.out.println("Login as a user: 'login'");
		System.out.println("List a user: 'list");
		System.out.println("Delete a user: 'delete'");
		System.out.println("Make an appointment: 'make appointment'");
		System.out.println("Delete an appointment: 'delete appointment'");
		System.out.println("To list appoinments: 'print appointments'");
		System.out.println("To Logout and terminate program: 'logout'");
		boolean mastercheck = false;
		while(mastercheck == false)
		{
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		if(input.equals("create"))
		{
			System.out.println("Enter your first name");
			String first = sc.nextLine();
			System.out.println("Enter your middle name");
			String middle = sc.nextLine();
			System.out.println("Enter your last name");
			String last = sc.nextLine();
			System.out.println("Enter your user id");
			String id = sc.nextLine();
			try{
				while(control.createUser(id, first, middle, last)==false)
				{
					
					System.out.println("Enter your user id");
					id = sc.nextLine();
				}
				System.out.println(id+" has successfully been created.");
			}
			catch(IOException e)
			{
				System.out.println("Cmd exceptio0n 2");
			}
	
			
		}
		else if(input.equals("print appointments"))
		{
			if(control.printAppointments()==false)
			{
				System.out.println("You must log in to print appointments");
			}
		}
		else if(input.equals("login"))
		{
			System.out.println("Enter your first name");
			String first = sc.nextLine();
			System.out.println("Enter your userID");
			String id = sc.nextLine();
			control.login(first, id);
			
		}
		else if(input.equals("list"))
		{
			control.listUsersInSystem();
		}
		else if(input.equals("make appointment"))
		{
				if(control.currentUser== null)
				{
					System.out.println("A user must be logged in before creating an appointment.");
				}
				else
				{
				
					System.out.println("Enter a starting date in the format: MM/DD/YYYY");
					String start = sc.nextLine();
					
					while(control.dateFormat(start)==false)
					{
						System.out.println("The format of your date was invalid, try entering the date again");
						System.out.println("Enter a starting date in the format: MM/DD/YYYY");
						start = sc.nextLine();
					}
				
					System.out.println("Enter an ending date in the format: MM/DD/YYYY");
					String end = sc.nextLine();
				
					while(control.dateFormat(end)==false)
					{
						System.out.println("The format of your date was invalid, try entering the date again");
						System.out.println("Enter a starting date in the format: MM/DD/YYYY");
						end = sc.nextLine();
					}
		
					System.out.println("Enter a valid hour and minute for the starting time in the form:   HH:MM");
					String starttime= sc.nextLine();
					while(control.timeFormat(starttime)==false)
					{
						System.out.println("That time is invalid try entering a correctly formated time again.");
						System.out.println("Enter a valid hour and minute for the starting time in the form:   HH:MM");
						starttime = sc.nextLine();
					}	
				
					System.out.println("Enter a valid hour and minute for the ending time in the form HH:MM");
					String endtime = sc.nextLine();
					while(control.timeFormat(endtime)==false)
					{
						System.out.println("That time is invalid try entering a correctly formated time again.");
						System.out.println("Enter a valid hour and minute for the ending time in the form:   HH:MM");
						endtime = sc.nextLine();
					}
					System.out.println("Enter a description for your appointment.");
					String tempdescription = sc.nextLine();
					System.out.println("Enter a location for your appointment");
					String templocation = sc.nextLine();
					
					control.createAppointment(start,end,starttime,endtime,tempdescription,templocation);
				}
		}
		else if(input.equals("logout"))
		{
			System.out.println("Thank you for using our Calendar program");
			control.logout();
			return;
		}
		else if(input.equals("delete"))
		{
			System.out.println("Enter the id of the user you want to delete");
			String tempuser = sc.nextLine();
			control.deleteUser(tempuser);
		}
		else if(input.equals("delete appointment"))
		{
			System.out.println("Enter the description of the appointment you want to remove");
			String description = sc.nextLine();
			if(control.removeAppointment(description)==true)
			{
				System.out.println("The appointment with the description "+description+ " has been deleted");
			}
			else
			{
				System.out.println("The appointment with the description "+description+" cannot be found.");
			}
		}
		else if(input.equals("java cs113.calendar.simpleview.CmdView listusers"))
		{
		}
		else if(input.equals("java cs113.calendar.simpleview.CmdView deleteuser <user id> "))
		{
		}
		else
		{
			System.out.println("We are sorry,but we did not recognize that input, try choosing an option again.");
		}
		}*/
	}
		
}
