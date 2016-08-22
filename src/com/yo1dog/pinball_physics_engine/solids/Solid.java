package com.yo1dog.pinball_physics_engine.solids;

import com.yo1dog.pinball_physics_engine.props.Prop;
import com.yo1dog.pinball_physics_engine.solids.SurfaceProperties;

public abstract class Solid {
  public Prop parentProp;
  public SurfaceProperties surfaceProperties;
  public boolean collisionsEnabled = true;
  
  public Solid(SurfaceProperties surfaceProperties) {
    this.surfaceProperties = surfaceProperties;
  }
}
