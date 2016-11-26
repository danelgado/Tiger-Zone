public class animals {

//constructuor
public animals(){
}
	
	//place_croc checks most recent tiles within proximity to recent one placed to check:
	//surrounding tiles for opposing tiger placement and surrounding tiles for unique prey
	//returns String CROCODILE to concatenate with final return message or null if a crocodile 
	//should not be placed.

	public String place_croc(Stack<Tile> stack){
		
		boolean tiger_present = false;
		boolean prey_present = false;
		String place_croc= "CROCODILE";
		for(int i=0; i< stack.size(); i++ ){
			Tile existingTile = stack.get(i);
			char prey = existingTile.tileID[5];
			if (  existingTile.meeple > 0 || existingTile.meeple < 10 ) {
				tiger_present = true;
			}
			if(     prey == 'D' || 
				prey == 'd' || 
				prey == 'P' || 
				prey == 'p' || 
				prey == 'B' ||
				prey == 'b'){
				prey_present = true;
			}
			if( tiger_present && prey_present && !existingTile.ownTile) return place_croc;
			
		}
		return null;
		
	public String place_tiger(char[] tileID, int meeple){
		String place_tiger = "TIGER 
		
		return place_tiger;
		return null;
	
	
	
	}
		
    
   }
