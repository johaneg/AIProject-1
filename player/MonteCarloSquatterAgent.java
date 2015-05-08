package squatter.player;

import java.util.Random;
import java.util.Collections;

import squatter.core.Move;


public class MonteCarloSquatterAgent extends SquatterAgent{

	private Random r;
	private int totalSimulations;
	
	public MonteCarloSquatterAgent(){
		super();
		r = new Random();
		this.totalSimulations = 0;

	}
	
	@Override
	public int[] whereToGo (SquatterBoard b, int color, int[][] positions){

		int numb = b.emptycells;
		
		int[] wins = new int[b.emptycells];
		int[] timesVisited = new int[b.emptycells];
		Double[] weigth = new Double[b.emptycells];
		int simulations = 0;
		
		int tmpPlay;

		for(int i = 0; i < 10000; i++){
			
			tmpPlay = r.nextInt(numb);
			
			boolean win = isRandomGameWon(color, b.cloneBoard(), positions[tmpPlay]);
			
			timesVisited[tmpPlay]++;
			simulations++;
			totalSimulations++;
			if(win) wins[tmpPlay]++;
			
		
		}
		
		for (int i = 0; i < b.emptycells; i++){
			weigth[i] = ( (double) wins[i] )/ timesVisited[i] + Math.sqrt(2)*Math.sqrt(Math.log((double)simulations)/totalSimulations);
		}
		
		double tmp = 0;
		int index = 0;
		
		for (int i = 0; i < b.emptycells; i++){
			if(weigth[i] > tmp)
			{
				tmp = weigth[i];
				index = i;
			}
		}
		
		
		return positions[index];
	}
	
	private boolean isRandomGameWon(int color, SquatterBoard b, int[] play){
		int oponentColor;
		if(color == WHITE) oponentColor = BLACK;
		else oponentColor = WHITE;
		int whoseTurn;
		int numb;
		int rand;
		int[][] positions;
		
		// makes the first play
		b.makeMove(play[0], play[1], color);
		whoseTurn = oponentColor;
		
		while(b.GetWinner() == 0){
			numb = b.emptycells;
			rand = r.nextInt(numb);
			positions = b.GetPositions();
			b.makeMove(positions[rand][0], positions[rand][1], whoseTurn);
			
			whoseTurn = (whoseTurn == BLACK) ? WHITE : BLACK; 
		}

		if(b.GetWinner() == color) return true;
		
		else return false;
	}


}
