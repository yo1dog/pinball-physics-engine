package pinball2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class GameWindow extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;
  
  private final GameInputListener inputListener;
  private final GamePanel gamePanel;
  private final JButton pauseButton;
  
  public GameWindow(Game game, GameInputListener inputListener) {
    super("Pinball 2.0");
    
    this.inputListener = inputListener;
    gamePanel = new GamePanel(game, inputListener);
    pauseButton = new JButton("Pause");
    
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    
    JPanel buttonPannel = new JPanel();
    buttonPannel.add(pauseButton);
    
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(gamePanel,    BorderLayout.CENTER);
    contentPane.add(buttonPannel, BorderLayout.SOUTH);
    
    pauseButton.addActionListener(this);
    setSize(400, 800);
  }
  
  public void display() {
    setVisible(true);
  }
  
  public void draw() {
    gamePanel.repaint();
  }
  
  public void setGamePanelSize(int width, int height) {
    gamePanel.setPreferredSize(new Dimension(width, height));
  }
  
  private static class GamePanel extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;
    private final Game game;
    private final GameInputListener inputListener;
    
    public final double pxPerM = 400;
    
    public GamePanel(Game game, GameInputListener inputListener) {
      super();
      this.game = game;
      this.inputListener = inputListener;
      this.addMouseListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
      Graphics2D g2d = (Graphics2D)g;
      
      g2d.setColor(Color.BLACK);
      g2d.fillRect(0, 0, getWidth(), getHeight());
      
      //AffineTransform originalTransform = g2d.getTransform();
      AffineTransform transform = new AffineTransform();
      transform.setToScale(pxPerM, pxPerM);
      g2d.transform(transform);
      
      game.preDraw();
      game.draw(g2d);
      game.postDraw();
      
      //g2d.setTransform(originalTransform);
	}
    
    @Override public void mousePressed(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();
      
      inputListener.mousePressed(x / pxPerM, y / pxPerM);
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
  }
  
  
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == pauseButton) {
      inputListener.pauseButtonPressed();
    }
  }
}
