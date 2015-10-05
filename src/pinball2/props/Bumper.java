package pinball2.props;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import pinball2.Vector;
import pinball2.collisions.Collision;
import pinball2.solids.Solid;
import pinball2.solids.SurfaceProperties;
import pinball2.solids.statics.StaticCircle;

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
    // TODO: add pushForce in direction of collision normal if collision force is >= activationForce
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
