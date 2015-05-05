package squatter.wrapper;

import java.io.PrintStream;

import aima.core.agent.Action;
import squatter.core.Move;
import squatter.core.Player;
import squatter.player.SquatterAgent;
import squatter.player.SquatterMove;

/** This class is a wrapper to use the aima agent class 
 * in the interface provided .
 * @author manoelribeiro
 */
public class SquatterAgentWrapper extends SquatterAgent implements Player {

	public SquatterAgentWrapper(){
		super();
	}
	
	@Override
	public int init(int n, int p) {
		return super.init(n, p);
	}

	@Override
	public Move makeMove() {
		return moveFromSquatterMove(super.execute());
	}

	@Override
	public int opponentMove(Move m) {
		super.changeBoard((squatterMoveFromMove(m)));
		return 0;
	}

	@Override
	public int getWinner() {
		return super.getWinner();
	}

	@Override
	public void printBoard(PrintStream output) {
		super.printBoard(output);
	}
	
	private Move moveFromSquatterMove(SquatterMove a){
		Move m = new Move();
		m.Col = a.Col;
		m.P = a.P;
		m.Row = a.Row;
		return m;
	}
	
	private SquatterMove squatterMoveFromMove (Move a){
		// TODO
		return null;
	}

}
