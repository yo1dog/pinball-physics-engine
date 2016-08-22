package com.yo1dog.pinball_physics_engine.solids.dynamics;

import com.yo1dog.pinball_physics_engine.Vector;
import com.yo1dog.pinball_physics_engine.solids.Solid;
import com.yo1dog.pinball_physics_engine.solids.SurfaceProperties;

public abstract class DynamicSolid extends Solid {
  public DynamicSolid(SurfaceProperties surfaceProperties) {
    super(surfaceProperties);
  }
  
  public abstract void move(double dTimeS);
  public abstract void applyTableNormal(Vector tableNormalAcc, double tableCOF, double dTimeS);
}
