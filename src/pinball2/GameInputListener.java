package pinball2;

public class GameInputListener {
  private final Game game;
  
  public GameInputListener(Game game) {
    this.game = game;
  }
  
  public void pauseButtonPressed() {
    game.togglePause();
  }
  
  public void mousePressed(double x, double y) {
    game.mousePressed(x, y);
  }
}
