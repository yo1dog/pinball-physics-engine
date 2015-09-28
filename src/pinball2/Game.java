package pinball2;

public class Game {
  private boolean paused = false;
  
  public void init() {}
  
  public void preUpdate() {}
  public void update(long dTimeNS) {
    if (paused) {
      return;
    }
  }
  public void postUpdate() {}
  
  
  public void togglePause() {
    paused = !paused;
  }
}
