package pinball2.props;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import pinball2.Vector;
import pinball2.collisions.Collision;
import pinball2.solids.Solid;
import pinball2.solids.SurfaceProperties;
import pinball2.solids.statics.StaticLine;

public class Flag extends StaticLine implements Prop {
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
    g2d.fill(new Line2D.Double(
      end1.x, end1.y,
      end2.x, end2.y
    ));
  }
  
  @Override public void preUpdate() {}
  @Override public void update(long dTimeNS) {}
  @Override public void postUpdate() {}
  @Override public void preSolidsExtrapolation() {}
  @Override public void postSolidsExtrapolation() {}
}