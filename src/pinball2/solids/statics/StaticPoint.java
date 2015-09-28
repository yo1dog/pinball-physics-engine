package pinball2.solids.statics;

import pinball2.solids.SurfaceProperties;

public class StaticPoint extends StaticSolid {
  public final double x, y;
  
  public StaticPoint(double x, double y) {
    this(x, y, new SurfaceProperties());
  }
  public StaticPoint(double x, double y, SurfaceProperties surfaceProperties) {
    super(surfaceProperties);
    
    this.x = x;
    this.y = y;
  }
}
