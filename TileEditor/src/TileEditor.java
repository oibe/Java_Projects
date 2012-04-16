import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

public class TileEditor extends JFrame implements ComponentListener
{
	
	/********************************
	 * 		COMPONENT DIMENSIONS	*
	 ********************************/

	private Toolkit tk = Toolkit.getDefaultToolkit();
	private Dimension screenDimensions = tk.getScreenSize();
	private Dimension frameDimensions = new Dimension(screenDimensions.width*2/3,screenDimensions.height*4/5);
	private Rectangle textureDisplayDimensions = new Rectangle(frameDimensions.width/80,frameDimensions.height/20,
															   frameDimensions.width*2/3,frameDimensions.height*4/6);
	private int paddingWidth = frameDimensions.width/60;
	private Rectangle texturePaletteDimensions = new Rectangle(textureDisplayDimensions.x+textureDisplayDimensions.width+paddingWidth,
															   textureDisplayDimensions.y+(textureDisplayDimensions.height/2),
															   frameDimensions.width*2/7,
															   textureDisplayDimensions.height/2);
	Rectangle leftClickTileDimensions = new Rectangle(texturePaletteDimensions.x,textureDisplayDimensions.y,32,32);
	Rectangle rightClickTileDimensions = new Rectangle(texturePaletteDimensions.x+leftClickTileDimensions.width+paddingWidth,
													   textureDisplayDimensions.y,32,32);
	private TileDisplay textureDisplay;
	private Palette texturePalette;
	
	private TileEditor()
	{
		
		ImagePanel leftClickTile = new ImagePanel();
		ImagePanel rightClickTile = new ImagePanel();
		textureDisplay = new TileDisplay(leftClickTile,rightClickTile);
		textureDisplay.setBounds(textureDisplayDimensions);
		texturePalette = new Palette();
		texturePalette.setBounds(texturePaletteDimensions);
		
		
		leftClickTile.setBounds(leftClickTileDimensions);
		rightClickTile.setBounds(rightClickTileDimensions);
		
		this.add(leftClickTile);
		this.add(rightClickTile);
		this.add(textureDisplay);
		this.add(texturePalette);
		this.setBounds(screenDimensions.width/6,screenDimensions.height/6,frameDimensions.width	,frameDimensions.height);
		this.setResizable(false);
		this.setLayout(null);
		this.addComponentListener(this);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	/***********************************
	 * 		COMPONENT LISTENERS		   *
	 ***********************************/
	public void componentHidden(ComponentEvent arg0){}
	public void componentMoved(ComponentEvent arg0){}
	public void componentShown(ComponentEvent arg0){}	
	
	public void componentResized(ComponentEvent arg0)
	{
		//init();
	}
	
	public static void main(String[] args)
	{
		TileEditor editor = new TileEditor();
	
	}
}
