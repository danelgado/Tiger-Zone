import java.net.*;
import java.io.*;
import java.util.*;

public class TigerZone {
    // board# is an ArrayList that will hold all tiles placed in the game thus far.
    static Tile centerTile = new Tile();
    static ArrayList<Tile> stack1 = new ArrayList<Tile>();
    static ArrayList<Tile> stack2 = new ArrayList<Tile>();
    static Animals animalPlacement = new Animals();
    static Player me1 = new Player();
    static Player me2 = new Player();
    static String gameId1 = null;
    static String gameId2 = null;
    
    public static void main(String[] args) {
        
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String tournamentPassword = args[2];
        String user = args[3];
        String userPassword = args[4];
        String opponent = null;
        String moveMsg = null;
        
        ArrayList<Tile> stack = new ArrayList<Tile>();
        ArrayList<Tile> board = new ArrayList<Tile>();
        Player me = new Player();

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
                } else if (fromServer.startsWith("END")) {
                    fromUser = null;
                } else if(fromServer.startsWith("PLEASE WAIT")) {
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
                    stack2.clear();
                    stack1.add(centerTile);
                    stack2.add(centerTile);
                    fromUser = null;
                } else if (fromServer.startsWith("THE REMAINING")) {
                    String[] message = fromServer.split(" ");
                    for (int i = 6; i < 6 + Integer.parseInt(message[2]); i++) {
                        Tile newTile = new Tile();
                        newTile.tileID = message[i].toCharArray();
                        stack1.add(newTile);
                        stack2.add(newTile);
                    }
                } else if (fromServer.startsWith("MAKE YOUR MOVE")) {
                    String[] message = fromServer.split(" ");
                    int moveId = Integer.parseInt(message[10]);
                    String tileId = message[12];
                    
                    if (gameId1 == null)
                        gameId1 = message[5];
                    else if (gameId2 == null)
                        gameId2 = message[5];
                    
                    if (message[5].equals(gameId1)) {
                        moveMsg = makeMove(stack1, me1, gameId1, moveId, tileId, centerTile);
                        if (moveMsg == null)
                            fromUser = "GAME " + gameId1 + " MOVE " + moveId + " TILE " + tileId + " UNPLACEABLE PASS";
                        else
                            fromUser = moveMsg;
                    } else if (message[5].equals(gameId2)) {
                        moveMsg = makeMove(stack2, me2, gameId2, moveId, tileId, centerTile);
                        if (moveMsg == null)
                            fromUser = "GAME " + gameId2 + " MOVE " + moveId + " TILE " + tileId + " UNPLACEABLE PASS";
                        else
                            fromUser = moveMsg;
                    }
                } else if (fromServer.startsWith("GAME")) {
                    fromUser = null;
                    String[] message = fromServer.split(" ");
                    if (message[5] == opponent)
                        parseMove(message);
                } else if (fromServer.equals("THANK YOU FOR PLAYING! GOODBYE"))
                    break;
                
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
                
                // output("Stack:");
                // for (int x = 0; x < stack1.size(); x++) {
                //     Tile temp = stack1.get(x);
                //     output(temp.tileID + "\t" + temp.xCoord + "\t" + temp.yCoord + "\t" + temp.rotation + "\t" + temp.meeple + "\t");
                // }
                // 
                // output("Stack:");
                // for (int x = 0; x < stack2.size(); x++) {
                //     Tile temp = stack2.get(x);
                //     output(temp.tileID + "\t" + temp.xCoord + "\t" + temp.yCoord + "\t" + temp.rotation + "\t" + temp.meeple + "\t");
                // }
                        
            }
        } catch (Exception e) {System.out.println("ERROR: " + e);}
    }

    public static void parseMove(String[] message) {
        String gameId = message[1];
        String opponent = message[5];
        char[] placedTile = message[7].toCharArray();
        int x = Integer.parseInt(message[9]);
        int y = Integer.parseInt(message[10]);
        int rotation = Integer.parseInt(message[11]);
        String meeple = message[12];
        int meepleLoc = 0;
        if(message[6] != "FORFEITED") {
            if (meeple.equals("TIGER")) {
                meepleLoc = Integer.parseInt(message[13]);
            } else if (meeple.equals("CROCODILE"))
                meepleLoc = -1;

            Tile curTile = new Tile();
            if (gameId == gameId1) {
    	        for (int i = 0; i < stack1.size(); i++) {
    	            if (stack1.get(i).tileID == placedTile) {
    	                curTile = stack1.get(i);
    	                break;
    	            }
    	        }
    	    }
    	    else {
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

    };

    public static String makeMove(ArrayList<Tile> stack, Player me, String game, int move, String tileId, Tile curTile) {

        char[] tileToPlace = tileId.toCharArray();

        String replyMessage = null;

        String position = null;
        int xCoord = 0;
        int yCoord = 0;
        int rotation = 0;

// NORTH --------------------------------------
        if (curTile.north == null
                && curTile.rotation == 0
                && curTile.tileID[0] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 0)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 0;
            position = "north";
            output("IN North 1");
        } else if (curTile.north == null
                && curTile.rotation == 0
                && curTile.tileID[0] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 90)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 90;
            position = "north";
            output("IN North 2");
        } else if (curTile.north == null
                && curTile.rotation == 0
                && curTile.tileID[0] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 180)) {
            System.out.println(curTile.tileID[0]);
            System.out.println(tileToPlace[0]);
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 180;
            position = "north";
            output("IN North 3");
        } else if (curTile.north == null
                && curTile.rotation == 0
                && curTile.tileID[0] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 270)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 270;
            position = "north";
            output("IN North 4");
        }
        
        else if (curTile.north == null
                && curTile.rotation == 90
                && curTile.tileID[1] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 0)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 0;
            position = "north";
            output("IN North 1");
        } else if (curTile.north == null
                && curTile.rotation == 90
                && curTile.tileID[1] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 90)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 90;
            position = "north";
            output("IN North 2");
        } else if (curTile.north == null
                && curTile.rotation == 90
                && curTile.tileID[1] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 180)) {
            System.out.println(curTile.tileID[0]);
            System.out.println(tileToPlace[0]);
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 180;
            position = "north";
            output("IN North 3");
        } else if (curTile.north == null
                && curTile.rotation == 90
                && curTile.tileID[1] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 270)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 270;
            position = "north";
            output("IN North 4");
        }
        
        else if (curTile.north == null
                && curTile.rotation == 180
                && curTile.tileID[2] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 0)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 0;
            position = "north";
            output("IN North 1");
        } else if (curTile.north == null
                && curTile.rotation == 180
                && curTile.tileID[2] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 90)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 90;
            position = "north";
            output("IN North 2");
        } else if (curTile.north == null
                && curTile.rotation == 180
                && curTile.tileID[2] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 180)) {
            System.out.println(curTile.tileID[0]);
            System.out.println(tileToPlace[0]);
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 180;
            position = "north";
            output("IN North 3");
        } else if (curTile.north == null
                && curTile.rotation == 180
                && curTile.tileID[2] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 270)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 270;
            position = "north";
            output("IN North 4");
        }
        
        else if (curTile.north == null
                && curTile.rotation == 270
                && curTile.tileID[3] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 0)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 0;
            position = "north";
            output("IN North 1");
        } else if (curTile.north == null
                && curTile.rotation == 270
                && curTile.tileID[3] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 90)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 90;
            position = "north";
            output("IN North 2");
        } else if (curTile.north == null
                && curTile.rotation == 270
                && curTile.tileID[3] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 180)) {
            System.out.println(curTile.tileID[0]);
            System.out.println(tileToPlace[0]);
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 180;
            position = "north";
            output("IN North 3");
        } else if (curTile.north == null
                && curTile.rotation == 270
                && curTile.tileID[3] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord + 1, 270)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 270;
            position = "north";
            output("IN North 4");
        }
        
// EAST --------------------------------------
        else if (curTile.east == null
                && curTile.rotation == 0
                && curTile.tileID[1] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 0)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "east";
            output("IN East 1");
        } else if (curTile.east == null
                && curTile.rotation == 0
                && curTile.tileID[1] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 90)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "east";
            output("IN East 2");
        } else if (curTile.east == null
                && curTile.rotation == 0
                && curTile.tileID[1] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 180)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "east";
            output("IN East 3");
        } else if (curTile.east == null
                && curTile.rotation == 0
                && curTile.tileID[1] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 270)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "east";
            output("IN East 4");
        }
        
        else if (curTile.east == null
                && curTile.rotation == 90
                && curTile.tileID[2] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 0)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "east";
            output("IN East 1");
        } else if (curTile.east == null
                && curTile.rotation == 90
                && curTile.tileID[2] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 90)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "east";
            output("IN East 2");
        } else if (curTile.east == null
                && curTile.rotation == 90
                && curTile.tileID[2] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 180)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "east";
            output("IN East 3");
        } else if (curTile.east == null
                && curTile.rotation == 90
                && curTile.tileID[2] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 270)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "east";
            output("IN East 4");
        }
        
        else if (curTile.east == null
                && curTile.rotation == 180
                && curTile.tileID[3] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 0)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "east";
            output("IN East 1");
        } else if (curTile.east == null
                && curTile.rotation == 180
                && curTile.tileID[3] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 90)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "east";
            output("IN East 2");
        } else if (curTile.east == null
                && curTile.rotation == 180
                && curTile.tileID[3] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 180)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "east";
            output("IN East 3");
        } else if (curTile.east == null
                && curTile.rotation == 180
                && curTile.tileID[3] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 270)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "east";
            output("IN East 4");
        }
        
        else if (curTile.east == null
                && curTile.rotation == 270
                && curTile.tileID[0] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 0)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "east";
            output("IN East 1");
        } else if (curTile.east == null
                && curTile.rotation == 270
                && curTile.tileID[0] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 90)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "east";
            output("IN East 2");
        } else if (curTile.east == null
                && curTile.rotation == 270
                && curTile.tileID[0] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 180)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "east";
            output("IN East 3");
        } else if (curTile.east == null
                && curTile.rotation == 270
                && curTile.tileID[0] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord + 1, curTile.yCoord, 270)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "east";
            output("IN East 4");
        }
        
// SOUTH --------------------------------------

        else if (curTile.south == null
                && curTile.rotation == 0
                && curTile.tileID[2] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 0)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 0;
            position = "south";
            output("IN South 1");
        } else if (curTile.south == null
                && curTile.rotation == 0
                && curTile.tileID[2] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 90)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 90;
            position = "south";
            output("IN South 2");
        } else if (curTile.south == null
                && curTile.rotation == 0
                && curTile.tileID[2] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 180)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 180;
            position = "south";
            output("IN South 3");
        } else if (curTile.south == null
                && curTile.rotation == 0
                && curTile.tileID[2] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 270)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 270;
            position = "south";
            output("IN South 4");
        }
        
        else if (curTile.south == null
                && curTile.rotation == 90
                && curTile.tileID[3] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 0)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 0;
            position = "south";
            output("IN South 1");
        } else if (curTile.south == null
                && curTile.rotation == 90
                && curTile.tileID[3] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 90)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 90;
            position = "south";
            output("IN South 2");
        } else if (curTile.south == null
                && curTile.rotation == 90
                && curTile.tileID[3] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 180)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 180;
            position = "south";
            output("IN South 3");
        } else if (curTile.south == null
                && curTile.rotation == 90
                && curTile.tileID[3] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 270)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 270;
            position = "south";
            output("IN South 4");
        }
        
        else if (curTile.south == null
                && curTile.rotation == 180
                && curTile.tileID[0] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 0)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 0;
            position = "south";
            output("IN South 1");
        } else if (curTile.south == null
                && curTile.rotation == 180
                && curTile.tileID[0] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 90)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 90;
            position = "south";
            output("IN South 2");
        } else if (curTile.south == null
                && curTile.rotation == 180
                && curTile.tileID[0] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 180)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 180;
            position = "south";
            output("IN South 3");
        } else if (curTile.south == null
                && curTile.rotation == 180
                && curTile.tileID[0] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 270)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 270;
            position = "south";
            output("IN South 4");
        }
        
        else if (curTile.south == null
                && curTile.rotation == 270
                && curTile.tileID[1] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 0)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 0;
            position = "south";
            output("IN South 1");
        } else if (curTile.south == null
                && curTile.rotation == 270
                && curTile.tileID[1] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 90)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 90;
            position = "south";
            output("IN South 2");
        } else if (curTile.south == null
                && curTile.rotation == 270
                && curTile.tileID[1] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 180)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 180;
            position = "south";
            output("IN South 3");
        } else if (curTile.south == null
                && curTile.rotation == 270
                && curTile.tileID[1] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord, curTile.yCoord - 1, 270)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 270;
            position = "south";
            output("IN South 4");
        }
        
// WEST --------------------------------------

        else if (curTile.west == null
                && curTile.rotation == 0
                && curTile.tileID[3] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 0)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "west";
            output("IN West 1");
        } else if (curTile.west == null
                && curTile.rotation == 0
                && curTile.tileID[3] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 90)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "west";
            output("IN West 2");
        } else if (curTile.west == null
                && curTile.rotation == 0
                && curTile.tileID[3] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 180)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "west";
            output("IN West 3");
        } else if (curTile.west == null
                && curTile.rotation == 0
                && curTile.tileID[3] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 270)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "west";
            output("IN West 4");
        }
        
        else if (curTile.west == null
                && curTile.rotation == 90
                && curTile.tileID[0] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 0)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "west";
            output("IN West 1");
        } else if (curTile.west == null
                && curTile.rotation == 90
                && curTile.tileID[0] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 90)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "west";
            output("IN West 2");
        } else if (curTile.west == null
                && curTile.rotation == 90
                && curTile.tileID[0] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 180)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "west";
            output("IN West 3");
        } else if (curTile.west == null
                && curTile.rotation == 90
                && curTile.tileID[0] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 270)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "west";
            output("IN West 4");
        }
        
        else if (curTile.west == null
                && curTile.rotation == 180
                && curTile.tileID[1] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 0)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "west";
            output("IN West 1");
        } else if (curTile.west == null
                && curTile.rotation == 180
                && curTile.tileID[1] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 90)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "west";
            output("IN West 2");
        } else if (curTile.west == null
                && curTile.rotation == 180
                && curTile.tileID[1] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 180)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "west";
            output("IN West 3");
        } else if (curTile.west == null
                && curTile.rotation == 180
                && curTile.tileID[1] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 270)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "west";
            output("IN West 4");
        }
        
        else if (curTile.west == null
                && curTile.rotation == 270
                && curTile.tileID[2] == tileToPlace[1]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 0)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "west";
            output("IN West 1");
        } else if (curTile.west == null
                && curTile.rotation == 270
                && curTile.tileID[2] == tileToPlace[2]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 90)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "west";
            output("IN West 2");
        } else if (curTile.west == null
                && curTile.rotation == 270
                && curTile.tileID[2] == tileToPlace[3]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 180)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "west";
            output("IN West 3");
        } else if (curTile.west == null
                && curTile.rotation == 270
                && curTile.tileID[2] == tileToPlace[0]
                && hasValidProx(stack, tileToPlace, curTile.xCoord - 1, curTile.yCoord, 270)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "west";
            output("IN West 4");
        }
        
// ---------------------------------

        if (xCoord != 0 || yCoord != 0) {
            Tile newTile = new Tile();
            newTile.tileID = tileToPlace;
            newTile.meeple = 0;
            newTile.xCoord = xCoord;
            newTile.yCoord = yCoord;
            newTile.rotation = rotation;
            newTile.ownTile = true;
            if (position.equals("north"))
                curTile.north = newTile;
            else if (position.equals("east"))
                curTile.east = newTile;
            else if (position.equals("south"))
                curTile.south = newTile;
            else if (position.equals("west"))
                curTile.west = newTile;

            replyMessage = "GAME " + game + " MOVE " + move + " PLACE " + tileId + " AT " + newTile.xCoord + " " + newTile.yCoord + " " + newTile.rotation + " NONE";
            
            // String meeplePlace = null;
            // if (me.crocodiles > 0)
            //     meeplePlace = animalPlacement.place_croc(me, curTile, stack);
            // if (meeplePlace == null && me.tigers > 0)
            //     meeplePlace = animalPlacement.place_tiger(me, curTile, stack);
            // if (meeplePlace == null)
            //     meeplePlace = " NONE";
            // // animalPlacement.meeple_return(curTile, stack, me);
            // return replyMessage += meeplePlace;

        } else {
            if (replyMessage == null) {
                replyMessage = makeMove(stack, me, game, move, tileId, curTile.north);
            }
            if (replyMessage == null) {
                replyMessage = makeMove(stack, me, game, move, tileId, curTile.east);
            }
            if (replyMessage == null) {
                replyMessage = makeMove(stack, me, game, move, tileId, curTile.south);
            }
            if (replyMessage == null) {
                replyMessage = makeMove(stack, me, game, move, tileId, curTile.west);
            }
        }

        return replyMessage;
    };
    

    public static boolean hasValidProx(ArrayList<Tile> stack, char[] tileIdToPlace, int x, int y, int rotation) {

        return true;
        
        // boolean isValid = true;
        // 
        // output("\nTile to place >> " + tileIdToPlace[0] + tileIdToPlace[1] + tileIdToPlace[2] + tileIdToPlace[3] + tileIdToPlace[4]);
        // output("Want to place at >>\t " + x + "\t" + y);
        // output("\n");
        // 
        // for (int i = 0; i < stack.size(); i++) {
        //     Tile existingTile = stack.get(i);
        //     
        //     // north
        //     if (existingTile.xCoord == x && existingTile.yCoord == (y + 1)) {
        //         output("Checking north >> " + x + "\t" + (y + 1));
        //         if (rotation == 0)
        //             if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[0])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[3] != tileIdToPlace[0])
        //                 return false;
        //             else if (existingTile.rotation == 180 && existingTile.tileID[0] != tileIdToPlace[0])
        //                 return false;
        //             else if (existingTile.rotation == 270 && existingTile.tileID[1] != tileIdToPlace[0])
        //                 return false;
        //         else if (rotation == 90)
        //             if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[1])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[3] != tileIdToPlace[1])
        //                 return false;
        //             else if (existingTile.rotation == 180 && existingTile.tileID[0] != tileIdToPlace[1])
        //                 return false;
        //             else if (existingTile.rotation == 270 && existingTile.tileID[1] != tileIdToPlace[1])
        //                 return false;
        //         else if (rotation == 180)
        //             if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[2])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[3] != tileIdToPlace[2])
        //                 return false;
        //             else if (existingTile.rotation == 180 && existingTile.tileID[0] != tileIdToPlace[2])
        //                 return false;
        //             else if (existingTile.rotation == 270 && existingTile.tileID[1] != tileIdToPlace[2])
        //                 return false;
        //         else if (rotation == 270)
        //             if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[3] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 180 && existingTile.tileID[0] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 270 && existingTile.tileID[1] != tileIdToPlace[3])
        //                 return false;
        //     }
        //     // east
        //     if (existingTile.xCoord == (x + 1) && existingTile.yCoord == y) {
        //         output("\nChecking east >> " + (x + 1) + "\t" + y);
        //         if (rotation == 0)
        //             if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[1])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[0] != tileIdToPlace[1])
        //                 return false;
        //             else if (existingTile.rotation == 180 && existingTile.tileID[1] != tileIdToPlace[1])
        //                 return false;
        //             else if (existingTile.rotation == 270 && existingTile.tileID[2] != tileIdToPlace[1])
        //                 return false;
        //         else if (rotation == 90)
        //             if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[2])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[0] != tileIdToPlace[2])
        //                 return false;
        //             else if (existingTile.rotation == 180 && existingTile.tileID[1] != tileIdToPlace[2])
        //                 return false;
        //             else if (existingTile.rotation == 270 && existingTile.tileID[2] != tileIdToPlace[2])
        //                 return false;
        //         else if (rotation == 180)
        //             if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[0] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 180 && existingTile.tileID[1] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 270 && existingTile.tileID[2] != tileIdToPlace[3])
        //                 return false;
        //         else if (rotation == 270)
        //             if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[0] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 180 && existingTile.tileID[1] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 270 && existingTile.tileID[2] != tileIdToPlace[3])
        //                 return false;
        //     }
        //     // south
        //     if (existingTile.xCoord == x && existingTile.yCoord == (y - 1)) {
        //         output("\nChecking south >> " + x + "\t" + (y - 1));
        //         if (rotation == 0)
        //             if (existingTile.rotation == 0 && existingTile.tileID[0] != tileIdToPlace[2])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[1] != tileIdToPlace[2])
        //                 return false;
        //             else if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[2])
        //                 return false;
        //             else if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[2])
        //                 return false;
        //         else if (rotation == 90)
        //             if (existingTile.rotation == 0 && existingTile.tileID[0] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[1] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[3])
        //                 return false;
        //         else if (rotation == 180)
        //             if (existingTile.rotation == 0 && existingTile.tileID[0] != tileIdToPlace[0])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[1] != tileIdToPlace[0])
        //                 return false;
        //             else if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[0])
        //                 return false;
        //             else if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[0])
        //                 return false;
        //         else if (rotation == 270)
        //             if (existingTile.rotation == 0 && existingTile.tileID[0] != tileIdToPlace[1])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[1] != tileIdToPlace[1])
        //                 return false;
        //             else if (existingTile.rotation == 0 && existingTile.tileID[2] != tileIdToPlace[1])
        //                 return false;
        //             else if (existingTile.rotation == 0 && existingTile.tileID[3] != tileIdToPlace[1])
        //                 return false;
        //     }
        //     // west
        //     if (existingTile.xCoord == (x - 1) && existingTile.yCoord == y) {
        //         output("\nChecking west >> " + (x - 1) + "\t" + y);
        //         if (rotation == 0)
        //             if (existingTile.rotation == 0 && existingTile.tileID[1] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[2] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 180 && existingTile.tileID[3] != tileIdToPlace[3])
        //                 return false;
        //             else if (existingTile.rotation == 270 && existingTile.tileID[0] != tileIdToPlace[3])
        //                 return false;
        //         else if (rotation == 90)
        //             if (existingTile.rotation == 0 && existingTile.tileID[1] != tileIdToPlace[0])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[2] != tileIdToPlace[0])
        //                 return false;
        //             else if (existingTile.rotation == 180 && existingTile.tileID[3] != tileIdToPlace[0])
        //                 return false;
        //             else if (existingTile.rotation == 270 && existingTile.tileID[0] != tileIdToPlace[0])
        //                 return false;
        //         else if (rotation == 180)
        //             if (existingTile.rotation == 0 && existingTile.tileID[1] != tileIdToPlace[1])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[2] != tileIdToPlace[1])
        //                 return false;
        //             else if (existingTile.rotation == 180 && existingTile.tileID[3] != tileIdToPlace[1])
        //                 return false;
        //             else if (existingTile.rotation == 270 && existingTile.tileID[0] != tileIdToPlace[1])
        //                 return false;
        //         else if (rotation == 270)
        //             if (existingTile.rotation == 0 && existingTile.tileID[1] != tileIdToPlace[2])
        //                 return false;
        //             else if (existingTile.rotation == 90 && existingTile.tileID[2] != tileIdToPlace[2])
        //                 return false;
        //             else if (existingTile.rotation == 180 && existingTile.tileID[3] != tileIdToPlace[2])
        //                 return false;
        //             else if (existingTile.rotation == 270 && existingTile.tileID[0] != tileIdToPlace[2])
        //                 return false;
        //     }
        // }
        // 
        // return isValid;
    }

    public static void output(String s) {
        System.out.println(s);
    }
}
