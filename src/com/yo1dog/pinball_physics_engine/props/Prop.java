package com.yo1dog.pinball_physics_engine.props;

import java.awt.Graphics2D;

import com.yo1dog.pinball_physics_engine.collisions.Collision;
import com.yo1dog.pinball_physics_engine.solids.Solid;

public interface Prop {
  public Solid[] init();
  
  public void preUpdate();
  public void update(long dTimeNS);
  public void postUpdate();
  
  public void preSolidsExtrapolation();
  public void postSolidsExtrapolation();
  
  public void onCollision(Collision collision, Prop otherProp);
  
  public abstract void draw(Graphics2D g2d);
}