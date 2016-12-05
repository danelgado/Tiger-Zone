import java.util.*;
import java.io.*;

public class Animals {

	//constructuor
	public Animals() {
	}
	
	//place_croc checks most recent tiles within proximity to recent one placed to check:
	//surrounding tiles for opposing tiger placement and surrounding tiles for unique prey
	//returns String CROCODILE to concatenate with final return message or null if a crocodile 
	//should not be placed.

	public String place_croc(Player me, Tile curTile, ArrayList<Tile> stack) {
		
		for(int i=0; i< stack.size(); i++ ) {
			boolean tiger_present = false;
			boolean prey_present = false;
			Tile existingTile = stack.get(i);
			char prey = existingTile.tileID[4];
			if (  existingTile.meeple > 0 || existingTile.meeple < 10 ) {
				tiger_present = true;
			}
			if (prey == 'D' || 
				prey == 'd' || 
				prey == 'P' || 
				prey == 'p' || 
				prey == 'B' ||
				prey == 'b') {
					prey_present = true;
			}
			if (tiger_present && prey_present && !existingTile.ownTile) {
				curTile.meeple = -1;
				me.crocodiles--;
				return " CROCODILE";
           	}
			
		};
		return null;
	};
	
	//runs through most recent tiles placed
	//ensures tile is not Player's tile and if a tiger is present, that it is not the Player's tiger
	//priority for tiger placement goes to Dens
	//then priority goes to Lakes 
	//then priority goes to Game-trails and Jungles
		
		
	public String place_tiger(Player me, Tile curTile, ArrayList<Tile> stack){
		boolean own; 
		int location = 0;
		String place_tiger = " TIGER ";
		// priority 1 = Den 
		if (curTile.tileID[4] == 'X') {
			location = 5;
			me.tigers--;
			return place_tiger + location;
		}
		// priority 2 = Lake
		else {
			for(int i=0; i< stack.size(); i++){
				Tile existingTile = stack.get(i);
				String strID = String.valueOf(existingTile.tileID);
				if( !existingTile.ownTile && (existingTile.meeple > 9 || existingTile.meeple < 1) ) {
					if (curTile.tileID[3] == 'L' ) {
						location= 5;
						curTile.meeple = location;
						me.tigers--;
						return place_tiger + location;
					} else if(strID == "TLJT-" ||
							strID == "TLJTP" ||
							strID == "JLTT-" ||
							strID == "JLTT-" ||
							strID == "JLTTB" ||
							strID == "TLTJ-" ||
							strID == "TLTJD" ||
							strID == "TLTT-" ||
							strID == "TLTTP" ||
							strID == "JLLJ-") {
						if (curTile.rotation == 0) location = 6; 
						else if (curTile.rotation == 90) location = 2;
						else if (curTile.rotation == 180) location = 4;
						else if (curTile.rotation == 270) location = 8;
						curTile.meeple = location;
						me.tigers--;
						return place_tiger + location;		
					} else if (strID == "TLLT-" || strID == "TLLTB") {
						if (curTile.rotation == 0)  location = 9; 
						else if (curTile.rotation == 90) location = 3;
						else if (curTile.rotation == 180) location = 1;
						else if (curTile.rotation == 270) location = 7;
						curTile.meeple = location;
						me.tigers--;
						return place_tiger + location;
					} else if (strID == "LJTJ-" ||
						strID == "LJTJD" ||
						strID == "LJLJ-" ||
						strID == "LJJJ-" ||
						strID == "LLJJ-") {
						if (curTile.rotation == 0) location = 2;
						else if (curTile.rotation == 90) location = 4;
						else if (curTile.rotation == 180) location = 8;
						else if (curTile.rotation == 270) location = 6;
						curTile.meeple = location;
						me.tigers--;
						return place_tiger + location;
					} else {
						location = 5;
						curTile.meeple = location;
						me.tigers--;
						return place_tiger + location;
					}
			    }
			}
		}
		return null;
	}
	
	public void meeple_return(Tile curTile, ArrayList<Tile> stack, Player me ){
		//check if Den is completed
		if( (curTile.tileID[4] == 'D') && (curTile.meeple == 5) ){
		    int X = curTile.xCoord;
		    int Y = curTile.yCoord;
		    int _count= 0;
		    for (int i = 0; i < stack.size(); i++) {
			Tile existingTile = stack.get(i); 
			int newX = existingTile.xCoord;
			int newY = existingTile.yCoord;
			if ( (newX == X-1) && ( (newY == Y+1) || (newY == Y) || (newY == Y+1) ) ) _count++;
			else if ( (newX == X) && ( (newY == Y+1) || (newY == Y) || (newY == Y+1) ) ) _count++;
			else if ( (newX == X+1) && ( (newY == Y+1) || (newY == Y) || (newY == Y+1) ) ) _count++;
		    }
		    if ( _count == 8 ) me.tigers++;
		}
	};
		
    
}