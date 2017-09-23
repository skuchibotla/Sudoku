package sudoku;

import java.util.*;

public class Grid {
	private int[][] values;

	//
	// DON'T CHANGE THIS.
	//
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows) {
		values = new int[9][9];
		for (int j = 0; j < 9; j++) {
			String row = rows[j]; 				// Accesses first row of String[] representation of values
			char[] charray = row.toCharArray(); // Has each character in row
			for (int i = 0; i < 9; i++) {
				char ch = charray[i]; 			// Accesses each char in array
				if (ch != '.')		
					values[j][i] = ch - '0';	// Makes value[j][i] equal int version of char
			}
		}
	}

	//
	// DON'T CHANGE THIS.
	//
	public String toString() {
		String s = "";						
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {
				int n = values[j][i];		
				if (n == 0)					
					s += '.';				// 0 is replaced by '.'
				else
					s += (char) ('0' + n);	// Any other number replaced with a char version of int
			}
			s += "\n";
		}
		return s;
	}

	//
	// COMPLETE THIS
	//
	//
	// Finds an empty member of values[][]. Returns an array list of 9 grids
	// that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the
	// current grid is full.
	//
	// Example: if this grid = 1........
	// .........
	// .........
	// .........
	// .........
	// .........
	// .........
	// .........
	// .........
	//
	// Then the returned array list would contain:
	//
	// 11....... 12....... 13....... 14....... and so on 19.......
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	//
	
	public int[] getNumber() {
		int[] num = new int[2];					// Array with "coordinates" of empty value in values
		for(int row = 0; row < 9; row++) {
			for(int col = 0; col < 9; col++) {
				if(values[row][col] == 0) {		
					num[0] = row;				// "x" position of empty value
					num[1] = col;				// "y" position of empty value
					return num;
				}
			}
		}
		return num;
	}
	
	// Sets empty value based on coordinates given
	public void setValue(int x, int y, int value) {
		values[x][y] = value;						
	}
	
	public ArrayList<Grid> makeGrid() {
		ArrayList<Grid> g = new ArrayList<Grid>();
		int[] index = getNumber();
		String[] rows = new String[9];	
		String r = "";
		for(int count = 1; count < 10; count++) {
			for(int row = 0; row < 9; row++) {
				for(int col = 0; col < 9; col++) {
					r += "" + values[row][col];			// Makes string representation of each row
				}
				rows[row] = r;
				r = "";
			}
			Grid grid = new Grid(rows);
			int x = index[0];							// x coordinate of empty value
			int y = index[1];							// y coordinate of empty value
			grid.setValue(x, y, count);					// set empty value based on coordinates given
			g.add(grid);								// adds to grid
		}
		return g;
	}
	
	public ArrayList<Grid> next9Grids() {
		if (this.isFull()) {
			return null;
		}
		else {
			return makeGrid();
		}
	}
		

	//
	// COMPLETE THIS
	//
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal() {
		HashSet<Integer> rows = new HashSet<Integer>();
		HashSet<Integer> columns = new HashSet<Integer>();
		for(int row = 0; row < values.length; row++) {
			for(int column = 0; column < values[row].length; column++) {
				int r = this.values[row][column];	// row					
				int c = this.values[column][row];	// column
				
				// If you can't add to row or column and the number is between 1 and 9
				if(((!rows.add(r)) && (r > 0 && r < 10)) || ((!columns.add(c)) && (c > 0 && c < 10))) {
					return false;
				}
				else {
					rows.add(r);
					columns.add(c);
				}
			}
			// Clear rows and columns for new ones
			rows.clear();
			columns.clear();
		}
		
		// 3x3
		HashSet<Integer> hashSet1 = new HashSet<Integer>();	// side 1 (index 0 - 2)
		HashSet<Integer> hashSet2 = new HashSet<Integer>();	// side 2 (index 3 - 5)
		HashSet<Integer> hashSet3 = new HashSet<Integer>(); // side 3 (index 6 - 8)
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				if(j < 3) {
					// If you can't add to the first hashSet and the value isn't 0
					if(!hashSet1.add(values[i][j]) && (values[i][j] != 0)) { 
						return false;
					}
				}
				else if(j < 6) {
					// If you can't add to the second hashSet and the value isn't 0
					if(!hashSet2.add(values[i][j]) && (values[i][j] != 0)) {
						return false;
					}
				}
				else {
					// If you can't add to the third hashSet and the value isn't 0
					if(!hashSet3.add(values[i][j]) && (values[i][j] != 0)) {
						return false;
					}
				}
			}
			
			// Clear hashSets once the index reaches the end of side
			if(i == 2 || i == 5 || i == 8) {
				hashSet1.clear();
				hashSet2.clear();
				hashSet3.clear();
			}
		}
		return true;
	}

	//
	// COMPLETE THIS
	//
	// Returns true if every cell member of values[][] is a digit from 1-9.
	//
	public boolean isFull() {
		int count = 0;
		for (int i = 0; i < values.length; i++) {
			for (int k = 0; k < values[i].length; k++) {
				if (values[i][k] >= 1 && values[i][k] <= 9)
					count++;
			}
		}
		
		// since there are 81 values in a sudoku grid
		if (count == 81) {
			return true;
		}
		else {
			return false;
		}
	}

	//
	// COMPLETE THIS
	//
	// Returns true if x is a Grid and, for every (i,j),
	// x.values[i][j] == this.values[i][j].
	//
	public boolean equals(Object x) {
		Grid x1 = (Grid) x;
		int count = 0;
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				if(x1.values[i][j] == this.values[i][j]) { 	//checking for each value
					count++;
				}
			}
		}
		
		if(count == 81) {
			return true;
		}
		return false;
	}
}

