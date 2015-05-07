package squatter.player;

import java.io.PrintStream;

import squatter.core.Move;
import squatter.core.Player;


/**
 * Random moving squatter agent. To improve override the method execute
 * @author manoelribeiro
 */
public abstract class SquatterAgent implements SquatterPiece, Player {

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
		this.color = p;
		this.board = new SquatterBoard(n);
		return 1;
	}
	
	public Move makeMove(){
		return this.makeMove(this.board, this.color);
	}
	
	private Move makeMove(SquatterBoard b, int color) {

		int [][] positions = b.GetPositions();
		
		int[] play = whereToGo(b,color,positions);
		
		Move next = new Move();
		next.Row = play[0];
		next.Col = play[1];
		next.P = this.color;
		
		b.makeMove(next.Row, next.Col, next.P);
		
		return next;
	}

	/**  Should return an array with two elements [row, column] on where the play should be made
	 * @param b
	 * @param color
	 * @param Positions
	 * @return
	 */
	public abstract int[] whereToGo (SquatterBoard b, int color, int[][] positions);
	
	public int opponentMove(Move m) {
		return board.makeMove(m.Row, m.Col, m.P);
	}
	
	/** Gets the winner outputting 1 if WHITE
	 * 2 if BLACK, 0 if none and -1 if DRAWN
	 * @return
	 */
	public int getWinner(){
		return this.board.GetWinner();
	}
	
	/** Prints the board in the given PrintStream
	 * @param output printstream where it'll be printed
	 */
	public void printBoard(PrintStream output) {
		this.board.printBoard(output);
	}
	
}
