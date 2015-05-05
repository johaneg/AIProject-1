package squatter.player;

import java.io.PrintStream;

import aima.core.agent.Percept;
import squatter.core.Piece;

public class SquatterBoard implements Percept, SquatterPiece {

	private int [][] board;
	
	/** Initialize an empty board of size n
	 * @param n This creates a n x n board
	 */
	public SquatterBoard(int n)
	{
		// a default value of 0 (empty) is guaranteed by java specs
		board = new int[n][n];
	}
	
	public void printBoard (PrintStream output){
		// TODO
	}
}
