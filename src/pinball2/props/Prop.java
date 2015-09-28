package pinball2.props;

import pinball2.collisions.Collision;
import pinball2.solids.Solid;

public interface Prop {
  public Solid[] init();
  
  public void preUpdate();
  public void update(long dTimeNS);
  public void postUpdate();
  
  public void preSolidsExtrapolation();
  public void postSolidsExtrapolation();
  
  public void onCollision(Collision collision);
}