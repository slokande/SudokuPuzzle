import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SudokuHelper {
	
	//To start with initialize all the cells to have 1 to 9 values as possibilities
	static Sudoku initialize(Sudoku sudoku)
	{
		ArrayList<Integer> each = new ArrayList<Integer>();
		for(int j = 1; j < 10; j++){
			each.add(j);
		}
		for(int i = 0; i <=8; i++)
		{
			for (int j = 0; j <=8; j++)
			{
				String index = Integer.toString(i) + Integer.toString(j);
				int key = Integer.parseInt(index);
				sudoku.possibilities.put(key, each);
			}
		}
		return sudoku;
	}

	//Writing to a file
	static void fileWriter(Sudoku s) throws IOException
	{
		File file = new File("<filename>");
		if (!file.exists()) 
		{
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter writer = new BufferedWriter(fw);
		
		writer.write("\n = = = = = = = = = ");
		for(int i = 0; i < 9; i++)	{
			writer.write("\n");
			for(int j =0; j < 9; j++){
				writer.write(" " + s.grid[i][j]);
			}
		}
		writer.close();
	}

	/*
	 * Reading file which consists of Sudoku inputs.
	 * Input can be in the two forms as below:
	 * Case 1:
		 *	4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......
	 * Case 1:
		 * 	003020600
		 *	900305001
		 *	001806400
		 *	008102900
		 *	700000008
		 *	006708200
		 *	002609500
		 *	800203009
		 *	005010300
		 *	========
	 *
	 */
	static ArrayList<Sudoku> fileReader(String fileName, ArrayList<Sudoku> inputs) throws IOException
	{
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String line = "";
		int row = 0; int col = 0;
		int rowCount = -1;
		Sudoku s = null;
		while((line = br.readLine()) != null)
		{
			//Case 1, where the input is given in each line.
			//Reading one line will result in one sudoku.
			if(line.length() == 81)
			{
				//When row = 8, Sudoku input is read.
				if(row >= 8)
				{
					row = 0;
					col = 0;
					inputs.add(s);
				}
				
				//Row is set to 0, then create a new instance of Sudoku for new input puzzle
				if(row == 0)
				{
					s = new Sudoku();
				}
				
				for(int i = 0; i < 81; i++)
				{
					if(row < 9)
					{
						if(col < 9)
						{
							if(line.charAt(i) == '.')
								s.grid[row][col] = 0;
							else 
								s.grid[row][col] = Integer.parseInt(Character.toString(line.charAt(i)));
							col++;
						}
						//New Row when col = 9
						else {
							row++;
							col = 0;
							if(line.charAt(i) == '.')
								s.grid[row][col] = 0;
							else 
								s.grid[row][col] = Integer.parseInt(Character.toString(line.charAt(i)));
							col++;
						}
					}
				}
			}
			
			//Reading each line.
			if(line.length() == 9)
			{
				//If rowCount is -1, then create a new instance of Sudoku for input
				if(rowCount == -1)
				{
					s = new Sudoku();
					row = 0;
					for(int i = 0; i < 9; i++)
					{
						s.grid[row][i] = Integer.parseInt(Character.toString(line.charAt(i)));
					}
					rowCount++;
					row++;
				}
				
				//Reading each line. 
				else if(rowCount >= 8 )
				{
					//If rowCount is -1 or >= 8, then create a new instance of Sudoku for input
					rowCount = -1;
					inputs.add(s);
					row = 0;
					col = 0;
					s = new Sudoku();
					for(int i = 0; i < 9; i++)
					{
						s.grid[row][i] = Integer.parseInt(Character.toString(line.charAt(i)));
					}
					rowCount++;
					row++;
				}
				//read each character
				else {
					for(int i = 0; i < 9; i++)
					{
						s.grid[row][i] = Integer.parseInt(Character.toString(line.charAt(i)));
					}
					rowCount++;
					row++;
				}
			}
		}
		br.close();
		inputs.add(s);
		return inputs;
	}
}
