package pinball2.collisions;

import java.util.ArrayList;

import pinball2.solids.Solid;
import pinball2.solids.dynamics.DynamicCircle;
import pinball2.solids.statics.StaticCircle;
import pinball2.solids.statics.StaticSegment;

public class CollisionDetector {
  public static ArrayList<Collision> detect(ArrayList<Solid> solids, double durS) {
    ArrayList<Collision> earliestConcurrentCollisions = new ArrayList<Collision>();
    double earliestCollisionDTimeS = -1;
    
    // check for collisions that are within the given duration
    double collisionDectDurS = durS;
    
    // for each solid...
    for (int i = 0; i < solids.size() - 1; ++i) {
      // and each other solid...
      for (int j = i + 1; j < solids.size(); ++j) {
        Solid solidA = solids.get(i);
        Solid solidB = solids.get(j);
        
        // check if a collision will occur between the two solids
        Collision collision = detect(solidA, solidB, collisionDectDurS);
        
        if (collision == null) {
          continue;
        }
        
        // check if the collision is earlier than the current earliest collisions
        // (or if there is no current earliest collision)
        if (earliestConcurrentCollisions.size() == 0 || collision.dTimeS < earliestCollisionDTimeS) {
          // set the collision as the new earliest collision and reset the list of concurrent collisions
          earliestCollisionDTimeS = collision.dTimeS;
          earliestConcurrentCollisions.clear();
          earliestConcurrentCollisions.add(collision);
          
          // since we are looking for the earliest collision, set the collision detection duration
          // to the time when the collision occurred. This way going forward we will only detect collisions
          // that happened before or at the same time as this one.
          collisionDectDurS = earliestCollisionDTimeS;
        }
        // check if the collision is at the same time as the current earliest collisions
        else if (collision.dTimeS == earliestCollisionDTimeS) {
          earliestConcurrentCollisions.add(collision);
        }
      }
    }
    
    return earliestConcurrentCollisions;
  }

  public static Collision detect(Solid solidA, Solid solidB, double durS) {
    if (!solidA.collisionsEnabled || !solidB.collisionsEnabled) {
      return null;
    }
    
    DynamicCircle dynCircle;
    Solid otherSolid;
    
    if (solidA instanceof DynamicCircle) {
      dynCircle = (DynamicCircle)solidA;
      otherSolid = solidB;
    }
    else if (solidB instanceof DynamicCircle) {
      dynCircle = (DynamicCircle)solidB;
      otherSolid = solidA;
    }
    else {
      return null;
    }
    
    Collision collision = null;
    
    if (otherSolid instanceof StaticCircle) {
      collision = DynamicCircle_StaticCircle_CollisionDetector.detect(dynCircle, (StaticCircle)otherSolid, durS);
    }
    else if (otherSolid instanceof StaticSegment) {
      collision = DynamicCircle_StaticSegment_CollisionDetector.detect(dynCircle, (StaticSegment)otherSolid, durS);
    }
    else if (otherSolid instanceof DynamicCircle) {
      collision = DynamicCircle_DynamicCircle_CollisionDetector.detect(dynCircle, (DynamicCircle)otherSolid, durS);
    }
    else {
      // does not support collision detection between these two solids
    }
    
    if (collision == null) {
      return null;
    }
    
    //check if the collision is significant enough to register
    // this avoids problems with rounding errors on very small numbers (eg. 1 + 0.5E-18 == 1)
    if (
      collision.dynCircleAAcc.mag < 0.0001d &&
      (collision.dynCircleBAcc     == null || collision.dynCircleBAcc    .mag < 0.0001d) &&
      (collision.dynCircleAImpulse == null || collision.dynCircleAImpulse.mag < 0.0001d) &&
      (collision.dynCircleBImpulse == null || collision.dynCircleBImpulse.mag < 0.0001d)
    ) {
      return null;
    }
    
    return collision;
  }
}
