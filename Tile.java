package tzone;
import tzone.*;
import java.util.*;

public class Tile{
  private Cardibool faces;
  private Cardibool roads;
  private Prey prey = new Prey();
  private Boolean den;                 // True: tile has den; False: no den
  private List<Continuity> cont;       // List to hold continuities, maybe not

  public Tile(){}

//----- Mutators -----
  public void setFaces(Boolean n, Boolean e, Boolean s, Boolean w){
    this.faces = new Cardibool(n, e, s, w);
  }
  public void setRoads(Boolean n, Boolean e, Boolean s, Boolean w){
    this.roads = new Cardibool(n, e, s, w);
  }
  public void setPrey(char p){
    this.prey = new Prey(p);
  }
  public void setDen(Boolean d){
    this.den = d;
  }
  public void setContinuity(Boolean t, String s){
    cont.add(new Continuity(t, s));
  }

//----- Accessors -----
  public Cardibool getFaces(){
    return this.faces;
  }
  public Cardibool getRoads(){
    return this.roads;
  }
  public Prey getPrey(){
    return this.prey;
  }
  public Boolean getDen(){
    return this.den;
  }
  public List getContinuity(){
    return this.cont;
  }

//----- Print functions -----
  public void printFaces(){
    this.faces.print(true);
  }
  public void printRoads(){
    this.roads.print(false);
  }
  public void printPrey(){
    System.out.printf("Prey: %c\n", this.prey.Grazing() ? this.prey.preyType() : 'X');
  }
  public void printDen(){
    System.out.printf("Den: %s\n", this.den ? "Yes" : "No");
  }
  public void printAll(){
    this.printFaces();
    this.printRoads();
    this.printPrey();
    this.printDen();
  }
}
