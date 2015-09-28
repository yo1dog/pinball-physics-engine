package pinball2.props;

import pinball2.Vector;
import pinball2.collisions.Collision;
import pinball2.solids.Solid;
import pinball2.solids.statics.StaticCircle;

public class Bumper extends StaticCircle implements Prop {
  private final double activationForce;
  private final double pushForce;
  
  protected Bumper(Vector pos, double radius, double activationForce, double pushForce) {
    super(pos, radius);
    
    this.activationForce = activationForce;
    this.pushForce = pushForce;
  }
  
  @Override
  public Solid[] init() {
    return new Solid[] {this};
  }
  
  @Override
  public void onCollision(Collision collision) {
    // TODO: add pushForce in direction of collision normal if collision force is >= activationForce
  }
  
  @Override public void preUpdate() {}
  @Override public void update(long dTimeNS) {}
  @Override public void postUpdate() {}
  @Override public void preSolidsExtrapolation() {}
  @Override public void postSolidsExtrapolation() {}
}
