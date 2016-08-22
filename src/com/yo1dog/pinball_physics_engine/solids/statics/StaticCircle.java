package com.yo1dog.pinball_physics_engine.solids.statics;

import com.yo1dog.pinball_physics_engine.Vector;
import com.yo1dog.pinball_physics_engine.solids.Solid;
import com.yo1dog.pinball_physics_engine.solids.SurfaceProperties;

public class StaticCircle extends Solid {
  public final Vector pos;
  public final double radius;
  
  public StaticCircle(Vector pos, double radius, SurfaceProperties surfaceProperties) {
    super(surfaceProperties);
    
    this.pos = pos;
    this.radius = radius;
  }
}
