import java.util.*;
import java.lang.*;

public class TilePlacement {
	static List<String> myList = new ArrayList<String>();
	private static String [][] board;
	private static int [][] tried;
	static int boardSize=3;
	static int x,y;
	static int row,col;
	static int side=0;
	static int tilesPlaced = 0;
	static int rotated=0;
	static boolean validMove;
	
  public static void main(String[] args){
		int totalTiles=77;
		board = new String[boardSize][boardSize];
		tried = new int[boardSize][boardSize];
		
	  	//click to place first tile
	    String tile0 = "TLTJ-";
	    board[(int) Math.ceil(boardSize/2)][(int) Math.ceil(boardSize/2)] = tile0;

	    //all tiles EXCLUDING first placement tile
		myList.add("JJJJ-");		//0 index
	    myList.add("LLL-");
	    myList.add("TLJT-");
	    myList.add("TLTT-");
	    myList.add("JJJJX");
	    myList.add("JJJJX");
	    myList.add("JJJJX");
	    myList.add("JJJJX");
	    myList.add("JLLL-");
	    myList.add("JLLL-");
	    myList.add("JLLL-");
	    myList.add("JLLL-");
	    myList.add("TLJTP");
	    myList.add("TLJTP");
	    myList.add("TLTTP");
	    myList.add("TLTTP");
	    myList.add("JJTJX");
	    myList.add("JJTJX");	//17 index
	    myList.add("LLJJ-");
	    myList.add("LLJJ-");
	    myList.add("LLJJ-");
	    myList.add("LLJJ-");
	    myList.add("LLJJ-");
	    myList.add("JLTT-");		//23 index
	    myList.add("TLLT-");
	    myList.add("TLLT-");
	    myList.add("TLLT-");
	    myList.add("TTTT-");
	    myList.add("JLJL-");
	    myList.add("JLJL-");
	    myList.add("JLJL-");
	    myList.add("JLTTB");
	    myList.add("JLTTB");
	    myList.add("TLLTB");
	    myList.add("TLLTB");
	    myList.add("TJTJ-");
	    myList.add("TJTJ-");
	    myList.add("TJTJ-");
	    myList.add("TJTJ-");
	    myList.add("TJTJ-");
	    myList.add("TJTJ-");
	    myList.add("TJTJ-");
	    myList.add("TJTJ-");
	    myList.add("LJLJ-");
	    myList.add("LJLJ-");
	    myList.add("LJLJ-");
	    myList.add("TLTJ-");		//46 index
	    myList.add("TLTJ-");
	    myList.add("LJTJ-");
	    myList.add("TJJT-");
	    myList.add("TJJT-");
	    myList.add("TJJT-");
	    myList.add("TJJT-");
	    myList.add("TJJT-");
	    myList.add("TJJT-");
	    myList.add("TJJT-");
	    myList.add("TJJT-");
	    myList.add("TJJT-");
	    myList.add("LJJJ-");
	    myList.add("LJJJ-");
	    myList.add("LJJJ-");
	    myList.add("LJJJ-");
	    myList.add("LJJJ-");
	    myList.add("TLTJD");
	    myList.add("TLTJD");
	    myList.add("LJTJD");
	    myList.add("LJTJD");
	    myList.add("TJTT-");
	    myList.add("TJTT-");
	    myList.add("TJTT-");
	    myList.add("TJTT-");
	    myList.add("JLLJ-");
	    myList.add("JLLJ-");
	    myList.add("TLLL-");
	    myList.add("TLLL-");
	    myList.add("TLLL-"); //75 index
	    
	    //Shuffle Tiles
	    Collections.shuffle(myList);

		
			//Show board for available spaces
			//Print out the board tiles INCLUDING null (empty board space)
			System.out.println("BEgins here");
			for(int i=0;i<board.length;i++) {
				for(int j=0;j<board.length;j++) {
					System.out.print(board[i][j]);
					System.out.print("\t");
				}
				System.out.println();
			}
			
			System.out.println("Tiles unshuffled:");
		    System.out.println(myList);
			
		    Collections.shuffle(myList);
		    System.out.println("Tiles shuffled:");
		    System.out.println(myList+ "\n");
		    
		    findTile();
		    
		    side=0;
			changeSide();
			
			validMove = checkPlace(row,col,tilesPlaced);
			System.out.println("is this a valid move? " + validMove);
			
			while(tilesPlaced<4){
				if(validMove==true) { //Seems to work!!!
					System.out.println("This is a valid placement!");
					
					//Places tile in appropriate spot
					board[row][col] = myList.get(tilesPlaced);
					rotated=0;  //resets times rotated since looking at new tile
					side=0; 	//starts it from first side since new tile
					tilesPlaced++;
					x=0;
					y=0;
			
					//prints new board
					for(int i=0;i<board.length;i++) {
						for(int j=0;j<board.length;j++) {
							System.out.print(board[i][j]);
							System.out.print("\t");
						}
						System.out.println();
					}
					
					validMove=false;
					findTile();
					changeSide();
					validMove = checkPlace(row,col,tilesPlaced);
					
				}
				
				while(validMove==false && rotated<4) {
					System.out.println("Not a valid move");
					System.out.println("Before rotate");
					System.out.println(myList.get(tilesPlaced));
				
					rotate(row,col,tilesPlaced);
					rotated++;
						
					System.out.println("After rotate");
					System.out.println(myList.get(tilesPlaced));
					System.out.println("How many times rotated? " + rotated);
						
					System.out.println("Is this a valid placement now?");
					validMove = checkPlace(row,col,tilesPlaced);
					System.out.println(validMove);
				}
				
				
				if(validMove==false && rotated==4 && side<4){
					side++;
					rotated=0;
					System.out.println("Switching sides now to side "+side);
					changeSide();
					
					System.out.println("Is this a valid placement now?");
					validMove = checkPlace(row,col,tilesPlaced);
					System.out.println(validMove);
				}
				
				if(validMove==false && side==4){
					side=0;
					rotated=0;
					System.out.println("Looking for NEW tile to place next to.");
					
					findTile();
					changeSide();
					
					System.out.println("Is this a valid placement now?");
					validMove = checkPlace(row,col,tilesPlaced);
					System.out.println(validMove);
				}
			}
  	}
  
  	public static void changeSide(){
		//Check all sides
		switch (side) {
        case 0:	//top
        		row=x-1;
        		col=y;
        		
        		if(row<0){
        			side++;
        			changeSide();
        		}
        		break;
        case 1:	//right
        		row=x;
        		col=y+1;
        		
        		if(col>boardSize-1){
        			side++;
        			changeSide();
        		}
                break;
        case 2:	//bottom
        		row=x+1;
        		col=y;
        		
        		if(row>boardSize-1){
        			side++;
        			changeSide();
        		}
                break;
        case 3:	//left
        		row=x;
        		col=y-1;
                
                if(col<0){
                	side++;
                	changeSide();
                }
                break;
        case 4: //check 4 sides need to look for another tile to attach to
				side=0;
				rotated=0;
				System.out.println("Looking for NEW tile to place next to.");
				
				findTile();
				changeSide();
				
				System.out.println("Is this a valid placement now?");
				validMove = checkPlace(row,col,tilesPlaced);
				System.out.println(validMove);
				break;
		}

		if(board[row][col] != null) {
			side++;
			changeSide();
		}
		
		//Just to test
		System.out.println("Looking to place at (" +row+ ", "+col+")");
		System.out.println("Looking at tile "+myList.get(tilesPlaced));
  	}
  	
  	public static void findTile(){
  		outerloop:
  		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board.length;j++) {
				if (board[i][j] !=null && tried[i][j]!=1) {
					x=i;
					y=j;
					break outerloop;
				}
			}
		}

  		System.out.println("Tile you need to place around (" +x+", "+y +")");
  		tried[x][y]=1;
  	}

	public static boolean checkPlace(int a, int b, int tilesPlaced) {
		boolean left = false;
		boolean right = false;
		boolean top = false;
		boolean bottom = false;
		boolean valid = false;
		
		if(a==0){
			top=true;
		}

		else {
			if( board[a-1][b]==null){
				top=true;
			}
			
			else if(myList.get(tilesPlaced).charAt(0)==board[a-1][b].charAt(2)) {
				top = true;
			}
		}
		
		if(a==boardSize-1){
			bottom=true;
		}
		
		else {
			if(board[a+1][b]==null){
				bottom = true;
			}
			
			else if(myList.get(tilesPlaced).charAt(2)==board[a+1][b].charAt(0)) {
				bottom = true;
			}
		}
		
		if(b==boardSize-1){
			right=true;
		}
		
		else {
			if(board[a][b+1]==null){
				right = true;
			}
			
			else if(myList.get(tilesPlaced).charAt(1)==board[a][b+1].charAt(3)) {
				right = true;
			}
		}
		
		if(b==0){
			left=true;
		}
		
		else {
			if(board[a][b-1]==null){
				left = true;
			}
			
			else if(myList.get(tilesPlaced).charAt(3)==board[a][b-1].charAt(1)) {
				left = true;
			}
		}
		
		if(left==true && right==true && top==true && bottom==true){
			valid = true;
		}
		
		return valid;
	}
	
	
	//Allows user to rotate tile (does't affect boars, deer, etc.)
	public static void rotate(int a, int b, int tilesPlaced) {
		String tileRotated;
		char[] c = myList.get(tilesPlaced).toCharArray();
		
		char temp = c[0];
		c[0] = c[1];
		c[1] = c[2];
		c[2] = c[3];
		c[3] = temp;

		tileRotated = new String(c);
		
		myList.set(tilesPlaced,tileRotated);
	}
  
}