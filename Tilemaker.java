package tzone;
import tzone.*;
import java.util.*;

// This uses a very long switch-case block to represent the tile variations
public class Tilemaker{
  public Tile tile = new Tile();
  public Terrain t = new Terrain(); // Constants, for readability

  public Tilemaker(){}

  // The convention is function(NORTH, EAST, SOUTH, WEST), clockwise from the top
  // Continuity not yet implemented.
  // Need to represent prey location better
  public Tilemaker(int i){
    switch (i){
      case 1:
        tile.setFaces(t.JUNGLE, t.JUNGLE, t.JUNGLE, t.JUNGLE);
        tile.setRoads(t.NULL, t.NULL, t.NULL, t.NULL);
        tile.setDen(t.NULL);
        break;
      case 2:
        tile.setFaces(t.JUNGLE, t.JUNGLE, t.JUNGLE, t.JUNGLE);
        tile.setRoads(t.NULL, t.NULL, t.NULL, t.NULL);
        tile.setDen(t.DEN);
        break;
      case 3:
        tile.setFaces(t.JUNGLE, t.JUNGLE, t.JUNGLE, t.JUNGLE);
        tile.setRoads(t.NULL, t.NULL, t.ROAD, t.NULL);
        tile.setDen(t.DEN);
        break;
      case 4:
        tile.setFaces(t.JUNGLE, t.JUNGLE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.ROAD, t.ROAD, t.ROAD, t.ROAD);
        this.tile.setDen(t.NULL);
        break;
      case 5:
        tile.setFaces(t.JUNGLE, t.JUNGLE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.ROAD, t.NULL, t.ROAD, t.NULL);
        this.tile.setDen(t.NULL);
        break;
      case 6:
        tile.setFaces(t.JUNGLE, t.JUNGLE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.ROAD, t.NULL, t.NULL, t.ROAD);
        this.tile.setDen(t.NULL);
        break;
      case 7:
        tile.setFaces(t.JUNGLE, t.JUNGLE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.ROAD, t.NULL, t.ROAD, t.ROAD);
        this.tile.setDen(t.NULL);
        break;
      case 8:
        this.tile.setFaces(t.LAKE, t.LAKE, t.LAKE, t.LAKE);
        this.tile.setRoads(t.NULL, t.NULL, t.NULL, t.NULL);
        this.tile.setDen(t.NULL);
        break;
      case 9:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.LAKE, t.LAKE);
        this.tile.setRoads(t.NULL, t.NULL, t.NULL, t.NULL);
        this.tile.setDen(t.NULL);
        break;
      case 10:
        this.tile.setFaces(t.LAKE, t.LAKE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.NULL, t.NULL, t.NULL, t.NULL);
        this.tile.setDen(t.NULL);
        break;
      case 11:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.JUNGLE, t.LAKE);
        this.tile.setRoads(t.NULL, t.NULL, t.NULL, t.NULL);
        this.tile.setDen(t.NULL);
        break;
      case 12:
        this.tile.setFaces(t.LAKE, t.JUNGLE, t.LAKE, t.JUNGLE);
        this.tile.setRoads(t.NULL, t.NULL, t.NULL, t.NULL);
        this.tile.setDen(t.NULL);
        break;
      case 13:
        this.tile.setFaces(t.LAKE, t.JUNGLE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.NULL, t.NULL, t.NULL, t.NULL);
        this.tile.setDen(t.NULL);
        break;
      case 14:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.LAKE, t.JUNGLE);
        this.tile.setRoads(t.NULL, t.NULL, t.NULL, t.NULL);
        this.tile.setDen(t.NULL);
        break;
      case 15:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.ROAD, t.NULL, t.NULL, t.ROAD);
        this.tile.setDen(t.NULL);
        break;
      case 16:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.ROAD, t.NULL, t.NULL, t.ROAD);
        this.tile.setPrey(t.BOAR);
        this.tile.setDen(t.NULL);
        break;
      case 17:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.NULL, t.NULL, t.ROAD, t.ROAD);
        this.tile.setDen(t.NULL);
        break;
      case 18:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.NULL, t.NULL, t.ROAD, t.ROAD);
        this.tile.setPrey(t.BUFFALO);
        this.tile.setDen(t.NULL);
        break;
      case 19:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.ROAD, t.NULL, t.ROAD, t.NULL);
        this.tile.setDen(t.NULL);
        break;
      case 20:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.ROAD, t.NULL, t.ROAD, t.NULL);
        this.tile.setPrey(t.DEER);
        this.tile.setDen(t.NULL);
        break;
      case 21:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.LAKE, t.LAKE);
        this.tile.setRoads(t.ROAD, t.NULL, t.NULL, t.NULL);
        this.tile.setDen(t.NULL);
        break;
      case 22:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.ROAD, t.NULL, t.ROAD, t.ROAD);
        this.tile.setDen(t.NULL);
        break;
      case 23:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.ROAD, t.NULL, t.ROAD, t.ROAD);
        this.tile.setPrey(t.BOAR);
        this.tile.setDen(t.NULL);
        break;
      case 24:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.LAKE, t.JUNGLE);
        this.tile.setRoads(t.ROAD, t.NULL, t.NULL, t.ROAD);
        this.tile.setDen(t.NULL);
        break;
      case 25:
        this.tile.setFaces(t.JUNGLE, t.LAKE, t.LAKE, t.JUNGLE);
        this.tile.setRoads(t.ROAD, t.NULL, t.NULL, t.ROAD);
        this.tile.setPrey(t.BUFFALO);
        this.tile.setDen(t.NULL);
        break;
      case 26:
        this.tile.setFaces(t.LAKE, t.JUNGLE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.NULL, t.NULL, t.ROAD, t.NULL);
        this.tile.setDen(t.NULL);
        break;
      case 27:
        this.tile.setFaces(t.LAKE, t.JUNGLE, t.JUNGLE, t.JUNGLE);
        this.tile.setRoads(t.NULL, t.NULL, t.ROAD, t.NULL);
        this.tile.setPrey(t.DEER);
        this.tile.setDen(t.NULL);
        break;
    }
  }

  public Tile getTile(){
    return this.tile;
  }

}
