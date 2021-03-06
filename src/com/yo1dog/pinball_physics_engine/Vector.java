package com.yo1dog.pinball_physics_engine;

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
    return new Vector(x*scale, y*scale, Math.abs(mag*scale));
  }
  
  public double dot(Vector vector) {
    return x*vector.x + y*vector.y;
  }
  
  public Vector normalize() {
    // shortcut for this.scale(1 / mag);
    if (mag == 0) {
      return zero;     
    }
    
    return new Vector(x/mag, y/mag, 1.0d);  
  }
  public Vector getNormal() {
    // shortcut for getPerpendicular().normalize()
    if (mag == 0) {
      return zero;      
    }
    
    return new Vector(y/mag, -x/mag, 1.0d);  
  }
  public Vector getPerpendicular() {
    return new Vector(y, -x, mag);
  }
  public Vector invert() {
    return new Vector(-x, -y, mag);
  }
  
  public Vector copy() {
    return new Vector(x, y, mag);
  }
  
  public boolean equals(Vector vector) {
    return x == vector.x && y == vector.y;
  }
  
  public double distance(Vector vectorB) {
    double dx = vectorB.x - x;
    double dy = vectorB.y - y;
    
    return Math.sqrt(dx*dx + dy*dy);
  }
  
  public static double calcMagnitude(double x, double y) {
    return Math.sqrt(x*x + y*y);
  }
  
  public String toString() {
    return "<" + this.x + ", " + this.y + ">";
  }
  
  public static final Vector zero = new Vector(0, 0, 0);
}
