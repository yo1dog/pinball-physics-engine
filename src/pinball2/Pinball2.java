package pinball2;

public class Pinball2 {
  public static void main(String[] args) {
    Game              game   = new Game();
    GameInputListener input  = new GameInputListener(game);
    GameWindow        window = new GameWindow(game, input);
    GameRunner        runner = new GameRunner(game, window);
    
    game.init(window);
    window.display();
    runner.start();
  }
}
