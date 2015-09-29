package pinball2.collisions;

import pinball2.Vector;
import pinball2.solids.dynamics.DynamicCircle;
import pinball2.solids.statics.StaticCircle;

public class DynamicCircle_StaticCircle_CollisionDetector {
  public static Collision detect(DynamicCircle circleA, StaticCircle circleB, double dTimeS) {
    if (circleA.vel.mag == 0) {
      return null;
    }
    
    // solve for the possible times of collision
    // find the time when the distance between the two circles' centers is equal to the sum of radii of the circles
    // in vector match this is expressed as
    // ||(p_a + v_a*t) - p_b|| = r_a + r_b
    // but we can do it with linear algebra too
    // d = sqrt((x2 - x1)^2 + (y2 - y1)^2)
    // d(t) = sqrt((x_b(t) - x_a(t))^2 + (y_b(t) - y_a(t))^2)
    // x_a(t) = x_a + v_xa*t
    // y_a(t) = y_a + v_ya*t
    // x_b(t) = x_b + v_xb*t
    // y_b(t) = y_b + v_yb*t
    // d(t) = sqrt(((x_b + v_xb*t) - (x_a + v_xa*t))^2 + ((y_b + v_yb*t) - (y_a + v_ya*t))^2)
    // solve for
    // d(t) = r_a + r_b
    // sqrt(((x_b + v_xb*t) - (x_a + v_xa*t))^2 + ((y_b + v_yb*t) - (y_a + v_ya*t))^2) = r_a + r_b
    // after some algebra we find that
    // t = (+-sqrt(Math.pow(2*(x_a*v_ax + y_a*v_ay - x_b*v_ax - y_b*v_ay), 2) - 4*(v_ax*v_ax + v_ay*v_ay)*(-2*x_a*x_b - 2*y_a*y_b + x_a*x_a + y_a*y_a + x_b*x_b + y_b*y_b - r_b*r_b - 2*r*r_a - r_a*r_a)) - (2*x_a*v_ax - 2*y_a*v_ay + 2*x_b*v_ax + 2*y_b*v_ay)) / (2*(v_ax*v_ax + v_ay*v_ay))
    
    double f = Math.pow(2*(circleA.pos.x*circleA.vel.x + circleA.pos.y*circleA.vel.y - circleB.pos.x*circleA.vel.x - circleB.pos.y*circleA.vel.y), 2) - 4*(circleA.vel.x*circleA.vel.x + circleA.vel.y*circleA.vel.y)*(-2*circleA.pos.x*circleB.pos.x - 2*circleA.pos.y*circleB.pos.y + circleA.pos.x*circleA.pos.x + circleA.pos.y*circleA.pos.y + circleB.pos.x*circleB.pos.x + circleB.pos.y*circleB.pos.y - circleB.radius*circleB.radius - 2*circleB.radius*circleA.radius - circleA.radius*circleA.radius);
    if (f < 0) {
      return null;
    }
    
    double a = Math.sqrt(f);
    double b = -2*circleA.pos.x*circleA.vel.x - 2*circleA.pos.y*circleA.vel.y + 2*circleB.pos.x*circleA.vel.x + 2*circleB.pos.y*circleA.vel.y;
    double d = 2*(circleA.vel.x*circleA.vel.x + circleA.vel.y*circleA.vel.y);
    
    double t;
    double t1 = (a + b) / d;
    double t2 = (-a + b) / d;
    
    // both possible times of collision must be in the future
    if (t1 < 0 || t2 < 0) {
      return null;
    }
    
    // choose the time that is soonest
    if (t1 < t2) {
      t = t1;
    } 
    else {
      t = t2;
    }
    
    // make sure the collision will happen during the alloted time
    if (t > dTimeS) {
      return null;
    }
    
    // calculate the collision normal
    // get the position of circle A at the time of the collision
    // x = x + v*t
    Vector circleACollisionPos = circleA.pos.add(circleA.vel.scale(t));
    Vector collsionNormal = circleACollisionPos.getNormal(circleB.pos);
    
    return new Collision(circleA, circleB, t, collsionNormal);
  }
}
