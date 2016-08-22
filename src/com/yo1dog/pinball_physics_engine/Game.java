package com.yo1dog.pinball_physics_engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import com.yo1dog.pinball_physics_engine.tables.Table;
import com.yo1dog.pinball_physics_engine.tables.TestTable;

public class Game {
  private boolean paused = false;
  private final Table table;
  public int fps = 0;
  public int tick = 0;
  
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
  public void drawHUD(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);
    g2d.drawString("FPS: " + fps + " - " + tick, 10, 15);
    
    table.drawHUD(g2d);
  }
  public void postDraw() {
    table.postDraw();
  }
  
  
  public void togglePause() {
    paused = !paused;
  }
  
  public void mousePressed(double x, double y, MouseEvent mouseEvent) {
    table.mousePressed(x, y, mouseEvent);
  }
}
