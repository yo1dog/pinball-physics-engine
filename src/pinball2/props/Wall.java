package pinball2.props;

import java.awt.Color;
import java.awt.Graphics;

import pinball2.Vector;
import pinball2.collisions.Collision;
import pinball2.solids.Solid;
import pinball2.solids.SurfaceProperties;
import pinball2.solids.statics.StaticLine;

public class Wall extends StaticLine implements Prop {
  public Wall(Vector end1, Vector end2) {
    super(end1, end2, new SurfaceProperties());
  }
  
  @Override
  public Solid[] init() {
    return new Solid[] {this};
  }
  
  @Override
  public void draw(Graphics g) {
    g.setColor(Color.BLACK);
    g.drawLine(
      (int)end1.x, (int)end1.y,
      (int)end2.x, (int)end2.y
    );
  }
  
  @Override public void preUpdate() {}
  @Override public void update(long dTimeNS) {}
  @Override public void postUpdate() {}
  @Override public void preSolidsExtrapolation() {}
  @Override public void postSolidsExtrapolation() {}
  @Override public void onCollision(Collision propCollision, Prop otherProp) {}
}
