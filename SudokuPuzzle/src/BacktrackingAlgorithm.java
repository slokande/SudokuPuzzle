import java.util.ArrayList;

class helperInt{
	
	int value;
	helperInt(int d)
	{
		value = d;
	}
	helperInt()  //default value of "value" =0
	{
		value = 0;
	}
}

/*
 *  constraint propagation and search are two techniques used to create backtracking Algorithm
 *  The modificaiton here is that it reduces the search space when all the possibilities for each cell 
 *  is considered.
 */
public class BacktrackingAlgorithm {
	
	static boolean solve(Sudoku s)
	{
		helperInt row=new helperInt();  //default value of "value" =0
		helperInt col=new helperInt();
		
		if(!findNextUnassigned(s, row, col))
		{	
			return true;
		}
		
		String index = Integer.toString(row.value) + Integer.toString(col.value);
		int key = Integer.parseInt(index);
		ArrayList<Integer> possibleValues = new ArrayList<Integer>();
		
		if(s.possibilities.containsKey(key))
		{
			possibleValues.addAll(s.possibilities.get(key));
		}
		
		for(int num : possibleValues)
		{
			if(isSafe(s.grid, row, col, num))
			{
				s.grid[row.value][col.value] = num;
				
				if(solve(s))
				{
					return true;
				}
				s.grid[row.value][col.value] = 0;
			}
		}
		return false;
	}
	
	/*
	 * Finding next empty cell.
	 */
	static boolean findNextUnassigned(Sudoku s, helperInt row, helperInt col)
	{
		for(row.value = 0; row.value < 9; row.value++)
		{
			for(col.value = 0; col.value < 9; col.value++)
			{
				if(s.grid[row.value][col.value] == 0)
					return true;
			}
		}
		return false;
	}
	/*
	 * Checking if the temporary assignment on each cell matches all the
	 * constraints.
	 */
	static boolean isSafe(int [][]grid, helperInt r, helperInt c, int val)
	{
		boolean safe = true;
		
		for(int i = 0; i < 9; i++)
		{
			if(grid[r.value][i] == val)
			{
				safe = false;
				return safe;
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			if(grid[i][c.value] == val)
			{
				safe= false;
				return safe;
			}
		}
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(grid[(r.value - r.value%3) + i][(c.value - c.value%3) + j] == val)
				{
					safe = false;
					return safe;
				}
			}
		}
		return safe;
	}
}
