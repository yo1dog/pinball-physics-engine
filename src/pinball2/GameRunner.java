package pinball2;

public class GameRunner implements Runnable {
  public final static long TARGET_FPS = 60;
  public final static long TARGET_FRAME_DURATION_NS = 1000000000l / TARGET_FPS;
  
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
      long startTimeNS = System.nanoTime();
      
      synchronized(game) {
        game.preUpdate();
        game.update(TARGET_FRAME_DURATION_NS);
        game.postUpdate();
        
        window.draw();
        
        ++game.tick;
        if (game.tick > 59) {
          game.tick = 0;
        }
      }
      
      long afterUpdateTimeNS = System.nanoTime();
      long updateDurationNS = afterUpdateTimeNS - startTimeNS;
      
      long waitDurationNS = TARGET_FRAME_DURATION_NS - updateDurationNS;
      long waitDurationMS = (waitDurationNS / 1000000) - 3;
      
      try {
        Thread.sleep(Math.max(1, waitDurationMS));
      }
      catch(Exception e) {
        e.printStackTrace();
      }
      
      while (System.nanoTime() < startTimeNS + TARGET_FRAME_DURATION_NS) {
        // noop
      }
      
      long frameDurationNS = System.nanoTime() - startTimeNS;
      game.fps = (int)Math.round(1.0d / (frameDurationNS / 1000000000.0d));
    }
  }
}
