package pinball2.props;

import java.awt.Color;
import java.awt.Graphics;

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
  public void draw(Graphics g) {
    g.setColor(Color.RED);
    g.drawOval(
      (int)(pos.x - radius), (int)(pos.y - radius),
      (int)(radius * 2), (int)(radius * 2)
    );
  }
  
  @Override public void preUpdate() {}
  @Override public void update(long dTimeNS) {}
  @Override public void postUpdate() {}
  @Override public void preSolidsExtrapolation() {}
  @Override public void postSolidsExtrapolation() {}
}
