import java.util.*;

public class Tiles {
	private static String [][] tiles;
	static String tile0 = "tltj";
	static String tile1 = "jjjj";
	static String tile2 = "llll";
	static String tile3 = "tljt";
	static String tile4 = "tltt";
	static String tile5 = "jjjjd";
	static String tile6 = "jjjjd";
	static String tile7 = "jjjjd";
	static String tile8 = "jjjjd";
	static String tile9 = "jlll";
	static String tile10 = "jlll";
	static String tile11 = "jlll";
	static String tile12 = "jlll";
	static String tile13 = "tljtb";
	static String tile14 = "tljtb";
	static String tile15 = "tlttb";
	static String tile16 = "tlttb";
	static String tile17 = "jjtjd";
	static String tile18 = "jjtjd";	
	static String tile19 = "lljj";
	static String tile20 = "lljj";
	static String tile21 = "lljj";
	static String tile22 = "lljj";
	static String tile23 = "lljj";
	static String tile24 = "jltt";
	static String tile25 = "tllt";
	static String tile26 = "tllt";	
	static String tile27 = "tllt";
	static String tile28 = "tttt";
	static String tile29 = "jljl";
	static String tile30 = "jljl";
	static String tile31 = "jljl";
	static String tile32 = "jlttb";
	static String tile33 = "jlttb";
	static String tile34 = "tlltb";
	static String tile35 = "tlltb";
	static String tile36 = "tjtj";
	static String tile37 = "tjtj";
	static String tile38 = "tjtj";
	static String tile39 = "tjtj";
	static String tile40 = "tjtj";
	static String tile41 = "tjtj";
	static String tile42 = "tjtj";
	static String tile43 = "tjtj";
	static String tile44 = "ljlj";
	static String tile45 = "ljlj";
	static String tile46 = "ljlj";
	static String tile47 = "tltj";
	static String tile48 = "tltj";
	static String tile49 = "ljtj";
	static String tile50 = "tjjt";
	static String tile51 = "tjjt";
	static String tile52 = "tjjt";
	static String tile53 = "tjjt";
	static String tile54 = "tjjt";
	static String tile55 = "tjjt";
	static String tile56 = "tjjt";
	static String tile57 = "tjjt";
	static String tile58 = "tjjt";
	static String tile59 = "ljjj";
	static String tile60 = "ljjj";
	static String tile61 = "ljjj";
	static String tile62 = "ljjj";
	static String tile63 = "ljjj";
	static String tile64 = "tltjd";
	static String tile65 = "tltjd";
	static String tile66 = "ljtjd";
	static String tile67 = "ljtjd";
	static String tile68 = "tjtt";
	static String tile69 = "tjtt";
	static String tile70 = "tjtt";
	static String tile71 = "tjtt";
	static String tile72 = "jllj";
	static String tile73 = "jllj";
	static String tile74 = "tlll";
	static String tile75 = "tlll";
	static String tile76 = "tlll";
	
	
	public static void main(String[] args){
		int boardSize=77;
		tiles = new String[boardSize][boardSize];
		
//		//Randomly fills the "board" 5x5 matrix
//		for(int i=0;i<tiles.length;i++) {
//			for(int j=0;j<tiles.length;j++) {
//				Random rand = new Random();
//				tiles[i][j] = rand.nextInt(5);
//				System.out.print(tiles[i][j]);
//				System.out.print("\t");
//			}
//			System.out.println();
//	    }
		
		tiles[0][0]=tile1;
		tiles[0][1]=tile2;
		tiles[0][2]=tile3;
		tiles[0][3]=tile4;
		tiles[0][4]=tile5;
		tiles[1][0]=tile6;
		tiles[1][1]=tile7;
		tiles[1][2]=tile8;
		tiles[1][3]=tile9;
		tiles[1][4]=tile10;
		tiles[2][0]=tile11;
		tiles[2][1]=tile12;
		tiles[2][2]="jjjj";	//HARDCODED to TEST
		tiles[2][3]=tile14;
		tiles[2][4]=tile15;
		tiles[3][0]=tile16;
		tiles[3][1]="lllj";	//HARDCODED to TEST
		tiles[3][2]="jlll";	//HARDCODED to TEST
		tiles[3][3]="llll";	//HARDCODED to TEST
		tiles[3][4]=tile20;
		tiles[4][0]=tile21;
		tiles[4][1]="jljj"; //HARDCODED to TEST
		tiles[4][2]=tile23;
		tiles[4][3]=tile24;
		tiles[4][4]=tile25;
		
		tiles[3][10]="jlll"; //HARDCODED to TEST
		
		//Print out the board tiles INCLUDING null (empty board space)
		for(int i=0;i<tiles.length;i++) {
			for(int j=0;j<tiles.length;j++) {
				System.out.print(tiles[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		
		//HARDCODED to TEST x and y of where user would click (3,2) valid, (3,1) not valid
		int a = 3;
		int b = 1;	
		
		boolean validMove = checkPlace(a,b);
		
		if(validMove==true) { 
			System.out.println("Is this a valid placement? ");
			System.out.println(validMove);
		}
		
		while(validMove==false) {
			System.out.println("Is this a valid placement?");
			System.out.println(validMove);
			
			System.out.println("Before rotate");
			System.out.println(tiles[a][b]);
			
			String rotate = rotate(a,b);
			tiles[a][b] = rotate;
			
			System.out.println("After rotate");
			System.out.println(tiles[a][b]);
			
			System.out.println("\nIs this a valid placement now?");
			System.out.println("a is:" +a);
			System.out.println("b is:" +b);
			System.out.println(checkPlace(a,b));
			
			validMove = checkPlace(a,b);
		}
	}

	public static boolean checkPlace(int a, int b) {
		boolean left = false;
		boolean right = false;
		boolean top = false;
		boolean bottom = false;
		boolean valid = false;
		
		
		if( tiles[a-1][b]==null){
			top=true;
		}
		
		else if(tiles[a][b].charAt(0)==tiles[a-1][b].charAt(2)) {
			top = true;
		}
		
		if(tiles[a+1][b]==null){
			bottom = true;
		}
		
		else if(tiles[a][b].charAt(2)==tiles[a+1][b].charAt(0)) {
			bottom = true;
		}
		
		if(tiles[a][b+1]==null){
			right = true;
		}
		
		else if(tiles[a][b].charAt(1)==tiles[a][b+1].charAt(3)) {
			right = true;
		}
		
		if(tiles[a][b-1]==null){
			left = true;
		}
		
		else if(tiles[a][b].charAt(3)==tiles[a][b-1].charAt(1)) {
			left = true;
		}
		
		if(left==true && right==true && top==true && bottom==true){
			valid = true;
		}
		return valid;
		
	}
	
	
	//Allows user to rotate tile (does't affect boars, deer, etc.)
	public static String rotate(int a, int b) {
		char[] c = tiles[a][b].toCharArray();
		
		char temp = c[0];
		c[0] = c[1];
		c[1] = c[2];
		c[2] = c[3];
		c[3] = temp;

		tiles[a][b] = new String(c);
		
		return tiles[a][b];
	}
	
}