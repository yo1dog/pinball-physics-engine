package com.yo1dog.pinball_physics_engine.props;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import com.yo1dog.pinball_physics_engine.Vector;
import com.yo1dog.pinball_physics_engine.collisions.Collision;
import com.yo1dog.pinball_physics_engine.solids.Solid;
import com.yo1dog.pinball_physics_engine.solids.SurfaceProperties;
import com.yo1dog.pinball_physics_engine.solids.statics.StaticSegment;

public class Flag extends StaticSegment implements Prop {
  public boolean isDown = false;
  
  public Flag(Vector end1, Vector end2) {
    super(end1, end2, new SurfaceProperties());
  }
  
  @Override
  public Solid[] init() {
    return new Solid[] {this};
  }
  
  @Override
  public void onCollision(Collision collision, Prop otherProp) {
    // put flag down if a ball hits it
    if (otherProp instanceof Ball) {
      collisionsEnabled = false;
      isDown = true;
    }
  }
  
  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(Color.BLUE);
    g2d.draw(new Line2D.Double(
      p1.x, p1.y, p2.x, p2.y
    ));
  }
  
  @Override public void preUpdate() {}
  @Override public void update(long dTimeNS) {}
  @Override public void postUpdate() {}
  @Override public void preSolidsExtrapolation() {}
  @Override public void postSolidsExtrapolation() {}
}
