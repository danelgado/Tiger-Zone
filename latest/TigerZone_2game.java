import java.net.*;
import java.io.*;
import java.util.*;

public class TigerZone_2game {
    // board# is an ArrayList that will hold all tiles placed in the game thus far.
    static Tile centerTile = new Tile();
    static ArrayList<Tile> stack1 = new ArrayList<Tile>();
    static ArrayList<Tile> board1 = new ArrayList<Tile>();
    static ArrayList<Tile> stack2 = new ArrayList<Tile>();
    static ArrayList<Tile> board2 = new ArrayList<Tile>();
    static animals animalPlacement = new animals();
    static player me1 = new player();
    static player me2 = new player();
  

    public static void main(String[] args) {
        String gameid1 = NULL;
        String gameid2 = NULL;
      
    	ArrayList<Tile> stack = new ArrayList<Tile>();
        ArrayList<Tile> board = new ArrayList<Tile>();
    	player me = new player();
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String tournamentPassword = args[2];
        String user = args[3];
        String userPassword = args[4];
        String opponent = null;

        Socket kkSocket;
        BufferedReader in = null;
        String fromServer;
        String fromUser = null;

        try {
            kkSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);

                if (fromServer.equals("THANK YOU FOR PLAYING! GOODBYE"))
                    break;

                if (fromServer.equals("THIS IS SPARTA!")) {
                    fromUser = "JOIN " + tournamentPassword;
                } else if (fromServer.equals("HELLO!")) {
                    fromUser = "I AM " + user + " " + userPassword;
                } else if (fromServer.startsWith("WELCOME")) {
                    fromUser = null;
                } else if (fromServer.startsWith("NEW CHALLENGE")) {
                    fromUser = null;
                } else if (fromServer.startsWith("BEGIN ROUND")) {
                    fromUser = null;
                } else if (fromServer.startsWith("YOUR OPPONENT IS")) {
                    fromUser = null;
                    String[] message = fromServer.split(" ");
                    opponent = message[4];
                } else if (fromServer.startsWith("STARTING TILE IS")) {
                    String[] message = fromServer.split(" ");
                    centerTile.tileID = message[3].toCharArray();
                    centerTile.xCoord = Integer.parseInt(message[5]);
                    centerTile.yCoord = Integer.parseInt(message[6]);
                    centerTile.rotation = Integer.parseInt(message[7]);
                    stack1.clear();
                    board1.clear();
                    stack2.clear();
                    board2.clear();
                    stack1.add(centerTile);
                    board1.add(centerTile);
                    stack2.add(centerTile);
                    board2.add(centerTile);
                    fromUser = null;
                } else if (fromServer.startsWith("THE REMAINING")) {
                    String[] message = fromServer.split(" ");
                    for (int i = 6; i < 6 + Integer.parseInt(message[2]); i++) {
                        Tile newTile = new Tile();
                        newTile.tileID = message[i].toCharArray();
                        stack1.add(newTile);
                        stack2.add(newTile);
                    }
                    // plan moves
                    //parsing "reading" from server
                    } else if (fromServer.startsWith("GAME")) {
                    fromUser = null;
                    String[] message = fromServer.split(" ");
                    String gameID = message[1];
                    if (message[5] == opponent)
                        parseMove(gameID, message);
                    } else if (fromServer.startsWith("MAKE YOUR MOVE")) {
                    String[] message = fromServer.split(" ");
                    int moveId = Integer.parseInt(message[10]);
                    String tileId = message[12];
                    if ( gameid1 == null){
                        gameid1 = message[5];
                    }
                    else if ( gameid2 == null){
                        gameid2 = messsage[5];
                    }
                    else if( message[5] == gameid1){
                        stack = stack1;
                        me = me1;
                        board = board1;
                        gameId = gameid1;

                    }
                    else {
                    	stack = stack2;
                    	me = me2;
                        board  = board2;
                        gameId = gameid2;
                    }
                    fromUser = makeMove(stack, board, me, gameId, moveId, tileId, centerTile);
                }

                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch (Exception e) {System.out.println(e);}
    }

    public static void parseMove(String gameid, String[] message) {
        //	String gameID = message[1];
        String opponent = message[5];
        char[] placedTile = message[7].toCharArray();
        int x = Integer.parseInt(message[9]);
        int y = Integer.parseInt(message[10]);
        int rotation = Integer.parseInt(message[11]);
        String meeple = message[12];
        int meepleLoc = 0;
        if (meeple.equals("TIGER")) {
            meepleLoc = Integer.parseInt(message[13]);
        } else if (meeple.equals("CROCODILE"))
            meepleLoc = -1;

        Tile curTile = new Tile();
        if( gameid == gameid1 ){
	        for (int i = 0; i < stack1.size(); i++) {
	            if (stack1.get(i).tileID == placedTile) {
	                curTile = stack1.get(i);
	                break;
	            }
	        }
	    }
	    else{
	    	for (int i = 0; i < stack2.size(); i++) {
	            if (stack2.get(i).tileID == placedTile) {
	                curTile = stack2.get(i);
	                break;
	            }
	        }
	    }
	        curTile.xCoord = x;
	        curTile.yCoord = y;
	        curTile.rotation = rotation;
	        curTile.meeple = meepleLoc;

    }

    public static String makeMove(ArrayList<Tile> stack, ArrayList<Tile> board, player me, String game, int move, String tileId, Tile curTile) {

        char[] tileToPlace = tileId.toCharArray();

        String replyMessage = "GAME " + game + " MOVE " + move + " PLACE " + tileId + " AT ";

        String position = null;
        int xCoord = 0;
        int yCoord = 0;
        int rotation = 0;

        if (curTile.north == null
                && curTile.tileID[0] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 0)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 0;
            position = "north";
        } else if (curTile.north == null
                && curTile.tileID[0] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 90)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 90;
            position = "north";
        } else if (curTile.north == null
                && curTile.tileID[0] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 180)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 180;
            position = "north";
        } else if (curTile.north == null
                && curTile.tileID[0] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 270)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 270;
            position = "north";
        }

        else if (curTile.east == null
                && curTile.tileID[1] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 0)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "east";
        } else if (curTile.east == null
                && curTile.tileID[1] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 90)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "east";
        } else if (curTile.east == null
                && curTile.tileID[1] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 180)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "east";
        } else if (curTile.east == null
                && curTile.tileID[1] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 270)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "east";
        }

        else if (curTile.south == null
                && curTile.tileID[2] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 0)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 0;
            position = "south";
        } else if (curTile.south == null
                && curTile.tileID[2] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 90)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 90;
            position = "south";
        } else if (curTile.south == null
                && curTile.tileID[2] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 180)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 180;
            position = "south";
        } else if (curTile.south == null
                && curTile.tileID[2] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 270)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 270;
            position = "south";
        }

        else if (curTile.west == null
                && curTile.tileID[3] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 0)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "west";
        } else if (curTile.west == null
                && curTile.tileID[3] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 90)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "west";
        } else if (curTile.west == null
                && curTile.tileID[3] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 180)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "west";
        } else if (curTile.west == null
                && curTile.tileID[3] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 270)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "west";
        }

        if (xCoord != 0 || yCoord != 0) {
            Tile newTile = new Tile();
            newTile.tileID = tileToPlace;
            newTile.meeple = 0;
            newTile.xCoord = xCoord;
            newTile.yCoord = yCoord;
            newTile.rotation = rotation;
            newTile.ownTile = true;
            board.add(newTile);
            if (position.equals("north"))
                curTile.north = newTile;
            else if (position.equals("east"))
                curTile.east = newTile;
            else if (position.equals("south"))
                curTile.south = newTile;
            else if (position.equals("west"))
                curTile.west = newTile;

            replyMessage += newTile.xCoord + " " + newTile.yCoord + " " + newTile.rotation;

        } else {
            replyMessage = makeMove(stack, board, me, game, move, tileId, curTile.north);

            if (replyMessage == null) {
                replyMessage = makeMove(stack, board, me, game, move, tileId, curTile.east);
            }
            if (replyMessage == null) {
                replyMessage = makeMove(stack, board, me, game, move, tileId, curTile.south);
            }
            if (replyMessage == null) {
                replyMessage = makeMove(stack, board, me, game, move, tileId, curTile.west);
            }
        }

        if (replyMessage != null) {
            String meeplePlace = null;
            if (me.crocodiles > 0)
                meeplePlace = animalPlacement.place_croc(me, curTile, stack);
            if (meeplePlace == null && me.tigers > 0)
                meeplePlace = animalPlacement.place_tiger(me, curTile, stack);
            if (meeplePlace == null)
                meeplePlace = " NONE";
            animalPlacement.meeple_return(curTile, stack, me);
            return replyMessage += meeplePlace;
        } else {
            
            return null;
        }
    }

    public static boolean hasValidProx(ArrayList<Tile> stack, char[] tileIdToPlace, int x, int y, int rotation) {

        boolean isValid = true;

        for (int i = 0; i < stack.size(); i++) {
            Tile existingTile = stack.get(i);

            String tid = "";
            for (int j = 0; j < existingTile.tileID.length; j++) {
                tid += existingTile.tileID[j];
            }

            // north
            if (existingTile.xCoord == x && existingTile.yCoord == y) {
                if (rotation == 0)
                    if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[0])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[3] != tileIdToPlace[0])
                        return false;
                    else if (existingTile.rotation == 180 && existingTile.tileID[0] != tileIdToPlace[0])
                        return false;
                    else if (existingTile.rotation == 270 && existingTile.tileID[1] != tileIdToPlace[0])
                        return false;
                else if (rotation == 90)
                    if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[1])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[3] != tileIdToPlace[1])
                        return false;
                    else if (existingTile.rotation == 180 && existingTile.tileID[0] != tileIdToPlace[1])
                        return false;
                    else if (existingTile.rotation == 270 && existingTile.tileID[1] != tileIdToPlace[1])
                        return false;
                else if (rotation == 180)
                    if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[2])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[3] != tileIdToPlace[2])
                        return false;
                    else if (existingTile.rotation == 180 && existingTile.tileID[0] != tileIdToPlace[2])
                        return false;
                    else if (existingTile.rotation == 270 && existingTile.tileID[1] != tileIdToPlace[2])
                        return false;
                else if (rotation == 270)
                    if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[3] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 180 && existingTile.tileID[0] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 270 && existingTile.tileID[1] != tileIdToPlace[3])
                        return false;
            }
            // east
            if (existingTile.xCoord == x && existingTile.yCoord == y) {
                if (rotation == 0)
                    if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[1])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[0] != tileIdToPlace[1])
                        return false;
                    else if (existingTile.rotation == 180 && existingTile.tileID[1] != tileIdToPlace[1])
                        return false;
                    else if (existingTile.rotation == 270 && existingTile.tileID[2] != tileIdToPlace[1])
                        return false;
                else if (rotation == 90)
                    if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[2])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[0] != tileIdToPlace[2])
                        return false;
                    else if (existingTile.rotation == 180 && existingTile.tileID[1] != tileIdToPlace[2])
                        return false;
                    else if (existingTile.rotation == 270 && existingTile.tileID[2] != tileIdToPlace[2])
                        return false;
                else if (rotation == 180)
                    if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[0] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 180 && existingTile.tileID[1] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 270 && existingTile.tileID[2] != tileIdToPlace[3])
                        return false;
                else if (rotation == 270)
                    if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[0] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 180 && existingTile.tileID[1] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 270 && existingTile.tileID[2] != tileIdToPlace[3])
                        return false;
            }
            // south
            if (existingTile.xCoord == x && existingTile.yCoord == y) {
                if (rotation == 0)
                    if (existingTile.rotation == 0 && existingTile.tileID[0] != tileIdToPlace[2])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[1] != tileIdToPlace[2])
                        return false;
                    else if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[2])
                        return false;
                    else if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[2])
                        return false;
                else if (rotation == 90)
                    if (existingTile.rotation == 0 && existingTile.tileID[0] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[1] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[3])
                        return false;
                else if (rotation == 180)
                    if (existingTile.rotation == 0 && existingTile.tileID[0] != tileIdToPlace[0])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[1] != tileIdToPlace[0])
                        return false;
                    else if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[0])
                        return false;
                    else if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[0])
                        return false;
                else if (rotation == 270)
                    if (existingTile.rotation == 0 && existingTile.tileID[0] != tileIdToPlace[1])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[1] != tileIdToPlace[1])
                        return false;
                    else if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[1])
                        return false;
                    else if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[1])
                        return false;
            }
            // west
            if (existingTile.xCoord == x && existingTile.yCoord == y) {
                if (rotation == 0)
                    if (existingTile.rotation == 0 && existingTile.tileID[1] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[2] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 180 && existingTile.tileID[3] != tileIdToPlace[3])
                        return false;
                    else if (existingTile.rotation == 270 && existingTile.tileID[0] != tileIdToPlace[3])
                        return false;
                else if (rotation == 90)
                    if (existingTile.rotation == 0 && existingTile.tileID[1] != tileIdToPlace[0])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[2] != tileIdToPlace[0])
                        return false;
                    else if (existingTile.rotation == 180 && existingTile.tileID[3] != tileIdToPlace[0])
                        return false;
                    else if (existingTile.rotation == 270 && existingTile.tileID[0] != tileIdToPlace[0])
                        return false;
                else if (rotation == 180)
                    if (existingTile.rotation == 0 && existingTile.tileID[1] != tileIdToPlace[1])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[2] != tileIdToPlace[1])
                        return false;
                    else if (existingTile.rotation == 180 && existingTile.tileID[3] != tileIdToPlace[1])
                        return false;
                    else if (existingTile.rotation == 270 && existingTile.tileID[0] != tileIdToPlace[1])
                        return false;
                else if (rotation == 270)
                    if (existingTile.rotation == 0 && existingTile.tileID[1] != tileIdToPlace[2])
                        return false;
                    else if (existingTile.rotation == 90 && existingTile.tileID[2] != tileIdToPlace[2])
                        return false;
                    else if (existingTile.rotation == 180 && existingTile.tileID[3] != tileIdToPlace[2])
                        return false;
                    else if (existingTile.rotation == 270 && existingTile.tileID[0] != tileIdToPlace[2])
                        return false;
            }
        }

        return isValid;
    }

    public static void output(String s) {
        System.out.println(s);
    }

    public static Tile tileExplorer(ArrayList<Tile> board, int x, int y) {
      /*
        Logic:
          1. Iterate through ArrayList until tile with matching x/y coordinates is
              found. If no tile is found, return null or take proper action.

        Pls help me test.
      */
      Iterator<Tile> bItr = board.iterator();
      while (bItr.hasNext()){
        Tile curTile = bItr.next();
        if (curTile.xCoord == x && curTile.yCoord == y){ return curTile; }
      }
      return null;
    }

    public static char[] Rotate(Tile t){
      if (t == null){ return null; }

      int len = t.tileID.length;
      char[] result = new char[len];
      int rotCount = t.rotation/90;
      ArrayList<Character> tid = new ArrayList<Character>();

      for (int i = 0; i < len; i++){
        tid.add(t.tileID[i]);
      }

      char temp;
      while(rotCount > 0){
        temp = tid.remove(0);
        tid.add(3, temp);
        rotCount--;
      }

      for (int i = 0; i < len; i++){
        result[i] = tid.get(i).charValue();
      }
      return result;
      /*
        This method is used to simulate tile rotation by using a tileID that
          represents the rotated tile. This method does not change the orientation of
          the game object, but instead returns a tileID that represents the cardinally
          accurate form of the tile according to its rotation value.

        Logic
          1. Cast the chars of tileID as ASCII values and place them in an ArrayList.
          2. Calculate the magnitude of the rotation as the quotient of the tile
              rotation value and the value of quarter rotation (90).
          3. Remove the head of the ASCII list and replaces it as the 4th element.
              ArrayList.add(int i, E element) shifts the previous 4th element and all
              subsequent elements to the right (adds 1 to the index), so this preserves
              the tileID syntax. This single action is a 90 degree rotation and is
              repeated x times, where x is the rotation magnitude determined in 2.
          4. Recast ASCII values as char and add them in order to the char array to
              be returned.

              Example: 1 counterclockwise rotation of tile TLLTB.

                TLLTB -> LLTB -> LLTTB

                            -----------
                           |t  trail   |
                           |r         l|
                           |a   bu    a|
                           |i         k|
                           |l  lake   e|
                            -----------
                                VV
                      1 COUNTERCLOCKWISE ROTATION
                                VV
                            -----------
                           |t  lake    |
                           |r         l|
                           |a   bu    a|
                           |i         k|
                           |l  trail  e|
                            -----------
      */
    }

    public static Boolean isValidMove(ArrayList<Tile> board, Tile t){
      if (t == null){ return false; }

      int x = t.xCoord;
      int y = t.yCoord;

      Tile north = tileExplorer(board, x, y+1);
      Tile east = tileExplorer(board, x+1, y);
      Tile south = tileExplorer(board, x, y-1);
      Tile west = tileExplorer(board, x-1, y);

      char[] tID = Rotate(t);
      char[] nID = Rotate(north);
      char[] eID = Rotate(east);
      char[] sID = Rotate(south);
      char[] wID = Rotate(west);

      if (north == null && east == null && south == null && west == null){
        return false;
      }
      else if ( (tID[0] == nID[2] || north == null) &&
                (tID[1] == eID[3] || east == null) &&
                (tID[2] == sID[0] || south == null) &&
                (tID[3] == wID[1] || west == null) ){ return true; }
      else{ return false; }

      /*
        Checks tiles located one unit in each cardinal direction to see if the
          placement of the current tile (t) is valid at the location (x, y) with
          rotation r.

        If all cardinal tiles are null, then valid placement is not possible.

        If at least 1 cardinal tile is non-null, the non-null tiles are checked for
          edge agreement - the touching edges must be of the same type.
      */
    }

    public static void placeTile(ArrayList<Tile> board, char[] tID, int x, int y, int r) {
      Tile check = tileExplorer(board, x, y);
      if (check == null){
        Tile newTile = new Tile();
        newTile.tileID = tID;
        newTile.xCoord = x;
        newTile.yCoord = y;
        newTile.rotation = r;
        if (isValidMove(board, newTile)){
          board.add(newTile);
        }
      }
      return;

      /*
        Creates tile tID at location (x, y) with rotation r.

        1. Check if there is a tile already located at intended coordinates (x, y)
              case (check == null):
                a) Create a new Tile object with the corresponding input parameters
                b) If move is valid, place the tile on the board.
      */
    }

}
