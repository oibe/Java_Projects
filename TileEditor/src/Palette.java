import static java.lang.Math.floor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import LowLevel.Chunk;
import LowLevel.TileDictionary;
import LowLevel.TileMap;

public class Palette extends JScrollPane implements MouseListener
{

	private static final long serialVersionUID = 1L;
	Color tileGridColor = Color.red;
	Color chunkGridColor = Color.green;
	Color selectColor = Color.yellow;
	TileMap map;
	int tileWidth = 32;
	int tileHeight = 32;
	Dimension mapDim;
	Point selectedTile;
	
	TileDictionary td = new TileDictionary();
	Image[][] paletteImages;
	int rows; 
	int cols;
	public Palette() 
	{
		super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		AdjustmentListener listener = new MyAdjustmentListener(this);
		this.getHorizontalScrollBar().addAdjustmentListener(listener);
		this.getVerticalScrollBar().addAdjustmentListener(listener);
		map = new TileMap(1,1);//number of chunks
		selectedTile = new Point(0,0);
		PaletteDisplayPanel display = new PaletteDisplayPanel(this);
		
		Dimension chunkSize = map.getChunk(0, 0).getSizeByTiles();
		mapDim = new Dimension(((int)(tileWidth*chunkSize.getWidth())),(int)(tileHeight*chunkSize.getHeight()));
		display.setPreferredSize(mapDim);
		this.setViewportView(display);
		display.addMouseListener(this);
		
		Set<String> tdKeys = td.keySet();
		
		Iterator iter = tdKeys.iterator();
		rows =(int)Math.ceil(((double)tdKeys.size())/3.0);
		cols = 3;
		paletteImages = new Image[rows][cols];
		for(int i = 0; i < rows;i++)
		{
			int j = 0;
			while(iter.hasNext() && j < cols)
			{
				String key = (String)iter.next();
				paletteImages[i][j] = td.get(key);
				j++;
			}
		}
		
	}

	/***********************************
	 * 			MOUSE LISTENERS		   *
	 ***********************************/
	public void mouseClicked(MouseEvent e)	{}
	public void mouseEntered(MouseEvent e)	{}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	
	public void mouseReleased(MouseEvent e)
	{
		System.out.println("x = "+e.getX()+" y = "+e.getY());
		selectedTile.setLocation(new Point((int)floor(e.getX()/tileWidth),(int)floor(e.getY()/tileHeight)));
		this.repaint();
	}
	

	
	 class PaletteDisplayPanel extends ImagePanel 
	{

		private static final long serialVersionUID = 1L;
		Palette display = null;
	  
	  public PaletteDisplayPanel(Palette disp)
	  {
		  display = disp;
	  }
	  
	  public void paintComponent(Graphics g) {
		  
		
		  Graphics2D g2 = (Graphics2D)g;
		 
		  Chunk ck = map.getChunk(0, 0);
		  Dimension tileDim = ck.getSizeByTiles();
		 // Rectangle2D.Double[][] a = new Rectangle2D.Double[dim][dim];
			int i = 0;
			int j = 0;

			Rectangle2D.Double rect =new Rectangle2D.Double(i*tileWidth, j*tileHeight,tileWidth,tileHeight);
			Rectangle view = new Rectangle(
					(int)((double)display.getHorizontalScrollBar().getValue() / display.getHorizontalScrollBar().getMaximum() * mapDim.width),
					(int)((double)display.getVerticalScrollBar().getValue() / display.getVerticalScrollBar().getMaximum() * mapDim.height), 
					display.getWidth(), 
					display.getHeight());
			
		  for(i =0; i < rows; i++)
		  {
			for(j = 0; j < cols;j++)
			{
				rect.setFrame(j*tileWidth, i*tileHeight,tileWidth,tileHeight);
				
				if(rect.x +rect.width < view.x || rect.x > view.x +view.width || rect.y+rect.height < view.y || rect.y > view.y+view.height)
				{
				}
				else
				{
				g2.setColor(tileGridColor);
				g.drawImage(paletteImages[i][j], j*tileWidth, i*tileHeight,tileWidth,tileHeight, null);
				g2.draw(rect);
				if(selectedTile != null)
				{
					g2.setColor(selectColor);
					g2.draw(new Rectangle((int)selectedTile.getX()*tileWidth,(int)selectedTile.getY()*tileHeight,tileWidth,tileHeight));
				}
				}
			}
		  }
	  }
	}
}
