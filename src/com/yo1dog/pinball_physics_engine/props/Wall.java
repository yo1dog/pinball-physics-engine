package com.yo1dog.pinball_physics_engine.props;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import com.yo1dog.pinball_physics_engine.Vector;
import com.yo1dog.pinball_physics_engine.collisions.Collision;
import com.yo1dog.pinball_physics_engine.solids.Solid;
import com.yo1dog.pinball_physics_engine.solids.SurfaceProperties;
import com.yo1dog.pinball_physics_engine.solids.statics.StaticSegment;

public class Wall implements Prop {
  private final StaticSegment segment1;
  private final StaticSegment segment2;
  
  public Wall(Vector p1, Vector p2, boolean isDoubleSided) {
    segment1 = new StaticSegment(p1, p2, new SurfaceProperties());
    segment2 = isDoubleSided? new StaticSegment(p2, p1, new SurfaceProperties()) : null;
  }
  
  @Override
  public Solid[] init() {
    return new Solid[] {segment1, segment2};
  }
  
  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);
    g2d.draw(new Line2D.Double(
      segment1.p1.x, segment1.p1.y,
      segment1.p2.x, segment1.p2.y
    ));
  }
  
  @Override public void preUpdate() {}
  @Override public void update(long dTimeNS) {}
  @Override public void postUpdate() {}
  @Override public void preSolidsExtrapolation() {}
  @Override public void postSolidsExtrapolation() {}
  @Override public void onCollision(Collision propCollision, Prop otherProp) {}
}
