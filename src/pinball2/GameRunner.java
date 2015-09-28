package pinball2;

public class GameRunner implements Runnable {
  private final static long TARGET_FPS = 60;
  private final static long TARGET_FRAME_DURATION_NS = 1000000000l / TARGET_FPS;
  
  private final Game game;
  private final GameWindow window;
  private boolean running = false;
  
  public GameRunner(Game game, GameWindow window) {
    this.game   = game;
    this.window = window;
  }
  
  public void start() {
    if (running)
      return;
    
    running = true;
    run();
  }
  public void stop() {
    running = false;
  }
  
  @Override
  public void run() {
    while (running) {
      long beforeUpdateTimeNS = System.nanoTime();
      
      game.preUpdate();
      game.update(TARGET_FRAME_DURATION_NS);
      game.postUpdate();
      
      window.draw();
      
      long afterUpdateTimeNS = System.nanoTime();
      long updateDurationNS = afterUpdateTimeNS - beforeUpdateTimeNS;
      
      long waitDurationNS = TARGET_FRAME_DURATION_NS - updateDurationNS;
      long waitDurationMS = waitDurationNS / 1000000;
      
      try {
        Thread.sleep(Math.max(1, waitDurationMS));
      }
      catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
}
