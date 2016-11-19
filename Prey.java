package tzone;
import tzone.*;
import java.util.*;

public class Prey{
  private Boolean grazing = false;
  private char preyType = '-';

  public Prey(){}

  public Prey(char p){
    this.preyType = p;
    this.grazing = true;
  }

  public Boolean Grazing(){
    return this.grazing;
  }

  public char preyType(){
    return this.preyType;
  }
}
