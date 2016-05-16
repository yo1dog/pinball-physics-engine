package pinball2.collisions;

import pinball2.Vector;
import pinball2.solids.dynamics.DynamicCircle;
import pinball2.solids.statics.StaticSegment;

public class DynamicCircle_StaticSegment_CollisionDetector {
  public static Collision detect(DynamicCircle circle, StaticSegment segment, double durS) {
    if (circle.vel.mag == 0) {
      return null;
    }
    
    // the collision normal will always be the segment's normal
    Vector collsionNormal = segment.normal;
    
    // get the point on the edge of the circle that is closest to the line
    // through the segement's points
    // we can get this by starting from the circle's position and moving the
    // distance of the cirle's radius in the direction of the line's normal.
    // We can do this because the shortest path between a point and a line
    // is always perpendicular to that line
    // I will refer to this point as C
    Vector c = circle.pos.add(segment.normal.scale(circle.radius));
    
    // now we need to find if/when point C traveling along the velocity vector
    // will intersect a line through the segement's points
    // 
    // we can do this by comparing the projection of the velocity vector on the
    // line's normal to the distance between point C and the line (along the
    // line's normal). This will give us the faction of time point C spent on one
    // side of the line vs the other.
    // 
    // imagine a right triangle. One point is point C, another is point C + velocity
    // vector (we will call this point B), and the third is point C + velocity
    // vector projected on the line\'s normal (call this point A). The right angle
    // will be at point A. Now we can image our line (which is parallel to the B->A
    // side) passes through our triangle. Now sides C->B and C->A intersect with the line
    // at points D and E respectively and a smaller right triangle (CDE) is created inside of
    // our original triangle (CBA) with the right angle at point E. Because they are right
    // triangles with the same angles, the ratio of the sides will be equal. This can be
    // proven with some simple trig:
    // cos(angle C) = C->E/C->D
    // cos(angle C) = C->A/C->B
    // C->E/C->D = C->A/C->B
    // So, if we can find the ratio of the C->E side of the small triangle to the C->A side
    // of the large triangle, we can use that ratio to solve for the C->D and C->B sides the
    // triangles Meaning, if we can find the ratio between the distance from point C to the 
    // line (along the line normal, C->E side of the small triangle) and the velocity vector
    // projected on the line's normal (C->A side of the large triangle) we can use that to
    // find the distance between point C and line (along the velocity vector, side C->D of the
    // small triangle) compared to the velocity vector (side C->B of the large triangle.)
    
    // first, we project the velocity vector onto the line's normal. This will give
    // us the length of side C->A of the larger triangle in the example above
    double velLineNormalProj = circle.vel.dot(segment.normal);
    
    // if the projection is 0 then either the velocity vector's magnitude is 0 or
    // the velocity vector and the line are parallel. We already made sure the
    // velocity vector's magnitude was not 0 above so they must be parallel
    if (velLineNormalProj == 0) {
      // it is impossible for there to be a collision if the circle is moving
      // parallel to the line
      return null;
    }
    
    // if the projection is negative, then the circle is moving in the opposite direction
    // as the line's normal
    if (velLineNormalProj < 0) {
      // it is impossible for there to be a collision if the circle is moving in the
      // opposite direction as the normal
      return null;
    }
    
    // next, we calculate the distance from point C to the line (along the line's
    // normal)
    // we do this by projecting the vector from point C to any point on the line
    // (we will use the segment's p1 end point as it is a known point on the line)
    // onto the line's normal. This will give us the length of side C->A of
    // the smaller triangle in the example above.
    double c_p1LineNormalProj = new Vector(c, segment.p1).dot(segment.normal);
    
    // if the projection is negative then point C is already passed the line (the
    // velocity vector is in the same direction as the line's normal but point C and
    // point C + velocity vector are on the same side as the line's normal)
    if (c_p1LineNormalProj < 0) {
      // no collision can occur unless the circle is overlapping the line. In real life
      // this would not be possible (one solid can not be inside of another) but because
      // of rounding errors this is possible in our simulation.
      // 
      // There is an overlap if the projection is negative and distance from point C to the
      // line (along the line's normal) is less than the diameter of the circle. However,
      // we will only register an overlap if the distance from point C to the line (along
      // the line's normal) is less than the RADIUS of the circle. This helps the problem
      // where the circle can overlap two segments with opposite normals and collides with
      // both of them at the same time.
      
      // check if the distance is less than the radius of the circle
      double c_lineDist = -c_p1LineNormalProj;
      if (c_lineDist >= circle.radius) {
        // there is no overlap and no collision
        return null;
      }
      
      // there is an overlap
      // to calculate the collision point we need to know where the circle intersects the
      // line. But, because they are overlapping, there will be two points of intersection
      // and infinite points of collision. Therefore, let's use the point on the segment that
      // is closest to point C.
      Vector segmentClosestPoint;
      
      // first, find the closest point to point C on the line through the segement's points.
      // We can get this by starting from point C and moving the distance of the projection
      // we calculated above along the the line's normal.
      Vector lineClosestPoint = c.add(segment.normal.scale(c_p1LineNormalProj));
      
      // because we are colliding with a segment and not a line, we must make sure that
      // the point we use is on the segment
      // project the vector from the line segment's p1 end point to the closest point on the line
      double p1_lineClosestPointLineProj = new Vector(segment.p1, lineClosestPoint).dot(segment.p1p2.normalize());
      
      // if the projection is negative, then the closest point on the line is below (in relation
      // to the p1->p2 vector's direction) the p1 end point
      if (p1_lineClosestPointLineProj < 0) {
        // check if p1 is within the circle. If it is, p1 is the closest point on the segment
        // to point C. If not then the segment does not intersect the circle.
        // we do this by simply checking that the distance between p1 and the center of the circle
        // is less than the circle's radius
        double p1_circleCenterDist = new Vector(segment.p1, circle.pos).mag;
        if (p1_circleCenterDist < circle.radius) {
          segmentClosestPoint = segment.p1;
        }
        else {
          return null;
        }
      }
      // if the projection is longer than the segment length, then the closest point on the line
      // is above the p2 end point
      else if (p1_lineClosestPointLineProj > segment.p1p2.mag) {
        // check if p2 is within the circle. If it is, p2 is the closest point on the segment
        // to point C. If not then the segment does not intersect the circle.
        // we do this by simply checking that the distance between p2 and the center of the circle
        // is less than the circle's radius
        double p2_circleCenterDist = new Vector(segment.p2, circle.pos).mag;
        if (p2_circleCenterDist < circle.radius) {
          segmentClosestPoint = segment.p2;
        }
        else {
          return null;
        }
      }
      // the closest point on the line is between p1 and p2
      else {
        // the closest point on the line is also the closest point on the segment
        segmentClosestPoint = lineClosestPoint;
      }
      
      // use an impulse to resolve the overlap
      // we must move the circle a distance in the opposite direction along the line's
      // normal equal to the distance between the closest point and the edge of the circle
      // along the the line's.
      // 
      // Draw our circle with the line from our segment intersecting it. The line
      // intersects the circle at points J and K. Draw a line along the segment's
      // normal that runs through the circle's center. Where this line and the
      // segment's line intersect is the closest point on the segment line. Let's
      // call this point L. Now draw the closest point on the segment anywhere
      // along J->K. Let's call this point M. Now draw another line along the
      // segment's normal and through point L. This line intersects the circle at
      // points N and O. Draw a final line parallel to the segment that runs through
      // the circles center. Let's call the point where this line intersects N->O
      // point P.
      // 
      // So we need to find |M->O| and move the circle that distance in the opposite
      // direction along the line's normal. N->O is a chord in our circle that is
      // offset from the center by a distance equal to |L->M|. We can find the length
      // of the chord (|N->O|) using the formula described here:
      // http://www.ajdesigner.com/phpcircle/circle_segment_chord_c.php
      // |N->O| = 2*sqrt(circleRadius^2 - |L->M|^2)
      // 
      // Once we know |N->O| we can divide it by 2 which will give us |P->O| and then
      // add |M->P| to get |M->O|.
      
      // get the length of the chord (|N->O|)
      // |N->O| = 2*sqrt(circleRadius^2 - |L->M|^2)
      // |L->M| is the distance between the closest point on the line and the closest
      double l_mMag = new Vector(lineClosestPoint, segmentClosestPoint).mag;
      double n_oMag = 2*Math.sqrt(circle.radius*circle.radius - l_mMag*l_mMag);
      
      // earlier we calculated the distance from C to the line along the line's normal.
      // We can subtract the circle's radius from that to get |circleCenter->L| which is
      // equal to |M->P|.
      double m_pMag = c_lineDist - circle.radius;
      
      // now we can calculate |M->O|
      double m_oMag = (n_oMag * 0.5d) + m_pMag;
      
      Vector impulse = segment.normal.scale(-m_oMag);
      Vector poc = segmentClosestPoint;
      
      return new Collision(circle, segment, collsionNormal, poc, impulse);
    }
    else {
      // now we can find the ratio
      // this will be the ratio between the small triangle and the large triangle
      // in the example above
      double ratio = c_p1LineNormalProj / velLineNormalProj;
      
      // finally, we can use the ratio to determine the distance from point C to the
      // line (along the velocity vector) which is the distance to the collision.
      // This will be the length of side C->D of the small triangle in the example above.
      double collisionDist = ratio * circle.vel.mag;
      
      // now we can calculate how long it would take to travel that distance
      double collisionDTime = collisionDist / circle.vel.mag;
      
      // check if the collision would occur within the given duration
      if (collisionDTime > durS) {
        return null;
      }
      
      // because we are checking for a collision with a segment and not a line, we
      // must check to see if the collision occurs between the segment's end points
      // calculate circle's point of collision with the line
      Vector lineCollisionPoint = c.add(circle.vel.scale(collisionDTime));
      
      // next, project the vector from the line segment's p1 end point to the
      // collision point along the line
      double p1_lineCollisionPointLineProj = new Vector(segment.p1, lineCollisionPoint).dot(segment.p1p2.normalize());
      
      // if the projection is negative, then the collision occurs below (in relation
      // to the p1->p2 vector's direction) the p1 end point
      if (p1_lineCollisionPointLineProj < 0) {
        return null;
      }
      // if the projection is greater than the segment's length, then the collision
      // occurs above the p2 end point
      if (p1_lineCollisionPointLineProj > segment.p1p2.mag) {
        return null;
      }
      
      Vector poc = lineCollisionPoint;
      
      return new Collision(circle, segment, collsionNormal, poc, collisionDTime);
    }
  }
}
