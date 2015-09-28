package pinball2;

public class Pinball2 {
  public static void main(String[] args) {
    Game              game   = new Game();
    GameInputListener input  = new GameInputListener(game);
    GameDrawer        drawer = new GameDrawer(game);
    GameWindow        window = new GameWindow(drawer, input);
    GameRunner        runner = new GameRunner(game, window);
    
    game.init();
    window.display();
    runner.start();
  }
}
