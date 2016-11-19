package tzone;
import tzone.*;
import java.util.*;

public class Main{
  public static void main(String[] args) {
    Tilemaker tm = new Tilemaker(Integer.parseInt(args[0]));
    Tile t = tm.getTile();
    t.printAll();
    return;
  }
}
