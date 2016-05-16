package pinball2.solids.statics;

import pinball2.Vector;
import pinball2.solids.Solid;
import pinball2.solids.SurfaceProperties;

public class StaticCircle extends Solid {
  public final Vector pos;
  public final double radius;
  
  public StaticCircle(Vector pos, double radius, SurfaceProperties surfaceProperties) {
    super(surfaceProperties);
    
    this.pos = pos;
    this.radius = radius;
  }
}
