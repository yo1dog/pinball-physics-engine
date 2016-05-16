package pinball2.collisions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import pinball2.Vector;
import pinball2.solids.Solid;
import pinball2.solids.dynamics.DynamicCircle;

public class Collision {
  public final DynamicCircle dynCircle;
  public final Solid solid;
  public final double dTimeS;
  public final Vector normal; // collision normal that is always from solidA side of the collision plane to solidB side
  public final Vector poc;
  public final Vector dynCircleCollisionForce;
  public final Vector solidCollisionForce;
  public final Vector dynCircleAAcc;
  public final Vector dynCircleBAcc;
  public final Vector dynCircleAImpulse;
  public final Vector dynCircleBImpulse;
  
  public Collision(DynamicCircle dynCircle, Solid solid, Vector normal, Vector poc, double dTimeS) {
    this(dynCircle, solid, normal, poc, dTimeS, null, null, false);
  }
  public Collision(DynamicCircle dynCircle, Solid solid, Vector normal, Vector poc, Vector dynCircleAImpulse) {
    this(dynCircle, solid, normal, poc, dynCircleAImpulse, false);
  }
  public Collision(DynamicCircle dynCircle, Solid solid, Vector normal, Vector poc, Vector dynCircleAImpulse, boolean impulseOnly) {
    this(dynCircle, solid, normal, poc, dynCircleAImpulse, null, impulseOnly);
  }
  public Collision(DynamicCircle dynCircle, Solid solid, Vector normal, Vector poc, Vector dynCircleAImpulse, Vector dynCircleBImpulse) {
    this(dynCircle, solid, normal, poc, dynCircleAImpulse, dynCircleBImpulse, false);
  }
  public Collision(DynamicCircle dynCircle, Solid solid, Vector normal, Vector poc, Vector dynCircleAImpulse, Vector dynCircleBImpulse, boolean impulseOnly) {
    this(dynCircle, solid, normal, poc, 0, dynCircleAImpulse, dynCircleBImpulse, impulseOnly);
  }
  
  public Collision(DynamicCircle dynCircle, Solid solid, Vector normal, Vector poc, double dTimeS, Vector dynCircleAImpulse, Vector dynCircleBImpulse, boolean impulseOnly) {
    this.dynCircle = dynCircle;
    this.solid = solid;
    this.normal = normal;
    this.poc = poc;
    this.dTimeS = dTimeS;
    this.dynCircleAImpulse = dynCircleAImpulse;
    this.dynCircleBImpulse = dynCircleBImpulse;
    
    if (dTimeS < 0)
      throw new IllegalArgumentException("Collision dTimeS can not be negative.");
    
    if (impulseOnly)  {
      dynCircleAAcc = Vector.zero;
      dynCircleBAcc = Vector.zero;
      dynCircleCollisionForce = Vector.zero;
      solidCollisionForce = Vector.zero;
    }
    else if (solid instanceof DynamicCircle) {
      DynamicCircle dynCircleA = dynCircle;
      DynamicCircle dynCircleB = (DynamicCircle)solid;
      
      double a_m = dynCircleA.bodyProperties.mass;
      double b_m = dynCircleB.bodyProperties.mass;
      
      // project the velocities along the collision normal
      double a_vi = dynCircleA.vel.dot(normal);
      double b_vi = dynCircleB.vel.dot(normal);
      
      // conservation of momentum states
      // p_i = p_f
      // so in our system of solids a and b
      // a_pi + b_pi = a_pf + b_pf
      // momentum is given by
      // p = m*v
      // a_m*a_vi + b_m*b_vi = a_m*a_vf + b_m*b_vf
      
      // conservation of kenetic energy states
      // E_ki = E_kf
      // so in our system of solids a and b
      // a_Eki + b_Eki = a_Ekf + b_Ekf
      // kenetic energy of a mass in motion is given by
      // E_k = 1/2*m*v^2
      // 1/2*a_m*a_vi^2 + 1/2*b_m*b_vi^2 = 1/2*a_m*a_vf^2 + 1/2*b_m*b_vf^2
      
      // using these two formulas and some algebra we find that
      // a_vf = (a_vi*(a_m - b_m) + 2*b_m*b_vi)/(a_m + b_m)
      // b_vf = (b_vi*(b_m - a_m) + 2*a_m*a_vi)/(a_m + b_m)
      // or
      // b_vf = (a_m*a_vi + b_m*b_vi - a_m*a_vf)/b_m
      // TODO:
      double a_vf = (a_vi*(a_m - b_m) + 2*b_m*b_vi)/(a_m + b_m);
      double b_vf = (a_m*a_vi + b_m*b_vi - a_m*a_vf)/b_m;
      
      // apply COR
      //a_vf *= dynCircleA.surfaceProperties.cor * dynCircleB.surfaceProperties.cor;
      //b_vf *= dynCircleA.surfaceProperties.cor * dynCircleB.surfaceProperties.cor;
      
      // v_f = v_i + Δv
      // Δv = v_f - f_i
      double a_a = a_vf - a_vi;
      double b_a = b_vf - b_vi;
      
      dynCircleAAcc = normal.scale(a_a);
      dynCircleBAcc = normal.scale(b_a);
      
      dynCircleCollisionForce = normal.scale(b_vi * b_m);
      solidCollisionForce     = normal.scale(a_vi * a_m);
    }
    else {
      // project the circle's velocity along the collision normal
      double a_vi = dynCircle.vel.dot(normal);
      
      // the other solid is static (infinite mass) so the velocity simply switches directions
      double a_vf = -a_vi;
      
      // apply COR
      a_vf *= dynCircle.surfaceProperties.cor * solid.surfaceProperties.cor;
      
      // v_f = v_i + Δv
      double a_dv = a_vf - a_vi;
      
      dynCircleAAcc = normal.scale(a_dv);
      dynCircleBAcc = null;
      
      solidCollisionForce     = normal.scale(a_vi * dynCircle.bodyProperties.mass);
      dynCircleCollisionForce = solidCollisionForce.invert();
    }
  }
  
  public void draw(Graphics2D g2d) {
    if (dynCircleAAcc != null) {
      g2d.setColor(Color.BLUE);
      g2d.draw(new Line2D.Double(
        poc.x, poc.y,
        poc.x + dynCircleAAcc.x, poc.y + dynCircleAAcc.y
      ));
    }
    if (dynCircleAImpulse != null) {
      g2d.setColor(Color.RED);
      g2d.draw(new Line2D.Double(
        dynCircle.pos.x,
        dynCircle.pos.y,
        dynCircle.pos.x + dynCircleAImpulse.x,
        dynCircle.pos.y + dynCircleAImpulse.y
      ));
    }
    if (dynCircleBAcc != null) {
      g2d.setColor(Color.ORANGE);
      g2d.draw(new Line2D.Double(
        poc.x, poc.y,
        poc.x + dynCircleBAcc.x, poc.y + dynCircleBAcc.y
      ));
    }
  }
}
