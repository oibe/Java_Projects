package CalendarGui;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public abstract class AppointmentGenericPanel extends GenericPanel
{
	JLabel startYear = new JLabel("Start Year");
	JLabel startMonth = new JLabel("Start Month");
	JLabel startDay = new JLabel("Start Day");
	JLabel startHour = new JLabel("Start Hour");
	JLabel startMinute = new JLabel("Start Minute");
	JLabel endYear = new JLabel("End Year");
	JLabel endMonth = new JLabel("End Month");
	JLabel endDay = new JLabel("End Day");
	JLabel endHour = new JLabel("End Hour");
	JLabel endMinute = new JLabel("End Minute");
	
	static SpinnerModel sym = new SpinnerNumberModel(2009,2009-100,2009+100,1);
	static SpinnerModel smom = new SpinnerNumberModel(1,1,12,1);
	static SpinnerModel sdm = new SpinnerNumberModel(1,1,31,1);
	static SpinnerModel shm = new SpinnerNumberModel(0,0,23,1);
	static SpinnerModel smm = new SpinnerNumberModel(0,0,59,1);
	JSpinner startYear_spinner = new JSpinner(sym);
	JSpinner startMonth_spinner = new JSpinner(smom);
	JSpinner startDay_spinner = new JSpinner(sdm);
	JSpinner startHour_spinner = new JSpinner(shm);
	JSpinner startMinute_spinner = new JSpinner(smm);
	
	static SpinnerModel eym = new SpinnerNumberModel(2009,2009-100,2009+100,1);
	static SpinnerModel emom = new SpinnerNumberModel(1,1,12,1);
	static SpinnerModel edm = new SpinnerNumberModel(1,1,31,1);
	static SpinnerModel ehm = new SpinnerNumberModel(0,0,23,1);
	static SpinnerModel emm = new SpinnerNumberModel(0,0,59,1);
	JSpinner endYear_spinner = new JSpinner(eym);
	JSpinner endMonth_spinner = new JSpinner(emom);
	JSpinner endDay_spinner = new JSpinner(edm);
	JSpinner endHour_spinner = new JSpinner(ehm);
	JSpinner endMinute_spinner = new JSpinner(emm);
	JLabel location = new JLabel("Location");
	JLabel description = new JLabel("Description");
	JTextArea location_area = new JTextArea();
	JTextArea description_area = new JTextArea();
	
	//didn't work previously because spinners were declared static
	
	/**
	 * This class is an abstract class for the Create,Edit, and Search Appointment Panels, it initializes the setBounds methods and other parameters
	 * for the classes that extend it.
	 */
	public AppointmentGenericPanel()
	{
		this.setOpaque(false);
		location.setBounds(10,220,80,20);
		location.setForeground(new Color(255,255,255));
		this.add(location);
		location_area.setBounds(10,240,170,50);
		location_area.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(location_area);
		description.setForeground(new Color(255,255,255));
		description.setBounds(10, 290, 80, 15);
		this.add(description);
		description_area.setBounds(10,305,170,70);
		description_area.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(description_area);
		
		startYear.setBounds(10,20,80,20);
		startMonth.setBounds(10,40,80,20);
		startDay.setBounds(10,60,80,20);
		startHour.setBounds(10,80,80,20);
		startMinute.setBounds(10,100,80,20);
		endYear.setBounds(10,120,80,20);
		endMonth.setBounds(10,140,80,20);
		endDay.setBounds(10,160,80,20);
		endHour.setBounds(10,180,80,20);
		endMinute.setBounds(10,200,80,20);
		
		startYear_spinner.setEditor(new JSpinner.NumberEditor(startYear_spinner, "#"));
		endYear_spinner.setEditor(new JSpinner.NumberEditor(endYear_spinner, "#"));
		startYear_spinner.setBounds(100,20,50,16);
		startMonth_spinner.setBounds(100,40,50,16);
		startDay_spinner.setBounds(100,60,50,16);
		startHour_spinner.setBounds(100,80,50,16);
		startMinute_spinner.setBounds(100,100,50,16);
		endYear_spinner.setBounds(100,120,50,16);
		endMonth_spinner.setBounds(100,140,50,16);
		endDay_spinner.setBounds(100,160,50,16);
		endHour_spinner.setBounds(100,180,50,16);
		endMinute_spinner.setBounds(100,200,50,16);
		
		Color color = new Color(255,255,255);
		startYear.setForeground(color);
		startMonth.setForeground(color);
		startDay.setForeground(color);
		startHour.setForeground(color);
		startMinute.setForeground(color);
		endYear.setForeground(color);
		endMonth.setForeground(color);
		endDay.setForeground(color);
		endHour.setForeground(color);
		endMinute.setForeground(color);
		
		this.add(startYear);
		this.add(startMonth);
		this.add(startDay);
		this.add(startHour);
		this.add(startMinute);
		this.add(endYear);
		this.add(endMonth);
		this.add(endDay);
		this.add(endHour);
		this.add(endMinute);
		
		this.add(startYear_spinner);
		this.add(startMonth_spinner);
		this.add(startDay_spinner);
		this.add(startHour_spinner);
		this.add(startMinute_spinner);
		this.add(endYear_spinner);
		this.add(endMonth_spinner);
		this.add(endDay_spinner);
		this.add(endHour_spinner);
		this.add(endMinute_spinner);
	}
	/**
	 * Used to satisfy the ActionListener interface
	 */
	public void actionPerformed(ActionEvent p)
	{
		
	}
	
}