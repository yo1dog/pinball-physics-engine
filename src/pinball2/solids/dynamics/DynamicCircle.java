package pinball2.solids.dynamics;

import pinball2.Vector;
import pinball2.collisions.Collision;
import pinball2.solids.BodyProperties;
import pinball2.solids.SurfaceProperties;

public class DynamicCircle extends DynamicSolid {
  public Vector pos;
  public final double radius;
  public Vector vel;
  
  public DynamicCircle(
    Vector pos, double radius
  ) {
    this(
      pos, radius,
      new Vector(0.0d, 0.0d)
    );
  }
  public DynamicCircle(
    Vector pos, double radius,
    Vector vel
  ) {
    this(
      pos, radius,
      vel,
      new SurfaceProperties(), new BodyProperties()
    );
  }
  public DynamicCircle(
    Vector pos, double radius,
    Vector vel,
    SurfaceProperties surfaceProperties, BodyProperties bodyProperties
  ) {
    super(surfaceProperties, bodyProperties);
    
    this.pos = pos;
    this.radius = radius;
    this.vel = vel;
  }
  
  @Override
  public void move(double dTimeS, Collision collision) {
    // x = x + vt
    pos = pos.add(vel.scale(dTimeS));
    
    if (collision != null) {
      // v = v + instantaneous acceleration
      vel = vel.add(collision.solidAAcc);
    }
  }
  
  public void applyFriction(double gravity, double cof, double cosf, double dTimeS) {
    // F_µ = µmg
    // a = F/m
    // a_µ = F_µ/m
    // a_µ = µmg/m
    // a_µ = µg
    double a_cof = cof * gravity;
    
    // Δv = at
    // Δv = (a_µ)t
    double dv = a_cof*dTimeS;
    
    // v_f = v_i + Δv
    // apply in the opposite direction of motion
    // shortcut for vel.substract(vel.normalize().scale(dv))
    vel = new Vector(
      vel.x - (vel.x / vel.mag)*dv,
      vel.y - (vel.y / vel.mag)*dv
    );
  }
  
  @Override
  public void accelerate(Vector acc, double dTimeS) {
    // v_f = v_i + at
    vel = vel.add(acc.scale(dTimeS));
  }
}
