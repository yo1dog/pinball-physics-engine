package com.yo1dog.pinball_physics_engine.solids.statics;

import com.yo1dog.pinball_physics_engine.Vector;
import com.yo1dog.pinball_physics_engine.solids.Solid;
import com.yo1dog.pinball_physics_engine.solids.SurfaceProperties;

public class StaticSegment extends Solid {
  public final Vector p1, p2;
  public final Vector p1p2;
  public final Vector normal;
  
  public StaticSegment(Vector p1, Vector p2, SurfaceProperties surfaceProperties) {
    super(surfaceProperties);
    
    this.p1 = p1;
    this.p2 = p2;
    this.p1p2 = new Vector(p1, p2);
    this.normal = p1p2.getNormal();
  }
}
