package squatter.player;

import java.util.Random;

public class RandomSquatterAgent extends SquatterAgent{

	Random r;
	
	public RandomSquatterAgent(){
		super();
		r = new Random();
	}
	
	@Override
	public int[] whereToGo (SquatterBoard b, int color, int[][] positions){
		int numb = b.emptycells;
		int rand = r.nextInt(numb);
		int[] whereToGo = new int[]{positions[rand][0],positions[rand][1]};
		
		return whereToGo;
	}


}
