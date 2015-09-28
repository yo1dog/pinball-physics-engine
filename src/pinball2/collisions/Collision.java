package pinball2.collisions;

import pinball2.Vector;
import pinball2.solids.Solid;

public class Collision {
  public final double dTimeS;
  public final Solid solidA;
  public final Solid solidB;
  public final Vector solidAAcc;
  public final Vector solidBAcc;
  
  public Collision(Solid solidA, Solid solidB, double dTimeS) {
    this.solidA = solidA;
    this.solidB = solidB;
    this.dTimeS = dTimeS;
    
    solidAAcc = new Vector(0, 0);
    solidBAcc = new Vector(0, 0);
  }
  
  private Collision(Collision collisionToSwap) {
    this.dTimeS = collisionToSwap.dTimeS;
    
    this.solidA = collisionToSwap.solidB;
    this.solidB = collisionToSwap.solidA;
    this.solidAAcc = collisionToSwap.solidBAcc;
    this.solidBAcc = collisionToSwap.solidAAcc;
  }
  
  public Collision swapSolids() {
    return new Collision(this);
  }
}
