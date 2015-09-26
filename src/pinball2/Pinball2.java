package pinball2;

public class Pinball2 {
  public static void main(String[] args) {
    Game game = new Game();
    GameWindow window = new GameWindow();
    GameRunner runner = new GameRunner(game, window);
    
    game.init();
    window.show();
    runner.start();
  }
}
