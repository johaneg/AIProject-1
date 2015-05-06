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
	
	public void makeMove (int x, int y, int p){
		this.b[x][y] = p;
		updateBoard(x,y,p);
	}
	
	private void updateBoard(int x, int y, int p){
		//degrees of freedom, where is NOT filled by same piece
		// Right, Top, Left and Bottom respectively
		Boolean fR = true, fT = true, fL = true, fB = true;
		
		if (neighboorhoodCheck(x,y,p, fR, fT, fL, fB)){
			emptycells--;
			return;
		}
		else {
			// declare check matrix
			Boolean[][][] check = new Boolean[4][size][size];
			
			if(fR)
				if(Helper.isRegionConquered(b, check[0], size, x + 1, y, p) == 1)
					updateConquered(check[0],p);
			
			if(fT)
				if(Helper.isRegionConquered(b, check[1], size, x, y + 1, p) == 1)
					updateConquered(check[1],p);

			if(fL)
				if(Helper.isRegionConquered(b, check[2], size, x - 1, y, p) == 1)
					updateConquered(check[2],p);
			
			if(fB)
				if(Helper.isRegionConquered(b, check[3], size, x, y - 1, p) == 1);
					updateConquered(check[3],p);

		}
		
	}
	
	public boolean neighboorhoodCheck (int x, int y, int p, Boolean fR, Boolean fT, Boolean fL, Boolean fB){
		int adj = 0;
		if ((x+1 < size)||(y < size)||(this.b[x+1][y] == p))
			{ adj++;  fR = false; }
		if ((x < size)||(y+1 < size)||(this.b[x][y+1] == p))
			{ adj++; fT = false; }
		if ((x-1 < size)||(y < size)||(this.b[x-1][y] == p))
			{ adj++; fL = false; }
		if ((x < size)||(y-1 < size)||(this.b[x-1][y] == p))
			{ adj++; fB = false; }
		return ((adj == 2)||(adj == 3));		
	}
	
	public void updateConquered(Boolean [][] c, int p){
			
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
