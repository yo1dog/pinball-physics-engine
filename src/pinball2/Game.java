package pinball2;

import java.awt.Graphics2D;

import pinball2.tables.Table;
import pinball2.tables.TestTable;

public class Game {
  private boolean paused = false;
  private final Table table;
  
  public Game() {
    table = new TestTable();
  }
  
  public void init(GameWindow window) {
    table.init();
  }
  
  public void preUpdate() {
    if (paused) {
      return;
    }
    
    table.preUpdate();
  }
  public void update(long dTimeNS) {
    if (paused) {
      return;
    }
    
    table.update(dTimeNS);
  }
  public void postUpdate() {
    if (paused) {
      return;
    }
    
    table.postUpdate();
  }
  
  public void preDraw() {
    table.preDraw();
  }
  public void draw(Graphics2D g2d) {
    table.draw(g2d);
  }
  public void postDraw() {
    table.postDraw();
  }
  
  
  public void togglePause() {
    paused = !paused;
  }
  
  public void mousePressed(double x, double y) {
    table.mousePressed(x, y);
  }
}
