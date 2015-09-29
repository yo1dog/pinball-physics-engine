package pinball2.solids.statics;

import pinball2.Vector;
import pinball2.solids.SurfaceProperties;

public class StaticLine extends StaticSolid {
  public final Vector end1, end2;
  
  public StaticLine(Vector end1, Vector end2, SurfaceProperties surfaceProperties) {
    super(surfaceProperties);
    
    this.end1 = end1;
    this.end2 = end2;
  }
}
