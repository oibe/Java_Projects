package CalendarGui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

public class themeEditor extends JPanel implements ChangeListener 
{

    protected JColorChooser tcc;
    protected JLabel banner;
    
    public themeEditor() {
        super(new BorderLayout());

        banner = new JLabel("test");
        banner.setPreferredSize(new Dimension(100, 65));
        tcc = new JColorChooser(getForeground());
        tcc.getSelectionModel().addChangeListener(this);
        tcc.setBorder(BorderFactory.createTitledBorder(
                                             "Choose Text Color"));
    
        add(banner, BorderLayout.WEST);
        add(tcc, BorderLayout.EAST);

        //Set up the banner at the top of the window

    }
    
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("KAlendar Theme Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new themeEditor();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Color newColor = tcc.getColor();
		
	}

	
}
