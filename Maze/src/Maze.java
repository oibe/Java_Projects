import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Maze extends JFrame 
{
	/* CONSTANTS (NO MAGIC NUMBERS) */
	private static final long serialVersionUID = 1L;
	final static int FRAMEDIMENSIONS = 600;
	final static int MAZEDIMENSIONS = 500;
	final static int NUMBEROFCELLS = 70;
	final static int MAZEOFFSET = 30;
	
	/**
	 * East is the integer that stands for the eastern direction.
	 */
	final static int EAST = 0;
	/**
	 * North is the integer that stands for the northern direction.
	 */
	final static int NORTH = 1;
	/**
	 * South is the integer that stands for the southern direction.
	 */
	final static int SOUTH = 2;
	/**
	 * West is the integer that stands for the western direction.
	 */
	final static int WEST = 3;
	final static int ERROR = 4;
	final static int SLEEPTIME = 5;
	final static int FIXEDSEED = 666;
	final static double CELLWIDTH = MAZEDIMENSIONS / NUMBEROFCELLS;
	final static int COMPONENTOFFSET = 20;
	Random rgen = new Random(FIXEDSEED);
	JButton solve = null;
	static Character c = null;
	/* PATH GRAPHICS */
	final static float PATHLENGTH = .5f;
	final static float PATHWIDTH = .1f;
	final static float LENGTHFIX = (float) (.1 * CELLWIDTH);
	Cell prevRectCell = null;
	Cell currentRectCell = null;

	/* OTHER */
	private Cell[][] mazeRepresentation = new Cell[NUMBEROFCELLS][NUMBEROFCELLS];
	private static JComponent jc = null;

	
	private void setUnvisited()
	{
		for (int i = 0; i < NUMBEROFCELLS; i++)
		{
			for (int j = 0; j < NUMBEROFCELLS; j++)
			{
				mazeRepresentation[i][j].visited = false;
			}
		}
	}

	private void setRectangles(int x, int y, int dir)
	{
		if (currentRectCell != null && prevRectCell != null)
		{
			prevRectCell.initializeRectangles();
			currentRectCell.initializeRectangles();
			if (dir == NORTH || dir == SOUTH)
			{	
				if (dir == NORTH)
				{
					prevRectCell.setTopDrawable();
					currentRectCell.setBotDrawable();
				} 
				else
				{
					prevRectCell.setBotDrawable();
					currentRectCell.setTopDrawable();
				}
			} 
			else if (dir == WEST || dir == EAST)
			{
				if (dir == WEST)
				{
					prevRectCell.setLeftHorizDrawable();
					currentRectCell.setRightHorizDrawable();
				} 
				else
				{
					prevRectCell.setRightHorizDrawable();
					currentRectCell.setLeftHorizDrawable();
				}
			} 
			else
			{
				System.err.println("error in maze generation method");
			}
		}
	}

	private void generateMaze()
	{
		generateMazeHelper(0, 0);
		jc.repaint(0);
	}

	private void generateMazeHelper(int x, int y)
	{
		Cell startingCell = mazeRepresentation[x][y];
		startingCell.setVisited();
		prevRectCell = startingCell;
		ArrayList<Cell> neighbors = findNeighbors(x, y);
		while (neighbors != null && neighbors.size() > 0)
		{
			int randomNum = rgen.nextInt(neighbors.size());
			Cell chosen = neighbors.get(randomNum);
			if (chosen.getVisited() == false)
			{
				removeWall(startingCell, chosen);
				try
				{
					Thread.sleep(SLEEPTIME);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			neighbors.remove(randomNum);
			if (chosen.getVisited() == false)
			{
				currentRectCell = chosen;
				// ///////////////////////
				// setRectangles(x,y);
				// ///////////////////////
				generateMazeHelper(chosen.x, chosen.y);
				prevRectCell = startingCell;
				// prevRectCell.col = Color.gray;
			}
		}
	}

	private void removeWall(Cell from, Cell to)
	{
		int dir = direction(from, to);
		if (dir == ERROR)
		{
			System.err.println("removeWall error");
		}
		int a = from.x;
		int b = from.y;
		if(dir == EAST)
		{
			removeEastWall(a, b);
		}
		if (dir == WEST)
		{
			removeWestWall(a, b);
		}
		if (dir == NORTH)
		{
			removeNorthWall(a, b);
		}
		if (dir == SOUTH)
		{
			removeSouthWall(a, b);
		}
	}

	private ArrayList<Cell> findNeighbors(int x, int y)
	{
		if (x < 0 || x > NUMBEROFCELLS - 1)
		{
			return null;
		}
		if (y < 0 || y > NUMBEROFCELLS - 1)
		{
			return null;
		}

		ArrayList<Cell> list = new ArrayList<Cell>();
		int a = x - 1;
		int b = y;
		if (a >= 0 && mazeRepresentation[a][b].getVisited() == false)
		{
			list.add(mazeRepresentation[a][b]);
		}
		a = x + 1;
		b = y;
		if (a < NUMBEROFCELLS && mazeRepresentation[a][b].getVisited() == false)
		{
			list.add(mazeRepresentation[a][b]);
		}

		a = x;
		b = y + 1;
		if (b < NUMBEROFCELLS && mazeRepresentation[a][b].getVisited() == false)
		{
			list.add(mazeRepresentation[a][b]);
		}
		a = x;
		b = y - 1;
		if (b >= 0 && mazeRepresentation[a][b].getVisited() == false)
		{
			list.add(mazeRepresentation[a][b]);
		}
		return list;
	}

	private int direction(Cell from, Cell to)
	{
		int x = to.x - from.x;
		int y = to.y - from.y;
		if (x == 0 && y == 0)
		{
			System.err
					.println("The cells for the direction method are the same");
		}
		if (x > 0)
		{
			return EAST;
		} else if (x < 0)
		{
			return WEST;
		} else if (y < 0)
		{
			return NORTH;
		} else if (y > 0)
		{
			return SOUTH;
		} else
		{
			System.err.println("Something is wrong with the direction method");
			return ERROR;
		}
	}

	private void removeEastWall(int x, int y)
	{
		if (x < 0 || y < 0)
		{
			return;
		}
		if (x > NUMBEROFCELLS - 1 || y > NUMBEROFCELLS - 1)
		{
			return;
		}
		mazeRepresentation[x][y].setEast(null);
		if (x != NUMBEROFCELLS - 1)
		{
			mazeRepresentation[x + 1][y].setWest(null);
		}
		this.reDrawMaze();
	}

	private void removeSouthWall(int x, int y)
	{
		if (x < 0 || y < 0)
		{
			return;
		}
		if (x > NUMBEROFCELLS - 1 || y > NUMBEROFCELLS - 1)
		{
			return;
		}
		mazeRepresentation[x][y].setSouth(null);
		if (y != NUMBEROFCELLS - 1)
		{
			mazeRepresentation[x][y + 1].setNorth(null);
		}
		this.reDrawMaze();
	}

	private void removeWestWall(int x, int y)
	{
		if (x < 0 || y < 0)
		{
			return;
		}
		if (x > NUMBEROFCELLS - 1 || y > NUMBEROFCELLS - 1)
		{
			return;
		}
		mazeRepresentation[x][y].setWest(null);
		if (x > 0)
		{
			mazeRepresentation[x - 1][y].setEast(null);
		}
		this.reDrawMaze();
	}

	private void removeNorthWall(int x, int y)
	{
		if (x < 0 || y < 0)
		{
			return;
		}
		if (x > NUMBEROFCELLS - 1 || y > NUMBEROFCELLS - 1)
		{
			return;
		}
		mazeRepresentation[x][y].setNorth(null);
		if (y > 0)
		{
			mazeRepresentation[x][y - 1].setSouth(null);
		}
		this.reDrawMaze();
	}

	private void reDrawMaze()
	{
		jc.repaint(0);
	}

	private void initializeLines()
	{
		for (int i = 0; i < NUMBEROFCELLS; i++)
		{
			for (int j = 0; j < NUMBEROFCELLS; j++)
			{
				mazeRepresentation[i][j] = new Cell((byte)i, (byte)j);
				Cell c = mazeRepresentation[i][j];
				if (j != 0)
				{
					c.setNorth(mazeRepresentation[i][j - 1].getSouth());
				} else
				{
					c.setNorth(new Line2D.Double(i * CELLWIDTH, j * CELLWIDTH,
							(i + 1) * CELLWIDTH, j * CELLWIDTH));
				}
				if (i != 0)
				{
					c.setWest(mazeRepresentation[i - 1][j].getEast());
				} else
				{
					c.setWest(new Line2D.Double(i * CELLWIDTH, j * CELLWIDTH, i
							* CELLWIDTH, (j + 1) * CELLWIDTH));
				}
				c.setEast(new Line2D.Double((i + 1) * CELLWIDTH, j * CELLWIDTH,
						(i + 1) * CELLWIDTH, (j + 1) * CELLWIDTH));
				c.setSouth(new Line2D.Double(i * CELLWIDTH,
						(j + 1) * CELLWIDTH, (i + 1) * CELLWIDTH, (j + 1)
								* CELLWIDTH));

			}
		}
	}
	
	private void resetMaze()
	{
		for(int i = 0; i < NUMBEROFCELLS;i++)
		{
			for(int j = 0; j < NUMBEROFCELLS;j++)
			{
				Cell c = mazeRepresentation[i][j];
				c.values.clear();
				c.visited = false;
			}
		}
		jc.repaint(0);
	}
	private void addPaintSurface()
	{
		jc = new PaintSurface();
		jc.setBounds(MAZEOFFSET, MAZEOFFSET, MAZEDIMENSIONS + 1,
				MAZEDIMENSIONS + 1);
		this.add(jc);
	}
	
	/**
	 * This method is responsible for moving a character east in the maze,and the 
	 * graphical representation of the maze.
	 */
	public void moveEast(){ c.moveEast(); }
	/**
	 * This method is responsible for moving a character south in the maze,and the 
	 * graphical representation of the maze.
	 */
	public void moveSouth(){ c.moveSouth(); }
	/**
	 * This method is responsible for moving a character north in the maze,and the 
	 * graphical representation of the maze.
	 */
	public void moveNorth(){ c.moveNorth(); }
	/**
	 * This method is responsible for moving a character west in the maze,and the 
	 * graphical representation of the maze.
	 */
	public void moveWest(){ c.moveWest(); }
	
	/**
	 * This method is used to get the current direction, the maze character is facing.
	 * @return Returns type int
	 */
	public int getDirection(){ return c.direction; }
	/**
	 * This method is used to set the current direction, the maze character is facing.
	 * @param dir
	 */
	public void setDirection(int dir){ c.direction = dir; }
	
	
	/**
	 * Checks to see if the maze character is currently facing a wall
	 * @param direction
	 * @return
	 */
	public boolean wallPresent(int direction){ return c.wallPresent(direction); }
	
	/**
	 * Returns true while we should continue to look for the goal, and false otherwise.
	 * @return
	 */
	public boolean goalIsNotReached(){ 
		try
		{
			Thread.sleep(SLEEPTIME);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		if(c.x != NUMBEROFCELLS -1 || c.y != NUMBEROFCELLS -1){
			return true;
		}
		return false;
		}
	public Maze()
	{
		this.setBounds(100, 100, FRAMEDIMENSIONS, FRAMEDIMENSIONS);
		this.setTitle("Maze");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		initializeLines();
		addPaintSurface();
		
		this.validate();
		this.setResizable(false);
		this.setVisible(true);
		generateMaze();
		c = new Character(mazeRepresentation);
		/*JPanel options = new JPanel();
		options.setLayout(null);
		options.setBounds(MAZEOFFSET+MAZEDIMENSIONS, MAZEOFFSET, MAZEDIMENSIONS/2, MAZEDIMENSIONS);
		options.setBorder(BorderFactory.createLineBorder(Color.black));
		JCheckBox animate = new JCheckBox("Animation");
		solve = new JButton("Solve");
		solve.setBounds((MAZEDIMENSIONS/4)-45,MAZEDIMENSIONS-(MAZEDIMENSIONS/10),90,30);
		solve.addActionListener(this);
		animate.setBounds(COMPONENTOFFSET,COMPONENTOFFSET,100,30);
		options.add(solve);
		options.add(animate);
		this.add(options);*/
	}

	/***************************
	 **THE PAINT SURFACE CLASS** 
	 ***************************/
	
	private class PaintSurface extends JComponent
	{
		private static final long serialVersionUID = 1L;

		private void myDraw(Graphics2D g2, Shape s)
		{
			if (s == null)
			{
				return;
			}
			g2.draw(s);
		}

		private void drawPath(Graphics2D g2, Cell c, boolean cArg)
		{
			if (prevRectCell != null && currentRectCell != null)
			{
				Color col = null;
				if (c.getBotDrawable() && (c.getBotVertColor() == cArg))
				{
					col = (c.getBotVertColor() == false) ? Color.red: Color.gray;
					g2.setPaint(col);
					g2.fill(c.botVertRect);
					myDraw(g2, c.botVertRect);
				}
				if (c.getTopDrawable() && (c.getTopVertColor() == cArg))
				{
					col = (c.getTopVertColor() == false) ? Color.red: Color.gray;
					g2.setPaint(col);
					g2.fill(c.topVertRect);
					myDraw(g2, c.topVertRect);
				}
				if (c.getLeftHorizDrawable() && (c.getLeftHorizColor() == cArg))
				{
					col = (c.getLeftHorizColor() == false) ? Color.red: Color.gray;
					g2.setPaint(col);
					g2.fill(c.leftHorizRect);
					myDraw(g2, c.leftHorizRect);
				}
				if (c.getRightHorizDrawable() && (c.getRightHorizColor() == cArg))
				{
					col = (c.getRightHorizColor() == false) ? Color.red: Color.gray;
					g2.setPaint(col);
					g2.fill(c.rightHorizRect);
					myDraw(g2, c.rightHorizRect);
				}

			}
			g2.setPaint(Color.black);
		}

		public void paint(Graphics g)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(Color.black);
			Cell c = null;
			for (int i = 0; i < NUMBEROFCELLS; i++)
			{
				for (int j = 0; j < NUMBEROFCELLS; j++)
				{
					c = mazeRepresentation[i][j];
					myDraw(g2, c.getEast());
					myDraw(g2, c.getWest());
					myDraw(g2, c.getNorth());
					myDraw(g2, c.getSouth());
					// TODO warp
					drawPath(g2, c, true);//true will color gray
					drawPath(g2, c, false);// false will color red
				}

			}
		}
	}

	private class Character
	{
		int x;
		int y;
		Cell[][] maze = null;
		int direction = SOUTH;

		private Character(Cell[][] maze)
		{
			x = 0;
			y = 0;
			setUnvisited();
			this.maze = maze;
		}

		private void traversal()
		{
			setUnvisited();
			traversalHelper(0, 0, NUMBEROFCELLS - 1, NUMBEROFCELLS - 1);
			jc.repaint(0);
		}

		private boolean traversalHelper(int x, int y, int goalx, int goaly)
		{
			if (x == goalx && y == goaly)
			{
				return true;
			}
			Cell startingCell = maze[x][y];

			startingCell.setVisited();
			prevRectCell = startingCell;
			Cell prev = null;
			Cell curr = null;
			int dir = -77;
			ArrayList<Integer> neighbors = findCells(startingCell);
			for (int i = 0; i < neighbors.size(); i++)
			{
				int direction = neighbors.get(i);
				Cell chosen = getNextCell(startingCell, direction);
				if (chosen.getVisited() == false)
				{
					//prevRectCell.col = Color.red;
					if (chosen.getVisited() == false)
					{
						currentRectCell = chosen;
						// ///////////////////////
						dir = direction(prevRectCell, currentRectCell);
						setRectangles(x, y, dir);
						// ///////////////////////
						jc.repaint(0);
						try
						{
							Thread.sleep(SLEEPTIME);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}

						if (traversalHelper(chosen.x, chosen.y, goalx, goaly))
						{
							return true;
						} else
						{
							prevRectCell = startingCell;
							prev = prevRectCell;
							curr = chosen;
							/*************************************************************************/
							if (prev != null && curr != null)
							{
								if (dir == NORTH || dir == SOUTH)
								{
									if (dir == NORTH)
									{
										prev.setTopVertRectGray();
										curr.setBotVertRectGray();
									} else
									{
										prev.setBotVertRectGray();
										curr.setTopVertRectGray();
									}
								} else if (dir == WEST || dir == EAST)
								{
									if (dir == WEST)
									{
										prev.setLeftHorizRectGray();
										curr.setRightHorizRectGray();
									} else
									{
										prev.setRightHorizRectGray();
										curr.setLeftHorizRectGray();
									}
								} else
								{
									System.err.println("prev " + prev.x + " , "
											+ prev.y + " curr ");
								}
							}
							/*************************************************************************/
						}
					}
				}
			}
			// prevRectCell.col = Color.gray;
			jc.repaint(0);
			try
			{
				Thread.sleep(SLEEPTIME);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			return false;
		}

		

		private void moveEast()
		{
			if (x < NUMBEROFCELLS - 1 && direction == EAST)
			{
				prevRectCell = mazeRepresentation[x][y];
				prevRectCell.setVisited();
				currentRectCell = mazeRepresentation[x + 1][y];
				x++;
				setRectangles(x, y, EAST);
			}
		}

		private void moveWest()
		{
			
			if (x > 0 && direction == WEST)
			{
				prevRectCell = mazeRepresentation[x][y];
				prevRectCell.setVisited();
				currentRectCell = mazeRepresentation[x - 1][y];
				x--;
				setRectangles(x, y, WEST);
				jc.repaint(0);
			}
		}

		private void moveSouth()
		{
			if (y < NUMBEROFCELLS - 1 && direction == SOUTH)
			{
				prevRectCell = mazeRepresentation[x][y];
				prevRectCell.setVisited();
				currentRectCell = mazeRepresentation[x][y + 1];
				y++;
				setRectangles(x, y, SOUTH);
				jc.repaint(0);
			}
		}

		private void moveNorth()
		{
			if (y > 0 && direction == NORTH)
			{
				prevRectCell = mazeRepresentation[x][y];
				prevRectCell.setVisited();
				currentRectCell = mazeRepresentation[x][y - 1];
				y--;
				setRectangles(x, y, NORTH);
				jc.repaint(0);
			}
		}

		private boolean wallPresent(int arg)
		{
			if (arg == EAST)
			{
				if (mazeRepresentation[x][y].east == null)
				{
					return false;
				}
				return true;
			} else if (arg == WEST)
			{
				if (mazeRepresentation[x][y].west == null)
				{
					return false;
				}
				return true;
			} else if (arg == NORTH)
			{
				if (mazeRepresentation[x][y].north == null)
				{
					return false;
				}
				return true;
			} else
			{
				if (mazeRepresentation[x][y].south == null)
				{
					return false;
				}
				return true;
			}
		}

		private Cell getNextCell(Cell from, int direction)
		{
			int x = from.x;
			int y = from.y;
			if (direction == EAST)
			{
				return maze[x + 1][y];
			}
			if (direction == WEST)
			{
				return maze[x - 1][y];
			}
			if (direction == NORTH)
			{
				return maze[x][y - 1];
			}
			if (direction == SOUTH)
			{
				return maze[x][y + 1];
			}
			return null;
		}

		private ArrayList<Integer> findCells(Cell currentCell)
		{
			if (currentCell == null)
			{
				return null;
			}
			ArrayList<Integer> list = new ArrayList<Integer>();
			if (currentCell.north == null)
			{
				list.add(NORTH);
			}
			if (currentCell.east == null)
			{
				list.add(EAST);
			}
			if (currentCell.west == null)
			{
				list.add(WEST);
			}
			if (currentCell.south == null)
			{
				list.add(SOUTH);
			}
			return list;
		}
	}

	private class Cell
	{
		byte x;
		byte y;
		private Line2D.Double north = null;
		private Line2D.Double south = null;
		private Line2D.Double east = null;
		private Line2D.Double west = null;
		Shape botVertRect = null;
		Shape topVertRect = null;
		Shape leftHorizRect = null;
		Shape rightHorizRect = null;
		
		BitSet values =null;
		
		

		boolean visited = false;

		private Cell(byte x, byte y)
		{
			this.x = x;
			this.y = y;
			values = new BitSet(8);
		}

		private void initializeRectangles()
		{
			topVertRect = new Rectangle2D.Double((x * CELLWIDTH)
					+ ((CELLWIDTH / 2) - (CELLWIDTH * PATHWIDTH)), y
					* CELLWIDTH, CELLWIDTH * PATHWIDTH, PATHLENGTH * CELLWIDTH);
			botVertRect = new Rectangle2D.Double((x * CELLWIDTH)
					+ ((CELLWIDTH / 2) - (CELLWIDTH * PATHWIDTH)), y
					* CELLWIDTH + (CELLWIDTH / 2) - LENGTHFIX, CELLWIDTH
					* PATHWIDTH, PATHLENGTH * CELLWIDTH + LENGTHFIX);
			leftHorizRect = new Rectangle2D.Double(x * CELLWIDTH, y * CELLWIDTH
					+ ((CELLWIDTH / 2) - (CELLWIDTH * PATHWIDTH)), PATHLENGTH
					* CELLWIDTH, CELLWIDTH * PATHWIDTH);
			rightHorizRect = new Rectangle2D.Double(x * CELLWIDTH
					+ (CELLWIDTH / 2), y * CELLWIDTH
					+ ((CELLWIDTH / 2) - (CELLWIDTH * PATHWIDTH)), PATHLENGTH
					* CELLWIDTH, CELLWIDTH * PATHWIDTH);
		}
		
		private Line2D.Double getNorth(){ return north; }
		private Line2D.Double getSouth(){ return south; }
		private Line2D.Double getEast(){ return east; }
		private Line2D.Double getWest(){ return west; }
		private boolean getVisited(){ return visited; }
		private boolean getBotVertColor(){ return values.get(0); }
		private boolean getTopVertColor(){ return values.get(1); }
		private boolean getLeftHorizColor(){ return values.get(2); }
		private boolean getRightHorizColor(){ return values.get(3); }
		private boolean getBotDrawable(){ return values.get(4); }
		private boolean getTopDrawable(){ return values.get(5); }
		private boolean getLeftHorizDrawable(){ return values.get(6); }
		private boolean getRightHorizDrawable(){ return values.get(7); }
		
		private void setNorth(Line2D.Double wall){ north = wall; }
		private void setSouth(Line2D.Double wall){ south = wall; }
		private void setEast(Line2D.Double wall){ east = wall; }
		private void setWest(Line2D.Double wall){ west = wall; }
		private void setVisited(){ visited = true; }
		private void setBotVertRectGray(){ values.flip(0); }
		private void setTopVertRectGray(){ values.flip(1); }
		private void setLeftHorizRectGray(){ values.flip(2); }
		private void setRightHorizRectGray(){ values.flip(3); }
		private void setBotDrawable(){ values.set(4); }
		private void setTopDrawable(){ values.set(5); }
		private void setLeftHorizDrawable(){ values.set(6); }
		private void setRightHorizDrawable(){ values.set(7); }
	}


}
