package squatter.player;

import java.io.PrintStream;

import aima.core.agent.Percept;
import squatter.core.Piece;

public class SquatterBoard implements Percept, SquatterPiece {

	public int [][] b;
	public int size;
	public int emptycells;
	/** Initialize an empty board of size n
	 * @param n This creates a n x n board
	 */
	public SquatterBoard(int n)
	{
		this.size = n;
		// a default value of 0 (empty) is guaranteed by java specs
		this.b = new int[n][n];
		this.emptycells = n*n;
	}
	
	public void printBoard (PrintStream output){
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				output.print(" " + b[i][j] + " ");
			}
			output.print("\n");
		}
	}
	
	public void makeMove (int x, int y, int p){
		this.b[x][y] = p;
		this.emptycells--;
	}
}
