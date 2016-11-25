import java.net.*;
import java.io.*;
import java.util.*;

public class TigerZone {

    static Tile centerTile = new Tile();
    static ArrayList<String> stack = new ArrayList<String>();

    public static void main(String[] args) {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String tournamentPassword = args[2];
        String user = args[3];
        String userPassword = args[4];

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
                } else if (fromServer.startsWith("STARTING TILE IS")) {
                    String[] message = fromServer.split(" ");
                    centerTile.tileID = message[3].toCharArray();
                    centerTile.xCoord = Integer.parseInt(message[5]);
                    centerTile.yCoord = Integer.parseInt(message[6]);
                    centerTile.rotation = Integer.parseInt(message[7]);
                    fromUser = null;
                } else if (fromServer.startsWith("THE REMAINING")) {
                    String[] message = fromServer.split(" ");
                    for (int i = 5; i < 5 + Integer.parseInt(message[1]); i++) {
                        stack.add(message[i]);
                    }
                    // plan moves
                } else if (fromServer.startsWith("GAME")) {
                    String[] message = fromServer.split(" ");
                } else if (fromServer.startsWith("MAKE YOUR MOVE")) {
                    String[] message = fromServer.split(" ");
                    String gameId = message[5];
                    int moveId = Integer.parseInt(message[10]);
                    String tileId = message[12];
                    fromUser = makeMove(gameId, moveId, tileId, centerTile);
                }

                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch (Exception e) {System.out.println(e);}
    }

    public static void parseMove(String message) {
        String[] tokens = message.split(" ");

//        if ()
    }

    public static String makeMove(String game, int move, String tileId, Tile curTile) {

        char[] tileToPlace = tileId.toCharArray();

        String replyMessage = "GAME " + game + " MOVE " + move + " PLACE " + tileId + " AT ";

        String position = null;
        int xCoord = 0;
        int yCoord = 0;
        int rotation = 0;

        if (curTile.north == null && curTile.tileID[0] == tileToPlace[2]) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 0;
            position = "north";
        } else if (curTile.north == null && curTile.tileID[0] == tileToPlace[3]) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 90;
            position = "north";
        } else if (curTile.north == null && curTile.tileID[0] == tileToPlace[0]) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 180;
            position = "north";
        } else if (curTile.north == null && curTile.tileID[0] == tileToPlace[1]) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 270;
            position = "north";
        }

        else if (curTile.east == null && curTile.tileID[1] == tileToPlace[3]) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "east";
        } else if (curTile.east == null && curTile.tileID[1] == tileToPlace[0]) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "east";
        } else if (curTile.east == null && curTile.tileID[1] == tileToPlace[1]) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "east";
        } else if (curTile.east == null && curTile.tileID[1] == tileToPlace[2]) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "east";
        }

        else if (curTile.south == null && curTile.tileID[2] == tileToPlace[0]) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 0;
            position = "south";
        } else if (curTile.south == null && curTile.tileID[2] == tileToPlace[1]) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 90;
            position = "south";
        } else if (curTile.south == null && curTile.tileID[2] == tileToPlace[2]) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 180;
            position = "south";
        } else if (curTile.south == null && curTile.tileID[2] == tileToPlace[3]) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 270;
            position = "south";
        }

        else if (curTile.west == null && curTile.tileID[3] == tileToPlace[1]) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "west";
        } else if (curTile.west == null && curTile.tileID[3] == tileToPlace[2]) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "west";
        } else if (curTile.west == null && curTile.tileID[3] == tileToPlace[3]) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "west";
        } else if (curTile.west == null && curTile.tileID[3] == tileToPlace[0]) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "west";
        }

        if (xCoord != 0 || yCoord != 0) {
            Tile newTile = new Tile();
            newTile.tileID = tileToPlace;
            newTile.tiger = 0;
            newTile.xCoord = xCoord;
            newTile.yCoord = yCoord;
            newTile.rotation = rotation;
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
            replyMessage = makeMove(game, move, tileId, curTile.north);

            if (replyMessage == null) {
                replyMessage = makeMove(game, move, tileId, curTile.east);
            }
            if (replyMessage == null) {
                replyMessage = makeMove(game, move, tileId, curTile.south);
            }
            if (replyMessage == null) {
                replyMessage = makeMove(game, move, tileId, curTile.west);
            }
        }

        if (replyMessage != null) {
            replyMessage += " NONE";
            return replyMessage;
        } else {
            return null;
        }
    }
}

