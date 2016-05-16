package pinball2.collisions;

import pinball2.Vector;
import pinball2.solids.Solid;
import pinball2.solids.dynamics.DynamicCircle;
import pinball2.solids.statics.StaticCircle;

public class DynamicCircle_StaticCircle_CollisionDetector {
  public static Collision detect(DynamicCircle circleA, StaticCircle circleB, double durS) {
    double circleACollisionPointDist = detectCollisionDist(
      circleA.pos, circleA.radius, circleA.vel,
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
      dCollisionTimeS = circleACollisionPointDist / circleA.vel.mag;
    }
    
    // check if the collision will occur within the given duration
    if (dCollisionTimeS > durS) {
      return null;
    }
    
    // start from circle A's position and move along the velocity vector for the distance between the circle A's position and
    // the collision point
    // we can not use the collision time to calculate this because it may be 0 because of overlapping
    Vector circleACollisionPos = circleA.pos.add(circleA.vel.normalize().scale(circleACollisionPointDist));
    
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
  }
  
  public static double detectCollisionDist(
    Vector circleAPos, double circleARadius, Vector circleAVel,
    Vector circleBPos, double circleBRadius
  ) {
    if (circleAVel.mag == 0) {
      return -1;
    }
    
    // get the point along the velocity vector that is closest to circle B's position
    
    // create a vector from the circle A's position to the circle B's position
    // (remember that the velocity vector's origin is also the circle A's position so these two
    // vectors have the same origin)
    Vector circleA_circleB = new Vector(circleAPos, circleBPos);
    
    // project the A->B vector along the velocity vector's normal to get the distance between the closest point
    // and circle B's position. We can do this because the line between the closest point and circle B's position
    // will always be perpendicular to the velocity vector
    // we must take the absolute value as circle B's position can be on either side of the velocity vector which
    // means the projection would be negative if circle B's position is on the side opposite of the velocity
    // normal's direction
    double closestPointCircleBDist = Math.abs(circleA_circleB.dot(circleAVel.getNormal()));
    
    // check if the distance between the closest point and the circle B's position is close enough to cause a collision
    if (closestPointCircleBDist >= circleARadius + circleBRadius) {
      return -1;
    }
    
    // project the A->B vector along the velocity vector to get the distance between circle A's position and the closest point
    Vector circleAVelNormalized = circleAVel.normalize();
    double circleA_closestPointDist = circleA_circleB.dot(circleAVelNormalized);
    
    // if the projection is negative then the collision point would be in the opposite direction of the velocity
    if (circleA_closestPointDist < 0) {
      return -1;
    }
    
    // moving in the opposite direction as the velocity vector, calculate the point along the velocity vector a collision
    // would take place
    // the collision will be where the distance between the two circles is equal to the sum of the circles' radii.
    // Draw circle A and circle B so that they are touching. Now draw a line that represents circle A's velocity vector that
    // runs through circle A's center. Draw a second line perpendicular to circle A's velocity vector that runs through circle
    // B's center. Where these two lines intersect is the closest point that we found above. Let's call this point C. Finally,
    // draw a line from circle B's center (point B) to circle A's center (point A). ABC creates a right triangle with the right
    // angle at point C. |A->B| is equal to circle A's radius + circle B's radius. We already calculated the distance between
    // the closest point and circle B's position (|C->B|). We can use the Pythagorean theorem to find the final side of the
    // triangle |A->C|:
    // |A-C|^2 + |C->B|^2 = |A->B|^2
    // |A-C| = sqrt(|A->B|^2 - |C->B|^2)
    double a_bMag = circleARadius + circleBRadius;
    double c_bMag = closestPointCircleBDist;
    double a_cMag = Math.sqrt(a_bMag*a_bMag - c_bMag*c_bMag);
    
    // this gives us the distance from the closest point to the collision point along the velocity vector's opposite
    double closestPoint_collisionPointDist = a_cMag;
    
    // we can now find the distance circle A will have to travel along it's velocity vector to collide with circle B
    // earlier we calculated the distance from circle A's position to the closest point along the velocity vector. Then
    // we calculated the distance between the closest point and the collision point along the velocity vector's opposite.
    // So, we can subtract the distance from the closest point to the collision point from the distance from circle A's
    // position to the closest point to get the distance from circle A's position to the collision point along the velocity
    // vector.
    return circleA_closestPointDist - closestPoint_collisionPointDist;
  }
}