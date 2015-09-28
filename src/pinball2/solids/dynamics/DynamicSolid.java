package pinball2.solids.dynamics;

import pinball2.Vector;
import pinball2.collisions.Collision;
import pinball2.solids.BodyProperties;
import pinball2.solids.Solid;
import pinball2.solids.SurfaceProperties;

public abstract class DynamicSolid extends Solid {
  public final BodyProperties bodyProperties;
  
  public DynamicSolid(SurfaceProperties surfaceProperties, BodyProperties bodyProperties) {
    super(surfaceProperties);
    
    this.bodyProperties = bodyProperties;
  }
  
  public abstract void move(double dTimeS, Collision collision);
  public abstract void accelerate(Vector acc, double dTimeS);
  public abstract void applyFriction(double cof, double cosf);
}
