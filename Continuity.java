package tzone;
import tzone.*;
import java.util.*;

public class Continuity{
  public Boolean terrain;
  public String edge = new String();

  public Continuity(){}

  public Continuity(Boolean t, String c){
    this.terrain = t;
    this.edge = c;
  }

  public Boolean isCountinuous(String a, String b){
    if (this.edge.contains(a) && this.edge.contains(b)){ return true; }
    else return false;
  }

//----- Accessors -----
  public String getEdge(){
    return this.edge;
  }
  public Boolean getTerrain(){
    return this.terrain;
  }

//----- Mutators -----
  public void setEdge(String e){
    this.edge = e;
  }
  public void setTerrain(Boolean t){
    this.terrain = t;
  }
}
