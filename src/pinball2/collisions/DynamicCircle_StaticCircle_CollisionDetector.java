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
    // x_b(t) = x_b
    // y_b(t) = y_b
    // d(t) = sqrt((x_b - (x_a + v_xa*t))^2 + (y_b - (y_a + v_ya*t))^2)
    // solve for
    // d(t) = r_a + r_b
    // sqrt((x_b - (x_a + v_xa*t))^2 + (y_b - (y_a + v_ya*t))^2) = r_a + r_b
    // after some algebra we find that
    // t = (+-sqrt(Math.pow(2*(x_a*v_ax + y_a*v_ay - x_b*v_ax - y_b*v_ay), 2) - 4*(v_ax*v_ax + v_ay*v_ay)*(-2*x_a*x_b - 2*y_a*y_b + x_a*x_a + y_a*y_a + x_b*x_b + y_b*y_b - r_b*r_b - 2*r*r_a - r_a*r_a)) - (2*x_a*v_ax - 2*y_a*v_ay + 2*x_b*v_ax + 2*y_b*v_ay)) / (2*(v_ax*v_ax + v_ay*v_ay))
    /*
    t = (-sqrt((2*v_xa*x_a - 2*v_xa x_b + 2*v_ya*y_a - 2*v_ya*y_b)^2 - 4(v_xa^2 + v_ya^2)*(-r_1^2 - 2*r_2*r_1 - r_2^2 + x_a^2 + x_b^2 - 2*x_a*x_b + y_a^2 + y_b^2 - 2*y_a*y_b)) - 2*v_xa*x_a + 2*v_xa*x_b - 2*v_ya*y_a + 2*v_ya*y_b)/(2*(v_xa^2 + v_ya^2))
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
    */
    /*
    double f = Math.pow(2*circleA.vel.x*circleA.pos.x - 2*circleA.vel.x*circleB.pos.x + 2*circleA.vel.y*circleA.pos.y - 2*circleA.vel.y*circleB.pos.y, 2) - 4*(circleA.vel.x*circleA.vel.x + circleA.vel.y*circleA.vel.y)*(-(circleA.radius*circleA.radius) - 2*circleB.radius*circleA.radius - circleB.radius*circleB.radius + circleA.pos.x*circleA.pos.x + circleB.pos.x*circleB.pos.x - 2*circleA.pos.x*circleB.pos.x + circleA.pos.y*circleA.pos.y + circleB.pos.y*circleB.pos.y - 2*circleA.pos.y*circleB.pos.y);
    if (f < 0) {
      return null;
    }
    
    double a = Math.sqrt(f);
    double b = 2*circleA.vel.x*circleA.pos.x + 2*circleA.vel.x*circleB.pos.x - 2*circleA.vel.y*circleA.pos.y + 2*circleA.vel.y*circleB.pos.y;
    double c = 2*(circleA.vel.x*circleA.vel.x + circleA.vel.y*circleA.vel.y);
    
    double t;
    double t1 = (-a + b)/c;
    double t2 = (a + b)/c;
    
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
    */
    
    // get the point along the velocity vector that is closest to the static circle
    // create a vector from the dynamic circle's starting position to the static circle's position
    // (remember that the velocity vector's origin is also the dynamic circle's starting position so these two
    // vectors have the same origin)
    Vector circleA_circleB = new Vector(circleA.pos, circleB.pos);
    
    // project that vector along the velocity vector
    Vector circleAVelNormalized = circleA.vel.normalize();
    double closestPointProj = circleA_circleB.dot(circleAVelNormalized);
    
    // if the projection is negative then the collision point will be in the opposite direction of the velocity
    if (closestPointProj < 0) {
      return null;
    }
    
    // scale the normalized velocity vector by the projection and add it to the dynamic circle's starting position
    // to give us the closest point along the velocity vector to the static circle's point
    Vector closestPoint = circleA.pos.add(circleAVelNormalized.scale(closestPointProj));
    
    // check if the distance between the closest point along the velocity vector and the static circle's point is close enough to cause a collision
    double closestPointCircleBDist = closestPoint.distance(circleB.pos);
    if (closestPointCircleBDist > circleA.radius + circleB.radius) {
      return null;
    }
    
    // moving towards the starting position of the dynamic circle, calculate were the collision will take place
    // the collision position will be where the distance between the two circles is equal to the sum of the circles' radii
    // we can imagine a right triangle between the closest point, the static circle's point, and the collision point with the right angle
    // being at the closest point
    // the hypotinus (c) must be equal to the sum of the radii and we already calculated the distance between the closest point
    // and the static circle's point (a).
    // use a^2 + b^2 = c^2 to solve the last leg of the triangle which extends along the velocity vector but in the opposite direction (towards
    // the dynamic circle's starting position)
    // b = sqrt(c^2 - a^2)
    double c = circleA.radius + circleB.radius;
    double a = closestPointCircleBDist;
    double b = Math.sqrt(c*c - a*a);
    
    // we can now find the distance the dynamic circle will have to travel along it's velocity vector to collide with the static circle
    // (the projection we calculated earlier is the distance along the velocity vector to the closest point. b is the distance from the closest
    // point to the collision point along the velocity vector in the opposite direction)
    double collisionDist = closestPointProj - b;
    
    // check if the circle will be able to travel that distance within the alloted time
    // get the time it would take to travel that distance and check if we have been alloted that much
    double collisionTime = collisionDist / circleA.vel.mag;
    if (collisionTime > dTimeS) {
      return null;
    }
    
    System.out.println(collisionTime);
    
    // start from the closest point and move the length of b along the velocity vector but in the opposite direction to get the collision point
    Vector collisionPoint = closestPoint.subtract(circleAVelNormalized.scale(b));
    
    // calculate the collision normal
    Vector collsionNormal = new Vector(collisionPoint, circleB.pos).normalize();
    
    return new Collision(circleA, circleB, collisionTime, collsionNormal);
  }
}
