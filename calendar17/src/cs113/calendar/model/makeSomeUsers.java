package cs113.calendar.model;

import java.io.IOException;

import CalendarGui.Login;

public class makeSomeUsers {

	public static void main(String[] args)
	{
			String ID;
			String firstName;
			String MiddleName;
			String LastName;
			
			String password;
			
		
			for (int i=0; i<20; i++)
			{
				user tempUser= new user("Silver"+i);
				tempUser.setName("Akhil"+i, "Danger"+i, "Gopinath"+i);
				tempUser.setPassword("password"+i);
				try {
					backend.createUser(tempUser);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.out.println("done");
	}
}
