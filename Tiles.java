import java.util.*;

public class Tiles {
	private static String [][] tiles;
	static String tile1 = "jjjj";
	static String tile2 = "llll";
	static String tile3 = "tlgt";
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
	static String tile33 = "tlltb";
	static String tile34 = "tjtj";
	static String tile35 = "tjtj";
	static String tile36 = "tjtj";
	static String tile37 = "tjtj";
	static String tile38 = "tjtj";
	static String tile39 = "tjtj";
	static String tile40 = "tjtj";
	static String tile41 = "tjtj";
	static String tile42 = "ljlj";
	static String tile43 = "ljlj";
	static String tile44 = "ljlj";
	//1+2??
	static String tile45 = "tltj";
	
	static String tile46 = "ljtj";
	static String tile47 = "tjjt";
	static String tile48 = "tjjt";
	static String tile49 = "tjjt";
	static String tile50 = "tjjt";
	static String tile51 = "tjjt";
	static String tile52 = "tjjt";
	static String tile53 = "tjjt";
	static String tile54 = "tjjt";
	static String tile55 = "tjjt";
	static String tile56 = "ljjj";
	static String tile57 = "ljjj";
	static String tile58 = "ljjj";
	static String tile59 = "ljjj";
	static String tile60 = "ljjj";
	static String tile61 = "tltjd";
	static String tile62 = "tltjd";
	static String tile63 = "ljtjd";
	static String tile64 = "ljtjd";
	static String tile65 = "tjtt";
	static String tile66 = "tjtt";
	static String tile67 = "tjtt";
	static String tile68 = "tjtt";
	static String tile69 = "jllj";
	static String tile70 = "jllj";
	static String tile71 = "tlll";
	static String tile72 = "tlll";
	
	
	public static void main(String[] args){
		tiles = new String[5][5];
		
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
		tiles[2][2]="jjjj";
		tiles[2][3]=tile14;
		tiles[2][4]=tile15;
		tiles[3][0]=tile16;
		tiles[3][1]="lljj";
		tiles[3][2]="jlll";
		tiles[3][3]="llll";
		tiles[3][4]=tile20;
		tiles[4][0]=tile21;
		tiles[4][1]=tile22;
		tiles[4][2]=tile23;
		tiles[4][3]=tile24;
		tiles[4][4]=tile25;
		
		for(int i=0;i<tiles.length;i++) {
			for(int j=0;j<tiles.length;j++) {
				System.out.print(tiles[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		
		
		int a = 3;
		int b = 2;	
		boolean validMove = validPlace(a,b);
		
		System.out.println(validMove);
	}

	public static boolean validPlace(int a, int b) {
		boolean left = false;
		boolean right = false;
		boolean top = false;
		boolean bottom = false;
		boolean valid = false;
		
		//check if same tile
		System.out.println(tiles[3][2].charAt(0));
		
		
		if(tiles[a][b].charAt(0)==tiles[a-1][b].charAt(2)){
			top = true;
		}
		
		if(tiles[a][b].charAt(2)==tiles[a+1][b].charAt(0)){
			bottom = true;
		}
		
		if(tiles[a][b].charAt(1)==tiles[a][b+1].charAt(3)){
			right = true;
		}
		
		if(tiles[a][b].charAt(3)==tiles[a][b-1].charAt(1)){
			left = true;
		}
		
		if(left==true && right==true && top==true && bottom==true){
			valid = true;
		}
		return valid;
		
	}
	
}