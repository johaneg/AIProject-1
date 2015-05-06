package squatter.player;

import java.io.PrintStream;
import java.util.Arrays;

import aima.core.agent.Percept;
import squatter.core.Piece;

public class SquatterBoard implements Percept, SquatterPiece {

	public int [][] b;
	public int size;
	public int emptycells;
	public int whitepoints, blackpoints;

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
		emptycells--;
		updateBoard(row,col,p);
	}
	
	private void updateBoard(int row, int col, int p){
		boolean[] conditions = neighboorhoodCheck(row,col,p);
		if (!conditions[0]){
			return;
		}
		else {

			// declare check matrix
			boolean[][][] check = new boolean[4][size][size];
			
			
			if(conditions[1])//fr
			{
				if(this.isRegionConquered(b, check[0], size, row, col + 1, p) == 1)
					{ updateConquered(check[0],p); }
			}
			
			if(conditions[2])//ft
			{
				if(this.isRegionConquered(b, check[1], size, row - 1, col, p) == 1)
					{ updateConquered(check[1],p); }
			}
			
			if(conditions[3])//fl
			{
				if(this.isRegionConquered(b, check[2], size, row, col - 1, p) == 1)
					{ updateConquered(check[2],p); } 
			}
			
			if(conditions[4])//fb
			{
				if(this.isRegionConquered(b, check[3], size, row + 1, col, p) == 1)
					{ updateConquered(check[3],p); }
			}
			

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
							whitepoints++;
						}
						if(b[x][y] == BLACK){ b[x][y] = BCAPbyW; whitepoints++;}
						if(b[x][y] == WCAPbyB){ b[x][y] = WHITE; blackpoints--;}
						if(b[x][y] == ECAPbyB) {b[x][y] = ECAPbyW; whitepoints++; blackpoints--;}

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
							blackpoints++;
						}
						if(b[x][y] == WHITE){ b[x][y] = WCAPbyB; blackpoints++;}
						if(b[x][y] == BCAPbyW){ b[x][y] = BLACK; whitepoints--;}
						if(b[x][y] == ECAPbyW){ b[x][y] = ECAPbyB; blackpoints++; whitepoints--;}

					}		
				}
			}
		}
	}

	public int isRegionConquered(int[][] b, boolean [][] n, int size, int x, int y, int surroundedby){
		
		//checks for corner hit
		if((x>=size)||(x<0)||(y>=size)||(y<0))
			return 0;
		
		// checks for other color hit
		if(b[x][y] == surroundedby)
			return 1;
		
		//visits the place
		n[x][y] = true;
		
		//recursively checks every other adjacent place
		int output = 1;
		
		if(x+1>=size) output = 0;
		else if(n[x+1][y] == false)
			output *= isRegionConquered(b,n,size,x+1,y,surroundedby);
		
		if(y+1>=size) output = 0;
		else if (n[x][y+1] == false)
			output *= isRegionConquered(b,n,size,x,y+1,surroundedby);
		
		if(x-1<0) output = 0;
		else if (n[x-1][y]  == false)
			output *= isRegionConquered(b,n,size,x-1,y,surroundedby);
		
		if(y-1<0) return 0;
		else if(n[x][y-1]  == false)
			output *= isRegionConquered(b,n,size,x,y-1,surroundedby);
		
		// returns 1 if place is surrounded by 
		// surroundeby and 0 otherwise
		return output;	
	}
	
	
}
