package CalendarGui;
import javax.swing.*;


import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.Calendar;

public class CalendarFrame extends JFrame implements ActionListener
{

	static public ImagePanel panel;
	static public OptionsPanel options;
	static public JPanel current_panel;
	static public Login openingscreen;
	static public int width;
	static public int height;
	static public AppointmentGenericPanel ep = new EditAppointmentPanel();
	static public AppointmentGenericPanel cp = new CreateAppointmentPanel();
	static public CalendarScroll cs = new CalendarScroll();
	
	static public CalendarFrame cmf;
	
	//ALIGNMENT VARIABLES
	static public int left = 10;
	static public ImageIcon icon = new ImageIcon("Themes/Warped-Time.jpg");
	static public Calendar supremeInstance= Calendar.getInstance();
	static public CurrentMonthPanel currmop = new CurrentMonthPanel(supremeInstance.get(Calendar.MONTH),supremeInstance.get(Calendar.YEAR));
	static public CalendarMainPanel main_panel = new CalendarMainPanel(supremeInstance);
	
	/**
	 * The main method for our program launches the CalendarFrame
	 * @param args
	 */
	public static void main(String[] args)
	{
		//VERY IMPORTANT REMINDER OF STUFF TO DO
		//MAKE SPINNER FOR DAYS CALLED BY METHOD
		//CALENDAR BUTTON THAT CHANGES YEAR AND MONTH
		//SPINNERS ARE THERE JUST NOT VISIBLE PROBABLY COLOR SETTING PROBLEM
		//admin control logout then log back in and the users are doubled, not good,also causes conflict problems,could affect session persistence if not fixed
		deleteDir(new File("users/CVS"));
		openingscreen = new Login();
		
	}
	/**
	 * Deletes a random CVS Folder that appears everytime we commit to CVS.
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        
        return dir.delete();
	}
	
	/**
	 *This is the constructor for Our main page, this constructor initializes a CalendarFrame Object.
	 */
	public CalendarFrame()
	{
		super("Calendar");
		/*Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)dim.getWidth();
		height = (int)dim.getHeight();*/
		int z = supremeInstance.MONTH+1;
		
		ImageIcon icon = new ImageIcon("SystemFiles/Icons/Icon-head.gif");
		Image frame_image = icon.getImage();
		this.setIconImage(frame_image);
		
		this.setSize(800,600);
		this.setLocation(150,100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.add(cs);
		
		panel = new ImagePanel();
		this.add(panel);
		panel.setLayout(null);
		options = new OptionsPanel();
		panel.add(options);
		
		miniBox box = new miniBox();
		panel.add(box);
		
		this.setVisible(true);
		InformationPanel info_panel = new InformationPanel();
		panel.add(info_panel);
		
		
		panel.add(currmop);
		panel.add(main_panel);
		DayPanel day = new DayPanel();
		panel.add(day);
		SearchPanel search = new SearchPanel();
		panel.add(search);
		//WeekButtonPanel wbp = new WeekButtonPanel();
		//panel.add(wbp);
		
		current_panel = options;
		
		CalendarFrame.panel.repaint();
	}
	
	/**
	 * Implemented to satisfy an ActionListener interface
	 */
	public void actionPerformed(ActionEvent e)
	{
		
		
	}	
	
	/**
	 * 
	 * @author oibe
	 *This is the image panel class thats used to display our background image
	 */
	public static class ImagePanel extends JPanel
	{
		
		
		public static Image img = icon.getImage();
		/**
		 * Constructor that initializes an ImagePanel object
		 */
		ImagePanel()
		{
			Image i = img;	
		}
		
		/**
		 * At runtime this method paints the background on the Image Panel, this is the method repaint() calls.
		 */
		protected void paintComponent(Graphics g) 
		{
        		super.paintComponent(g);
        		g.drawImage(img, 0, 0, this);
    	}

	}
	
}


