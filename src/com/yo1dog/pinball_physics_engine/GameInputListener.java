package com.yo1dog.pinball_physics_engine;

import java.awt.event.MouseEvent;

public class GameInputListener {
  private final Game game;
  
  public GameInputListener(Game game) {
    this.game = game;
  }
  
  public void pauseButtonPressed() {
    game.togglePause();
  }
  
  public void mousePressed(double x, double y, MouseEvent mouseEvent) {
    game.mousePressed(x, y, mouseEvent);
  }
}
