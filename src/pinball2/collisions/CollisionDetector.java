package pinball2.collisions;

import pinball2.solids.Solid;
import pinball2.solids.dynamics.DynamicCircle;
import pinball2.solids.dynamics.DynamicSolid;
import pinball2.solids.statics.StaticSolid;
import pinball2.solids.statics.StaticCircle;
import pinball2.solids.statics.StaticLine;

public class CollisionDetector {
  public static Collision detect(DynamicSolid solidA, Iterable<Solid> solidBs) {
    Collision firstCollision = null;
    
    // for each other solid...
    for (Solid solidB: solidBs) {
      // check if a collision will occur
      Collision collision = detect(solidA, solidB);
      
      if (collision != null) {
        if (firstCollision == null || collision.dTimeS < firstCollision.dTimeS) {
          firstCollision = collision;
        }
      }
    }
    
    return firstCollision;
  }
  
  public static Collision detect(DynamicSolid solidA, Solid solidB) {
    if (solidB instanceof StaticSolid)
      return detect(solidA, (StaticSolid)solidB);
    else
      return detect(solidA, (DynamicSolid)solidB);
  }
  
  // dynamic vs static
  public static Collision detect(DynamicSolid solidA, StaticSolid solidB) {
    if (!solidA.collisionsEnabled || !solidB.collisionsEnabled)
      return null;
    
    if (solidA instanceof DynamicCircle) {
      if (solidB instanceof StaticCircle)
        return DynamicCircle_StaticCircle_CollisionDetector.detect((DynamicCircle)solidA, (StaticCircle)solidB);
      if (solidB instanceof StaticLine)
        return DynamicCircle_StaticLine_CollisionDetector.detect((DynamicCircle)solidA, (StaticLine)solidB);
    }
    
    // does not support collision detection between these two solids
    return null;
  }
  
  // dynamic vs dynamic
  public static Collision detect(DynamicSolid solidA, DynamicSolid solidB) {
    if (!solidA.collisionsEnabled || !solidB.collisionsEnabled)
      return null;
    
    if (solidA instanceof DynamicCircle) {
      if (solidB instanceof DynamicCircle)
        return DynamicCircle_DynamicCircle_CollisionDetector.detect((DynamicCircle)solidA, (DynamicCircle)solidB);
    }
    
    // does not support collision detection between these two solids
    return null;
  }
}
