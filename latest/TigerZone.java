import java.net.*;
import java.io.*;
import java.util.*;

public class TigerZone {

    static Tile centerTile = new Tile();
    static ArrayList<Tile> stack = new ArrayList<Tile>();

    public static void main(String[] args) {
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
                    stack.clear();
                    stack.add(centerTile);
                    fromUser = null;
                } else if (fromServer.startsWith("THE REMAINING")) {
                    String[] message = fromServer.split(" ");
                    for (int i = 6; i < 6 + Integer.parseInt(message[2]); i++) {
                        Tile newTile = new Tile();
                        newTile.tileID = message[i].toCharArray();
                        stack.add(newTile);
                    }
                    // plan moves
                } else if (fromServer.startsWith("GAME")) {
                    fromUser = null;
                    String[] message = fromServer.split(" ");
                    if (message[5] == opponent)
                        parseMove(message);
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

    public static void parseMove(String[] message) {
        String opponent = message[5];
        char[] placedTile = message[7].toCharArray();
        int x = Integer.parseInt(message[9]);
        int y = Integer.parseInt(message[10]);
        int rotation = Integer.parseInt(message[11]);
        String meeple = message[12];
        int meepleLoc = 0;
        if (!meeple.equals("NONE")) {
            meepleLoc = Integer.parseInt(message[13]);
        }

        Tile curTile = new Tile();
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).tileID == placedTile) {
                curTile = stack.get(i);
                break;
            }
        }
        curTile.xCoord = x;
        curTile.yCoord = y;
        curTile.rotation = rotation;
    }

    public static String makeMove(String game, int move, String tileId, Tile curTile) {

        char[] tileToPlace = tileId.toCharArray();

        String replyMessage = "GAME " + game + " MOVE " + move + " PLACE " + tileId + " AT ";

        String position = null;
        int xCoord = 0;
        int yCoord = 0;
        int rotation = 0;

        output(curTile.tileID[0] + " - " + tileToPlace[2] + (curTile.north == null));

        if (curTile.north == null
                && curTile.tileID[0] == tileToPlace[2]
                && hasValidProx(tileToPlace, curTile.xCoord, curTile.yCoord + 1, 0)) {
            System.out.println("north 0");
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 0;
            position = "north";
        } else if (curTile.north == null
                && curTile.tileID[0] == tileToPlace[3]
                && hasValidProx(tileToPlace, curTile.xCoord, curTile.yCoord + 1, 90)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 90;
            position = "north";
        } else if (curTile.north == null
                && curTile.tileID[0] == tileToPlace[0]
                && hasValidProx(tileToPlace, curTile.xCoord, curTile.yCoord + 1, 180)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 180;
            position = "north";
        } else if (curTile.north == null
                && curTile.tileID[0] == tileToPlace[1]
                && hasValidProx(tileToPlace, curTile.xCoord, curTile.yCoord + 1, 270)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord + 1;
            rotation = 270;
            position = "north";
        }

        else if (curTile.east == null
                && curTile.tileID[1] == tileToPlace[3]
                && hasValidProx(tileToPlace, curTile.xCoord + 1, curTile.yCoord, 0)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "east";
        } else if (curTile.east == null
                && curTile.tileID[1] == tileToPlace[0]
                && hasValidProx(tileToPlace, curTile.xCoord + 1, curTile.yCoord, 90)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "east";
        } else if (curTile.east == null
                && curTile.tileID[1] == tileToPlace[1]
                && hasValidProx(tileToPlace, curTile.xCoord + 1, curTile.yCoord, 180)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "east";
        } else if (curTile.east == null
                && curTile.tileID[1] == tileToPlace[2]
                && hasValidProx(tileToPlace, curTile.xCoord + 1, curTile.yCoord, 270)) {
            xCoord = curTile.xCoord + 1;
            yCoord = curTile.yCoord;
            rotation = 270;
            position = "east";
        }

        else if (curTile.south == null
                && curTile.tileID[2] == tileToPlace[0]
                && hasValidProx(tileToPlace, curTile.xCoord, curTile.yCoord - 1, 0)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 0;
            position = "south";
        } else if (curTile.south == null
                && curTile.tileID[2] == tileToPlace[1]
                && hasValidProx(tileToPlace, curTile.xCoord, curTile.yCoord - 1, 90)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 90;
            position = "south";
        } else if (curTile.south == null
                && curTile.tileID[2] == tileToPlace[2]
                && hasValidProx(tileToPlace, curTile.xCoord, curTile.yCoord - 1, 180)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 180;
            position = "south";
        } else if (curTile.south == null
                && curTile.tileID[2] == tileToPlace[3]
                && hasValidProx(tileToPlace, curTile.xCoord, curTile.yCoord - 1, 270)) {
            xCoord = curTile.xCoord;
            yCoord = curTile.yCoord - 1;
            rotation = 270;
            position = "south";
        }

        else if (curTile.west == null
                && curTile.tileID[3] == tileToPlace[1]
                && hasValidProx(tileToPlace, curTile.xCoord - 1, curTile.yCoord, 0)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 0;
            position = "west";
        } else if (curTile.west == null
                && curTile.tileID[3] == tileToPlace[2]
                && hasValidProx(tileToPlace, curTile.xCoord - 1, curTile.yCoord, 90)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 90;
            position = "west";
        } else if (curTile.west == null
                && curTile.tileID[3] == tileToPlace[3]
                && hasValidProx(tileToPlace, curTile.xCoord - 1, curTile.yCoord, 180)) {
            xCoord = curTile.xCoord - 1;
            yCoord = curTile.yCoord;
            rotation = 180;
            position = "west";
        } else if (curTile.west == null
                && curTile.tileID[3] == tileToPlace[0]
                && hasValidProx(tileToPlace, curTile.xCoord - 1, curTile.yCoord, 270)) {
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

    public static boolean hasValidProx(char[] tileIdToPlace, int x, int y, int rotation) {

        boolean isValid = true;

        System.out.println("validating...");

        for (int i = 0; i < stack.size(); i++) {
            Tile existingTile = stack.get(i);

            // north
            if (existingTile.xCoord == x && existingTile.yCoord == y) {
                if (rotation == 0 && existingTile.tileID[2] != tileIdToPlace[0])
                    return false;
                else if (rotation == 90 && existingTile.tileID[2] != tileIdToPlace[1])
                    return false;
                else if (rotation == 180 && existingTile.tileID[2] != tileIdToPlace[2])
                    return false;
                else if (rotation == 270 && existingTile.tileID[2] != tileIdToPlace[3])
                    return false;
            }
            // east
            if (existingTile.xCoord == x && existingTile.yCoord == y) {
                if (rotation == 0 && existingTile.tileID[3] != tileIdToPlace[1])
                    return false;
                else if (rotation == 90 && existingTile.tileID[3] != tileIdToPlace[2])
                    return false;
                else if (rotation == 180 && existingTile.tileID[3] != tileIdToPlace[3])
                    return false;
                else if (rotation == 270 && existingTile.tileID[3] != tileIdToPlace[0])
                    return false;
            }
            // south
            if (existingTile.xCoord == x && existingTile.yCoord == y) {
                if (rotation == 0 && existingTile.tileID[0] != tileIdToPlace[2])
                    return false;
                else if (rotation == 90 && existingTile.tileID[0] != tileIdToPlace[3])
                    return false;
                else if (rotation == 180 && existingTile.tileID[0] != tileIdToPlace[0])
                    return false;
                else if (rotation == 270 && existingTile.tileID[0] != tileIdToPlace[1])
                    return false;
            }
            // west
            if (existingTile.xCoord == x && existingTile.yCoord == y) {
                if (rotation == 0 && existingTile.tileID[1] != tileIdToPlace[3])
                    return false;
                else if (rotation == 90 && existingTile.tileID[1] != tileIdToPlace[0])
                    return false;
                else if (rotation == 180 && existingTile.tileID[1] != tileIdToPlace[1])
                    return false;
                else if (rotation == 270 && existingTile.tileID[1] != tileIdToPlace[2])
                    return false;
            }
        }

        System.out.println(isValid);

        return isValid;
    }

    public static void output(String s) {
        System.out.println(s);
    }
}
