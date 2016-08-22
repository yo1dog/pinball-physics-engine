package com.yo1dog.pinball_physics_engine.props;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import com.yo1dog.pinball_physics_engine.Vector;
import com.yo1dog.pinball_physics_engine.collisions.Collision;
import com.yo1dog.pinball_physics_engine.solids.BodyProperties;
import com.yo1dog.pinball_physics_engine.solids.Solid;
import com.yo1dog.pinball_physics_engine.solids.SurfaceProperties;
import com.yo1dog.pinball_physics_engine.solids.dynamics.DynamicCircle;

public class Ball extends DynamicCircle implements Prop {
  Color color;
  
  public Ball(Vector pos, double radius) {
    this(pos, radius, Color.GREEN);
  }
  public Ball(Vector pos, double radius, Color color) {
    this(pos, radius, color, new SurfaceProperties(0.05d, 0.3d), new BodyProperties());
  }
  public Ball(Vector pos, double radius, Color color, SurfaceProperties surfaceProperties, BodyProperties bodyProperties) {
    super(pos, radius, new Vector(0.0d, 0.0d), surfaceProperties, bodyProperties);
    this.color = color;
  }
  
  @Override
  public Solid[] init() {
    return new Solid[] {this};
  }
  
  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(color);
    g2d.fill(new Ellipse2D.Double(
      pos.x - radius,
      pos.y - radius,
      radius * 2, radius * 2
    ));
    
    /*
    g2d.setColor(java.awt.Color.GRAY);
    g2d.draw(new java.awt.geom.Line2D.Double(
      pos.x,
      pos.y,
      pos.x + (vel.x * pinball2.GameRunner.TARGET_FRAME_DURATION_NS / 1000000000.0d),
      pos.y + (vel.y * pinball2.GameRunner.TARGET_FRAME_DURATION_NS / 1000000000.0d)
    ));
    */
  }
  
  @Override public void preUpdate() {}
  @Override public void update(long dTimeNS) {}
  @Override public void postUpdate() {}
  @Override public void preSolidsExtrapolation() {}
  @Override public void postSolidsExtrapolation() {}
  @Override public void onCollision(Collision collision, Prop otherProp) {}
}
