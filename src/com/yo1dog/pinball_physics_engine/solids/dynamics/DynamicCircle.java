package com.yo1dog.pinball_physics_engine.solids.dynamics;

import com.yo1dog.pinball_physics_engine.Vector;
import com.yo1dog.pinball_physics_engine.solids.BodyProperties;
import com.yo1dog.pinball_physics_engine.solids.SurfaceProperties;

public class DynamicCircle extends DynamicSolid {
  public Vector pos;
  public double radius;
  public Vector vel;
  public BodyProperties bodyProperties;
  
  public DynamicCircle(
    Vector pos, double radius,
    Vector vel,
    SurfaceProperties surfaceProperties, BodyProperties bodyProperties
  ) {
    super(surfaceProperties);
    
    this.pos = pos;
    this.radius = radius;
    this.vel = vel;
    this.bodyProperties = bodyProperties;
  }
  
  @Override
  public void move(double dTimeS) {
    // x_f = x_i + v*t
    pos = pos.add(vel.scale(dTimeS));
  }
  
  @Override
  public void applyTableNormal(Vector tableNormalAcc, double tableCOF, double dTimeS) {
    // v_f = v_i + a*t
    vel = vel.add(tableNormalAcc.scale(dTimeS));
    
    if (vel.mag == 0) {
      return;
    }
    
    // apply friction
    double cof = this.surfaceProperties.cof + tableCOF;
    
    // F = m*a
    // a = F/m
    // F_µ = µ*N
    // a_µ = F_µ/m
    // a_µ = µ*N/m
    // a_µ = µ*m*a_n/m
    // a_µ = µ*a_n
    double a_cof = cof*tableNormalAcc.mag;
    
    // Δv = a*t
    // Δv = a_µ*t
    double dv = a_cof*dTimeS;
    
    // check if this should cause the solid to stop
    if (Math.abs(dv) > vel.mag) {
      vel = new Vector(0.0d, 0.0d);
    }
    else {
      // v_f = v_i + Δv
      // apply in the opposite direction of motion
      vel = vel.subtract(vel.normalize().scale(dv));
    }
  }
}
