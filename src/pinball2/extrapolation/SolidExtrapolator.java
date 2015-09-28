package pinball2.extrapolation;

import java.util.ArrayList;

import pinball2.collisions.Collision;
import pinball2.collisions.CollisionDetector;
import pinball2.components.Board;
import pinball2.solids.Solid;
import pinball2.solids.dynamics.DynamicSolid;

public class SolidExtrapolator {
  public static ArrayList<Collision> extrapolate(Board board, ArrayList<DynamicSolid> dynamicSolids, ArrayList<Solid> solids, double dTimeS) {
    ArrayList<Collision> collisions = new ArrayList<Collision>();
    
    Collision collision;
    while((collision = extrapolateToFirstCollision(dynamicSolids, solids, dTimeS)) != null) {
      collisions.add(collision);
    }
    
    // apply gravity
    for (DynamicSolid dynamicSolid: dynamicSolids) {
      dynamicSolid.accelerate(board.gravityAcc);
    }
    
    // apply board friction
    
    
    
    return collisions;
  }
  
  public static Collision extrapolateToFirstCollision(ArrayList<DynamicSolid> dynamicSolids, ArrayList<Solid> solids, double maxDTimeS) {
    Collision firstCollision = null;
    
    // for each dynamic solid...
    for (DynamicSolid dynamicSolid: dynamicSolids) {
      // check if the solid will collide with another solid
      Collision collision = CollisionDetector.detect(dynamicSolid, solids);
      
      if (collision != null) {
        if (firstCollision == null || collision.dTimeS < firstCollision.dTimeS) {
          firstCollision = collision;
        }
      }
    }
    
    // extrapolate until the first collision or for the remainder of the time
    double dTimeS;
    if (firstCollision != null) {
      dTimeS = firstCollision.dTimeS;
    }
    else {
      dTimeS = maxDTimeS;
    }
    
    // for each dynamic solid...
    for (DynamicSolid dynamicSolid: dynamicSolids) {
      // move the solid
      // apply the collision if it involves the solid
      Collision collision = null;
      if (firstCollision.solidA == dynamicSolid) {
        collision = firstCollision;
      }
      else if (firstCollision.solidB == dynamicSolid) {
        collision = firstCollision.swapSolids();
      }
      
      dynamicSolid.move(dTimeS, collision);
    }
    
    return firstCollision;
  }
}
