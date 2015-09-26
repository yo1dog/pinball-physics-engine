package pinball2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
  private static final long serialVersionUID = 1L;
  
  final long TARGET_FPS = 60;
  final long TARGET_FRAME_DURATION_NS = 1000000000l / TARGET_FPS;
  
  private final Game game;
  private final GamePanel gamePanel;
  private boolean running = true;
  
  
  public GameWindow(Game game)
  {
    super("Pinball 2.0");
    
    this.game = game;
    
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(gamePanel, BorderLayout.CENTER);
    
    setSize(500, 500);
  }
  
  public void actionPerformed(ActionEvent e)
  {
    /*
    Object source = e.getSource();
    if (source == ...) {}
    */
  }
}
