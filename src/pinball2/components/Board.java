package pinball2.components;

import java.util.ArrayList;

import pinball2.Vector;
import pinball2.collisions.Collision;
import pinball2.extrapolation.SolidExtrapolator;
import pinball2.props.Prop;
import pinball2.solids.Solid;
import pinball2.solids.SurfaceProperties;
import pinball2.solids.dynamics.DynamicSolid;

public class Board {
  public static final double GRAVITY_ACC = -9.8d;
  
  public final int width, height;
  public final SurfaceProperties surfaceProperties;
  public final double elevationAngle;
  public final Vector gravityAcc; // not really gravity but the acceleration due to the normal force applied by the board
  
  private ArrayList<Prop> props;
  private ArrayList<Solid> solids;
  private ArrayList<DynamicSolid> dynamicSolids;
  
  public Board(int width, int height) {
    this(width, height, 0.349d/*~20deg*/, new SurfaceProperties());
  }
  
  public Board(int width, int height, double elevationAngle, SurfaceProperties surfaceProperties) {
    this.width = width;
    this.height = height;
    this.elevationAngle = elevationAngle;
    this.surfaceProperties = surfaceProperties;
    
    gravityAcc = new Vector(0, Math.sin(elevationAngle) * GRAVITY_ACC);
    
    props = new ArrayList<Prop>();
  }
  
  public void init() {
    solids = new ArrayList<Solid>();
    dynamicSolids = new ArrayList<DynamicSolid>();
    
    // for each prop...
    for (Prop prop: props) {
      // initiate the prop
      Solid[] propSolids = prop.init();
      
      // add the prop's solids to the lists
      for (int i = 0; i < propSolids.length; ++i) {
        Solid solid = propSolids[i];
        
        solid.parentProp = prop;
        
        solids.add(solid);
        
        if (solid instanceof DynamicSolid) {
          dynamicSolids.add((DynamicSolid)solid);
        }
      }
    }
  }
  
  public void preUpdate() {}
  public void update(long dTimeNS) {
    // extrapolate solids
    for (Prop prop: props) {
      prop.preSolidsExtrapolation();
    }
    
    double dTimeS = dTimeNS / 1000000000.0d;
    ArrayList<Collision> collisions = SolidExtrapolator.extrapolate(this, dynamicSolids, solids, dTimeS);
    
    for (Prop prop: props) {
      prop.postSolidsExtrapolation();
    }
    
    // notify props of collisions
    for (Collision collision: collisions) {
      collision.solidA.parentProp.onCollision(collision);
      collision.solidB.parentProp.onCollision(collision.swapSolids());
    }
    
    // update props
    for (Prop prop: props) {
      prop.preUpdate();
    }
    for (Prop prop: props) {
      prop.update(dTimeNS);
    }
    for (Prop prop: props) {
      prop.postUpdate();
    }
  }
  public void postUpdate() {}
  
  public void addProp(Prop prop) {
    props.add(prop);
  }
}
