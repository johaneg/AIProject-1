package squatter.player;


import java.util.ArrayList;

import squatter.core.Piece;

public class Helper implements Piece {

	/** Returns if someone won the game.
	 * @param board Current situation of the game
	 * @return 1 if white won, 2 if black won, 0 if drawn
	 */
	public static int GetWinner(SquatterBoard board){
		int result = board.whitepoints - board.blackpoints;
		if(result > 0) return 1;
		else if (result < 0) return 2;
		else return 0;
	}
	

	
	public static int isRegionConquered(int[][] b, boolean [][] n, int size, int x, int y, int surroundedby){
		
		//checks for corner hit
		if(cornerHit(size,x,y))
			return 0;
		
		// checks for other color hit
		if(b[x][y] == surroundedby)
			return 1;
		
		//visits the place
		n[x][y] = true;
		
		int output = 1;
		
		if(x+1>=size) return 0;
		else if(n[x+1][y] == false)
			output *= isRegionConquered(b,n,size,x+1,y,surroundedby);
		
		if(y+1>=size) return 0;
		if (n[x][y+1] == false)
			output *= isRegionConquered(b,n,size,x,y+1,surroundedby);
		
		if(x-1<0) return 0;
		if (n[x-1][y]  == false)
			output *= isRegionConquered(b,n,size,x-1,y,surroundedby);
		
		if(y-1<0) return 0;
		if(n[x][y-1]  == false)
			output *= isRegionConquered(b,n,size,x,y-1,surroundedby);
		
		return output;	
	}
	
	public static boolean cornerHit(int s, int x, int y){
		return ((x>=s)||(x<0)||(y>=s)||(y<0));
	}
	
}
