package cs113.calendar.model;

import java.awt.Color;

public class theme {

	String themeDir;
	String bgFileName;
	
	Color currentDay = new Color(255, 255, 255, 255);
	Color normalDay = new Color(255, 255, 255, 255);
	Color selectedDay = new Color(255, 255, 255, 255);
	Color outsideMonthDay = new Color(255, 255, 255, 255);
	
	Color menuBox = new Color(255, 255, 255, 255);
	Color optionsBox = new Color(255, 255, 255, 255);
	Color nameBox = new Color(255, 255, 255, 255);

	Color outsideMonthDayTextColor = new Color(255, 255, 255, 255);
	Color monthDayTextColor = new Color(255, 255, 255, 255);
	Color dayOfWeekColor = new Color(255, 255, 255, 255);
	
	Color menuText = new Color(255, 255, 255, 255);
	Color optionsText = new Color(255, 255, 255, 255);
	Color optionsButtonText = new Color(255, 255, 255, 255);
	Color nameText = new Color(255, 255, 255, 255);
	Color appointmentSelectionText = new Color(255, 255, 255, 255);
	
	Color weekBorder = new Color(255, 255, 255, 255);
	Color outsideMonthBorder = new Color(255, 255, 255, 255);
	
	public theme(String themeName) {themeDir=themeName;}
	
	public void setbgFileName(String name) {bgFileName=name;}
	
	public void setCurrentDay(int a, int b, int c, int d) {currentDay = new Color(a, b, c, d);}
	public void setNormalDay(int a, int b, int c, int d) {normalDay = new Color(a, b, c, d);}
	public void setSelectedDay(int a, int b, int c, int d) {selectedDay = new Color(a, b, c, d);}
	public void setOutsideMonthDay(int a, int b, int c, int d) {outsideMonthDay = new Color(a, b, c, d);}
	
	public void setMenuBox(int a, int b, int c, int d) {menuBox = new Color(a, b, c, d);}
	public void setOptionsBox(int a, int b, int c, int d) {optionsBox = new Color(a, b, c, d);}
	public void setNameBox(int a, int b, int c, int d) {nameBox = new Color(a, b, c, d);}

	public void setOutsideMonthDayTextColor(int a, int b, int c, int d) {outsideMonthDayTextColor = new Color(a, b, c, d);}
	public void setMonthDayTextColor(int a, int b, int c, int d) {monthDayTextColor = new Color(a, b, c, d);}
	public void setDayOfWeekColor(int a, int b, int c, int d) {dayOfWeekColor= new Color(a, b, c, d);}
	
	public void setMenuText(int a, int b, int c, int d) {menuText = new Color(a, b, c, d);}
	public void setOptionsText(int a, int b, int c, int d) {optionsText= new Color(a, b, c, d);}
	public void setOptionsButtonText(int a, int b, int c, int d) {optionsButtonText = new Color(a, b, c, d);}
	public void setNameText(int a, int b, int c, int d) {nameText = new Color(a, b, c, d);}
	public void setAppointmentSelectionText(int a, int b, int c, int d) {appointmentSelectionText = new Color(a, b, c, d);}
	
	
	public String getbgFileName() {return bgFileName;}
	
	public void setWeekBorder(int a, int b, int c, int d) {weekBorder = new Color(a, b, c, d);}
	public void setOutsideMonthBorder(int a, int b, int c, int d) {outsideMonthBorder = new Color(a, b, c, d);}
	
	public Color getCurrentDay() {return currentDay;}
	public Color getNormalDay() {return normalDay;}
	public Color getSelectedDay() {return selectedDay;}
	public Color getOutsideMonthDay() {return currentDay;}
	
	public Color getMenuBox() {return menuBox;}
	public Color getOptionsBox() {return optionsBox;}
	public Color getNameBox() {return nameBox;}
	
	public Color getOutsideMonthDayTextColor() {return outsideMonthDayTextColor;}
	public Color getMonthDayTextColor() {return monthDayTextColor;}
	public Color getDayOfWeekColor() {return dayOfWeekColor;}
	
	public Color getMenuText() {return menuText;}
	public Color getOptionsText() {return optionsText;}
	public Color getOptionsButtonText() {return optionsButtonText;}
	public Color getNameText() {return nameText;}
	public Color getAppointmentSelectionText() {return appointmentSelectionText;}
	
	
	
	
	
	
	
	

	
}
