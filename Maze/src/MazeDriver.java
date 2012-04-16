
public class MazeDriver 
{
	public static void main(String[] args)
	{
		Maze m = new Maze();
		
		while (m.goalIsNotReached()) 
		{
			
			
			if (m.getDirection() == Maze.NORTH) // wall is to our west
			{
				if (m.wallPresent(Maze.NORTH)) // north is blocked
				{
					m.setDirection(Maze.EAST); // turn clockwise, keep wall on left
				} 
				else
				{
					m.moveNorth();
					if (m.wallPresent(Maze.WEST) == false) // wall is no longer on our left
					{
						m.setDirection(Maze.WEST); // turn counterclockwise, keep
					}
				}
			} 
			else if (m.getDirection() == Maze.SOUTH) // wall is to our east
			{
				if (m.wallPresent(Maze.SOUTH)) // south is blocked
				{
					m.setDirection(Maze.WEST); // turn clockwise, keep wall on left
				} else
				{
					m.moveSouth();
					if (m.wallPresent(Maze.EAST) == false) // wall is no longer on
					{
						m.setDirection(Maze.EAST);  // turn counterclockwise, keep
					}
				}
			} 
			else if (m.getDirection() == Maze.EAST) // wall is to our north
			{
				if (m.wallPresent(Maze.EAST)) // east is blocked
				{
					m.setDirection(Maze.SOUTH); // turn clockwise, keep wall on left
				} 
				else
				{
					m.moveEast();
					if (m.wallPresent(Maze.NORTH) == false) // wall is no longer on
					{
						m.setDirection(Maze.NORTH); // turn counterclockwise, keep
					}
				}
			} 
			else
			{
				if (m.wallPresent(Maze.WEST)) // west is blocked
				{
					m.setDirection(Maze.NORTH); // turn clockwise, keep wall on left
				} 
				else
				{
					m.moveWest();
					if (m.wallPresent(Maze.SOUTH) == false) // wall is no longer on
					{
						m.setDirection(Maze.SOUTH); // turn counterclockwise, keep
					}
				}
			}
		}
	}
}
