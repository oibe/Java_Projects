import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Scanner;


public class Sudoku
{

	private BitSet board;
	public Sudoku(String initial)
	{
		board = new BitSet(891);
		for(int i = 0; i < 9;i++)
		{
			for(int j = 0; j < 9; j++)
			{
				char val = initial.charAt(i*9+j);
				setIndex(i,j, (val == 'X')? 0 : (val - '0'),false);
			}
		}
		for(int k = 0; k < 9;k++)
		{
			setPossibleRowVals(k);
			setPossibleColVals(k);
			setPossibleGridVals(k);
		}
	}
	

	
	private void printBoard()
	{

		for(int i = 0; i < 9;i++)
		{
			if(i == 0)
				System.out.print("------------------------------"+"\n");

			for(int j = 0; j < 9; j++)
			{
				if(j == 0)
					System.out.print("| ");
				int bval = getIndex(i,j);
				
				if(bval == 0)
					System.out.print(" ");
				else
					System.out.print(bval);
					
				if((j+1) == 9)
					System.out.print("|");
				else if((j+1)%3 == 0)
					System.out.print(" | ");
				else
					System.out.print("  ");
			}
			
			if((i+1)%3 == 0)
				System.out.print("\n------------------------------");
			System.out.println();
		}
		System.out.println();
	}
	
	private void setIndex(int x, int y,int value,boolean undo)
	{
		int initial = value;
		int linearCellNumber = (int)(x*9+y);
		for(int i = 0; i < 4;i++)
			board.set(linearCellNumber*4+i,false);
		if(!undo)
			for(int j = 0; j < 4;j++,value>>=1)
				if((value & 0x1)== 1)
					board.set(linearCellNumber*4+j,true);
		
			
		board.set((324+(9*x)+(initial-1)),undo);
		board.set((405+(9*y)+(initial-1)),undo);
		board.set((486+((9*((x/3)*3+(y/3))+(initial-1)))),undo);
	}
	
	private int getIndex(int x, int y)
	{
		int linearCellNumber = (int)(x*9+y);
		int value = 0;
		for(int j = 0; j < 4;j++)
			if(board.get(linearCellNumber*4+j) == true)
				value |= (1 << j);
		return value;
	}
	
	private void setPossibleRowVals(int row)
	{
		boolean[] table = new boolean[9];
		for(int i = 0; i < 9;i++)
		{
			int val = getIndex(row,i);
			if(val != 0)
				table[val-1] = true;
		}
		for(int j = 0; j < 9;j++)
			if(table[j] == false)
				board.set(324+((9*row)+j),true);
	}
	
	private void setPossibleColVals(int col)
	{
		boolean[] table = new boolean[9];
		for(int i = 0; i < 9;i++)
		{
			int val = getIndex(i,col);
			if(val != 0)
				table[val-1] = true;
		}
		for(int j = 0; j < 9;j++)
			if(table[j] == false)
				board.set(405+((9*col)+j),true);
		
	}
	
	private void setPossibleGridVals(int gridNum)
	{
		int x = gridNum/3;
		int y = gridNum%3;
		boolean[] table = new boolean[9];
		for(int row = 0; row < 3; row++)
		{
			for(int col = 0; col < 3;col++)
			{
				int val = getIndex(row+x*3,col+y*3);
				if(val != 0)
					table[val-1] = true;
			}
		}
		
		for(int j = 0; j < 9;j++)
			if(table[j] == false)
				board.set(486+((9*gridNum)+j),true);
	}
	
	private int getPossibleRowVals(int row)
	{
		int rvals = 0;
		for(int j = 0; j < 9;j++)
			if(board.get((324+(9*row)+j)))
				rvals|= (0x1 << j);
		return rvals;
	}
	
	private int getPossibleColVals(int col)
	{
		int cvals = 0;
		for(int j = 0; j < 9;j++)
			if(board.get((405+(9*col)+j)))
				cvals|= (0x1 << j);
		return cvals;
	}
	
	private int getPossibleGridVals(int gridNum)
	{
		int gvals = 0;
		for(int j = 0; j < 9;j++)
			if(board.get((486+(9*gridNum)+j)))
				gvals|= (0x1 << j);
		return gvals;
	}
	
	private int getPossibleIndex(int x, int y)
	{
		return getPossibleRowVals(x) & getPossibleColVals(y) & getPossibleGridVals((x/3)*3+(y/3));
	}
	
	private void simplify()
	{
		boolean check = true;
		while(check)
		{
			check = false;
			for(int row = 0; row < 9; row++)
			{
				for(int col = 0; col < 9; col++)
				{
					int value = getIndex(row,col);
					if(value == 0)
					{
						int possible = getPossibleIndex(row,col);
						if((possible & (possible-1))== 0)
						{
							int val = (int)(Math.log(possible)/Math.log(2));
							setIndex(row,col,val+1,false);
							check = true;
						}
					}
				}
			}
		}
	}
	
	private void printPossible(int val)
	{
		String result = "";
		for(int i = 0; i < 9;i++,val>>=1)
		{
			if((val&0x1) == 1)
				result+=(i+1)+",";
		}
		System.out.println(result);
	}
	
	private void printPossibleValues(int x, int y)
	{
		int ret = getPossibleIndex(x,y);
		String result = "("+x+","+y+"):";
		for(int i = 0; i < 9;i++)
		{
			if((ret&0x1)==1)
			{
				result+=(i+1)+",";
			}
			ret >>=1;
		}
		System.out.println(" "+result);
		
	}
	
	private ArrayList<Integer> getPossibilities(int num)
	{
		ArrayList<Integer> list = new ArrayList<Integer>(0);
		for(int i = 0; i < 9; i++,num>>=1)
			if((num&0x1)== 1)
				list.add(Integer.parseInt((i+1)+""));
		return list;
	}
	
	private boolean Nishio(int gridNum)
	{
		if(gridNum == 81)
			return true;
		
		ArrayList<Integer> list = null;
		int x = gridNum/9;
		int y = gridNum %9;
		int value = getIndex(x,y);
		if(value == 0)
		{
			list = getPossibilities(getPossibleIndex(x,y));
			if(list.size() == 0)
				return false;
			for(Integer choice: list)
			{
				setIndex(x,y,choice.intValue(),false);
				if(Nishio(gridNum+1))
					return true;
				setIndex(x,y,choice.intValue(),true);
			}
		}
		else
		{
			return Nishio(gridNum+1);
		}
		return false;
	}
	
	public void solve()
	{
		simplify();
		Nishio(0);	
	}
	
	public static void main(String[] args)
	{
		String b = 
		"12X4XX3XX"+
		"3XXX1XX5X"+
		"XX6XXX1XX"+
		"7XXX9XXXX"+
		"X4X6X3XXX"+
		"XX3XX2XXX"+
		"5XXX8X7XX"+
		"XX7XXXXX5"+
		"XXXXXXX98";
		
		Sudoku su = new Sudoku(b);
		su.solve();
		su.printBoard();		
	}
}


















