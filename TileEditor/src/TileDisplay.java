import static java.lang.Math.floor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import LowLevel.Chunk;
import LowLevel.TileMap;

public class TileDisplay extends JScrollPane implements MouseListener
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
	ImagePanel leftClick;
	ImagePanel rightClick;
	public TileDisplay(ImagePanel leftClick, ImagePanel rightClick) 
	{
		super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.leftClick = leftClick;
		this.rightClick = rightClick;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		AdjustmentListener listener = new MyAdjustmentListener(this);
		this.getHorizontalScrollBar().addAdjustmentListener(listener);
		this.getVerticalScrollBar().addAdjustmentListener(listener);
		map = new TileMap(1,1);//number of chunks
		selectedTile = new Point(0,0);
		TileDisplayPanel display = new TileDisplayPanel(this);
		
		Dimension chunkSize = map.getChunk(0, 0).getSizeByTiles();
		mapDim = new Dimension(((int)(tileWidth*chunkSize.getWidth())),(int)(tileHeight*chunkSize.getHeight()));
		display.setPreferredSize(mapDim);
		this.setViewportView(display);
		display.addMouseListener(this);
		
		
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
	

	
	 class TileDisplayPanel extends ImagePanel 
	{

		private static final long serialVersionUID = 1L;
		TileDisplay display = null;
	  
	  public TileDisplayPanel(TileDisplay disp)
	  {
		  display = disp;
	  }
	  
	  public void paintComponent(Graphics g) {
		  
		
		  Graphics2D g2 = (Graphics2D)g;
		 
		  Chunk ck = map.getChunk(0, 0);
		  Dimension tileDim = ck.getSizeByTiles();
		 // Rectangle2D.Double[][] a = new Rectangle2D.Double[dim][dim];
			int row = 0;
			int col = 0;

			Rectangle2D.Double rect =new Rectangle2D.Double(row*tileWidth, col*tileHeight,tileWidth,tileHeight);
			Rectangle view = new Rectangle(
					(int)((double)display.getHorizontalScrollBar().getValue() / display.getHorizontalScrollBar().getMaximum() * mapDim.width),
					(int)((double)display.getVerticalScrollBar().getValue() / display.getVerticalScrollBar().getMaximum() * mapDim.height), 
					display.getWidth(), 
					display.getHeight());
			
		  for(row =0; row < tileDim.height; row++)
		  {
			for(col = 0; col < tileDim.width;col++)
			{
				rect.setFrame(col*tileWidth, row*tileHeight,tileWidth,tileHeight);

				if(rect.x +rect.width < view.x || rect.x > view.x +view.width || rect.y+rect.height < view.y || rect.y > view.y+view.height)
				{
				}
				else
				{
				g2.setColor(tileGridColor);
				g.drawImage(ck.getTile(col, row), col*tileWidth, row*tileHeight,tileWidth,tileHeight, null);
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
