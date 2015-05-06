package squatter.player;

import java.io.PrintStream;
import java.util.Arrays;

import aima.core.agent.Percept;
import squatter.core.Piece;

public class SquatterBoard implements Percept, SquatterPiece {

	public int [][] b;
	public int size;
	public int emptycells;
	public int whitepoints;
	public int blackpoints;
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
	
	public void makeMove (int row, int col, int p){
		this.b[row][col] = p;
		updateBoard(row,col,p);
	}
	
	private void updateBoard(int row, int col, int p){

		boolean[] conditions = neighboorhoodCheck(row,col,p);
		if (conditions[0]){
			emptycells--;
			return;
		}
		else {
			// declare check matrix
			boolean[][][] check = new boolean[4][size][size];
			
			if(conditions[1])
				if(Helper.isRegionConquered(b, check[0], size, row + 1, col, p) == 1)
					updateConquered(check[0],p);
			
			if(conditions[2])
				if(Helper.isRegionConquered(b, check[1], size, row, col + 1, p) == 1)
					updateConquered(check[1],p);

			if(conditions[3])
				if(Helper.isRegionConquered(b, check[2], size, row - 1, col, p) == 1)
					updateConquered(check[2],p);
			
			if(conditions[4])
				if(Helper.isRegionConquered(b, check[3], size, row, col - 1, p) == 1);
					updateConquered(check[3],p);

		}
	}
	
	public boolean[] neighboorhoodCheck (int row, int col, int p){
		int adj = 0;
		
		boolean fR = true, fT = true, fL = true, fB = true;
		
		if (row+1 >= size) fR = false;
		else if(this.b[row+1][col] == p){adj++; fB = false; }
		
		if (col-1 < 0) fT = false;
		else if(this.b[row][col-1] == p){adj++; fL = false;}
		
		if (row-1 < 0) fL = false;
		else if(this.b[row-1][col] == p){adj++; fT = false;}

		if (col+1 >= size) fB = false;
		else if(this.b[row][col+1] == p){adj++; fR = false;}

		boolean worthInspecting = ((adj == 2)||(adj == 3));	
		
		return new boolean[]{worthInspecting,fR, fT, fL, fB};
	}
	
	public void updateConquered(boolean [][] c, int p){
			
		if(p == WHITE){
			for (int x = 0; x < size; x++){
				for (int y = 0; y < size; y++){
					if (c[x][y] == true){
						if(b[x][y] == EMPTY){
							b[x][y] = ECAPbyW;
							emptycells--;
						}
						if(b[x][y] == BLACK) b[x][y] = BCAPbyW;
						if(b[x][y] == WCAPbyB) b[x][y] = WHITE;
					}
				}
			}
		}
		if(p == BLACK){
			for (int x = 0; x < size; x++){
				for (int y = 0; y < size; y++){
					if (c[x][y] == true){
						if(b[x][y] == EMPTY){
							b[x][y] = ECAPbyB;
							emptycells--;
						}
						if(b[x][y] == WHITE) b[x][y] = WCAPbyB;
						if(b[x][y] == BCAPbyW) b[x][y] = BLACK;
					}		
				}
			}
		}
	}
	
	
}
