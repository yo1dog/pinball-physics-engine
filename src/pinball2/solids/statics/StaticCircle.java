package pinball2.solids.statics;

import pinball2.solids.SurfaceProperties;

public class StaticCircle extends StaticSolid {
  public final double x, y;
  public final double radius;
  
  public StaticCircle(double x, double y, double radius) {
    this(x, y, radius, new SurfaceProperties());
  }
  public StaticCircle(double x, double y, double radius, SurfaceProperties surfaceProperties) {
    super(surfaceProperties);
    
    this.x = x;
    this.y = y;
    this.radius = radius;
  }
}
