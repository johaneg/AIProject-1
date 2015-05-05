package squatter.player;

import java.io.PrintStream;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.Percept;
import squatter.player.Helper;

public class SquatterAgent implements Agent, SquatterPiece {

	private int color;
	private SquatterBoard board;
	private boolean isAlive;
	
	/** Creates new empty squatter agent
	 */
	public SquatterAgent(){
		isAlive = true;
	}
	
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
		return isAlive;
	}

	@Override
	public void setAlive(boolean arg0) {
		this.isAlive = arg0;
	}

	public int changeBoard(SquatterMove move){
		int position = board.b[move.Row][move.Col];
		if(position != EMPTY) return -1;
		else{
			board.makeMove(move.Row, move.Col, move.P);
			return 0;
		}
	}
	
	public int getWinner(){
		return Helper.GetWinner(this.board);
	}
	
	public void printBoard(PrintStream output) {
		this.board.printBoard(output);
	}
	
}
