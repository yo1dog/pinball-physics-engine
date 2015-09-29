package pinball2.extrapolation;

import java.util.ArrayList;

import pinball2.collisions.Collision;
import pinball2.collisions.CollisionDetector;
import pinball2.solids.Solid;
import pinball2.solids.dynamics.DynamicSolid;
import pinball2.tables.Table;

public class SolidExtrapolator {
  public static ArrayList<Collision> extrapolate(Table board, ArrayList<DynamicSolid> dynamicSolids, ArrayList<Solid> solids, double dTimeS) {
    ArrayList<Collision> collisions = new ArrayList<Collision>();
    
    Collision collision;
    do {
      collision = extrapolateToFirstCollision(dynamicSolids, solids, dTimeS);
      
      if (collision != null) {
        collisions.add(collision);
      }
    }
    while(collision != null && collisions.size() < 3);
    
    // for each dynamic solid...
    for (DynamicSolid dynamicSolid: dynamicSolids) {
      // apply table normal acceleration
      dynamicSolid.accelerate(board.normalAcc, dTimeS);
      
      // apply table friction
      dynamicSolid.applyFriction(board.normalAcc, board.surfaceProperties.cof, dTimeS);
    }
    
    return collisions;
  }
  
  public static Collision extrapolateToFirstCollision(ArrayList<DynamicSolid> dynamicSolids, ArrayList<Solid> solids, double maxDTimeS) {
    Collision earliestCollision = null;
    
    // for each dynamic solid...
    for (DynamicSolid dynamicSolid: dynamicSolids) {
      // check if the solid will collide with another solid
      Collision collision = CollisionDetector.detect(dynamicSolid, solids, maxDTimeS);
      
      if (collision != null) {
        if (earliestCollision == null || collision.dTimeS < earliestCollision.dTimeS) {
          earliestCollision = collision;
        }
      }
    }
    
    // extrapolate until the earliest collision or for the remainder of the time
    double dTimeS;
    if (earliestCollision != null) {
      dTimeS = earliestCollision.dTimeS;
    }
    else {
      dTimeS = maxDTimeS;
    }
    
    // for each dynamic solid...
    for (DynamicSolid dynamicSolid: dynamicSolids) {
      // move the solid
      dynamicSolid.move(dTimeS);
    }
    
    if (earliestCollision == null) {
      return null;
    }
    
    // apply the collision
    earliestCollision.solidA.vel.add(earliestCollision.solidADVel);
    
    if (earliestCollision.solidB instanceof DynamicSolid) {
      ((DynamicSolid)earliestCollision.solidB).vel.add(earliestCollision.solidBDVel);
    }
    
    return earliestCollision;
  }
}
