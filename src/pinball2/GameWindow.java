package pinball2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;
  
  private final GameInputListener inputListener;
  private final GamePanel gamePanel;
  private final JButton pauseButton;
  
  public GameWindow(GameDrawer drawer, GameInputListener inputListener) {
    super("Pinball 2.0");
    
    this.inputListener = inputListener;
    gamePanel = new GamePanel(drawer);
    pauseButton = new JButton("Pause");
    
    JPanel buttonPannel = new JPanel();
    buttonPannel.add(pauseButton);
    
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(gamePanel,    BorderLayout.CENTER);
    contentPane.add(buttonPannel, BorderLayout.SOUTH);
    
    pauseButton.addActionListener(this);
    
    setSize(500, 500);
  }
  
  public void display() {
    setVisible(true);
  }
  
  public void draw() {
    gamePanel.repaint();
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == pauseButton) {
      inputListener.pauseButtonPressed();
    }
  }
  
  
  private static class GamePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final GameDrawer drawer;
    
    public GamePanel(GameDrawer drawer) {
      super();
      this.drawer = drawer;
    }
    
    @Override
    public void paintComponent(Graphics g) {
      drawer.preDraw();
      drawer.draw(g, getWidth(), getHeight());
      drawer.postDraw();
	}
  }
}
