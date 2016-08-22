package com.yo1dog.pinball_physics_engine;

public class Main {
  public static GameWindow window;
  
  public static void main(String[] args) {
    Game              game   = new Game();
    GameInputListener input  = new GameInputListener(game);
    GameWindow        window = new GameWindow(game, input);
    GameRunner        runner = new GameRunner(game, window);
    
    Main.window = window;
    
    game.init(window);
    window.display();
    
    // wait a bit and then redraw the window to prevent some display bugs
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {}
    window.invalidate();
    
    runner.start();
  }
}
