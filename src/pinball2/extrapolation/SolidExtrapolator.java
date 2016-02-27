package pinball2.extrapolation;

import java.util.ArrayList;

import pinball2.Vector;
import pinball2.collisions.Collision;
import pinball2.collisions.CollisionDetector;
import pinball2.solids.Solid;
import pinball2.solids.dynamics.DynamicSolid;
import pinball2.tables.Table;

public class SolidExtrapolator {
  public static ArrayList<Collision> extrapolate(Table table, ArrayList<DynamicSolid> dynamicSolids, ArrayList<Solid> solids, double dTimeS) {
    // for each dynamic solid...
    for (DynamicSolid dynamicSolid: dynamicSolids) {
      // apply table normal acceleration
      dynamicSolid.accelerate(table.normalAcc, dTimeS);
      
      // apply table friction
      dynamicSolid.applyFriction(table.normalAcc, table.surfaceProperties.cof, dTimeS);
    }
    
    
    ArrayList<Collision> collisions = new ArrayList<Collision>();
    
    // the max time we should detect collisions and extrapolate for starts at the total time we are extrapolating for
    double maxDTimeS = dTimeS;
    
    Collision collision;
    // TODO: should loop while time remains instead of by collisions
    do {
      // extrapolate to the first collision
      collision = extrapolateToFirstCollision(dynamicSolids, solids, maxDTimeS);
      
      if (collision != null) {
        // if there was a collision, subtract time that was used from the max time
        maxDTimeS -= collision.dTimeS;
        collisions.add(collision);
      }
    }
    // continue to extrapolate until no collision occurred
    while(collision != null);
    
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
    earliestCollision.solidA.vel = earliestCollision.solidA.vel.add(earliestCollision.solidADVel);
    
    if (earliestCollision.solidB instanceof DynamicSolid) {
      DynamicSolid dynSolidB = (DynamicSolid)earliestCollision.solidB;
      
      dynSolidB.vel = dynSolidB.vel.add(earliestCollision.solidBDVel);
    }
    
    return earliestCollision;
    
  }
}
