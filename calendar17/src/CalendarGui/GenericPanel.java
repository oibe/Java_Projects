package CalendarGui;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public abstract class GenericPanel extends JPanel implements ActionListener
{
	/**
	 * used as an abstract class for AppointmentGenericPanel, initializes values common to all subclasses.
	 */
	public GenericPanel()
	{
		this.setLayout(null);
		this.setBounds(590,140,200,425);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}
