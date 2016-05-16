package pinball2.solids.dynamics;

import pinball2.Vector;
import pinball2.solids.Solid;
import pinball2.solids.SurfaceProperties;

public abstract class DynamicSolid extends Solid {
  public DynamicSolid(SurfaceProperties surfaceProperties) {
    super(surfaceProperties);
  }
  
  public abstract void move(double dTimeS);
  public abstract void applyTableNormal(Vector tableNormalAcc, double tableCOF, double dTimeS);
}
