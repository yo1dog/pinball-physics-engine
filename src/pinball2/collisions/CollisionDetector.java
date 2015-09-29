package pinball2.collisions;

import pinball2.solids.Solid;
import pinball2.solids.dynamics.DynamicCircle;
import pinball2.solids.dynamics.DynamicSolid;
import pinball2.solids.statics.StaticSolid;
import pinball2.solids.statics.StaticCircle;
import pinball2.solids.statics.StaticLine;

public class CollisionDetector {
  public static Collision detect(DynamicSolid solidA, Iterable<Solid> solidBs, double dTimeS) {
    Collision firstCollision = null;
    
    // for each other solid...
    for (Solid solidB: solidBs) {
      // check if a collision will occur
      Collision collision = detect(solidA, solidB, dTimeS);
      
      if (collision != null) {
        if (firstCollision == null || collision.dTimeS < firstCollision.dTimeS) {
          firstCollision = collision;
        }
      }
    }
    
    return firstCollision;
  }
  
  public static Collision detect(DynamicSolid solidA, Solid solidB, double dTimeS) {
    if (solidB instanceof StaticSolid)
      return detect(solidA, (StaticSolid)solidB, dTimeS);
    else
      return detect(solidA, (DynamicSolid)solidB, dTimeS);
  }
  
  // dynamic vs static
  public static Collision detect(DynamicSolid solidA, StaticSolid solidB, double dTimeS) {
    if (!solidA.collisionsEnabled || !solidB.collisionsEnabled)
      return null;
    
    if (solidA instanceof DynamicCircle) {
      if (solidB instanceof StaticCircle)
        return DynamicCircle_StaticCircle_CollisionDetector.detect((DynamicCircle)solidA, (StaticCircle)solidB, dTimeS);
      if (solidB instanceof StaticLine)
        return DynamicCircle_StaticLine_CollisionDetector.detect((DynamicCircle)solidA, (StaticLine)solidB, dTimeS);
    }
    
    // does not support collision detection between these two solids
    return null;
  }
  
  // dynamic vs dynamic
  public static Collision detect(DynamicSolid solidA, DynamicSolid solidB, double dTimeS) {
    if (!solidA.collisionsEnabled || !solidB.collisionsEnabled)
      return null;
    
    if (solidA instanceof DynamicCircle) {
      if (solidB instanceof DynamicCircle)
        return DynamicCircle_DynamicCircle_CollisionDetector.detect((DynamicCircle)solidA, (DynamicCircle)solidB, dTimeS);
    }
    
    // does not support collision detection between these two solids
    return null;
  }
}
