package squatter.player;

import java.io.PrintStream;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.Percept;
import squatter.core.Move;
import squatter.core.Player;
import squatter.core.Piece;

public class SquatterAgent implements Agent, SquatterPiece {

	private int color;
	private SquatterBoard board;
	
	/** Creates new empty squatter agent
	 */
	public SquatterAgent(){}
	
	/** Initializes new squatter agent,
	 * @param n Color of the agent.
	 * @param p Board size.
	 */
	public int init (int n, int p){
		this.color = n;
		this.board = new SquatterBoard(p);
		return 1;
	}
	
	public SquatterMove execute(){
		return this.execute(this.board);
	}
	
	@Override
	public SquatterMove execute(Percept b) {
		if(!(b instanceof SquatterBoard))
			throw new IllegalArgumentException("Percept must be of subclass board");
		// TODO	
		return null;
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAlive(boolean arg0) {
		// TODO Auto-generated method stub
	}

	public void changeBoard(SquatterMove move){
		// TODO
	}
	
	public int getWinner(){
		// TODO
		return 0;
	}

	public void printBoard(PrintStream output) {
		this.board.printBoard(output);
	}
	
}
