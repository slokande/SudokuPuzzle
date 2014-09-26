import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;


public class Sudoku {
	
	int grid[][];
	/* possibilities is a map which has key, value pair. The cell number (00,01, 02, ... 80 ) is the key
	 * and value is the list of integers which are the possible values for each cell
	 */
	Map<Integer, ArrayList<Integer>> possibilities;
	
	//tracker to keep empty spaces
	int emptySpaces;
	
	public Sudoku() {
		this.grid = new int[9][9];
		for(int[] temp : this.grid)	{
			Arrays.fill(temp, 0);
		}
		this.emptySpaces =0;
		this.possibilities = new TreeMap<Integer, ArrayList<Integer>>();
	}
	
	public static void main(String [] args) throws IOException
	{
		ArrayList<Sudoku> inputs = new ArrayList<Sudoku>();
		//Reading from the file.
		inputs = SudokuHelper.fileReader("<filename>", inputs);
		System.out.println("Total number of inputs are: " + inputs.size());
		int i = 0;
		long startTime = System.nanoTime();
		while(i < inputs.size())
		{
			Sudoku s = inputs.get(i);
			
			//Initializing the possibility for each cell to zeroes
			s = SudokuHelper.initialize(s);
			
			//Getting the list of each possibilites for each cell.
			s = IntelligentAlgorithms.getAllPossibilities(s);
			
			//Solving the sudoku
			s = SudokuSolver.solveSudoku(s);
			
			/*//Writing to the file
			SudokuHelper.fileWriter(s);
			*/
			i++;
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000; 
		
		System.out.println("Runtime for " + duration +"ms");
	}
}