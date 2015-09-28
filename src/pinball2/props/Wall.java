package pinball2.props;

import pinball2.Vector;
import pinball2.collisions.Collision;
import pinball2.solids.Solid;
import pinball2.solids.statics.StaticLine;

public class Wall extends StaticLine implements Prop {
  public Wall(Vector end1, Vector end2) {
    super(end1, end2);
  }
  
  @Override
  public Solid[] init() {
    return new Solid[] {this};
  }
  
  @Override public void preUpdate() {}
  @Override public void update(long dTimeNS) {}
  @Override public void postUpdate() {}
  @Override public void preSolidsExtrapolation() {}
  @Override public void postSolidsExtrapolation() {}
  @Override public void onCollision(Collision propCollision) {}
}
