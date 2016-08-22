package com.yo1dog.pinball_physics_engine.props;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import com.yo1dog.pinball_physics_engine.Vector;
import com.yo1dog.pinball_physics_engine.collisions.Collision;
import com.yo1dog.pinball_physics_engine.solids.Solid;
import com.yo1dog.pinball_physics_engine.solids.SurfaceProperties;
import com.yo1dog.pinball_physics_engine.solids.statics.StaticCircle;

public class Bumper extends StaticCircle implements Prop {
  private final double activationForce;
  private final double pushForce;
  
  public Bumper(Vector pos, double radius, double activationForce, double pushForce) {
    super(pos, radius, new SurfaceProperties());
    
    this.activationForce = activationForce;
    this.pushForce = pushForce;
  }
  
  @Override
  public Solid[] init() {
    return new Solid[] {this};
  }
  
  @Override
  public void onCollision(Collision collision, Prop otherProp) {
    if (collision.solidCollisionForce.mag > activationForce) {
      double accMag = -pushForce / collision.dynCircle.bodyProperties.mass;
      Vector acc = collision.normal.scale(accMag);
      
      collision.dynCircle.vel = collision.dynCircle.vel.add(acc);
    }
  }
  
  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(Color.RED);
    g2d.fill(new Ellipse2D.Double(
      pos.x - radius,
      pos.y - radius,
      radius * 2, radius * 2
    ));
  }
  
  @Override public void preUpdate() {}
  @Override public void update(long dTimeNS) {}
  @Override public void postUpdate() {}
  @Override public void preSolidsExtrapolation() {}
  @Override public void postSolidsExtrapolation() {}
}
