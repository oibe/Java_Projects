package CalendarGui;
import javax.swing.*;

import java.awt.*;

public class DayPanel extends JPanel
{
	JLabel Sunday = new JLabel("Su");
	JLabel Monday = new JLabel("Mo");
	JLabel Tuesday = new JLabel("Tu");
	JLabel Wednesday = new JLabel("We");
	JLabel Thursday = new JLabel("Th");
	JLabel Friday = new JLabel("Fr");
	JLabel Saturday = new JLabel("Sa");
	
	public DayPanel()
	{
		this.setLayout(null);
		this.setBounds(CalendarFrame.left+50,65,490,30);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		Font font = Sunday.getFont();
		
		Color black = new Color(0,0,0);
		Sunday.setBorder(BorderFactory.createLineBorder(Color.white));
		Sunday.setBounds(0,0,70,30);
		this.setBackground(black);
		Sunday.setForeground(new Color(255,255,255));
		Sunday.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(Sunday);
		Sunday.setFont(new Font(font.getFontName(),font.getStyle(),18));
		
		Monday.setForeground(new Color(255,255,255));
		Monday.setBorder(BorderFactory.createLineBorder(Color.white));
		Monday.setBounds(70,0,70,30);
		Monday.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(Monday);
		Monday.setFont(new Font(font.getFontName(),font.getStyle(),18));
		
		Tuesday.setForeground(new Color(255,255,255));
		Tuesday.setBorder(BorderFactory.createLineBorder(Color.white));
		Tuesday.setBounds(140,0,70,30);
		Tuesday.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(Tuesday);
		Tuesday.setFont(new Font(font.getFontName(),font.getStyle(),18));
		
		Wednesday.setForeground(new Color(255,255,255));
		Wednesday.setBorder(BorderFactory.createLineBorder(Color.white));
		Wednesday.setBounds(210,0,70,30);
		Wednesday.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(Wednesday);
		Wednesday.setFont(new Font(font.getFontName(),font.getStyle(),18));
		
		Thursday.setForeground(new Color(255,255,255));
		Thursday.setBorder(BorderFactory.createLineBorder(Color.white));
		Thursday.setBounds(280,0,70,30);
		Thursday.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(Thursday);
		Thursday.setFont(new Font(font.getFontName(),font.getStyle(),18));
		
		Friday.setForeground(new Color(255,255,255));
		Friday.setBorder(BorderFactory.createLineBorder(Color.white));
		Friday.setBounds(350,0,70,30);
		Friday.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(Friday);
		Friday.setFont(new Font(font.getFontName(),font.getStyle(),18));
		
		Saturday.setForeground(new Color(255,255,255));
		Saturday.setBorder(BorderFactory.createLineBorder(Color.white));
		Saturday.setBounds(420,0,70,30);
		Saturday.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(Saturday);
		Saturday.setFont(new Font(font.getFontName(),font.getStyle(),18));
		
		
	}
}