package pinball2;

public class Vector {
  public final double x, y;
  public final double mag;
  
  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
    mag = calcMagnitude(x, y);
  }
  public Vector(Vector vectorA, Vector vectorB) {
    x = vectorB.x - vectorA.x;
    y = vectorB.y - vectorA.y;
    mag = calcMagnitude(x, y);
  }
  
  private Vector(double x, double y, double mag) {
    this.x = x;
    this.y = y;
    this.mag = mag;
  }
  
  
  public Vector add(Vector vector) {
    return new Vector(x + vector.x, y + vector.y);
  }
  public Vector subtract(Vector vector) {
    return new Vector(x - vector.x, y - vector.y);
  }
  public Vector scale(double scale) {
    return new Vector(x*scale, y*scale, mag*scale);
  }
  
  public double dot(Vector vector) {
    return x*vector.x + y*vector.y;
  }
  
  public Vector normalize() {
    // shortcut for this.scale(1 / mag);
    double scale = 1/mag;
    return new Vector(x*scale, y*scale, 1.0d);
  }
  public Vector getNormal(Vector vector) {
    // shortcut for new Vector(this, vector).getPerpendicular().normalize()
    return new Vector(y - vector.y, vector.x - x).normalize();
  }
  public Vector getPerpendicular() {
    return new Vector(y, -x, mag);
  }
  
  public Vector copy() {
    return new Vector(x, y, mag);
  }
  
  public boolean equals(Vector vector) {
    return x == vector.x && y == vector.y;
  }
  
  public static double calcMagnitude(double x, double y) {
    return Math.sqrt(x*x + y*y);
  }
}
