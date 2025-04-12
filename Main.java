import include.java.web.*;
import include.java.config.*;
import include.java.gui.*;

import java.awt.*;
import java.awt.event.*;

import java.io.InputStream;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class Main {
  public ServerConfig config;

  public JFrame frame;

  public JTabbedPane tabbedPane;

  public JPanel countPanel;
  public JPanel colorPanel;

  public JLabel countLabel;
  public JColorChooser textChooser;
  public JColorChooser backgroundChooser;

  public static Font poppins;

  public static void main(String[] args) throws Exception{
    new Main();
  }

  public Main() throws Exception {
    poppins = loadPoppins();

    config = new ServerConfig(ServerConfig.DONOTRESETCONFIGS);
    
    frame = new JFrame("Counter");

    tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

    countPanel = new JPanel(new BorderLayout(10, 0));
    colorPanel = new JPanel(new GridLayout(2, 1));

    JLabel keybindUpLabel = new JLabel("Alt+Up: Increment");
    keybindUpLabel.setFont(poppins.deriveFont(25f));

    JLabel keybindDownLabel = new JLabel("Alt+Down: Decrement");
    keybindDownLabel.setFont(poppins.deriveFont(25f));

    countLabel = new JLabel("" + config.runCount);
    countLabel.setFont(poppins.deriveFont(50f));
    
    countPanel.add(keybindUpLabel, BorderLayout.NORTH);
    countPanel.add(keybindDownLabel, BorderLayout.SOUTH);
    countPanel.add(countLabel, BorderLayout.CENTER); 

    tabbedPane.addTab("Counter", countPanel);

    textChooser = new JColorPicker(ServerConfig.hextoColor(config.textColor), "textColor");
    textChooser.getSelectionModel().addChangeListener(new ColorPickerChanged(this));

    keybindUpLabel.setForeground(textChooser.getColor());
    keybindDownLabel.setForeground(textChooser.getColor());

    backgroundChooser = new JColorPicker(ServerConfig.hextoColor(config.backgroundColor), "backgroundColor");
    backgroundChooser.getSelectionModel().addChangeListener(new ColorPickerChanged(this));

    tabbedPane.addTab("Text Color", textChooser);
    tabbedPane.addTab("Background Color", backgroundChooser);

    frame.add(tabbedPane);

    frame.pack();
    frame.setVisible(true);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setAlwaysOnTop(true);

    new ServerSocket(config);
    IncrementAction increment = new IncrementAction(this);
    DecrementAction decrement = new DecrementAction(this);

    InputMap inMap = countPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = countPanel.getActionMap();

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

  static class ColorPickerChanged implements ChangeListener {
    public Main parent;

    public ColorPickerChanged(Main parent) {
      this.parent = parent;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
      JColorPicker cp = ((ValuedSelectionModel) e.getSource()).parent;
      String actionID = cp.actionID;

      if(actionID.equals("textColor")) {
        cp.updateColor();
        parent.config.textColor = cp.toHex();
        parent.config.writeConfigs();
      } else {
        cp.updateColor();
        parent.config.backgroundColor = cp.toHex();
        parent.config.writeConfigs();
      }
    }
  }
}