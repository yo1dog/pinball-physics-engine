package com.yo1dog.pinball_physics_engine.extrapolation;

import java.util.ArrayList;

import com.yo1dog.pinball_physics_engine.collisions.Collision;
import com.yo1dog.pinball_physics_engine.collisions.CollisionDetector;
import com.yo1dog.pinball_physics_engine.solids.Solid;
import com.yo1dog.pinball_physics_engine.solids.dynamics.DynamicCircle;
import com.yo1dog.pinball_physics_engine.solids.dynamics.DynamicSolid;
import com.yo1dog.pinball_physics_engine.tables.Table;

public class SolidExtrapolator {
  public static ArrayList<Collision> extrapolate(Table table, ArrayList<Solid> solids, ArrayList<DynamicSolid> dynamicSolids, double durNS) {
    // extrapolate for the given duration
	  double extrapolateDurS = durNS / 1000000000.0d;
	  
    // for each dynamic solid...
    for (DynamicSolid dynamicSolid: dynamicSolids) {
      // apply table normal acceleration
      dynamicSolid.applyTableNormal(table.normalAcc, table.surfaceProperties.cof, extrapolateDurS);
    }
    
    ArrayList<Collision> collisions = new ArrayList<Collision>();
    
    // continue to calculate the earliest collision and extrapolate to it until there are no more collisions
    // within the extrapolation duration
    ArrayList<Collision> earliestConcurrentCollisions;
    while ((earliestConcurrentCollisions = CollisionDetector.detect(solids, extrapolateDurS)).size() > 0) {
      double earliestCollisionDTimeS = earliestConcurrentCollisions.get(0).dTimeS;
      
      // move all dynamic solids until the time of the collision
      if (earliestCollisionDTimeS > 0) {
        for (DynamicSolid dynamicSolid: dynamicSolids) {
          dynamicSolid.move(earliestCollisionDTimeS);
        }
      }
      
      // apply the collision forces and impulses
      for (Collision collision: earliestConcurrentCollisions) {
        collision.dynCircle.vel = collision.dynCircle.vel.add(collision.dynCircleAAcc);
        
        if (collision.dynCircleAImpulse != null) {
          collision.dynCircle.pos = collision.dynCircle.pos.add(collision.dynCircleAImpulse);
        }
        
        if (collision.solid instanceof DynamicCircle) {
          DynamicCircle dynCircleB = (DynamicCircle)collision.solid;
          
          dynCircleB.vel = dynCircleB.vel.add(collision.dynCircleBAcc);
          
          if (collision.dynCircleBImpulse != null) {
            dynCircleB.pos = dynCircleB.pos.add(collision.dynCircleBImpulse);
          }
        }
        
        /*
        // draw between every collision
        table.frameCollisions = new ArrayList<Collision>(1);
        table.frameCollisions.add(collision);
        double x2 = collision.dynCircle.pos.x + collision.dynCircle.vel.x * (extrapolateDurS - earliestCollisionDTimeS);
        double y2 = collision.dynCircle.pos.y + collision.dynCircle.vel.y * (extrapolateDurS - earliestCollisionDTimeS);
        com.yo1dog.pinball_physics_engine.Main.window.draw(new com.yo1dog.pinball_physics_engine.GameWindow.Drawer() {
          @Override
          public void draw(java.awt.Graphics2D g2d) {
            g2d.setColor(java.awt.Color.GRAY);
            g2d.draw(new java.awt.geom.Line2D.Double(
              collision.dynCircle.pos.x,
              collision.dynCircle.pos.y,
              x2,
              y2
            ));
            g2d.draw(new java.awt.geom.Ellipse2D.Double(
              x2 - collision.dynCircle.radius,
              y2 - collision.dynCircle.radius,
              collision.dynCircle.radius * 2,
              collision.dynCircle.radius * 2
            ));
          }
        });
        int z = 1+2;
        */
      }
      
      // subtract the time we have extrapolated from the extrapolation duration
      extrapolateDurS -= earliestCollisionDTimeS;
      
      // add the collisions to the list
      collisions.addAll(earliestConcurrentCollisions);
      
      // safegaurd
      //if (collisions.size() > 100000) {
      //  break;
      //}
    }
    
    
    // if there is still time remaining in the extrapolation duration...
    if (extrapolateDurS > 0) {
      // move all dynamic solids for the rest of the extrapolation duration
      for (DynamicSolid dynamicSolid: dynamicSolids) {
        dynamicSolid.move(extrapolateDurS);
      }
    }
    
    /*
    // draw between every extrapolation
    table.frameCollisions = collisions;
    com.yo1dog.pinball_physics_engine.Main.window.draw();
    */
    
    return collisions;
  }
}
