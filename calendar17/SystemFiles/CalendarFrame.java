package CalendarGui;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
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
	//ALIGNMENT VARIABLES
	static public int left = 10;
	
	
	public static void main(String[] args)
	{
		
			//openingscreen = new Login();
		//CalendarFrame temp = new CalendarFrame();
	}
	public CalendarFrame()
	{
		super("Calendar");
		/*Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)dim.getWidth();
		height = (int)dim.getHeight();*/
		
		ImageIcon icon = new ImageIcon("awesome-face.jpg");
		Image frame_image = icon.getImage();
		this.setIconImage(frame_image);
		
		this.setSize(800,600);
		this.setLocation(150,100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
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
		CalendarMainPanel main_panel = new CalendarMainPanel();
		panel.add(main_panel);
		DayPanel day = new DayPanel();
		panel.add(day);
		SearchPanel search = new SearchPanel();
		panel.add(search);
		WeekButtonPanel wbp = new WeekButtonPanel();
		panel.add(wbp);
		CalendarFrame.panel.repaint();
		current_panel = options;
		
	}
	
	public static void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align)
	{
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = width;
		gc.gridheight = height;
		gc.weightx = 100;
		gc.weighty = 100;
		gc.insets = new Insets(5,5,5,5);
		gc.anchor = align;
		gc.fill = GridBagConstraints.NONE;
		p.add(c,gc);
	}
	public static void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align,int fill)
	{
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = x;
		gc.gridy = y;
		gc.gridwidth = width;
		gc.gridheight = height;
		gc.weightx = 100;
		gc.weighty = 100;
		gc.insets = new Insets(5,5,5,5);
		gc.anchor = align;
		gc.fill = fill;
		p.add(c,gc);
	}
	public void actionPerformed(ActionEvent e)
	{
		
	}	
	class ImagePanel extends JPanel
	{
		
		ImageIcon icon = new ImageIcon("Themes/Crimson-Dawn.jpg");
		Image img = icon.getImage();
	
		ImagePanel()
		{
			Image i = img;	
		}
	
		protected void paintComponent(Graphics g) 
		{
        		super.paintComponent(g);
        		g.drawImage(img, 0, 0, this);
    	}

	}
	
}


