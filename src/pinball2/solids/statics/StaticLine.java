package pinball2.solids.statics;

import pinball2.solids.SurfaceProperties;

public class StaticLine extends StaticSolid {
  public final double x1, y1, x2, y2;
  
  public StaticLine(double x1, double y1, double x2, double y2) {
    this(x1, y1, x2, y2, new SurfaceProperties());
  }
  public StaticLine(double x1, double y1, double x2, double y2, SurfaceProperties surfaceProperties) {
    super(surfaceProperties);
    
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }
}
