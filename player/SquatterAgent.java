package squatter.player;

import java.io.PrintStream;
import java.util.Random;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.Percept;
import squatter.player.Helper;

/**
 * Random moving squatter agent. To improve override the method execute
 * @author manoelribeiro
 */
public class SquatterAgent implements Agent, SquatterPiece {

	private int color;
	private SquatterBoard board;
	private boolean isAlive;
	private Random rand;
	
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
		this.color = p;
		this.board = new SquatterBoard(n);
		rand = new Random();
		return 1;

	}
	
	public SquatterMove execute(){
		return this.execute(this.board);
	}
	
	@Override
	public SquatterMove execute(Percept b) {
		if(!(b instanceof SquatterBoard))
			throw new IllegalArgumentException("Percept must be of subclass board");
		
		
		int [][] positions = Helper.GetPositions(board);
		System.out.println(board.emptycells + " "+ positions.length);
		int play = rand.nextInt(board.emptycells);
		
		SquatterMove next = new SquatterMove(positions[play][0],positions[play][1],this.color);
		
		changeBoard(next);
		
		return next;
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
		
		if(isPosValid(move.Row, move.Col)) return -1;
		else{
			board.makeMove(move.Row, move.Col, move.P);
			return 0;
		}
	}
	
	private boolean isPosValid (int x, int y){
		return (this.board.b[x][y] != EMPTY);
	}
	
	public int getWinner(){
		return Helper.GetWinner(this.board);
	}
	
	public void printBoard(PrintStream output) {
		this.board.printBoard(output);
	}
	
}
