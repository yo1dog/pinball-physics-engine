package pinball2.solids.statics;

import pinball2.Vector;
import pinball2.solids.SurfaceProperties;

public class StaticPoint extends StaticSolid {
  public final Vector pos;
  
  public StaticPoint(Vector pos, SurfaceProperties surfaceProperties) {
    super(surfaceProperties);
    
    this.pos = pos;
  }
}
