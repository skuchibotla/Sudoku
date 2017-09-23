package sudoku;

import java.util.*;


public class Solver 
{
	private Grid						problem;
	private ArrayList<Grid>				solutions;
	
	
	public Solver(Grid problem)
	{
		this.problem = problem;
	}
	
	
	public void solve()
	{
		solutions = new ArrayList<>();
		solveRecurse(problem);
	}
	
		
	// 
	// FINISH THIS.
	//
	// Standard backtracking recursive solver.
	//
	private void solveRecurse(Grid grid)
	{	
		Evaluation eval = evaluate(grid);
		
		if (eval == Evaluation.ABANDON)
		{
			// Abandon evaluation of this illegal board.
			return;
		}
		else if (eval == Evaluation.ACCEPT)
		{
			// A complete and legal solution. Add it to solutions.
			solutions.add(grid);
		}
		else  if(eval == Evaluation.CONTINUE)
		{
			// Here if eval == Evaluation.CONTINUE. Generate all 9 possible next grids. Recursively 
			// call solveRecurse() on those grids.
			ArrayList<Grid> s = grid.next9Grids();
			for(Grid g : s) {
				if(g.isLegal())		// only recurse grids that are legal
					solveRecurse(g);
			}
		}
	}
	
	//
	// COMPLETE THIS
	//
	// Returns Evaluation.ABANDON if the grid is illegal. 
	// Returns ACCEPT if the grid is legal and complete.
	// Returns CONTINUE if the grid is legal and incomplete.
	//
	public Evaluation evaluate(Grid grid)
	{
		if(!(grid.isLegal())) {							// illegal grid
			return Evaluation.ABANDON;
		}
		else if(grid.isLegal() && grid.isFull()) {		// legal but not complete
			return Evaluation.ACCEPT;
		}
		else {
			return Evaluation.CONTINUE;					// everything else
		}
	}

	
	public ArrayList<Grid> getSolutions()
	{
		return solutions;
	}
	
	
	public static void main(String[] args)
	{
		Grid g = TestGridSupplier.getSudoku();		// or any other puzzle
		Solver solver = new Solver(g);
		solver.solve();
		
		// Print out your solution, or test if it equals() the solution in TestGridSupplier.
		Grid g1 = solver.getSolutions().get(0);
		System.out.println(g1);
	}
}
