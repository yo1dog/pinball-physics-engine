package pinball2.solids;

public class BodyProperties {
  public final double mass;
  
  public BodyProperties() {
    this(1.0d);
  }
  public BodyProperties(double mass) {
    this.mass = mass;
  }
}
