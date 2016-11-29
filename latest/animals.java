import java.util.*;
import java.io.*;

public class animals {

//constructuor
public animals(){
}
	
	//place_croc checks most recent tiles within proximity to recent one placed to check:
	//surrounding tiles for opposing tiger placement and surrounding tiles for unique prey
	//returns String CROCODILE to concatenate with final return message or null if a crocodile 
	//should not be placed.

	public String place_croc(player me, Tile curTile, ArrayList<Tile> stack){
		
		for(int i=0; i< stack.size(); i++ ){
			boolean tiger_present = false;
			boolean prey_present = false;
			Tile existingTile = stack.get(i);
			char prey = existingTile.tileID[4];
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
			if( tiger_present && prey_present && !existingTile.ownTile) {
				curTile.meeple = -1;
				me.crocodiles--;
				return " CROCODILE";
           		 }
			
		};
		return null;
	};
	
	//runs through most recent tiles placed
	//ensures tile is not player's tile and if a tiger is present, that it is not the player's tiger
	//priority for tiger placement goes to Dens
	//then priority goes to Lakes 
	//then priority goes to Game-trails and Jungles
		
		
	public String place_tiger(player me, Tile curTile, ArrayList<Tile> stack){
		boolean own; 
		int location = 0;
		String place_tiger = " TIGER " + location;
		// priority 1 = Den 
		if( curTile.tileID[4] == 'X') {
			location = 5;
			me.tigers--;
			return place_tiger;
		}
		// priority 2 = Lake
		else {
			for(int i=0; i< stack.size(); i++){
				Tile existingTile = stack.get(i);
				String strID = String.valueOf(existingTile.tileID);
				if ( !existingTile.ownTile && (existingTile.meeple > 9 || existingTile.meeple < 1) ) {
					if (curTile.tileID[3] == 'L' ) {
						location= 5;
						curTile.meeple = location;
						me.tigers--;
						return place_tiger;
					    }
					else if ( strID == "TLJT-" ||
						  strID == "TLJTP" ||
						  strID == "JLTT-" ||
						  strID == "JLTT-" ||
						  strID == "JLTTB" ||
						  strID == "TLTJ-" ||
						  strID == "TLTJD" ||
						  strID == "TLTT-" ||
						  strID == "TLTTP" ||
						  strID == "JLLJ-"){
							if(curTile.rotation == 0 ) location = 6; 
							else if (curTile.rotation == 90 ) location = 2;
							else if ( curTile.rotation == 180 ) location = 4;
							else if ( curTile.rotation == 270 ) location = 8;
							else{
								curTile.meeple = location;
								me.tigers--;
								return place_tiger;
                            				}
					}
					else if ( strID == "TLLT-" || strID == "TLLTB"  ){
						if(curTile.rotation == 0 ) location = 9; 
						else if (curTile.rotation == 90 ) location = 3;
						else if ( curTile.rotation == 180 ) location = 1;
						else if ( curTile.rotation == 270 ) location = 7;
						else {
                            				curTile.meeple = location;
							me.tigers--;
						   	 return place_tiger;
                       				 }
						
					}
					else if ( strID == "LJTJ-" ||
						  strID == "LJTJD" ||
						  strID == "LJLJ-" ||
					          strID == "LJJJ-" ||
					          strID == "LLJJ-"){
							if ( curTile.rotation == 0) location = 2;
							else if (curTile.rotation == 90 ) location = 4;
							else if ( curTile.rotation == 180 ) location = 8;
							else if ( curTile.rotation == 270 ) location = 6;
							else {
                               					curTile.meeple = location;
								me.tigers--;
							    	return place_tiger;
                           				 }
					}
					else {
						location = 5;
						curTile.meeple = location;
						me.tigers--;
						return place_tiger;
                   			 }
				}
			    }
			}
		return null;
	};
		
    
   }
