import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IntelligentAlgorithms {
	
	/*
	 * Getting all the possibilities for each cell of the grid.
	 */
	static Sudoku getAllPossibilities(Sudoku sudoku)	
	{
		sudoku.emptySpaces = 0;
		for(int r = 0; r < 9; r++)	
		{
			for(int c = 0; c< 9; c++)	
			{
				//Getting the current cell index in the grid
				String index = Integer.toString(r) + Integer.toString(c);
				int key = Integer.parseInt(index);
				
				if(sudoku.possibilities.containsKey(key))
				{
					ArrayList<Integer> possibleValues = new ArrayList<Integer>();
					possibleValues.addAll(sudoku.possibilities.get(key));
					
					if(sudoku.grid[r][c] ==0)	
					{
						sudoku.emptySpaces++;
						
						//row wise check. 
						for(int x = 0; x < 9; x++)	
						{
							if(sudoku.grid[r][x]!=0)
							{
								int value = sudoku.grid[r][x];
								if(possibleValues.contains(value))
								{
									possibleValues.remove((Object)value);
								}
							}
						}
						//col wise check.
						for(int y = 0; y < 9; y ++)	
						{
							if(sudoku.grid[y][c] != 0)	
							{
								int value = sudoku.grid[y][c];
								if(possibleValues.contains(value))
								{
									possibleValues.remove((Object)value);
								}
							}
						}
						//subgroup wise check.
						for(int i = 0; i < 3; i++)
						{
							for(int j = 0; j < 3; j++)
							{
								if(sudoku.grid[(r - r%3) + i][(c - c%3) + j] !=0)
								{
									int value = sudoku.grid[(r - r%3) + i][(c - c%3) + j];
									if(possibleValues.contains(value))
									{
										possibleValues.remove((Object)value);
									}
								}
							}
						}
					}
					sudoku.possibilities.remove(key);
					sudoku.possibilities.put(key, possibleValues);
				}
			}
		}
		
		List<Integer> keysToRemove = new ArrayList<Integer>();
		for(int key:sudoku.possibilities.keySet())
		{
			ArrayList<Integer> possibleValues = new ArrayList<Integer>();
			possibleValues.addAll(sudoku.possibilities.get(key));
			if(possibleValues.size() == 9)
				keysToRemove.add(key);
		}
		
		for(int eachKey:keysToRemove)
		{
			sudoku.possibilities.remove(eachKey);
		}
		return sudoku;
	}
	
	/*
	 * This technique is known as “Naked Subset” or “Disjoint Subset” in general, 
	 * and works by looking for candidates that can be removed from other cells. Naked Pairs are when there are just
	 * two candidates being looked for, Naked Triple when there are three
	 */
	
	/*
	 *  A Naked Pair (also known as a Conjugate Pair) is a set of two candidate numbers
	 *  sited in two cells that belong to at least one unit in common. That is they reside 
	 *  in the same row, box or column.
	 */
	static Sudoku nakedPairs(int n,  Sudoku s)
	{
		/* Rows: Traversing each row to find Naked-Pair*/
		s = rowWiseLogc(n,s);
		
		/*Traversing each column to find the Naked Pair*/
		s = colWiseLogic(n,s);
		
		//subgroup wise
		subGroupWiseLogic(n,s);
		return s;
	}
	
	/*
	 * Any group of three cells in the same unit that contain IN TOTAL three candidates is a Naked Triple. 
	 * Each cell can have two or three numbers, as long as in combination all three cells have only three numbers. 
	 * When this happens, the three candidates can be removed from all other cells in the same unit.
	 */
	static Sudoku nakedTriples(int n, Sudoku s)
	{
		/* Rows: Traversing each row to find Naked-Pair*/
		s= rowWiseLogc(n,s);
		
		/*Traversing each column to find the Naked Pair*/
		s= colWiseLogic(n,s);
		
		//subgroup wise
		s= subGroupWiseLogic(n,s);
		return s;
	}
	 
	static Sudoku rowWiseLogc(int n,Sudoku sudoku)
	{
		for(int r = 0; r < 9; r++)
		{
			Map<ArrayList<Integer>, Integer> rows = new HashMap<ArrayList<Integer>, Integer>();
			for(int c =0; c < 9; c++)
			{
				ArrayList<Integer> eachRow = new ArrayList<Integer>();
				
				String index = Integer.toString(r) +Integer.toString(c);
				int key = Integer.parseInt(index);
				
				if(sudoku.possibilities.containsKey(key))
				{
					if(sudoku.possibilities.get(key).size() == n)
					{
						eachRow.addAll(sudoku.possibilities.get(key));
						Collections.sort(eachRow);
					}
				
					if(eachRow.size() > 0)	
					{
						if(rows.containsKey(eachRow))
						{
							int count = rows.get(eachRow);
							count++;
							rows.put(eachRow, count);
						}
						
						else 
						{
							rows.put(eachRow, 1);
						}
					}
				}
			}
			
			Map<ArrayList<Integer>, Integer> finalRows = new HashMap<ArrayList<Integer>, Integer>();
			for(ArrayList<Integer> key:rows.keySet())
			{
				if(rows.get(key)==n)
					finalRows.put(key,rows.get(key));
			}
			
			for(int c =0; c<9; c++)
			{
				String index = Integer.toString(r) +Integer.toString(c);
				int key = Integer.parseInt(index);
				
				ArrayList<Integer> eachRow = new ArrayList<Integer>();
				
				if(sudoku.possibilities.containsKey(key))
				{
					eachRow.addAll(sudoku.possibilities.get(key));
					Collections.sort(eachRow);
					
					for(ArrayList<Integer> pairKey:finalRows.keySet())
					{
						if(!pairKey.containsAll(eachRow))
						{
							eachRow.removeAll(pairKey);
						}
					}
					sudoku.possibilities.put(key,eachRow);
				}
			}
			
		}
		return sudoku;
	}
	
	static Sudoku colWiseLogic(int n, Sudoku sudoku)
	{
		for(int c = 0; c < 9; c++)
		{
			Map<ArrayList<Integer>, Integer> cols = new HashMap<ArrayList<Integer>, Integer>();
			for(int r =0; r < 9; r++)
			{
				ArrayList<Integer> eachCol = new ArrayList<Integer>();
				
				String index = Integer.toString(r) +Integer.toString(c);
				int key = Integer.parseInt(index);
				
				if(sudoku.possibilities.containsKey(key))
				{
					if(sudoku.possibilities.get(key).size() == n)
					{
						eachCol.addAll(sudoku.possibilities.get(key));
						Collections.sort(eachCol);
					}
				
					if(eachCol.size() > 0)	
					{
						if(cols.containsKey(eachCol))
						{
							int count = cols.get(eachCol);
							count++;
							cols.put(eachCol, count);
						}
						
						else 
						{
							cols.put(eachCol, 1);
						}
					}
				}
			}
			
			Map<ArrayList<Integer>, Integer> fianlCols = new HashMap<ArrayList<Integer>, Integer>();
			for(ArrayList<Integer> key:cols.keySet())
			{
				if(cols.get(key)==n)
					fianlCols.put(key,cols.get(key));
			}
			
			for(int r =0; r<9; r++)
			{
				String index = Integer.toString(r) +Integer.toString(c);
				int key = Integer.parseInt(index);
				
				ArrayList<Integer> eachCol= new ArrayList<Integer>();
				
				if(sudoku.possibilities.containsKey(key))
				{
					eachCol.addAll(sudoku.possibilities.get(key));
					Collections.sort(eachCol);
					
					for(ArrayList<Integer> pairKey:fianlCols.keySet())
					{
						if(!pairKey.containsAll(eachCol))
						{
							eachCol.removeAll(pairKey);
						}
					}
					sudoku.possibilities.put(key,eachCol);
				}
			}
		}
		return sudoku;
	}
	
	static Sudoku subGroupWiseLogic(int n,Sudoku sudoku)
	{
		for(int rowIndex = 0; rowIndex < 9; rowIndex+=3)
		{
			for(int colIndex = 0; colIndex < 9; colIndex+=3)
			{
				Map<ArrayList<Integer>, Integer> subGroups = new HashMap<ArrayList<Integer>, Integer>();
				for(int r = rowIndex; r < rowIndex+3; r++)
				{
					for(int c = colIndex; c < colIndex+ 3; c++)
					{
						String index = Integer.toString(r) +Integer.toString(c);
						int key = Integer.parseInt(index);
						ArrayList<Integer> eachGroup= new ArrayList<Integer>();
						
						if(sudoku.possibilities.containsKey(key))
						{
							if(sudoku.possibilities.get(key).size() == n)
							{
								eachGroup.addAll(sudoku.possibilities.get(key));
								Collections.sort(eachGroup);
							}
							if(eachGroup.size() > 0)	
							{
								if(subGroups.containsKey(eachGroup))
								{
									int count = subGroups.get(eachGroup);
									count++;
									subGroups.put(eachGroup, count);
								}
								
								else 
								{
									subGroups.put(eachGroup, 1);
								}
							}
						}
					}
				}
				
				Map<ArrayList<Integer>, Integer> fianlSubGroups = new HashMap<ArrayList<Integer>, Integer>();
				
				if(subGroups.size() > 0)
				{
					for(ArrayList<Integer> key:subGroups.keySet())
					{
						if(subGroups.get(key)==n)
							fianlSubGroups.put(key,subGroups.get(key));
					}
					
					for(int r = rowIndex; r < rowIndex+3; r++)
					{
						for(int c = colIndex; c < colIndex+ 3; c++)
						{
							String index = Integer.toString(r) +Integer.toString(c);
							int key = Integer.parseInt(index);
							
							ArrayList<Integer> eachGroup= new ArrayList<Integer>();
							
							if(sudoku.possibilities.containsKey(key))
							{
								eachGroup.addAll(sudoku.possibilities.get(key));
								Collections.sort(eachGroup);
								
								for(ArrayList<Integer> pairKey:fianlSubGroups.keySet())
								{
									if(!pairKey.containsAll(eachGroup))
									{
										eachGroup.removeAll(pairKey);
									}
								}
							}
							
							sudoku.possibilities.put(key,eachGroup);
						}
					}
				}
			}
		}
		return sudoku;
	}
}
