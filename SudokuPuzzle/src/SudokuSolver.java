import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SudokuSolver {
	
	static Sudoku solveIntelligent(Sudoku sudoku)
	{
		int prevEmptySpaces = 0;
		
		/*If the Current empty space is equal to previous empty space
		 *then Intelligent Algorithms are not able to find the solution
		 *Need to use backtracking algorithm with a reduced search space to solve
		 */
		
		while(sudoku.emptySpaces > 0 && prevEmptySpaces != sudoku.emptySpaces)
		{
			prevEmptySpaces = sudoku.emptySpaces;
			Map<Integer, ArrayList<Integer>>tempPossibilities = new TreeMap<Integer, ArrayList<Integer>>();;
			tempPossibilities.putAll(sudoku.possibilities);
			List<Integer> keysToRemove = new ArrayList<Integer>();
			
			//Set the cell value if there is a single possible value in the possibilities list.
			for(int key:tempPossibilities.keySet())
			{
				ArrayList<Integer> possibleValues = new ArrayList<Integer>();
				possibleValues.addAll(tempPossibilities.get(key));
				
				/*Singleton case
				 * there's only one number left that could possibly fit there - you can fill in that number. 
				*/
				if(possibleValues.size() == 1)
				{
					String index = Integer.toString(key);
					//The cells of the first row has key length of 1: 01=>1, 02=>2...
					if(index.length() == 1)	
					{
						int col = Integer.parseInt(index);
						sudoku.grid[0][col] = possibleValues.get(0);
						keysToRemove.add(key);
						IntelligentAlgorithms.getAllPossibilities(sudoku);
					}
					//rest of the cells has the key of length 2 
					else 
					{
						int r = Character.getNumericValue(index.charAt(0));
						int c = Character.getNumericValue(index.charAt(1));
						sudoku.grid[r][c] = possibleValues.get(0);
						keysToRemove.add(key);
						IntelligentAlgorithms.getAllPossibilities(sudoku);
					}
				}
			}
			//Once the cell value is set, remove the key.
			for(int key:keysToRemove)
			{
				sudoku.possibilities.remove(key);
			}
			sudoku = IntelligentAlgorithms.nakedPairs(2,sudoku);
			sudoku = IntelligentAlgorithms.nakedTriples(3,sudoku);
		}
		
		if(sudoku.emptySpaces > 0 && prevEmptySpaces == sudoku.emptySpaces)
		{
			boolean solved = BacktrackingAlgorithm.solve(sudoku);
			if(solved == false)
			{
				System.out.println("Could not be solved");
			}
		}
		return sudoku;
	}
	
	static Sudoku solveSudoku(Sudoku sudoku)	
	{
		//On assumption that hard and evil problems have more than 60 empty spaces.
		
		if(sudoku.emptySpaces <= 60)
		{
			return SudokuSolver.solveIntelligent(sudoku);
		}
		else 
		{
			boolean solved = BacktrackingAlgorithm.solve(sudoku);
			if(solved == false)
			{
				System.out.println("Could not be solved");
			}
			return sudoku;
		}
		
		//Only backtracking
		/*boolean solved = BacktrackingAlgorithm.solve(sudoku);
		if(solved == false)
		{
			System.out.println("Could not be solved");
		}
		return sudoku;*/
	}
}
