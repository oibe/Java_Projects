package CalendarGui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;
import cs113.calendar.control.*;

public class Login extends JFrame implements ActionListener {
	
	static public int left = 10;
	static public admincontrol enterAdmin;

    private JLabel userLabel = new JLabel("USERNAME");
    private JLabel passwordLabel = new JLabel("PASSWORD");
    static public JFormattedTextField userField;
    static public JPasswordField passwordField;
    ImagePanel panel;
    static public CalendarFrame main_frame;

    JButton login = new JButton("Login");

    /**
     * Initializes a login window.
     */
	public Login()
	{
		super("KAlendar");

		panel = new ImagePanel();
		this.add(panel);
		this.setSize(800,600);
		this.setLocation(150,100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		panel.setLayout(null);
		this.setVisible(true);
		
		userField = new JFormattedTextField();
		passwordField = new JPasswordField();
		
		userField.setBounds(380, 340, 140, 20);
		passwordField.setBounds(380, 380, 140, 20);
		userLabel.setBounds(300, 340, 140, 20);
		passwordLabel.setBounds(300, 380, 140, 20);
		userLabel.setForeground(new Color(255,255,255));
		passwordLabel.setBounds(300, 380, 140, 20);
		passwordLabel.setForeground(new Color(255,255,255));
		login.setBounds(350, 420, 130, 20);
		panel.add(userField);
		panel.add(passwordField);
		panel.add(userLabel);
		panel.add(passwordLabel);
		panel.add(login);
		this.setIconImage(panel.img2);
		login.addActionListener(this);
		repaint();
		userField.setText("");
		passwordField.setText("");
	}
	
	/**
	 * Sets up the login button
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (!userField.getText().contains("/"))
		{
		if (e.getSource()==login)
		{
			
			String user = userField.getText();
			String password = new String(passwordField.getPassword());
			
			if (user.equals("admin") && password.equals("admin"))
			{
				enterAdmin = new admincontrol();
				if (admincontrol.openingscreen!=null){admincontrol.openingscreen.dispose();}
				else {CalendarFrame.openingscreen.dispose();}
				
			}
			else
			{
			
				if(control.login(user)== false)
				{
					JOptionPane.showMessageDialog(login, "Incorrect Username/Password Combination", "Error", JOptionPane.PLAIN_MESSAGE, panel.passError);
				}
				else
				{
					
					control.login(userField.getText());
					//CalendarFrame.cmf =main_frame;
					main_frame= new CalendarFrame();
					CalendarFrame.openingscreen.dispose();
					if (miniBox.openingscreen!=null)
						{miniBox.openingscreen.dispose();}
				
					//CalendarFrame main_frame = new CalendarFrame();
					//should use boolean to signal in the main method to move on 
				}
			}
		}
		}
		else
		{
			JOptionPane.showMessageDialog(login, "Invalid character: /", "Error", JOptionPane.PLAIN_MESSAGE, panel.passError);
		}
	}	
	
	/**
	 * Creates an image panel.
	 * @author Akhil Gopinath
	 *
	 */
	class ImagePanel extends JPanel
	{
		
		ImageIcon icon = new ImageIcon("SystemFiles/login-background.jpg");
		Image img = icon.getImage();
		ImageIcon icon2 = new ImageIcon("SystemFiles/Icons/Icon-head.gif");
		Image img2 = icon2.getImage();
		ImageIcon passError = new ImageIcon("SystemFiles/Icons/bad-input.gif");
		Image img3 = icon2.getImage();
		/**
		 * initializes an image panel.
		 */
		ImagePanel()
		{
			Image i = img;	
		}
		/**
		 * draws the image onto the image panel repaint() calls this method.
		 */
		protected void paintComponent(Graphics g) 
		{
        		super.paintComponent(g);
        		g.drawImage(img, 0, 0, this);
    	}

	}
	
}