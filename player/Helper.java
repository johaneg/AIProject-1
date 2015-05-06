package squatter.player;


import java.util.ArrayList;

import aima.core.util.datastructure.Pair;
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
		else return -1;
	}
	
	public static int[][] GetPositions(SquatterBoard board){
		
		int [][] options = new int[board.emptycells][2];
		
		int k = 0;
		for(int i = 0; i < board.size; i++)
		{
			for(int j = 0; j < board.size; j++){
				if (board.b[i][j] == EMPTY){
					options[k][0] = i;
					options[k][1] = j;
					k++;
				}
			}

		}
		System.out.println();
		return options;
	}
	
	
}
