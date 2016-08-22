package com.yo1dog.pinball_physics_engine.solids;

public class SurfaceProperties {
  public final double cof; // Coefficient of Friction
  public final double cor; // Coefficient of Restitution - How "bouncy" the surface is
  
  public SurfaceProperties() {
    this(0.0d, 1.0d);
  }
  public SurfaceProperties(double cof, double cor) {
    this.cof = cof;
    this.cor = cor;
  }
}
