package com.yo1dog.pinball_physics_engine.tables;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.yo1dog.pinball_physics_engine.Vector;
import com.yo1dog.pinball_physics_engine.props.Ball;
import com.yo1dog.pinball_physics_engine.props.Bumper;
import com.yo1dog.pinball_physics_engine.props.Wall;
import com.yo1dog.pinball_physics_engine.solids.SurfaceProperties;

public class TestTable extends Table {
  private final Ball ball1;
  private final Ball ball2;
  
  public TestTable() {
    super(2.0d, 4.0d, 0.349d/*~20deg*/, new SurfaceProperties());
    
    ball1 = new Ball(new Vector(0.8, 0.0d), 0.04d, Color.GREEN);
    ball2 = new Ball(new Vector(0.3, 0.1d), 0.04d, Color.CYAN);
    
    this.addProp(ball1);
    this.addProp(ball2);
    
    double w = 425.0d;
    double h = 400.0d;
    this.addProp(new Wall(new Vector(0  /w, 0  /h), new Vector(500/w, 0  /h), false));
    this.addProp(new Wall(new Vector(50 /w, 700/h), new Vector(0  /w, 0  /h), false));
    this.addProp(new Wall(new Vector(500/w, 200/h), new Vector(450/w, 700/h), false));
    
    this.addProp(new Wall(new Vector(500/w, 0  /h), new Vector(550/w, 100/h), false));
    this.addProp(new Wall(new Vector(550/w, 100/h), new Vector(600/w, 300/h), false));
    this.addProp(new Wall(new Vector(550/w, 100/h), new Vector(500/w, 200/h), false));
    this.addProp(new Wall(new Vector(600/w, 300/h), new Vector(600/w, 800/h), false));
    this.addProp(new Wall(new Vector(550/w, 800/h), new Vector(500/w, 200/h), false));
    this.addProp(new Wall(new Vector(600/w, 800/h), new Vector(550/w, 800/h), false));
    
    this.addProp(new Wall(new Vector(450/w, 700/h), new Vector(350/w, 800/h), false));
    this.addProp(new Wall(new Vector(350/w, 800/h), new Vector(150/w, 800/h), false));
    this.addProp(new Wall(new Vector(150/w, 800/h), new Vector(50 /w, 700/h), false));
    

    this.addProp(new Wall(new Vector(280/w, 80 /h), new Vector(400/w, 200/h), true));
    this.addProp(new Wall(new Vector(400/w, 200/h), new Vector(250/w, 350/h), true));
    this.addProp(new Wall(new Vector(250/w, 350/h), new Vector(100/w, 200/h), true));
    this.addProp(new Wall(new Vector(100/w, 200/h), new Vector(220/w, 80/h), true));
    
    this.addProp(new Bumper(new Vector(210/w, 160/h), 0.035, 0.5d, 2.0d));
    this.addProp(new Bumper(new Vector(290/w, 160/h), 0.035, 0.5d, 2.0d));
    this.addProp(new Bumper(new Vector(290/w, 240/h), 0.035, 0.5d, 2.0d));
    this.addProp(new Bumper(new Vector(210/w, 240/h), 0.035, 0.5d, 2.0d));
    this.addProp(new Bumper(new Vector(370/w, 200/h), 0.035, 0.5d, 2.0d));
    this.addProp(new Bumper(new Vector(130/w, 200/h), 0.035, 0.5d, 2.0d));
    this.addProp(new Bumper(new Vector(250/w, 320/h), 0.035, 0.5d, 2.0d));
  }

  @Override
  public void mousePressed(double x, double y, MouseEvent mouseEvent) {
    if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
      ball1.pos = new Vector(x, y);
      ball1.vel = new Vector(0, 0);
    }
    else if (SwingUtilities.isRightMouseButton(mouseEvent)) {
      ball2.pos = new Vector(x, y);
      ball2.vel = new Vector(0, 0);
    }
    else {
      this.addProp(new Ball(new Vector(x, y), 0.04d));
    }
  }
}
