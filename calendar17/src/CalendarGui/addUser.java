package CalendarGui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import cs113.calendar.control.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import cs113.calendar.model.*;


public class addUser extends JFrame implements ActionListener {

	static public JButton buttonAddUser = new JButton("Add User");
	static public JButton cancel = new JButton("Cancel");
	public String fire;
    private JLabel firstName = new JLabel("First Name");
    private JLabel middleName = new JLabel("Middle Name");
    private JLabel lastName = new JLabel("Last Name");
    private JLabel userID = new JLabel("User ID");
    private JLabel password = new JLabel("Password");
    private JLabel rePassword = new JLabel("Re-Enter Password");
    private JFormattedTextField firstNameField = new JFormattedTextField();
    private JFormattedTextField middleNameField = new JFormattedTextField();
    private JFormattedTextField lastNameField = new JFormattedTextField();
    private JFormattedTextField userIDField = new JFormattedTextField();
    private JFormattedTextField passwordField = new JFormattedTextField();
    private JFormattedTextField rePasswordField = new JFormattedTextField();
	public ImagePanel panel;
	
	/**
	 * This method initializes a user Frame where all the parameters necessary for adding users are specified.
	 */
	public addUser()
	{
		super("Administrator Control Panel");

		panel = new ImagePanel();
		this.add(panel);
		this.setSize(400,480);
		this.setLocation(750,100);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		panel.setLayout(null);
		this.setVisible(true);
		
		firstName.setBounds(40, 140, 140, 20);
		firstName.setForeground(new Color(255,255,255));
		panel.add(firstName);
		middleName.setBounds(40, 180, 140, 20);
		middleName.setForeground(new Color(255,255,255));
		panel.add(middleName);
		lastName.setBounds(40, 220, 140, 20);
		lastName.setForeground(new Color(255,255,255));
		panel.add(lastName);
		userID.setBounds(40, 260, 140, 20);
		userID.setForeground(new Color(255,255,255));
		panel.add(userID);
		password.setBounds(40, 300, 140, 20);
		password.setForeground(new Color(255,255,255));
		panel.add(password);
		rePassword.setBounds(40, 340, 140, 20);
		rePassword.setForeground(new Color(255,255,255));
		panel.add(rePassword);
		
		firstNameField.setBounds(190, 140, 140, 20);
		panel.add(firstNameField);
		middleNameField.setBounds(190, 180, 140, 20);
		panel.add(middleNameField);
		lastNameField.setBounds(190, 220, 140, 20);
		panel.add(lastNameField);
		userIDField.setBounds(190, 260, 140, 20);
		panel.add(userIDField);
		passwordField.setBounds(190, 300, 140, 20);
		panel.add(passwordField);
		rePasswordField.setBounds(190, 340, 140, 20);
		panel.add(rePasswordField);
		
		buttonAddUser.setBounds(100 ,400, 100, 40);
		panel.add(buttonAddUser);
		
		cancel.setBounds(210,400, 100,40);
		panel.add(cancel);
		
		buttonAddUser.addActionListener(this);
		cancel.addActionListener(this);

		
		
		this.setIconImage(panel.img2);
		panel.repaint();
		
	}
	
	
	/**
	 * This method is here to satisfy the ActionListener interface.
	 */
	public void actionPerformed(ActionEvent e)
	{
		
		
	if (!userIDField.getText().contains("/"))
	{
		if (e.getSource()==addUser.buttonAddUser)
		{
			if (userIDField.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(buttonAddUser, "Must Enter UserID.", "Error", JOptionPane.PLAIN_MESSAGE, panel.passError);return;
			}
			
		    File f = new File("users/"+userIDField.getText());
		    System.out.println(!f.exists());
		    if (!f.exists())
		    {
			System.out.println("the button from addUser panel was clicked");
			String id = userIDField.getText();
			String first = firstNameField.getText();
			String middle = middleNameField.getText();
			String last = lastNameField.getText();
			String password = passwordField.getText();
			if(password.equals(rePasswordField.getText()))
			{
				try{
					admincontrol.users.add(userIDField.getText());
					control.createUser(id, first, middle, last, password);
					String newUserLine[] = new String [4];
					newUserLine[0] =id;
					newUserLine[1] =password;
					newUserLine[2] =first;
					newUserLine[3] =middle;
					admincontrol.model.addRow(newUserLine);
					userIDField.setText("");
					firstNameField.setText("");
					middleNameField.setText("");
					lastNameField.setText("");
					passwordField.setText("");
					rePasswordField.setText("");
					admincontrol.users.add(userIDField.getText());
				}
				catch(IOException z)
				{
					System.out.print("IOException in buttonAddUser");
				}
			}
			else
			{
				passwordField.setText("");
				rePasswordField.setText("");
				JOptionPane.showMessageDialog(buttonAddUser, "Passwords do not match.", "Error", JOptionPane.PLAIN_MESSAGE, panel.passError);
			}
		    }
		    else
		    {
		    	JOptionPane.showMessageDialog(buttonAddUser, "UserID already exists", "Error", JOptionPane.PLAIN_MESSAGE, panel.passError);
		    }
		}
		}
		else
		{
			JOptionPane.showMessageDialog(buttonAddUser, "Invalid Character /", "Error", JOptionPane.PLAIN_MESSAGE, panel.passError);
		}
		
		if (e.getSource()==cancel)
		{
			this.dispose();
		}
		

		
	}	
	
	/**
	 * This is the class that allows us to put an image on the back of a panel
	 * @author oibe
	 *
	 */
	class ImagePanel extends JPanel
	{
		
		ImageIcon icon = new ImageIcon("SystemFiles/addUser-Background.jpg");
		Image img = icon.getImage();
		ImageIcon icon2 = new ImageIcon("Icon-head.gif");
		Image img2 = icon2.getImage();
		ImageIcon passError = new ImageIcon("Incorrect-password.gif");
		Image img3 = icon2.getImage();
		
		/**
		 * This the constructor that initializes an image variable for our ImagePanel class.
		 */
		ImagePanel()
		{
			Image i = img;	
		}
		
		/**
		 * This method is called at runtime to draw the Image onto the panel the repaint() method draws this panel.
		 */
		protected void paintComponent(Graphics g) 
		{
        		super.paintComponent(g);
        		g.drawImage(img, 0, 0, this);
    	}

	}
}