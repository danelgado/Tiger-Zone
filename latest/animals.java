public class animals {

//constructuor
public animals(){
}
	
	//place_croc checks most recent tiles within proximity to recent one placed to check:
	//surrounding tiles for opposing tiger placement and surrounding tiles for unique prey
	//returns String CROCODILE to concatenate with final return message or null if a crocodile 
	//should not be placed.

	public String place_croc(ArrayList<Tile> stack){
		
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
	};
	
	//runs through most recent tiles placed
	//ensures tile is not player's tile and if a tiger is present, that it is not the player's tiger
	//priority for tiger placement goes to Dens
	//then priority goes to Lakes 
		
		
	public String place_tiger(Tile curTile, ArrayList<Tile> stack){
		boolean own; 
		int location = 0;
		String place_tiger = "TIGER" + location; 
		// priority 1 = Den 
		if( curTile.tileID[5] == 'X') {
			location = 5;
			return place_tiger;
		}
		// priority 2 = Lake
		else {
			for(int i=0; i< stack.size(); i++){
				Tile existingTile = stack.get(i);
				if ( !existingTile.ownTile && (existingTile.meeple > 9 || existingTile.meeple < 1) ) {
					if (curTile.tileID == "LLLL-" ||
						curTile.tileID == "JLLL-" ||
						curTile.tileID == "JLJL-" ||
						curTile.tileID == "TLLL-" ||
						curTile.tileID == "TLLLC") location= 5;
					else if ( curTile.tileID == "TLJT-" ||
							  curTile.tileID == "TLJTP" ||
							  curTile.tileID == "JLTT-" ||
							  curTile.tileID == "JLTT-" ||
							  curTile.tileID == "JLTTB" ||
							  curTile.tileID == "TLTJ-" ||
							  curTile.tileID == "TLTJD" ||
							  curTile.tileID == "TLTT-" ||
							  curTile.tileID == "TLTTP" ||
							  curTile.tileID == "JLLJ-"){
							if(curTile.rotation == 0 ) location = 6; 
							else if (curTile.rotation == 90 ) location = 2;
							else if ( curTile.rotation == 180 ) location = 4;
							else if ( curTile.rotation == 270 ) location = 8;
					}
					else if ( curTile.tileID == "TLLT-" || curTile.tileID == "TLLTB"  ){
						if(curTile.rotation == 0 ) location = 9; 
						else if (curTile.rotation == 90 ) location = 3;
						else if ( curTile.rotation == 180 ) location = 1;
						else if ( curTile.rotation == 270 ) location = 7;
						
					}
					else if ( curTile.tileID == "LJTJ-" ||
							  curTile.tileID == "LJTJD" ||
							  curTile.tileID == "LJLJ-" ||
							  curTile.tileID == "LJJJ-" ||
							  curTile.tileID == "LLJJ-"){
							if ( curTile.rotation == 0) location = 2;
							else if (curTile.rotation == 90 ) location = 4;
							else if ( curTile.rotation == 180 ) location = 8;
							else if ( curTile.rotation == 270 ) location = 6;
					}
					else if( location > 0) return place_tiger;
				}
			}
			return null;
		}
	};
		
    
   }
