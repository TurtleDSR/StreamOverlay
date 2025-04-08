import include.java.web.*;

import java.awt.*;
import java.awt.event.*;

import java.io.InputStream;

import javax.swing.*;

public class Main {
  public ServerConfig config;

  public JFrame frame;
  public JPanel mainPanel;
  public JLabel countLabel;

  public static Font poppins;

  public static void main(String[] args) throws Exception{
    new Main();
  }

  public Main() throws Exception {
    poppins = loadPoppins();
    
    config = new ServerConfig(ServerConfig.DONOTRESETCONFIGS);

    frame = new JFrame("Counter");
    mainPanel = new JPanel(new BorderLayout(5, 0));

    countLabel = new JLabel("" + config.runCount);
    countLabel.setFont(poppins.deriveFont(50f));
    
    mainPanel.add(countLabel);
    frame.add(mainPanel);

    frame.setSize(300, 100);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setAlwaysOnTop(true);

    new ServerSocket(config);
    IncrementAction increment = new IncrementAction(this);
    DecrementAction decrement = new DecrementAction(this);

    InputMap inMap = mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = mainPanel.getActionMap();

    inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.ALT_DOWN_MASK), "increment");
    inMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.ALT_DOWN_MASK), "decrement");

    actionMap.put("increment", increment);
    actionMap.put("decrement", decrement);
  }

  public static Font loadPoppins() throws Exception{
    InputStream is = Main.class.getResourceAsStream("include/web/static/font/Poppins-Regular.ttf");
    return Font.createFont(Font.TRUETYPE_FONT, is);
  }

  //action classes

  static class IncrementAction extends AbstractAction{
    public Main parent;

    public IncrementAction(Main parent) {
      this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      parent.countLabel.setText("" + ++parent.config.runCount);
      parent.config.writeConfigs();
    }
  }

  static class DecrementAction extends AbstractAction{
    public Main parent;

    public DecrementAction(Main parent) {
      this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      parent.countLabel.setText("" + --parent.config.runCount);
      parent.config.writeConfigs();
    }
  }
}