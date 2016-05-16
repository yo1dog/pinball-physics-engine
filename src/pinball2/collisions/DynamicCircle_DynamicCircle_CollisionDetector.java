package pinball2.collisions;

import pinball2.Vector;
import pinball2.solids.dynamics.DynamicCircle;

public class DynamicCircle_DynamicCircle_CollisionDetector {
  public static Collision detect(DynamicCircle circleA, DynamicCircle circleB, double durS) {
    // subtract the velocity of circle B from circle A to make circle B relatively still compared
    // to circle A. Then we can use our dynamic circle/static circle collision detection
    Vector circleAVel = circleA.vel.subtract(circleB.vel);
    
    double circleACollisionPointDist = DynamicCircle_StaticCircle_CollisionDetector.detectCollisionDist(
      circleA.pos, circleA.radius, circleAVel,
      circleB.pos, circleB.radius
    );
    
    if (circleACollisionPointDist == -1) {
      return null;
    }
    
    // calculate the collision time
    double dCollisionTimeS;
    
    // if the distance between circle A's position and the collision point is negative then circle A is overlapping circle B
    if (circleACollisionPointDist < 0) {
      // circle A should be moved outside of circle B immediately
      dCollisionTimeS = 0;
    }
    else {
      // get the time it would take to travel that distance and check if it is within the given duration
      dCollisionTimeS = circleACollisionPointDist / circleAVel.mag;
    }
    
    // check if the collision will occur within the given duration
    if (dCollisionTimeS > durS) {
      return null;
    }
    
    // start from circle A's position and move along the velocity vector for the distance between the circle A's position and
    // the collision point
    // we can not use the collision time to calculate this because it may be 0 because of overlapping
    Vector circleACollisionPos = circleA.pos.add(circleAVel.normalize().scale(circleACollisionPointDist));
    
    // the collision normal is always between the circle's centers
    Vector collsionNormal = new Vector(circleACollisionPos, circleB.pos).normalize();
    
    // calculate the point of collision
    Vector poc = circleACollisionPos.add(collsionNormal.scale(circleA.radius));
    
    // if the collision time is 0 then an overlap has occurred
    if (dCollisionTimeS == 0) {
      // use an impulse to resolve the overlap
      Vector circleACollisionImpulse = new Vector(circleA.pos, circleACollisionPos);
      return new Collision(circleA, circleB, collsionNormal, poc, circleACollisionImpulse);
    }
    
    return new Collision(circleA, circleB, collsionNormal, poc, dCollisionTimeS);
    
    
    /*
    // two circles collide when the distance between their centers is equal to the sum
    // of their radii
    // the distance between circles A and B can be found using the distance formula
    // d = sqrt((a_x - b_x)^2 + (a_y - b_y)^2)
    
    // because both circles are moving, we must factor in their velocity and time
    // a_xf = a_xi + a_vx*t
    // b_xf = b_xi + b_vx*t
    // d = sqrt((a_xf - b_xf)^2 + (a_yf - b_yf)^2)
    
    // now we can solve for t when the distance is the sum of the radii
    // a_r + b_r = sqrt(( - b_xf)^2 + (a_yf - b_yf)^2)
    // a_r + b_r = sqrt(((a_xi + a_vx*t) - (b_xi + b_vx*t))^2 + ((a_yi + a_vy*t) - (b_yi + b_vy*t))^2)
    // (a_r + b_r)^2 = ((a_xi + a_vx*t) - (b_xi + b_vx*t))^2 + ((a_yi + a_vy*t) - (b_yi + b_vy*t))^2
    // (a_r + b_r)^2 = (a_xi + a_vx*t - b_xi - b_vx*t)^2 + (a_yi + a_vy*t - b_yi - b_vy*t)^2
    // (a_r + b_r)^2 = (a_xi + a_vx*t - b_xi - b_vx*t)^2 + (a_yi + a_vy*t - b_yi - b_vy*t)^2
    // (a_r + b_r)^2 =
    //   a_xi^2 + 2*a_xi*a_vx*t - 2*a_xi*b_xi - 2*a_xi*b_vx*t
    //   + a_vx^2*t^2 - 2*a_vx*b_xi*t - 2*a_vx*b_vx*t^2
    //   + b_xi^2 + 2*b_xi*b_vx*t
    //   + b_vx^2*t^2
    //   
    //   + a_yi^2 + 2*a_yi*a_vy*t - 2*a_yi*b_yi - 2*a_yi*b_vy*t
    //   + a_vy^2*t^2 - 2*a_vy*b_yi*t - 2*a_vy*b_vy*t^2
    //   + b_yi^2 + 2*b_yi*b_vy*t
    //   + b_vy^2*t^2
    // 0 =
    //   (
    //      a_vx^2 - 2*a_vx*b_vx + b_vx^2
    //     +a_vy^2 - 2*a_vy*b_vy + b_vy^2
    //   )*t^2
    //   + (
    //      2*a_xi*a_vx - 2*a_xi*b_vx - 2*a_vx*b_xi + 2*b_xi*b_vx
    //     +2*a_yi*a_vy - 2*a_yi*b_vy - 2*a_vy*b_yi + 2*b_yi*b_vy
    //   )*t
    //   + (
    //      a_xi^2 - 2*a_xi*b_xi + b_xi^2
    //     +a_yi^2 - 2*a_yi*b_yi + b_yi^2
    //     -(a_r + b_r)^2
    //   )
    // 
    // Now we can use the quadratic formula:
    // a*x^2 + b*x + c = 0
    // x = -b +- sqrt(b^2 - 4*a*c)/2*a
    // 
    // a =
    //    a_vx^2 - 2*a_vx*b_vx + b_vx^2
    //   +a_vy^2 - 2*a_vy*b_vy + b_vy^2
    // b =
    //    2*a_xi*a_vx - 2*a_xi*b_vx - 2*a_vx*b_xi + 2*b_xi*b_vx
    //   +2*a_yi*a_vy - 2*a_yi*b_vy - 2*a_vy*b_yi + 2*b_yi*b_vy
    // c =
    //    a_xi^2 - 2*a_xi*b_xi + b_xi^2
    //   +a_yi^2 - 2*a_yi*b_yi + b_yi^2
    //   -(a_r + b_r)^2
    
    DynamicCircle cA = circleA;
    DynamicCircle cB = circleB;
    
    double a =
       cA.vel.x*cA.vel.x - 2*cA.vel.x*cB.vel.x + cB.vel.x*cB.vel.x
      +cA.vel.y*cA.vel.y - 2*cA.vel.y*cB.vel.y + cB.vel.y*cB.vel.y;
    double b =
       2*cA.pos.x*cA.vel.x - 2*cA.pos.x*cB.vel.x - 2*cA.vel.x*cB.pos.x + 2*cB.pos.x*cB.vel.x
      +2*cA.pos.y*cA.vel.y - 2*cA.pos.y*cB.vel.y - 2*cA.vel.y*cB.pos.y + 2*cB.pos.y*cB.vel.y;
    double c =
       cA.pos.x*cA.pos.x - 2*cA.pos.x*cB.pos.x + cB.pos.x*cB.pos.x
      +cA.pos.y*cA.pos.y - 2*cA.pos.y*cB.pos.y + cB.pos.y*cB.pos.y
      -(cA.radius + cB.radius)*(cA.radius + cB.radius);
    
    // if a is 0 then both circles have the same velocity
    if (a == 0) {
      return null;
    }
    
    // if b^ - 4*a*c is negative then the circles will never collide
    if (b*b - 4*a*c < 0) {
      return null;
    }
    
    // we get two times because there is always 2 times the circles would be the correct distance
    double dTime1S = (-b + Math.sqrt(b*b - 4*a*c))/(2*a);
    double dTime2S = (-b - Math.sqrt(b*b - 4*a*c))/(2*a);
    
    // if both of the times are negative then the collision would have happened in the past
    if (dTime1S < 0 && dTime2S < 0) {
      return null;
    }
    
    // chose the time that is soonest
    double dTimeS = Math.min(dTime1S, dTime2S);
    
    // if the collision time is negative (in the past) then there is an overlap
    if (dTimeS < 0) {
      // separate the circles
      Vector a_b = new Vector(circleA.pos, circleB.pos);
      
      // get the amount of overlap
      double overlapDist = (circleA.radius + circleB.radius) - a_b.mag + 0.001d;
      
      // move the circles away from each other so they no longer overlap
      Vector normal = a_b.normalize();
      
      Vector circleAImpulse = normal.invert().scale(overlapDist/2);
      Vector circleBImpulse = normal.scale(overlapDist/2);
      
      Vector poc = circleA.pos.add(normal.scale(circleA.radius));
      
      return new Collision(circleA, circleB, normal, poc, circleAImpulse, circleBImpulse, true);
    }
    
    // check if the collision will occur during our time frame
    if (dTimeS > durS) {
      return null;
    }
    
    // calculate the positions of the circles at the time of collision
    Vector circleACollisionPos = circleA.pos.add(circleA.vel.scale(dTimeS));
    Vector circleBCollisionPos = circleB.pos.add(circleB.vel.scale(dTimeS));
    
    // the normal will be along the line between the circle's centers at the time of the collision
    Vector normal = new Vector(circleACollisionPos, circleBCollisionPos).normalize();
    
    // calculate the point of collision
    Vector poc = circleACollisionPos.add(normal.scale(circleA.radius));
    
    return new Collision(circleA, circleB, normal, poc, dTimeS);
    */
  }
}

/*


*/