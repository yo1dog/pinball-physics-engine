package pinball2.solids.dynamics;

import pinball2.Vector;
import pinball2.solids.BodyProperties;
import pinball2.solids.Solid;
import pinball2.solids.SurfaceProperties;

public abstract class DynamicSolid extends Solid {
  public final BodyProperties bodyProperties;
  public Vector pos;
  public Vector vel;
  
  public DynamicSolid(Vector pos, Vector vel, SurfaceProperties surfaceProperties, BodyProperties bodyProperties) {
    super(surfaceProperties);
    
    this.pos = pos;
    this.vel = vel;
    this.bodyProperties = bodyProperties;
  }
  
  public void move(double dTimeS) {
    // x_f = x_i + v*t
    pos = pos.add(vel.scale(dTimeS));
  }
  
  public void applyFriction(Vector normalAcc, double cof, double dTimeS) {
    if (vel.mag == 0) {
      return;
    }
    
    // F = m*a
    // a = F/m
    // F_µ = µ*N
    // a_µ = F_µ/m
    // a_µ = µ*N/m
    // a_µ = µ*m*a_n/m
    // a_µ = µ*a_n
    double a_cof = cof*normalAcc.mag;
    
    // Δv = a*t
    // Δv = a_µ*t
    double dv = a_cof*dTimeS;
    
    
    // check if this should cause the solid to stop
    if (Math.abs(dv) > Math.abs(vel.mag)) {
      vel = new Vector(0.0d, 0.0d);
    }
    else {
      // v_f = v_i + Δv
      // apply in the opposite direction of motion
      vel = vel.subtract(vel.normalize().scale(dv));
    }
  }
  
  public void accelerate(Vector acc, double dTimeS) {
    // v_f = v_i + at
    vel = vel.add(acc.scale(dTimeS));
  }
}
