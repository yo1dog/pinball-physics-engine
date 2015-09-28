package pinball2;

import java.awt.Graphics;

public class GameDrawer {
  private final Game game;
  
  public GameDrawer(Game game) {
    this.game = game;
  }
  
  public void preDraw() {}
  public void draw(Graphics g, int width, int height) {}
  public void postDraw() {}
}
