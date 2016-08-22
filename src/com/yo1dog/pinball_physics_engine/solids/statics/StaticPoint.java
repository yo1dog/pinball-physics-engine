package com.yo1dog.pinball_physics_engine.solids.statics;

import com.yo1dog.pinball_physics_engine.Vector;
import com.yo1dog.pinball_physics_engine.solids.Solid;
import com.yo1dog.pinball_physics_engine.solids.SurfaceProperties;

public class StaticPoint extends Solid {
  public final Vector pos;
  
  public StaticPoint(Vector pos, SurfaceProperties surfaceProperties) {
    super(surfaceProperties);
    
    this.pos = pos;
  }
}
