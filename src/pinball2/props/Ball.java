package pinball2.props;

import pinball2.Vector;
import pinball2.collisions.Collision;
import pinball2.solids.Solid;
import pinball2.solids.dynamics.DynamicCircle;

public class Ball extends DynamicCircle implements Prop {
  public Ball(Vector pos, double radius) {
    super(pos, radius);
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
  @Override public void onCollision(Collision collision) {}
}
