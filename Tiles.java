import java.util.*;

public class Tiles {
	static List<String> myList = new ArrayList<String>();
	
	private static String [][] board;
	
  public static void main(String[] args){
	  	//click to place first tile
	    String tile0 = "tltj";

	    //all tiles EXCLUDING first placement tile
		myList.add("jjjj");		//0 index
	    myList.add("llll");
	    myList.add("tljt");
	    myList.add("tltt");
	    myList.add("jjjjd");
	    myList.add("jjjjd");
	    myList.add("jjjjd");
	    myList.add("jjjjd");
	    myList.add("jlll");
	    myList.add("jlll");
	    myList.add("jlll");
	    myList.add("jlll");
	    myList.add("tljtb");
	    myList.add("tljtb");
	    myList.add("tlttb");
	    myList.add("tlttb");
	    myList.add("jjtjd");
	    myList.add("jjtjd");	//17 index
	    myList.add("lljj");
	    myList.add("lljj");
	    myList.add("lljj");
	    myList.add("lljj");
	    myList.add("lljj");
	    myList.add("jltt");		//23 index
	    myList.add("tllt");
	    myList.add("tllt");
	    myList.add("tllt");
	    myList.add("tttt");
	    myList.add("jljl");
	    myList.add("jljl");
	    myList.add("jljl");
	    myList.add("jlttb");
	    myList.add("jlttb");
	    myList.add("tlltb");
	    myList.add("tlltb");
	    myList.add("tjtj");
	    myList.add("tjtj");
	    myList.add("tjtj");
	    myList.add("tjtj");
	    myList.add("tjtj");
	    myList.add("tjtj");
	    myList.add("tjtj");
	    myList.add("tjtj");
	    myList.add("ljlj");
	    myList.add("ljlj");
	    myList.add("ljlj");
	    myList.add("tltj");		//46 index
	    myList.add("tltj");
	    myList.add("ljtj");
	    myList.add("tjjt");
	    myList.add("tjjt");
	    myList.add("tjjt");
	    myList.add("tjjt");
	    myList.add("tjjt");
	    myList.add("tjjt");
	    myList.add("tjjt");
	    myList.add("tjjt");
	    myList.add("tjjt");
	    myList.add("ljjj");
	    myList.add("ljjj");
	    myList.add("ljjj");
	    myList.add("ljjj");
	    myList.add("ljjj");
	    myList.add("tltjd");
	    myList.add("tltjd");
	    myList.add("ljtjd");
	    myList.add("ljtjd");
	    myList.add("tjtt");
	    myList.add("tjtt");
	    myList.add("tjtt");
	    myList.add("tjtt");
	    myList.add("jllj");
	    myList.add("jllj");
	    myList.add("tlll");
	    myList.add("tlll");
	    myList.add("tlll"); //75 index
	    
	    //Pre-shuffle
	    System.out.println("Tiles unshuffled:");
	    System.out.println(myList);
		
	    Collections.shuffle(myList);
	    System.out.println("Tiles shuffled:");
	    System.out.println(myList+ "\n");
		
		int boardSize=5;
		int totalTiles=77;
		board = new String[boardSize][boardSize];
		int tilesPlaced = 0;
		
		while(tilesPlaced<totalTiles) {
			//Show board for available spaces
			//Print out the board tiles INCLUDING null (empty board space)
			for(int i=0;i<board.length;i++) {
				for(int j=0;j<board.length;j++) {
					System.out.print(board[i][j]);
					System.out.print("\t");
				}
				System.out.println();
			}
			
			Scanner scanner = new Scanner(System.in);
		    System.out.println("Where would you like to place your tile: " + myList.get(tilesPlaced));
		    System.out.println("Row?");
		    int row = scanner.nextInt();
		    System.out.println("Column?");
		    int col = scanner.nextInt();
		    
			
			/*
			board[0][0]=myList.get(0);
			board[0][1]=myList.get(1);
			board[0][2]=myList.get(2);
			board[0][3]=myList.get(3);
			board[0][4]=myList.get(4);
			board[1][0]=myList.get(5);
			board[1][1]=myList.get(6);
			board[1][2]=myList.get(7);
			board[1][3]=myList.get(8);
			board[1][4]=myList.get(9);
			board[2][0]=myList.get(10);
			board[2][1]="jlll"; //HARDCODED to TEST
			board[2][2]=myList.get(12);	
			board[2][3]=myList.get(13);
			board[2][4]=myList.get(14);
			board[3][0]="tlttb";
			board[3][1]="lllj";	//HARDCODED to TEST
			board[3][2]="jlll";	//HARDCODED to TEST
			board[3][3]=myList.get(18);	
			board[3][4]=myList.get(19);
			board[4][0]=myList.get(20);
			board[4][1]="jljj"; //HARDCODED to TEST
			board[4][2]=myList.get(22);
			board[4][3]=myList.get(23);
			board[4][4]=myList.get(24);
			
			board[3][10]="jlll"; //HARDCODED to TEST
			
			*/				
			
			boolean validMove = checkPlace(row,col);
			
			if(validMove==true) { 
				System.out.println("Is this a valid placement? ");
				System.out.println(validMove);
				
				//Place tile
				System.out.println("Place tile? (Y/N)");
				String place = scanner.next();
				System.out.println("You selected: " + place);
				
				if(place.equals("Y") || place.equals("y")) {
					System.out.println("You selected yes");
			
					board[row][col] = myList.get(tilesPlaced);
					tilesPlaced++;
				}
				
				else {
					System.out.println("You selected no");
					
				}
			}
			
			while(validMove==false) {
				System.out.println("Is this a valid placement?");
				System.out.println(validMove);
				
				System.out.println("Before rotate");
				System.out.println(board[row][col]);
				
				String rotate = rotate(row,col);
				board[row][col] = rotate;
				
				System.out.println("After rotate");
				System.out.println(board[row][col]);
				
				System.out.println("\nIs this a valid placement now?");
				System.out.println("a is:" + row);
				System.out.println("b is:" + col);
				System.out.println(checkPlace(row,col));
				
				validMove = checkPlace(row,col);
			}
		}
  	}

	public static boolean checkPlace(int a, int b) {
		boolean left = false;
		boolean right = false;
		boolean top = false;
		boolean bottom = false;
		boolean valid = false;
		
		
		if( board[a-1][b]==null){
			top=true;
		}
		
		else if(board[a][b].charAt(0)==board[a-1][b].charAt(2)) {
			top = true;
		}
		
		if(board[a+1][b]==null){
			bottom = true;
		}
		
		else if(board[a][b].charAt(2)==board[a+1][b].charAt(0)) {
			bottom = true;
		}
		
		if(board[a][b+1]==null){
			right = true;
		}
		
		else if(board[a][b].charAt(1)==board[a][b+1].charAt(3)) {
			right = true;
		}
		
		if(board[a][b-1]==null){
			left = true;
		}
		
		else if(board[a][b].charAt(3)==board[a][b-1].charAt(1)) {
			left = true;
		}
		
		if(left==true && right==true && top==true && bottom==true){
			valid = true;
		}
		return valid;
		
	}
	
	
	//Allows user to rotate tile (does't affect boars, deer, etc.)
	public static String rotate(int a, int b) {
		char[] c = board[a][b].toCharArray();
		
		char temp = c[0];
		c[0] = c[1];
		c[1] = c[2];
		c[2] = c[3];
		c[3] = temp;

		board[a][b] = new String(c);
		
		return board[a][b];
	}
  
}