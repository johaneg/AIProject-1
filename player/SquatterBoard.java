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
	
	/** Prints boards
	 * @param output print stream where board will be printed
	 */
	public void printBoard (PrintStream output){
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				output.print(" " + b[i][j] + " ");
			}
			output.print("\n");
		}
	}
	
	/** Execute a move, change the number of empty cells and
	 * updates the board
	 * @param row
	 * @param col
	 * @param p color of the player making the move
	 */
	public void makeMove (int row, int col, int p){
		this.b[row][col] = p;
		emptycells--;
		updateBoard(row,col,p);
	}
	
	/** Check neighborhood to see if conquering is possible
	 * if it is, for each possible conquered direction
	 * see if it was conquered and updates accordingly
	 * @param row row of the move
	 * @param col col of the move
	 * @param p color of the player who made the move
	 */
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
				if(this.isRegionConquered(check[0], size, row, col + 1, p) == 1)
					{ updateConquered(check[0],p); }
			}
			
			if(conditions[2])//ft
			{
				if(this.isRegionConquered(check[1], size, row - 1, col, p) == 1)
					{ updateConquered(check[1],p); }
			}
			
			if(conditions[3])//fl
			{
				if(this.isRegionConquered(check[2], size, row, col - 1, p) == 1)
					{ updateConquered(check[2],p); } 
			}
			
			if(conditions[4])//fb
			{
				if(this.isRegionConquered(check[3], size, row + 1, col, p) == 1)
					{ updateConquered(check[3],p); }
			}
			

		}
	}
	
	/** Analyse the neighborhood of a play
	 * @param row
	 * @param col
	 * @param p
	 * @return return a list where the the elements are:
	 * l[0] if there is any chance of the play giving points
	 * l[1] if there's any chance of points being done to the RIGHT
	 * l[2] if there's any chance of points being done to the TOP
	 * l[3] if there's any chance of points being done to the LEFT
	 * l[4] if there's any chance of points being done to the BOTTOM
	 */
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
	
	/** Updates the region conquered, transforming all the (assuming color A is the conqueror)
	 * COLORB -> ColorB conquered by Color A
	 * Empty Conquered by Color B -> Empty Conquered by Color A
	 * Color A Conquered by Color B -> COLORA
	 * And handling the number of points and pieces accordingly
	 * @param c region conquered
	 * @param p color of the conqueror
	 */
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

	/** Expands the possibly conquered area
	 * @param n matrix that will be updated with the conquered area
	 * @param size size of the board
	 * @param x position of start x
	 * @param y position of start y
	 * @param surroundedby conqueror
	 * @return 1 if the area was truly conquered, 0 otherwise
	 */
	public int isRegionConquered(boolean [][] n, int size, int x, int y, int surroundedby){
		
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
			output *= isRegionConquered(n,size,x+1,y,surroundedby);
		
		if(y+1>=size) output = 0;
		else if (n[x][y+1] == false)
			output *= isRegionConquered(n,size,x,y+1,surroundedby);
		
		if(x-1<0) output = 0;
		else if (n[x-1][y]  == false)
			output *= isRegionConquered(n,size,x-1,y,surroundedby);
		
		if(y-1<0) return 0;
		else if(n[x][y-1]  == false)
			output *= isRegionConquered(n,size,x,y-1,surroundedby);
		
		// returns 1 if place is surrounded by 
		// surroundeby and 0 otherwise
		return output;	
	}
	
	
}
