package com.yo1dog.pinball_physics_engine.tables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.yo1dog.pinball_physics_engine.Vector;
import com.yo1dog.pinball_physics_engine.collisions.Collision;
import com.yo1dog.pinball_physics_engine.extrapolation.SolidExtrapolator;
import com.yo1dog.pinball_physics_engine.props.Prop;
import com.yo1dog.pinball_physics_engine.solids.Solid;
import com.yo1dog.pinball_physics_engine.solids.SurfaceProperties;
import com.yo1dog.pinball_physics_engine.solids.dynamics.DynamicSolid;

public abstract class Table {
  public static final double GRAVITY_ACC = 9.8d;
  
  public final double width, height;
  public final SurfaceProperties surfaceProperties;
  public final double elevationAngle;
  public final Vector normalAcc; // acceleration due to the normal force applied by the tilted surface of the table
  
  private ArrayList<Prop> props;
  private ArrayList<Solid> solids = new ArrayList<Solid>();
  private ArrayList<DynamicSolid> dynamicSolids = new ArrayList<DynamicSolid>();
  
  public ArrayList<Collision> frameCollisions = new ArrayList<Collision>(0);
  
  public Table(double width, double height, double elevationAngle, SurfaceProperties surfaceProperties) {
    this.width = width;
    this.height = height;
    this.elevationAngle = elevationAngle;
    this.surfaceProperties = surfaceProperties;
    
    normalAcc = new Vector(0, -Math.sin(elevationAngle) * -GRAVITY_ACC);
    
    props = new ArrayList<Prop>();
  }
  
  public void init() {}
  
  public void preUpdate() {}
  public void update(long dTimeNS) {
    // extrapolate solids
    for (Prop prop: props) {
      prop.preSolidsExtrapolation();
    }
    
    ArrayList<Collision> collisions = SolidExtrapolator.extrapolate(this, solids, dynamicSolids, dTimeNS);
    
    // notify props of collisions
    for (Collision collision: collisions) {
      collision.dynCircle.parentProp.onCollision(collision, collision.solid.parentProp);
      collision.solid.parentProp.onCollision(collision, collision.dynCircle.parentProp);
    }
    
    for (Prop prop: props) {
      prop.postSolidsExtrapolation();
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
    
    frameCollisions = collisions;
  }
  public void postUpdate() {}
  
  public void addProp(Prop prop) {
    props.add(prop);
    
    // add the prop's solids to the lists
    Solid[] propSolids = prop.init();
    
    for (int i = 0; i < propSolids.length; ++i) {
      Solid solid = propSolids[i];
      if (solid == null) {
        continue;
      }
      
      solid.parentProp = prop;
      
      solids.add(solid);
      
      if (solid instanceof DynamicSolid) {
        dynamicSolids.add((DynamicSolid)solid);
      }
    }
  }
  
  
  public void preDraw() {
  }
  public void draw(Graphics2D g2d) {
    // fill background
    g2d.setColor(Color.WHITE);
    g2d.fill(new Rectangle2D.Double(0.0d, 0.0d, width, height));
    
    // draw all props
    for (Prop prop: props) {
      prop.draw(g2d);
    }
    
    // draw all collisions
    for (Collision collision: frameCollisions) {
      collision.draw(g2d);
    }
  }
  public void drawHUD(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);
    g2d.drawString("Collisions: " + frameCollisions.size(), 10, 20);
  }
  public void postDraw() {}
  
  public abstract void mousePressed(double x, double y, MouseEvent mouseEvent);
}
