package pinball2.solids.dynamics;

import pinball2.Vector;
import pinball2.solids.BodyProperties;
import pinball2.solids.SurfaceProperties;

public class DynamicCircle extends DynamicSolid {
  public final double radius;
  
  public DynamicCircle(
    Vector pos, double radius,
    Vector vel,
    SurfaceProperties surfaceProperties, BodyProperties bodyProperties
  ) {
    super(pos, vel, surfaceProperties, bodyProperties);
    
    this.radius = radius;
  }
}
