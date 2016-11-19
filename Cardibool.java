package tzone;
import tzone.*;
import java.util.*;


public class Cardibool{

  /*
      -----------
     |   north   |
     |w         e|
     |e         a|
     |s         s|
     |t  south  t|
      -----------
      True: Jungle on the edge/road extends from edge to center
      False: Lake on the edge/no road on edge
  */

  private Boolean north;
  private Boolean east;
  private Boolean south;
  private Boolean west;

  public Cardibool(){}

  public Cardibool(Boolean n, Boolean e, Boolean s, Boolean w){
    this.north = n;
    this.east = e;
    this.south = s;
    this.west = w;
  }

//----- Accessors -----
  public Boolean North(){
    return this.north;
  }
  public Boolean East(){
    return this.east;
  }
  public Boolean South(){
    return this.south;
  }
  public Boolean West(){
    return this.west;
  }

  public void print(Boolean b){
      System.out.printf("North: %s\n", this.north ? (b ? "Jungle" : "Road") : (b ? "Lake" : "No Road"));
      System.out.printf("East: %s\n", this.east ? (b ? "Jungle" : "Road") : (b ? "Lake" : "No Road"));
      System.out.printf("South: %s\n", this.south ? (b ? "Jungle" : "Road") : (b ? "Lake" : "No Road"));
      System.out.printf("West: %s\n", this.west ? (b ? "Jungle" : "Road") : (b ? "Lake" : "No Road"));
  }
}
