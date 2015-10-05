package pinball2.tables;

import pinball2.Vector;
import pinball2.props.Ball;
import pinball2.props.Bumper;
import pinball2.solids.SurfaceProperties;

public class TestTable extends Table {
  private final Ball ball;
  
  public TestTable() {
    super(1.0d, 2.0d, 0.349d/*~20deg*/, new SurfaceProperties());
    
    ball = new Ball(new Vector(0.5, 0.5d), 0.05d);
    
    this.addProp(ball);
    this.addProp(new Bumper(new Vector(0.5, 1.0d), 0.125d, 0.0d, 0.0d));
  }

  @Override
  public void mousePressed(double x, double y) {
    ball.pos = new Vector(x, y);
    ball.vel = new Vector(0, 0);
  }
}
