package cs113.calendar.model;

import java.io.*;
import cs113.calendar.control.*;
import java.util.*;
/**
 * 
 * @author Akhil Gopinath
 *
 */


public class backend 
{
	/**
	 * creates a file for the user
	 * @param aUser
	 * @throws IOException
	 */
	public static void createUser(user aUser) throws IOException
	{
		aUser.getID();
		File f = new File("users", aUser.getID());
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(aUser);
		oos.close();
	}
	/**
	 * reads user from memory
	 * @param userID
	 * @return
	 */
	public static user readUserFromMemory(String userID)
	{
		try
		{
			File f = new File("users",userID);
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			user temp = (user)ois.readObject();
			ois.close();
			return temp;
		}
		catch(IOException c)
		{
			
		}
		catch(ClassNotFoundException e)
		{
			
		}
		return null;
		
	}
	
	/**
	 * gets the info for the users theme
	 * @param themeName
	 * @return
	 */
	public static theme getThemeInfo(String themeName)
	{
		try
		{
			File f = new File("Themes/" +themeName + "/",themeName);
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			theme temp = (theme)ois.readObject();
			ois.close();
			return temp;
		}
		catch(IOException c)
		{
			
		}
		catch(ClassNotFoundException e)
		{
			
		}
		return null;
		
	}
	
	/**
	 * writes a user to memory
	 * @param userID
	 */
	public static void writeToMemory(String userID)
	{
		if(control.currentUser == null)
		{
			return;
		}
		try
		{
			File f = new File("users", userID);
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(control.currentUser);
			oos.close();
		}
		catch(IOException e)
		{
			
		}
		return;
	}
	/**
	 * deletes a user from memory
	 * @param userID
	 * @return
	 */
	public static boolean deleteUser(String userID) 
	{
		File fileToDelete = new File("users",userID);
		if(fileToDelete.exists()==true)
		{
			control.currentUser= null;
			fileToDelete.delete();
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * saves a user to memory
	 * @param temp
	 */
	public static void saveCurrent(user temp)
	{
		if(temp == null)
		{
			return;
		}
		try
		{
		FileOutputStream fos = new FileOutputStream("currentUser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(temp.ID);
		oos.close();
		}
		catch(IOException e)
		{
			//nothing should go wrong
		}
	}
	/**
	 * lists users in a directory
	 * @return
	 */
	public static String[] listUsers()
	{
		File a = new File("users");
		String[] list = a.list();
	
		if(list == null)
		{
			
			String[] temp = {"There are no users in the system."};
			return temp;
		}
		else
		{
			
			return list;
		}
	}
}