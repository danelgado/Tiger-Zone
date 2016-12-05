public class Tile {
  
  char[] tileID;
  boolean ownTile = false;
  int xCoord = 0;
  int yCoord = 0;
  int rotation = 0;
  int meeple = 0;
  Tile north = null;
  Tile east = null;
  Tile south = null;
  Tile west = null;
  
  public String getTileID() {
      return new String(tileID);
  }
}