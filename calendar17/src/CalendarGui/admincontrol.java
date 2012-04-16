package CalendarGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import cs113.calendar.control.control;
import cs113.calendar.model.appointment;
import cs113.calendar.model.backend;
import cs113.calendar.model.user;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class admincontrol extends JFrame implements ActionListener {

    JButton buttonAddUser = new JButton("Add User");
    JButton deleteUser = new JButton("Delete User");
    JButton logOut = new JButton("Logout");
    int counter=0;
    int thisvariableisonlyheresoicancommitt;
    static public Login openingscreen;
    

    static public String data[][];
	static public String tempusers[] = control.listUsersInSystem();
	static public ArrayList<String> users = new ArrayList<String>(Arrays.asList(tempusers));
	static public String col[] = {"USER NAME","PASSWORD","FIRST NAME","LAST NAME"};
	static public DefaultTableModel model = new DefaultTableModel(data,col);
	JTable table = new JTable(model);

	/**
	 * This constructor opens the frame for an administrator control window.
	 */
	public admincontrol()
	{
		super("Administrator Control Panel");

		ImagePanel panel = new ImagePanel();
		this.add(panel);
		this.setSize(800,600);
		this.setLocation(150,100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		panel.setLayout(null);
		this.setVisible(true);
		
		tempusers = control.listUsersInSystem();
		users = new ArrayList<String>(Arrays.asList(tempusers));
		
		data = new String[users.size()][4];
		model.setRowCount(0);
		for (int i=0; i<users.size(); i++)
		{
			System.out.println("users "+users);
			user temp = backend.readUserFromMemory(users.get(i));
			String username = temp.getID();
			String password = temp.getPassword();
			String first = temp.getFirstName();
			String last = temp.getLastName();
			
			data[i][0]=username;
			data[i][1]=password;
			data[i][2]=first;
			data[i][3]=last;
			model.addRow(data[i]);
			
		}
		
		
		
		this.setIconImage(panel.img2);
		buttonAddUser.addActionListener(this);
		deleteUser.addActionListener(this);
		logOut.addActionListener(this);
		
		JTableHeader header = table.getTableHeader();
	    header.setBackground(new Color(70, 130, 180));
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(20,250,560,300);
		pane.setBorder(BorderFactory.createLineBorder(Color.black));
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(pane);
		
		
		panel.repaint();
		buttonAddUser.setBounds(600, 280, 130, 40);
		panel.add(buttonAddUser);
		deleteUser.setBounds(600, 380, 130, 40);
		panel.add(deleteUser);
		logOut.setBounds(600, 480, 130, 40);
		panel.add(logOut);
		
		panel.repaint();
		
	}
	
	/**
	 * This method carries out the functions for our buttons that use the ActionListener interface.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==buttonAddUser)
		{
			addUser frame = new addUser();
		}
		
		
		
		if (e.getSource()==deleteUser)
		{
			int[] selectedRows=table.getSelectedRows();
			for (int i=0; i<selectedRows.length; i++)
			{
				System.out.println(selectedRows[i]);
				backend.deleteUser(users.get(selectedRows[i]-i));
				//System.out.println(users.get(selectedRows[i]-i));
				users.remove(selectedRows[i]-i);
				model.removeRow(selectedRows[i]-i);
			}
		}
		if (e.getSource()==logOut)
		{
			System.out.println("Logout");
			openingscreen = new Login();
			Login.enterAdmin.dispose();
		}
		
	}	
	
	/**
	 *Class for an ImagePanel
	 * @author oibe
	 *
	 */
	class ImagePanel extends JPanel
	{
		
		ImageIcon icon = new ImageIcon("SystemFiles/admin-background.jpg");
		Image img = icon.getImage();
		ImageIcon icon2 = new ImageIcon("SystemFiles/Icons/Icon-head.gif");
		Image img2 = icon2.getImage();
		ImageIcon passError = new ImageIcon("Incorrect-password.gif");
		Image img3 = icon2.getImage();
		/**
		 * Initialzes the image for the admincontrol frame
		 * @author AkhilGopinath
		 *
		 */
		ImagePanel()
		{
			Image i = img;	
		}
		/**
		 * draws an image on to the panel called by repaint() as well.
		 * @author AkhilGopinath
		 *
		 */
		protected void paintComponent(Graphics g) 
		{
        		super.paintComponent(g);
        		g.drawImage(img, 0, 0, this);
    	}

	}
}