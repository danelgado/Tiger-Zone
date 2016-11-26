public class animals {

//constructuor
public animals(){
}

	public String place_croc(char[] tileID, int meeple_type){
		char prey = tileID[5];
		boolean opposing_tiger = false;
		boolean prey_present = false;
		String place_croc= "CROCODILE";
		for(int i=0; i< stack.size(); i++ ){
			Tile existingTile = stack.get(i);
			if ( existingTile.meeple > 0 || existingTile.meeple < 10 ) opposing_tiger = true;
			if( prey == 'D' || 
				prey == 'd' || 
				prey == 'P' || 
				prey == 'p' || 
				prey == 'B' ||
				prey == 'b'){
				prey_present = true;
			}
			if( opposing_tiger && prey_present) return place_croc;
			else return null;
		}
    
   }
