package squatter.player;


import java.util.ArrayList;

import squatter.core.Piece;

public class Helper implements Piece {

	/** Returns if someone won the game.
	 * @param board Current situation of the game
	 * @return 1 if white won, 2 if black won, 0 if drawn
	 */
	public static int GetWinner(SquatterBoard board){
		if(board.emptycells != 0) return 0;
		
		int result = board.whitepoints - board.blackpoints;
		if(result > 0) return 1;
		else if (result < 0) return 2;
		else return 0;
	}
	
	
	

	
}
