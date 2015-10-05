package pinball2.props;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import pinball2.Vector;
import pinball2.collisions.Collision;
import pinball2.solids.BodyProperties;
import pinball2.solids.Solid;
import pinball2.solids.SurfaceProperties;
import pinball2.solids.dynamics.DynamicCircle;

public class Ball extends DynamicCircle implements Prop {
  public Ball(Vector pos, double radius) {
    super(pos, radius, new Vector(0.0d, 0.0d), new SurfaceProperties(), new BodyProperties());
  }
  
  @Override
  public Solid[] init() {
    return new Solid[] {this};
  }
  
  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(Color.GREEN);
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
  @Override public void onCollision(Collision collision, Prop otherProp) {}
}
