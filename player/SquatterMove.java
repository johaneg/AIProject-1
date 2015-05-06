package squatter.player;

import aima.core.agent.Action;

public class SquatterMove implements Action, SquatterPiece {

	public int P;
	public int Row;
	public int Col;		
	
	@Override
	public boolean isNoOp() {
		return false;
	}
	
	public SquatterMove (int r, int c, int p){
		this.P = p;
		this.Row = r;
		this.Col = c;
	}

}
