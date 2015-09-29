package pinball2.tables;

import pinball2.Vector;
import pinball2.props.Ball;
import pinball2.props.Bumper;
import pinball2.solids.SurfaceProperties;

public class TestTable extends Table {

  public TestTable() {
    super(400, 800, 0.349d/*~20deg*/, new SurfaceProperties());
    
    this.addProp(new Ball(new Vector(175.0d, 300.0d), 10.0d));
    this.addProp(new Bumper(new Vector(200.0d, 400.0d), 50.0d, 0.0d, 0.0d));
  }
}
