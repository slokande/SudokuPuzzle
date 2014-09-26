References:
http://norvig.com/sudoku.html
http://www.sudokudragon.com/sudokustrategy.htm
http://www.sudokuwiki.org/naked_candidates

Sudoku.java
- Calls the main method
- Calls input reader.
- Initializes and solves the puzzle.
- Calls output writer.

SudokuHelper.java
- Methods: initializer, file reader, file writer.

SudokuSolver.java
- Solves the sudoku using two kinds of algorithm:
	1. Bactracking algorithm: constraint propagation and search are two techniques used to create backtracking Algorithm.The modificaiton here is that it reduces the search space when all the possibilities for each cell is considered.
	2. Two intelligent algorithms:
		This technique is known as “Naked Subset” or “Disjoint Subset” in general, and works by looking for candidates that can be removed from other cells. Naked Pairs are when there are just two candidates being looked for, Naked Triple when there are three.

IntelligentAlgorithms.java
- Methods: Getting all possibilities for each cell in the ground to reduce the search space for each cell, Naked pairs and Naked triplets.

BacktrackingAlgorithm.java
- Methods: Finding next unassigned value (defaulted to zero), Checking if none of the constraint is broken.
