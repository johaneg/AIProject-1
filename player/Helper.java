package squatter.player;

import squatter.core.Piece;

public class Helper implements Piece {

	/** Returns if someone won the game.
	 * @param board Current situation of the game
	 * @return 1 if white won, 2 if black won, 0 if drawn
	 */
	public static int GetWinner(SquatterBoard board){
		if(board.emptycells == 0)
			return 1;
		else
			return 0;
		// TODO
	}
}
