package pinball2.solids;

import pinball2.props.Prop;
import pinball2.solids.SurfaceProperties;

public abstract class Solid {
  public Prop parentProp;
  public SurfaceProperties surfaceProperties;
  public boolean collisionsEnabled = true;
  
  public Solid(SurfaceProperties surfaceProperties) {
    this.surfaceProperties = surfaceProperties;
  }
}
