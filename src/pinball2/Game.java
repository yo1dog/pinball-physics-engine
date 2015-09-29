package pinball2;

import java.awt.Graphics;

import pinball2.tables.Table;
import pinball2.tables.TestTable;

public class Game {
  private boolean paused = false;
  private final Table table;
  
  public Game() {
    table = new TestTable();
  }
  
  public void init(GameWindow window) {
    window.setGamePanelSize(table.width, table.height);
    
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
  public void draw(Graphics g, int pxWidth, int pxHeight, double mPerPx) {
    table.draw(g, pxWidth, pxHeight, mPerPx);
  }
  public void postDraw() {
    table.postDraw();
  }
  
  public void togglePause() {
    paused = !paused;
  }
}
